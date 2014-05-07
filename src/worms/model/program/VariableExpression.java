/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worms.model.program;

import worms.model.program.exceptions.IllegalArgException;
import worms.model.programs.ProgramParser;

/**
 *
 * @author Admin
 */
public class VariableExpression implements Expression {

    public VariableExpression(ProgramParser<?,?,Variable> parser, String variableName, int line, int column) {
        if(parser == null || variableName == null)
            throw new IllegalArgumentException("The parser & the variableName musn't be a null reference.");
        
        this.parser = parser;
        this.variableName = variableName;
        this.variable = null;
        this.line = line;
        this.column = column;
    }

    public VariableExpression(ProgramParser parser, Variable variable, int line, int column) {
        if(parser == null || variable == null)
            throw new IllegalArgumentException("The parser & the variable musn't be a null reference.");
                
        this.parser = parser;
        this.variableName = null;
        this.variable = variable;
        this.line = line;
        this.column = column;
    }

    private final Variable variable;
    private final ProgramParser<?,?,Variable> parser;
    private final String variableName;
    
    private final int line;
    private final int column;

    @Override
    public Object getResult() {
        if(this.variable != null) {
            return this.variable.getValue();
        } else {
            Variable obtainedVar = parser.getGlobals().get(variableName);
            if (obtainedVar == null)
                throw new IllegalArgException(line, column, variableName + " does not exist.");
            return obtainedVar.getValue();
        }
    }

    @Override
    public Class getType() {
        if(this.variable != null) {
            return this.variable.getType();
        } else {
            Variable obtainedVar = parser.getGlobals().get(variableName);
            if (obtainedVar == null)
                throw new IllegalArgException(line, column, variableName + " does not exist.");
            return obtainedVar.getType();
        }
    }

}
