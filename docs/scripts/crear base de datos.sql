-- ----------------------------------------
-- BD Ejercicios Gym
-- ----------------------------------------
USE bdejerciciosgym;

-- ----------------------------------------
-- Tabla ejercicios
-- ----------------------------------------
DROP TABLE IF EXISTS ejercicios;
CREATE TABLE ejercicios (
	id_ejercicio INT UNSIGNED AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(90) NOT NULL,
    descripcion TEXT NOT NULL,
    ruta_imagen VARCHAR(180) NOT NULL,
    peso_minimo INT UNSIGNED,
    peso_maximo INT UNSIGNED,
    PRIMARY KEY (id_ejercicio)
);

-- ----------------------------------------
-- Tabla musculos
-- ----------------------------------------
DROP TABLE IF EXISTS musculos;
CREATE TABLE musculos (
	id_musculo INT UNSIGNED AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(90) NOT NULL,
    descripcion TEXT NOT NULL,
    zona ENUM('SUPERIOR', 'INFERIOR', 'CORE') NOT NULL,
    grupo ENUM('PECHO', 'ESPALDA', 'HOMBRO', 'BRAZO', 'LUMBARES', 'ABDOMINALES', 'PIERNA') NOT NULL,
    PRIMARY KEY (id_musculo)
);

-- ----------------------------------------
-- Relación ejercicio -> musculos
-- ----------------------------------------
DROP TABLE IF EXISTS ejercicio_musculos;
CREATE TABLE ejercicio_musculos (
	id_ejercicio INT UNSIGNED NOT NULL,
    id_musculo INT UNSIGNED NOT NULL,
    descripcion TEXT NOT NULL,
    es_directo BOOLEAN NOT NULL,
    porcentaje_activacion INT NOT NULL,
    PRIMARY KEY (id_ejercicio, id_musculo),
    CONSTRAINT fk_eje_id_eje
		FOREIGN KEY (id_ejercicio)
        REFERENCES ejercicios (id_ejercicio)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
	CONSTRAINT fk_mus_id_mus
		FOREIGN KEY (id_musculo)
        REFERENCES musculos (id_musculo)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- ----------------------------------------
-- Cargar músculos
-- ----------------------------------------
INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Pectoral superior', 'Parte alta del pecho, implicada en movimientos de empuje inclinado y elevación del brazo.', 'SUPERIOR', 'PECHO');
INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Pectoral medio', 'Zona central del pecho, principal en movimientos de empuje horizontal como el press de banca.', 'SUPERIOR', 'PECHO');
INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Pectoral inferior', 'Parte baja del pecho, activada en empujes declinados y movimientos descendentes.', 'SUPERIOR', 'PECHO');

INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Trapecio', 'Músculo superior de la espalda que eleva, retrae y estabiliza los hombros y escápulas.', 'SUPERIOR', 'ESPALDA');
INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Dorsal ancho', 'Músculo grande de la espalda responsable de la aducción, extensión y rotación interna del brazo.', 'SUPERIOR', 'ESPALDA');
INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Romboides', 'Músculos situados entre las escápulas que permiten retraer y estabilizar la espalda.', 'SUPERIOR', 'ESPALDA');

INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Deltoide anterior', 'Parte frontal del hombro, participa en la elevación frontal del brazo y movimientos de empuje.', 'SUPERIOR', 'HOMBRO');
INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Deltoide lateral', 'Parte media del hombro, responsable de la abducción del brazo y de la anchura del hombro.', 'SUPERIOR', 'HOMBRO');
INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Deltoide posterior', 'Parte trasera del hombro, implicada en movimientos de tirón y apertura de brazos.', 'SUPERIOR', 'HOMBRO');

INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Tríceps', 'Músculo posterior del brazo encargado de la extensión del codo.', 'SUPERIOR', 'BRAZO');
INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Bíceps', 'Músculo anterior del brazo que flexiona el codo y ayuda en la supinación del antebrazo.', 'SUPERIOR', 'BRAZO');
INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Antebrazo', 'Conjunto de músculos que controlan la muñeca, la mano y la fuerza de agarre.', 'SUPERIOR', 'BRAZO');

INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Lumbares', 'Zona baja de la espalda que estabiliza el tronco y permite su extensión.', 'CORE', 'LUMBARES');
INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Abdominales', 'Músculos frontales del core que permiten la flexión del tronco y estabilización.', 'CORE', 'ABDOMINALES');
INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Oblicuos', 'Músculos laterales del abdomen que permiten la rotación e inclinación del tronco.', 'CORE', 'ABDOMINALES');

INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Cuádriceps', 'Músculos frontales del muslo responsables de la extensión de la rodilla.', 'INFERIOR', 'PIERNA');
INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Femoral', 'Parte posterior del muslo que permite la flexión de la rodilla y extensión de la cadera.', 'INFERIOR', 'PIERNA');
INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Gemelo', 'Músculo de la pantorrilla responsable de la flexión plantar del pie.', 'INFERIOR', 'PIERNA');
INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Glúteos', 'Músculos de la cadera que intervienen en la extensión, abducción y estabilidad del cuerpo.', 'INFERIOR', 'PIERNA');
INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Aductores', 'Músculos internos del muslo que permiten acercar las piernas al eje del cuerpo.', 'INFERIOR', 'PIERNA');
INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES ('Abductores', 'Músculos externos del muslo que permiten separar las piernas del eje del cuerpo.', 'INFERIOR', 'PIERNA');
