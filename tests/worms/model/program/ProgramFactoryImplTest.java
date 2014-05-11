
package worms.model.program;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import worms.model.Facade;
import worms.model.SimpleActionHandler;
import worms.model.programs.ProgramParser;

/**
 *
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 */
public class ProgramFactoryImplTest {
    
    public ProgramFactoryImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
       Facade facade = new Facade(); 
       SimpleActionHandler handler = new SimpleActionHandler(facade);
       factory = new ProgramFactoryImpl(handler);
       ProgramParser parser = new ProgramParser(factory);
       factory.setProgramParser(parser);
    }
    
    static ProgramFactoryImpl factory;

    /**
     * Proofs us if an Object of a certain type A is a null reference it still isn't an instance of A.
     */
    @Test
    public void testProofNull_NotInstance() {
        BooleanExpression e = factory.createBooleanLiteral(0, 0, true);
        BooleanExpression e2 = null;
        
        assertTrue(e instanceof BooleanExpression);
        assertFalse(e2 instanceof BooleanExpression);
    }
}
