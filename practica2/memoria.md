# Práctica 2

Se pide implementar un programa para acceder mediante una interfaz gráfica de ventanas a una aplicación legada ejecutada sobre un mainframe IBM ESA/390 con sistema operativo MUSIC/SP, accedido desde un terminal TN3270.

La aplicación legada a encapsular se trata de un gestor de tareas, en el que se pueden añadir y consultar tareas generales (con fecha y descripción) o específicas (con fecha, nombre y descripción). Para acceder a la aplicación es necesario conectarse al mainframe, iniciar sesión con un usuario y una contraseña válidos y lanzar la aplicación legada.

La aplicación GUI permite al usuario conectarse, inciar sesión y lanzar la aplicación legada, visualizar las tareas generales y específicas y añadir tareas de ambos tipos. Se ha programado en Java, usando la librería `javax.swing` para gestionar la interfaz gráfica. El acceso al terminal no se ha realizado usando ninguna librería, debido a que no se ha encontrado ninguna que permita un manejo sencillo de terminales TN3270. En su lugar se ha usado el emulador de terminal [*x3270*](http://x3270.bgp.nu/).

## Acceso al emulador de terminal *x3270*

El programa *x3270* es el que se encarga de gestionar toda la comunicación con el mainframe. Una vez iniciado, acepta comandos (por la entrada estándar o a través de un puerto TCP) y devuelve la salida de los comandos. Se distribuye en dos variantes: la versión gráfica (x3270) y la versión para usarse en *scrapers* (s3270). Algunos de los comandos que acepta x2370 son:

* `Connect(<ip>:<port>)` para abrir una conexión con un host

* `Disconnect` para desconectarse

* `String("<str>")` para escribir texto

* `Enter` para enviar el texto escrito

* `Tab` para enviar una pulsación del tabulador

* `Snap` para guardar en memoria una captura de la terminal

* `Snap Ascii` para imprimir por pantalla una captura previamente guardada

* `EraseEOF` para eliminar un campo de texto

Para enviar comandos se utiliza la utilidad `x3270if`.

Se ha creado la clase `X3270Terminal` para gestionar la comunicación con el emulador de terminal. Esta clase crea un proceso de x3270 que corre de fondo escuchando en un puerto al instanciarse:

```java
PROCESS = Runtime.getRuntime().exec("s3270 -scriptport " + S3270_PORT);
```

Se ha optado por escuchar en un puerto TCP, en lugar de enviar los comandos por la entrada estándar de proceso como se propone en el guión de la práctica para simplificar el proceso. De esta manera es posible enviar comandos a `x3270` haciendo uso de la utilidad `x3270if` simplemente especificando el puerto en el que está escuchando la utilidad:

```java
Process p = Runtime.getRuntime().exec(new String[] { "x3270if", "-t", Integer.toString(S3270_PORT), cmd });
p.waitFor();
```

La clase almacena en una variable la pantalla en la que se encuentra para facilitar el uso de los métodos:

```java
private X3270TerminalState state = X3270TerminalState.DISCONNECTED;
private enum X3270TerminalState {
  DISCONNECTED,
  CONNECTED_SCREEN,
  LOGIN_SCREEN,
  LOGGED_IN_SCREEN,
  MAIN_MENU,
  LEGACY_APP
}
```


