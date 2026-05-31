# Evaluación Parcial - Java Persistence API (JPA)

Descripción breve
- Proyecto de ejemplo para gestionar categorías y productos usando JPA (Hibernate) y base de datos H2 en modo archivo. Contiene una consola interactiva con la clase principal `com.tp.jpa.Main` y repositorios simples para `Categoria` y `Producto`.

Requisitos
- JDK 17 o superior.
- Gradle wrapper incluido (`gradlew`, `gradlew.bat`).

Instrucciones para ejecutarlo

1) Desde un IDE (recomendado)
- Abra el proyecto en su IDE (IntelliJ IDEA, Eclipse o VS Code con soporte Java).
- Ejecute la clase `com.tp.jpa.Main` como una aplicación Java.

2) Desde línea de comandos (Windows)
- Compilar:

```powershell
.\gradlew.bat clean build
```

- Ejecutar (opción A — desde clases compiladas):

```powershell
java -cp build\classes\java\main;build\resources\main;libs\* com.tp.jpa.Main
```

- Ejecutar (opción B — agregar plugin `application` si desea usar `gradlew run`). Si lo prefiere, puedo agregar esa tarea para facilitar la ejecución.

Base de datos (H2)
- Archivo de configuración: [src/main/resources/META-INF/persistence.xml](src/main/resources/META-INF/persistence.xml#L1-L40).
- URL por defecto actual: `jdbc:h2:file:~/data/mydb;AUTO_SERVER=true`.
	- Nota: `~` se resuelve al directorio home del usuario (por ejemplo `C:\Users\gabri\data\mydb.*`). Si usted espera usar los archivos `data/mydb.*` dentro del proyecto, H2 no los encontrará mientras la URL use `~`.
- Opciones:
	- Usar la carpeta `data` del proyecto: cambiar la URL a `jdbc:h2:file:./data/mydb`.
	- Usar ruta absoluta: `jdbc:h2:file:C:/ruta/completa/a/proyecto/data/mydb`.
- Archivo `.lock.db`: si aparece `data/mydb.lock.db` significa que otra instancia tiene la DB abierta; cierre otras instancias (IDE, servidor o procesos H2) antes de ejecutar.

Depuración y logs
- Para ver las sentencias SQL en consola, en `persistence.xml` cambie `hibernate.show_sql` a `true`.

Archivos importantes
- `src/main/java/com/tp/jpa/Main.java`: punto de entrada de la aplicación.
- `src/main/resources/META-INF/persistence.xml`: configuración de JPA/Hibernate y URL de conexión.

Recomendaciones finales
- Si desea, puedo:
	- Actualizar `persistence.xml` para apuntar por defecto a `./data/mydb` (usa la carpeta del proyecto).
	- Agregar el plugin `application` en `build.gradle` para permitir `gradlew run`.
	- Generar un `fat-jar` ejecutable.

Materia: Programación III
Estudiante: Etchegoyen Gabriel
Legajo: 18756

