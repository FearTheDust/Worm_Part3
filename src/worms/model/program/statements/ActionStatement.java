
package worms.model.program.statements;

/**
 *
 * @author Admin
 */
public abstract class ActionStatement implements Statement {
    
    /**
     * Returns true.
     * @return true.
     */
    @Override
    public boolean hasActionStatement() {
        return true;
    }

    //TODO: We will probabaly have to add stuff to check failure/succes etc..
    //TODO: ANY ASSIGNMENT must check if a counter of 1000 is exceeded.
}
