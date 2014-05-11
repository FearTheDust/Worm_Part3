
package worms.model.program.statements;

import worms.gui.game.IActionHandler;
import worms.model.Program;
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
     * @param handler The ActionHandler to use for printing.
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
    
    /**
     * Execute the printer.
     * @param program
     * @return When we had executed 1000 statements already (program.getCounter() <= 0)
     *          Set the program's lastStatement to this & return false.
     * 
     * @return When the program's state is isFinished() or the program's last executed statement is this.
     *          Execute the statement code, subtract from the program's counter.
     *              If the program wasn't finished && this is the last statement it had executed,
     *              toggle finished back to true
     *          return true
     */
    @Override
    public boolean execute(Program program) {
        if (program.getCounter() <= 0)
            return false;
        
        if (program.isFinished()) {
            this.perform();
            program.subtractFromCounter();
        }
        
        return true;
    }
    
    /**
     * Perform the print action.
     */
    private void perform() {
        if (expression == null)
            handler.print("null");
        
        Object result = expression.getResult();
        if(result == null)
            handler.print("null");
        else
            handler.print(result.toString());
    }

    /**
     * Returns false. 
     * @return false.
     */
    @Override
    public boolean hasActionStatement() {
       return false;
    }
    
}
