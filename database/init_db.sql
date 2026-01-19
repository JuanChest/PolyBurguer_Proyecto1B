-- =============================================
-- Script de Inicialización de Base de Datos
-- PolyBurguer - Sistema de Pedidos
-- =============================================

-- =============================================
-- PARTE 1: CREAR BASE DE DATOS (EJECUTAR PRIMERO)
-- =============================================

DROP DATABASE IF EXISTS poliburguer;
CREATE DATABASE poliburguer CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE poliburguer;

-- =============================================
-- IMPORTANTE - LEE ESTO:
-- =============================================
-- Las tablas NO se crean aquí, las crea JPA automáticamente.
-- 
-- PROCESO CORRECTO:
-- 1. Ejecuta solo la PARTE 1 (arriba) para crear la BD vacía
-- 2. Inicia tu aplicación en Tomcat - JPA creará las tablas automáticamente
-- 3. Luego ejecuta la PARTE 2 (abajo) para insertar datos de prueba
--
-- VERIFICACIÓN: persistence.xml debe tener:
-- <property name="eclipselink.ddl-generation" value="create-tables" />
-- =============================================

-- =============================================
-- PARTE 2: INSERTAR DATOS DE PRUEBA
-- (EJECUTAR DESPUÉS DE QUE JPA CREE LAS TABLAS)
-- =============================================

USE poliburguer;

-- =============================================
-- 2.1 INSERTAR USUARIOS DE PRUEBA
-- =============================================

-- Usuario Administrador de Prueba
INSERT INTO usuarios (nombre, apellido, cedula, contrasenia, estado, tipo_usuario) 
VALUES ('Admin', 'Principal', '0987654321', 'admin123', 'ACTIVO', 'ADMINISTRADOR');

-- Usuario Cocinero de Prueba 1 (Activo)
INSERT INTO usuarios (nombre, apellido, cedula, contrasenia, estado, tipo_usuario) 
VALUES ('Juan', 'Pérez', '1234567890', 'cocinero123', 'ACTIVO', 'COCINERO');

-- Usuario Cocinero de Prueba 2 (Inactivo - para probar flujo alterno)
INSERT INTO usuarios (nombre, apellido, cedula, contrasenia, estado, tipo_usuario) 
VALUES ('María', 'González', '1122334455', 'maria123', 'INACTIVO', 'COCINERO');

-- =============================================
-- 2.2 INSERTAR PLATOS DE MENÚ (OPCIONAL)
-- =============================================

-- Hamburguesas
INSERT INTO Platos_menu (nombre, descripcion, precio, disponible, categoria, imagen) 
VALUES 
('PoliBurguer Clásica', 'Hamburguesa de carne de res, lechuga, tomate, cebolla y queso cheddar', 5.50, true, 'HAMBURGUESA', 'poliburguer_clasica.jpg'),
('PoliBurguer Especial', 'Doble carne, tocino, queso, huevo frito, lechuga y salsa BBQ', 7.99, true, 'HAMBURGUESA', 'poliburguer_especial.jpg'),
('PoliBurguer Vegetariana', 'Hamburguesa de lentejas con aguacate, lechuga y tomate', 6.50, true, 'HAMBURGUESA', 'poliburguer_vegetariana.jpg'),
('PoliBurguer Picante', 'Carne especiada, jalapeños, queso pepper jack y salsa picante', 6.99, true, 'HAMBURGUESA', 'poliburguer_picante.jpg');

-- Bebidas
INSERT INTO Platos_menu (nombre, descripcion, precio, disponible, categoria, imagen) 
VALUES 
('Coca Cola', 'Refresco de cola 500ml', 1.50, true, 'BEBIDA', 'coca_cola.jpg'),
('Sprite', 'Refresco de lima-limón 500ml', 1.50, true, 'BEBIDA', 'sprite.jpg'),
('Fanta', 'Refresco de naranja 500ml', 1.50, true, 'BEBIDA', 'fanta.jpg'),
('Agua Mineral', 'Agua mineral sin gas 500ml', 1.00, true, 'BEBIDA', 'agua.jpg'),
('Jugo de Naranja Natural', 'Jugo 100% natural recién exprimido 300ml', 2.50, true, 'BEBIDA', 'jugo_naranja.jpg');

-- Plato NO disponible (para probar validación de disponibilidad en UC04)
INSERT INTO Platos_menu (nombre, descripcion, precio, disponible, categoria, imagen) 
VALUES 
('PoliBurguer Triple', 'Triple carne, triple queso - AGOTADO', 9.99, false, 'HAMBURGUESA', 'poliburguer_triple.jpg');

-- =============================================
-- 2.3 VERIFICACIÓN DE DATOS INSERTADOS
-- =============================================

SELECT 'VERIFICACIÓN: Usuarios creados' AS '';
SELECT id, nombre, apellido, cedula, estado, tipo_usuario FROM usuarios;

SELECT '-----------------------------------------' AS '';
SELECT 'VERIFICACIÓN: Platos de menú creados' AS '';
SELECT id_plato_menu, nombre, precio, disponible, categoria FROM Platos_menu;

-- =============================================
-- CREDENCIALES PARA PRUEBAS
-- =============================================
-- 
-- ADMINISTRADOR:
--   Cédula: 0987654321
--   Contraseña: admin123
--   Estado: ACTIVO
--
-- COCINERO (Activo):
--   Cédula: 1234567890
--   Contraseña: cocinero123
--   Estado: ACTIVO
--
-- COCINERO (Inactivo):
--   Cédula: 1122334455
--   Contraseña: maria123
--   Estado: INACTIVO
--
-- =============================================

-- =============================================
-- CONSULTAS ÚTILES PARA DESARROLLO
-- =============================================

-- Ver estructura de la tabla usuarios
-- DESCRIBE usuarios;

-- Ver todos los pedidos
-- SELECT * FROM Pedidos ORDER BY fecha_creacion DESC;

-- Ver detalles de un pedido específico
-- SELECT p.nro_pedido, p.total_pedido, p.estado_pedido, 
--        d.cantidad, d.subtotal, pm.nombre AS plato
-- FROM Pedidos p
-- INNER JOIN Detalle_pedidos d ON p.id_pedido = d.id_pedido
-- INNER JOIN Platos_menu pm ON d.id_plato_menu = pm.id_plato_menu
-- WHERE p.id_pedido = 1;

-- Limpiar todos los pedidos (útil para testing)
-- DELETE FROM Detalle_pedidos;
-- DELETE FROM Pedidos;

-- Reiniciar auto_increment de pedidos
-- ALTER TABLE Pedidos AUTO_INCREMENT = 1;
