## Escuela Colombiana de Ingeniería
### Arquitecturas de Software
### Introducción al paralelismo - hilos

### Trabajo individual o en parejas

Entrega: Martes en el transcurso del día.
Entregar: Fuentes y documento PDF con las respuestas.

**Daniel Alejandro Acero Varela**

**Parte I Hilos Java**

1. De acuerdo con lo revisado en las lecturas, complete las clases CountThread, para que las mismas definan el ciclo de vida de un hilo que imprima por pantalla los números entre A y B.
2. Complete el método __main__ de la clase CountMainThreads para que:
	1. Cree 3 hilos de tipo CountThread, asignándole al primero el intervalo [0..99], al segundo [99..199], y al tercero [200..299].
	2. Inicie los tres hilos con 'start()'.
	3. Ejecute y revise la salida por pantalla. 
	4. Cambie el incio con 'start()' por 'run()'. Cómo cambia la salida?, por qué?.
	RTA:// Cambie el incio con 'start()' por 'run()'. Cómo cambia la salida?, por qué?. RTA: con start() se imprimen los números paralelamente, por rangos; Mientras que run() se ejecuta, primero un hilo y luego el otro. Es decir, imprime todos los rangos de un mismo hilo y, luego continúa con el siguiente, hasta terminar (es ordenado). Esto se debe a que run() no genera nuevos hilos y, start() está implementado para funcionar concurrentemente.

**Parte II Hilos Java**

