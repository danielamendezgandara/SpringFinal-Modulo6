-- SPRINT Modulo 6 - Grupo 01 

-- Crear una base de datos empresa de prevencion de riesgos
DROP DATABASE IF EXISTS empresaprevencionriesgos;
CREATE DATABASE empresaprevencionriesgos DEFAULT CHARACTER SET utf8mb4;
-- Crear un usuario con todos los privilegios para trabajar con la base de datos recién creada.  
DROP USER IF EXISTS 'adminepr'@'localhost';  
CREATE USER 'adminepr'@'localhost' IDENTIFIED BY 'password';
SET PASSWORD FOR 'adminepr'@'localhost' = '123456';

-- Totalidad de permisos a la base de datos creada.
GRANT ALL PRIVILEGES ON empresaprevencionriesgos.* TO 'adminepr'@'localhost';
-- Se cargan los privilegios del usuario.
FLUSH PRIVILEGES;

-- SHOW DATABASES;
-- SHOW TABLES;
-- USE empresaprevencionriesgos;

-- TABLAS

DROP TABLE IF EXISTS empresaprevencionriesgos.sistema_salud; 
CREATE TABLE empresaprevencionriesgos.sistema_salud (
   sistema_salud_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, 
   nombre VARCHAR(10) NOT NULL UNIQUE, 
   PRIMARY KEY (sistema_salud_id)
) DEFAULT CHARSET=utf8mb4;
DESCRIBE empresaprevencionriesgos.sistema_salud; 
INSERT INTO empresaprevencionriesgos.sistema_salud (sistema_salud_id, nombre) VALUES (1,'fonasa'), (2,'isapre');
SELECT * FROM empresaprevencionriesgos.sistema_salud;

DROP TABLE IF EXISTS empresaprevencionriesgos.afp ; 
CREATE TABLE empresaprevencionriesgos.afp (
   afp_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, 
   nombre VARCHAR(50) NOT NULL UNIQUE, 
   PRIMARY KEY (afp_id)
) DEFAULT CHARSET=utf8mb4;
DESCRIBE empresaprevencionriesgos.afp; 
INSERT INTO empresaprevencionriesgos.afp (afp_id, nombre) VALUES (1,'afp capital'), (2,'afp provida'), (3,'afp cuprum'),(4,'afp habitat'),(5,'afp plan vital'),(6,'afp modelo'),(7,'afp uno');
SELECT * FROM empresaprevencionriesgos.afp;

DROP TABLE IF EXISTS empresaprevencionriesgos.dia; 
CREATE TABLE empresaprevencionriesgos.dia (
   dia_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, 
   nombre VARCHAR(10) NOT NULL UNIQUE, 
   PRIMARY KEY (dia_id)
) DEFAULT CHARSET=utf8mb4;
DESCRIBE empresaprevencionriesgos.dia; 
INSERT INTO empresaprevencionriesgos.dia (dia_id,nombre) VALUES (1,'lunes'), (2,'martes'), (3,'miércoles'),(4,'jueves'),(5,'viernes'),(6,'sábado'),(7,'domingo');
SELECT * FROM empresaprevencionriesgos.dia;

DROP TABLE IF EXISTS empresaprevencionriesgos.perfil; 
CREATE TABLE empresaprevencionriesgos.perfil (
   perfil_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, 
   nombre VARCHAR(50) NOT NULL UNIQUE, 
   PRIMARY KEY (perfil_id)
) DEFAULT CHARSET=utf8mb4;
DESCRIBE empresaprevencionriesgos.perfil; 
INSERT INTO empresaprevencionriesgos.perfil (perfil_id, nombre) VALUES (1,'administrativo'), (2,'cliente'), (3,'profesional');
SELECT * FROM empresaprevencionriesgos.perfil;

DROP TABLE IF EXISTS empresaprevencionriesgos.usuario; 
CREATE TABLE empresaprevencionriesgos.usuario (
   usuario_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, 
   nick VARCHAR(50) NOT NULL UNIQUE, 
   password1 VARCHAR(200) NOT NULL, 
   rut VARCHAR(15) NOT NULL UNIQUE,
   nombres VARCHAR(50) NOT NULL, 
   apellidos VARCHAR(50) NOT NULL, 
   fecha_de_nacimiento DATE NOT NULL,
   perfil_id SMALLINT UNSIGNED NOT NULL,
   PRIMARY KEY (usuario_id),
   CONSTRAINT usuario_ibfk_1  FOREIGN KEY (perfil_id) REFERENCES empresaprevencionriesgos.perfil (perfil_id) ON DELETE CASCADE
) DEFAULT CHARSET=utf8mb4;
DESCRIBE empresaprevencionriesgos.usuario; 

