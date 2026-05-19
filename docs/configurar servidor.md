# Configurar Servidor Debian

Esta guía es sobre los pasos que hacer para preparar el entorno de servidor para la aplicación `ejerciciosgym`. 

Si el entorno ya esta preparado podemos omitir los primeros 6 pasos.

## 1. Crear directorio de la aplicación

Creamos el directorio:

```bash
sudo mkdir /opt/ejerciciosgym
sudo mkdir /opt/ejerciciosgym/imagenes
sudo chown -R administrador:administrador /opt/ejerciciosgym
```

## 2. Instalar MariaDB

Instalamos MariaDB desde los repositorios:

```bash
sudo apt install mariadb-server
```

## 3. Configurar MariaDB

Cargamos el script `crear usuario como root.sql`:

```bash
sudo mysql -u root < "crear usuario como root.sql"
```

Cargamos el script `crear base de datos.sql`:

```bash
mysql -u ejerciciosgym -p < "crear base de datos.sql"
```


Para permitir acceso desde fuera de localhost modificamos:

```bash
sudo nano /etc/mysql/mariadb.conf.d/50-server.cnf
```

Cambiamos `bin-address: 127.0.0.1` por `bind-address: 0.0.0.0`.

Reiniciamos el servicio:

```bash
sudo systemctl restart mariadb
```

## 4. Descargar TomEE

Instalamos TomEE desde `https://www.apache.org/dyn/closer.cgi/tomee/tomee-10.1.5/apache-tomee-10.1.5-plume.tar.gz`.

Descomprimimos TomEE:

```bash
tar -xzf Descargas/apache-tomee-10.1.4-plume.tar.gz
mv Descargas/apache-tomee-10.1.5-plume tomee
```

## 5. Reenvio de puertos

<p style="color:red;">¡Si el servidor está alojado en una máquina en Virtual Box!</p>

Configuramos la salida de los puertos para Mariadb y TomEE.

* Asignaríamos para MariaDB: Anfitrión 0.0.0.0:3306 <-> Invitado :3306
* Asignaríamos para TomEE: Anfitrión 0.0.0.0:8080 <-> Invitado :8080

## 6. Preparar servicio web

Antes de levantar la aplicación necesitamos instalar Java:

```bash
sudo apt install default-jdk
```

Movemos el archivo `wsejerciciosgym.war` a /home/administrador/apache-tomee-plume-10.1.4/webapps.

## 7. Levantar aplicación

La base de datos ya está como servicio, asi que unicamente tendremos que iniciar TomEE (contiene el servicio web).

Para levantar TomEE desde terminal:

```bash
/home/administrador/apache-tomee-plume-10.1.4/bin/startup.sh
```