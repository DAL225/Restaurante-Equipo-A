Para la gestion de los datos se uso MySQL Server en este proyecto.
Por lo cual se sugieren los siguientes scripts.

Script para root BD:

-- 0. Eliminar la BD si existe(usado cuando ya existe para limpiarla)
DROP DATABASE IF EXISTS restaurante;

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

-- 7. Permite crear eventos
SET GLOBAL event_scheduler = ON;

Script para usuario adminRes BD:
Al terminar este, esta el script para correr de una sola vez:

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
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Sopa Azteca', 'entrada', '/images/menu/entradas/sopa_azteca.jpg', 50, 'tomate, tiras de tortilla, chile, pollo, queso, crema', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Sopa de fideos', 'entrada', '/images/menu/entradas/sopa_fideos.jpg', 50, 'fideos, tomate,', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Chileatole', 'entrada', '/images/menu/entradas/chileatole.jpg', 60, 'tomate, chile verde, carne de res, maiz', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Crema de calabaza', 'entrada', '/images/menu/entradas/crema_calabaza.jpg', 60, 'calabaza verde, cebolla, ajo, nata', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Crema de zanahoria', 'entrada', '/images/menu/entradas/crema_zanahoria.jpg', 60, 'zanahoria, cebolla, ajo, nata', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Consome de verduras', 'entrada', '/images/menu/entradas/consome.jpg', 60, 'zanahoria, cebolla, calabaza, arroz, pollo', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Panza de res', 'entrada', '/images/menu/entradas/panza_res.jpg', 60, 'carne de rez, cebolla, ajo, laurel, chile guajillo', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Totopos', 'entrada', '/images/menu/entradas/totopos.jpg', 30, 'totopos, frijoles, queso', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Nachos', 'entrada', '/images/menu/entradas/nachos.jpg', 40, 'nachos, queso amarillo, chile jalapeño', TRUE, TRUE);

-- Productos platillos
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('pizza de pepperoni', 'platillo', '/images/menu/platillos/pizza.jpg', 99, 'Salsa de tomate, peperoni, queso, especias', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('hamburguesa', 'platillo', '/images/menu/platillos/hamburguesa.jpg', 79, 'Lechuga, tomate, carne de res, aderezos', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Enchiladas suizas', 'platillo', '/images/menu/platillos/enchiladas_suizas.jpg', 90, 'tortilla, queso amarillo, tomate verde, chile serrano, aceite', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Chilaquiles', 'platillo', '/images/menu/platillos/chilaquiles.jpg', 90, 'totopos, queso fresco, jitomate, chile guajillo', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Milanesa de pollo', 'platillo', '/images/menu/platillos/milanesa_pollo.jpg', 110, 'pollo, pan molido, jitomate, lechuga, cebolla, aceite', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Ensalada césar', 'platillo', '/images/menu/platillos/ensalada_cesar.jpg', 100, 'jitomate, lechuga, cebolla, crotones, queso parmesano', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Huevos a la mexicana', 'platillo', '/images/menu/platillos/huevos_al_gusto.jpg', 90, 'huevos, jitomate, cebolla, aceite', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Enfrijoladas', 'platillo', '/images/menu/platillos/enfrijoladas.jpg', 100, 'frijoles, tortilla, cebolla, queso, crema, aceite', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Pozole', 'platillo', '/images/menu/platillos/pozole.jpg', 90, 'maiz, tomate, cebolla, rabano, lechuga, chile', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Tacos dorados', 'platillo', '/images/menu/platillos/tacos_dorados.jpg', 100, 'tortilla, lechuga, papa, queso, crema, aceite', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Empanadas', 'platillo', '/images/menu/platillos/empanadas.jpg', 100, 'masa, pollo, queso, crema, aceite', TRUE, TRUE);

-- Bebidas
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Agua de jamaica', 'bebida', '/images/menu/bebidas/agua_jamaica.jpg', 50, 'hoja de jamaica, azucar', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Agua de horchata', 'bebida', '/images/menu/bebidas/agua_horchata.jpg', 50, 'leche, avena, azucar', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Agua simple', 'bebida', '/images/menu/bebidas/agua_simple.jpg', 30, '', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Cocacola', 'bebida', '/images/menu/bebidas/cocacola.jpg', 40, '', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Manzanita', 'bebida', '/images/menu/bebidas/manzanita.jpg', 40, '', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('7up', 'bebida', '/images/menu/bebidas/7up.jpg', 40, '', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Cafe', 'bebida', '/images/menu/bebidas/cafe.jpg', 40, 'Grano de cafe, azucar, leche', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Limonada', 'bebida', '/images/menu/bebidas/limonada.jpg', 50, 'Limon, azucar', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Naranjada', 'bebida', '/images/menu/bebidas/naranjada.jpg', 50, 'Naranja, azucar', TRUE, TRUE);

-- Postres
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Platanos fritos', 'postre', '/images/menu/postres/platanos_fritos.jpg', 60, 'platano macho, queso, crema, aceite', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Helado', 'postre', '/images/menu/postres/helado.jpg', 80, 'leche, huevos, azucar, crema', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Flan', 'postre', '/images/menu/postres/flan.jpg', 70, 'leche, huevos, azucar, vainilla', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Pay de queso', 'postre', '/images/menu/postres/pay_queso.jpg', 70, '', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Pay de limón', 'postre', '/images/menu/postres/pay_limon.jpg', 70, '', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Pastel de chocolate', 'postre', '/images/menu/postres/pastel_chocolate.jpg', 90, '', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Pastel de zanahoria', 'postre', '/images/menu/postres/pastel_zanahoria.jpg', 90, '', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Carlota','postre', '/images/menu/postres/carlota.jpg', 90, 'galletas, leche, limon, crema', TRUE, TRUE);
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES ('Gelatina','postre', '/images/menu/postres/gelatina.jpg', 70, 'saborizante, azucar', TRUE, TRUE);


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

