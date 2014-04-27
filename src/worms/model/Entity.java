package worms.model;

import worms.util.Position;

/**
 * Part of the "Program" - The computer programmed worms.
 * When a class implements this interface the class is a valid type to have a variable of with the type entity.
 * 
 * If we had renamed those functions to match with the already existing functions in Worm.java we would've had to rename
 * those functions in Worm.java from type int to double. We decided to not do this as it could work confusing for the users of these classes.
 * Instead we named the functions below differently and for the implementation in e.g Worm.java we just call the differently named function
 * which returns the int.
 * 
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 */
public interface Entity {
    // TODO: Nog iets?
    
    /**
     * The position of this entity.
     * @return The position.
     * @throws IllegalStateException
     *          This may throw an IllegalStateException if the class implementing this doesn't support a position.
     */
    public Position getPosition() throws IllegalStateException;
    
    /**
     * The radius of this entity.
     * @return The radius.
     * @throws IllegalStateException
     *          This may throw an IllegalStateException if the class implementing this doesn't support a radius.
     */
    public double getRadius() throws IllegalStateException;
    
    /**
     * The direction of this entity.
     * @return The direction, an angle in radian. 0 -> 2*Math.PI
     * @throws IllegalStateException
     *          This may throw an IllegalStateException if the class implementing this doesn't support a direction.
     */
    public double getAngle() throws IllegalStateException;
    
    /**
     * The current amount of hit points this entity has.
     * @return The current hit points.
     * @throws IllegalStateException
     *          This may throw an IllegalStateException if the class implementing this doesn't support hit points.
     */
    public double getHP() throws IllegalStateException;
    
    /**
     * The current maximum amount of hit points this entity can have at this moment.
     * @return The maximum amount of hit points.
     * @throws IllegalStateException
     *          This may throw an IllegalStateException if the class implementing this doesn't support maximum hit points.
     */
    public double getMaxHP() throws IllegalStateException;
    
    /**
     * The current amount of action points this entity has.
     * @return The current amount of action points.
     * @throws IllegalStateException
     *          This may throw an IllegalStateException if the class implementing this doesn't support action points.
     */
    public double getAP() throws IllegalStateException;
    
    /**
     * The current maximum amount of action points this entity can have at this moment.
     * @return The maximum amount of action points.
     * @throws IllegalStateException
     *          This may throw an IllegalStateException if the class implementing this doesn't support maximum action points.
     */
    public double getMaxAP() throws IllegalStateException;
    
}
