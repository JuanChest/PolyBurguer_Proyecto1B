drop database if exists poliburguer;
create database poliburguer;
use poliburguer;

create table plato(
    id_plato int primary key auto_increment,
    nombre_plato varchar(50) not null,
    precio decimal(10,2) not null,
    descripcion varchar(255) not null,
    imagen varchar(255) not null,
    -- 1 = Activo, 0 = Inactivo
    estado TINYINT(1) NOT NULL DEFAULT 1
);

insert into plato (nombre_plato, precio, descripcion, imagen, estado) values
('Poliburguer Clásico', 8.99, 'Poliburguer Clásico', 'poliburguer-clasico.jpg', 1),
('Poliburguer Vegetariano', 8.99, 'Poliburguer Vegetariano', 'poliburguer-vegetariano.jpg', 1),
('Poliburguer Pollo', 8.99, 'Poliburguer Pollo', 'poliburguer-pollo.jpg', 1),
('Poliburguer Carne', 8.99, 'Poliburguer Carne', 'poliburguer-carne.jpg', 1),
('Poliburguer Jumbo', 8.99, 'Poliburguer Jumbo', 'poliburguer-jumbo.jpg', 1);