-- -----------------------------------------------------
-- Crear base de datos
-- -----------------------------------------------------
DROP DATABASE IF EXISTS biblioteca_don_bosco;
CREATE DATABASE IF NOT EXISTS biblioteca_don_bosco;
USE biblioteca_don_bosco;
-- Tabla de Roles
CREATE TABLE Roles (
    id_rol INT PRIMARY KEY AUTO_INCREMENT,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE,
    cant_max_prestamo INT NOT NULL,
    dias_prestamo INT NOT NULL,
    mora_diaria DECIMAL(10,2) NOT NULL
);
-- Tabla de Usuarios
CREATE TABLE Usuarios (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE,
    contrasena VARCHAR(255) NOT NULL, -- Encriptada
    id_rol INT NOT NULL,
    FOREIGN KEY (id_rol) REFERENCES Roles(id_rol)
);


-- Tabla de Ejemplares (general)
CREATE TABLE Ejemplares (
    id_ejemplar INT PRIMARY KEY AUTO_INCREMENT,
    codigo_ejemplar VARCHAR(50) UNIQUE NOT NULL,  -- <<-- Nuevo campo
    titulo VARCHAR(200) NOT NULL,
    autor VARCHAR(200),
    ubicacion VARCHAR(100),
    tipo_documento ENUM('Libro', 'Diccionario', 'Mapas', 'Tesis', 'DVD', 'VHS', 'Cassettes', 'CD', 'Documento', 'Periodicos', 'Revistas') NOT NULL,
    estado ENUM('Disponible', 'Prestado''Reservado') DEFAULT 'Disponible'
);
-- Tabla de Reservas
CREATE TABLE Reservas (
    id_reserva INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    id_ejemplar INT NOT NULL,
    fecha_reserva DATE NOT NULL DEFAULT (CURRENT_DATE),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_ejemplar) REFERENCES Ejemplares(id_ejemplar)
);
-- Tabla de Préstamos
CREATE TABLE Prestamos (
    id_prestamo INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    id_ejemplar INT NOT NULL,
    fecha_prestamo DATE NOT NULL DEFAULT (CURRENT_DATE),
    estado ENUM('Activo', 'Devuelto') DEFAULT 'Activo',
    fecha_devolucion DATE NULL, -- Se actualiza al devolver
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_ejemplar) REFERENCES Ejemplares(id_ejemplar)
);


-- Tabla específica para Libros
CREATE TABLE Libros (
    id_ejemplar INT PRIMARY KEY,
    isbn VARCHAR(20),
    editorial VARCHAR(100),
    edicion INT,
    FOREIGN KEY (id_ejemplar) REFERENCES Ejemplares(id_ejemplar) ON DELETE CASCADE
);

-- Tabla específica para Diccionarios
CREATE TABLE Diccionarios (
    id_ejemplar INT PRIMARY KEY,
    idioma VARCHAR(50),
    volumen INT,
    FOREIGN KEY (id_ejemplar) REFERENCES Ejemplares(id_ejemplar) ON DELETE CASCADE
);

-- Tabla específica para Mapas
CREATE TABLE Mapas (
    id_ejemplar INT PRIMARY KEY,
    escala VARCHAR(50),
    tipo_mapa VARCHAR(100), -- físico, político, etc.
    FOREIGN KEY (id_ejemplar) REFERENCES Ejemplares(id_ejemplar) ON DELETE CASCADE
);

-- Tabla específica para Tesis
CREATE TABLE Tesis (
    id_ejemplar INT PRIMARY KEY,
    grado_academico VARCHAR(100), -- Licenciatura, Maestría, etc.
    facultad VARCHAR(100),
    FOREIGN KEY (id_ejemplar) REFERENCES Ejemplares(id_ejemplar) ON DELETE CASCADE
);

