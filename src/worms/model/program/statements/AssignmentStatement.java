package worms.model.program.statements;

import worms.model.Program;
import worms.model.program.Expression;
import worms.model.program.Variable;
import worms.model.programs.ProgramParser;

/**
 *
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 */
public class AssignmentStatement implements Statement {

    public AssignmentStatement(ProgramParser parser, String variableName, Expression rhs) {
        this.parser = parser;
        this.variableName = variableName;
        this.rhs = rhs;
    }

    private final ProgramParser parser;
    private final String variableName;
    private final Expression rhs;

    @Override
    public boolean execute(Program program) {
        if (program.getCounter() <= 0) {
            return false;
        }
        
        if (program.isFinished()) {
            if (parser.getGlobals().containsKey(variableName)) {
                Variable variable = (Variable) parser.getGlobals().get(variableName);
                if (variable.isValidValueType(rhs)) {
                    
                    //TODO: Move the isFinished if in here so the error gets thrown even if it doesn't get "executed"?
                    variable.setValue(rhs.getResult());
                    
                } else {
                    throw new IllegalStateException("The " + rhs.getResult() + " isn't a valid type for variable " + variableName);
                }
            } else {
                throw new IllegalStateException("The variable " + variableName + " isn't an existing variable anymore.");
            }
            program.subtractFromCounter();
        }
        return true;
    }

    /*public void perform() throws IllegalStateException {
        if (parser.getGlobals().containsKey(variableName)) {
            Variable variable = (Variable) parser.getGlobals().get(variableName);
            if (variable.isValidValueType(rhs)) {
                variable.setValue(rhs.getResult());
            } else {
                throw new IllegalStateException("The " + rhs.getResult() + " isn't a valid type for variable " + variableName);
            }
        } else {
            throw new IllegalStateException("The variable " + variableName + " isn't an existing variable anymore.");
        }
    }*/

    /**
     * Returns false.
     *
     * @return false.
     */
    @Override
    public boolean hasActionStatement() {
        return false;
    }

}
