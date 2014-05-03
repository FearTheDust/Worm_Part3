package worms.model.program.statements;

import java.util.Collection;
import worms.model.Entity;
import worms.model.Program;
import worms.model.program.Variable;
import worms.model.programs.*;
import worms.model.programs.ProgramFactory.ForeachType;
import static worms.model.programs.ProgramFactory.ForeachType.*;

/**
 *
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 */
public class ForEachStatement extends ConditionalStatement {

    public ForEachStatement(ProgramFactory factory, ProgramFactory.ForeachType type, Variable variable, Statement body) {
        this.factory = factory;
        this.type = type;
        this.variable = variable;
        this.body = body;
    }

    /**
     * ForEach statement.
     *
     * @param factory
     * @param type
     * @param variableName
     * @param parser
     * @param body
     */
    public ForEachStatement(ProgramFactory factory, ProgramFactory.ForeachType type, String variableName, ProgramParser parser, Statement body) {
        Variable givenVariable = (Variable) parser.getGlobals().get(variableName);

        if (givenVariable == null) {
            throw new IllegalArgumentException(variableName + " is not an existing variable in the given parser.");
        }

        if (!isValidArgument(factory, type, givenVariable, body)) {
            throw new IllegalArgumentException("The body statement contains an action statement.");
        }

        this.factory = factory;
        this.type = type;
        this.variable = givenVariable;
        this.body = body;
    }

    private final ProgramFactory factory;
    private final ForeachType type;
    private final Variable variable;
    private final Statement body;

    /**
     * Checks whether the given arguments are valid arguments to construct a for
     * each loop with. Checks if any reference is null. Checks for the existence
     * of action statement in the body.
     *
     * @param factory The factory which created this.
     * @param type The type to iterate over.
     * @param variable The variable we change
     * @param body The body we execute.
     *
     * @return Whether it is valid.
     */
    public static boolean isValidArgument(ProgramFactory factory, ForeachType type, Variable variable, Statement body) {
        if (factory == null || type == null || variable == null || body == null || !(variable instanceof Entity)) {
            return false;
        }

        return !body.hasActionStatement();
    }

    /**
     * Perform the actions a ForEachStatement should.
     * Which is, create a list, iterate over it and perform the body on it.
     * This may throw a nullPointerException when "factory.getWorm().getWorld()"
     * does.
     * 
     * @param program The program to perform on.
     * 
     * @return Whether the execution of certain statements failed.
     */
    @Override
    public boolean perform(Program program) {
        //To be 100% secure we should check if body does not contain an ActionStatement once again.
        //Since someone could've changed the reference.
        Collection<?> collection = null;

        switch (type) {
            case WORM:
                collection = factory.getWorm().getWorld().getWorms();
                break;
            case FOOD:
                collection = factory.getWorm().getWorld().getFood();
                break;
            case ANY:
                collection = factory.getWorm().getWorld().getGameObjects();
                break;
            default:
                throw new IllegalStateException("No valid type was found.");
        }

        /* Execute atleast once if not finished, else execute for all items in the collection. */
        boolean performedOnceFlag = false;
        for (Object obj : collection) {
            if(performedOnceFlag && !program.isFinished()) //Already checked for lastStatement.
                break;
            
            variable.setValue(obj);
            if (!body.execute(program))
                return false;
 
            performedOnceFlag = true;
        }
        
        return true;
    }

    /**
     * Checks if the body contains an ActionStatement.
     *
     * @return Whether the body does.
     */
    @Override
    public boolean hasActionStatement() {
        return body.hasActionStatement();
    }

    /*@Override
    public void perform() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

}
