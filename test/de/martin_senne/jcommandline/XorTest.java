package de.martin_senne.jcommandline;

import de.martin_senne.jcommandline.CommandLineParser;
import de.martin_senne.jcommandline.value.BooleanValue;
import de.martin_senne.jcommandline.option.XoredOption;
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
public class XorTest {

    BooleanValue booleanValue1;
    SingleValuedOption singleValueOption1;

    BooleanValue booleanValue2;
    SingleValuedOption singleValueOption2;

    XoredOption xorOption;

    CommandLineParser clp;

    @Before
    public void setup() {
        clp = new CommandLineParser();

        xorOption = new XoredOption();
        clp.addChild(xorOption);

        // -a [false|true]
        booleanValue1 = new BooleanValue(true, null);
        singleValueOption1 = new SingleValuedOption("a", true, booleanValue1);

        // -b [false|true]
        booleanValue2 = new BooleanValue(true, null);
        singleValueOption2 = new SingleValuedOption("b", true, booleanValue2);

        xorOption.addChild(singleValueOption1);
        xorOption.addChild(singleValueOption2);
    }

    public String getUsageString() {
        return "-a <true|false> | -b <string>";
    }


    @Test
    public void test1() {
        clp.setArguments( new String[] {"-a","true"} );  // valid
        assertTrue( clp.check() );
        assertTrue( true == booleanValue1.getValue() );
    }

    @Test
    public void test2() {
        clp.setArguments( new String[] {"-a","true","-b","false"} );  // not valid
        assertFalse( clp.check() );
    }

    @Test
    public void test3() {
        clp.setArguments( new String[] {"-b","false"} );  // valid
        assertTrue( clp.check() );
        assertTrue( false == booleanValue2.getValue() );
    }

    @Test
    public void test4() {
        clp.setArguments( new String[] {"-b","faasdfse"} );  // not valid
        assertFalse( clp.check() );
    }
}
