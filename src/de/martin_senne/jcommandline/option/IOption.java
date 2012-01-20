package de.martin_senne.jcommandline.option;

import de.martin_senne.jcommandline.value.IValue;
import java.util.Iterator;
import java.util.List;

/**
 * Every option implements this interface, which provides the basic functionality.
 * 
 * An option is something like "-a" in the commandline.
 * 
 * @author  Martin Senne
 */
public interface IOption {

    /**
     * If this option is valid (e.g. present in the parsed cli, if required to) and all values of this option are also
     * valid.
     *
     * @return  validity.
     */
    boolean isValid();

    
    /**
     * Add another option {@code option} as parameter.
     * A child option "depends" on the parent, meaning the child option is only allowed,
     * if the parent option is existent.
     * Thus, a dependence of option "-b" from "-a"
     * <pre>[-a valueA [-b valueB]]</pre>
     * is setup by:
     * 
     * <pre>{@code
     *    IOption a = new (some optional option)
     *    IOption b = <b>new (anaother optional option)</b>
     *    a.addChild( b );
     * }</pre>
     *
     * @param option 
     */
    void addChild(IOption option);


    /**
     * Set the parent of this option node.
     * @param parent
     */
    void setParent(IOption parent);


    Iterator<IOption> iterator();


    List<IOption> getChildren();


    void addValue(IValue<?> value);

    
    /**
     * Get appropriate error message.
     *
     * @return descriptive error message.
     */
    String getErrorMessage();

    /**
     * Get the name of this option.
     *
     * @return name
     */
    String getName();

    /**
     * Set values of parameter according to cli data.
     *
     * @param  values  contain data for values.
     */
    void propagateValues(List<String> values);

    /**
     * Call by CommandLineParser to indicate, that option is present in parsed command line argument.
     */
    void setExistentInCli();

    /**
     * If this option occurs in the parsed command line.
     *
     * @return  true, if so.
     */
    boolean isExistentInCli();

    /**
     * Return if this option is required (true) or optional (false).
     *
     * @return state
     */
    boolean isRequired();

    @Override
    String toString();

    List<IOption> getValidChildren();

    /**
     * Trigger callback.
     */
    void triggerCallback();
}
