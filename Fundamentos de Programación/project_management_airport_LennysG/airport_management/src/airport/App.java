package airport;

import java.util.Scanner;
import java.util.InputMismatchException;

public class App {
  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    Airport airport = new Airport("Aeropuerto Internacional Carriel Sur ");
    System.out.println("Bienvenido al " + airport.getNameAirport());
    int option = 0;

    // airport.showAirplanes();

    while (option != 7) {
      // Mostramos el menú al usuario
      showMenu();
      // Lee la opción seleccionada por el usuario
      option = readOption("Ingrese un número entero entre 1 y 7: ");
      if (option >= 1 && option <= 6) {
        switch (option) {
          case 1 -> airport.showIdAirplanes();
          case 2 -> airport.getTotalPassengersCount();
          case 3 -> airport.getTotalAvailableSpaces();
          case 4 -> airport.searchAirplaneById(
              readNumber("Ingrese el ID del avión que desea consultar: ", "Avión No Encontrado."));
          case 5 -> airport
              .embarkation(
                  readNumber("Ingrese el ID del avión al que deben embarcar los pasajeros: ",
                      "Error: Avión No Encontrado"),
                  readNumber("Ingrese la cantidad de pasajeros a embarcar: ", "Error: Ingrese una cantidad válida"));
          case 6 -> airport.disembarkation(
              readNumber("Ingrese el ID del avión que desea consultar: ", "Error: Avión No Encontrado"),
              readNumber("Ingrese la cantidad de pasajeros a desembarcar: ", "Error: Ingrese una cantidad válida."));
          default -> System.out.println("Opción inválida.");
        }
        String continueOption = readContinueOption("¿Desea realizar otra operación? (S/N): ");
        if (continueOption.equalsIgnoreCase("N")) {
          System.out.println("¡Adiós!");
          option = 7; // Salimos del bucle while
        }
      } else if (option == 7) {
        System.out.println("¡Adiós!");
      } // Si la opción seleccionada no es válida, mostramos un mensaje de error
      else {
        System.out.println("Opción inválida, intente de nuevo.");
      }
    }
    scanner.close();
  }

  public static void showMenu() {
    System.out.println("""
        ***Menú ***
        1. Listar el ID de todos los aviones.
        2. Cantidad de pasajeros actual en todos los aviones.
        3. Cantidad de asientos disponibles en todos los aviones.
        4. Buscar y mostrar todos los datos de un avión a través de su ID.
        5. Embarcar pasajeros a un avión.
        6. Desembarcar pasajeros de un avión.
        7. Salir.
        Ingrese una opción: """);

  }

  // Método para leer y validar la opción seleccionada por el usuario
  public static int readOption(String message) {
    while (true) {
      try {
        int option = scanner.nextInt();// Lee la entrada del usuario
        scanner.nextLine(); // Limpia el buffer de entrada
        return option;

        // Si el dato ingresado no es un entero se genera un "Exception" que es
        // manejado por el catch para mostrar un mensaje de error

      } catch (InputMismatchException e) {
        System.out.println(message);
        scanner.nextLine(); // Limpia el buffer de entrada
      }
    }
  }

  // Método para leer y validar un número ingresado por el usuario
  public static int readNumber(String message, String errorMessage) {
    while (true) {
      System.out.println(message);
      try {
        int number = scanner.nextInt(); // Lee la entrada del usuario
        scanner.nextLine(); // Limpia el buffer de entrada
        if (number >= 0) {
          return number;
        } else {
          System.out.println(errorMessage);
        }

      } catch (InputMismatchException e) {
        System.out.println("El valor ingresado debe ser un número valido.");
        scanner.nextLine(); // Limpia el buffer de entrada
      }
    }

  }

  // Método para leer y validar que el usuario ingrese "S/N"
  public static String readContinueOption(String message) {
    System.out.println(message);
    String continueOption = scanner.nextLine();
    while (!continueOption.equalsIgnoreCase("S") &&
        !continueOption.equalsIgnoreCase("N")) {
      System.out.println("Opción inválida, intente de nuevo: ");
      continueOption = scanner.nextLine();
    }
    return continueOption;
  }

}
