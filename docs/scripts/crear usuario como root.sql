-- ----------------------------------------
-- Crear usuario
-- ----------------------------------------
CREATE USER 'ejerciciosgym'@'%' IDENTIFIED BY 'Abcd1234.';


-- ----------------------------------------
-- Crear base de datos
-- ----------------------------------------
CREATE DATABASE bdejerciciosgym;

-- ----------------------------------------
-- Asignar permisos
-- ----------------------------------------
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, DROP ON bdejerciciosgym.* TO 'ejerciciosgym'@'%';
