package de.martin_senne.jcommandline;

import de.martin_senne.jcommandline.CommandLineParser;
import org.junit.Ignore;
import de.martin_senne.jcommandline.value.IntegerValue;
import de.martin_senne.jcommandline.option.SingleValuedOption;
import de.martin_senne.jcommandline.value.StringValue;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author  Martin Senne
 */
public class NestingTest {

    IntegerValue aValue;
    SingleValuedOption aOption;

    StringValue bValue;
    SingleValuedOption bOption;

    IntegerValue cValue;
    SingleValuedOption cOption;
    
    CommandLineParser clp;

    private int defaultValueA;
    private String defaultValueB;
    private int defaultValueC;

    @Before
    public void setup() {
        clp = new CommandLineParser();

        defaultValueA = 12;
        defaultValueB = "defaultOfB";
        defaultValueC = 17;
        
        aValue = new IntegerValue( true, defaultValueA );
        aOption = new SingleValuedOption("a", false, aValue);

        bValue = new StringValue( true, defaultValueB );
        bOption = new SingleValuedOption("b", false, bValue);

        cValue = new IntegerValue( true, defaultValueC );
        cOption = new SingleValuedOption("c", true, cValue);

        clp.addChild(aOption);
        aOption.addChild(bOption);
        bOption.addChild(cOption);
    }

    public String getUsageString() {
        return "[-a <string> [-b <string> -c <string>]]";
    }


    @Test
    public void test1() {
        clp.setArguments( new String[] {"-a","5", "-b","valueOfB", "-c","4"} );  // valid
        assertTrue( clp.check() );
        assertTrue( 5 == aValue.getValue() );
        assertTrue( "valueOfB".equals( bValue.getValue() ) );
        assertTrue( 4 == cValue.getValue() );
    }

    @Test
    public void test2() {
        clp.setArguments( new String[] {          "-b","valueOfB", "-c","5"} );  // not valid
        assertFalse( clp.check() );
    }

    @Test
    public void test3() {
        clp.setArguments( new String[] {"-a","3", "-b","valueOfB"          } );  // not valid
        assertFalse( clp.check() );
    }

    @Test
    public void test4() {
        clp.setArguments( new String[] {                           "-c","5"} );  // not valid
        assertFalse( clp.check() );
    }

    @Test
    public void test5() {
        clp.setArguments( new String[] {                                  } );  // valid
        assertTrue( clp.check() );
        assertTrue( defaultValueA == aValue.getValue() ); // default
        assertTrue( defaultValueB.equals( bValue.getValue() ) ); // default
        assertTrue( defaultValueC == cValue.getValue() ); // default
    }

    @Test
    public void test6() {
        clp.setArguments( new String[] {"-a","3"                          } );  // valid
        assertTrue( clp.check() );
        assertTrue( 3 == aValue.getValue() );
        assertTrue( defaultValueB.equals( bValue.getValue() ) ); // default
        assertTrue( defaultValueC == cValue.getValue() ); // default
    }

    @Test
    public void test7() {
        clp.setArguments( new String[] {"-a","5", "-b","valueOfB", "-c","af"} );  // invalid
        assertFalse( clp.check() );
    }

    @Test
    public void test8() {
        clp.setArguments( new String[] {"-a","5", "-b",            "-c","af"} );  // invalid
        assertFalse( clp.check() );
    }
}
