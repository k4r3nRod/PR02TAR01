# Documentación: Flujo AJAX → Servlet → BD → Respuesta

## 1. Flujo General

1. **Usuario** interactúa con el formulario web (HTML + Bootstrap).
2. **JavaScript** (app.js) valida los datos y realiza una petición AJAX (fetch) al Servlet correspondiente.
3. **Servlet** recibe la petición, procesa los datos y accede a la base de datos MySQL (`chalet`, tabla `productos`) usando `ConexionBD`.
4. **Servlet** responde en formato JSON.
5. **JavaScript** recibe la respuesta y actualiza la interfaz (tabla, mensajes, etc).

---

## 2. Ejemplo de flujo: Insertar Producto

1. El usuario llena el formulario y pulsa **INGRESAR**.
2. JS valida los datos y envía un `fetch` POST a `InsertarProductoServlet`.
3. El Servlet toma los datos, los inserta en la tabla `productos` y responde con un JSON:
   ```json
   { "exito": true, "mensaje": "Producto insertado correctamente" }
   ```
4. JS muestra el mensaje y actualiza la tabla de productos.

---

## 3. Diagrama de flujo simplificado

```
[Usuario] → [JS: fetch] → [Servlet] → [BD MySQL]
     ↑             ↓           ↑
   [HTML] ← [JSON] ← [Respuesta]
```

---

## 4. Capturas de pantalla sugeridas

- Formulario y tabla en la web.
- Consola de red (Network) mostrando la petición AJAX.
- Respuesta JSON del Servlet.
- Vista de la tabla `productos` en MySQL.

---

## 5. Notas técnicas

- Todas las operaciones CRUD siguen el mismo flujo.
- Los Servlets devuelven siempre JSON para facilitar la integración con JS.
- El frontend es responsivo gracias a Bootstrap.
- La validación se realiza tanto en JS como en el backend.

---

# Cómo ejecutar el proyecto

1. **Configura la base de datos MySQL:**
   - Ejecuta el script `src/main/resources/crear_bd_productos.sql` en tu servidor MySQL para crear la base de datos `chalet` y la tabla `productos`.

2. **Configura el usuario y contraseña de MySQL:**
   - Edita la clase `ConexionBD.java` y ajusta las variables `USER` y `PASSWORD` según tu entorno.

3. **Compila el proyecto:**
   - Desde la raíz del proyecto, ejecuta:
     ```sh
     mvn clean install
     ```
   - Esto compilará el proyecto y generará el archivo WAR necesario para desplegar la aplicación.

4. **Compilación manual (opcional):**
   - O bien, puedes compilar manualmente los archivos Java con el siguiente comando (ajusta la ruta de Tomcat y el conector MySQL si es necesario):
     ```sh
     javac -cp "C:\src\apache-tomcat-9.0.108\lib\servlet-api.jar;C:\src\mysql-connector-j-9.4.0\mysql-connector-j-9.4.0.jar" -d target\classes src\main\java\com\mycompany\pr02tar01\*.java src\main\java\com\mycompany\pr02tar01\*.java
     ```

5. **Despliega el archivo WAR:**
   - El archivo WAR generado estará en `target/PR02TAR01-1.0-SNAPSHOT.war`.
   - Despliega este archivo en tu servidor de aplicaciones (por ejemplo, Apache Tomcat o Payara).

6. **Accede a la aplicación:**
   - Abre tu navegador y entra a:
     ```
     http://localhost:8080/PR02TAR01-1.0-SNAPSHOT/
     ```
   - (La ruta puede variar según el servidor y configuración)

7. **¡Listo!**
   - Ya puedes usar el CRUD de productos desde la interfaz web.

---

¿Problemas?
- Verifica el log del servidor para mensajes de error.
- Asegúrate de que el driver de MySQL esté disponible en el classpath del servidor si usas un contenedor que no lo incluye por defecto.

---

**Fin de la documentación básica.**
