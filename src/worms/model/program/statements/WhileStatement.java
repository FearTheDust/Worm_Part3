package worms.model.program.statements;

import java.util.ArrayList;
import java.util.List;

import worms.model.Program;
import worms.model.program.expressions.BooleanExpression;
import worms.model.program.expressions.Expression;
import worms.model.program.expressions.VariableExpression;

/**
 * A while statement. (e.g. while())
 * 
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public class WhileStatement extends ConditionalStatement implements MultipleStatement {

    public WhileStatement(BooleanExpression condition, Statement body) {
        if (body == null || condition == null) {
            throw new IllegalArgumentException("The body statements or the condition musn't be a null reference.");
        }

        this.condition = condition;
        this.body = body;
    }
    
    public WhileStatement(VariableExpression<?> condition, Statement body) {
        if (body == null || condition == null) {
            throw new IllegalArgumentException("The body statements or the condition musn't be a null reference.");
        }
        if(condition.getType() != Boolean.class) {
            throw new IllegalArgumentException("The condition of the while statement must be of type Boolean.");
        }

        this.condition = condition;
        this.body = body;
    }

    private final Expression<?> condition;
    private final Statement body;

    /**
     * Returns whether the body contains an ActionStatement.
     *
     * @return Whether it does.
     */
    @Override
    public boolean hasActionStatement() {
        return body.hasActionStatement();
    }
    
    @Override
    public boolean perform(Program program) {
        if(condition.getType() != Boolean.class) {
            throw new IllegalStateException("The condition of the if-statement is a variable with a type different of Boolean.class. Type: " + condition.getType() + "; value: " + condition.getResult() +";");
        }
               
        /* Perform the statement, if we're searching for the last statement -> perform atleast once */
        boolean performedOnceFlag = false;
        while ((!performedOnceFlag && !program.isFinished()) || ((Boolean) condition.getResult() && program.isFinished())) {
           if(!body.execute(program))
               return false;

            performedOnceFlag = true;
        }
        
        return true;
    }

    @Override
    public List<Statement> getStatements() {
        ArrayList<Statement> myList = new ArrayList<>();
        myList.add(body);
        return myList;
    }
}
