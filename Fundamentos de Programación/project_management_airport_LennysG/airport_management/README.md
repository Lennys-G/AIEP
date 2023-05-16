# **SIMULADOR DE AEROPUERTO**

Este programa es una aplicación simple basada en consola que simula un sistema de gestión de aeropuertos. Permite a los usuarios realizar varias operaciones, como listar todos los identificadores de aviones, verificar el número actual de pasajeros, verificar los asientos disponibles, buscar datos de aviones por identificador, embarcar y desembarcar pasajeros y salir del programa.

El programa utiliza la clase Scanner para leer la entrada del usuario y manejar errores con el bloque try-catch. También tiene métodos para leer y validar la entrada del usuario, como readOption, readNumber y readContinueOption.

El método principal crea una instancia de la clase Airport, imprime un mensaje de bienvenida al usuario y entra en un bucle while que muestra las opciones del menú y solicita al usuario que seleccione una opción hasta que elija salir. Dependiendo de la opción seleccionada, el programa llama al método correspondiente de la clase Airport para realizar la operación deseada.

El programa consta dos clases: **"Airplane"** y **"Airport"**.

---

## **CLASE AIRPLANE**

La clase **"Airplane"** tiene atributos que permiten identificar el avión, conocer la capacidad máxima de pasajeros que puede transportar, la cantidad actual de pasajeros y el estado del avión. Además, tiene métodos para obtener y actualizar información sobre los atributos, y para mostrar la información formateada del avión.

La clase **"Airplane"** tiene los siguientes atributos:

- "id" (entero que identifica el avión).
- "maxNumberPassenger" (Entero que indica la capacidad máxima de pasajeros que el avión puede transportar).
- "currentNumberPassenger" (Entero que indica la cantidad actual de pasajeros en el avión).
- "state" (Booleano que indica el estado actual del avión, si estan en marchas los motores o no).
- "random" (objeto de la clase Random para generar números aleatorios).
- "previousId" (Se utiliza para asignar un identificador único a cada avión creado).

La clase **"Airplane"** tiene un constructor que asigna valores aleatorios a los atributos "maxNumberPassenger", "currentNumberPassenger" y "state".

La clase **"Airplane"** tiene los siguientes métodos:

- Airplane(): constructor que inicializa los atributos de la clase con valores aleatorios.
- getId(): método que retorna el id del avión.
- getMaxNumberPassenger(): método que retorna el número máximo de pasajeros que puede llevar el avión.
- getCurrentNumberPassenger(): método que retorna el número actual de pasajeros que lleva el avión.
- getState(): método que retorna el estado del avión.
- getAvailableSpaces(): método que retorna la cantidad de asientos disponibles que tiene el avión.
- disembarkationPassenger(int amount): método que desembarca pasajeros del avión, actualizando el número actual de pasajeros.
- embarkationPassenger(int amount): método que embarca pasajeros en el avión, actualizando el número actual de pasajeros.
- formatDataAirplane(): método que retorna una cadena con la información formateada del avión, incluyendo el id, número máximo y actual de pasajeros, y el estado del avión.

---

## **CLASE AIRPORT**

La clase **"Airport"** tiene atributos que identifican el nombre del aeropuerto y un arreglo de objetos de la clase "Airplane", que representan los aviones en el aeropuerto. Además, tiene métodos para obtener información sobre los aviones en el aeropuerto, buscar información de un avión por su identificador, embarcar y desembarcar pasajeros de un avión, y mostrar información resumida sobre los aviones en el aeropuerto.

La clase **"Airport"** tiene los siguientes atributos:

- "nameAirport" (Cadena de texto que indica el nombre del aeropuerto).
- "airplanes" (Un arreglo de objetos de la clase "Airplane").

La clase "Airport" tiene un constructor que inicializa el nombre del aeropuerto y crea un arreglo de tamaño 5 de objetos Airplane, que representan los aviones en el aeropuerto.

La clase **"Airport"** tiene los siguientes métodos:

- getNameAirport(): es un método que devuelve el nombre del aeropuerto.
- showIdAirplanes(): es un método que muestra por consola el id de todos los aviones del aeropuerto.
- getTotalPassengersCount(): es un método que muestra por consola la cantidad de pasajeros actuales en todos los aviones del aeropuerto.
- getTotalAvailableSpaces(): es un método que muestra por consola la cantidad de asientos disponibles en todos los aviones del aeropuerto.
- getAirplaneById(int id): es un método que recibe el id de un avión y devuelve un objeto de la clase Airplane con ese id, si existe en el arreglo de aviones del aeropuerto.
- searchAirplaneById(int id): es un método que muestra por consola la información de un avión del aeropuerto, identificado por su id.
- embarkation(int id, int amount): es un método que recibe el id de un avión y la cantidad de pasajeros a subir, y sube la cantidad de pasajeros a ese avión, siempre y cuando los motores del avión no estén en marcha.
- disembarkation(int id, int amount): es un método que recibe el id de un avión y la cantidad de pasajeros a bajar, y baja la cantidad de pasajeros de ese avión, siempre y cuando los motores del avión no estén en marcha.

En resumen, la clase "Airplane" representa un avión individual, mientras que la clase "Airport" representa un aeropuerto y gestiona los aviones que están en él.
