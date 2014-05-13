
package worms.model.program.statements;

import java.util.List;
import worms.model.Program;

/**
 * Represents a statement which is a list of statements.
 * When executed it executes each statement on the list, one by one.
 * 
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 */
public class SequencedStatement implements Statement, MultipleStatement {

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
    
    /***
     * Execute all statements.
     * When a statement fails, stop & returns false.
     * 
     * @param program The program to execute the statements on.
     * 
     * @return False when a statement failed.
     */
    @Override
    public boolean execute(Program program) {
        for(Statement statement : statements) {
            if(!statement.execute(program))
                return false;
        }
        return true;
    }

    /**
     * Checks each statement whether or not it contains an ActionStatement.
     * @return True if at least one does.
     */
    @Override
    public boolean hasActionStatement() {
        for(Statement statement : statements) {
            if(statement.hasActionStatement())
                return true;
        }
        
        return false;
    }

    @Override
    public List<Statement> getStatements() {
        return statements;
    }
    
}
