package de.martin_senne.jcommandline.value;

import de.martin_senne.jcommandline.value.AbstractValue;
import de.martin_senne.jcommandline.value.IValueCallback;

/**
 * @author  Martin Senne
 */
public class IntegerValue extends AbstractValue<Integer> {

    public IntegerValue(boolean required, Integer defaultValue) {
        super(Integer.class, required, defaultValue);
    }

    public IntegerValue(boolean required, Integer defaultValue, IValueCallback<Integer> callback) {
        super(Integer.class, required, defaultValue, callback);
    }

    @Override
    protected boolean parseCliValue() {
        try {
            this.value = Integer.parseInt(this.cliValue);

            return true;
        } catch (NumberFormatException ex) {
            this.errorMessage = "Value '" + cliValue + "' is not a valid integer.";

            return false;
        }
    }

    @Override public boolean isMultiValued() {
        return false;
    }
}
