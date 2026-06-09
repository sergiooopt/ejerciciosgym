# ejerciciosgym — AGENTS.md

Proyecto DAM: 3 componentes. Instrucciones para agentes de IA.

- `ejerciciosgym/` — App Flutter (Dart, Provider, shared_preferences)
- `adminejerciciosgym/` — Cliente Swing (Maven, Java 21, Jackson, JavaHelp)
- `wsejerciciosgym/` — API REST Jakarta EE 10 (WAR, TomEE, MariaDB)
- `docs/` — Docker, SQL, builds, imágenes, manual de usuario

---

## wsejerciciosgym — API REST

**Contexto:** `http://<host>:8080/wsejerciciosgym/api/` — TomEE jre21-webprofile, puerto 8080.

### Controladores (20 endpoints)

| Controlador | Ruta | Endpoints |
|---|---|---|
| `EjercicioController` | `/ejercicios` | GET, GET/{id}, POST, PUT/{id}, DELETE/{id}, GET/{id}/imagen, POST/{id}/imagen, DELETE/{id}/imagen |
| `MusculoController` | `/musculos` | GET, GET/{id}, POST, PUT/{id}, DELETE/{id} |
| `EjercicioMusculoController` | `/ejercicio_musculos` | GET, GET/{idEjercicio}, GET/{idEjercicio}/{idMusculo}, POST, PUT/{idEjercicio}/{idMusculo}, DELETE/{idEjercicio}, DELETE/{idEjercicio}/{idMusculo} |

### Modelos (JSON-B, snake_case)

| Clase | Atributos JSON |
|---|---|
| `Ejercicio` | `id_ejercicio`, `nombre`, `descripcion`, `ruta_imagen`, `peso_minimo`, `peso_maximo` |
| `Musculo` | `id_musculo`, `nombre`, `descripcion`, `zona`, `grupo` |
| `EjercicioMusculo` | `id_ejercicio`, `id_musculo`, `descripcion`, `es_directo`, `porcentaje_activacion` |

**Notas:**
- `Musculo.toString()` devuelve `nombre` (para `JComboBox` en Swing).
- Las rutas de imagen en BD son **relativas** (ej: `1-press_banca.jpg`), no absolutas.

### DAOs

- `Conexion.java` — singleton con DataSource JNDI `jdbc/ejerciciosgym-ds`.
- `EjercicioDao.java`, `MusculoDao.java`, `EjercicioMusculoDao.java` — CRUD completo.
- Los delete de `EjercicioDao` y `MusculoDao` borran manualmente las filas en `ejercicio_musculos` (con transacción) antes del registro padre, en lugar de depender de ON DELETE CASCADE.
- Capturan excepciones SQL silenciosamente y devuelven listas vacías.

### BD

**MariaDB** — BD: `bdejerciciosgym`, usuario: `ejerciciosgym` / `Abcd1234.`

```
ejercicios (id_ejercicio UNSIGNED AUTO_INCREMENT PK, nombre VARCHAR(90), descripcion TEXT, ruta_imagen VARCHAR(180), peso_minimo UNSIGNED NULL, peso_maximo UNSIGNED NULL)
musculos (id_musculo UNSIGNED AUTO_INCREMENT PK, nombre VARCHAR(90), descripcion TEXT, zona ENUM('SUPERIOR','INFERIOR','CORE'), grupo ENUM('PECHO','ESPALDA','HOMBRO','BRAZO','LUMBARES','ABDOMINALES','PIERNA'))
ejercicio_musculos (id_ejercicio UNSIGNED, id_musculo UNSIGNED, descripcion TEXT, es_directo BOOLEAN, porcentaje_activacion INT, PK(id_ejercicio,id_musculo), FK CASCADE)
```

**Precarga:** 21 músculos, 17 ejercicios (3 músculos c/u, 70/20/10 activación), 17 imágenes `docs/imagenes/{id}-{slug}.jpg`.

### Despliegue

```bash
mvn clean package && cp target/wsejerciciosgym.war /opt/ejerciciosgym/tomee/
```

**context.xml:** DataSource `jdbc:mariadb://mariadb:3306/bdejerciciosgym`. Hostname `mariadb` resuelve en Docker; cambiar para nativo.

### Gestión de imágenes

- `RUTA_IMAGEN = "/opt/ejerciciosgym/imagenes/"` en `EjercicioController`.
- BD guarda ruta **relativa**; el controller construye la absoluta.
- Patrón: `{id}-{slug}.jpg`. Slug: nombre en minúsculas sin acentos, espacios → `_`.

---

## adminejerciciosgym — Cliente Swing

**Entrada:** `es.sergiopt.formulario.FormularioPrincipal` (`main()`).

### Compilación y ejecución

```bash
mvn clean package                          # target/adminejerciciosgym-jar-with-dependencies.jar
java -jar target/adminejerciciosgym-jar-with-dependencies.jar          # localhost
java -Dhost=192.168.1.100 -jar target/adminejerciciosgym-jar-with-dependencies.jar
```

### Dependencias

- Jackson Databind 2.17 (`PropertyNamingStrategies.SNAKE_CASE`)
- JavaHelp 2.0.05

### Ventanas

