package worms.model;

import java.util.Map;
import worms.gui.game.IActionHandler;
import worms.model.program.Expression;
import worms.model.program.ProgramFactoryImpl;
import worms.model.program.Variable;
import worms.model.program.statements.ConditionalStatement;
import worms.model.program.statements.Statement;
import worms.model.programs.ParseOutcome;
import worms.model.programs.ProgramParser;
import worms.model.world.entity.Worm;

public class Program {

    public static final int MAX_STATEMENT_AMOUNT = 1000;

    //TODO (vraag) multiple error throwing.
    //TODO (vraag) 1.8.3 types Potential overflows in double to integer conversion shall be handler in a total manner.
    // there is no double to integer conversion
    
    /**
     * Create a program with certain variables and a main statement.
     * 
     * @param factory The factory with which the program was assembled.
     * @param globalMap The Map with all variables.
     * @param mainStatement The main statement.
     * 
     * @throws IllegalArgumentException 
     *          When globalMap, mainStatement or factory is a null reference.
     *          | globalMap == null || mainStatement == null || factory == null
     * 
     */
    public Program(ProgramFactoryImpl factory, Map<String, Variable> globalMap, Statement mainStatement) throws IllegalArgumentException {
        if(globalMap == null || mainStatement == null || factory == null)
            throw new IllegalArgumentException("globalMap,mainStatement and factory musn't be a null reference.");
        
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
     * @throws IllegalStateException 
     *          When the factory hasn't got a worm or the worm's world is a null reference.
     */
    public void execute() throws IllegalStateException {
        if(this.factory.getWorm() == null)
            throw new IllegalStateException("The factory hasn't got a worm set.");
        if(this.factory.getWorm().getWorld() == null)
            throw new IllegalStateException("The factory's worm hasn't got a world set.");
            
        counter = MAX_STATEMENT_AMOUNT;
        if(mainStatement.execute(this)) {
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
     *
     * @return
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
     * @param worm
     * @effect this.factory.setWorm(worm)
     */
    public void setWorm(Worm worm) {
        this.factory.setWorm(worm);
    }

    public static ParseOutcome<?> parseProgram(String programText,
			IActionHandler handler) {
            
		ProgramFactoryImpl factory = new ProgramFactoryImpl(handler);
                ProgramParser<Expression, Statement, Variable> parser = new ProgramParser<>(factory);
                factory.setProgramParser(parser); //NullPointerException
                Program program;
                
                parser.parse(programText);
                
                if(parser.getErrors().isEmpty()) {
                    program = new Program(factory, parser.getGlobals(), parser.getStatement()); //pass: statement, globals
                    return ParseOutcome.success(program);
                } else {
                    return ParseOutcome.failure(parser.getErrors());
                }
	}
    
}
