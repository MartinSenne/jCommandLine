package de.martin_senne.jcommandline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Read and evaluate command line. The format of the command line is follows:
 *
 * <pre>-option [value11 [value2 ... ]] -option [value1 [value2 ... ]] ...</pre>
 *
 * <p>Within this implemenation, each option starts with a dash "-". The dash sign "-" is therefore not allowed
 * at the beginning of value. <br/>
 * Exception: if a "-" sign is followed by a number, this string is treated as a negative number, instead as an option.
 * </p>
 *
 * <p>Options and values are seperated by whitespace. The values of one option run, until the next option is found or
 * till end of command line arguments is reached.</p>
 */
public class CommandLine {

    private Map<String, List<String>> optionsAndValues;

    public CommandLine(String[] args) {

        optionsAndValues = new HashMap<String, List<String>>();

        String currentOption = null;

        for ( int i = 0; i < args.length; i++ ) {

            String entity = args[i];
            
            if ( isOption( entity ) ) { // option found
                currentOption = getOptionName( entity );
                addOption( currentOption );
            } else { // value found
                if ( currentOption != null ) {
                    optionsAndValues.get( currentOption ).add( entity );
                } else {
                    throw new IllegalArgumentException( "Command line not starting with an option.");
                }
            }
        }
    }

    private void addOption(String option) {
        if (!optionsAndValues.containsKey(option)) {
            optionsAndValues.put( option, new ArrayList<String>() );
        } else {
            throw new IllegalArgumentException("Option '" + option + "' already exists.");
        }
    }

    /**
     * Get name of option. Here "-asdf" is convert to "asdf".
     *
     * @param optionFromCli
     * @return optionFromCli without dash
     */
    private String getOptionName( String optionFromCli ) {
        return optionFromCli.substring(1, optionFromCli.length() );
    }

    /**
     * Checks if option {@code option} exists in commandline.
     *
     * @param   option
     * @return  true if the specified option exists.
     */
    public boolean hasOption(String option) {
        return optionsAndValues.containsKey( option );
    }

    /**
     * Get all values for option as list of strings.
     *
     * @param   option  to retrieve values for
     * @return  argument of command cmd null if command cmd is not present
     */
    public List<String> getValues(String option) {
        return optionsAndValues.get( option );
    }

    private boolean isNumber( String s ) {
        try {
            Double.parseDouble( s );
            return true;
        } catch (NumberFormatException nfe ) {
            return false;
        }
    }

    private boolean isOption(String possibleOption) {
        if ( possibleOption.startsWith("-") && !isNumber( possibleOption ) ) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the specified command cmd has the given argument arg.
     *
     * @param   option  is the option name
     * @param   argument  argument to check
     *
     * @return  true if command cmd has argument arg
     */
    public boolean hasValue(String option, String argument) {
        List<String> valuesForOption = optionsAndValues.get( option );
        if (valuesForOption != null) {
            return valuesForOption.contains( argument );
        } else {
            return false;
        }
    }

    /**
     * Determine the number of values this option has.
     *
     * @param   option  option name (without dash) to get number of values for.
     *
     * @return  number of arguments
     */
    public int getNumberOfValues(String option) {
        List<String> valuesForOption = optionsAndValues.get( option );
        if (valuesForOption != null) {
            return valuesForOption.size();
        } else {
            return -1;
        }
    }

    /**
     * Get value of option {@code option} with selected index.
     *
     * @param   option   whose arguments to obtain
     * @param   index  index of argument for given command
     *
     * @return  ith argument of command cmd
     */
    public String getValue(String option, int index) {
        List<String> valuesForOption = optionsAndValues.get( option );
        if (valuesForOption != null) {
            return valuesForOption.get( index );
        } else {
            return null;
        }
    }

    /**
     * Returns the first value of given option.
     */
    public String getFirstValue(String option) {
        return getValue(option, 0);
    }

    public Collection<String> getOptions() {
        return optionsAndValues.keySet();
    }
}
