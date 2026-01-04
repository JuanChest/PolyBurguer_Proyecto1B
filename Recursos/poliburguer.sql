drop database if exists poliburguer;
create database poliburguer;
use poliburguer;

CREATE TABLE `platos_menu` (
    `IDPLATOMENU` int(11) NOT NULL,
    `categoria` varchar(255) DEFAULT NULL,
    `DESCRIPCION` varchar(255) DEFAULT NULL,
    `DISPONIBLE` tinyint(1) DEFAULT 0,
    `IMAGEN` varchar(255) DEFAULT NULL,
    `NOMBRE` varchar(255) DEFAULT NULL,
    `PRECIO` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Insertar datos iniciales
--

INSERT INTO `platos_menu` (`IDPLATOMENU`, `categoria`, `DESCRIPCION`, `DISPONIBLE`, `IMAGEN`, `NOMBRE`, `PRECIO`) VALUES
(1, 'HAMBURGUESA', 'Doble carne de res, queso cheddar, bacon crujiente y salsa BBQ', 1, 'hamburguesa-doble.jpg', 'PoliBurguer Doble', 12.99),
(2, 'HAMBURGUESA', 'Hamburguesa de carne 100% res, lechuga, tomate, cebolla y salsa especial', 1, 'hamburguesa-clasica.jpg', 'PoliBurguer Clásica', 8.99),
(3, 'BEBIDA', 'Refresco Coca Cola 500ml', 1, 'coca-cola.jpg', 'Coca Cola', 2.5);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `platos_menu`
--
ALTER TABLE `platos_menu`
    ADD PRIMARY KEY (`IDPLATOMENU`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `platos_menu`
--
ALTER TABLE `platos_menu`
    MODIFY `IDPLATOMENU` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;