package worms.model;

import java.util.Map;
import worms.model.program.Variable;
import worms.model.program.statements.Statement;

public class Program {

        //TODO: Best to put a generic T in Program or just use the Variable everywhere?
    
        //TODO: Add checker to check if the Map was valid? (not null etc)
        //TODO: little bit of doc
    
	public Program(Map<String, Variable> globalMap, Statement mainStatement) {
            this.globalMap = globalMap;
            this.mainStatement = mainStatement;
	}
        
        /**
         * The Map of global variables. <String name, T variable>
         * @return The map of global variables as provided in the constructor.
         */
        //TODO (vraag) How properly make such a clone without knowing the specific type.
        public Map<String, Variable> getGlobals() {
            //Actually it would be better to not give the reference but clone it.
            //But since we don't know which Maptype it is AND there is no clone function on a map we can't do that?
            return globalMap;
        }
        
        private Map<String, Variable> globalMap;
        
        /**
         * Return a reference to the main statement of this Program.
         * @return The main statement.
         */
        public Statement getMainStatement() {
            return mainStatement;
        }
        
        private Statement mainStatement;
               
        

}
