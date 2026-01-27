-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-01-2026
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30
--
-- Base de datos completa para PolyBurguer
-- Incluye todas las tablas necesarias y datos iniciales

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `poliburguer`
--

CREATE DATABASE IF NOT EXISTS `poliburguer`;
USE `poliburguer`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
-- Usa estrategia SINGLE_TABLE para la herencia (Usuario, Administrador, Cocinero)
--

CREATE TABLE IF NOT EXISTS `usuarios` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `tipo_usuario` varchar(31) DEFAULT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `cedula` varchar(255) DEFAULT NULL,
  `contrasenia` varchar(255) DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `cedula` (`cedula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`ID`, `tipo_usuario`, `apellido`, `cedula`, `contrasenia`, `estado`, `nombre`) VALUES
(1, 'ADMINISTRADOR', 'Principal', '0500000001', 'admin123', 'ACTIVO', 'Admin'),
(2, 'COCINERO', 'Pérez', '1234567890', 'cocinero123', 'ACTIVO', 'Juan'),
(3, 'COCINERO', 'González', '1122334455', 'maria123', 'ACTIVO', 'María'),
(4, 'ADMINISTRADOR', '1', '050000002', 'admin123', 'ACTIVO', 'Admin'),
(5, 'COCINERO', 'Lopez', '0987654321', 'pepe123', 'ACTIVO', 'Pepe');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `platos_menu`
--

CREATE TABLE IF NOT EXISTS `platos_menu` (
  `idPlatoMenu` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `precio` double NOT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `disponible` tinyint(1) DEFAULT 1,
  `categoria` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idPlatoMenu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `platos_menu`
--

INSERT INTO `platos_menu` (`idPlatoMenu`, `nombre`, `descripcion`, `precio`, `imagen`, `disponible`, `categoria`) VALUES
(1, 'PoliBurguer Clásica', 'Hamburguesa de carne 100% res, lechuga, tomate, cebolla y salsa especial', 8.99, 'hamburguesa-clasica.jpg', 1, 'HAMBURGUESA'),
(2, 'PoliBurguer Doble', 'Doble carne de res, queso cheddar, bacon crujiente y salsa BBQ', 12.99, 'hamburguesa-doble.jpg', 1, 'HAMBURGUESA'),
(3, 'Coca Cola', 'Refresco Coca Cola 500ml', 2.50, 'coca-cola.jpg', 1, 'BEBIDA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE IF NOT EXISTS `pedidos` (
  `id_pedido` int(11) NOT NULL AUTO_INCREMENT,
  `nro_pedido` varchar(7) DEFAULT NULL,
  `fecha_creacion` datetime DEFAULT NULL,
  `total_pedido` double DEFAULT NULL,
  `estado_pedido` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_pedido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_pedidos`
--

CREATE TABLE IF NOT EXISTS `detalle_pedidos` (
  `idDetallePedido` int(11) NOT NULL AUTO_INCREMENT,
  `cantidad` int(11) NOT NULL,
  `subtotal` double NOT NULL,
  `id_plato_menu` int(11) DEFAULT NULL,
  `id_pedido` int(11) DEFAULT NULL,
  PRIMARY KEY (`idDetallePedido`),
  KEY `FK_detalle_plato` (`id_plato_menu`),
  KEY `FK_detalle_pedido` (`id_pedido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Restricciones para las tablas volcadas
--

--
-- Filtros para la tabla `detalle_pedidos`
--
ALTER TABLE `detalle_pedidos`
  ADD CONSTRAINT `FK_detalle_plato` FOREIGN KEY (`id_plato_menu`) REFERENCES `platos_menu` (`idPlatoMenu`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_detalle_pedido` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id_pedido`) ON DELETE CASCADE ON UPDATE CASCADE;

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
