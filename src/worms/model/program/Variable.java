
package worms.model.program;

import worms.model.Entity;

/**
 * Represents a variable with a certain name, type and value.
 * Values that have been set are always supported by their type.
 * If no value is set yet the default value is applied, which is null. 
 * This implies that when a value is null and the type does not support null (double, boolean)
 * no assignment was given besides the declaration without value. (e.g bool x;)
 * 
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 */
public class Variable {
    
    /**
     * Initialize a variable with a certain type.
     * 
     * @param type The type.
     * 
     * @effect this(type, null)
     */
    public Variable(Class type) {
        this(type, null);
    }
    
    /**
     * Initialize a variable with a certain type and a value.
     * 
     * @param type The type of the new variable.
     * @param value The value for the variable.
     * 
     * @see If value != null this.setValue(value) since value has restrictions on its type.   
     */
    public Variable(Class type, Object value) {        
        this.type = type;
        if(value != null)
            this.setValue(value);
        else
            this.value = value;
    }
    
    // TODO create our own exceptions?
    
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
    public void setValue(Object value) throws IllegalArgumentException {
        if(value instanceof Number)
            value = ((Number) value).doubleValue();
        
        if(type.isInstance(value) || (Entity.class.isAssignableFrom(type) && value == null)) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("The value has to be the same as the type of the variable.");
        }
    }
    
    /**
     * The type of this variable as described at the declaration.
     * @return The variable type.
     */
    public Class getType() {
        return type;
    }
    
    private Class type;
    
    /**
     * The current value of this variable.
     * @return The current value.
     */
    public Object getValue() {
        return this.value;
    }
    
    private Object value;
 
}
