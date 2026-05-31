````md
# Evaluación Parcial – Java Persistence API (JPA)

**Materia:** Programación III  
**Estudiante:** Gabriel Etchegoyen  
**Legajo:** 18756

## Introducción

El presente proyecto fue desarrollado utilizando **Java Persistence API (JPA)** con **Hibernate** como proveedor de persistencia y **H2 Database** como sistema de almacenamiento de datos. La aplicación permite administrar categorías y productos mediante una interfaz de consola, implementando operaciones básicas de persistencia y consulta.

## Requisitos

Para ejecutar el proyecto se requiere:

- JDK 17 o superior.
- Gradle Wrapper incluido en el proyecto.
- Un entorno de desarrollo compatible con Java (IntelliJ IDEA, Eclipse o Visual Studio Code).

## Ejecución del Proyecto

Se recomienda abrir el proyecto en un entorno de desarrollo integrado (IDE) y ejecutar la clase principal:

```java
com.tp.jpa.Main
````

## Configuración de la Base de Datos

La configuración de persistencia se encuentra en el archivo:

```text
src/main/resources/META-INF/persistence.xml
```

La aplicación utiliza una base de datos H2 en modo archivo. La URL de conexión configurada actualmente es:

```text
jdbc:h2:file:~/data/mydb;AUTO_SERVER=true
```

### Consideraciones

* El símbolo `~` representa el directorio personal del usuario que ejecuta la aplicación.
* Si se desea utilizar una base de datos almacenada dentro del proyecto, la URL puede modificarse para apuntar a la carpeta correspondiente.
* En caso de existir un archivo `.lock.db`, deberá verificarse que no haya otra instancia de la aplicación utilizando la misma base de datos.

## Depuración

Para visualizar las sentencias SQL generadas por Hibernate durante la ejecución, puede habilitarse la siguiente propiedad en el archivo `persistence.xml`:

```xml
hibernate.show_sql=true
```

## Estructura Principal del Proyecto

| Archivo                                       | Descripción                                                    |
| --------------------------------------------- | -------------------------------------------------------------- |
| `src/main/java/com/tp/jpa/Main.java`          | Punto de entrada de la aplicación.                             |
| `src/main/resources/META-INF/persistence.xml` | Configuración de JPA, Hibernate y conexión a la base de datos. |

## Funcionalidades Implementadas

* Gestión de categorías.
* Gestión de productos.
* Persistencia de datos mediante JPA e Hibernate.
* Almacenamiento utilizando H2 Database.
* Interacción mediante consola.

## Conclusión

Este proyecto permite aplicar los conceptos fundamentales de persistencia de datos en Java utilizando JPA y Hibernate, integrando una base de datos relacional y una estructura organizada de acceso a datos mediante repositorios.

```
```