-- Procedimiento para modificar los datos de un productoMenu
DELIMITER //
CREATE PROCEDURE modificar_productoMenu(
    IN p_id INT,
    IN p_nombre VARCHAR(100),
    IN p_categoria VARCHAR(50),
    IN p_imagenRuta VARCHAR(255),
    IN p_precio DECIMAL(10,2),
    IN p_ingredientes VARCHAR(500),
    IN p_disponibilidad BOOLEAN
)
BEGIN

    -- Verificar si ya existe OTRO producto con el mismo nombre
    IF (
        SELECT COUNT(*)
        FROM productoMenu
        WHERE LOWER(nombre) = LOWER(p_nombre)
          AND estado = TRUE
          AND id_productoMenu != p_id
    ) > 0 THEN

        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El nombre de este producto ya se encuentra registrado en el menú';

    END IF;

    -- Actualizar producto del menú
    UPDATE productoMenu
    SET nombre = p_nombre,
        categoria = p_categoria,
        imagenRuta = p_imagenRuta,
        precio = p_precio,
        ingredientes = p_ingredientes,
        disponibilidad = p_disponibilidad
    WHERE id_productoMenu = p_id;

END //
DELIMITER ;



	



-- Seccion de los pedidos

DROP TABLE pedidosTab;
-- Tabla de pedidos
-- 2. Crea la tabla con la propiedad CASCADE en la actualización:
CREATE TABLE pedidosTab (
    idPedido INT PRIMARY KEY AUTO_INCREMENT NOT NULL, 
    producto VARCHAR(100) NOT NULL, 
    cantidad INT NOT NULL, 
    subtotal DECIMAL(10,2) NOT NULL, 
    estado BOOLEAN NOT NULL DEFAULT TRUE, 
    preparado BOOLEAN NOT NULL DEFAULT FALSE, 
    mesa INT NOT NULL,
    
    -- Llave foránea modificada:
    FOREIGN KEY (producto) REFERENCES productoMenu(nombre) 
        ON UPDATE CASCADE 
        ON DELETE RESTRICT
);



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
    WHERE mesa = p_mesa and estado = TRUE;
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

DROP PROCEDURE eliminarPedido;
DELIMITER //
CREATE PROCEDURE eliminarPedido(p_id int)
BEGIN
	DELETE FROM pedidosTab
    WHERE idPedido = p_id;
END //
DELIMITER ;

DROP PROCEDURE modificarPedido;
DELIMITER //
CREATE PROCEDURE modificarPedido(p_id int, p_producto varchar(20), p_cantidad int)
BEGIN
DECLARE v_estado BOOLEAN;
DECLARE v_preparado BOOLEAN;
DECLARE v_precioU DECIMAL(10,2);
	-- Obtenemos el estado actual del pedido
	SELECT estado INTO v_estado
    FROM pedidosTab
    WHERE idPedido= p_id;
    
    -- Obtenemos el estado_terminado actual del pedido
    SELECT preparado INTO v_preparado
    FROM pedidosTab
    WHERE idPedido= p_id;
    
   -- Si el id no existe se interrumpe el proceso
    IF v_estado IS NULL THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: El ID de pedido no existe'; 
    
	-- Verificamos si el pedido no esta cancelado
    ELSEIF v_estado = 0  THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: El pedido ya esta cancelado';
	-- Verificamos si el pedido esta terminado
	ELSEIF v_preparado = 1  THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: Un pedido terminado no puede modificarse';
	ELSE
		SELECT precio INTO v_precioU
        FROM productoMenu
        WHERE nombre = p_producto;
        
		UPDATE pedidosTab 
		SET producto = p_producto , cantidad = p_cantidad, subtotal = v_precioU * p_cantidad
		WHERE idPedido = p_id;
	 END IF;
END //
DELIMITER ;


-- Tabla ventas
CREATE TABLE ventas (
    id_venta INT PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(1000) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL, 
    iva DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    tipo_pago VARCHAR(100) NOT NULL 
);

DROP PROCEDURE agregarVenta;
DELIMITER // 
CREATE PROCEDURE agregarVenta( IN p_descripcion VARCHAR(1000), IN p_subtotal DECIMAL(10,2), IN p_tipo_pago VARCHAR(100)) BEGIN
Declare v_iva DECIMAL(10,2);
Declare v_total DECIMAL(10,2);
Declare v_ultimo_id INT;
-- Calculamos el IVA
SET v_iva = p_subtotal * 0.16;
-- Calculmaos el total
SET v_total = p_subtotal + v_iva;
-- Insertamos la venta. 
INSERT INTO ventas (
	descripcion,
    subtotal,
    iva,
    total,
    tipo_pago
) 
VALUES (
    p_descripcion, 
    p_subtotal,
    v_iva,
    v_total,
    p_tipo_pago
);    

-- Recuperamos el id del ultimo elemento insertado
SET v_ultimo_id = LAST_INSERT_ID();

SELECT *
FROM ventas
WHERE id_venta = v_ultimo_id;
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
-- pa eliminar y modificar empleados

DELIMITER //
-- Expliquenme por qué Eliminar empleado no había sido creado antes
CREATE PROCEDURE eliminar_empleado(
	IN id INT
)
BEGIN
	delete from empleado where id_empleado = id;
END //
DELIMITER ;

DELIMITER //
-- Para modificar específicamente el usuario del empleado
CREATE PROCEDURE cambiar_usuario_empleado(
	IN id INT,
    IN n_usuario VARCHAR(50)
)
BEGIN
	update empleado set usuario = n_usuario where id_empleado = id;
END //
DELIMITER ;

DELIMITER //
-- Para modificar específicamente la contraseña del empleado
CREATE PROCEDURE cambiar_password_empleado(
	IN id INT,
    IN n_password VARCHAR(255)
)
BEGIN
	update empleado set password = n_password where id_empleado = id;
END //
DELIMITER ;

DELIMITER //
-- Para modificar específicamente el rol del empleado
CREATE PROCEDURE cambiar_rol_empleado(
	IN id INT,
    IN n_rol VARCHAR(13)
)
BEGIN
	update empleado set rol = n_rol where id_empleado = id;
