package worms.model.program.statements;

import worms.model.Program;
import worms.model.program.BooleanExpression;

/**
 *
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public class WhileStatement extends ConditionalStatement {

    public WhileStatement(BooleanExpression condition, Statement body) {
        if (body == null || condition == null) {
            throw new IllegalArgumentException("The body statements or the condition musn't be a null reference.");
        }

        this.condition = condition;
        this.body = body;
    }

    private final BooleanExpression condition;
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
        /* Perform the statement, if we're searching for the last statement -> perform atleast once */
        boolean performedOnceFlag = false;
        while ((!performedOnceFlag && !program.isFinished())|| condition.getResult()) {
           if(!body.execute(program))
               return false;

            performedOnceFlag = true;
        }
        
        return true;
    }
}
