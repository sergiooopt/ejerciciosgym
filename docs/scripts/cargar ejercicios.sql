-- Conjuntamente se deben cargar las imagenes en "/opt/ejerciciosgym/imagenes"

-- ----------------------------------------
-- Cargar ejercicios y músculos 
-- ----------------------------------------
USE bdejerciciosgym;

-- ----------------------------------------
-- Press banca
-- ----------------------------------------
INSERT INTO ejercicios (id_ejercicio, nombre, descripcion, ruta_imagen, peso_minimo, peso_maximo)
VALUES (1, 'Press de banca plano con barra', 'Ejercicio básico de empuje horizontal para pecho medio.', '/opt/ejerciciosgym/imagenes/1-press_banca.jpg', NULL, NULL);

INSERT INTO ejercicio_musculos VALUES
(1, (SELECT id_musculo FROM musculos WHERE nombre='Pectoral medio'), 'Activación principal del pecho medio.', TRUE, 70),
(1, (SELECT id_musculo FROM musculos WHERE nombre='Tríceps'), 'Extensión del codo durante el empuje.', TRUE, 20),
(1, (SELECT id_musculo FROM musculos WHERE nombre='Deltoide anterior'), 'Estabilización del hombro.', FALSE, 10);


-- ----------------------------------------
-- Press inclinado
-- ----------------------------------------
INSERT INTO ejercicios VALUES
(2, 'Press de banca inclinado con mancuernas',  'Ejercicio de empuje inclinado para trabajar la parte superior del pecho.', '/opt/ejerciciosgym/imagenes/2-press_inclinado.jpg', 5, 45);

INSERT INTO ejercicio_musculos VALUES
(2, (SELECT id_musculo FROM musculos WHERE nombre='Pectoral superior'), 'Activación principal del pecho superior.', TRUE, 70),
(2, (SELECT id_musculo FROM musculos WHERE nombre='Deltoide anterior'), 'Apoyo directo en el empuje inclinado.', TRUE, 20),
(2, (SELECT id_musculo FROM musculos WHERE nombre='Tríceps'), 'Extensión del codo durante el empuje.', FALSE, 10);

-- ----------------------------------------
-- Fondos
-- ----------------------------------------
INSERT INTO ejercicios VALUES
(3, 'Fondos de pecho', 'Ejercicio de empuje vertical inclinado para pecho inferior y tríceps.', '/opt/ejerciciosgym/imagenes/3-fondos.jpg', NULL, NULL);

INSERT INTO ejercicio_musculos VALUES
(3, (SELECT id_musculo FROM musculos WHERE nombre='Pectoral inferior'), 'Activación principal del pecho inferior.', TRUE, 70),
(3, (SELECT id_musculo FROM musculos WHERE nombre='Tríceps'), 'Extensión del codo en el empuje.', TRUE, 20),
(3, (SELECT id_musculo FROM musculos WHERE nombre='Deltoide anterior'), 'Estabilización del hombro.', FALSE, 10);

-- ----------------------------------------
-- Press militar
-- ----------------------------------------
INSERT INTO ejercicios VALUES
(4, 'Press de hombro sentado con mancuernas',  'Ejercicio de empuje vertical para hombros.', '/opt/ejerciciosgym/imagenes/4-press_militar.jpg', 5, 45);

INSERT INTO ejercicio_musculos VALUES
(4, (SELECT id_musculo FROM musculos WHERE nombre='Deltoide anterior'), 'Motor principal del empuje vertical.', TRUE, 70),
(4, (SELECT id_musculo FROM musculos WHERE nombre='Deltoide lateral'), 'Apoyo directo en la abducción.', TRUE, 20),
(4, (SELECT id_musculo FROM musculos WHERE nombre='Tríceps'), 'Extensión del codo.', FALSE, 10);

-- ----------------------------------------
-- Elevaciones laterales
-- ----------------------------------------
INSERT INTO ejercicios VALUES
(5, 'Elevaciones laterales con mancuernas', 'Aislamiento del deltoide lateral.', '/opt/ejerciciosgym/imagenes/5-elevaciones_laterales.jpg', 5, 45);

INSERT INTO ejercicio_musculos VALUES
(5, (SELECT id_musculo FROM musculos WHERE nombre='Deltoide lateral'), 'Activación principal del hombro lateral.', TRUE, 70),
(5, (SELECT id_musculo FROM musculos WHERE nombre='Deltoide anterior'), 'Apoyo en la elevación.', TRUE, 20),
(5, (SELECT id_musculo FROM musculos WHERE nombre='Trapecio'), 'Estabilización escapular.', FALSE, 10);

-- ----------------------------------------
-- Dominadas
-- ----------------------------------------
INSERT INTO ejercicios VALUES
(6, 'Dominadas agarre neutro',  'Ejercicio de tracción vertical para espalda y brazos.', '/opt/ejerciciosgym/imagenes/6-dominadas.jpg', NULL, NULL);

