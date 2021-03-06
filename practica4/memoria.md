# Práctica 4

## Software utilizado

Se ha utilizado el siguiente software:

* Audacity para la digitalización de las cintas. Grabación mono y exportación de los archivos grabados como PCM 8-bit sin compresión.

* Maketzx para extraer los programas de los archivos WAV. Opciones por defecto.

* Spectaculator como emulador de Spectrum. Ninguna configuración especial.

## Problemas encontrados

El principal problema encontrado ha sido la digitalización de la cinta debido a la mala gestión de los volúmenes de entrada del Audacity y del sistema operativo (Fedora 24) que por un lado no es uniforme y por otro lado interfieren entre sí. Esto provocaba que a la hora de realizar la grabación, el resultado fuera un archivo de muy bajo nivel del que no se podía extraer ningún programa o por contra un archivo con un volumen excesivamente alto donde la señal excedía los niveles máximos y del que tampoco se podía extraer nada.

El problema se solucionó al realizar la captura del audio igualmente con Audacity, pero desde un sistema Windows 10 donde los volúmenes de grabación eran coherentes entre sí y la granularidad a la hora de incrementar el volumen de grabación era mayor. En total fueron necesarias 17 pasadas a la cinta hasta obtener resultados.

Por otra parte, el hecho de no conocer el sistema Spectrum y sus peculiaridades también implicó ciertos problemas a la hora de poder comprobar los programas recuperados ya que tiene algunas peculiaridades como el teclado y sus comandos asociados a teclas que necesitan de al menos un tiempo de reflexión.

## Programas encontrados

Los programas encontrados fueron los siguientes:

* Stop The Express http://www.worldofspectrum.org/infoseekid.cgi?id=0004916

* Match Day http://www.worldofspectrum.org/infoseekid.cgi?id=0002514

* Chess http://www.worldofspectrum.org/infoseekid.cgi?id=0000914

* JetPack http://www.worldofspectrum.org/infoseekid.cgi?id=0009362

## Programas recuperados

En todos los casos salvo en el de JetPack se pudo cargar el programa e incluso iniciar una partida (todos eran juegos). En el caso de JetPack se produce un error en la carga inicial y se detiene la ejecución. No fue posible su recuperación. A continuación se pueden observar algunas capturas realizadas

![](img/spectaculator_screenshot1.png)

![](img/spectaculator_screenshot2.png)

![](img/spectaculator_screenshot3.png)

![](img/spectaculator_screenshot4.png)

Los ficheros TZX recuperados se pueden descargar [aquí](cara_a.tzx) y [aquí](cara_b.tzx) (una cara y el reverso).
