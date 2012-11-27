package de.martin_senne.jcommandline;

import de.martin_senne.jcommandline.option.SingleValuedOption;
import de.martin_senne.jcommandline.value.BooleanValue;

/**
 * @author  Martin Senne
 */
public class SimpleExampleCmd extends AbstractCmd {

    private BooleanValue booleanValue;
    private SingleValuedOption singleValueOption;

    protected SimpleExampleCmd(String[] args) {
        super(args);
    }

    @Override
    protected void setupParser() {

        // -a [false|true]
        booleanValue = new BooleanValue(true, null);
        singleValueOption = new SingleValuedOption("a", true, booleanValue);
        getCommandLineParser().addChild(singleValueOption);
    }


    @Override
    public String getUsageString() {
        return "-a [false|true]";
    }

//    @Override
//    protected void parsingFailed() {
//        System.out.println("Failed:");
//        System.out.println(getCommandLineParser().getErrorMessage());
//        output();
//    }
//
    
    public void output() {
        System.out.println("Success");
        System.out.println( "booleanValue is valid      : " + booleanValue.isValid() );
        System.out.println( "booleanValue has value     : " + booleanValue.getValue() );
        System.out.println( "singleValueOption is valid : " + singleValueOption.isValid() );
    }


    @Override
    protected void run() {
        output();
    }

    public static void main(String[] args) {
        String[] argsTest1 = {"-a","false"};  // valid
        AbstractCmd.go(new SimpleExampleCmd(argsTest1));
    }
}

