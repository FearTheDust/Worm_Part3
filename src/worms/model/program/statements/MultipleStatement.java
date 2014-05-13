package worms.model.program.statements;

import java.util.List;

/**
 *
 * This interface represents classes which can contain multiple statements.
 * This interface is used to go trough ALL statements when trying to find additionalErrors as stated in Program.getAdditionalErrrs()
 * 
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 */
public interface MultipleStatement  {
    
    /**
     * This method will return a list of all Statement which this statement contains.
     * @return 
     */
    public List<Statement> getStatements();
    
}
