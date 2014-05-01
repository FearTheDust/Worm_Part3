
package worms.model.program.statements;

import worms.gui.game.IActionHandler;
import worms.model.program.Expression;

/**
 * Statement that prints out an expression.
 * 
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public class PrintStatement implements Statement {
    
    /**
     * Prints the result of the expression.
     * @param e The expression to print the result of.
     */
    public PrintStatement(IActionHandler handler, Expression e) throws IllegalArgumentException {
        if(handler == null)
            throw new IllegalArgumentException("The handler can't be null.");
        this.handler = handler;
        this.expression = e;
    }
    
    private final IActionHandler handler;
    
    private final Expression expression;
    
    @Override
    public void execute() {
        if(expression == null)
            handler.print(expression.toString());
        else
            handler.print(expression.getResult().toString());
    }
    
}
