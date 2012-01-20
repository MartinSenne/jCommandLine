package de.martin_senne.jcommandline;

import de.martin_senne.jcommandline.CommandLineParser;
import de.martin_senne.jcommandline.option.DefaultOption;
import de.martin_senne.jcommandline.option.IOption;
import de.martin_senne.jcommandline.option.IOptionCallback;
import de.martin_senne.jcommandline.option.SingleValuedOption;
import de.martin_senne.jcommandline.value.BooleanValue;

import org.junit.After;
import org.junit.AfterClass;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author  Martin Senne
 */
public class OverallParserTest {

    protected CommandLineParser clp;

    public OverallParserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    protected void createCommandLineParser( final String[] args, IOption option ) {
        clp = new CommandLineParser(args);
        clp.addChild( option );
    }

    /**
     * Test of addChild method, of class CommandLineParser.
     */
    @Test
    public void testSingleValueOption_OptionRequiredValueRequired() {

        // -a [false|true]
        final BooleanValue booleanValue = new BooleanValue( true, null );
        final SingleValuedOption singleValueOption = new SingleValuedOption( "a", true, booleanValue );

        createCommandLineParser( new String[]{
                    "-a", "true"
                }, singleValueOption );
        assertTrue( "Single Valued option not working for '-a true'.", clp.check() );
        assertTrue( booleanValue.isValid() );
        assertTrue( booleanValue.isExistent() );
        assertEquals( booleanValue.getValue(), true );

        assertTrue( singleValueOption.isExistentInCli() );
        assertTrue( singleValueOption.isValid() );
    }

    @Test
    public void testSingleValueOption_OptionRequiredValueRequired_ValueInvalid() {

        // -a [false|true]
        final BooleanValue booleanValue = new BooleanValue( true, null );
        final SingleValuedOption singleValueOption = new SingleValuedOption( "a", true, booleanValue );

        createCommandLineParser( new String[]{
                    "-a", "asdf"
                }, singleValueOption );
        assertFalse( "Single Valued option not working for '-a asdf'.", clp.check() );
        assertFalse( booleanValue.isValid() );
        assertTrue( booleanValue.isExistent() );

        assertTrue( singleValueOption.isExistentInCli() );
        assertFalse( singleValueOption.isValid() );
    }

    @Test
    public void testSingleValueOption_OptionRequiredValueOptional() {

        // -a [[false|true]]
        // -a with no value => default value
        final BooleanValue booleanValue = new BooleanValue( false, false );
        final SingleValuedOption singleValueOption = new SingleValuedOption( "a", true, booleanValue );

        createCommandLineParser( new String[]{
                    "-a"
                }, singleValueOption );
        assertTrue( "Single Valued option not working for '-a'.", clp.check() );
        assertTrue( booleanValue.isValid() );
        assertFalse( booleanValue.isExistent() );
        assertEquals( false, booleanValue.getValue() ); // test default value

        assertTrue( singleValueOption.isExistentInCli() );
        assertTrue( singleValueOption.isValid() );
    }

    @Test
    public void testSingleValueOption_OptionRequiredValueOptional_ValueInvalid() {

        // -a [[{false}|true]]
        // -a with no value => default value
        final BooleanValue booleanValue = new BooleanValue( false, false );
        final SingleValuedOption singleValueOption = new SingleValuedOption( "a", true, booleanValue );

        createCommandLineParser( new String[]{
                    "-a", "asdf"
                }, singleValueOption );
        assertFalse( "Single Valued option not working for '-a asdf'.", clp.check() );
        assertFalse( booleanValue.isValid() );
        assertTrue( booleanValue.isExistent() );

        assertTrue( singleValueOption.isExistentInCli() );
        assertFalse( singleValueOption.isValid() );
    }

    @Test
    public void testSingleValueOption_OptionOptionalValueOptional() {

        // [-a [[{false}|true]]]
        // -a with no value => default value
        final BooleanValue booleanValue = new BooleanValue( false, false );
        final SingleValuedOption singleValueOption = new SingleValuedOption( "a", false, booleanValue );

        createCommandLineParser( new String[]{
                    "-a"
                }, singleValueOption );
        assertTrue( "Single Valued option not working for '-a'.", clp.check() );
        assertTrue( booleanValue.isValid() );
        assertFalse( booleanValue.isExistent() );
        assertEquals( false, booleanValue.getValue() ); // test default value

        assertTrue( singleValueOption.isExistentInCli() );
        assertTrue( singleValueOption.isValid() );
    }

