package worms.model;

import be.kuleuven.cs.som.annotate.*;
import worms.model.Constants;
import worms.model.Entity;
import worms.model.Team;
import worms.model.world.entity.GameObject;
import worms.util.Position;

/**
 * Represents Food with a certain mass and radius.
 * 
 * @author Coosemans Brent
 * @author Derkinderen Vincent
 * 
 * @invar	This food's radius is equal to 0.20 m.
 * 			| this.getRadius() == Constants.FOOD_RADIUS;
 * @invar 	The mass of this food is always 0.
 * 			| this.getMass() == 0
 * @invar	The position of this food is never null.
 *			| this.getPosition() != null
 */
public class Food extends GameObject implements Entity {

	/**
	 * Initialize Food with a certain position.
	 * 
	 * @param world The world this food is in.
	 * @param position The position of this food.
	 * 
	 * @effect This food will be granted a provided position when valid.
	 * 			| super(world, position)
	 * @effect This food will be added to the given world.
	 * 			| world.add(this)
	 * 
	 * @post This food will be alive.
	 * 		| new.isAlive()
	 */
	@Raw
	public Food(World world, Position position) {
		super(world, position);
		this.alive = true;
		world.add(this);
	}

	/**
	 * Returns the radius of the food, always 0.2 m.
	 */
	@Override @Immutable @Basic
	public double getRadius() {
		return Constants.FOOD_RADIUS;
	}

	/**
	 * Returns the mass of this food, always 0.
	 */
	@Override @Immutable @Basic
	public double getMass() {
		return 0;
	}

	/**
	 * Check if this food is alive,
	 * 		meaning it is not yet eaten, living in a world that is not null and lies within the boundaries of that world.
	 */
	@Override @Basic
	public boolean isAlive() {
		return alive && this.getWorld() != null && this.getWorld().liesWithinBoundaries(this);
	}
	
	/**
	 * Sets the isAlive state to false.
	 * 
	 * @post This food is not alive anymore.
	 * 		| new.isAlive() == false
	 */
	public void setToEaten() {
            this.alive = false;
	}
	
	private boolean alive;

    /**
     * 
     * @throws IllegalStateException 
     *          This class doesn't support an angle and will throw this exception.
     */
    @Override
    public double getAngle() throws IllegalStateException {
        throw new IllegalStateException(this.getClass() + " does not support an angle.");
    }

    /**
     *
     * @throws IllegalStateException 
     *          This class doesn't support hit points and will throw this exception.
     */
    @Override
    public double getHP() throws IllegalStateException {
        throw new IllegalStateException(this.getClass() + " does not support hit points.");
    }

    /**
     * 
     * @throws IllegalStateException 
     *          This class doesn't support maximum hit points and will throw this exception.
     */
    @Override
    public double getMaxHP() throws IllegalStateException {
        throw new IllegalStateException(this.getClass() + " does not support maximum hit points.");
    }

    /**
     *
     * @throws IllegalStateException 
     *          This class doesn't support action points and will throw this exception.
     */
    @Override
    public double getAP() throws IllegalStateException {
        throw new IllegalStateException(this.getClass() + " does not support action points.");
    }

     /**
     * 
     * @throws IllegalStateException 
     *          This class doesn't support maximum action points and will throw this exception.
     */
    @Override
    public double getMaxAP() throws IllegalStateException {
        throw new IllegalStateException(this.getClass() + " does not support maximum action points.");
    }

    /**
     * 
     * @throws IllegalStateException 
     *          This class doesn't support teams.
     */
    @Override
    public Team getTeam() throws IllegalStateException {
        throw new IllegalStateException(this.getClass() + " does not support teams.");
    }

}
