
package worms.model.program;

import worms.model.Entity;

/**
 * Represents an expression of type Entity.
 * 
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public abstract class EntityExpression implements Expression {
    
    @Override
    public abstract Entity getResult();
    
    @Override
    public Class getType() {
        return Entity.class;
    }
}
