/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worms.model.program.exceptions;

/**
 *
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public class IllegalArgException extends IllegalArgumentException {

    public IllegalArgException(int line, int column) {
        super("line: " + line + "; column: " + column);
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