END //
DELIMITER ;

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
STARTS (CURRENT_DATE + INTERVAL 22 HOUR) -- 10:00 PM
ON COMPLETION PRESERVE
ENABLE
DO
BEGIN
    -- SEGURIDAD: Desactivar modo seguro para permitir UPDATE masivo por fecha
    SET @old_safe_updates = @@sql_safe_updates;
    SET sql_safe_updates = 0;

    -- PASO 1: Insertar a los empleados activos que NO tienen registro hoy.
    -- Los insertamos directamente como NO ASISTIÓ y BLOQUEADO.
    INSERT INTO asistencia (id_empleado, fecha, asistio, bloqueado)
    SELECT e.id_empleado, CURDATE(), FALSE, TRUE
    FROM empleado e
    LEFT JOIN asistencia a ON e.id_empleado = a.id_empleado AND a.fecha = CURDATE()
    WHERE e.estado = TRUE AND a.id_empleado IS NULL
    ON DUPLICATE KEY UPDATE bloqueado = TRUE;

    -- PASO 2: Bloquear a los que ya existían (los que marcaron temprano).
    -- Usamos un rango de fecha por si hay un ligero desfase de segundos en el servidor.
    UPDATE asistencia 
    SET bloqueado = TRUE 
    WHERE fecha = CURDATE();

    -- Restaurar configuración de seguridad original
    SET sql_safe_updates = @old_safe_updates;
END //
DELIMITER ;


####################################################################################
####################################################################################


-- Tabla productoAlmacen
CREATE TABLE productoAlmacen (
    id_productoAlmacen INT PRIMARY KEY AUTO_INCREMENT,
    marca VARCHAR(50) NOT NULL,
    tipo VARCHAR(30) NOT NULL, 
    stock INT NOT NULL,
    proveedor VARCHAR(50) NOT NULL, 
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    UNIQUE(marca, proveedor) 
);

DELIMITER //
-- Agregar un productoAlmacen a la BD
CREATE PROCEDURE agregar_productoAlmacen(
    IN p_marca VARCHAR(50),
    IN p_tipo VARCHAR(30),
    IN p_stock INT,
    IN p_proveedor VARCHAR(50)
)
BEGIN

	IF (SELECT COUNT(*) FROM productoAlmacen WHERE LOWER(marca) = LOWER(p_marca) AND 
		LOWER(proveedor) = LOWER(p_proveedor) AND
		estado = TRUE) > 0 THEN
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'La marca ya esta registrada con el mismo proveedor';
	END IF;
    
    IF p_stock < 0 THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'El stock no puede ser negativo';
	END IF;
    
    -- Insertamos el producto. 
    -- 'estado' se ponen en TRUE por defecto.
    INSERT INTO productoAlmacen (
        marca, 
        tipo, 
        stock, 
        proveedor,
        estado
    ) 
    VALUES (
        p_marca, 
        p_tipo, 
        p_stock, 
        p_proveedor, 
        TRUE
    );
END //

DELIMITER ;

-- Vista para obtener todos los productoAlmacen activos
CREATE VIEW vista_almacen_activos AS
SELECT 
    id_productoAlmacen, 
    marca, 
    tipo, 
    stock, 
    proveedor
FROM productoAlmacen
WHERE estado = TRUE; 

-- Retira cierta cantidad del stock de un producto
DELIMITER //
CREATE PROCEDURE retirarStockAlmacen(
	IN p_id_productoAlmacen INT,
    IN p_cantidad INT
    )
    
BEGIN

	DECLARE cantidad_actual INT;
    
	IF (SELECT COUNT(*) FROM productoAlmacen pA WHERE pA.id_productoAlmacen = p_id_productoAlmacen AND
		pA.estado = TRUE) = 0 THEN
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'El producto no existe';
	END IF;
    
    -- Validar cantidad
    IF p_cantidad <= 0 THEN

        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La cantidad debe ser mayor a 0';

    END IF;
    
	SELECT pA.stock INTO cantidad_actual 
    FROM productoAlmacen pA 
    WHERE pA.id_productoAlmacen = p_id_productoAlmacen;
    
    IF (p_cantidad > cantidad_actual) THEN
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'Stock no disponible';
	END IF;
    
    UPDATE productoAlmacen pA
    SET pA.stock = pA.stock - p_cantidad
    WHERE pA.id_productoAlmacen = p_id_productoAlmacen;

END //
DELIMITER ;


-- Procedimiento para modificar los datos de un productoAlmacen
DELIMITER //
CREATE PROCEDURE modificar_productoAlmacen(
    IN p_id INT,
    IN p_marca VARCHAR(100),
    IN p_tipo VARCHAR(100),
    IN p_stock INT,
    IN p_proveedor VARCHAR(100)
)
BEGIN

    -- Verificar si ya existe OTRO producto
    IF (
        SELECT COUNT(*)
        FROM productoAlmacen
        WHERE LOWER(marca) = LOWER(p_marca)
          AND LOWER(proveedor) = LOWER(p_proveedor)
          AND estado = TRUE
          AND id_productoAlmacen != p_id
    ) > 0 THEN

        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La marca ya esta registrada con el mismo proveedor';

    END IF;

    -- Actualizar producto
    UPDATE productoAlmacen
    SET marca = p_marca,
        tipo = p_tipo,
        stock = p_stock,
        proveedor = p_proveedor
    WHERE id_productoAlmacen = p_id;

END //
DELIMITER ;


######################################################
-- Creacion tabla guardara registros de movimientos en el stock de almacen
CREATE TABLE registroReporteAlmacen(
	fecha DATE NOT NULL,
    tipo ENUM ('ingreso', 'retiro', 'eliminacion') NOT NULL,
    idProducto INT NOT NULL,
    cantidad INT NOT NULL
);

-- Trigger que insertara despues de cada nuevo producto almacen
DELIMITER //
CREATE TRIGGER despues_productoAlmacen_nuevaInsercion
AFTER INSERT ON productoAlmacen
FOR EACH ROW
BEGIN
    INSERT INTO registroReporteAlmacen (fecha, tipo, idProducto, cantidad)
    VALUES (
        CURDATE(),            -- Captura la fecha actual del servidor (AAAA-MM-DD)
        'ingreso',            -- Tipo de movimiento para inserciones nuevas
        NEW.id_productoAlmacen, -- El ID que MySQL acaba de generar automáticamente
        NEW.stock             -- El stock inicial con el que se registra el producto
    );
