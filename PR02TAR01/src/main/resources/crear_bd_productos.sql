-- Script SQL para base de datos y tabla productos
CREATE DATABASE IF NOT EXISTS chalet CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE chalet;

CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    precio DECIMAL(10,2) NOT NULL CHECK (precio > 0)
);
