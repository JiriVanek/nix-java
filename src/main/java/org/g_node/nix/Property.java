package org.g_node.nix;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.annotation.*;
import org.g_node.nix.internal.*;
import org.g_node.nix.base.Entity;

import java.util.Date;
import java.util.List;

/**
 * <h1>Property</h1>
 * Class representing an odML property entity.
 * <p>
 * In the odML model information is stored in the form of extended
 * key-value pairs. A Property contains information that is valid for
 * all Values stored in it. Its {@link DataType} provides information about
 * the type of the stored Value entities (e.g. double or integer).
 * <p>
 * The {@link Property#setUnit(String)} is the unit of the stored values. Similar
 * to the {@link Section} entity, mapping information can be provided
 * using the {@link Property#setMapping(String)} field.
 *
 * @see DataType
 * @see Section
 */

@Properties(value = {
        @Platform(include = {"<nix/Property.hpp>"}),
        @Platform(value = "linux", link = BuildLibs.NIX_1, preload = BuildLibs.HDF5_7),
        @Platform(value = "macosx", link = BuildLibs.NIX, preload = BuildLibs.HDF5),
        @Platform(value = "windows",
                link = BuildLibs.NIX,
                preload = {BuildLibs.HDF5, BuildLibs.MSVCP120, BuildLibs.MSVCR120, BuildLibs.SZIP, BuildLibs.ZLIB})})
@Namespace("nix")
public class Property extends Entity implements Comparable<Property> {
    static {
        Loader.load();
    }

    //--------------------------------------------------
    // Constructors
    //--------------------------------------------------

    /**
     * Constructor that creates an uninitialized Property.
     * <p>
     * Calling any method on an uninitialized property will throw a {@link RuntimeException}.
     */
    public Property() {
        allocate();
    }

    private native void allocate();

    //--------------------------------------------------
    // Base class methods
    //--------------------------------------------------

    public native
    @Cast("bool")
    boolean isNone();

    /**
     * Get id of the property.
     *
     * @return ID string
     */
    public native
    @Name("id")
    @StdString
    String getId();

    private native
    @Cast("time_t")
    long createdAt();

    /**
     * Get the creation date of the property.
     *
     * @return The creation date of the property.
     */
    public Date getCreatedAt() {
        return DateUtils.convertSecondsToDate(createdAt());
    }

    private native
    @Cast("time_t")
    long updatedAt();

    /**
     * Get the date of the last update.
     *
     * @return The date of the last update.
     */
    public Date getUpdatedAt() {
        return DateUtils.convertSecondsToDate(updatedAt());
    }

    /**
     * Sets the time of the last update to the current time if the field is not set.
     */
    public native void setUpdatedAt();

    /**
     * Sets the time of the last update to the current time.
     */
    public native void forceUpdatedAt();

    /**
     * Sets the creation time to the current time if the field is not set.
     */
    public native void setCreatedAt();

    private native void forceCreatedAt(@Cast("time_t") long time);

    /**
     * Sets the creation date to the provided value even if the attribute is set.
     *
     * @param date The creation date to set.
     */
    public void forceCreatedAt(Date date) {
        forceCreatedAt(DateUtils.convertDateToSeconds(date));
    }


    //--------------------------------------------------
    // Attribute getter and setter
    //--------------------------------------------------

    /**
     * Getter for the name of the property.
     * <p>
     * The  of an property serves as a human readable identifier. It is not obliged
     * to be unique. However it is strongly recommended to use unique name inside one specific
     * {@link Section}.
     *
     * @return string The name of the property.
     */
    public native
    @Name("name")
    @StdString
    String getName();

    private native void definition(@Const @ByVal None t);

    private native void definition(@StdString String definition);

    /**
     * Setter for the definition of the property.
     *
     * @param definition definition of property. If <tt>null</tt> is passed definition is removed.
     */
    public void setDefinition(String definition) {
        if (definition != null) {
            definition(definition);
        } else {
            definition(new None());
        }
    }

    private native
    @ByVal
    OptionalUtils.OptionalString definition();

    /**
     * Getter for the definition of the property.
     * <p>
     * The definition is an optional property that allows the user to add
     * a freely assignable textual definition to the property.
     *
     * @return The definition of the property. Returns <tt>null</tt> if not present.
     */
    public String getDefinition() {
        OptionalUtils.OptionalString defintion = definition();
        if (defintion.isPresent()) {
            return defintion.getString();
        }
        return null;
    }


    private native
    @ByVal
    OptionalUtils.OptionalString mapping();

    /**
     * Retrieve Getter for the mapping information stored in this Property.
     *
     * @return The mapping for the Property. Returns <tt>null</tt> if not present.
     */
    public String getMapping() {
        OptionalUtils.OptionalString mapping = mapping();
        if (mapping.isPresent()) {
            return mapping.getString();
        }
        return null;
    }

    private native void mapping(@Const @ByVal None t);

    private native void mapping(@StdString String mapping);

    /**
     * Set the mapping information for this Property.
     * <p>
     * The mapping defines how this Property should be treated in a mapping procedure. The mapping
     * is provided in form of an url pointing to the definition of a section into which this
     * property should be mapped.
     *
     * @param mapping The mapping information. If <tt>null</tt> is passed the unit is removed.
     */
    public void setMapping(String mapping) {
        if (mapping != null) {
            mapping(mapping);
        } else {
            mapping(new None());
        }
    }

    /**
     * Returns the data type of the stored Values.
     *
     * @return The data type.
     * @see DataType
     */
    public native
    @Name("dataType")
    @ByVal
    @Cast("nix::DataType")
    int getDataType();

    private native
    @ByVal
    OptionalUtils.OptionalString unit();

    /**
     * Returns the unit for all stored values.
     *
     * @return The unit for all values. Returns <tt>null</tt> if not present.
     */
    public String getUnit() {
        OptionalUtils.OptionalString unit = unit();
        if (unit.isPresent()) {
            return unit.getString();
        }
        return null;
    }

    private native void unit(@StdString String unit);

    private native void unit(@Const @ByVal None t);

    /**
     * Set the unit for all stored values.
     *
     * @param unit The unit for all values. If <tt>null</tt> is passed the unit is removed.
     */
    public void setUnit(String unit) {
        if (unit != null) {
            unit(unit);
        } else {
            unit(new None());
        }
    }

    //--------------------------------------------------
    // Methods for Value access
    //--------------------------------------------------

    /**
     * Deletes all values from the property.
     *
     * @see Value
     */
    public native void deleteValues();

    /**
     * Get the number of values of the property.
     *
     * @return The number of values.
     * @see Value
     */
    public native
    @Name("valueCount")
    long getValueCount();

    private native void values(@Const @ByRef VectorUtils.ValueVector values);

    /**
     * Set the values of the property.
     *
     * @param values The values to set.
     * @see Value
     */
    public void setValues(List<Value> values) {
        values(new VectorUtils.ValueVector(values));
    }

    private native
    @ByVal
    VectorUtils.ValueVector values();

    /**
     * Get all values of the property.
     *
     * @return The values of the property.
     * @see Value
     */
    public List<Value> getValues() {
        return values().getValues();
    }

    //--------------------------------------------------
    // Overrides
    //--------------------------------------------------

    @Override
    public int compareTo(Property property) {
        if (this == property) {
            return 0;
        }
        return this.getName().compareTo(property.getName());
    }

    @Override
    public String toString() {
        return "Property: {name = " + this.getName() + "}";
    }
}