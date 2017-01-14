# Práctica 1
## Objetivo 1: compilar y ejecutar el código inicial
El compilador utilizado para desarrollar la práctica ha sido OpenCobol 1.1.0, por diversas razones:
* Open Source (licencia LGPL)
* Multiplataforma
* Sencilla integración con las herramientas de desarrollo de entornos UNIX (make)
* ...

El código del programa está dividido en tres subprogramas:
* `MAINHRMS` controla el menú de bienvenida y permite ejecutar los subprogramas `EMPREAD` y `EMPWRITE`
* `EMPREAD` permite realizar operaciones de lectura sobre ficheros .DAT creados por el programa
* `EMPWRITE` permite crear y modificar ficheros .DAT con los datos introducidos por el usuario

Sin embargo, el código inicial no se podía compilar directamente, por lo que para conseguir compilarlo y ejecutarlo hubo que realizar las siguientes modificaciones en el fichero fuente para solucionar cada uno de los errores de compilación:

1. Añadir sentencias `END PROGRAM` al final de cada subprograma
2. Reemplazar sentencias `DISPLAY ERASE` (usadas para borrar la pantalla) por un campo en la sección `SCREEN`:
  ```cobol
  SECREEN SECTION.
  01 ERASE-1
    02 BLANK SCREEN.

  PROCEDURE DIVISION.
  *> ...
    DISPLAY ERASE-1
  ```
3. Cambiada sintaxis de los verbos `DISPLAY` y `ACCEPT` por `DISPLAY ... AT LINE x COL y`
4. Reemplazado verbo `STOP` (usado para detener la ejecucin del programa hasta que el usuario pulse ENTER) por sentencia `ACCEPT`:
  ```cobol
  WORKING-STORAGE SECTION.
  77 OMIT PIC 99.
  
  PROCEDURE DIVISION.
  *> ...
    ACCEPT OMIT AT LINE x COL y.
  ```
5. Divididas líneas demasiado largas

Con estas modificaciones se consiguió que el programa compilara con `cobc` y se ejecutara.
