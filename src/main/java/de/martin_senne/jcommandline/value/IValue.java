package de.martin_senne.jcommandline.value;

import de.martin_senne.jcommandline.option.IOption;

/**
 * Interface for accessing value objects like {@link DoubleValue}, {@link IntegerValue} or {@link
 * AlternativeStringValue}.
 *
 * @param   <U>  is the type of the value.
 *
 * @author  Martin Senne
 */
public interface IValue<U> {

    /**
     * Return the values this object has.
     *
     * @return  value of type U.
     */
    public U getValue();

    /**
     * Get error message, if parsing has failed for some reason.
     *
     * @return  error message.
     */
    public String getErrorMessage();

    /**
     * Set the value of this object to {@code cliValue}. The current value does not change, the cliValue needs to be
     * parsed.
     *
     * @param  cliValue
     */
    void setCurrentValueFromCli(String cliValue);

    /**
     * Set a callback, which is called AFTER parsing of whole commandline was successful.
     *
     * @see    CommandLineParser#check()
     *
     * @param  callback  is the callback to set.
     */
    public void setCallback(IValueCallback<U> callback);

    /**
     * Get the option this value belongs to.
     *
     * @return  option.
     */
    public IOption getAssociatedParameter();

    /**
     * Set the option this value belongs to.
     *
     * @param  option  to set.
     */
    public void setAssociatedParameter(IOption option);

    /**
     * Returns, whether the value of this instance is valid.
     *
     * @return  true, if so.
     */
    public boolean isValid();

    /**
     * Indicates, if this value is required. If not required, the value is optional. An option has multiple ( 0 to n )
     * values.
     *
     * <p>Example 1: An optional option with two optional values.</p>
     *
     * <pre>
         [-o [v1 [v2]]]
     * </pre>
     *
     * <p>The option o, value v1 and value v2 are optional. Bracketing of options is ALWAYS done in the above manner,
     * but the brackets can be "turned on" or "off", by setting them required.</p>
     *
     * <p>Example 2: Option and two values, but only value v2 is optional:</p>
     *
     * <pre>
          -o  v1 [v2]
     * </pre>
     *
     * <p>Example 3: Option and two values, but value v1 is set optional:</p>
     *
     * <pre>
          -o [v1  v2]
     * </pre>
     *
     * @return  is value is required.
     */
    public boolean isRequired();

    /**
     * Is value set from command line interface.
     *
     * @return  true, if set from command line
     */
    public boolean isExistent();

    /**
     * Make the current value of this object the current value.
     *
     * @return  textual description of the default value.
     */
    public String useDefaultValue();


    /**
     * Indiciates if this value stores multipled values (arrays).
     *
     * @return  true if so
     */
    public boolean isMultiValued();
}
