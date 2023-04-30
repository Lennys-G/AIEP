# **DESCRIPCIÓN DEL PROGRAMA**

Este código en Java es un programa de consola que muestra un menú de opciones para realizar operaciones matemáticas básicas y también para resolver una ecuación de segundo grado. El usuario puede seleccionar una opción y seguir las instrucciones para ingresar los valores necesarios para realizar la operación.

## **CLASE PRINCIPAL APP:**

- ### **Método Main:**
  El Método main es el punto de entrada del programa, inicia el programa creando una instancia de la clase Calculator y mostrando un mensaje de bienvenida.
  Luego, entra en un bucle while que se ejecuta hasta que el usuario seleccione la opción "6" para salir del programa.
  En cada iteración del bucle, se muestra el menú de opciones al usuario y se lee la opción seleccionada.
  Si la opción seleccionada es entre 1 y 4, se solicitan dos números al usuario y se realiza la operación aritmética correspondiente.
  Si la opción seleccionada es 5, se solicitan los coeficientes de una ecuación cuadrática al usuario y se resuelve la ecuación.
  Si la opción seleccionada es 6, se muestra un mensaje de despedida y el programa finaliza.
  Si la opción seleccionada no es válida, se muestra un mensaje de error y se solicita al usuario que seleccione otra opción.

## **CLASE CALCULATOR:**

Esta clase contiene varios métodos que se encargan de leer la entrada del usuario, validarla y realizar las operaciones correspondientes.

- ### **Método showMenu:**

  El Método showMenu muestra el menú de opciones al usuario.

- ### **Método readOption:**

  El Método readOption lee y valida la opción seleccionada por el usuario. Si el usuario ingresa una opción inválida, se muestra un mensaje de error y se solicita que ingrese una opción válida.

- ### **Método readNumber:**

  El Método readNumber lee y valida un número ingresado por el usuario. Si el usuario ingresa un valor no numérico, se muestra un mensaje de error y se solicita que ingrese un número válido.

- ### **Método readContinueOption:**

  El Método readContinueOption lee y valida si el usuario desea continuar realizando operaciones. Si el usuario ingresa una opción inválida, se muestra un mensaje de error y se solicita que ingrese una opción válida.

- ### **Método performOperation:**

  El Método performOperation realiza una operación aritmética correspondiente a la opción seleccionada por el usuario. Si la opción seleccionada es la división y el segundo número ingresado es cero, se muestra un mensaje de error.

- ### **Método solveQuadraticEquation:**
  El Método solveQuadraticEquation resuelve una ecuación cuadrática ingresada por el usuario. Si la ecuación tiene dos soluciones, se muestran ambas. Si la ecuación tiene una solución, se muestra esa solución. Si la ecuación no tiene soluciones reales, se muestra un mensaje de error.
