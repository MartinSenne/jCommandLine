package de.martin_senne.jcommandline.option;

import de.martin_senne.jcommandline.value.BooleanValue;
import de.martin_senne.jcommandline.value.IValue;
import de.martin_senne.jcommandline.value.IValueCallback;
import java.util.List;

/**
 * Represents a single option like "-optionname", which has no parameters.
 * 
 * @author  Martin Senne
 */
public class OptionWithoutValue extends DefaultOption {

    protected BooleanValue internalValue;

    public OptionWithoutValue( String name, boolean required ) {
        super( name, required );
        this.internalValue = new BooleanValue( true, null );
        this.internalValue.setCurrentValueFromCli( "true" );
    }

    public OptionWithoutValue( String name, boolean required, IValueCallback<Boolean> booleanCallbackIfSet ) {
        super( name, required );
        this.internalValue = new BooleanValue( true, null, booleanCallbackIfSet );
    }

    public BooleanValue getInternalValue() {
        return this.internalValue;
    }

    @Override
    public void addValue( IValue<?> value ) {
        throw new UnsupportedOperationException( "This option has no value option." );
    }

    @Override
    public void setExistentInCli() {
        super.setExistentInCli();
        internalValue.setCurrentValueFromCli( "true" );
    }

    @Override
    public void propagateValues( List<String> cliValues ) {
        // do nothing, since there is not IOptionValue to propagate argValues to
    }

    @Override
    public void triggerCallback() {

        if ( existentInCli ) {
            internalValue.setCurrentValueFromCli( "true" );
        } else {
            internalValue.setCurrentValueFromCli( "false" );
        }

        internalValue.getValue();
    }
}
