
package worms.model.program.statements;

import worms.model.program.BooleanExpression;
import worms.model.program.Expression;

/**
 *
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public class WhileStatement implements Statement {
    
    public WhileStatement(BooleanExpression condition, Statement body) {
        if(body == null || condition == null)
            throw new IllegalArgumentException("The body statements or the condition musn't be a null reference.");
        
        this.condition = condition;
        this.body = body;
    }
       
    private final BooleanExpression condition;
    private final Statement body;
    
    @Override
    public void execute() {
        while(condition.getResult())
            body.execute();
    }

    /**
     * Returns whether the body contains an ActionStatement.
     * @return Whether it does.
     */
    @Override
    public boolean hasActionStatement() {
        return body.hasActionStatement();
    }
}
