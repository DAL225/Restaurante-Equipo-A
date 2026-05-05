Para la gestion de los datos se uso MySQL Server en este proyecto.
Por lo cual se sugieren los siguientes scripts.

Script para root BD:
-- 1. Asegúrate de que la base de datos exista
CREATE DATABASE IF NOT EXISTS restaurante;

-- 2. Crear el usuario gerente con contraseña
CREATE USER IF NOT EXISTS 'adminRes'@'localhost' IDENTIFIED BY '1234';

-- 3. Refresca los privilegios actuales
FLUSH PRIVILEGES;

-- 4. Dar todos los privilegios sobre la base de datos restaurante
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

-- Productos de entrada
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Sopa Azteca', 'entrada', 'img_productos/sopa_azteca.jpg', 50, 'tomate, tiras de tortilla, chile, pollo, queso, crema', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Sopa de fideos', 'entrada', 'img_productos/sopa_fideos.jpg', 50, 'fideos, tomate,', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Chileatole', 'entrada', 'img_productos/chileatole.jpg', 60, 'tomate, chile verde, carne de res, maiz', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Crema de calabaza', 'entrada', 'img_productos/crema_calabaza.jpg', 60, 'calabaza verde, cebolla, ajo, nata', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Crema de zanahoria', 'entrada', 'img_productos/crema_zanahoria.jpg', 60, 'zanahoria, cebolla, ajo, nata', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Consome de verduras', 'entrada', 'img_productos/consome.jpg', 60, 'zanahoria, cebolla, calabaza, arroz, pollo', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Panza de res', 'entrada', 'img_productos/panza_res.jpg', 60, 'carne de rez, cebolla, ajo, laurel, chile guajillo', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Totopos', 'entrada', 'img_productos/totopos.jpg', 30, 'totopos, frijoles, queso', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Nachos', 'entrada', 'img_productos/nachos.jpg', 40, 'nachos, queso amarillo, chile jalapeño', TRUE, TRUE);

