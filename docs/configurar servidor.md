# Configurar Servidor Debian

Esta guía es sobre los pasos que se deben seguir para levantar el entorno de **ejerciciosgym**.

### 1. Crear directorios de la aplicación

Creamos los directorio:

```bash
$ sudo mkdir /opt/ejerciciosgym
$ sudo mkdir /opt/ejerciciosgym/imagenes
$ sudo mkdir /opt/ejerciciosgym/mariadb
$ sudo mkdir /opt/ejerciciosgym/tomee
$ sudo chown -R usuario:usuario /opt/ejerciciosgym
```
### 2. Instalar y levantar Docker

Instalamos Docker:

```bash
sudo apt install docker
sudo apt install docker-compose-plugin
```

Iniciamos Docker:

```bash
sudo systemctl start docker
sudo systemctl enable docker
```

Copiamos **docs/** en **/home/usuario**.

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

Los ejecutamos manualmente dentro de mariadb (contraseña: Abcd1234.):

```bash
$ sudo docker exec -it mariadb-gym bash
$ mariadb -u root -p < /scripts/crear\ usuario\ como\ root.sql
$ mariadb -u ejerciciosgym -p < /scripts/crear\ base\ de\ datos.sql
```

También dentro de mariadb modificamos **/etc/mysql/mariadb.conf.d/50-server.cnf**.

```
bind-address = 0.0.0.0
```

Copiamos **wsejerciciosgym.war** en **/opt/ejerciciosgym/tomee**.

Recargamos ambos servicios:

```bash
$ sudo docker compose down
$ sudo docker compose up -d
```

### 4. Configurar reenvio de puertos

<p style="color:red;">¡Realizar si el servidor está alojado en una máquina virtual!</p>

Configuramos la salida de los puertos para Mariadb y TomEE.

* Mariadb: anfitrión 3306 invitado 3306
* TomEE: anfitrión 8080 invitado 8080
