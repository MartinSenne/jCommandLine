package de.martin_senne.jcommandline.value;

/**
 * @author  Martin Senne
 */
public interface IValueCallback<U> {
    void setValue(U value, IValue sendingValue);
}
