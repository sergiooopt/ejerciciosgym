# ejerciciosgym — AGENTS.md

Proyecto de fin de ciclo DAM: aplicación de ejercicios de gimnasio con 3 componentes.
Archivo local (`.gitignore`) con instrucciones para agentes de IA.

---

## Estructura del repositorio

```
ejerciciosgym/              App móvil Flutter (Dart, Provider)
adminejerciciosgym/         Cliente administrador Java Swing (Maven, Java 21)
wsejerciciosgym/            API REST Jakarta EE 10 (WAR, TomEE, MariaDB)
docs/                       Docker Compose, scripts SQL, builds precompilados, imágenes, manual de usuario
```

---

## wsejerciciosgym — API REST

**Punto de entrada:** `JakartaRestConfig` (`@ApplicationPath("/api")`), auto-descubrimiento por TomEE.  
**Servidor:** Apache TomEE jre21-webprofile, puerto `8080`.  
**Contexto completo:** `http://<host>:8080/wsejerciciosgym/api/`

### Controladores JAX-RS 3.1

| Controlador | Ruta | Endpoints |
|---|---|---|
| `EjercicioController` | `/ejercicios` | GET, GET/{id}, POST, PUT/{id}, DELETE/{id}, GET/{id}/imagen, POST/{id}/imagen, DELETE/{id}/imagen |
| `MusculoController` | `/musculos` | GET, GET/{id}, POST, PUT/{id}, DELETE/{id} |
| `EjercicioMusculoController` | `/ejercicio_musculos` | GET, GET/{idEjercicio}, GET/{idEjercicio}/{idMusculo}, POST, PUT/{idEjercicio}/{idMusculo}, DELETE/{idEjercicio}, DELETE/{idEjercicio}/{idMusculo} |

**Total: 20 endpoints REST.**

### Modelos (JSON-B, snake_case)

| Clase | Atributos JSON | Orden |
|---|---|---|
| `Ejercicio` | `id_ejercicio`, `nombre`, `descripcion`, `ruta_imagen`, `peso_minimo`, `peso_maximo` | explícito con `@JsonbPropertyOrder` |
| `Musculo` | `id_musculo`, `nombre`, `descripcion`, `zona`, `grupo` | explícito |
| `EjercicioMusculo` | `id_ejercicio`, `id_musculo`, `descripcion`, `es_directo`, `porcentaje_activacion` | explícito |

**Importante:** `Musculo.toString()` devuelve `nombre` (para `JComboBox` en Swing).

### DAOs (JDBC plano con JNDI)

- `Conexion.java` — singleton que obtiene conexión del DataSource JNDI `jdbc/ejerciciosgym-ds`.
- `EjercicioDao.java`, `MusculoDao.java`, `EjercicioMusculoDao.java` — CRUD completo.
- Los DAOs **capturan excepciones SQL silenciosamente** y devuelven listas vacías.

### Base de datos

**SGBD:** MariaDB, BD: `bdejerciciosgym`, usuario: `ejerciciosgym` / `Abcd1234.`

**Tablas:**

```sql
ejercicios (id_ejercicio INT UNSIGNED AUTO_INCREMENT PK,
            nombre VARCHAR(90) NOT NULL,
            descripcion TEXT NOT NULL,
            ruta_imagen VARCHAR(180) NOT NULL,
            peso_minimo INT UNSIGNED NULL,
            peso_maximo INT UNSIGNED NULL)

musculos (id_musculo INT UNSIGNED AUTO_INCREMENT PK,
          nombre VARCHAR(90) NOT NULL,
          descripcion TEXT NOT NULL,
          zona ENUM('SUPERIOR','INFERIOR','CORE') NOT NULL,
          grupo ENUM('PECHO','ESPALDA','HOMBRO','BRAZO','LUMBARES','ABDOMINALES','PIERNA') NOT NULL)

ejercicio_musculos (id_ejercicio INT UNSIGNED NOT NULL,
                    id_musculo INT UNSIGNED NOT NULL,
                    descripcion TEXT NOT NULL,
                    es_directo BOOLEAN NOT NULL,
                    porcentaje_activacion INT NOT NULL,
                    PK(id_ejercicio, id_musculo),
                    FK(id_ejercicio) -> ejercicios(id_ejercicio) CASCADE,
                    FK(id_musculo) -> musculos(id_musculo) CASCADE)
```

