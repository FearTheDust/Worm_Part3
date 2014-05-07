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
     *
     * @return false.
     */
    @Override
    public boolean hasActionStatement() {
        return false;
    }

}