DROP TABLE IF EXISTS empresaprevencionriesgos.cliente; 
CREATE TABLE empresaprevencionriesgos.cliente (
   cliente_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, 
   telefono VARCHAR(15),
   email VARCHAR(250) NOT NULL, 
   afp_id SMALLINT UNSIGNED NOT NULL,
   sistema_salud_id SMALLINT UNSIGNED NOT NULL,
   direccion VARCHAR(100), 
   comuna VARCHAR(50), 
   organizacion VARCHAR(100) NOT NULL, 
   usuario_id SMALLINT UNSIGNED NOT NULL,
   PRIMARY KEY (cliente_id),
   CONSTRAINT cliente_ibfk_1  FOREIGN KEY (afp_id) REFERENCES empresaprevencionriesgos.afp (afp_id) ON DELETE CASCADE,
   CONSTRAINT cliente_ibfk_2  FOREIGN KEY (sistema_salud_id) REFERENCES empresaprevencionriesgos.sistema_salud (sistema_salud_id) ON DELETE CASCADE,
   CONSTRAINT cliente_ibfk_3  FOREIGN KEY (usuario_id) REFERENCES empresaprevencionriesgos.usuario (usuario_id) ON DELETE CASCADE
) DEFAULT CHARSET=utf8mb4;
DESCRIBE empresaprevencionriesgos.cliente; 

DROP TABLE IF EXISTS empresaprevencionriesgos.administrativo; 
CREATE TABLE empresaprevencionriesgos.administrativo (
   administrativo_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, 
   area VARCHAR(50), 
   experiencia_previa VARCHAR(100), 
   usuario_id SMALLINT UNSIGNED NOT NULL,
   PRIMARY KEY (administrativo_id),
   CONSTRAINT administrativo_ibfk_1  FOREIGN KEY (usuario_id) REFERENCES empresaprevencionriesgos.usuario (usuario_id) ON DELETE CASCADE
) DEFAULT CHARSET=utf8mb4;
DESCRIBE empresaprevencionriesgos.administrativo; 

DROP TABLE IF EXISTS empresaprevencionriesgos.profesional; 
CREATE TABLE empresaprevencionriesgos.profesional (
   profesional_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, 
   titulo VARCHAR(100), 
   fecha_de_ingreso DATE,  
   usuario_id SMALLINT UNSIGNED NOT NULL,
   PRIMARY KEY (profesional_id),
   CONSTRAINT profesional_ibfk_1  FOREIGN KEY (usuario_id) REFERENCES empresaprevencionriesgos.usuario (usuario_id) ON DELETE CASCADE
) DEFAULT CHARSET=utf8mb4;
DESCRIBE empresaprevencionriesgos.profesional; 

DROP TABLE IF EXISTS empresaprevencionriesgos.capacitacion; 
CREATE TABLE empresaprevencionriesgos.capacitacion (
   capacitacion_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, 
   identificador LONG NOT NULL,
   dia_id SMALLINT UNSIGNED NOT NULL,
   hora VARCHAR(6), 
   lugar VARCHAR(100), 
   duracion SMALLINT UNSIGNED, 
   cantidad_de_asistentes SMALLINT UNSIGNED,
   cliente_id SMALLINT UNSIGNED NOT NULL,
   PRIMARY KEY (capacitacion_id),
   CONSTRAINT capacitacion_ibfk_1  FOREIGN KEY (dia_id) REFERENCES empresaprevencionriesgos.dia (dia_id) ON DELETE CASCADE,
   CONSTRAINT capacitacion_ibfk_2  FOREIGN KEY (cliente_id) REFERENCES empresaprevencionriesgos.cliente (cliente_id) ON DELETE CASCADE
) DEFAULT CHARSET=utf8mb4;
DESCRIBE empresaprevencionriesgos.capacitacion; 

DROP TABLE IF EXISTS empresaprevencionriesgos.contacto; 
CREATE TABLE empresaprevencionriesgos.contacto (
   contacto_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,  
   mensaje VARCHAR(250), 
   cliente_id SMALLINT UNSIGNED NOT NULL,
   PRIMARY KEY (contacto_id),
   CONSTRAINT contacto_ibfk_1  FOREIGN KEY (cliente_id) REFERENCES empresaprevencionriesgos.cliente (cliente_id) ON DELETE CASCADE
) DEFAULT CHARSET=utf8mb4;
DESCRIBE empresaprevencionriesgos.contacto; 

/*Carga de tablas base*/
DROP PROCEDURE IF EXISTS empresaprevencionriesgos.iniciarTablas
DELIMITER $$
CREATE PROCEDURE empresaprevencionriesgos.iniciarTablas()
BEGIN
delete from empresaprevencionriesgos.usuario where perfil_id>0;
insert into empresaprevencionriesgos.usuario (usuario_id, nick, password1, rut, nombres, apellidos, fecha_de_nacimiento, perfil_id)  
values (1,'ada','$2a$10$v/8X3O05Bv9KUbeF0MDfG.FI7k8QwOnF0vLeNnGCnccNeLjOk/G62','11.000.000-1','Ada','Lovelace','1815-12-10',1),
(2,'turin','$2a$10$v/8X3O05Bv9KUbeF0MDfG.FI7k8QwOnF0vLeNnGCnccNeLjOk/G62','12.000.000-2','Alan','Turin','1912-06-23',1),
(3,'awake','$2a$10$v/8X3O05Bv9KUbeF0MDfG.FI7k8QwOnF0vLeNnGCnccNeLjOk/G62','13.000.000-3','Awake','Lab','2000-01-30',2),
(4,'edu','$2a$10$v/8X3O05Bv9KUbeF0MDfG.FI7k8QwOnF0vLeNnGCnccNeLjOk/G62','14.000.000-4','Edu','Edu','2000-01-01',3),
(5,'client2','$2a$10$v/8X3O05Bv9KUbeF0MDfG.FI7k8QwOnF0vLeNnGCnccNeLjOk/G62','15.000.001-3','Pedro','Peres','2008-01-30',2);