    @Test
    public void testSingleValueOption_OptionOptionalValueOptional_NoOptionPresent() {

        // [-a [[{false}|true]]]
        // -a with no value => default value
        final BooleanValue booleanValue = new BooleanValue( false, false );
        final SingleValuedOption singleValueOption = new SingleValuedOption( "a", false, booleanValue );

        createCommandLineParser( new String[]{}, singleValueOption );
        assertTrue( "Single Valued option not working for '-a'.", clp.check() );
        assertTrue( booleanValue.isValid() );
        assertFalse( booleanValue.isExistent() );
        assertEquals( false, booleanValue.getValue() ); // test default value

        assertFalse( singleValueOption.isExistentInCli() );
        assertTrue( singleValueOption.isValid() );
    }

    // ======================================================
    @Test
    public void testNoValueOption_Mandatory_OptionPresentInCli() {

        // -a
        final DefaultOption optionWithoutValue = new DefaultOption( "a", true );
        optionWithoutValue.setCallback( new IOptionCallback() {

            public void optionOccursInCli( boolean occurs, IOption originOption ) {
                assertTrue( occurs );
            }
        } );

        createCommandLineParser( new String[]{
                    "-a"
                }, optionWithoutValue );
        assertTrue( "Single Valued option not working for '-a'.", clp.check() );

        assertTrue( optionWithoutValue.isExistentInCli() );
        assertTrue( optionWithoutValue.isValid() );

    }

    @Test
    public void testNoValueOption_Optional_OptionPresentInCli() {

        // [-a]
        final DefaultOption optionWithoutValue = new DefaultOption( "a", false );


        createCommandLineParser( new String[]{
                    "-a"
                }, optionWithoutValue );
        assertTrue( "Single Valued option not working for '-a'.", clp.check() );
        // assertTrue( optionWithoutValue.getInternalValue().isValid());
        // assertTrue( optionWithoutValue.getInternalValue().isExistent());
        // assertEquals( true, optionWithoutValue.getInternalValue().getValue() ); // test default value

        assertTrue( optionWithoutValue.isExistentInCli() );
        assertTrue( optionWithoutValue.isValid() );

    }

    @Test
    public void testNoValueOption_Optional_OptionNotPresentInCli() {

        // [-a]
        final DefaultOption optionWithoutValue = new DefaultOption( "a", false );

        createCommandLineParser( new String[]{}, optionWithoutValue );
        assertTrue( "Single Valued option not working for '-a'.", clp.check() );
        // assertTrue( optionWithoutValue.getInternalValue().isValid());
        // assertFalse( optionWithoutValue.getInternalValue().isExistent());
        // assertEquals(false, optionWithoutValue.getInternalValue().getValue() ); // test default value

        assertFalse( optionWithoutValue.isExistentInCli() );
        assertTrue( optionWithoutValue.isValid() );

    }

    @Test
    public void testNoValueOption_Optional_OptionPresentInCli_Callback() {

        // [-a]
        final DefaultOption optionWithoutValue = new DefaultOption( "a", false );
        optionWithoutValue.setCallback( new IOptionCallback() {

            public void optionOccursInCli( boolean occurs, IOption originOption ) {
                assertTrue( occurs );
            }
        } );

        createCommandLineParser( new String[]{
                    "-a"
                }, optionWithoutValue );
        assertTrue( "Single Valued option not working for '-a'.", clp.check() );

        assertTrue( optionWithoutValue.isExistentInCli() );
        assertTrue( optionWithoutValue.isValid() );

    }

    @Test
    public void testNoValueOption_Optional_OptionNotPresentInCli_Callback() {

        // [-a]
        final DefaultOption optionWithoutValue = new DefaultOption( "a", false );
        optionWithoutValue.setCallback( new IOptionCallback() {

            public void optionOccursInCli( boolean occurs, IOption originOption ) {
                assertFalse( occurs );
            }
        } );

        createCommandLineParser( new String[]{}, optionWithoutValue );
        assertTrue( "Single Valued option not working for '-a'.", clp.check() );

        assertFalse( optionWithoutValue.isExistentInCli() );
        assertTrue( optionWithoutValue.isValid() );

    }
}
