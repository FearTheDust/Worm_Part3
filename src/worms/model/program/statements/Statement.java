
package worms.model.program.statements;

/**
 *
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public interface Statement {
    
    /**
     * Execute the statement.
     * This may or may not throw any exception as wished.
     */
    public void execute();
    
    /**
     * Returns whether or not this statement contains or is an ActionStatement.
     * @return 
     */
    public boolean hasActionStatement();
    
}
