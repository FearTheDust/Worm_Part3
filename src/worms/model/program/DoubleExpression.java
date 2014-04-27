package worms.model.program;

/**
 * Represents an expression of type Double.
 * 
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public interface DoubleExpression extends Expression {
    
    @Override
    public Double getResult();
 
}
