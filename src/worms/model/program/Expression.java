
package worms.model.program;

/**
 * This class represents an expression.
 * 
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 */
public interface Expression {
    
    /**
     * The result of this expression.
     * @return The result.
     */
    public Object getResult();
    
    /**
     * The type of the expression.
     * @return The type.
     */
    public Class<?> getType();
    
}
