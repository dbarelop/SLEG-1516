# Práctica 1

## Objetivo 1: compilar y ejecutar el código inicial

El compilador utilizado para desarrollar la práctica ha sido OpenCobol 1.1.0 (actualmente [GNUCobol](https://sourceforge.net/projects/open-cobol/)), por diversas razones:

* Open Source (licencia LGPL)

* Multiplataforma

* Sencilla integración con las herramientas de desarrollo de entornos UNIX (make)

* [Guía de programación](http://open-cobol.sourceforge.net/) y soportado por (más o menos amplia) comunidad online

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

## Objetivo 2: Añadir funcionalidad `LIST BRANCH FILE`

Se requiere añadir una nueva funcionalidad para listar sucursales, con posibilidad de filtrar por el nombre de la ciudad.

En el registro de sucursales (`BRANCHREC`) no está declarado explícitamente un campo para la ciudad, así que se asume que la ciudad está en una parte del campo de la dirección (`BBRADD`). Para acceder a la nueva funcionalidad, se añade una opción nueva al menú de lectura: `03. LIST BRANCH FILE`.

El fichero de sucursales (`BRANCHFILE`) es un fichero de acceso dinámico. Esto significa que se pueden leer los registros de la siguiente manera:

```cobol
LIST-BRANCH-PARA.
  OPEN INPUT BRANCHFILE.
  PERFORM UNTIL FSB = 10
    *> Mostrar los campos
    READ BRANCHFILE NEXT RECORD
      AT END GO TO LIST-BRANCH-EXIT-PARA
  END-PERFORM.
LIST-BRANCH-EXIT-PARA.
  CLOSE BRANCHFILE.
```

El filtrado por ciudad (substring del campo donde se guarda la dirección, `BBRADD`), se implementa de la siguiente manera:

```cobol
*> LBCITY contiene el nombre de la ciudad a filtrar (30 caracteres, con espacios en los caracteres restantes)
IF LBCITY NOT = SPACE
  *> 1. Obtener el número de espacios en la variable LBCITY
  INSPECT FUNCTION REVERSE (LBCITY)
    TALLYING LBCITY-SPACES
    FOR LEADING SPACES
  *> 2. Obtener la longitud de la variable LBCITY (LBCITY-LEN = 30 - LBCITY-SPACES)
  SUBTRACT LBCITY-SPACES
    FROM 30 GIVING LBCITY-LEN
  *> 3. Obtener el número de ocurrencias de la cadena LBCITY en el campo BBRADD
  INSPECT BBRADD
    TALLYING CNTR
    FOR ALL LBCITY (1 : LBCITY-LEN)
END-IF
IF CNTR > 0 OR LBCITY = SPACE
  *> Mostrar los campos
END-IF
```

## Objetivo 3: Inserción de datos de prueba (versión automatizada)

Para insertar rápidamente registros de prueba en la aplicación se ha creado el script [`expect`](http://expect.sourceforge.net/) [add_test_data.sh](https://github.com/dbarelop/SLEG-1516/blob/master/practica1/add_test_data.sh), que se encarga de lanzar la aplicación y realizar la inserción de un número determinado de registros de sucursal diferentes.
