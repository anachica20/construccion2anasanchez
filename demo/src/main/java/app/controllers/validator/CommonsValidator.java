package app.controllers.validator;

public abstract class CommonsValidator {
    
    public CommonsValidator() {
    }

    // Método para validar una cadena vacía
    public void isValidString(String element, String value) throws Exception {
        if (value == null || value.equals("")) {
            throw new Exception(element + " no puede ser un valor vacío.");
        }
    }

    // Método para validar un entero
    public int isValidInteger(String element, String value) throws Exception {
        isValidString(element, value);  // Verificar si la cadena no está vacía
        try {
            return Integer.parseInt(value);  // Intentar convertir a int
        } catch (NumberFormatException e) {  // Capturar error si la conversión falla
            throw new Exception(element + " debe ser un valor entero válido.");
        }
    }

    // Método para validar un long
    public long isValidLong(String element, String value) throws Exception {
        isValidString(element, value);  // Verificar si la cadena no está vacía
        try {
            return Long.parseLong(value);  // Intentar convertir a long
        } catch (NumberFormatException e) {  // Capturar error si la conversión falla
            throw new Exception(element + " debe ser un valor número válido.");
        }
    }

    // Método para validar un double
    public double isValidDouble(String element, String value) throws Exception {
        isValidString(element, value);  // Verificar si la cadena no está vacía
        try {
            return Double.parseDouble(value);  // Intentar convertir a double
        } catch (NumberFormatException e) {  // Capturar error si la conversión falla
            throw new Exception(element + " debe ser un valor decimal válido.");
        }
    }
}
