package worms.model.program.statements;

import worms.model.Program;

/**
 *
 * @author Admin
 */
public abstract class ActionStatement implements Statement {

    /**
     * Performs the action and returns whether it succeeded.
     * It can return false due reaching 0 (program.getCounter()) or if the performing of the action fails.
     * Will only perform the actual action if program.isFinished(), a.k.a. if the last statement was already found.
     * 
     * @param program The program to execute on.
     * 
     * @return Whether it succeeded.
     */
    @Override
    public boolean execute(Program program) {
        if (program.getCounter() <= 0)
            return false;
        
        /* Perform the action, before that, we subtract the statement from the counter */
        if(program.isFinished()) {
            program.subtractFromCounter();
                        
            if(!this.perform(program)) //Performs the actual action.
                return false;
        }
        
        return true;
    }

    /**
     * Perform the actual action.
     *
     * @param program
     * @return
     */
    protected abstract boolean perform(Program program);

    /**
     * Returns true.
     *
     * @return true.
     */
    @Override
    public boolean hasActionStatement() {
        return true;
    }
}
