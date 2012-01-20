package de.martin_senne.jcommandline;

import de.martin_senne.jcommandline.CommandLineParser;
import de.martin_senne.jcommandline.option.DefaultOption;
import de.martin_senne.jcommandline.option.IOption;
import de.martin_senne.jcommandline.option.IOptionCallback;
import de.martin_senne.jcommandline.option.SingleValuedOption;
import de.martin_senne.jcommandline.value.AlternativeStringValue;
import de.martin_senne.jcommandline.value.BooleanValue;
import de.martin_senne.jcommandline.value.DoubleValue;
import de.martin_senne.jcommandline.value.StringValue;

import org.junit.After;
import org.junit.AfterClass;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author  Martin Senne
 */
public class OptionWithDifferentTypesOfValues {

    protected CommandLineParser clp;
    
    private StringValue freeEnergyFilenameValue;
    private SingleValuedOption freeEnergyOption;
        
    private DoubleValue kTValue;
    private SingleValuedOption kTOption;

    public OptionWithDifferentTypesOfValues() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        clp = new CommandLineParser();
        
        kTValue = new DoubleValue(true, 1.0d);
        kTOption = new SingleValuedOption("ktfactor", false, kTValue);
        
        freeEnergyFilenameValue = new StringValue(false, "");
        freeEnergyOption = new SingleValuedOption("freeenergy", false, freeEnergyFilenameValue);
        freeEnergyOption.addChild(kTOption);

        clp.addChild(freeEnergyOption);
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
    public void testEasy() {
        clp.setArguments(new String[]{ "-freeenergy", "asdf" } );
        assertTrue( clp.check());
        assertTrue( 1.0d == kTValue.getValue());
    }
    
    @Test
    public void testEasyAndkbT() {
        clp.setArguments(new String[]{ "-freeenergy", "asdf", "-ktfactor", "1.3" } );
        
        assertTrue( clp.check());
        assertTrue( 1.3d == kTValue.getValue());
    }
}
