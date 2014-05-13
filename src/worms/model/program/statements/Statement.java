
package worms.model.program.statements;

import worms.model.Program;

/**
 *
 * A statement should not subtract a cost from the program when executing a statement while looking for the last statement.
 * A statement should return true when looking for a statement which this statement doesn't contain or is.
 * 
 * When a statement is the last statement program.isFinished() should be true when returning from executing and program.getLastStatement() == null
 * 
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public interface Statement {
    
    /**
     * Execute the statement were the program ended last time.
     * Take into account, the amount of statements processed so far as well as insufficient AP.
     *
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
    public boolean hasActionStatement();
    
}
