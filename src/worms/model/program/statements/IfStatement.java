
package worms.model.program.statements;

import worms.model.program.BooleanExpression;
import worms.model.program.Expression;

/**
 * If statement (condition, then, else)
 * 
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 */
public class IfStatement implements Statement {

    /**
     * Initialize an If-Statement.
     * @param condition The condition of the If.
     * @param then The statement to execute if the condition is true
     * @param otherwise The statement to execute if the condition is false.
     * @throws IllegalArgumentException
     *          When at least one of the statements or the condition is a null reference.
     */
    public IfStatement(BooleanExpression condition, Statement then, Statement otherwise) throws IllegalArgumentException {
        if(then == null || otherwise == null || condition == null)
            throw new IllegalArgumentException("The if/else statements or the condition musn't be a null reference.");
        
        this.condition = condition;
        this.thenStatement = then;
        this.otherwiseStatement = otherwise;
    }
    
    private final BooleanExpression condition;
    private final Statement thenStatement;
    private final Statement otherwiseStatement;
    
    @Override
    public void execute() {
        if(condition.getResult())
            thenStatement.execute();
        else
            otherwiseStatement.execute();
    }
    
}