insert into empresaprevencionriesgos.administrativo (administrativo_id, area,experiencia_previa,usuario_id) 
values (1, 'area TI','mucha experiencia previa',1),(2, 'area TI','mucha experiencia previa',2) ;

insert into empresaprevencionriesgos.cliente (cliente_id,telefono,email,afp_id,sistema_salud_id,direccion,comuna, organizacion,usuario_id) 
values (1, 988774455,'awake@gmail.com',1,1,'calle principal 1111','valparaiso','AdalidAwake',3),
(2, 123654785,'client2@gmail.com',1,1,'calle siempre viva 1111','valparaiso','EmpresaClient',5);

insert into empresaprevencionriesgos.profesional (profesional_id,titulo,fecha_de_ingreso,usuario_id) 
values (1, 'titulo ti','2022-12-01',4);

insert into contacto (mensaje,cliente_id) values ('hola cobol',1);

-- capacitaciones
insert into empresaprevencionriesgos.capacitacion 
values (null, 11001101, 1, "11:30","online", 60, 10, 1),
(null, 11001102, 1, "12:30","online", 60, 20, 1),
(null, 11001103, 2, "13:30","online", 60, 30, 1),
(null, 11001104, 4, "14:30","online", 60, 40, 1),
(null, 11001105, 5, "15:30","online", 60, 50, 1),
(null, 11001106, 5, "16:30","online", 60, 60, 1),
(null, 11001107, 5, "17:30","online", 60, 70, 1),
(null, 11001108, 3, "18:30","online", 60, 80, 2),
(null, 11001109, 3, "19:30","online", 60, 90, 2),
(null, 11001110, 3, "20:30","online", 60, 100, 2),
(null, 11001111, 1, "21:30","online", 60, 110, 2),
(null, 11001112, 2, "22:30","online", 60, 120, 2);

END$$
DELIMITER ;
call empresaprevencionriesgos.iniciarTablas;

/*Select pruebas*/
select * from empresaprevencionriesgos.usuario 
inner join empresaprevencionriesgos.administrativo on usuario.usuario_id=administrativo.usuario_id;

select * from empresaprevencionriesgos.usuario 
inner join empresaprevencionriesgos.cliente on usuario.usuario_id=cliente.usuario_id;

select * from empresaprevencionriesgos.usuario 
inner join empresaprevencionriesgos.profesional on usuario.usuario_id=profesional.usuario_id;

select * from empresaprevencionriesgos.usuario 
inner join empresaprevencionriesgos.cliente on usuario.usuario_id=cliente.usuario_id
inner join empresaprevencionriesgos.capacitacion on cliente.cliente_id=capacitacion.cliente_id;

select * from empresaprevencionriesgos.usuario 
inner join empresaprevencionriesgos.cliente on usuario.usuario_id=cliente.usuario_id
inner join empresaprevencionriesgos.contacto on cliente.cliente_id=contacto.cliente_id;

select * from empresaprevencionriesgos.usuario inner join empresaprevencionriesgos.profesional on usuario.usuario_id=profesional.usuario_id;

select * from empresaprevencionriesgos.usuario inner join perfil on usuario.perfil_id=perfil.perfil_idadministrativocapacitacioncapacitacioncapacitacioncapacitacion;

select * from empresaprevencionriesgos.capacitacion;
select * from empresaprevencionriesgos.perfil;
select * from empresaprevencionriesgos.usuario;
select * from empresaprevencionriesgos.contacto;

select * from empresaprevencionriesgos.capacitacion inner join empresaprevencionriesgos.cliente on capacitacion.cliente_id=cliente.cliente_id;

-- Importante pasword de usuario indicados en este script están encriptados.Al momento de 
-- loguearse digitar la siguiente contraseña : 1234 
-- En el caso de que sea administrador que posee la facultad de crear usuarios la contraseña
-- será encriptada, por el momento recuerde la contraseña hasta que exista una sesión de re-
-- cuperación de password.
-- update empresaprevencionriesgos.usuario set password1 = '$2a$10$v/8X3O05Bv9KUbeF0MDfG.FI7k8QwOnF0vLeNnGCnccNeLjOk/G62' where nick = 'awake';