-- Productos platillos
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('pizza de pepperoni', 'platillo', 'img_productos/pizza.jpg', 99, 'Salsa de tomate, peperoni, queso, especias', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('hamburguesa', 'platillo', 'img_productos/hamburguesa.jpg', 79, 'Lechuga, tomate, carne de res, aderezos', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Enchiladas suizas', 'platillo', 'img_productos/enchiladas_suizas.jpg', 90, 'tortilla, queso amarillo, tomate verde, chile serrano, aceite', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Chilaquiles', 'platillo', 'img_productos/chilaquiles.jpg', 90, 'totopos, queso fresco, jitomate, chile guajillo', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Milanesa de pollo', 'platillo', 'img_productos/milanesa_pollo.jpg', 110, 'pollo, pan molido, jitomate, lechuga, cebolla, aceite', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Ensalada césar', 'platillo', 'img_productos/ensalada_cesar.jpg', 100, 'jitomate, lechuga, cebolla, crotones, queso parmesano', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Huevos a la mexicana', 'platillo', 'img_productos/huevos_mexicana.jpg', 90, 'huevos, jitomate, cebolla, aceite', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Enfrijoladas', 'platillo', 'img_productos/enfrijoladas.jpg', 100, 'frijoles, tortilla, cebolla, queso, crema, aceite', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Pozole', 'platillo', 'img_productos/pozole.jpg', 90, 'maiz, tomate, cebolla, rabano, lechuga, chile', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Tacos dorados', 'platillo', 'img_productos/tacos_dorados.jpg', 100, 'tortilla, lechuga, papa, queso, crema, aceite', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Empanadas', 'platillo', 'img_productos/empanadas.jpg', 100, 'masa, pollo, queso, crema, aceite', TRUE, TRUE);

-- Bebidas
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Agua de jamaica', 'bebida', 'img_productos/agua_jamaica.jpg', 50, 'hoja de jamaica, azucar', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Agua de horchata', 'bebida', 'img_productos/agua_horchata.jpg', 50, 'leche, avena, azucar', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Agua simple', 'bebida', 'img_productos/agua_simple.jpg', 30, '', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Cocacola', 'bebida', 'img_productos/cocacola.jpg', 40, '', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Manzanita', 'bebida', 'img_productos/manzanita.jpg', 40, '', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('7even', 'bebida', 'img_productos/7even.jpg', 40, '', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Cafe', 'bebida', 'img_productos/cafe.jpg', 40, 'Grano de cafe, azucar, leche', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Limonada', 'bebida', 'img_productos/limonada.jpg', 50, 'Limon, azucar', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Naranjada', 'bebida', 'img_productos/naranjada.jpg', 50, 'Naranja, azucar', TRUE, TRUE);

-- Postres
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Platanos fritos', 'postre', 'img_productos/platanos_fritos.jpg', 60, 'platano macho, queso, crema, aceite', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Helado', 'postre', 'img_productos/helado.jpg', 80, 'leche, huevos, azucar, crema', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Flan', 'postre', 'img_productos/flan.jpg', 70, 'leche, huevos, azucar, vainilla', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Pay de queso', 'postre', 'img_productos/pay_queso.jpg', 70, '', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Pay de limón', 'postre', 'img_productos/pay_limon.jpg', 70, '', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Pastel de chocolate', 'postre', 'img_productos/pastel_chocolate.jpg', 90, '', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Pastel de zanahoria', 'postre', 'img_productos/pastel_zanahoria.jpg', 90, '', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Carlota','postre', 'img_productos/carlota.jpg', 90, 'galletas, leche, limon, crema', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Gelatina','postre', 'img_productos/gelatina.jpg', 70, 'saborizante, azucar', TRUE, TRUE);

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





############################################################################################
############################################################################################

-- GESTION DE EMPLEADOS
-- Tabla empleado
CREATE TABLE empleado (
    id_empleado INT PRIMARY KEY AUTO_INCREMENT,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,  -- Debido al hashcode
    rol ENUM('Gerente', 'Recepcionista', 'Cajero', 'Chef', 'Mesero') NOT NULL,
    estado BOOLEAN DEFAULT TRUE
);

DELIMITER //
-- Agregar un empleado a la BD
CREATE PROCEDURE agregar_empleado(
    IN p_usuario VARCHAR(50),
    IN p_password VARCHAR(255),
    IN p_rol VARCHAR(13)
)
BEGIN

	IF (SELECT COUNT(*) FROM empleado WHERE LOWER(usuario) = LOWER(p_usuario) AND 
		estado = TRUE) > 0 THEN
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'El usuario ya existe';
	END IF;
    
    -- Insertamos el empleado. 
    -- 'estado' se pone en TRUE por defecto.
    INSERT INTO empleado (
        usuario, 
        password, 
        rol, 
        estado
    ) 
    VALUES (
        p_usuario, 
        p_password, 
        p_rol, 
        TRUE
    );
END //

DELIMITER ;

-- Vista para obtener todos los empleados activos
CREATE VIEW vista_empleados_activos AS
SELECT 
    id_empleado, 
    usuario, 
    password, 
    rol
FROM empleado
WHERE estado = TRUE; 


##############################################################################
##############################################################################

-- ASISTENCIA  
  
-- Tabla sistencia
CREATE TABLE asistencia(
	id_empleado INT NOT NULL,
    fecha DATE NOT NULL,
    asistio BOOLEAN NOT NULL DEFAULT FALSE,
    bloqueado BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE (id_empleado, fecha),
	foreign key (id_empleado) REFERENCES empleado(id_empleado)
);

-- Permite guardar la asistencia de un empleado
DELIMITER //
CREATE PROCEDURE guardarAsistencia(
    IN p_id_empleado INT,
    IN p_fecha DATE,
    IN p_asistio BOOLEAN
)
BEGIN
    INSERT INTO asistencia (id_empleado, fecha, asistio, bloqueado)
    VALUES (p_id_empleado, p_fecha, p_asistio, TRUE)
    ON DUPLICATE KEY UPDATE asistio = p_asistio,
    bloqueado = TRUE;
END //
DELIMITER ;

-- Genera una asistencia por defecto a todos los empleados no registrados en asistencia pero si en la tabla de empleados
DELIMITER //
CREATE PROCEDURE generarAsistenciaDefecto(
    IN p_fecha DATE
)
BEGIN
    INSERT INTO asistencia (id_empleado, fecha, asistio)
    SELECT e.id_empleado, p_fecha, FALSE
    FROM empleado e
    WHERE e.estado = TRUE
    -- Solo inserta si el empleado NO tiene registro para esa fecha
    AND NOT EXISTS (
        SELECT 1 
        FROM asistencia a 
        WHERE a.id_empleado = e.id_empleado 
        AND a.fecha = p_fecha
    );
END //
DELIMITER ;


-- Elimina las asistencias relacionadas a un empleado cuando este se elimina
DELIMITER //
CREATE TRIGGER eliminar_asistencias_empleado
AFTER UPDATE ON empleado
FOR EACH ROW
BEGIN
    IF OLD.estado = TRUE AND NEW.estado = FALSE THEN
        DELETE FROM asistencia a
        WHERE a.id_empleado = NEW.id_empleado;
    END IF;
END //
DELIMITER ;

-- Obtiene todas las asistencias almacenadas
DELIMITER //
CREATE PROCEDURE obtener_asistencias_mes(
    IN p_year INT,
    IN p_mes INT
)
BEGIN
    SELECT 
        a.id_empleado,
        e.usuario,
        DAY(a.fecha) AS dia,
        a.asistio
    FROM asistencia a
    JOIN empleado e ON a.id_empleado = e.id_empleado
    WHERE YEAR(a.fecha) = p_year
      AND MONTH(a.fecha) = p_mes
    ORDER BY e.id_empleado, dia;
END //
DELIMITER ;


-- Permite insertar asistencia por defecto a partir de las 10 pm
DELIMITER //
CREATE EVENT verificar_asistencia_diaria
ON SCHEDULE EVERY 1 DAY
STARTS TIMESTAMP(CURRENT_DATE, '22:00:00')
DO
BEGIN
    -- Si NADIE registró hoy
    IF NOT EXISTS (
        SELECT 1 FROM asistencia WHERE fecha = CURDATE()
    ) THEN

        INSERT INTO asistencia (id_empleado, fecha, asistio, bloqueado)
        SELECT e.id_empleado, CURDATE(), FALSE, TRUE
        FROM empleado e
        WHERE e.estado = TRUE
        ON DUPLICATE KEY UPDATE 
            asistio = VALUES(asistio),
            bloqueado = TRUE;
    END IF;
END //
DELIMITER ;
