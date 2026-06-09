# ejerciciosgym

Proyecto de fin de ciclo de **Desarrollo de Aplicaciones Multiplataforma**.  
Sistema para la gestión de ejercicios de gimnasio mediante una API REST.

## Estructura del proyecto

```text
ejerciciosgym/
│
├── adminejerciciosgym/   [DESKTOP] Aplicación de administración desarrollada con Java Swing
├── docs/                 [DOC] Documentación, servicio web, scripts y entorno de docker
├── ejerciciosgym/        [MOBILE] Aplicación móvil desarrollada con Flutter
└── wsejerciciosgym/      [BACKEND] API REST desarrollada con Jakarta EE y JDBC
```

## Entorno de desarrollo

[Guía de instalación del entorno de desarrollo](docs/configurar%20servidor.md).

La aplicación Java Swing permite pasar como parámetro de la máquina virtual la IP del servidor:

```bash
java -Dhost=127.0.0.1 -jar docs/builds/adminejerciciosgym-jar-with-dependencies.jar
```

La aplicación móvil tiene un apartado de configuración donde se puede cambiar la dirección IP del servidor.

## Compilación

Las aplicaciones Java están preparadas para usar Java 21.

Para compilar la aplicación Java Swing debemos tener instalado **Maven**. Ejecutamos en la terminal:

```
cd adminejerciciosgym
mvn clean install
```

Para compilar la aplicación móvil debemos tener instalado el framework **Flutter** con sus dependencias. Ejecutamos en la terminal:

```
cd ejerciciosgym
flutter build apk
```

## Uso de IA

Se ha usado OpenCode con DeepSeek V4 Flash en modo plan y GPT-5.5 Instant para resolver dudas y errores.

Se incluye el archivo [AGENTS.md](AGENTS.md) para mostrar conocimientos básicos en IA.

## Licencia

MIT - ver [LICENSE](LICENSE) para más detalles.
