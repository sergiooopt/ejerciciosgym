# ejerciciosgym

Proyecto de fin de ciclo de **Desarrollo de Aplicaciones Multiplataforma**.  
Sistema de gestión de ejercicios de gimnasio compuesto por tres componentes que se comunican a través de una API REST.

## Arquitectura general

```
┌───────────────────────┐     HTTP/JSON       ┌───────────────────┐
│   ejerciciosgym       │ ──────────────────→ │                   │
│   (Flutter mobile)    │                     │  wsejerciciosgym  │
│                       │ ←────────────────── │  (Jakarta EE 10)  │
│                       │    JSON + imágenes  │                   │
│                       │                     │       │           │
│                       │                     │  JDBC │           │
│                       │                     │       ↓           │
│                       │                     │  ┌──────────┐     │
│                       │                     │  │ MariaDB  │     │
│                       │                     │  └──────────┘     │
├───────────────────────┤                     └───────────────────┘
│ adminejerciciosgym    │     HTTP/JSON               ↑
│ (Java Swing desktop)  │ ────────────────────────────┘
│                       │
│ Ayuda integrada       │
│ (JavaHelp)            │
└───────────────────────┘
```

## Licencia

MIT — ver [LICENSE](LICENSE) para más detalles.
