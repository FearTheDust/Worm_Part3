package worms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import worms.gui.game.IActionHandler;
import worms.model.program.Expression;
import worms.model.program.ProgramFactoryImpl;
import worms.model.program.Variable;
import worms.model.program.statements.AssignmentStatement;
import worms.model.program.statements.ConditionalStatement;
import worms.model.program.statements.ForEachStatement;
import worms.model.program.statements.MultipleStatement;
import worms.model.program.statements.Statement;
import worms.model.programs.ParseOutcome;
import worms.model.programs.ProgramParser;

public class Program {

    public static final int MAX_STATEMENT_AMOUNT = 1000;

    /**
     * Create a program with certain variables and a main statement.
     *
     * @param factory The factory with which the program was assembled.
     * @param globalMap The Map with all variables.
     * @param mainStatement The main statement.
     *
     * @throws IllegalArgumentException When globalMap, mainStatement or factory
     * is a null reference. | globalMap == null || mainStatement == null ||
     * factory == null
     *
     */
    public Program(ProgramFactoryImpl factory, Map<String, Variable> globalMap, Statement mainStatement) throws IllegalArgumentException {
        if (globalMap == null || mainStatement == null || factory == null) {
            throw new IllegalArgumentException("globalMap,mainStatement and factory musn't be a null reference.");
        }

        this.globalMap = globalMap;
        this.mainStatement = mainStatement;
        this.factory = factory;
    }

    private ProgramFactoryImpl factory;

    /**
     * The Map of global variables. <String name, Variable variable>
     *
     * @return The map of global variables as provided in the constructor.
     */
    public Map<String, Variable> getGlobals() {
        //Actually it would be better to not give the reference but clone it.
        //But since we don't know which Maptype it is AND there is no clone function on a map we can't do that.
        return globalMap;
    }

    private Map<String, Variable> globalMap;

    /**
     *
     * @throws IllegalStateException When the factory hasn't got a worm or the
     * worm's world is a null reference.
     */
    public void execute() throws IllegalStateException {
        if (this.factory.getWorm() == null) {
            throw new IllegalStateException("The factory hasn't got a worm set.");
        }
        if (this.factory.getWorm().getWorld() == null) {
            throw new IllegalStateException("The factory's worm hasn't got a world set.");
        }

        counter = MAX_STATEMENT_AMOUNT;
        if (mainStatement.execute(this)) {
            this.setLastStatement(null);
            this.setFinished(true);
        } else {
            this.setFinished(false);
        }
    }

    /**
     * Return a reference to the main statement of this Program.
     *
     * @return The main statement.
     */
    public Statement getMainStatement() {
        return mainStatement;
    }

    private final Statement mainStatement;

    /**
     * Set the last statement we executed to statement. If this value is not
     * null it means we will start executing the given statement next time.
     *
     * @param statement The statement we will execute the next time.
     */
    public void setLastStatement(ConditionalStatement statement) {
        this.lastStatement = statement;
    }

    /**
     * Get the last executed statement. This is the statement we will start at
     * next time.
     *
     * @return
     */
    public ConditionalStatement getLastStatement() {
        return lastStatement;
    }

    private ConditionalStatement lastStatement;

    /**
     * Add one to the counter/the amount of statements we processed so far.
     */
    public void subtractFromCounter() {
        this.counter--;
    }

    /**
     * MAX_STATEMENT_AMOUNT - The amount of statements we processed so far/last
     * time.
     *
     * @return MAX_STATEMENT_AMOUNT - The amount of statements we have
     * processed.
     */
    public int getCounter() {
        return this.counter;
    }

    private int counter = MAX_STATEMENT_AMOUNT;

    /**
     * Check whether the program finished properly last time.
     * @return False if the program didn't finish properly last time and we should be searching from which statement to start from next.
     */
    public boolean isFinished() {
        return this.finished;
    }

    /**
     * Set the value of isFinished().
     *
     * @param newValue The new value of isFinished()
     */
    public void setFinished(boolean newValue) {
        this.finished = newValue;
    }

    private boolean finished = true;

