""" Script para hacer calculos de aritmética básica  """


def get_validated_num(value):
    """ Esta función recibe un valor y válida mediante un loop que sea un
    número o que coincida con la lista de operadores """
    message_error = "Ingresa un número válido y mayor que 0: "
    while True:
        if len(value) > 0:
            try:
                return float(value) if not value.startswith(
                    ("+", "-", ".")) and float(value) > 0 else get_validated_num(
                    input(message_error))
            except ValueError:
                value = input(message_error)
        else:
            value = input(message_error)


def get_validated_operator(value):
    """ Esta función recibe un valor y valida que sea un operador """
    list_operation = ["+", "-", "*", "/"]
    return value if value in list_operation else get_validated_operator(
        input("Ingresa un operador válido: "))


def math_operation(value1, value2, operation):
    """ Esta función toma dos números y un operador y devuelve un resultado"""
    operations = {
        "+": lambda value1, value2: value1+value2,
        "-": lambda value1, value2: value1-value2,
        "*": lambda value1, value2: value1*value2,
        "/": lambda value1, value2: value1/value2,
    }
    result_operation = operations[operation](value1, value2)
    if result_operation % 1 == 0:
        return int(result_operation)
    else:
        return result_operation


num1 = get_validated_num(input("Ingresa el primer número: "))
num2 = get_validated_num(input("Ingresa el segundo número: "))
operator = get_validated_operator(
    input("""Ingrese el signo de la operación que desea realizar
          + Suma, - Resta, * Multiplicación, / División: """))
result = math_operation(num1, num2, operator)

print(f"El resultado de la operación es: {result}")