### Datos precargados

- **21 músculos**: PECHO (3), ESPALDA (3), HOMBRO (3), BRAZO (3), LUMBARES (1), ABDOMINALES (2), PIERNA (6).
- **17 ejercicios** con 3 músculos cada uno (70/20/10 de activación).
- **17 imágenes** en `docs/imagenes/` con patrón `{id}-{slug}.jpg`.

### Despliegue

```bash
cd wsejerciciosgym && mvn clean package          # → target/wsejerciciosgym.war
cp target/wsejerciciosgym.war /opt/ejerciciosgym/tomee/
```

**context.xml:** DataSource apunta a `jdbc:mariadb://mariadb:3306/bdejerciciosgym`. El hostname `mariadb` es el nombre del servicio Docker. Si no se usa Docker, cambiar la URL.

---

## adminejerciciosgym — Cliente Swing

**Punto de entrada:** `es.sergiopt.formulario.FormularioPrincipal` (tiene `main()`).

### Compilación y ejecución

```bash
cd adminejerciciosgym && mvn clean package
# → target/adminejerciciosgym-jar-with-dependencies.jar (fat JAR)

# Por defecto: localhost
java -jar target/adminejerciciosgym-jar-with-dependencies.jar

# IP personalizada:
java -Dhost=192.168.1.100 -jar target/adminejerciciosgym-jar-with-dependencies.jar

# En NetBeans: Project Properties → Run → VM Options → -Dhost=<vm-ip>
```

### Dependencias

- Jackson Databind 2.17 (`PropertyNamingStrategies.SNAKE_CASE`)
- JavaHelp 2.0.05

### Ventanas

| Clase | Tipo | Descripción |
|---|---|---|
| `FormularioPrincipal` | `JFrame` (900×600) | Ventana principal con 4 secciones: formulario de ejercicio, músculos involucrados, tabla de ejercicios, detalles |
| `FormularioMusculos` | `JDialog` modal (640×360) | Gestión del catálogo de músculos (menú Editar > Editar músculos, Ctrl+Alt+E) |

### Servicios (todos static, comunicación HTTP)

| Clase | Llamadas a la API |
|---|---|
| `EjercicioService` | CRUD ejercicios + imagen (GET, POST, PUT, DELETE) |
| `MusculoService` | CRUD músculos |
| `EjercicioMusculosService` | CRUD asociaciones ejercicio-músculo |
| `Constantes` | `API = "http://" + host + ":8080/wsejerciciosgym/api/"` |

### Ayuda integrada (JavaHelp)

Archivos fuente en `adminejerciciosgym/src/main/resources/ayuda/`:
- `helpset.hs` — descriptor del sistema de ayuda
- `toc.xml` — índice de contenidos
- `map.jhm` — mapa de temas a páginas HTML
- `contenido/` — 8 páginas HTML + `estilos.css`

Se abre desde menú Ayuda > Sobre (Ctrl+Alt+S).

### Quirks

- `recargarEjercicios()` usa `tableModel.setRowCount(1)` — mantiene una fila vacía en índice 0. La tabla muestra 1 fila en blanco + datos.
- `FormularioMusculos` refresca el `JComboBox` de músculos al cerrarse.

---

## ejerciciosgym — App Flutter

**Punto de entrada:** `lib/main.dart` → `runApp(const Providers())`.

### Dependencias principales

- `provider` 6.1.5 — estado con `ChangeNotifier`
- `http` 1.6.0 — cliente HTTP

### Providers

| Provider | Estado | Métodos principales |
|---|---|---|
| `ConfiguracionProvider` | `_servidor` (String, default `"10.0.2.2"`) | `setServidor(String)` |
| `EjerciciosProvider` | Sin estado local (retorna `Future`s) | `cargarEjercicios()`, `cargarEjerciciosPorNombre()`, `cargarMusculoPorId()`, `cargarMusculosInvolucrados()` |

### Pantallas (rutas nombradas)

