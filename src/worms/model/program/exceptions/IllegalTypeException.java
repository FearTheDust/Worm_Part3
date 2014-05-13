package worms.model.program.exceptions;

/**
 * IllegalTypeException represents an exception made on types.
 * e.g if(DoubleExpression) { } else { }
 * 
 * This exception extends IllegalArgException.
 * 
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public class IllegalTypeException extends IllegalArgException {
    
    public IllegalTypeException (int line, int column) {
        super(line, column);
    }
    
    public IllegalTypeException(String message) {
        super(message);
    }

    public IllegalTypeException (int line, int column, String message) {
        super(line, column, message);
    }

    public IllegalTypeException (Throwable cause) {
        super(cause);
    }

    public IllegalTypeException (int line, int column, String message, Throwable cause) {
        super(line, column, message, cause);
    }
    
}
