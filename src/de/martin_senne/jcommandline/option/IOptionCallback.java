package de.martin_senne.jcommandline.option;

/**
 * Callback interface of option.
 * 
 * @author  Martin Senne
 */
public interface IOptionCallback {
    void optionOccursInCli(boolean occurs, IOption originOption);
}
