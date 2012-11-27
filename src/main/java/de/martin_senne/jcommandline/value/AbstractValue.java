package de.martin_senne.jcommandline.value;

import de.martin_senne.jcommandline.option.IOption;

/**
 * @author  Martin Senne
 *
 * @param   <U>
 */
abstract class AbstractValue<U> implements IValue<U> {
    protected static final int NOT_CHECKED = 1;
    protected static final int OK = 2;
    protected static final int INVALID = 3;

    protected Class<U> parameterClass;

    protected boolean required;

    protected U defaultValue;

    protected U value;

    protected String cliValue;

    protected boolean existent;

    protected int validity;

    protected String errorMessage;

    protected IValueCallback<U> callback;

    protected IOption associatedParameter;


    public AbstractValue(Class<U> parameterClass, boolean required, U defaultValue) {
        this(parameterClass, required, defaultValue, null);
    }


    public AbstractValue(Class<U> parameterClass, boolean required, U defaultValue, IValueCallback<U> callback) {
        this.parameterClass = parameterClass;
        this.required = required;
        this.existent = false;

        this.defaultValue = defaultValue;

        this.cliValue = "";
        this.errorMessage = "";
        this.callback = callback;
        this.validity = NOT_CHECKED;
    }

    public void setCurrentValueFromCli(String value) {
        this.cliValue = value;
        this.existent = true;
        this.isValid();
    }

    /**
     * Subclasses MUST implement this method. When this method is internally called from {@link CommandLineParser}, the
     * data in cliValue is set to that value from the command line interface.
     *
     * <p>In order to implement this method properly, 1. parse the value of cliValue . 2. set field value to the parsed
     * value of cliValue, if parsing was successful. 3. return result of parsing</p>
     *
     * @return  if parsing was successful. If so, value must contain the parsed value of cliValue.
     */
    protected abstract boolean parseCliValue();

    public String useDefaultValue() {

        // System.out.println( "Using default value for " + this.getAssociatedParameter().getName() );
        value = defaultValue;

        if (defaultValue == null) {
            return "<No default value>";
        }

        return value.toString();
    }

    public boolean isValid() {

        // check for validity if necessary
        if (validity == NOT_CHECKED) {

            if (existent) {

                if (parseCliValue()) { // parseCliValue has to set value accordingly
                    validity = OK;
                } else {
                    validity = INVALID;
                }
            } else {
                validity = OK;
            }
        }

        // return validity
        if (validity == OK) {
            return true;
        } else {
            return false;
        }
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public U getValue() {

        if (isValid()) {
            invokeCallback(value);

            return value;
        } else {
            throw new RuntimeException("Requesting invalid value.");
        }
    }

    protected void invokeCallback(U value) {

        if (callback != null) {
            callback.setValue(value, this);
        }
    }


    /**
     * @param  callback  the callback to set
     */
    public void setCallback(IValueCallback<U> callback) {
        this.callback = callback;
    }


    public IOption getAssociatedParameter() {
        return this.associatedParameter;
    }

    public void setAssociatedParameter(IOption p) {
        this.associatedParameter = p;
    }

    public boolean isRequired() {
        return required;
    }

    /**
     * @return  if existent in cli
     */
    public boolean isExistent() {
        return existent;
    }


    public abstract boolean isMultiValued();
}
