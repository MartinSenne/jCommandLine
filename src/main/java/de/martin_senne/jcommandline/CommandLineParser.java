package de.martin_senne.jcommandline;

import de.martin_senne.jcommandline.option.AndedOption;
import de.martin_senne.jcommandline.option.IOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * Essential class for command line parsing.
 *
 * @author  Martin Senne
 */
public class CommandLineParser {

    protected CommandLine commandLine;

    /** Root element of options. */
    protected AndedOption root;

    /**
     * Construct command line parser with the given {@code args}.
     *
     * @param args command line arguments from console.
     */
    public CommandLineParser(String[] args) {
        if (args == null) {
            this.commandLine = null;
        } else {
            this.commandLine = new CommandLine( args );
        }
        this.root = new AndedOption();
        root.setExistentInCli();
    }

    /**
     * Construct command line parser with no arguments set.
     * Use {@link #setArguments(java.lang.String[]) } to set command line arguments.
     */
    public CommandLineParser() {
        this(null);
    }

    public void setArguments(String[] args) {
        this.commandLine = new CommandLine( args );
    }


    /**
     * Add option {@code parameter}
     * @param parameter
     */
    public void addChild(IOption parameter) {
        root.addChild(parameter);
    }


    /**
     * Check if given arguments are valid.
     *
     * 1. check for additional undefined command line arguments
     * 2. fill parameters with appropriate values from cli
     * 3. check validity of parameters for "active" parameters
     * @return
     */
    public boolean check() {
        if (commandLine == null) {
            throw new RuntimeException("No command line arguments given. Use setArguments().");
        }

        boolean valid = checkForUndefinedCliParameters();
        fillInCliValues();

        if (valid) {
            valid &= root.isValid(); // visit every node of the tree
        }

        if (valid) {
            valid &= allCliParamsConsumed();
        }

        if (valid) {
            System.out.println("Command line parameters valid.");

            for (Iterator<IOption> it = root.onlyValidParametersIterator(); it.hasNext();) {
                IOption param = it.next();
                param.triggerCallback();
            }
        }

        return valid;
    }

    public String getErrorMessage() {
        return root.getErrorMessage();
    }

    protected boolean checkForUndefinedCliParameters() {
        boolean allValid = true;

        for (String paramName : commandLine.getOptions()) {
            boolean currentValid = false;

            for (IOption parameter : root) {

                if (parameter.getName().equals(paramName)) {
                    currentValid = true;
                }
            }

            if (!currentValid) {
                System.out.println("Command line parameter " + paramName + " unrecognized.");
                allValid = false;
            }
        }

        return allValid;
    }


    protected void fillInCliValues() {

        for (String cliOptionName : commandLine.getOptions()) {

            for (IOption option : root) {

                if (option.getName().equals(cliOptionName)) {
                    option.setExistentInCli();

                    // System.out.println( "arguments are: " + Arrays.toString(  arguments.getArguments( cliOptionName
                    // ) )  );
                    option.propagateValues(commandLine.getValues(cliOptionName));
                }
            }
        }
    }

    protected boolean allCliParamsConsumed() {
        Set<String> consumedCliParams = new HashSet<String>( commandLine.getOptions() );

        for (Iterator<IOption> it = root.onlyValidParametersIterator(); it.hasNext();) {
            IOption param = it.next();
            consumedCliParams.remove(param.getName());
        }

        if (consumedCliParams.isEmpty()) {
            return true;
        } else {

            for (String name : consumedCliParams) {
                System.out.println("Parameter '" + name + "' not allowed in this combination.");
            }

            return false;
        }
    }
}
