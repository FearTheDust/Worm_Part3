package worms.model.program.expressions;

import worms.model.program.Variable;
import worms.model.program.exceptions.IllegalArgException;
import worms.model.programs.ProgramParser;

/**
 * This expression simply contains a variable. The value and type will be the one of the variable.
 * 
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 * @param <T> The type of the value the variable holds.
 */
public class VariableExpression<T> implements Expression<T> {

    /**
     * Initialize a VariableExpression with a certain parser and a name of the variable.
     * This expression will try to look into the globalList of the parser to find the variable with that name.
     * 
     * @param line The line where this expression is used.
     * @param column The column where this expression is used.
     * @param parser The parser which contains our list of variables.
     * @param variableName The name of the variable.
     */
    public VariableExpression(int line, int column, ProgramParser<?,?,Variable> parser, String variableName) {
        if(parser == null || variableName == null)
            throw new IllegalArgumentException("The parser & the variableName musn't be a null reference.");
        
        this.parser = parser;
        this.variableName = variableName;
        this.variable = null;
        this.line = line;
        this.column = column;
    }

    /**
     * Initialize a VariableExpression of a certain variable.
     * This expression will try to look into the globalList of the parser to find the variable with that name.
     * 
     * @param line The line where this expression is used.
     * @param column The column where this expression is used.
     * @param variableName The name of the variable.
     * @param variable The variable itself.
     */
    public VariableExpression(int line, int column, String variableName, Variable<T> variable) {
        if(variable == null)
            throw new IllegalArgException(line, column, "The variable " + variableName + " does not exist.");
                
        this.parser = null;
        this.variableName = variableName;
        this.variable = variable;
        this.line = line;
        this.column = column;
    }

    private final Variable<T> variable;
    private final ProgramParser<?,?,Variable> parser;
    private final String variableName;
    
    private final int line;
    private final int column;

    /**
     * Can return an error when trying to retrieve the variable from the list of global variables.
     * That is when a name was passed to the constructor instead of the variable itself.
     * @return 
     */
    @Override
    public T getResult() {
        if(variable != null) {
            return this.variable.getValue();
        } else {
            Variable<T> obtainedVar = parser.getGlobals().get(variableName);
            if (obtainedVar == null)
                throw new IllegalArgException(line, column, variableName + " does not exist.");
            
            return obtainedVar.getValue();
        }
    }

    /**
     * Can return an error when trying to retrieve the variable from the list of global variables.
     * That is when a name was passed to the constructor instead of the variable itself.
     * @return 
     */
    @Override
    public Class<T> getType() {
        if(this.variable != null) {
            return this.variable.getType();
        } else {
            Variable<T> obtainedVar = parser.getGlobals().get(variableName);
            if (obtainedVar == null)
                throw new IllegalArgException(line, column, variableName + " does not exist.");
            return obtainedVar.getType();
        }
    }

}
