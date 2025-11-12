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
    estado ENUM('Disponible', 'Prestado') DEFAULT 'Disponible'
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