-- Tabla específica para DVDs
CREATE TABLE DVDs (
    id_ejemplar INT PRIMARY KEY,
    duracion TIME,
    genero VARCHAR(100),
    FOREIGN KEY (id_ejemplar) REFERENCES Ejemplares(id_ejemplar) ON DELETE CASCADE
);

-- Tabla específica para VHS
CREATE TABLE VHS (
    id_ejemplar INT PRIMARY KEY,
    duracion TIME,
    genero VARCHAR(100),
    FOREIGN KEY (id_ejemplar) REFERENCES Ejemplares(id_ejemplar) ON DELETE CASCADE
);

-- Tabla específica para Cassettes
CREATE TABLE Cassettes (
    id_ejemplar INT PRIMARY KEY,
    duracion TIME,
    tipo_cinta VARCHAR(50), -- audio, video
    FOREIGN KEY (id_ejemplar) REFERENCES Ejemplares(id_ejemplar) ON DELETE CASCADE
);

-- Tabla específica para CDs
CREATE TABLE CDs (
    id_ejemplar INT PRIMARY KEY,
    duracion TIME,
    genero VARCHAR(100),
    FOREIGN KEY (id_ejemplar) REFERENCES Ejemplares(id_ejemplar) ON DELETE CASCADE
);

-- Tabla específica para Documentos
CREATE TABLE Documentos (
    id_ejemplar INT PRIMARY KEY,
    tipo_documento_detalle VARCHAR(100), -- informe, memorando, etc.
    FOREIGN KEY (id_ejemplar) REFERENCES Ejemplares(id_ejemplar) ON DELETE CASCADE
);

-- Tabla específica para Periódicos
CREATE TABLE Periodicos (
    id_ejemplar INT PRIMARY KEY,
    fecha_publicacion DATE,
    tipo_periodico VARCHAR(100), -- local, nacional, etc.
    FOREIGN KEY (id_ejemplar) REFERENCES Ejemplares(id_ejemplar) ON DELETE CASCADE
);

-- Tabla específica para Revistas
CREATE TABLE Revistas (
    id_ejemplar INT PRIMARY KEY,
    fecha_publicacion DATE,
    tipo_revista VARCHAR(100), -- científica, cultural, etc.
    FOREIGN KEY (id_ejemplar) REFERENCES Ejemplares(id_ejemplar) ON DELETE CASCADE
);

-- Insertar roles
INSERT INTO Roles (nombre_rol, cant_max_prestamo, dias_prestamo,mora_diaria)
VALUES
    ('Administrador', 0, 0,0), -- 0 indica sin límite o sin préstamo
    ('Profesor', 6, 15,0.10),     -- 6 libros, 15 días
    ('Alumno', 3, 7,0.10);        -- 3 libros, 7 días
INSERT INTO Usuarios (nombre, apellido, correo, contrasena, id_rol)
VALUES ('admin', 'admin', 'admin@admin.udb.edu.sv', 
'$2a$10$6SNYnsJROk3eubVhAgS/rudlBpN8fC9XmMuPC0l8svopDdfPr3rcO', 1);
-- Insertar ejemplares en la tabla general
INSERT INTO Ejemplares (codigo_ejemplar, titulo, autor, ubicacion, tipo_documento, estado) VALUES
('LIB00001', 'Cien años de soledad', 'Gabriel García Márquez', 'Estantería A1', 'Libro', 'Disponible'),
('REV00001', 'National Geographic', 'Varios', 'Sala de lectura', 'Revistas', 'Disponible'),
('CDA00001', 'Best of Classic Music', 'Varios', 'Archivo audio', 'CD', 'Disponible'),
('DVD00001', 'Inception', 'Christopher Nolan', 'Archivo video', 'DVD', 'Prestado'),
('DIC00001', 'Diccionario de la Lengua Española', 'Real Academia Española', 'Consulta', 'Diccionario', 'Disponible'),
('MAP00001', 'Mapa físico de Sudamérica', 'IGN', 'Archivo mapas', 'Mapas', 'Disponible'),
('TES00001', 'Análisis de estructuras de datos', 'Juan Pérez', 'Archivo tesis', 'Tesis', 'Disponible'),
('VHS00001', 'Back to the Future', 'Robert Zemeckis', 'Archivo video', 'VHS', 'Disponible'),
('CAS00001', 'Historia del rock', 'Varios', 'Archivo audio', 'Cassettes', 'Disponible'),
('DOC00001', 'Informe de auditoría 2023', 'Departamento de auditoría', 'Archivo administrativo', 'Documento', 'Disponible'),
('PER00001', 'El País', 'Varios', 'Sala de lectura', 'Periodicos', 'Disponible');