END //
DELIMITER ;


-- Trigger que insertara despues de cada actualizacion en stock de producto almacen y eliminacion
DELIMITER //
CREATE TRIGGER despues_productoAlmacen_actualizacion
AFTER UPDATE ON productoAlmacen
FOR EACH ROW
BEGIN
	-- 1. Se ejecuta SOLO en el momento exacto de la desactivación (De TRUE a FALSE)
    IF (OLD.estado = TRUE AND NEW.estado = FALSE) THEN
		INSERT INTO registroReporteAlmacen (fecha, tipo, idProducto, cantidad)
		VALUES (
			CURDATE(),            -- Captura la fecha actual del servidor (AAAA-MM-DD)
			'eliminacion',            -- Tipo de movimiento para eliminacion logica del producto
			NEW.id_productoAlmacen, -- El ID del producto
			OLD.stock    -- Guardamos el stock que tenía justo antes de ser eliminado
		);
        
	-- 2. Para ingreso de Stock (Solo si el producto sigue activo o no se está desactivando)
	ELSEIF(OLD.stock < NEW.stock) THEN
		INSERT INTO registroReporteAlmacen (fecha, tipo, idProducto, cantidad)
		VALUES (
			CURDATE(),            -- Captura la fecha actual del servidor (AAAA-MM-DD)
			'ingreso',            -- Tipo de movimiento para ingresos de stock
			NEW.id_productoAlmacen, -- El ID del producto
			NEW.stock - OLD.stock    -- El stock se convierte a 0
		);

	-- 3. Para retiro de Stock (Solo si el producto sigue activo o no se está desactivando)
	ELSEIF(NEW.stock < OLD.stock) THEN
		INSERT INTO registroReporteAlmacen (fecha, tipo, idProducto, cantidad)
		VALUES (
			CURDATE(),            -- Captura la fecha actual del servidor (AAAA-MM-DD)
			'retiro',            -- Tipo de movimiento para retiros de stock
			NEW.id_productoAlmacen, -- El ID del producto
			OLD.stock - NEW.stock    -- El stock que se retiro, el antiguo - el actual
		);
    END IF;
END//
DELIMITER ;

-- Procedimiento que obtiene los registros segun el mes solicitado
DELIMITER //
CREATE PROCEDURE obtenerRegistrosReporteAlmacen(
    IN p_fecha DATE
)
BEGIN
    -- Declaramos variables para delimitar el mes solicitado
    DECLARE v_inicio_mes DATE;
    DECLARE v_fin_mes DATE;
    
    -- Calculamos el primer día de ese mes (ej. '2026-05-01')
    -- Funcion que resta a la fecha solicitada, el dia de la fecha -1
    -- ej. 2026/05/23 - (23 - 1= 22), esto para obtener el primer dia del mes
    SET v_inicio_mes = DATE_SUB(p_fecha, INTERVAL DAYOFMONTH(p_fecha) - 1 DAY);
    
    -- Calculamos el último día de ese mes (ej. '2026-05-31')
    SET v_fin_mes = LAST_DAY(p_fecha);

    -- Ejecutamos la consulta usando un rango directo y un JOIN moderno
	SELECT 
		r.fecha, 
		r.tipo, 
		r.idProducto, 
		r.cantidad, 
		p.marca
	FROM registroReporteAlmacen r
	INNER JOIN productoAlmacen p ON r.idProducto = p.id_productoAlmacen
	WHERE r.fecha >= v_inicio_mes 
	  AND r.fecha <= v_fin_mes;
      
    
END//
DELIMITER ;


#################################################################################################################################
#################################################################################################################################
#################################################################################################################################
#################################################################################################################################
#################################################################################################################################

USE restaurante;

-- ==============================================================================
-- 1. LIMPIEZA DE OBJETOS (De hijos a padres para evitar conflictos de Foreign Keys)
-- ==============================================================================

-- Triggers y Eventos
DROP TRIGGER IF EXISTS eliminar_asistencias_empleado;
DROP TRIGGER IF EXISTS despues_productoAlmacen_nuevaInsercion;
DROP TRIGGER IF EXISTS despues_productoAlmacen_actualizacion;
DROP EVENT IF EXISTS verificar_asistencia_diaria;

-- Vistas
DROP VIEW IF EXISTS vista_menu_activos;
DROP VIEW IF EXISTS vista_empleados_activos;
DROP VIEW IF EXISTS vista_almacen_activos;

-- Procedimientos Almacenados
DROP PROCEDURE IF EXISTS agregar_productoMenu;
DROP PROCEDURE IF EXISTS modificar_productoMenu;
DROP PROCEDURE IF EXISTS agregarPedido;
DROP PROCEDURE IF EXISTS pedidosMesa;
DROP PROCEDURE IF EXISTS cancelarPedido;
DROP PROCEDURE IF EXISTS modificarPedido;
DROP PROCEDURE IF EXISTS agregarVenta;
DROP PROCEDURE IF EXISTS agregarMesa;
DROP PROCEDURE IF EXISTS eliminarMesa;
DROP PROCEDURE IF EXISTS reservarMesa;
DROP PROCEDURE IF EXISTS eliminarReservacion;
DROP PROCEDURE IF EXISTS agregar_empleado;
DROP PROCEDURE IF EXISTS eliminar_empleado;
DROP PROCEDURE IF EXISTS cambiar_usuario_empleado;
DROP PROCEDURE IF EXISTS cambiar_password_empleado;
DROP PROCEDURE IF EXISTS cambiar_rol_empleado;
DROP PROCEDURE IF EXISTS guardarAsistencia;
DROP PROCEDURE IF EXISTS generarAsistenciaDefecto;
DROP PROCEDURE IF EXISTS obtener_asistencias_mes;
DROP PROCEDURE IF EXISTS agregar_productoAlmacen;
DROP PROCEDURE IF EXISTS retirarStockAlmacen;
DROP PROCEDURE IF EXISTS modificar_productoAlmacen;
DROP PROCEDURE IF EXISTS obtenerRegistrosReporteAlmacen;

