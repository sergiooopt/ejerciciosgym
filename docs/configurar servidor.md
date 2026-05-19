# Configurar Servidor Debian

Esta guía es sobre los pasos que se deben seguir para levantar el entorno de **ejerciciosgym**.

## 1. Crear directorios de la aplicación

Creamos los directorio:

```bash
$ sudo mkdir /opt/ejerciciosgym
$ sudo mkdir /opt/ejerciciosgym/imagenes
$ sudo mkdir /opt/ejerciciosgym/mariadb
$ sudo mkdir /opt/ejerciciosgym/tomee
$ sudo chown -R administrador:administrador /opt/ejerciciosgym
```
## 2. Instalar y levantar Docker

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

Copiamos **docs/** en **/home/administrador**.

Levantamos los servicios:

```bash
$ cd docs
$ docker compose up -d
```

## 3. Configurar servicios

Cargamos los scripts ubicados en **docs/scripts/** en MariaDB:

```bash
$ sudo docker cp docs mariadb-gym:/docs
$ sudo docker exec -it mariadb-gym bash
$ mariadb -u root -p < "/docs/scripts/crear usuario como root.sql"
$ mariadb -u ejerciciosgym -p < "/docs/scripts/crear base de datos.sql"
```

Modificamos **/etc/mysql/mariadb.conf.d/50-server.cnf**.

```
bind-address = 0.0.0.0
```

Copiamos **wsejerciciosgym.war** en **/opt/ejerciciosgym/tomee**.

Reiniciamos ambos servicios:

```bash
$ sudo docker restart mariadb-gym
$ sudo docker restart tomee-gym
```

## 4. Configurar reenvio de puertos

<p style="color:red;">¡Si el servidor está alojado en una máquina en Virtual Box!</p>

Configuramos la salida de los puertos para Mariadb y TomEE.

* Asignaríamos para MariaDB: Anfitrión 0.0.0.0:3306 <-> Invitado :3306
* Asignaríamos para TomEE: Anfitrión 0.0.0.0:8080 <-> Invitado :8080
