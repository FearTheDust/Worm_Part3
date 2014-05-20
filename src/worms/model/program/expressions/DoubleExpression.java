package worms.model.program.expressions;

/**
 * Represents an expression of type Double.
 * 
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public abstract class DoubleExpression implements Expression<Double> {
    
    @Override
    public abstract Double getResult();
 
    @Override
    public final Class<Double> getType() {
        return Double.class;
    }
}
