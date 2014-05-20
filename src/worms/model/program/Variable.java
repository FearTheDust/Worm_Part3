
package worms.model.program;

import worms.model.Entity;
import worms.model.program.expressions.Expression;


/**
 * Represents a variable with a certain name, type and value.
 * Values that have been set are always supported by their type.
 * 
 * ! If no value is set yet the default value is applied, which is null. !
 * 
 * This implies that when a value is null and the type does not support null (double, boolean)
 * no assignment was given besides the declaration without value. (e.g bool x;)
 * 
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 * @param <T> The type of this variable.
 */
public class Variable<T> {
    
    /**
     * Initialize a variable with a certain type.
     * 
     * @param dummy A class of the type. This will be used to verify expressions are assignable from this class type.
     * 
     * @effect this(type, null)
     */
    public Variable(Class<T> dummy) {
        this(dummy, null);
    }
    
    /**
     * Initialize a variable with a certain type and a value.
     * 
     * @param dummy A class of the type. This will be used to verify expressions are assignable from this class type.
     * @param value The value for the variable.
     * 
     * @post new.getType() == classType
     * 
     * @effect this.setValue(value)   
     */
    public Variable(Class<T> dummy, T value) throws IllegalArgumentException{
        if(dummy == null)
            throw new IllegalArgumentException("The Class<T> dummy argument musn't be a null reference.");
        
        classType = dummy;
        if(value != null)
            this.setValue(value);
    }
    
    /**
     * Set the value of this variable to value.
     * The null value is only allowed when the type supports it. (see throws IllegalArgumentException)
     * 
     * @Remark If value is an instance of Number the value will be cast to doubleValue() so if a clone should be taken, do so in advance.
     * 
     * @param value The value to set to.
     * @throws IllegalArgumentException 
     *          When value isn't a null reference and the type of the provided value can't be an instance of the type of this variable
     *          as described by this.getType().isInstance(value)
     *          When value is a null reference and this.getType() isn't a subclass of Entity. 
     *          This would mean the type doesn't support a null reference according to us.
     */
    public void setValue(T value) throws IllegalArgumentException {
        if(this.isValidValueType(value))
            this.value = value;
        else
            throw new IllegalArgumentException("isValidValueType(value) is false.");
    }
    
    /**
     * The current value of this variable.
     * @return The current value.
     */
    public T getValue() {
        return this.value;
    }
    
    private T value;
 
    /**
     * Whether the given value has a valid value type for this variable.
     * Basically, if the value is null and the type is not assignable from Entity then it will return false.
     * 
     * @param value The value to check.
     * @return Whether it is valid.
     *          | result == (value != null || this.getType().isAssignableFrom(Entity.class))
     */
    public boolean isValidValueType(T value) {
        return (value != null || this.getType().isAssignableFrom(Entity.class));
    }
    
    /**
     * Returns whether this expression is of a valid type for this variable.
     * @param expression The expression to check
     * @return Whether it is valid.
     *          | result == expression.getType().isAssignableFrom(this.getType());
     */
    public boolean isValidValueType(Expression<T> expression) {
        return expression.getType().isAssignableFrom(this.getType());
    }

    /**
     * The type of this variable as described in the constructor.
     * @return The variable type.
     */
    public Class<T> getType() {
        return this.classType;
    }
    
    private final Class<T> classType;
}
