package worms.model.program;

/**
 * Represents an expression of type Boolean.
 * 
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public abstract class BooleanExpression implements Expression {
    
    @Override
    public abstract Boolean getResult();
    
    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }
}
