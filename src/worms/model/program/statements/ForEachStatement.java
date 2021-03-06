package worms.model.program.statements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import worms.model.Entity;
import worms.model.Program;
import worms.model.program.ProgramFactoryImpl;
import worms.model.program.Variable;
import worms.model.programs.*;
import worms.model.programs.ProgramFactory.ForeachType;
import static worms.model.programs.ProgramFactory.ForeachType.*;

/**
 * This Statements represents a java For-each loop. It can iterate over types defined by ProgramFactory.ForeachType.
 * This class extends ConditionalStatement since that class handles when this class does the actual (when) performing of the body.
 * This class implements MultipleStatement as this class can have multiple statements in its body.
 * 
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 */
public class ForEachStatement extends ConditionalStatement implements MultipleStatement {

    /**
     * ForEach statement.
     *
     * @param factory The factory we use to retrieve the worm its world.
     * @param type The type of for each we loop trough
     * @param variableName The name of the variable we change.
     * @param body The statement we will execute each time.
     * 
     * @throws IllegalArgumentException
     *          | !isValidArgument(factory, type, variableName, body) || factory.getProgramParser() == null
     */
    public ForEachStatement(ProgramFactoryImpl factory, ProgramFactory.ForeachType type, String variableName, Statement body) throws IllegalArgumentException {
        if(body.hasActionStatement())
            throw new IllegalArgumentException("The body statement contains an action statement");
        if (containsNullReference(factory, type, variableName, body)) {
            throw new IllegalArgumentException("a null reference was passed.");
        }
        
        if(factory.getProgramParser() == null)
            throw new IllegalArgumentException("The factory provided did not have a ProgramParser");

        this.factory = factory;
        this.type = type;
        this.variableName = variableName;
        this.body = body;
    }

    private final ProgramFactoryImpl factory;
    private final ForeachType type;
    private final String variableName;
    private final Statement body;
    
    /**
     * The name of the variable we use in this for each statement.
     * @return 
     */
    public String getVariableName() {
        return this.variableName;
    }

    /**
     *  Checks if any reference is null. 
     *
     * @param factory The factory which created this.
     * @param type The type to iterate over.
     * @param variableName The variable's name we change
     * @param body The body we execute.
     *
     * @return Whether it contains a null reference.
     */
    public static boolean containsNullReference(ProgramFactoryImpl factory, ForeachType type, String variableName, Statement body) {
        return (factory == null || type == null || variableName == null || body == null);
    }

    /**
     * Perform the actions a ForEachStatement should.
     * Which is, create a list, iterate over it and perform the body on it.
     * 
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
        Variable givenVariable = (Variable) this.factory.getProgramParser().getGlobals().get(variableName);
        
        if(givenVariable == null)
            throw new IllegalArgumentException("The variable used in the for each statement does not exist.");
        
        if(givenVariable.getType() != Entity.class)
            throw new IllegalArgumentException("The variable used in the for each statement is not of the Entity type.");
        
        Collection<?> collection = null;

        switch (type) {
            case WORM:
                collection = factory.getWorm().getWorld().getWorms();
                break;
            case FOOD:
                collection = factory.getWorm().getWorld().getFood();
                break;
            case ANY:
                collection = factory.getWorm().getWorld().getEntities();
                break;
            default:
                throw new IllegalStateException("No valid type was found.");
        }

        /* Execute atleast once if not finished, else execute for all items in the collection. */
        boolean performedOnceFlag = false;
        for (Object obj : collection) {
            if(performedOnceFlag && !program.isFinished()) //Already checked for lastStatement.
                break;
            
            givenVariable.setValue(obj);
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

    @Override
    public List<Statement> getStatements() {
        ArrayList<Statement> myList = new ArrayList<>();
        myList.add(body);
        return myList;
    }

    /**
     * Returns whether this ForEachStatement is valid.
     * This means the type of the variable must match the type of collection we run through.
     * @return false if the variable does not exist, false if the type of the variable isn't Entity.class
     */
    public boolean isValidVariableType() {
        Variable givenVariable = (Variable) this.factory.getProgramParser().getGlobals().get(variableName);
        if(givenVariable == null)
            return false;
        return givenVariable.getType() == Entity.class;   
    }
}
