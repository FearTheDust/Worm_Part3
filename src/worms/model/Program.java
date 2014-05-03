package worms.model;

import java.util.Map;
import worms.model.program.Variable;
import worms.model.program.statements.ConditionalStatement;
import worms.model.program.statements.Statement;

public class Program {
    
    public static final int MAX_STATEMENT_AMOUNT = 1000;
    
    
    //POSSIBLE TODO's
    //TODO: We could add clone functions to Statements so we could clone them.
    //So if a statement has conditions on it we could clone the input so when executing the reference couldn't have been changed from the outside.

    //TODO: multiple error throwing.
    
    //TODO (vraag) When 'aborted' because of low AP or 1000 S, do we start at the statement lying above or ..? (as in While's, For's, If's) LAST

    //TODO !! (vraag) How to retrieve the global variables DURING parsing to know which type of variable we're dealing with to know which type of expression we're dealing with.
    //(we could add a reference to the WormsParseMyListener in ProgramParser & if parser.getGlobals() == null check
    //We could create/maintain our own lis of variables.
    
    
    
    //TODO: Best to put a generic T in Program or just use the Variable everywhere?
    
    //TODO: Add checker to check if the Map was valid? (not null etc)
    //TODO: little bit of doc
    public Program(Map<String, Variable> globalMap, Statement mainStatement) {
        this.globalMap = globalMap;
        this.mainStatement = mainStatement;
    }

    /**
     * The Map of global variables. <String name, T variable>
     *
     * @return The map of global variables as provided in the constructor.
     */
    public Map<String, Variable> getGlobals() {
            //Actually it would be better to not give the reference but clone it.
        //But since we don't know which Maptype it is AND there is no clone function on a map we can't do that?
        return globalMap;
    }

    private Map<String, Variable> globalMap;
    
    public void execute() {
        counter = MAX_STATEMENT_AMOUNT;
        
        if(finished) {
            finished = false;
            this.getMainStatement().execute(this);
            finished = true;
            this.setLastStatement(null);
        } else {
            
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
     * Set the last statement we executed to statement.
     * If this value is not null it means we will start executing the given statement next time.
     * 
     * @param statement The statement we will execute the next time.
     */
    public void setLastStatement(ConditionalStatement statement) {
        this.lastStatement = statement;
    }
    
    /**
     * Get the last executed statement. This is the statement we will start at next time.
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
     * MAX_STATEMENT_AMOUNT - The amount of statements we processed so far/last time.
     * @return MAX_STATEMENT_AMOUNT - The amount of statements we have processed.
     */
    public int getCounter() {
        return this.counter;
    }
    
    private int counter = MAX_STATEMENT_AMOUNT;
    
    /**
     * Check whether the program finished properly last time.
     * @return 
     */
    public boolean isFinished() {
        return this.finished;
    }
    
    /**
     * Toggle the value of isFinished().
     */
    public void toggleFinished() {
        this.finished = !this.finished;
    }
    
    private boolean finished = true;

}