INSERT INTO ejercicio_musculos VALUES
(6, (SELECT id_musculo FROM musculos WHERE nombre='Dorsal ancho'), 'Activación principal en la tracción vertical.', TRUE, 70),
(6, (SELECT id_musculo FROM musculos WHERE nombre='Bíceps'), 'Apoyo directo en la flexión del codo.', TRUE, 20),
(6, (SELECT id_musculo FROM musculos WHERE nombre='Romboides'), 'Estabilización escapular.', FALSE, 10);

---------------------------------------
-- Remo con barra libre
-- ----------------------------------------
INSERT INTO ejercicios VALUES
(7, 'Remo con barra recta', 'Ejercicio de tracción horizontal para espalda media.', '/opt/ejerciciosgym/imagenes/7-remo_con_barra_libre.jpg', NULL, NULL);

INSERT INTO ejercicio_musculos VALUES
(7, (SELECT id_musculo FROM musculos WHERE nombre='Dorsal ancho'), 'Motor principal en la tracción horizontal.', TRUE, 70),
(7, (SELECT id_musculo FROM musculos WHERE nombre='Romboides'), 'Retracción escapular durante el tirón.', TRUE, 20),
(7, (SELECT id_musculo FROM musculos WHERE nombre='Trapecio'), 'Estabilización de la cintura escapular.', FALSE, 10);

-- ----------------------------------------
-- Remo en polea
-- ----------------------------------------
INSERT INTO ejercicios VALUES
(8, 'Remo sentado en polea con agarre de cuerda',  'Tracción horizontal con énfasis en espalda media y deltoide posterior.', '/opt/ejerciciosgym/imagenes/8-remo_sen_polea.jpg', 9, 81);

INSERT INTO ejercicio_musculos VALUES
(8, (SELECT id_musculo FROM musculos WHERE nombre='Romboides'), 'Retracción escapular principal.', TRUE, 70),
(8, (SELECT id_musculo FROM musculos WHERE nombre='Deltoide posterior'), 'Apoyo directo en la apertura del tirón.', TRUE, 20),
(8, (SELECT id_musculo FROM musculos WHERE nombre='Dorsal ancho'), 'Estabilización y asistencia en el tirón.', FALSE, 10);

-- ----------------------------------------
-- Face pull
-- ----------------------------------------
INSERT INTO ejercicios VALUES
(9, 'Face pull en polea',  'Ejercicio para deltoide posterior y parte alta de la espalda.', '/opt/ejerciciosgym/imagenes/9-face_pull.jpg', 9, 81);

INSERT INTO ejercicio_musculos VALUES
(9, (SELECT id_musculo FROM musculos WHERE nombre='Deltoide posterior'), 'Activación principal en la apertura del tirón.', TRUE, 70),
(9, (SELECT id_musculo FROM musculos WHERE nombre='Trapecio'), 'Elevación y estabilización escapular.', TRUE, 20),
(9, (SELECT id_musculo FROM musculos WHERE nombre='Romboides'), 'Estabilización de la retracción.', FALSE, 10);

-- ----------------------------------------
-- Curl biceps inclinado
-- ----------------------------------------
INSERT INTO ejercicios VALUES
(10, 'Curl bíceps inclinado con mancuerna',  'Aislamiento del bíceps en posición inclinada.', '/opt/ejerciciosgym/imagenes/10-curl_biceps_inclinado.jpg', 5, 45);

INSERT INTO ejercicio_musculos VALUES
(10, (SELECT id_musculo FROM musculos WHERE nombre='Bíceps'), 'Activación principal del bíceps.', TRUE, 70),
(10, (SELECT id_musculo FROM musculos WHERE nombre='Antebrazo'), 'Apoyo en la flexión y agarre.', TRUE, 20),
(10, (SELECT id_musculo FROM musculos WHERE nombre='Deltoide anterior'), 'Estabilización del brazo.', FALSE, 10);

-- ----------------------------------------
-- Predicador
-- ----------------------------------------
INSERT INTO ejercicios VALUES
(11, 'Curl de bíceps en banco',  'Aislamiento del bíceps con apoyo en banco.', '/opt/ejerciciosgym/imagenes/11-predicador.jpg', 10, 50);

INSERT INTO ejercicio_musculos VALUES
(11, (SELECT id_musculo FROM musculos WHERE nombre='Bíceps'), 'Activación principal del bíceps.', TRUE, 70),
(11, (SELECT id_musculo FROM musculos WHERE nombre='Antebrazo'), 'Apoyo en la flexión y agarre.', TRUE, 20),
(11, (SELECT id_musculo FROM musculos WHERE nombre='Deltoide anterior'), 'Estabilización del brazo.', FALSE, 10);

