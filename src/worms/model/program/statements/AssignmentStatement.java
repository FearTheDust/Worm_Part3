package worms.model.program.statements;

import worms.model.Program;
import worms.model.program.Variable;
import worms.model.program.expressions.Expression;
import worms.model.programs.ProgramParser;

/**
 * This statement is an assignment (e.g x := 5).
 * This statement will give the value calculated using the right hand side expression to the variableName (left side) provided.
 * A check is performed during setting the value to make sure the type of the variable matches the type of the expression.
 * 
 * 
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 */
public class AssignmentStatement implements Statement {

	//TODO: Possibly generic??
	
    public AssignmentStatement(ProgramParser<Expression<?>, Statement, Variable<?>> parser, String variableName, Expression<?> rhs) {
        this.parser = parser;
        this.variableName = variableName;
        this.rhs = rhs;
    }

    private final ProgramParser<Expression<?>, Statement, Variable<?>> parser;
    private final String variableName;
    private final Expression<?> rhs;
    
    /**
     * The name of the variable used in this assignment (left hand side).
     * @return 
     */
    public String getVariableName() {
        return this.variableName;
    }

    /**
     * Will perform a check before executing the assignment.
     * It will make sure the type of the expression matches the type of the variable.
     * 
     * @param program The program to perform this assignment on.
     * @return 
     * @see Variable.isValidValueType(Expression)
     */
    @Override
    public boolean execute(Program program) {
        if (program.getCounter() <= 0) {
            return false;
        }
        
        if (parser.getGlobals().containsKey(variableName)) {
            Variable variable = (Variable) parser.getGlobals().get(variableName);
            if (variable.isValidValueType(rhs)) {
                if (program.isFinished()) {
                    variable.setValue(rhs.getResult());
                    program.subtractFromCounter();
                }             
            } else {
                throw new IllegalStateException("The " + rhs.getResult() + " isn't a valid type for variable " + variableName);
            }
        } else {
            throw new IllegalStateException("The variable " + variableName + " isn't an existing variable anymore.");
        }

        return true;
    }

    /**
     * Returns false.
     * @return false.
     */
    @Override
    public boolean hasActionStatement() {
        return false;
    }
    
    /**
     * Returns whether the type of the variable matches the type of the expression.
     * @return False when the variable doesn't exist or when !variable.isValidValueType(expression)
     */
    public boolean isValidVariableType() {
        Variable variable = (Variable) parser.getGlobals().get(variableName);
        if(variable == null)
            return false;
        
        return variable.isValidValueType(rhs);
    }

}
