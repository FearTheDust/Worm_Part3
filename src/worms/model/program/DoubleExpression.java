package worms.model.program;

/**
 * Represents an expression of type Double.
 * 
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public abstract class DoubleExpression implements Expression {
    
    @Override
    public abstract Double getResult();
 
    @Override
    public Class getType() {
        return Double.class;
    }
}
