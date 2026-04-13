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
