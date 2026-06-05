# Configurar Servidor Debian

Esta guía es sobre levantar el entorno de **ejerciciosgym**.

Debemos clonar el repositorio para acceder a la configuración y la compilación del servicio web:

```bash
git clone https://github.com/sergiooopt/ejerciciosgym.git
```

> Si nuestra máquina no tiene configurado **sudo** podemos elevar los privilegios con **su** pero debemos especificar nuestro usuario manualmente cuando establezcamos los permisos de los directorios.

### 1. Crear directorios de la aplicación

Creamos los directorios:

```bash
$ sudo mkdir -p /opt/ejerciciosgym/imagenes
$ sudo mkdir /opt/ejerciciosgym/mariadb
$ sudo mkdir /opt/ejerciciosgym/tomee
$ sudo chown -R $USER:$USER /opt/ejerciciosgym
```
### 2. Instalar y levantar Docker

Añadimos Docker a los repositorios:

```bash
$ sudo apt install ca-certificates curl
$ sudo install -m 0755 -d /etc/apt/keyrings
$ sudo curl -fsSL https://download.docker.com/linux/debian/gpg -o /etc/apt/keyrings/docker.asc
$ sudo tee /etc/apt/sources.list.d/docker.sources <<EOF
Types: deb
URIs: https://download.docker.com/linux/debian
Suites: $(. /etc/os-release && echo "$VERSION_CODENAME")
Components: stable
Architectures: $(dpkg --print-architecture)
Signed-By: /etc/apt/keyrings/docker.asc
EOF
```

Ahora lo instalamos:

```bash
$ sudo apt update
$ sudo apt install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```

Levantamos los servicios:

```bash
$ cd docs
$ sudo docker compose up -d
```

Comprobamos que se han levantado con **sudo docker ps**. Deberíamos ver:

```bash
CONTAINER ID   IMAGE                    COMMAND                  CREATED          STATUS          PORTS                                         NAMES
d3e6ffa3014c   tomee:jre21-webprofile   "/__cacert_entrypoin…"   52 seconds ago   Up 52 seconds   0.0.0.0:8080->8080/tcp, [::]:8080->8080/tcp   tomee-gym
5f29cf0cce4b   mariadb:latest           "docker-entrypoint.s…"   53 seconds ago   Up 52 seconds   0.0.0.0:3306->3306/tcp, [::]:3306->3306/tcp   mariadb-gym

```

### 3. Configurar servicios

Cargamos los scripts de la carpeta **scripts/** en MariaDB:

```bash
sudo docker cp scripts mariadb-gym:/scripts
```

Los ejecutamos manualmente dentro de MariaDB (contraseña: **Abcd1234.**):

```bash
$ sudo docker exec -it mariadb-gym bash
$ mariadb -u root -p < /scripts/crear\ usuario\ como\ root.sql
$ mariadb -u ejerciciosgym -p < /scripts/crear\ base\ de\ datos.sql
```

También dentro de MariaDB modificamos **/etc/mysql/mariadb.conf.d/50-server.cnf**.

```text
27: bind-address = 0.0.0.0
```

Si queremos cargar datos de prueba también debemos de añadir el script **cargar ejercicios.sql** (de la misma manera que el de crear base de datos) y mover el contenido de **imagenes** a **/opt/ejerciciosgym/imagenes**.

Copiamos **wsejerciciosgym.war** en **/opt/ejerciciosgym/tomee**.

Recargamos ambos servicios:

```bash
$ sudo docker restart mariadb-gym
$ sudo docker restart tomee-gym
```

### 4. Configurar reenvio de puertos

¡Realizar si el servidor está alojado en una máquina virtual!

Configuramos la salida de los puertos para MariaDB y TomEE:

* MariaDB: anfitrión 3306 invitado 3306
* TomEE: anfitrión 8080 invitado 8080
