/* Populate tabla clientes */
INSERT INTO clientes (region_id, nombre, apellido, email, fecha_nacimiento, created_at, updated_at) VALUES(1, 'Andrés', 'Guzmán', 'profesor@bolsadeideas.com', '2018-01-01', '2018-01-01', '2018-01-01');
INSERT INTO clientes (region_id, nombre, apellido, email, fecha_nacimiento, created_at, updated_at) VALUES(1, 'Mr. John', 'Doe', 'john.doe@gmail.com', '2018-01-02', '2018-01-01', '2018-01-01');
INSERT INTO clientes (region_id, nombre, apellido, email, fecha_nacimiento, created_at, updated_at) VALUES(1, 'Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2018-01-03', '2018-01-01', '2018-01-01');
INSERT INTO clientes (region_id, nombre, apellido, email, fecha_nacimiento, created_at, updated_at) VALUES(2, 'Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2018-01-04', '2018-01-01', '2018-01-01');
INSERT INTO clientes (region_id, nombre, apellido, email, fecha_nacimiento, created_at, updated_at) VALUES(1, 'Erich', 'Gamma', 'erich.gamma@gmail.com', '2018-02-01', '2018-01-01', '2018-01-01');
INSERT INTO clientes (region_id, nombre, apellido, email, fecha_nacimiento, created_at, updated_at) VALUES(1, 'Richard', 'Helm', 'richard.helm@gmail.com', '2018-02-10', '2018-01-01', '2018-01-01');
INSERT INTO clientes (region_id, nombre, apellido, email, fecha_nacimiento, created_at, updated_at) VALUES(3, 'Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2018-02-18', '2018-01-01', '2018-01-01');
INSERT INTO clientes (region_id, nombre, apellido, email, fecha_nacimiento, created_at, updated_at) VALUES(1, 'John', 'Vlissides', 'john.vlissides@gmail.com', '2018-02-28', '2018-01-01', '2018-01-01');
INSERT INTO clientes (region_id, nombre, apellido, email, fecha_nacimiento, created_at, updated_at) VALUES(1, 'Dr. James', 'Gosling', 'james.gosling@gmail.com', '2018-03-03', '2018-01-01', '2018-01-01');
INSERT INTO clientes (region_id, nombre, apellido, email, fecha_nacimiento, created_at, updated_at) VALUES(4, 'Magma', 'Lee', 'magma.lee@gmail.com', '2018-03-04', '2018-01-01', '2018-01-01');
INSERT INTO clientes (region_id, nombre, apellido, email, fecha_nacimiento, created_at, updated_at) VALUES(1, 'Tornado', 'Roe', 'tornado.roe@gmail.com', '2018-03-05', '2018-01-01', '2018-01-01');
INSERT INTO clientes (region_id, nombre, apellido, email, fecha_nacimiento, created_at, updated_at) VALUES(1, 'Jade', 'Doe', 'jane.doe@gmail.com', '2018-03-06', '2018-01-01', '2018-01-01');


/*Populate table regiones*/
INSERT INTO regiones (id, nombre) VALUES (1, 'Sudamérica');
INSERT INTO regiones (id, nombre) VALUES (2, 'Centroamérica');
INSERT INTO regiones (id, nombre) VALUES (3, 'Norteamérica');
INSERT INTO regiones (id, nombre) VALUES (4, 'Europa');
INSERT INTO regiones (id, nombre) VALUES (5, 'Asia');
INSERT INTO regiones (id, nombre) VALUES (6, 'Africa');
INSERT INTO regiones (id, nombre) VALUES (7, 'Oceanía');
INSERT INTO regiones (id, nombre) VALUES (8, 'Antártida');


/* Creamos algunos usuarios con sus roles */
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('gus','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',1, 'gus', 'mar','gus@gmail.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('admin','$2a$10$VuTRRVwT4ZtczK4wFPd7yOoVgYG85tkkaNKVcTNw.x8uIv5j1JHIa',1, 'admin_name', 'admin_lastname','admin@admin.com');

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 1);

