Para la gestion de los datos se uso MySQL Server en este proyecto.
Por lo cual se sugieren los siguientes scripts.

Script para root BD:
-- 1. Asegúrate de que la base de datos exista
CREATE DATABASE IF NOT EXISTS restaurante;

-- 2. Crear el usuario gerente con contraseña
CREATE USER IF NOT EXISTS 'adminRes'@'localhost' IDENTIFIED BY '1234';

-- 3. Refresca los privilegios actuales
FLUSH PRIVILEGES;

-- 4. Dar todos los privilegios sobre la base de datos farmacia
GRANT ALL PRIVILEGES ON `restaurante`.* TO 'adminRes'@'localhost';

-- 5. Refrescar nuevamente
FLUSH PRIVILEGES;

-- 6. Permitir crear funciones si el binlog está activo
SET GLOBAL log_bin_trust_function_creators = 1;

Script para usuario adminRes BD:

USE restaurante;

-- Borrados
drop view vista_menu_activos;
drop TABLE productoMenu;
drop PROCEDURE agregar_productoMenu;


-- Tabla productoMenu
CREATE TABLE productoMenu (
    id_productoMenu INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    categoria VARCHAR(50) NOT NULL, 
    imagenRuta VARCHAR(255) NULL,       -- Suficiente para rutas relativas
    precio DECIMAL(10,2) NOT NULL,
    ingredientes VARCHAR(500) NOT NULL, -- Varchar extendido para descripciones
    disponibilidad BOOLEAN NOT NULL DEFAULT TRUE, 
    estado BOOLEAN NOT NULL DEFAULT TRUE          
);

DELIMITER //
-- Agregar un productoMenu a la BD
CREATE PROCEDURE agregar_productoMenu(
    IN p_nombre VARCHAR(100),
    IN p_categoria VARCHAR(50),
    IN p_imagen VARCHAR(255),
    IN p_precio DECIMAL(10,2),
    IN p_ingredientes VARCHAR(500)
)
BEGIN

	IF (SELECT COUNT(*) FROM productoMenu WHERE LOWER(nombre) = LOWER(p_nombre) AND 
		estado = TRUE) > 0 THEN
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'El nombre del producto ya existe';
	END IF;
    
    -- Insertamos el producto. 
    -- 'disponibilidad' y 'estado' se ponen en TRUE por defecto.
    INSERT INTO productoMenu (
        nombre, 
        categoria, 
        imagenRuta, 
        precio, 
        ingredientes, 
        disponibilidad, 
        estado
    ) 
    VALUES (
        p_nombre, 
        p_categoria, 
        p_imagen, 
        p_precio, 
        p_ingredientes, 
        TRUE, 
        TRUE
    );
END //

DELIMITER ;

-- Vista para obtener todos los productoMenu activos
CREATE VIEW vista_menu_activos AS
SELECT 
    id_productoMenu, 
    nombre, 
    categoria, 
    imagenRuta, 
    precio, 
    ingredientes, 
    disponibilidad
FROM productoMenu
WHERE estado = TRUE; 

select * From productoMenu;

-- Insercion productos menu
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) 
    VALUES ('pizza de pepperoni', 'platillo', 'img_productos/pizza.jpg', 99, 'Salsa de tomate, peperoni, queso, especias', TRUE, TRUE);
    
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) 
    VALUES ('hamburguesa', 'platillo', 'img_productos/hamburguesa.jpg', 79, 'Lechuga, tomate, carne de res, aderezos', TRUE, TRUE);


	



-- Seccion de los pedidos

DROP TABLE pedidosTab;
-- Tabla de pedidos
CREATE TABLE pedidosTab (idPedido INT PRIMARY KEY AUTO_INCREMENT NOT NULL, producto VARCHAR (100) NOT NULL, cantidad INT NOT NULL, 
subtotal DECIMAL(10,2) NOT NULL, foreign key (producto) REFERENCES productoMenu(nombre), estado BOOLEAN NOT NULL DEFAULT TRUE, 
preparado BOOLEAN NOT NULL DEFAULT FALSE, mesa INT NOT NULL);


DROP PROCEDURE agregarPedido;
-- Agregar pedidos a la BD
DELIMITER // 
CREATE PROCEDURE agregarPedido( IN p_producto VARCHAR(100), IN p_cantidad int, p_mesa int) BEGIN
Declare p_subtotal DECIMAL(10,2);
Declare precioU DECIMAL(10,2);

-- Obtenemos el precio unitario
SELECT precio INTO precioU 
FROM productoMenu 
WHERE nombre = p_producto 
LIMIT 1;

-- Calculamos el subtotal
SET p_subtotal = precioU * p_cantidad;

