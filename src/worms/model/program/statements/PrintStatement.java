
package worms.model.program.statements;

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
    public PrintStatement(Expression e) {
        this.expression = e;
    }
    
    private final Expression expression;
    
    @Override
    public void execute() {
        if(expression == null)
            System.out.println(expression);
        else
            System.out.println(expression.getResult());
    }
    
}
