package worms.model.program;

/**
 * Represents an expression of type Boolean.
 * 
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public interface BooleanExpression extends Expression {
    
    @Override
    public Boolean getResult();
    
}
