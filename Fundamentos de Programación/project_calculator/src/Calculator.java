import java.util.Scanner;
import java.util.InputMismatchException;

public class Calculator {
  // Creamos un objeto Scanner para leer la entrada del usuario
  private static final Scanner scanner = new Scanner(System.in);

  // Método que contiene el menú de opciones a mostrar al usuario
  public void showMenu() {
    System.out.println("*** Menú ***");
    System.out.println("1. Suma");
    System.out.println("2. Resta");
    System.out.println("3. Multiplicación");
    System.out.println("4. División");
    System.out.println("5. Cálculo ecuación de segundo grado");
    System.out.println("6. Salir");
    System.out.print("Ingrese una opción: ");
  }

  // Método para leer y validar la opción seleccionada por el usuario
  public int readOption(String message) {
    while (true) {
      try {
        int option = scanner.nextInt();// Lee la entrada del usuario
        scanner.nextLine(); // Limpia el buffer de entrada
        return option;
        /*
         * Si el dato ingresado no es un entero se genera un "Exception" que es
         * manejado por el catch para mostrar un mensaje de error
         */
      } catch (InputMismatchException e) {
        System.out.println(message);
        scanner.nextLine(); // Limpia el buffer de entrada
      }
    }
  }

  // Método para leer y validar un número ingresado por el usuario
  public double readNumber(String message) {
    while (true) {
      System.out.println(message);
      try {
        double number = scanner.nextDouble(); // Lee la entrada del usuario
        scanner.nextLine(); // Limpia el buffer de entrada
        return number;
      } catch (InputMismatchException e) {
        System.out.println("La entrada debe ser un número.");
        scanner.nextLine(); // Limpia el buffer de entrada
      }
    }

  }

  // Método para leer y validar que el usuario ingrese "S/N"
  public String readContinueOption(String message) {
    System.out.println(message);
    String continueOption = scanner.nextLine();
    while (!continueOption.equalsIgnoreCase("S") && !continueOption.equalsIgnoreCase("N")) {
      System.out.println("Opción inválida, intente de nuevo: ");
      continueOption = scanner.nextLine();
    }
    return continueOption;
  }

  // Método para realizar una operación aritmética seleccionada por el usuario
  public void performOperation(int option) {
    double num1 = readNumber("Ingrese el primer número: ");
    double num2 = readNumber("Ingrese el segundo número: ");

    // Realiza la operación correspondiente al caso según la opción ingresada
    switch (option) {
      case 1 -> System.out.println("El resultado de la suma es: " + (num1 + num2));
      case 2 -> System.out.println("El resultado de la resta es: " + (num1 - num2));
      case 3 -> System.out.println("El resultado de la multiplicación es: " + (num1 * num2));
      case 4 -> {
        if (num2 == 0) {
          System.out.println("No se puede dividir por cero");
        } else {
          System.out.println("El resultado de la división es: " + (num1 / num2));
        }
      }
    }
  }

  // Método para realizar una operación de segundo grado
  public void solveQuadraticEquation() {
    double a = readNumber("Ingrese el valor de a: ");
    double b = readNumber("Ingrese el valor de b: ");
    double c = readNumber("Ingrese el valor de c: ");

    double discriminant = b * b - 4 * a * c;
    if (discriminant > 0) {
      double x1 = (-b + Math.sqrt(discriminant)) / (2 * a);
      double x2 = (-b - Math.sqrt(discriminant)) / (2 * a);
      System.out.println("La ecuación tiene dos soluciones reales: x1 = " + x1 + ", x2 = " + x2);
    } else if (discriminant == 0) {
      double x = -b / (2 * a);
      System.out.println("La ecuación tiene una única solución real: x = " + x);
    } else {
      System.out.println("La ecuación no tiene soluciones reales.");
    }
  }

}