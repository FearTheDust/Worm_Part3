
package worms.model.program.statements;

import java.util.ArrayList;
import java.util.List;
import worms.model.Program;
import worms.model.program.BooleanExpression;
import worms.model.program.Expression;
import worms.model.program.VariableExpression;

/**
 * If statement (condition, then, else)
 * 
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 */
public class IfStatement extends ConditionalStatement implements MultipleStatement {

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
    
    /**
     * Initialize an If-Statement.
     * @param condition The condition of the If.
     * @param then The statement to execute if the condition is true
     * @param otherwise The statement to execute if the condition is false.
     * @throws IllegalArgumentException
     *          When at least one of the statements or the condition is a null reference.
     */
    public IfStatement(VariableExpression condition, Statement then, Statement otherwise) throws IllegalArgumentException {
        if(then == null || otherwise == null || condition == null)
            throw new IllegalArgumentException("The if/else statements or the condition musn't be a null reference.");
        
        if(condition.getType() != Boolean.class) {
            throw new IllegalArgumentException("The condition of the if-statement must be of type Boolean.");
        }
        
        this.condition = condition;
        this.thenStatement = then;
        this.otherwiseStatement = otherwise;
    }
    
    private final Expression condition;
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
     * 
     * @throws IllegalStateException
     *          When the type of the condition isn't a Boolean.
     *          | condition.getType() != Boolean.class  //Can only be possible for variables.
     */
    @Override
    public boolean perform(Program program) throws IllegalStateException {
        if(condition.getType() != Boolean.class) {
            throw new IllegalStateException("The condition of the if-statement is a variable with a type different of Boolean.class. Type: " + condition.getType() + "; value: " + condition.getResult() +";");
        }
        
        /* Perform the statement, if we're searching for the last statement -> perform both if necessary */
        if(program.isFinished()) { //We're executing like usual
            if((Boolean) condition.getResult()) {
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

    @Override
    public List<Statement> getStatements() {
        ArrayList<Statement> myList = new ArrayList<>();
        myList.add(thenStatement);
        myList.add(otherwiseStatement);
        return myList;
    }
    
}