-- Insertamos el pedido. 
INSERT INTO pedidosTab (
    producto, 
    cantidad,
    subtotal,
    estado,
    preparado,
    mesa
) 
VALUES (
    p_producto, 
    p_cantidad,
    p_subtotal,
    TRUE,
    FALSE,
    p_mesa
);    
END //
DELIMITER ;

DROP PROCEDURE pedidosMesa;
-- Vista para obtener todos los productoMenu por mesa 
DELIMITER //
CREATE PROCEDURE pedidosMesa (p_mesa int) 
	BEGIN
	SELECT idPedido, producto, cantidad, subtotal, estado, preparado, mesa
    FROM pedidosTab
    WHERE mesa = p_mesa;
END//
DELIMITER ;

DROP PROCEDURE cancelarPedido;
-- Vista para obtener todos los productoMenu por mesa 
DELIMITER //
CREATE PROCEDURE cancelarPedido (p_id int)
BEGIN
DECLARE v_estado BOOLEAN;
	-- Verificamos el estado actual del pedido
	SELECT estado INTO v_estado
    FROM pedidosTab
    WHERE p_id= idPedido;
	-- Verificamos si el pedido no esta cancelado
    IF v_estado IS FALSE THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: El pedido ya esta cancelado';
	
    ELSE
		UPDATE pedidosTab
			SET estado = FALSE
			WHERE idPedido = p_id;
    END IF;
END //
DELIMITER ;

-- Inserciones para pruebas 

INSERT INTO pedidosTab (producto, cantidad, subtotal, estado) VALUES ('pizza de pepperoni', 2, 250.00, TRUE);
call  agregarPedido('pizza de pepperoni', 2, 1);
call  agregarPedido('hamburguesa', 4, 2);
call  agregarPedido('pizza de pepperoni', 6, 3);
call  agregarPedido('pizza de pepperoni', 2, 3);
call eliminarPedido(1);
call pedidosMesa(3);
call cancelarPedido(4);

-- Tablas y procedimientos de las mesas
DROP TABLE mesas;
-- Tabla de mesas
CREATE TABLE mesas (
	idMesa INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	-- El propio id
    estadoMesa BOOLEAN NOT NULL DEFAULT FALSE,
	-- Si está ocupada o no
    cantidadPersonasPosibles INT NOT NULL
	-- Cantidad de personas que se pueden sentar
);

DROP PROCEDURE agregarMesa;
-- Procedimiento pa añadir mesas
DELIMITER //
CREATE PROCEDURE agregarMesa (cantidad INT)
	BEGIN
		-- Solo la cantidad es necesario, la id es automática y el estado default a False
		INSERT INTO mesas (cantidadPersonasPosibles)
		VALUES (cantidad);
    END //
DELIMITER ;

DROP PROCEDURE eliminarMesa;
-- Procedimiento pa eliminar mesas
DELIMITER //
CREATE PROCEDURE eliminarMesa (id INT)
	BEGIN
		DELETE FROM mesas
		WHERE idMesa = id;
	END //
DELIMITER ;

-- Tabla y procedimientos de reservacion
DROP TABLE reservaciones;
-- Tabla para las reservaciones
CREATE TABLE reservaciones (
	idReservacion INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	-- La ID
    mesa INT NOT NULL,
    foreign key (mesa) REFERENCES mesas(idMesa),
	-- La ID de la mesa en la que está, no implemente que se puedan multiples mesas, honestamente que eso sea problema de los clientes
    personas INT NOT NULL,
    fecha DATE,
    hora TIME,
    nombre VARCHAR(100)
	-- A nombre de quién estará la reservación
);

DROP PROCEDURE reservarMesa;
-- Función pa reservar mesas
DELIMITER //
CREATE PROCEDURE reservarMesa (cantPersonas INT, fecha DATE, hora TIME, aNombre VARCHAR(100))
	BEGIN
		-- Toda esta primera parte con el select es para asegurarse que dos reservaciones estén en el mismo día con menos de 3 horas entre sí
		DECLARE mesa INT;
		SELECT M.idMesa
		INTO mesa
		FROM mesas M
		WHERE M.cantidadPersonasPosibles >= cantPersonas
		AND NOT EXISTS (SELECT R.mesa FROM reservaciones R WHERE R.fecha = fecha AND TIMEDIFF(hora, R.hora) < '03:00:00' AND M.idMesa = R.mesa)
		LIMIT 1;
        IF (mesa IS NOT NULL) THEN
			INSERT INTO reservaciones (mesa, personas, fecha, hora, nombre)
			VALUES (mesa, cantPersonas, fecha, hora, aNombre);
		END IF;
    END //
DELIMITER ;

DROP PROCEDURE eliminarReservacion;
-- Procedimiento pa eliminar reservaciones
DELIMITER //
CREATE PROCEDURE eliminarReservacion (id INT)
	BEGIN
		DELETE FROM reservaciones
		WHERE idReservacion = id;
	END //
DELIMITER ;