-- Tablas (Hijas primero, luego Padres)
DROP TABLE IF EXISTS registroReporteAlmacen;
DROP TABLE IF EXISTS productoAlmacen;
DROP TABLE IF EXISTS asistencia;
DROP TABLE IF EXISTS empleado;
DROP TABLE IF EXISTS reservaciones;
DROP TABLE IF EXISTS mesas;
DROP TABLE IF EXISTS ventas;
DROP TABLE IF EXISTS pedidosTab;
DROP TABLE IF EXISTS productoMenu;


-- ==============================================================================
-- 2. CREACIÓN DE TABLAS
-- ==============================================================================

CREATE TABLE productoMenu (
    id_productoMenu INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    categoria VARCHAR(50) NOT NULL, 
    imagenRuta VARCHAR(255) NULL,       
    precio DECIMAL(10,2) NOT NULL,
    ingredientes VARCHAR(500) NOT NULL, 
    disponibilidad BOOLEAN NOT NULL DEFAULT TRUE, 
    estado BOOLEAN NOT NULL DEFAULT TRUE          
);

CREATE TABLE pedidosTab (
    idPedido INT PRIMARY KEY AUTO_INCREMENT NOT NULL, 
    producto VARCHAR(100) NOT NULL, 
    cantidad INT NOT NULL, 
    subtotal DECIMAL(10,2) NOT NULL, 
    estado BOOLEAN NOT NULL DEFAULT TRUE, 
    preparado BOOLEAN NOT NULL DEFAULT FALSE, 
    mesa INT NOT NULL,
    FOREIGN KEY (producto) REFERENCES productoMenu(nombre) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE ventas (
    id_venta INT PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(1000) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL, 
    iva DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    tipo_pago VARCHAR(100) NOT NULL 
);

CREATE TABLE mesas (
	idMesa INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    estadoMesa BOOLEAN NOT NULL DEFAULT FALSE,
    cantidadPersonasPosibles INT NOT NULL
);

CREATE TABLE reservaciones (
	idReservacion INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    mesa INT NOT NULL,
    personas INT NOT NULL,
    fecha DATE,
    hora TIME,
    nombre VARCHAR(100),
    FOREIGN KEY (mesa) REFERENCES mesas(idMesa)
);

CREATE TABLE empleado (
    id_empleado INT PRIMARY KEY AUTO_INCREMENT,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,  
    rol ENUM('Gerente', 'Recepcionista', 'Cajero', 'Chef', 'Mesero') NOT NULL,
    estado BOOLEAN DEFAULT TRUE
);

CREATE TABLE asistencia(
	id_empleado INT NOT NULL,
    fecha DATE NOT NULL,
    asistio BOOLEAN NOT NULL DEFAULT FALSE,
    bloqueado BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE (id_empleado, fecha),
	FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado)
);

CREATE TABLE productoAlmacen (
    id_productoAlmacen INT PRIMARY KEY AUTO_INCREMENT,
    marca VARCHAR(50) NOT NULL,
    tipo VARCHAR(30) NOT NULL, 
    stock INT NOT NULL,
    proveedor VARCHAR(50) NOT NULL, 
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    UNIQUE(marca, proveedor) 
);

CREATE TABLE registroReporteAlmacen(
	fecha DATE NOT NULL,
    tipo ENUM ('ingreso', 'retiro', 'eliminacion') NOT NULL,
    idProducto INT NOT NULL,
    cantidad INT NOT NULL
);


-- ==============================================================================
-- 3. VISTAS
-- ==============================================================================

CREATE VIEW vista_menu_activos AS
SELECT id_productoMenu, nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad
FROM productoMenu WHERE estado = TRUE;

CREATE VIEW vista_empleados_activos AS
SELECT id_empleado, usuario, password, rol
FROM empleado WHERE estado = TRUE;

CREATE VIEW vista_almacen_activos AS
SELECT id_productoAlmacen, marca, tipo, stock, proveedor
FROM productoAlmacen WHERE estado = TRUE;


-- ==============================================================================
-- 4. PROCEDIMIENTOS ALMACENADOS
-- ==============================================================================

DELIMITER //

CREATE PROCEDURE agregar_productoMenu(
    IN p_nombre VARCHAR(100),
    IN p_categoria VARCHAR(50),
    IN p_imagen VARCHAR(255),
    IN p_precio DECIMAL(10,2),
    IN p_ingredientes VARCHAR(500)
)
BEGIN
	IF (SELECT COUNT(*) FROM productoMenu WHERE LOWER(nombre) = LOWER(p_nombre) AND estado = TRUE) > 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El nombre del producto ya existe';
	END IF;
    
    INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) 
    VALUES (p_nombre, p_categoria, p_imagen, p_precio, p_ingredientes, TRUE, TRUE);
END //

CREATE PROCEDURE modificar_productoMenu(
    IN p_id INT,
    IN p_nombre VARCHAR(100),
    IN p_categoria VARCHAR(50),
    IN p_imagenRuta VARCHAR(255),
    IN p_precio DECIMAL(10,2),
    IN p_ingredientes VARCHAR(500),
    IN p_disponibilidad BOOLEAN
)
BEGIN
    IF (SELECT COUNT(*) FROM productoMenu WHERE LOWER(nombre) = LOWER(p_nombre) AND estado = TRUE AND id_productoMenu != p_id) > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El nombre de este producto ya se encuentra registrado en el menú';
    END IF;

    UPDATE productoMenu
    SET nombre = p_nombre, categoria = p_categoria, imagenRuta = p_imagenRuta, precio = p_precio, ingredientes = p_ingredientes, disponibilidad = p_disponibilidad
    WHERE id_productoMenu = p_id;
END //

CREATE PROCEDURE agregarPedido(IN p_producto VARCHAR(100), IN p_cantidad INT, p_mesa INT) 
BEGIN
    DECLARE p_subtotal DECIMAL(10,2);
    DECLARE precioU DECIMAL(10,2);

    SELECT precio INTO precioU FROM productoMenu WHERE nombre = p_producto LIMIT 1;
    SET p_subtotal = precioU * p_cantidad;

    INSERT INTO pedidosTab (producto, cantidad, subtotal, estado, preparado, mesa) 
    VALUES (p_producto, p_cantidad, p_subtotal, TRUE, FALSE, p_mesa);
