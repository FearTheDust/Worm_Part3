package worms.model.program.expressions;

/**
 * Represents an expression of type Boolean.
 * 
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public abstract class BooleanExpression implements Expression<Boolean> {
    
    @Override
    public abstract Boolean getResult();
    
    @Override
    public final Class<Boolean> getType() {
        return Boolean.class;
    }
}
