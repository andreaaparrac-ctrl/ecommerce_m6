### 💧 Mi Proyecto: Fuente de Vida - E-commerce de Agua Purificada

Este es el repositorio de **Fuente de Vida**, mi proyecto de e-commerce de agua purificada disenado para la comuna de Pelluhue. Este sistema viene de los modulos anteriores del programa, pero para este Modulo 6 hice la migracion completa a Spring Boot 3.x. Mi objetivo principal fue mantener la arquitectura base, incorporando seguridad real por roles con Spring Security, persistencia de datos en PostgreSQL y un control estricto de stock para asegurar el correcto funcionamiento del negocio.

### Mi Stack Tecnologico

*   **Backend:** Desarrollado y ejecutado con exito por mi en Java 25.
*   **Framework Principal:** Spring Boot 3.x junto con Spring Data JPA y Spring Security.
*   **Base de Datos:** PostgreSQL en el puerto `5432` (`ecommerce-db-m3`).
*   **Motor de Vistas:** HTML5 dinamico acoplado con Thymeleaf y estilizado mediante la CDN oficial de Bootstrap 5.
*   **Gestor de Dependencias:** Maven.

### Cómo hacer correr mi aplicacion (Instrucciones)

Si deseas levantar mi proyecto y revisarlo localmente, te comparto los pasos visuales que sigo directamente en mi entorno de desarrollo desde Eclipse, sin usar comandos externos:

1.  **Importar el proyecto:** Abro Eclipse, voy a *File -> Import -> Existing Maven Projects*, selecciono la carpeta raiz del proyecto donde se ubica mi archivo `pom.xml` y presiono *Finish*.
2.  **Limpiar y compilar la aplicacion:**
    *   Hago clic derecho sobre la carpeta raiz de mi proyecto en el *Project Explorer*.
    *   Selecciono la opción **Run As** -> **Maven clean** para borrar cualquier compilacion vieja.
3.  **Arrancar el servidor de Spring Boot:**
    *   Hago clic derecho sobre la carpeta raiz de mi proyecto.
    *   Selecciona la opción **Run As** -> **Spring Boot App**.
    *   Veras en la pestaña *Console* de Eclipse como avanza el log hasta que el servidor embebido de Tomcat quede listo.
4.  **Ingresar al sistema:** Configure la aplicacion en el puerto **8081**, por lo que solo debes abrir tu navegador web e ingresar de forma inmediata a:  
     `http://localhost:8081/login`

### Cuentas de prueba que configure (Usuarios y Roles)

Para facilitar el proceso de evaluacion, implemente una clase de carga de datos iniciales (`DataInitializer`) que inserta automaticamente los siguientes perfiles de prueba en mi base de datos:

### Perfil de Cliente (Rol: `CLIENT`)

*   **Correo electronico:** `cliente@fuentedevida.com`
*   **Contraseña de acceso:** `cliente123`
*   **Comportamiento del flujo:** Al iniciar sesion, mi sistema te redirigira directamente a la vista de `/catalogo`. Aqui podras ver el listado de bidones y dispensadores. Como mejora clave, que incorpore, **las tarjetas muestran el Stock Disponible real** directo de PostgreSQL. Ademas, si intentas agregar al carro mas unidades de las existentes en bodega, el sistema disparara una alerta interactiva y bloqueara la accion.

### Perfil de Administrador (Rol: `ADMIN`)

*   **Correo electronico:** `admin@fuentedevida.com`
*   **Contraseña de acceso:** `admin123`
*   **Comportamiento del flujo:** Al ingresar estas credenciales, mi componente `CustomSuccessHandler` detectara tus privilegios superiores y te despachara automaticamente al panel privado de `/admin/products`. Diseñe esta interfaz con una tabla de control donde el administrador puede reponer existencias usando un boton rapido de guardado, eliminar productos o abrir un modal flotante para registrar nuevas ofertas de agua especificando su stock inicial.

### Rutas protegidas

Para proteger la integridad de los datos, configure un bloque de reglas dentro de mi clase `SecurityConfig`:

*   **Accesos Publicos (`.permitAll()`):** Deje libres los formularios de `/login` y `/register`. 
*   **Zonas de Clientes:** El ingreso a la raiz `/` y a la ruta `/catalogo` exige estrictamente que el usuario se encuentre logueado en el sistema con sus credenciales autorizadas.
*   **Zonas de Administracion (`.hasRole("ADMIN")`):** Blinde por completo todas las rutas que cuelguen de `/admin/**`. Si un usuario con rol de cliente intenta manipular la URL para ingresar al panel de inventarios, Spring Security interceptara la peticion de inmediato, denegara el acceso y retornara un error HTTP 403.

### Las dos pruebas que programe para revisar el sistema (Tests)

Dentro de la carpeta de pruebas (`src/test/java`), cree dos pequeños programas para comprobar automaticamente que las partes mas delicadas de la aplicacion funcionen bien antes de ponerla en marcha. Como todavia estoy aprendiendo, estos tests me sirvieron para asegurarme de no romper el codigo anterior:

1.  **`testRutaAdminProtegida` (Prueba de Seguridad)**: Aqui simule de forma falsa la entrada de una persona desconocida (sin iniciar sesion) directo a la ruta `/admin/products`. El test da luz verde si el sistema lo frena a tiempo y lo manda de vuelta a la pagina de inicio de sesion (`/login`), demostrando que el candado de Spring Security si funciona.
2.  **`testRegistroYEncriptacion` (Prueba de Registro de Usuarios)**: Con este test revise lo que pasa cuando alguien se registra. El programa se asegura de que:
    *   El sistema le ponga automaticamente el rol de **CLIENT** en la base de datos de forma predeterminada.
    *   La contraseña **nunca** se guarde en texto plano (como la escribio el usuario), sino que pase por el encriptador **BCrypt** para convertirse en un codigo secreto seguro e ilegible.

Para correr estas dos pruebas dentro de mi entorno de desarrollo, simplemente hago:

*   Clic derecho sobre mi proyecto en Eclipse.
*   Selecciono la opción **Run As** -> **JUnit Test**.
*   En la pestaña *JUnit*, el sistema me marca una barra verde si todo mi codigo paso las verificaciones con exito.

### 🔗 Codigo Fuente del Proyecto

*   **Mi Repositorio en GitHub:** https://github.com/andreaaparrac-ctrl/ecommerce_m6