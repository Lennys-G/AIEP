package airport;

public class Airport {
  private String nameAirport;
  private Airplane[] airplanes;

  public Airport(String nameAirport) {
    this.nameAirport = nameAirport;
    this.airplanes = new Airplane[100];
    for (int i = 0; i < airplanes.length; i++) {
      this.airplanes[i] = new Airplane();
    }
  }

  // Método para obtener el nombre del aeropuerto
  public String getNameAirport() {
    return this.nameAirport;
  }

  // Método para obtener el id de todos los aviones
  public void showIdAirplanes() {
    for (Airplane airplane : airplanes) {
      System.out.println("ID: " + airplane.getId());
    }
  }

  // Método para mostrar todos los aviones
  public void showAirplanes() {
    for (Airplane airplane : airplanes) {
      System.out.println(airplane.formatDataAirplane());
    }
  }

  // Método para obtener la cantidad de pasajeros actuales en todos los aviones.
  public void getTotalPassengersCount() {
    int totalPassengersCount = 0;
    for (Airplane airplane : airplanes) {
      totalPassengersCount += airplane.getCurrentNumberPassenger();
    }
    System.out
        .println("La cantidad de pasajeros actual en todos los aviones es de: " + totalPassengersCount + " Pasajeros");
  }

  // Método para obtener el número total de asientos disponibles en todos los
  // aviones.
  public void getTotalAvailableSpaces() {
    int totalAvailableSpaces = 0;
    for (Airplane airplane : airplanes) {
      totalAvailableSpaces += airplane.getMaxNumberPassenger() - airplane.getCurrentNumberPassenger();
    }
    System.out.println(
        "La cantidad de asientos disponibles en todos los aviones es de: " + totalAvailableSpaces + " Asientos.");
  }

  // Método para obtener un avión por su id.
  public Airplane getAirplaneById(int id) {
    Airplane getAirplane = null;
    for (Airplane airplane : airplanes) {
      if (airplane.getId() == id) {
        getAirplane = airplane;
        break;
      }
    }
    return getAirplane;
  }

  // Método para retornar la información de un avión.
  public void searchAirplaneById(int id) {
    Airplane getAirplane = getAirplaneById(id);
    if (getAirplane != null) {
      System.out.println(getAirplane.formatDataAirplane());
    } else {
      System.out.println("Avión no encontrado.");
    }
  }

  // Método para subir pasajeros a un avión según su id.
  public void embarkation(int id, int amount) {
    Airplane getAirplane = getAirplaneById(id);
    if (getAirplane != null) {
      if (getAirplane.getState() == true) {
        System.out.println("No pueden abordar pasajeros mientras los motores del avión están en marcha.");
      } else {
        getAirplane.embarkationPassenger(amount);
      }
    } else {
      System.out.println("Avión no encontrado.");
    }
  }

  // Método para bajar pasajeros de un avión según su id.
  public void disembarkation(int id, int amount) {
    Airplane getAirplane = getAirplaneById(id);
    if (getAirplane != null) {
      if (getAirplane.getState() == true) {
        System.out.println("No pueden desembarcar pasajeros mientras los motores del avión están en marcha.");
      } else {
        getAirplane.disembarkationPassenger(amount);
      }
    } else {
      System.out.println("Avión no encontrado.");
    }
  }

}