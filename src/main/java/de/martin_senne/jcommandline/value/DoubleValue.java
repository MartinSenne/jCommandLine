package de.martin_senne.jcommandline.value;

/**
 * @author  Martin Senne
 */
public class DoubleValue extends AbstractValue<Double> {

    public DoubleValue(boolean required, Double defaultValue) {
        super(Double.class, required, defaultValue);
    }

    public DoubleValue(boolean required, Double defaultValue, IValueCallback<Double> callback) {
        super(Double.class, required, defaultValue, callback);
    }

    @Override public boolean parseCliValue() {

        try {
            this.value = Double.parseDouble(this.cliValue);

            return true;
        } catch (NumberFormatException ex) {
            this.errorMessage = "Value '" + cliValue + "' is not a valid double.";

            return false;
        }
    }

    @Override public boolean isMultiValued() {
        return false;
    }
}
