package worms.model.program.statements;

import worms.model.Program;

/**
 * The class representing statements with conditions.
 * e.g. If/Else, WhileStatement, ForEachStatement
 * 
 * When executed a setup is executed to find or keep track of the last performed conditionalStatement.
 * When we're trying to find the last performed statement every conditionalStatement will have to make sure
 * that they check all of their statements and may therefor perform statements even when that wouldn't be the case with such condition state.
 * 
 * ConditionalStatements should set program.getLastStatement() to "this" when a statement inside their body failed (return false) 
 * and no other lastStatement has been set yet. (meaning not 'caught' by a while inside that body.)
 * 
 * If a statement extends this abstract class and the statement is able to contain multipleStatement do not forget to implement MultipleStatement
 * to ensure that all statements in a program are checked for validation.
 * 
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 */
public abstract class ConditionalStatement implements Statement {

    /**
     * Execute the program.
     * 
     * Can change program.getLastStatement() && program.isFinished()
     * 
     * @param program
     * @return Whether the execution was successful. (a.k.a. didn't find last statement here or executed all properly.)
     */
    @Override
    public boolean execute(Program program) {
        /* Check for 1000 statements reached */
        if (program.getCounter() <= 0) {
            if(program.getLastStatement() == null)
                program.setLastStatement(this);
            return false;
        }

        /* Set-up our way back to this statement */
        if (program.isFinished() || program.getLastStatement() == this) {
            program.subtractFromCounter(); //subtract

            //toggle back to finished if we found it.
            if (program.getLastStatement() == this && !program.isFinished()) {
                program.setFinished(true);
                program.setLastStatement(null);
            }

            //last statement we performed.
            //program.setLastStatement(this);
        }
        
        /* Perform the statement's execution */
        if(!this.perform(program)) {
            if(program.getLastStatement() == null)
                program.setLastStatement(this);
            
            return false;
        }

        return true;
    }

    /**
     * Perform the execution of the other statements we are supposed to execute.
     * Beware, if searching for the lastStatement we might have to execute all statements at least once
     * even if the condition is not true/false at this moment.
     * 
     * May throw errors if needed.
     * 
     * @param program The program to execute on.
     * 
     * @return Whether performing the other appropriate statement failed.
     */
    protected abstract boolean perform(Program program);

}
