package de.martin_senne.jcommandline.value;

/**
 * Class BooleanValue.
 *
 * @author  Martin Senne
 */
public class BooleanValue extends AbstractValue<Boolean> {

    public BooleanValue(boolean required, Boolean defaultValue) {
        super(Boolean.class, required, defaultValue);
    }

    public BooleanValue(boolean required, Boolean defaultValue, IValueCallback<Boolean> callback) {
        super(Boolean.class, required, defaultValue, callback);
    }

    @Override
    protected boolean parseCliValue() {

        if (("true".equals(cliValue)) || ("enable".equals(cliValue)) || ("1".equals(cliValue)) ||
                ("yes".equals(cliValue))) {
            value = true;

            return true;
        }

        if (("false".equals(cliValue)) || ("disable".equals(cliValue)) || ("0".equals(cliValue)) ||
                ("no".equals(cliValue))) {
            value = false;

            return true;
        }

        this.errorMessage = "Value '" + cliValue + "' is not a valid boolean (enable|disable|1|0|true|false).";

        return false;

    }

    @Override
    public boolean isMultiValued() {
        return false;
    }
}
