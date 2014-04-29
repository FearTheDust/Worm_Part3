
package worms.model.program;

import worms.model.Entity;

/**
 * Represents an expression of type Entity.
 * 
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public interface EntityExpression extends Expression {
    
    @Override
    public Entity getResult();
    
}
