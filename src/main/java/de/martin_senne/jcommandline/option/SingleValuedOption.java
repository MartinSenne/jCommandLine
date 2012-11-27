package de.martin_senne.jcommandline.option;

import de.martin_senne.jcommandline.value.IValue;


/**
 * Class SingleValueOption.
 *
 * @author  Martin Senne
 */
public class SingleValuedOption extends DefaultOption {

    public SingleValuedOption(String name, boolean required, IValue<?> value) {
        super(name, required);
        addValue(value); // dangerous
    }

    public IValue<?> getSingleValue() {
        return values.get(0);
    }

    @Override
    public void addValue(IValue<?> value) {

        if (values.size() >= 1) {
            throw new UnsupportedOperationException("Already has a value assigned.");
        } else {
            super.addValue(value);
        }
    }
}
