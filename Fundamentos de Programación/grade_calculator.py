""" El script define una función llamada "get_validated_num" que valida si el
valor ingresado es un número válido y lo convierte a un número de tipo flotante
Además, se define una clase llamada "GradeCalculator" que contiene cuatro
métodos: "input_grades", "get_average_grade", "get_max_grade" y
"get_min_grade". La clase "GradeCalculator" se utiliza para calcular el
promedio, la nota máxima y la nota mínima de las 3 calificaciones
ingresadas por el usuario. También se crea una instancia de la clase
"GradeCalculator" y se llama a los métodos para calcular el promedio, la
nota máxima y la nota mínima de las 3 calificaciones ingresadas por el usuario.
Si se producen más de 5 intentos fallidos, se imprime un mensaje de error. """


def get_validated_num(value, error_message):
    """  La función recibe dos argumentos: el valor a validar y un
        mensaje de error a imprimir si el valor ingresado no es válido. """
    is_valid = False  # establece la condición de validez en False
    # Se utiliza para limitar el número de intentos permitidos
    attempts = 0
    # entra en el bucle mientras la condición de validez sea False
    while not is_valid and attempts < 5:
        attempts += 1
        if len(value) > 0:  # si el valor ingresado tiene longitud mayor a 0
            try:
                # Convierte el "value" a tipo flotante y lo retorna si
                # el valor ingresado no inicia con un signo y es mayor a 0,
                # sino arroja un mensaje de error y solicita un nuevo valor
                if float(value) > 0 and not value.startswith(("+", "-", ".")):
                    # Cambia la condición a True para salir del bucle
                    is_valid = True
                    # si el valor es válido retorna el valor como tipo flotante
                    return float(value)
                else:  # solicita un nuevo valor si el ingresado no es válido
                    value = input(error_message)
            # si no se puede convertir a flotante el valor ingresado
            except ValueError:
                value = input(error_message)  # solicita un nuevo valor
        else:  # si el valor ingresado tiene longitud 0
            value = input(error_message)  # solicita un nuevo valor
    return False  # Retorna False cuando agote los 5 intentos


class GradeCalculator:
    """Esta clase contiene todos los métodos necesarios para el
      cálculo del promedio, nota máxima y nota mínima."""

    def __init__(self):
        self.__grades = []  # inicializa la lista de calificaciones vacía

    def input_grades(self):
        """ Este método permite ingresar y almacenar las notas """
        # Lista provicional que almacena las calificaciones mientras se evaluan
        grades = []
        for i in range(3):  # solicita el ingreso de 3 calificaciones
            grade = input(f"Ingresa la calificación {i +1}: ")
            # valida la calificación ingresada usando la función auxiliar
            validated_grade = get_validated_num(
                grade, "Error: Ingresa una nota válida entre 0 y 7: ")
            # Después de 5 intentos validated_grade retorna False y se
            # detiene la ejecución
            if not validated_grade:
                break
            # Si se ingreso una calificación válida la guarda en la lista
            else:
                grades.append(validated_grade)
        # Si se ingresaron 3 calificaciones válidas las guarda en la lista
        # principal y devuelve True
        if len(grades) == 3:
            self.__grades = grades
            return True
        # Si se ingresaron menos de 3 calificaciones retorna False
        else:
            return False

    def get_average_grade(self):
        """ Permite obtener el promedio de notas """
        if len(self.__grades) > 0:  # Verifica que la lista no este vacía
            # retorna el promedio de notas redondeados a 2 decimales
            return round((sum(self.__grades) / len(self.__grades)), 2)
        else:
            return None

    def get_max_grade(self):
        """ Permite obtener la nota máxima """
        if len(self.__grades) > 0:  # Verifica que la lista no este vacía
            return max(self.__grades)  # retorna la nota máxima de la lista
        else:
            return None

    def get_min_grade(self):
        """ Permite obtener la nota mínima """
        if len(self.__grades) > 0:  # Verifica que la lista no este vacía
            return min(self.__grades)  # retorna la nota mínima de la lista
        else:
            return None


grade_calculator = GradeCalculator()  # Instancia de la clase

# Imprime un error si se agotaron los 5 intentos para ingresar un valor
if not grade_calculator.input_grades():
    print("Error: Demasiados Intentos Fallidos")
# Si el usuario introdujo 3 calificaciones válidas se ejecutan los métodos
else:
    average = grade_calculator.get_average_grade()
    max_grade = grade_calculator.get_max_grade()
    min_grade = grade_calculator.get_min_grade()
    # Imprime el promedio
    print(f"El promedio de las notas ingresadas es: {average}")
    # Imprime la califiación máxima
    print(f"La nota máxima ingresada es: {max_grade}")
    # Imprime la calificación mínima
    print(f"La nota mínima ingresada es: {min_grade}")
