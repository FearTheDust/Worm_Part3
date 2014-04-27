package worms.model.program;

import java.math.BigDecimal;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import worms.model.Entity;
import worms.model.Facade;
import worms.model.world.World;
import worms.model.world.entity.Worm;

/**
 *
 * @author Admin
 */
public class VariableTest {

    public VariableTest() {
    }

    /**
     * Try setting a null reference while not supported.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetValue_Null_Illegal() {
        Variable test = new Variable(Boolean.class);
        test.setValue(null);
    }

    /**
     * Try setting a null reference while supported.
     */
    @Test
    public void testSetValue_Null_Legal() {
        Variable test = new Variable(Entity.class);
        test.setValue(null);
        assertEquals(test.getValue(), null);
    }

    /**
     * Try setting a value that is supported.
     */
    @Test
    public void testSetValue_Legal() {
        /* Worm stuff required for our test (Entity) */
        boolean[][] passableMap = new boolean[][]{
            {true, true},
            {true, true}
        };
        Random random = new Random(7357);
        World world = new World(2.0, 2.0, passableMap, random);
        Facade facade = new Facade();
        Worm worm = facade.createWorm(world, 1, 1.5, Math.PI / 2, 0.5, "Test", null);
 
        Variable entityTest = new Variable(Entity.class);
        entityTest.setValue(worm);
        assertEquals(entityTest.getValue(), worm);

        Variable boolTest = new Variable(Boolean.class);
        boolTest.setValue(true);
        assertEquals(boolTest.getValue(), true);

        Variable doubleTest = new Variable(Double.class);
        doubleTest.setValue(1);
        assertEquals(doubleTest.getValue(), 1.0);
        
        Variable wormTest = new Variable(Worm.class);
        wormTest.setValue(worm);
        assertEquals(wormTest.getValue(), worm);
        
        Variable bigDecimalTest = new Variable(Double.class);
        bigDecimalTest.setValue(new BigDecimal("5084000.0588484"));
        assertEquals(bigDecimalTest.getValue(), 5084000.0588484);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSetValue_Illegal() {
        Variable doubleTest = new Variable(Double.class);
        doubleTest.setValue(true);
    }
}
