package de.martin_senne.jcommandline.value;

/**
 * @author  Martin Senne
 */
public class AlternativeStringValue extends AbstractValue<String> {

    String[] allowedValues;

    public AlternativeStringValue(boolean required, String defaultValue, String[] allowedValues) {
        super(String.class, required, defaultValue);
        this.allowedValues = allowedValues;
    }

    public AlternativeStringValue(boolean required, String defaultValue, String[] allowedValues,
        IValueCallback<String> callback) {
        super(String.class, required, defaultValue, callback);
        this.allowedValues = allowedValues;
    }

    @Override
    protected boolean parseCliValue() {
        boolean status = false;

        for (String allowedValue : allowedValues) {

            if (allowedValue.equals(cliValue)) {
                status = true;
                value = cliValue;
            }
        }

        if (!status) {
            errorMessage = "Value '" + cliValue + "' is not one of ";

            for (String allowedValue : allowedValues) {
                errorMessage += "'" + allowedValue + "', ";
            }

            errorMessage = errorMessage.substring(0, errorMessage.length() - 2) + ".";

        }

        return status;
    }

    @Override public boolean isMultiValued() {
        return false;
    }
}