END //

CREATE PROCEDURE pedidosMesa (p_mesa INT) 
BEGIN
	SELECT idPedido, producto, cantidad, subtotal, estado, preparado, mesa
    FROM pedidosTab WHERE mesa = p_mesa;
END //

CREATE PROCEDURE cancelarPedido (p_id INT)
BEGIN
    DECLARE v_estado BOOLEAN;
	SELECT estado INTO v_estado FROM pedidosTab WHERE p_id = idPedido;
    
    IF v_estado IS FALSE THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: El pedido ya esta cancelado';
    ELSE
		UPDATE pedidosTab SET estado = FALSE WHERE idPedido = p_id;
    END IF;
END //

CREATE PROCEDURE modificarPedido(p_id INT, p_producto VARCHAR(20), p_cantidad INT)
BEGIN
    DECLARE v_estado BOOLEAN;
	SELECT estado INTO v_estado FROM pedidosTab WHERE idPedido = p_id;
    
    IF v_estado IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: El ID de pedido no existe';
    ELSEIF v_estado = 0  THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: El pedido ya esta cancelado';
    ELSE
		UPDATE pedidosTab SET producto = p_producto , cantidad = p_cantidad WHERE idPedido = p_id;
	END IF;
END //

CREATE PROCEDURE agregarVenta(IN p_descripcion VARCHAR(1000), IN p_subtotal DECIMAL(10,2), IN p_tipo_pago VARCHAR(100)) 
BEGIN
    DECLARE v_iva DECIMAL(10,2);
    DECLARE v_total DECIMAL(10,2);
    
    SET v_iva = p_subtotal * 0.16;
    SET v_total = p_subtotal + v_iva;
    
    INSERT INTO ventas (descripcion, subtotal, iva, total, tipo_pago) 
    VALUES (p_descripcion, p_subtotal, v_iva, v_total, p_tipo_pago);
END //

CREATE PROCEDURE agregarMesa (cantidad INT)
BEGIN
	INSERT INTO mesas (cantidadPersonasPosibles) VALUES (cantidad);
END //

CREATE PROCEDURE eliminarMesa (id INT)
BEGIN
	DELETE FROM mesas WHERE idMesa = id;
END //

CREATE PROCEDURE reservarMesa (cantPersonas INT, fecha DATE, hora TIME, aNombre VARCHAR(100))
BEGIN
	DECLARE mesa INT;
	SELECT M.idMesa INTO mesa FROM mesas M
	WHERE M.cantidadPersonasPosibles >= cantPersonas
	AND NOT EXISTS (SELECT R.mesa FROM reservaciones R WHERE R.fecha = fecha AND TIMEDIFF(hora, R.hora) < '03:00:00' AND M.idMesa = R.mesa)
	LIMIT 1;
    
	IF (mesa IS NOT NULL) THEN
		INSERT INTO reservaciones (mesa, personas, fecha, hora, nombre)
		VALUES (mesa, cantPersonas, fecha, hora, aNombre);
	END IF;
END //

CREATE PROCEDURE eliminarReservacion (id INT)
BEGIN
	DELETE FROM reservaciones WHERE idReservacion = id;
END //

CREATE PROCEDURE agregar_empleado(IN p_usuario VARCHAR(50), IN p_password VARCHAR(255), IN p_rol VARCHAR(13))
BEGIN
	IF (SELECT COUNT(*) FROM empleado WHERE LOWER(usuario) = LOWER(p_usuario) AND estado = TRUE) > 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El usuario ya existe';
	END IF;
    
    INSERT INTO empleado (usuario, password, rol, estado) VALUES (p_usuario, p_password, p_rol, TRUE);
END //

CREATE PROCEDURE eliminar_empleado(IN id INT)
BEGIN
	DELETE FROM empleado WHERE id_empleado = id;
END //

CREATE PROCEDURE cambiar_usuario_empleado(IN id INT, IN n_usuario VARCHAR(50))
BEGIN
	UPDATE empleado SET usuario = n_usuario WHERE id_empleado = id;
END //

CREATE PROCEDURE cambiar_password_empleado(IN id INT, IN n_password VARCHAR(255))
BEGIN
	UPDATE empleado SET password = n_password WHERE id_empleado = id;
END //

CREATE PROCEDURE cambiar_rol_empleado(IN id INT, IN n_rol VARCHAR(13))
BEGIN
	UPDATE empleado SET rol = n_rol WHERE id_empleado = id;
END //

CREATE PROCEDURE guardarAsistencia(IN p_id_empleado INT, IN p_fecha DATE, IN p_asistio BOOLEAN)
BEGIN
    INSERT INTO asistencia (id_empleado, fecha, asistio, bloqueado)
    VALUES (p_id_empleado, p_fecha, p_asistio, TRUE)
    ON DUPLICATE KEY UPDATE asistio = p_asistio, bloqueado = TRUE;
END //

CREATE PROCEDURE generarAsistenciaDefecto(IN p_fecha DATE)
BEGIN
    INSERT INTO asistencia (id_empleado, fecha, asistio)
    SELECT e.id_empleado, p_fecha, FALSE
    FROM empleado e WHERE e.estado = TRUE
    AND NOT EXISTS (
        SELECT 1 FROM asistencia a WHERE a.id_empleado = e.id_empleado AND a.fecha = p_fecha
    );
END //

CREATE PROCEDURE obtener_asistencias_mes(IN p_year INT, IN p_mes INT)
BEGIN
    SELECT a.id_empleado, e.usuario, DAY(a.fecha) AS dia, a.asistio
    FROM asistencia a JOIN empleado e ON a.id_empleado = e.id_empleado
    WHERE YEAR(a.fecha) = p_year AND MONTH(a.fecha) = p_mes
    ORDER BY e.id_empleado, dia;
END //

