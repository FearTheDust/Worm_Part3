
package worms.model.program.statements;

import worms.model.Program;
import worms.model.program.BooleanExpression;

/**
 * If statement (condition, then, else)
 * 
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 */
public class IfStatement extends ConditionalStatement {

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

    /**
     * Whether the then or else Statements contain an ActionStatement.
     * @return Whether they do.
     */
    @Override
    public boolean hasActionStatement() {
        return thenStatement.hasActionStatement() || otherwiseStatement.hasActionStatement();
    }

    /**
     * Execute the if or else depending on the condition.
     * (If !program.isFinished() we will execute the if statement & if there our program didn't end earlier we'll execute the else as well.)
     * 
     * @param program The program where we perform this on.
     * @return Whether performing the statements worked out well or failed.
     */
    @Override
    public boolean perform(Program program) {
        /* Perform the statement, if we're searching for the last statement -> perform both if necessary */
        if(program.isFinished()) { //We're executing like usual
            if(condition.getResult()) {
                if(!thenStatement.execute(program))
                    return false;
            } else {
                if(!otherwiseStatement.execute(program))
                   return false;
            }
        } else { //Find the last statement! if not in first, execute second.
            if(!thenStatement.execute(program))
                return false;
            
            if(!program.isFinished()) {
                if(!otherwiseStatement.execute(program))
                    return false;
            }
        }
        
        return true;
    }
    
}
