# Evaluación Parcial - Java Persistence API (JPA)

Descripción breve
- Proyecto de ejemplo para gestionar categorías y productos usando JPA (Hibernate) y base de datos H2 en modo archivo. Contiene una consola interactiva con la clase principal `com.tp.jpa.Main` y repositorios simples para `Categoria` y `Producto`.

Requisitos
- JDK 17 o superior.
- Gradle wrapper incluido (`gradlew`, `gradlew.bat`).

Instrucciones para ejecutarlo

Desde un IDE (recomendado)
- Abra el proyecto en su IDE (IntelliJ IDEA, Eclipse o VS Code con soporte Java).
- Ejecute la clase `com.tp.jpa.Main` como una aplicación Java.


Base de datos (H2)
- Archivo de configuración: [src/main/resources/META-INF/persistence.xml](src/main/resources/META-INF/persistence.xml#L1-L40).
- URL por defecto actual: `jdbc:h2:file:~/data/mydb;AUTO_SERVER=true`.
	- Nota: `~` se resuelve al directorio home del usuario (por ejemplo `C:\Users\gabri\data\mydb.*`). Si usted espera usar los archivos `data/mydb.*` dentro del proyecto, H2 no los encontrará mientras la URL use `~`.
- Opciones:
	- Usar la carpeta `data` del proyecto: cambiar la URL a `jdbc:h2:file:./data/mydb`.
	- Usar ruta absoluta: `jdbc:h2:file:C:/ruta/completa/a/proyecto/data/mydb`.


Archivos importantes
- `src/main/java/com/tp/jpa/Main.java`: punto de entrada de la aplicación.
- `src/main/resources/META-INF/persistence.xml`: configuración de JPA/Hibernate y URL de conexión.



Materia: Programación III
Estudiante: Etchegoyen Gabriel
Legajo: 18756