CREATE PROCEDURE agregar_productoAlmacen(IN p_marca VARCHAR(50), IN p_tipo VARCHAR(30), IN p_stock INT, IN p_proveedor VARCHAR(50))
BEGIN
	IF (SELECT COUNT(*) FROM productoAlmacen WHERE LOWER(marca) = LOWER(p_marca) AND LOWER(proveedor) = LOWER(p_proveedor) AND estado = TRUE) > 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La marca ya esta registrada con el mismo proveedor';
	END IF;
    
    IF p_stock < 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El stock no puede ser negativo';
	END IF;
    
    INSERT INTO productoAlmacen (marca, tipo, stock, proveedor, estado) VALUES (p_marca, p_tipo, p_stock, p_proveedor, TRUE);
END //

CREATE PROCEDURE retirarStockAlmacen(IN p_id_productoAlmacen INT, IN p_cantidad INT)
BEGIN
	DECLARE cantidad_actual INT;
    IF (SELECT COUNT(*) FROM productoAlmacen pA WHERE pA.id_productoAlmacen = p_id_productoAlmacen AND pA.estado = TRUE) = 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El producto no existe';
	END IF;
    
    IF p_cantidad <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La cantidad debe ser mayor a 0';
	END IF;
    
	SELECT pA.stock INTO cantidad_actual FROM productoAlmacen pA WHERE pA.id_productoAlmacen = p_id_productoAlmacen;
    IF (p_cantidad > cantidad_actual) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Stock no disponible';
	END IF;
    
    UPDATE productoAlmacen pA SET pA.stock = pA.stock - p_cantidad WHERE pA.id_productoAlmacen = p_id_productoAlmacen;
END //

CREATE PROCEDURE modificar_productoAlmacen(IN p_id INT, IN p_marca VARCHAR(100), IN p_tipo VARCHAR(100), IN p_stock INT, IN p_proveedor VARCHAR(100))
BEGIN
    IF (SELECT COUNT(*) FROM productoAlmacen WHERE LOWER(marca) = LOWER(p_marca) AND LOWER(proveedor) = LOWER(p_proveedor) AND estado = TRUE AND id_productoAlmacen != p_id) > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La marca ya esta registrada con el mismo proveedor';
    END IF;

    UPDATE productoAlmacen
    SET marca = p_marca, tipo = p_tipo, stock = p_stock, proveedor = p_proveedor
    WHERE id_productoAlmacen = p_id;
END //

CREATE PROCEDURE obtenerRegistrosReporteAlmacen(IN p_fecha DATE)
BEGIN
    DECLARE v_inicio_mes DATE;
    DECLARE v_fin_mes DATE;
    
    SET v_inicio_mes = DATE_SUB(p_fecha, INTERVAL DAYOFMONTH(p_fecha) - 1 DAY);
    SET v_fin_mes = LAST_DAY(p_fecha);
    
	SELECT r.fecha, r.tipo, r.idProducto, r.cantidad, p.marca
	FROM registroReporteAlmacen r
	INNER JOIN productoAlmacen p ON r.idProducto = p.id_productoAlmacen
	WHERE r.fecha >= v_inicio_mes AND r.fecha <= v_fin_mes;
END //

DELIMITER ;


-- ==============================================================================
-- 5. TRIGGERS Y EVENTOS
-- ==============================================================================

DELIMITER //

CREATE TRIGGER eliminar_asistencias_empleado
AFTER UPDATE ON empleado
FOR EACH ROW
BEGIN
    IF OLD.estado = TRUE AND NEW.estado = FALSE THEN
        DELETE FROM asistencia WHERE id_empleado = NEW.id_empleado;
    END IF;
END //

CREATE TRIGGER despues_productoAlmacen_nuevaInsercion
AFTER INSERT ON productoAlmacen
FOR EACH ROW
BEGIN
    INSERT INTO registroReporteAlmacen (fecha, tipo, idProducto, cantidad)
    VALUES (CURDATE(), 'ingreso', NEW.id_productoAlmacen, NEW.stock);
END //

CREATE TRIGGER despues_productoAlmacen_actualizacion
AFTER UPDATE ON productoAlmacen
FOR EACH ROW
BEGIN
    IF (OLD.estado = TRUE AND NEW.estado = FALSE) THEN
		INSERT INTO registroReporteAlmacen (fecha, tipo, idProducto, cantidad)
		VALUES (CURDATE(), 'eliminacion', NEW.id_productoAlmacen, OLD.stock);
	ELSEIF(OLD.stock < NEW.stock) THEN
		INSERT INTO registroReporteAlmacen (fecha, tipo, idProducto, cantidad)
		VALUES (CURDATE(), 'ingreso', NEW.id_productoAlmacen, NEW.stock - OLD.stock);
	ELSEIF(NEW.stock < OLD.stock) THEN
		INSERT INTO registroReporteAlmacen (fecha, tipo, idProducto, cantidad)
		VALUES (CURDATE(), 'retiro', NEW.id_productoAlmacen, OLD.stock - NEW.stock);
	END IF;
END //

CREATE EVENT verificar_asistencia_diaria
ON SCHEDULE EVERY 1 DAY
STARTS (CURRENT_DATE + INTERVAL 22 HOUR)
ON COMPLETION PRESERVE
ENABLE
DO
BEGIN
    SET @old_safe_updates = @@sql_safe_updates;
    SET sql_safe_updates = 0;

    INSERT INTO asistencia (id_empleado, fecha, asistio, bloqueado)
    SELECT e.id_empleado, CURDATE(), FALSE, TRUE
    FROM empleado e
    LEFT JOIN asistencia a ON e.id_empleado = a.id_empleado AND a.fecha = CURDATE()
    WHERE e.estado = TRUE AND a.id_empleado IS NULL
    ON DUPLICATE KEY UPDATE bloqueado = TRUE;

    UPDATE asistencia SET bloqueado = TRUE WHERE fecha = CURDATE();
    
    SET sql_safe_updates = @old_safe_updates;
END //

DELIMITER ;


-- ==============================================================================
-- 6. INSERCIONES DE DATOS (DATA SEEDING)
-- ==============================================================================

