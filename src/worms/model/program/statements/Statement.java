
package worms.model.program.statements;

import worms.model.Program;

/**
 *
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public interface Statement {
    
    /**
     * Execute the statement were the program ended last time.
     * Take into account, the amount of statements processed so far as well as insufficient AP.

     * This may or may not throw any exception as wished.
     * 
     * @param program The program which this statement is executed of.
     * @return Whether the execution of the statement was successful.
     */
    public boolean execute(Program program); /*{
    
    /**
     * Returns whether or not this statement contains or is an ActionStatement.
     * @return 
     */
    public abstract boolean hasActionStatement();
    
}