| Clase | Tipo | Descripción |
|---|---|---|
| `FormularioPrincipal` | `JFrame` (900×600) | Principal: formulario ejercicio, músculos involucrados, tabla ejercicios, detalles |
| `FormularioMusculos` | `JDialog` modal (640×360) | Gestión catálogo músculos (menú Editar > Editar músculos, Ctrl+Alt+E) |

### Servicios (todos static, HTTP)

| Clase | Llamadas API |
|---|---|
| `EjercicioService` | CRUD ejercicios + imagen |
| `MusculoService` | CRUD músculos |
| `EjercicioMusculosService` | CRUD asociaciones (con update y delete individual) |
| `Constantes` | `API = "http://" + host + ":8080/wsejerciciosgym/api/"`, host por defecto `"localhost"` |

### Ayuda (JavaHelp)

`adminejerciciosgym/src/main/resources/ayuda/`: `helpset.hs`, `toc.xml`, `map.jhm`, `contenido/` (10 HTML + estilos.css). Menú Ayuda > Sobre (Ctrl+Alt+S).

### Notas

- `recargarEjercicios()` usa `tableModel.setRowCount(1)` — mantiene fila vacía en índice 0.
- `FormularioMusculos` refresca el `JComboBox` de músculos al cerrarse.
- `comprobarError()` acepta códigos 200 y 204.
- Tabla ejercicios: 3 columnas (id, nombre, ruta_imagen).
- `JFileChooser` para imágenes filtrado solo a `.jpg`/`.jpeg` (sin "Todos los archivos").

---

## ejerciciosgym — App Flutter

**Entrada:** `lib/main.dart` → `runApp(const Providers())`.

### Dependencias

- `provider` 6.1.5, `http` 1.6.0, `shared_preferences` 2.5.5

### Providers

| Provider | Estado | Métodos |
|---|---|---|
| `ConfiguracionProvider` | `_servidor` (default `"10.0.2.2"`) | `setIpServidor(String)` — persiste en SharedPreferences |
| `EjerciciosProvider` | Sin estado local (retorna `Future`s) | `cargarEjercicios()`, `cargarEjerciciosPorNombre()`, `cargarMusculoPorId()`, `cargarMusculosInvolucrados()` |

### Pantallas

| Ruta | Descripción |
|---|---|
| `HomeScreen` | Inicio con logo + Drawer |
| `ListaEjerciciosScreen` | ListView con imágenes, navega a detalle |
| `InformacionEjercicioScreen` | Imagen, descripción, músculos con barras de progreso |
| `ConfiguracionScreen` | Formulario para cambiar IP del servidor |

### Widgets

- `ElementoEjercicioWidget` — tarjeta con `FadeInImage`
- `ElementoMusculoWidget` — músculo con indicador de activación, zona/grupo y descripción
- `ErrorEjerciciosWidget` — estado de error con icono; para `ClientException` muestra mensaje personalizado de conexión
- `EjerciciosSearchDelegate` — búsqueda por nombre

### Compilación

```bash
flutter build apk
# Sin tests (carpeta test/ vacía)
```

---

## Docker

### docker-compose.yml

```yaml
mariadb: image: mariadb:latest, ports: [3306], volumes: [/opt/ejerciciosgym/mariadb:/var/lib/mysql], env: MYSQL_ROOT_PASSWORD=Abcd1234.
tomee: image: tomee:jre21-webprofile, ports: [8080], volumes: [/opt/ejerciciosgym/tomee:/usr/local/tomee/webapps, /opt/ejerciciosgym/imagenes:/opt/ejerciciosgym/imagenes], depends_on: [mariadb]
```

### Puesta en marcha

```bash
sudo mkdir -p /opt/ejerciciosgym/{imagenes,mariadb,tomee} && sudo chown -R $USER:$USER /opt/ejerciciosgym
cd docs && sudo docker compose up -d
sudo docker cp scripts mariadb-gym:/scripts
# Ejecutar scripts en orden: crear usuario root.sql → crear base de datos.sql → cargar ejercicios.sql
cp builds/wsejerciciosgym.war /opt/ejerciciosgym/tomee/ && sudo docker restart mariadb-gym tomee-gym
```

### Scripts SQL (`docs/scripts/`)

1. `crear usuario como root.sql` — crea usuario `ejerciciosgym`
2. `crear base de datos.sql` — tablas + 21 músculos
3. `cargar ejercicios.sql` — 17 ejercicios + asociaciones

### VM con reenvío de puertos

- 3306 (MariaDB) y 8080 (TomEE) anfitrión → invitado
- Swing: `-Dhost=<ip-anfitrion>`

---

## Preferencia del usuario

- El usuario **prefiere una explicación detallada de la solución** a que el agente implemente los cambios directamente.

---

## Convenciones

- **JSON snake_case:** servidor usa `@JsonbProperty`, Swing usa Jackson `SNAKE_CASE`, Flutter usa ambos en `fromMap()`.
- **Hostname BD:** `mariadb` resuelve solo en Docker. Cambiar para nativo.
- **DAOs silenciosos:** si BD falla, API retorna `200` con `[]`.
- **Prebuilt:** `docs/builds/` contiene WAR y fat JAR listos para desplegar.
