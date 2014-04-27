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
public class IllegalTypeException extends IllegalArgumentException {
    
    public IllegalTypeException () {
        super();
    }

    public IllegalTypeException (String message) {
        super(message);
    }

    public IllegalTypeException (Throwable cause) {
        super(cause);
    }

    public IllegalTypeException (String message, Throwable cause) {
        super(message, cause);
    }
    
}
