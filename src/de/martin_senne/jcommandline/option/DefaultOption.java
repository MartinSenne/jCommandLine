package de.martin_senne.jcommandline.option;

import de.martin_senne.jcommandline.TreeIterator;
import de.martin_senne.jcommandline.value.IValue;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;


/**
 * @author  Martin Senne
 */
public class DefaultOption implements IOption, Iterable<IOption> {

    protected String name;
    protected List<IOption> children;
    protected List<IValue<?>> values;
    protected String errorMessage = "";
    protected boolean existentInCli;
    protected boolean required;
    protected String childrenErrorMessage;
    protected String selfErrorMessage;
    protected String valuesErrorMessage;
    protected IOptionCallback callback;
    private IOption parent;

    /**
     * Create parameter with given name. Represents a parameter like "-name opt" in cli.
     *
     * @param name 
     * @param required
     */
    public DefaultOption(final String name, final boolean required) {

        this.name = name;
        this.required = required;

        existentInCli = false;
        children = new ArrayList<IOption>();
        values = new ArrayList<IValue<?>>();
        childrenErrorMessage = "";
        selfErrorMessage = "";
        callback = null;
    }

    public void setCallback(final IOptionCallback callback) {
        this.callback = callback;
    }

    @Override
    public void addChild(final IOption parameter) {
        children.add(parameter);
        parameter.setParent(this);
    }

    public void setParent(IOption parent) {
        this.parent = parent;
    }