-- ----------------------------------------
-- Hack squat
-- ----------------------------------------
INSERT INTO ejercicios VALUES
(12, 'Sentadilla hack squat',  'Ejercicio de empuje para cuádriceps con apoyo guiado.', '/opt/ejerciciosgym/imagenes/12-hack_squat.jpg', NULL, NULL);

INSERT INTO ejercicio_musculos VALUES
(12, (SELECT id_musculo FROM musculos WHERE nombre='Cuádriceps'), 'Activación principal en la extensión de rodilla.', TRUE, 70),
(12, (SELECT id_musculo FROM musculos WHERE nombre='Glúteos'), 'Apoyo directo en la extensión de cadera.', TRUE, 20),
(12, (SELECT id_musculo FROM musculos WHERE nombre='Femoral'), 'Estabilización posterior.', FALSE, 10);

-- ----------------------------------------
-- Extensión de piernas
-- ----------------------------------------
INSERT INTO ejercicios VALUES
(13, 'Extensión de piernas en máquina',  'Aislamiento del cuádriceps en extensión de rodilla.', '/opt/ejerciciosgym/imagenes/13-extension_de_piernas.jpg', 9, 113);

INSERT INTO ejercicio_musculos VALUES
(13, (SELECT id_musculo FROM musculos WHERE nombre='Cuádriceps'), 'Activación principal del cuádriceps.', TRUE, 70),
(13, (SELECT id_musculo FROM musculos WHERE nombre='Glúteos'), 'Estabilización de cadera.', TRUE, 20),
(13, (SELECT id_musculo FROM musculos WHERE nombre='Femoral'), 'Estabilización posterior.', FALSE, 10);

-- ----------------------------------------
-- Peso muerto rumano
-- ----------------------------------------
INSERT INTO ejercicios VALUES
(14, 'Peso muerto rumano con barra',  'Ejercicio dominante de cadera para femorales y glúteos.', '/opt/ejerciciosgym/imagenes/14-peso_muerto_rumano.jpg', NULL, NULL);

INSERT INTO ejercicio_musculos VALUES
(14, (SELECT id_musculo FROM musculos WHERE nombre='Femoral'), 'Activación principal en la extensión de cadera.', TRUE, 70),
(14, (SELECT id_musculo FROM musculos WHERE nombre='Glúteos'), 'Apoyo directo en la extensión.', TRUE, 20),
(14, (SELECT id_musculo FROM musculos WHERE nombre='Lumbares'), 'Estabilización del tronco.', FALSE, 10);

-- ----------------------------------------
-- Femoral sentado en máquina
-- ----------------------------------------
INSERT INTO ejercicios VALUES
(15, 'Curl femoral sentado en máquina',  'Aislamiento del femoral en flexión de rodilla.', '/opt/ejerciciosgym/imagenes/15-femoral_sentado_en_maquina.jpg', 9, 113);

INSERT INTO ejercicio_musculos VALUES
(15, (SELECT id_musculo FROM musculos WHERE nombre='Femoral'), 'Activación principal del femoral.', TRUE, 70),
(15, (SELECT id_musculo FROM musculos WHERE nombre='Glúteos'), 'Estabilización de cadera.', TRUE, 20),
(15, (SELECT id_musculo FROM musculos WHERE nombre='Lumbares'), 'Estabilización del tronco.', FALSE, 10);

-- ----------------------------------------
-- Gemelo de pie
-- ----------------------------------------
INSERT INTO ejercicios VALUES
(16, 'Elevación de talones de pie en máquina',  'Aislamiento del gemelo en flexión plantar.', '/opt/ejerciciosgym/imagenes/16-gemelo_de_pie.jpg', NULL, NULL);

INSERT INTO ejercicio_musculos VALUES
(16, (SELECT id_musculo FROM musculos WHERE nombre='Gemelo'), 'Activación principal del gemelo.', TRUE, 70),
(16, (SELECT id_musculo FROM musculos WHERE nombre='Aductores'), 'Estabilización de piernas.', TRUE, 20),
(16, (SELECT id_musculo FROM musculos WHERE nombre='Abductores'), 'Estabilización lateral.', FALSE, 10);

-- ----------------------------------------
-- Abdominales en máquina
-- ----------------------------------------
INSERT INTO ejercicios VALUES
(17, 'Crunch sentado en máquina',  'Flexión del tronco para trabajar abdominales.', '/opt/ejerciciosgym/imagenes/17-abdominales_en_maquina.jpg', 9, 113);

INSERT INTO ejercicio_musculos VALUES
(17, (SELECT id_musculo FROM musculos WHERE nombre='Abdominales'), 'Activación principal del abdomen.', TRUE, 70),
(17, (SELECT id_musculo FROM musculos WHERE nombre='Oblicuos'), 'Apoyo en la flexión del tronco.', TRUE, 20),
(17, (SELECT id_musculo FROM musculos WHERE nombre='Lumbares'), 'Estabilización del tronco.', FALSE, 10);
