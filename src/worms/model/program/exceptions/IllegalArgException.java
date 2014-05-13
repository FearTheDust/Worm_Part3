package worms.model.program.exceptions;

/**
 * This exception is an IllegalArgumentException but has the option to ask for a line and column number where the exception is caused from.
 * This will be formatted into a String and passed to IllegalArgumentException.
 * 
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public class IllegalArgException extends IllegalArgumentException {

    public IllegalArgException(int line, int column) {
        super("line: " + line + "; column: " + column);
    }
    
    public IllegalArgException(String message) {
        super(message);
    }
    
    public IllegalArgException (int line, int column, String message) {
        super("line: " + line + "; column: " + column + "; " + message);
    }

    public IllegalArgException (Throwable cause) {
        super(cause);
    }

    public IllegalArgException (int line, int column, String message, Throwable cause) {
        super("line: " + line + "; column: " + column + "; " + message, cause);
    }
}
