package de.martin_senne.jcommandline.value;

import de.martin_senne.jcommandline.value.AbstractValue;
import de.martin_senne.jcommandline.value.IValueCallback;

/**
 * @author  Martin Senne
 */
public class ConstrainedStringValue extends AbstractValue<String> {

    protected String targetValue;

    public ConstrainedStringValue(boolean required, String targetValue) {
        super(String.class, required, targetValue); // default value is automatically target value
        this.targetValue = targetValue;
    }

    public ConstrainedStringValue(boolean required, String targetValue, IValueCallback<String> callback) {
        super(String.class, required, targetValue, callback); // default value is automatically target value
        this.targetValue = targetValue;
    }

    @Override protected boolean parseCliValue() {
        boolean status = cliValue.equals(targetValue);

        if (status) {
            value = cliValue;
        } else {
            errorMessage = "Value is '" + cliValue + "' instead of '" + targetValue + "'.";
        }

        return status;
    }

    @Override public boolean isMultiValued() {
        return false;
    }
}
