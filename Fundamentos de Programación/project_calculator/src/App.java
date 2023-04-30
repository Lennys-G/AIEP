
public class App {
  public static void main(String[] args) {
    Calculator calculator = new Calculator();
    System.out.println("Bienvenido a la calculadora.");
    int option = 0;
    /*
     * El programa se ejecutará hasta que el usuario seleccione la opción "6" para
     * salir
     */
    while (option != 6) {
      calculator.showMenu(); // Mostramos el menú al usuario
      // Lee la opción seleccionada por el usuario
      option = calculator.readOption("Ingrese un número entero entre 1 y 6: ");
      // Si la opción seleccionada es entre 1 y 4, realizamos una operación aritmética
      if (option >= 1 && option <= 4) {
        calculator.performOperation(option);
        String continueOption = calculator.readContinueOption("¿Desea realizar otra operación? (S/N): ");
        if (continueOption.equalsIgnoreCase("N")) {
          System.out.println("¡Adiós!");
          option = 6; // Salimos del bucle while
        }
      } // Si la opción seleccionada es 5, resolvemos una ecuación cuadrática
      else if (option == 5) {
        calculator.solveQuadraticEquation();
      } // Si la opción seleccionada es 6, el programa finaliza
      else if (option == 6) {
        System.out.println("¡Adiós!");
      } // Si la opción seleccionada no es válida, mostramos un mensaje de error
      else {
        System.out.println("Opción inválida, intente de nuevo.");
      }
    }
  }
}
