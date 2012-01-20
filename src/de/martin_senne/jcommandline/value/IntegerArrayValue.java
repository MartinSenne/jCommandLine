package de.martin_senne.jcommandline.value;

import java.util.regex.Pattern;


/**
 * @author  Martin Senne
 */
public class IntegerArrayValue extends AbstractValue<int[]> {

    public IntegerArrayValue(boolean required, int[] defaultValue) {
        super(int[].class, required, defaultValue);
    }

    public IntegerArrayValue(boolean required, int[] defaultValue, IValueCallback<int[]> callback) {
        super(int[].class, required, defaultValue, callback);
    }

    @Override protected boolean parseCliValue() {

        try {
            String[] intEntries = Pattern.compile("\\s+").split(this.cliValue);
            this.value = new int[intEntries.length];

            for (int i = 0; i < intEntries.length; i++) {
                value[i] = Integer.parseInt(intEntries[i]);
            }

            return true;
        } catch (NumberFormatException ex) {
            this.errorMessage = "Value '" + cliValue + "' contains a non valid integer.";

            return false;
        }
    }

    public boolean isMultiValued() {
        return true;
    }
}
