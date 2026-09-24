# Kinetix

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![JavaFX](https://img.shields.io/badge/JavaFX-22-blue?style=for-the-badge)
![Supabase](https://img.shields.io/badge/Supabase-DB-3ECF8E?style=for-the-badge&logo=supabase)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven)

Aplicación de gestión deportiva con enfoque móvil (Mobile-First) desarrollada en escritorio mediante JavaFX. Diseñada bajo principios de economía conductual y psicología del usuario para reducir la fricción cognitiva, Kinetix ofrece a los miembros del **Gimnasio Leal** una experiencia fluida, directa y motivadora.

## 🚀 Características Principales

La interfaz utiliza una paleta de colores de bajo estrés visual (Negro, Gris oscuro) con acentos en tonos café/bronce para destacar las acciones principales (Core Actions). La navegación se centraliza en una barra inferior (Bottom Navigation Bar) que intercambia dinámicamente las vistas sin recargar la ventana principal, dividiéndose en tres módulos clave:

*   🏋️ **Tu Día:** Vista inmediata de la rutina diaria asignada. Elimina la sobrecarga de información mostrando únicamente el enfoque del entrenamiento actual y un botón de acción para iniciar la rutina.
*   📈 **Avance:** Seguimiento estadístico del progreso del usuario. Visualización de métricas, asistencia y marcas personales para activar el circuito de recompensa visual.
*   📅 **Agenda:** Gestión de clases y reservas. Visualización de horarios disponibles e inscripción directa a clases del gimnasio.

## 🛠️ Arquitectura y Tecnologías

El proyecto sigue un patrón de diseño **MVC (Modelo-Vista-Controlador)** y utiliza el sistema de módulos de Java.

*   **Lenguaje:** Java 21
*   **Interfaz Gráfica:** JavaFX 22 (Vistas diseñadas en FXML con Scene Builder)
*   **Persistencia de Datos:** PostgreSQL alojado en Supabase, consumido vía API REST.
*   **Cliente HTTP:** `java.net.http.HttpClient` nativo (implementación asíncrona vía `sendAsync` para proteger el hilo de la UI).
*   **Mapeo JSON:** FasterXML Jackson (`com.fasterxml.jackson.databind`).
*   **Gestor de Dependencias:** Apache Maven.

## 📁 Estructura del Proyecto

```text
src/main/java/com.gymManage
│
├── config
│   └── DBConnection.java       # Singleton HttpClient, centraliza URLs y API Keys. Peticiones asíncronas REST.
├── controller
│   ├── MainController.java     # Enrutador de vistas dinámicas en StackPane central.
│   ├── CrearUsuarioController.java # Validación segura y registro de usuarios en DB.
│   ├── LoginController.java    # Gestión de autenticación.
│   └── ...                     # Controladores individuales para TuDiaView, AvanceView, AgendaView.
├── dao
│   └── UsuarioDAO.java         # Data Access Object para operaciones CRUD.
├── model
│   ├── Persona.java            # POJO genérico base.
│   ├── Usuario.java            # Mapeado por Jackson, incluye credenciales y rol.
│   ├── Rutina.java             # Entidad de entrenamiento (soporta JSONB para listas de ejercicios).
│   └── Clase.java              # Entidad de agenda de sesiones grupales.
├── service
│   └── PersonaService.java     # Capa de lógica de negocio que conecta el DAO con los Controladores.
└── Main.java                   # Punto de entrada de la aplicación (extiende javafx.application.Application).

src/main/resources/com/gymManage/ui
└── ...                         # Archivos .fxml de las distintas vistas (MainView, TuDiaView, etc.)