-- Entradas
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES 
('Sopa Azteca', 'entrada', 'img_productos/sopa_azteca.jpg', 50, 'tomate, tiras de tortilla, chile, pollo, queso, crema', TRUE, TRUE),
('Sopa de fideos', 'entrada', 'img_productos/sopa_fideos.jpg', 50, 'fideos, tomate,', TRUE, TRUE),
('Chileatole', 'entrada', 'img_productos/chileatole.jpg', 60, 'tomate, chile verde, carne de res, maiz', TRUE, TRUE),
('Crema de calabaza', 'entrada', 'img_productos/crema_calabaza.jpg', 60, 'calabaza verde, cebolla, ajo, nata', TRUE, TRUE),
('Crema de zanahoria', 'entrada', 'img_productos/crema_zanahoria.jpg', 60, 'zanahoria, cebolla, ajo, nata', TRUE, TRUE),
('Consome de verduras', 'entrada', 'img_productos/consome.jpg', 60, 'zanahoria, cebolla, calabaza, arroz, pollo', TRUE, TRUE),
('Panza de res', 'entrada', 'img_productos/panza_res.jpg', 60, 'carne de rez, cebolla, ajo, laurel, chile guajillo', TRUE, TRUE),
('Totopos', 'entrada', 'img_productos/totopos.jpg', 30, 'totopos, frijoles, queso', TRUE, TRUE),
('Nachos', 'entrada', 'img_productos/nachos.jpg', 40, 'nachos, queso amarillo, chile jalapeño', TRUE, TRUE);

-- Platillos
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES 
('pizza de pepperoni', 'platillo', 'img_productos/pizza.jpg', 99, 'Salsa de tomate, peperoni, queso, especias', TRUE, TRUE),
('hamburguesa', 'platillo', 'img_productos/hamburguesa.jpg', 79, 'Lechuga, tomate, carne de res, aderezos', TRUE, TRUE),
('Enchiladas suizas', 'platillo', 'img_productos/enchiladas_suizas.jpg', 90, 'tortilla, queso amarillo, tomate verde, chile serrano, aceite', TRUE, TRUE),
('Chilaquiles', 'platillo', 'img_productos/chilaquiles.jpg', 90, 'totopos, queso fresco, jitomate, chile guajillo', TRUE, TRUE),
('Milanesa de pollo', 'platillo', 'img_productos/milanesa_pollo.jpg', 110, 'pollo, pan molido, jitomate, lechuga, cebolla, aceite', TRUE, TRUE),
('Ensalada césar', 'platillo', 'img_productos/ensalada_cesar.jpg', 100, 'jitomate, lechuga, cebolla, crotones, queso parmesano', TRUE, TRUE),
('Huevos a la mexicana', 'platillo', 'img_productos/huevos_mexicana.jpg', 90, 'huevos, jitomate, cebolla, aceite', TRUE, TRUE),
('Enfrijoladas', 'platillo', 'img_productos/enfrijoladas.jpg', 100, 'frijoles, tortilla, cebolla, queso, crema, aceite', TRUE, TRUE),
('Pozole', 'platillo', 'img_productos/pozole.jpg', 90, 'maiz, tomate, cebolla, rabano, lechuga, chile', TRUE, TRUE),
('Tacos dorados', 'platillo', 'img_productos/tacos_dorados.jpg', 100, 'tortilla, lechuga, papa, queso, crema, aceite', TRUE, TRUE),
('Empanadas', 'platillo', 'img_productos/empanadas.jpg', 100, 'masa, pollo, queso, crema, aceite', TRUE, TRUE);

-- Bebidas
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES 
('Agua de jamaica', 'bebida', 'img_productos/agua_jamaica.jpg', 50, 'hoja de jamaica, azucar', TRUE, TRUE),
('Agua de horchata', 'bebida', 'img_productos/agua_horchata.jpg', 50, 'leche, avena, azucar', TRUE, TRUE),
('Agua simple', 'bebida', 'img_productos/agua_simple.jpg', 30, '', TRUE, TRUE),
('Cocacola', 'bebida', 'img_productos/cocacola.jpg', 40, '', TRUE, TRUE),
('Manzanita', 'bebida', 'img_productos/manzanita.jpg', 40, '', TRUE, TRUE),
('7even', 'bebida', 'img_productos/7even.jpg', 40, '', TRUE, TRUE),
('Cafe', 'bebida', 'img_productos/cafe.jpg', 40, 'Grano de cafe, azucar, leche', TRUE, TRUE),
('Limonada', 'bebida', 'img_productos/limonada.jpg', 50, 'Limon, azucar', TRUE, TRUE),
('Naranjada', 'bebida', 'img_productos/naranjada.jpg', 50, 'Naranja, azucar', TRUE, TRUE);

-- Postres
INSERT INTO productoMenu (nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad, estado) VALUES 
('Platanos fritos', 'postre', 'img_productos/platanos_fritos.jpg', 60, 'platano macho, queso, crema, aceite', TRUE, TRUE),
('Helado', 'postre', 'img_productos/helado.jpg', 80, 'leche, huevos, azucar, crema', TRUE, TRUE),
('Flan', 'postre', 'img_productos/flan.jpg', 70, 'leche, huevos, azucar, vainilla', TRUE, TRUE),
('Pay de queso', 'postre', 'img_productos/pay_queso.jpg', 70, '', TRUE, TRUE),
('Pay de limón', 'postre', 'img_productos/pay_limon.jpg', 70, '', TRUE, TRUE),
('Pastel de chocolate', 'postre', 'img_productos/pastel_chocolate.jpg', 90, '', TRUE, TRUE),
('Pastel de zanahoria', 'postre', 'img_productos/pastel_zanahoria.jpg', 90, '', TRUE, TRUE),
('Carlota','postre', 'img_productos/carlota.jpg', 90, 'galletas, leche, limon, crema', TRUE, TRUE),
('Gelatina','postre', 'img_productos/gelatina.jpg', 70, 'saborizante, azucar', TRUE, TRUE);


-- ==============================================================================
-- 7. PRUEBAS DE LLAMADAS (Testing)
-- ==============================================================================

CALL agregarPedido('pizza de pepperoni', 2, 1);
CALL agregarPedido('hamburguesa', 4, 2);
CALL agregarPedido('pizza de pepperoni', 6, 3);
CALL agregarPedido('pizza de pepperoni', 2, 3);

CALL pedidosMesa(3);
CALL cancelarPedido(4);

CALL agregar_empleado('admin', '12345678', 'Gerente');
