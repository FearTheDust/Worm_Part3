
package worms.model.program;

/**
 * This class represents an expression.
 * 
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 * 
 * @param <T> The type of the value the expression holds.
 */
public interface Expression<T> {
    
    /**
     * The result of this expression.
     * @return The result.
     */
    public T getResult();
    
    /**
     * The type of the expression.
     * @return The type.
     */
    public Class<T> getType();
    
}