-- Insertar datos específicos en las tablas hijas
INSERT INTO Libros (id_ejemplar, isbn, editorial, edicion) VALUES
(1, '978-3-16-148410-0', 'Sudamericana', 1);

INSERT INTO Revistas (id_ejemplar, fecha_publicacion, tipo_revista) VALUES
(2, '2023-05-15', 'Científica');

INSERT INTO CDs (id_ejemplar, duracion, genero) VALUES
(3, '01:45:00', 'Clásica');

INSERT INTO DVDs (id_ejemplar, duracion, genero) VALUES
(4, '02:28:00', 'Ciencia Ficción');

INSERT INTO Diccionarios (id_ejemplar, idioma, volumen) VALUES
(5, 'Español', 1);

INSERT INTO Mapas (id_ejemplar, escala, tipo_mapa) VALUES
(6, '1:5000000', 'Físico');

INSERT INTO Tesis (id_ejemplar, grado_academico, facultad) VALUES
(7, 'Maestría', 'Facultad de Ingeniería');

INSERT INTO VHS (id_ejemplar, duracion, genero) VALUES
(8, '01:40:00', 'Comedia');

INSERT INTO Cassettes (id_ejemplar, duracion, tipo_cinta) VALUES
(9, '01:30:00', 'Audio');

INSERT INTO Documentos (id_ejemplar, tipo_documento_detalle) VALUES
(10, 'Informe');

INSERT INTO Periodicos (id_ejemplar, fecha_publicacion, tipo_periodico) VALUES
(11, '2023-06-01', 'Nacional');

/*
SELECT  p.id_prestamo,u.nombre,u.apellido, e.titulo,r.mora_diaria,r.dias_prestamo,p.fecha_prestamo,
    DATEDIFF(CURRENT_DATE, p.fecha_prestamo) AS dias_transcurridos,
    CASE 
        WHEN DATEDIFF(CURRENT_DATE, p.fecha_prestamo) > r.dias_prestamo 
        THEN (DATEDIFF(CURRENT_DATE, p.fecha_prestamo) - r.dias_prestamo) * r.mora_diaria
        ELSE 0 
    END AS total_mora
FROM Prestamos p JOIN Usuarios u ON p.id_usuario = u.id_usuario JOIN Roles r ON u.id_rol = r.id_rol
JOIN Ejemplares e ON p.id_ejemplar = e.id_ejemplar WHERE p.id_prestamo = 5 AND p.estado = 'Activo';

SELECT  p.id_prestamo AS id_prestamo,e.titulo AS titulo,e.codigo_ejemplar AS codigo,e.tipo_documento AS tipo_documento,
    u.correo AS correo_usuario,r.nombre_rol AS nombre_rol,p.fecha_prestamo AS fecha_prestamo,p.estado AS estado,
    
    -- Días transcurridos
    DATEDIFF(CURRENT_DATE, p.fecha_prestamo) AS dias_transcurridos,
    
    -- Cálculo de mora
    CASE 
        WHEN DATEDIFF(CURRENT_DATE, p.fecha_prestamo) > r.dias_prestamo 
            THEN (DATEDIFF(CURRENT_DATE, p.fecha_prestamo) - r.dias_prestamo) * r.mora_diaria
        ELSE 0 
    END AS total_mora

FROM Prestamos p
INNER JOIN Ejemplares e ON p.id_ejemplar = e.id_ejemplar
INNER JOIN Usuarios u   ON p.id_usuario = u.id_usuario
INNER JOIN Roles r      ON u.id_rol = r.id_rol

WHERE (e.titulo LIKE "%%" OR u.correo LIKE "%%" OR e.tipo_documento LIKE "%%") AND p.estado = 'Activo'

ORDER BY p.fecha_prestamo DESC;*/


