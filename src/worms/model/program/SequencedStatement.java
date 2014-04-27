
package worms.model.program;

import java.util.List;

/**
 * Represents a statement which is a list of statements.
 * When executed it executes each statement on the list, one by one.
 * 
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 */
public class SequencedStatement implements Statement {

    /**
     * A statement which is a list of statements
     * 
     * @param statements The list of statements
     * 
     * @throws NullPointerException
     *          When statements equals null
     */
    public SequencedStatement(List<Statement> statements) throws NullPointerException {
        if(statements == null)
            throw new NullPointerException();
        //As it's a List instead of ArrayList, we can't clone or make a new list out of that list to prevent misuse.
        this.statements = statements;
    }
    
    private final List<Statement> statements;
    
    @Override
    public void execute() {
        for(Statement statement : statements)
            statement.execute();
    }
    
}
