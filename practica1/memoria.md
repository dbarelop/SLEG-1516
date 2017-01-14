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

El código inicial no se puede compilar directamente, por lo que para conseguir compilarlo y ejecutarlo hay que realizar las siguientes modificaciones en el fichero fuente para solucionar cada uno de los errores de compilación:

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

3. Cambiar la sintaxis de los verbos `DISPLAY` y `ACCEPT` por `DISPLAY ... AT LINE x COL y`

4. Reemplazar el verbo `STOP` (usado para detener la ejecucin del programa hasta que el usuario pulse ENTER) por la sentencia `ACCEPT`:

  ```cobol
  WORKING-STORAGE SECTION.
  77 OMIT PIC 99.
  
  PROCEDURE DIVISION.
  *> ...
    ACCEPT OMIT AT LINE x COL y.
  ```

5. Dividir las líneas demasiado largas

Con estas modificaciones se consigue que el programa compile con `cobc` y se ejecute.

Aunque el programa se compila correctamente, la creación de ficheros no funciona correctamente. Para modificar o crear un fichero .DAT, el programa intenta abrirlo primero en modo `I-O` (modifica un fichero existente) y comprueba si ha habido un error (`FILE STATUS`). De ser así, asume que el fichero no existe y lo abre en modo `OUTPUT` (crea un fichero nuevo o trunca uno existente).

El programa inicialmente comprueba que el estado de la apertura del fichero sea `30` para decidir que el fichero no existe y debe ser creado. Sin embargo, se ha comprobado que la llamada no devuelve `30`, con lo que el programa no puede crear ficheros. Se soluciona cambiando la condición:

```cobol
*> Código inicial que falla
OPEN I-O EMPFILE.
IF FSO = 30
  OPEN OUTPUT EMPFILE.
*> Código reemplazado
OPEN I-O EMPFILE.
IF FSO <> 00
  OPEN OUTPUT EMPFILE.
```

Con esta modificación se consigue crear ficheros .DAT correctamente, y se comprueba que la lectura también funciona correctamente.