    @Override
    public void addValue(IValue<?> value) {
        values.add(value);
        value.setAssociatedParameter(this);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isValid() {

        boolean childrenValid = checkChildren();
        boolean selfValid = checkSelf();

        if (childrenValid && selfValid) {
            return true;
        } else {
            this.errorMessage = selfErrorMessage + childrenErrorMessage;

            return false;
        }
    }

    /**
     * This does the recursion for checking.
     * 
     * @return true if children are valid.
     */
    protected boolean checkChildren() {
        boolean isValid = true;
        childrenErrorMessage = "";

        // check, if children are valid
        for (IOption childOption : children) {

            if (!childOption.isValid()) {
                childrenErrorMessage += childOption.getErrorMessage();
                isValid = false;
            }
        }
        return isValid;
    }

    private boolean checkSelf() {
        boolean isValid = true;

        if (parent != null) {
            if (!parent.isExistentInCli() && isExistentInCli()) {
                selfErrorMessage +="Option " + getName() + " not allowed, "
                            + "if option " + parent.getName() + " not present. ";
                isValid = false;
            }

            if (isValid) {
                if ( isRequired() && (isExistentInCli() != parent.isExistentInCli()) ) {
                    selfErrorMessage += "Option " + getName() + " is required, "
                            + "but MISSING. ";
                    isValid = false;
                } else {
                    if (parent.isExistentInCli()) {
                        boolean checkSelfValuesStatus = checkSelfValues();
                        if ( checkSelfValuesStatus == false ) {
                            selfErrorMessage += "Option " + getName() + " has the following error: " +
                                    valuesErrorMessage;
                            isValid = false;
                        }
                    }
                    if (isValid) {
                        useDefaultValuesForNonExistentValues();
                    }
                }
            }
            return isValid;
        } else { // parent null
            return true;
        }
    }

    private boolean checkSelfValues() {
        valuesErrorMessage = "";

        // first check, if all values have proper value
        boolean allValuesValid = true;

        for (IValue<?> value : values) {
            // System.out.println("  Checking value: " + value );
            if (!value.isValid()) {
                allValuesValid = false;
                valuesErrorMessage += "Value for parameter " + name + " incorrect: " + value.getErrorMessage();
            }
        }

        // if so, check existance of option and values
        if (!allValuesValid) {
            return false;
        } else {
            int n = values.size();

            //             0     1     2     3     4
            // required:   opt   val1  val2  val3  -       (n+1 entries) HERE: n=3 values
            // exists:     1     opt   val1  val2  val3    (n+2 entries)
            BitSet bsRequired = new BitSet(1 + n);
            BitSet bsExistent = new BitSet(2 + n);
            bsRequired.clear();
            bsExistent.clear();

            bsRequired.set(0, required);
            // bsRequired.set(0, true); // testing

            bsExistent.set(0, true); // as filler for the first
            bsExistent.set(1, existentInCli);
            // bsExistent.set(1, true); // testing

            // set bits accordingly to the state of required and existentInCli of this option and all its values.
            int i = 0;

            for (IValue<?> value : values) {
                bsRequired.set(1 + i, value.isRequired());
                bsExistent.set(2 + i, value.isExistent());
                i++;
            }

            // Exist bits must be 1*0*, that is any bitset like
            // starting with some 1s and followed by 0s.
            // e.g. 11000 or 100 or 0000 or 1000
            boolean validCombination = true;
            int firstZeroAt = 2 + n;

            for (int e = 1; e < (2 + n); e++) {

                if (bsExistent.get(e) == false) { // zero found
                    firstZeroAt = e;
                } else { // one found (at position e)

                    if (e > firstZeroAt) { // after a zero found beforehand ???
                        valuesErrorMessage += "Problem with value for option. Value #" + (e - 1) + " not allowed here.";
                        validCombination = false;
                    }
                }
            }

            // found an "exist" bitset which is not 1*0* ???
            if (!validCombination) {
                return false; // then declare
            }

            // Check if required option and required values are present
            for (int r = 0; r < (1 + n); r++) {

                if (bsRequired.get(r)) {

                    if (bsExistent.get(r) != bsExistent.get(r + 1)) {

                        if (r == 0) {
                            valuesErrorMessage += "Option " + name + " is required, but missing.";
                        } else {
                            valuesErrorMessage += "Parameter " + (r) + " for option " + name +
                                " is required, but missing.";
                        }

                        return false;
                    }
                }
            }

//          System.out.println("Here for option " + this + " " + getName());

            // up to here the option and its values are perfect, so
            // set default values of those non existentInCli values
//            for (int e = 2; e < (2 + n); e++) {
//
//                if (bsExistent.get(e) == false) {
//                    String defValueText = values.get(e - 2).useDefaultValue();
//                    // System.out.println( "Using default value '" + defValueText + "' for value #" + (e-1) + " of " +
//                    // this.getName() +"." );
//                }
//            }

            return true;
        } // end if-else
    }

    protected void useDefaultValuesForNonExistentValues() {
        for ( IValue<?> value : values) {
            if (!value.isExistent()) {
                value.useDefaultValue();
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setExistentInCli() {
        existentInCli = true;
    }

    public boolean isExistentInCli() {
        return existentInCli;
    }

    public void propagateValues(List<String> cliValues) {

        int argValueIdx = 0;
        int valueIdx = 0;

        while (argValueIdx < cliValues.size()) {

            if (argValueIdx >= values.size()) { // more data then values ... abort
                break;
            } else {
                IValue<?> value = values.get(valueIdx);

                if (!value.isMultiValued()) {
                    value.setCurrentValueFromCli(cliValues.get( argValueIdx ) );
                    argValueIdx++;
                    valueIdx++;
                } else {
                    String t = "";

                    for (int i = argValueIdx; i < cliValues.size(); i++) {
                        t += cliValues.get( argValueIdx ) + " ";
                        argValueIdx++;
                    }

                    value.setCurrentValueFromCli(t);

                    break;
                }
            }
        } // end while
    }

    public List<IOption> getChildren() {
        return children;
    }

    public Iterator<IOption> iterator() {
        return new TreeIterator(this);
    }

    public Iterator<IOption> onlyValidParametersIterator() {
        return new TreeIterator(this, true);
    }

    @Override public String toString() {
        return super.toString() + "(" + getName() + ", " + existentInCli + ")";
    }

    public List<IOption> getValidChildren() {
        return children;
    }

    public void triggerCallback() {

        if (callback != null) {
            callback.optionOccursInCli(existentInCli, this);
        }

        // callbacks for values by getValue()
        for (IValue<?> value : values) {
            value.getValue();
        }
    }

    public boolean isRequired() {
        return required;
    }
}