    /**
     * Returns whether or not this program is well-formed. A program is
     * well-formed if a for-each statement does not (directly or indirectly)
     * contain one or more action statements.
     *
     * @return true if this program is well-formed; false otherwise
     */
    public boolean isWellFormed() {
        //Always true since we do not allow ForEachLoops with Action Statements (in ForEach constructor).
        return true;
    }

    /**
     * Set the worm associated with this program.
     *
     * @param worm
     * @effect this.factory.setWorm(worm)
     */
    public void setWorm(Worm worm) {
        this.factory.setWorm(worm);
    }

    /**
     * This additionally checks for additional errors in the program created as defined by Program.getAdditionalErrors()
     * 
     * @param programText
     * @param handler
     * @see Documentation as defined by the assignment (IFacade.parseProgram(...))
     * @return 
     */
    public static ParseOutcome<?> parseProgram(String programText,
            IActionHandler handler) {

        ProgramFactoryImpl factory = new ProgramFactoryImpl(handler);
        ProgramParser<Expression, Statement, Variable> parser = new ProgramParser<>(factory);
        factory.setProgramParser(parser); //NullPointerException
        Program program;

        parser.parse(programText);

        if (parser.getErrors().isEmpty()) {
            program = new Program(factory, parser.getGlobals(), parser.getStatement()); //pass: statement, globals
            ArrayList<String> list = program.getAdditionalErrors();
            if (list.isEmpty()) {
                return ParseOutcome.success(program);
            } else {
                return ParseOutcome.failure(list);
            }
        } else {

            return ParseOutcome.failure(parser.getErrors());
        }
    }

    /**
     * This function checks whether the main Statement is an AssignmentStatement or ForEachStatement and validates them.
     * If the mainStatement is of type MultipleStatement it will call the auxiliary method with the MultipleStatement.getStatements() as a parameter.
     * All errors found will be returned in an ArrayList.
     * @return
     */
    public ArrayList<String> getAdditionalErrors() {
        ArrayList<String> myList = new ArrayList<>();

        if (this.getMainStatement() instanceof AssignmentStatement) {
            if (!(((AssignmentStatement) this.getMainStatement()).isValidVariableType())) {
                myList.add("The variable " + ((AssignmentStatement) this.getMainStatement()).getVariableName() + " its type does not match the type of the expression assigned to it in an assignment.");
            }
        } else if (this.getMainStatement() instanceof ForEachStatement) {
            if (!((ForEachStatement) this.getMainStatement()).isValidVariableType()) {
                myList.add("The variable " + ((ForEachStatement) this.getMainStatement()).getVariableName() + " of a For-each loop does not match type Entity.");
            }
        }

        if (this.getMainStatement() instanceof MultipleStatement) {
            myList = getAdditionalErrors_Aux(((MultipleStatement) this.getMainStatement()).getStatements());
        }
        return myList;
    }

    /**
     * Auxiliary function. This goes trough all the statements provided in the
     * list, performs the validation checks for ForEach and
     * AssignmentStatements. Next it checks whether the statement it looped over
     * is of type MultipleStatement, if so it will get all statements of this
     * MultipleStatement and call itself with those as a parameter.
     *
     * @param statements The statements to go trough.
     * @return A list of errors found in the statements.
     */
    private ArrayList<String> getAdditionalErrors_Aux(List<Statement> statements) {
        ArrayList<String> myList = new ArrayList<>();

        for (Statement statement : statements) {
            if (statement instanceof AssignmentStatement) {
                if (!(((AssignmentStatement) statement).isValidVariableType())) {
                    myList.add("The variable " + ((AssignmentStatement) statement).getVariableName() + " its type does not match the type of the expression assigned to it in an assignment.");
                }
            } else if (statement instanceof ForEachStatement) {
                if (!((ForEachStatement) statement).isValidVariableType()) {
                    myList.add("The variable " + ((ForEachStatement) statement).getVariableName() + " of a For-each loop does not match type Entity.");
                }
            }

            if (statement instanceof MultipleStatement) {
                myList.addAll(getAdditionalErrors_Aux(((MultipleStatement) statement).getStatements()));
            }
        }

        return myList;
    }

}