-- Inserción de usuarios adicionales para pruebas
INSERT INTO Usuarios (nombre, apellido, correo, contrasena, id_rol) VALUES
('Carlos', 'González', 'carlos.gonzalez@udb.edu.sv', 
'$2a$10$6SNYnsJROk3eubVhAgS/rudlBpN8fC9XmMuPC0l8svopDdfPr3rcO', 2), -- Profesor
('María', 'López', 'maria.lopez@udb.edu.sv', 
'$2a$10$6SNYnsJROk3eubVhAgS/rudlBpN8fC9XmMuPC0l8svopDdfPr3rcO', 3), -- Alumno
('Andrés', 'Hernández', 'andres.hernandez@udb.edu.sv', 
'$2a$10$6SNYnsJROk3eubVhAgS/rudlBpN8fC9XmMuPC0l8svopDdfPr3rcO', 3); -- Alumno

-- Registro de reservas
INSERT INTO Reservas (id_usuario, id_ejemplar, fecha_reserva) VALUES
(2, 2, '2025-11-10'), -- María (Alumno) reserva National Geographic
(3, 3, '2025-11-15'), -- Andrés (Alumno) reserva Best of Classic Music
(2, 6, '2025-11-18'); -- María (Alumno) reserva Mapa físico de Sudamérica

-- Registro de préstamos sin mora (aún dentro del plazo)
-- Profesor Carlos: préstamo reciente (dentro de 15 días)
INSERT INTO Prestamos (id_usuario, id_ejemplar, fecha_prestamo, estado) VALUES
(2, 1, '2025-11-15', 'Activo'); -- Cien años de soledad (5 días de préstamo)

-- Alumno María: préstamo reciente (dentro de 7 días)
INSERT INTO Prestamos (id_usuario, id_ejemplar, fecha_prestamo, estado) VALUES
(3, 5, '2025-11-18', 'Activo'); -- Diccionario de la Lengua Española (2 días de préstamo)

-- Alumno Andrés: préstamo devuelto a tiempo
INSERT INTO Prestamos (id_usuario, id_ejemplar, fecha_prestamo, estado, fecha_devolucion) VALUES
(4, 11, '2025-11-10', 'Devuelto', '2025-11-16'); -- El País (devuelto dentro del plazo)

-- Registro de préstamos con mora (fuera del plazo permitido)
-- Préstamo con 8 días de retraso (mora de 1 día) para alumno María
INSERT INTO Prestamos (id_usuario, id_ejemplar, fecha_prestamo, estado) VALUES
(3, 9, '2025-11-11', 'Activo'); -- Historia del rock (8 días de retraso)

-- Préstamo con 10 días de retraso para alumno Andrés
INSERT INTO Prestamos (id_usuario, id_ejemplar, fecha_prestamo, estado) VALUES
(4, 7, '2025-11-09', 'Activo'); -- Análisis de estructuras de datos (10 días de retraso)

-- Actualizar estados de ejemplares según su situación
-- Ejemplares en préstamo activo
UPDATE Ejemplares SET estado = 'Prestado' WHERE id_ejemplar IN (1, 5, 9, 7);

-- Ejemplares en reserva (no prestados activamente)
UPDATE Ejemplares SET estado = 'Reservado' WHERE id_ejemplar IN (2, 3, 6);
