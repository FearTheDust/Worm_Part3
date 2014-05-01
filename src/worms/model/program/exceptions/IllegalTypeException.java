/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worms.model.program.exceptions;

/**
 * IllegalTypeException represents an exception made on types.
 * e.g if(DoubleExpression) { } else { }
 * 
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public class IllegalTypeException extends IllegalArgException {
    
    public IllegalTypeException (int line, int column) {
        super(line, column);
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
