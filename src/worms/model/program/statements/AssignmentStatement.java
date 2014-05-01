/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worms.model.program.statements;

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
    public void execute() throws IllegalStateException {
        if(parser.getGlobals().containsKey(variableName)) {
            Variable variable = (Variable) parser.getGlobals().get(variableName);
            if(variable.isValidValueType(rhs))
                variable.setValue(rhs.getResult());
            else
                throw new IllegalStateException("The " + rhs.getResult() + " isn't a valid type for variable " + variableName);
        } else {
            throw new IllegalStateException("The variable " + variableName + " isn't an existing variable anymore.");
        }
    }
    
}
