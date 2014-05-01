package worms.model;

import java.util.Map;
import worms.model.program.Variable;
import worms.model.program.statements.Statement;

public class Program {

    //TODO (vraag) How would we do the error-handling? We stop when all errors are displayed? 
    // But we can't throw one because we can't catch in code that isn't ours.
    
    //TODO (vraag) When 'aborted' because of low AP or 1000 S, do we start at the statement lying above or ..? (as in While's, For's, If's) LAST

    
    
    
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

    /**
     * Return a reference to the main statement of this Program.
     *
     * @return The main statement.
     */
    public Statement getMainStatement() {
        return mainStatement;
    }

    private Statement mainStatement;

}
