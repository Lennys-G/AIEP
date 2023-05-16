package airport;

import java.util.Random;

class Airplane {
  // Atributos de la clase
  private int id;
  private static int previousId = 0;
  private int maxNumberPassenger;
  private int currentNumberPassenger;
  private Boolean state;
  Random random = new Random();

  /*
   * Constructor para crear un objeto pasando los valores como argumentos y
   * asignandolos a los atributos.
   */
  /*
   * public Airplane(int id, int maxNumberPassenger, int currentNumberPassenger,
   * Boolean state) {
   * this.id = id;
   * this.maxNumberPassenger = maxNumberPassenger;
   * this.currentNumberPassenger = currentNumberPassenger;
   * this.state = state;
   * }
   */

  /*
   * Constructor para crear un objeto asignando a los atributos de forma
   * aleatoria.
   */
  public Airplane() {
    // Asigna el id de manera consecutiva.
    this.id = ++previousId;
    // Asigna una cantidad máxima de pasajeros aleatoria entre 50 y 299.
    this.maxNumberPassenger = random.nextInt(250) + 50;
    /*
     * Asigna la cantidad actual de pasajeros aleatoria entre 0 y la cantidad
     * máxima.
     */
    this.currentNumberPassenger = random.nextInt(maxNumberPassenger + 1);
    // Asigna un estado aleatorio, true o false.
    this.state = random.nextBoolean();

  }

  // Método para obtener el ID.
  public int getId() {
    return this.id;
  }

  // Método para obtener el número máximo de pasajeros.
  public int getMaxNumberPassenger() {
    return this.maxNumberPassenger;
  }

  // Método para obtener el número actual de pasajeros.
  public int getCurrentNumberPassenger() {
    return this.currentNumberPassenger;
  }

  // Método para obtener el estado del avión.
  public Boolean getState() {
    return this.state;
  }

  // Método para obtener la cantidad de asientos disponibles
  public int getAvailableSpaces() {
    return (this.maxNumberPassenger - this.currentNumberPassenger);
  }

  // Método para desembarcar pasajeros.
  /*
   * Si el número de pasajeros a desembarcar está entre 0 y el número actual se
   * actualiza el número actual de pasajeros, restando el número a desembarcar al
   * número actual, quedando solo el número de pasajeros que no desembarcó.
   * Permite que desembarquen muchos, uno o ningún pasajero.
   */
  public void disembarkationPassenger(int amount) {
    if (amount < 0 || amount > this.currentNumberPassenger) {
      System.out.println("Error: No es posible desembarcar más pasajeros de los que estan abordo ");
    } else {
      this.currentNumberPassenger -= amount;
      System.out.println(
          amount + " Pasajeros desembarcaron del avión. Quedan " + currentNumberPassenger + " pasajeros y "
              + getAvailableSpaces() + " asientos vacíos en el avión.");
    }
  }

  // Método para embarcar pasajeros.
  /**
   * Si el número de pasajeros a embarcar está entre 1 y el número de asientos
   * vacios(se obtiene restando el número máximo de pasajeros al número actual de
   * pasajeros dentro del avión) se actualiza el número actual de pasajeros,
   * sumando el número de pasajeros a embarcar al número actual.
   */
  public void embarkationPassenger(int amount) {
    if (amount < 0 || amount > getAvailableSpaces()) {
      System.out.println("Desafortunadamente, ya no hay asientos disponibles para agregar más pasajeros al avión.");
      System.out.println("Hay " + getAvailableSpaces() + " asientos disponibles");
    } else if (this.currentNumberPassenger + amount > this.maxNumberPassenger) {
      System.out.println(
          "Lo sentimos, no es posible abordar a todos los pasajeros debido a que la cantidad de personas excede la capacidad máxima asientos disponibles en el avión");
    } else {
      this.currentNumberPassenger += amount;
      System.out
          .println(amount + " Pasajeros embarcaron el avión. Ahora hay " + currentNumberPassenger + " pasajeros y "
              + getAvailableSpaces() + " asientos disponibles en el avión.");
    }
  }

  // Método para imprimir formateada la información de una avión.
  public String formatDataAirplane() {
    return "El ID del avión es: " + this.id + "\n"
        + "Cantidad máxima de pasajeros: " +
        +this.maxNumberPassenger + "\n"
        + "Cantidad de pasajeros abordo: " +
        +this.currentNumberPassenger + "\n"
        + "Estado del avión: " + this.state;
  }

}
