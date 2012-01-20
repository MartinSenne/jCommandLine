package de.martin_senne.jcommandline.value;

import java.util.regex.Pattern;


/**
 * @author  Martin Senne
 */
public class StringArrayValue extends AbstractValue<String[]> {

    public StringArrayValue(boolean required, String[] defaultValue) {
        super(String[].class, required, defaultValue);
    }

    public StringArrayValue(boolean required, String[] defaultValue, IValueCallback<String[]> callback) {
        super(String[].class, required, defaultValue, callback);
    }

    @Override protected boolean parseCliValue() {

        try {
            String[] entries = Pattern.compile("\\s+").split(this.cliValue);
            this.value = new String[entries.length];

            for (int i = 0; i < entries.length; i++) {
                value[i] = entries[i];
            }

            return true;
        } catch (NumberFormatException ex) {
            this.errorMessage = "Value '" + cliValue + "' contains a non valid integer.";

            return false;
        }
    }

    @Override public boolean isMultiValued() {
        return true;
    }
}
