-- Tabla de usuarios
CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    tipo_usuario ENUM('administrador', 'profesor', 'alumno') NOT NULL,
    estado_activo BOOLEAN DEFAULT TRUE
);

-- Tipos de documentos (para normalización)
CREATE TABLE tipos_documento (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL -- 'libro', 'revista', 'tesis', 'cd'
);

-- Documentos (tabla base)
CREATE TABLE documentos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    tipo_documento_id INT NOT NULL,
    ubicacion_fisica VARCHAR(100) NOT NULL,
    cantidad_total INT NOT NULL,
    cantidad_disponible INT NOT NULL,
    FOREIGN KEY (tipo_documento_id) REFERENCES tipos_documento(id)
);

-- Detalles específicos de libros
CREATE TABLE libros (
    documento_id INT PRIMARY KEY,
    isbn VARCHAR(20),
    autor VARCHAR(200),
    editorial VARCHAR(150),
    anio_publicacion INT,
    FOREIGN KEY (documento_id) REFERENCES documentos(id) ON DELETE CASCADE
);

-- Préstamos
CREATE TABLE prestamos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    documento_id INT NOT NULL,
    fecha_prestamo DATE NOT NULL,
    fecha_devolucion_esperada DATE NOT NULL,
    fecha_devolucion_real DATE NULL,
    mora_calculada DECIMAL(10,2) DEFAULT 0.00,
    estado ENUM('activo', 'devuelto') DEFAULT 'activo',
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (documento_id) REFERENCES documentos(id)
);

-- Configuración del sistema
CREATE TABLE configuracion (
    id INT PRIMARY KEY DEFAULT 1,
    max_prestamos_por_usuario INT DEFAULT 3,
    mora_diaria DECIMAL(5,2) DEFAULT 1.00
);