La fórmula [BBP](https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula) (Bailey–Borwein–Plouffe formula) es un algoritmo que permite calcular el enésimo dígito de PI en base 16, con la particularidad de no necesitar calcular nos n-1 dígitos anteriores. Esta característica permite convertir el problema de calcular un número masivo de dígitos de PI (en base 16) a uno [vergonzosamente paralelo](https://en.wikipedia.org/wiki/Embarrassingly_parallel). En este repositorio encontrará la implementación, junto con un conjunto de pruebas. 

Para este ejercicio se quiere calcular, en el menor tiempo posible, y en una sola máquina (aprovechando las características multi-core de la mismas) al menos el primer millón de dígitos de PI (en base 16). Para esto

1. Cree una clase de tipo Thread que represente el ciclo de vida de un hilo que calcule una parte de los dígitos requeridos.
2. Haga que la función PiDigits.getDigits() reciba como parámetro adicional un valor N, correspondiente al número de hilos entre los que se va a paralelizar la solución. Haga que dicha función espere hasta que los N hilos terminen de resolver el problema para combinar las respuestas y entonces retornar el resultado. Para esto, revise el método [join](https://docs.oracle.com/javase/tutorial/essential/concurrency/join.html) del API de concurrencia de Java.
3. Ajuste las pruebas de JUnit, considerando los casos de usar 1, 2 o 3 hilos (este último para considerar un número impar de hilos!)


**Parte III Evaluación de Desempeño**

A partir de lo anterior, implemente la siguiente secuencia de experimentos para calcular el millon de dígitos (hex) de PI, tomando los tiempos de ejecución de los mismos (asegúrese de hacerlos en la misma máquina):

1. Un solo hilo.
![Captura de pantalla 2025-01-29 210431.png](..%2F..%2F..%2FOneDrive%2FPictures%2FScreenshots%2FCaptura%20de%20pantalla%202025-01-29%20210431.png)
![Captura de pantalla 2025-01-29 210426.png](..%2F..%2F..%2FOneDrive%2FPictures%2FScreenshots%2FCaptura%20de%20pantalla%202025-01-29%20210426.png)
2. Tantos hilos como núcleos de procesamiento (haga que el programa determine esto haciendo uso del [API Runtime](https://docs.oracle.com/javase/7/docs/api/java/lang/Runtime.html)).
![Captura de pantalla 2025-01-29 211507.png](..%2F..%2F..%2FOneDrive%2FPictures%2FScreenshots%2FCaptura%20de%20pantalla%202025-01-29%20211507.png)
![Captura de pantalla 2025-01-29 211403.png](..%2F..%2F..%2FOneDrive%2FPictures%2FScreenshots%2FCaptura%20de%20pantalla%202025-01-29%20211403.png)
3. Tantos hilos como el doble de núcleos de procesamiento.
![Captura de pantalla 2025-01-29 211507.png](..%2F..%2F..%2FOneDrive%2FPictures%2FScreenshots%2FCaptura%20de%20pantalla%202025-01-29%20211507.png)
![Captura de pantalla 2025-01-29 211403.png](..%2F..%2F..%2FOneDrive%2FPictures%2FScreenshots%2FCaptura%20de%20pantalla%202025-01-29%20211403.png)
4. 200 hilos.
![Captura de pantalla 2025-01-29 211704.png](..%2F..%2F..%2FOneDrive%2FPictures%2FScreenshots%2FCaptura%20de%20pantalla%202025-01-29%20211704.png)
![Captura de pantalla 2025-01-29 211546.png](..%2F..%2F..%2FOneDrive%2FPictures%2FScreenshots%2FCaptura%20de%20pantalla%202025-01-29%20211546.png)
5. 500 hilos.
![Captura de pantalla 2025-01-29 211704.png](..%2F..%2F..%2FOneDrive%2FPictures%2FScreenshots%2FCaptura%20de%20pantalla%202025-01-29%20211704.png)
![Captura de pantalla 2025-01-29 211403.png](..%2F..%2F..%2FOneDrive%2FPictures%2FScreenshots%2FCaptura%20de%20pantalla%202025-01-29%20211403.png)

Al iniciar el programa ejecute el monitor jVisualVM, y a medida que corran las pruebas, revise y anote el consumo de CPU y de memoria en cada caso. ![](img/jvisualvm.png)

Con lo anterior, y con los tiempos de ejecución dados, haga una gráfica de tiempo de solución vs. número de hilos. Analice y plantee hipótesis con su compañero para las siguientes preguntas (puede tener en cuenta lo reportado por jVisualVM):



1. Según la [ley de Amdahls](https://www.pugetsystems.com/labs/articles/Estimating-CPU-Performance-using-Amdahls-Law-619/#WhatisAmdahlsLaw?):

	![](img/ahmdahls.png), donde _S(n)_ es el mejoramiento teórico del desempeño, _P_ la fracción paralelizable del algoritmo, y _n_ el número de hilos, a mayor _n_, mayor debería ser dicha mejora. Por qué el mejor desempeño no se logra con los 500 hilos?, cómo se compara este desempeño cuando se usan 200?. 
	RTA// Produce el fenómeno de **sobrecarga de gestión de hilos** reduciendo las ventajas de la paralelización. El tiempo dedicado a la parte secuencial limita el rendimiento máximo posible. 
	Un menor número de hilos (200) puede generar un mayor rendimiento.

   2. Cómo se comporta la solución usando tantos hilos de procesamiento como núcleos comparado con el resultado de usar el doble de éste?.
       RTA// Con hilos de igual número de núcleos la **sobrecarga de gestión de hilos** es mínima. El hardware no necesita competir por recursos lo que optimiza su desempeño. 
       En el caso de doble número de hilos por núcleo, puede ocurrir **hyper-threading**, beneficiandose cada dos hilos por un núcleo.
   		Esto puede ser un factor negativo, generando menor aprovechamiento de recursos.

3. De acuerdo con lo anterior, si para este problema en lugar de 500 hilos en una sola CPU se pudiera usar 1 hilo en cada una de 500 máquinas hipotéticas, la ley de Amdahls se aplicaría mejor?. Si en lugar de esto se usaran c hilos en 500/c máquinas distribuidas (siendo c es el número de núcleos de dichas máquinas), se mejoraría?. Explique su respuesta.
	RTA// Distribuir el trabajo en múltiples máquinas puede mejorar el rendimiento, pero la **Ley de Amdahl** sigue aplicándose debido a la parte secuencial del algoritmo y la posible latencia en la comunicación entre máquinas.


#### Criterios de evaluación.

1. Funcionalidad:
	- El problema fue paralelizado (el tiempo de ejecución se reduce y el uso de los núcleos aumenta), y permite parametrizar el número de hilos usados simultáneamente.

2. Diseño:
	- La signatura del método original sólo fue modificada con el parámetro original, y en el mismo debe quedar encapsulado la paralelización e inicio de la solución, y la sincronización de la finalización de la misma.
	- Las nuevas pruebas con sólo UN hilo deben ser exactamente iguales a las originales, variando sólo el parámetro adicional. Se incluyeron pruebas con hilos adicionales, y las mismas pasan.
	- Se plantea un método eficiente para combinar los resultados en el orden correcto (iterar sobre cada resultado NO sera eficiente).

3. Análisis.
	- Se deja evidencia de la realización de los experimentos.
	- Los análisis realizados son consistentes.
