package de.martin_senne.jcommandline.value;

/**
 * @author  Martin Senne
 */
public class StringValue extends AbstractValue<String> {

    public StringValue(boolean required, String defaultValue) {
        super(String.class, required, defaultValue);
    }

    public StringValue(boolean required, String defaultValue, IValueCallback<String> callback) {
        super(String.class, required, defaultValue, callback);
    }

    @Override
    protected boolean parseCliValue() {
        // System.out.println("Parse cli value for " + this);
        if (!cliValue.isEmpty()) {
            value = cliValue;

            return true;
        } else {
            this.errorMessage = "Value must not be empty.";

            return false;
        }
    }

    @Override
    public boolean isMultiValued() {
        return false;
    }
}
