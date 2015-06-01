package org.gnode.nix.base;

import org.bytedeco.javacpp.annotation.Platform;

@Platform(value = "linux")
public abstract class NamedEntity extends Entity {

    /**
     * Setter for the type of the entity.
     *
     * @param type The type to set.
     */
    abstract public void setType(String type);

    /**
     * Getter for the type of the entity.
     * <p/>
     * The property type is used in order to allow the specification
     * of additional semantic meaning for an entity and therefore can introduce
     * domain-specificity into the quite generic data model.
     *
     * @return The current type.
     */
    abstract public String getType();

    /**
     * Getter for the name of the entity.
     * <p/>
     * The name of an entity serves as a human readable identifier. It is not obliged
     * to be unique. However it is strongly recommended to use unique name inside one specific
     * {@link org.gnode.nix.Block}.
     *
     * @return string The name of the entity.
     */
    abstract public String getName();

    /**
     * Setter for the definition of the entity.
     *
     * @param definition The definition of the entity.
     */
    abstract public void setDefinition(String definition);

    /**
     * Getter for the definition of the entity.
     * <p/>
     * The definition is an optional property that allows the user to add
     * a freely assignable textual definition to the entity.
     *
     * @return The definition of the entity.
     */
    abstract public String getDefinition();
}
