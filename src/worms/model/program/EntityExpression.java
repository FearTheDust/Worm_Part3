
package worms.model.program;

import worms.model.Entity;

/**
 * Represents an expression of type Entity.
 * 
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public abstract class EntityExpression implements Expression<Entity> {
    
    @Override
    public abstract Entity getResult();
    
    @Override
    public final Class<Entity> getType() {
        return Entity.class;
    }
}
