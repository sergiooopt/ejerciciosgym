# ejerciciosgym

Proyecto de fin de ciclo de **Desarrollo de Aplicaciones Multiplataforma**.  
Sistema para la gestión de ejercicios de gimnasio mediante una API REST.

## Estructura del proyecto

```text
ejerciciosgym/
│
├── adminejerciciosgym/   Aplicación de administración desarrollada con Java Swing
├── docs/                 Documentación, scripts y entorno de docker
├── ejerciciosgym/        Aplicación móvil desarrollada con Flutter
└── wsejerciciosgym/      API REST desarrollada con Jakarta EE y JDBC
```

## Entorno de desarrollo

El proyecto necesita de un servidor para funcionar, guía de instalación [aquí](docs/configurar%20servidor.md).

La aplicación Java Swing permite pasar como parámetro de la máquina virtual la IP del servidor:

```bash
java -Dhost=127.0.0.1 -jar  docs/builds/adminejerciciosgym-jar-with-dependencies.jar
```

La aplicación móvil tiene un apartado de configuración donde se puede cambiar la dirección IP del servidor.

## Compilación

Para compilar la aplicación Java Swing debemos tener instalado **Maven**. Hacemos desde terminal:

```
cd adminejerciciosgym
mvn clean install
```

Para compilar la aplicación móvil debemos tener instalado el framework **Flutter** con sus dependencias. Hacemos desde terminal:

```
cd ejerciciosgym
flutter build apk
```

## Uso de IA

Durante el desarrollo se usaron Opencode con Deepseek V4 Flash en modo plan y GPT-5.5 Instant para resolver dudas con excepción de la implementación de Java Help con el modo build de Opencode.

Se incluye el archivo [AGENTS.md](AGENTS.md) como forma de mostrar conocimientos básicos en IA.

## Licencia

MIT - ver [LICENSE](LICENSE) para más detalles.