| Ruta | Pantalla | Descripción |
|---|---|---|
| `HomeScreen` | Inicio | Logo + Drawer con navegación |
| `ListaEjerciciosScreen` | Lista de ejercicios | `ListView` con imágenes, navega a detalle |
| `InformacionEjercicioScreen` | Detalle del ejercicio | Imagen, descripción, músculos con barras de progreso |
| `ConfiguracionScreen` | Configuración | Formulario para cambiar IP del servidor |

### Widgets reutilizables

- `ElementoEjercicioWidget` — tarjeta con `FadeInImage` y nombre
- `ElementoMusculoWidget` — músculo con indicador de activación
- `ErrorEjerciciosWidget` — estado de error con icono
- `EjerciciosSearchDelegate` — búsqueda de ejercicios por nombre

### Compilación

```bash
cd ejerciciosgym && flutter build apk
# Otras plataformas: flutter build ios/web/linux/macos/windows
# No existen tests (carpeta test/ vacía)
```

---

## Entorno de desarrollo (Docker)

### docker-compose.yml (`docs/docker-compose.yml`)

```yaml
mariadb:
  image: mariadb:latest
  container_name: mariadb-gym
  ports: ["3306:3306"]
  volumes: ["/opt/ejerciciosgym/mariadb:/var/lib/mysql"]
  environment: { MYSQL_ROOT_PASSWORD: Abcd1234. }
  restart: unless-stopped

tomee:
  image: tomee:jre21-webprofile
  container_name: tomee-gym
  ports: ["8080:8080"]
  volumes:
    - "/opt/ejerciciosgym/tomee:/usr/local/tomee/webapps"
    - "/opt/ejerciciosgym/imagenes:/opt/ejerciciosgym/imagenes"
  depends_on: [mariadb]
  restart: unless-stopped
```

### Puesta en marcha

```bash
# Requisito: docs/ en /home/usuario
sudo mkdir -p /opt/ejerciciosgym/{imagenes,mariadb,tomee}
sudo chown -R $USER:$USER /opt/ejerciciosgym

cd docs
sudo docker compose up -d

# Cargar BD
sudo docker cp scripts mariadb-gym:/scripts
sudo docker exec -it mariadb-gym bash
mariadb -u root -p < /scripts/crear\ usuario\ como\ root.sql   # pw: Abcd1234.
mariadb -u ejerciciosgym -p < /scripts/crear\ base\ de\ datos.sql
exit

# Desplegar WAR
cp builds/wsejerciciosgym.war /opt/ejerciciosgym/tomee/
sudo docker restart mariadb-gym tomee-gym
```

### Scripts SQL (`docs/scripts/`)

Ejecutar en orden:
1. `crear usuario como root.sql` — crea usuario `ejerciciosgym`
2. `crear base de datos.sql` — tablas + 21 músculos
3. **`cargar ejercicios.sql`** — 17 ejercicios + asociaciones. **No incluido en script #2, ejecutar manualmente.**

### VM con reenvío de puertos

Cuando el servidor está en una máquina virtual (desde Windows/NetBeans):
- Reenviar puerto anfitrión `3306` → invitado `3306` (MariaDB)
- Reenviar puerto anfitrión `8080` → invitado `8080` (TomEE)
- Cliente Swing: `-Dhost=<ip-anfitrion>` (o `localhost` si funciona reenvío en anfitrión)

---

## Convenciones y notas importantes

- **JSON snake_case:** servidor usa JSON-B (`@JsonbProperty`). Swing usa Jackson `PropertyNamingStrategies.SNAKE_CASE`. Flutter lee ambos formatos en `fromMap()`.
- **Hostname BD:** `mariadb` en `context.xml` resuelve solo dentro de la red Docker. Cambiar para despliegues nativos.
- **Imágenes:** almacenamiento en `/opt/ejerciciosgym/imagenes/`, patrón `{id}-{slug}.jpg`.
- **DAOs silenciosos:** si la BD falla, API retorna `200` con `[]`. La tabla Swing muestra fila vacía sin error.
- **Prebuilt:** `docs/builds/` contiene WAR y fat JAR listos para desplegar sin recompilar.

---

## Historial y resolución de tareas

Este archivo describe el estado actual del proyecto. Al resolver tareas, actualizar la información relevante aquí para mantener la coherencia con el agente.
