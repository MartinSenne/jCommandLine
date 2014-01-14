# Overview

jCommandLine is a Java library which allows you to easily parse arbitrary (combinations of) command-line parameters.

# Introduction by example

Say you want parse `yourJavaProgram [-a <integer> [-b <string> -c <integer>]]`, with option "-a" having a default value of 12, "-b" of "defaultOfB" and option "-c" a default of 17. Please note the brackets `[...]`, they denote optional options or optional group of options.

## Arguments parsing examples

* Valid args:
    * `-a 5 -b valueOfB -c 4` is valid ( see clp.check() == true) and values ( a=5, b="valueOfB", c=4 ) can be queried via aValue.getValue() etc.
    * `-a 3` is valid, since `-b` and `-c` together are optional. Values are: a=3 and defaults b="defaultOfB", c=17 (default)
    * ` ` the empty input is valid, as all parameters together are optional. Values are thus the defaults a=12, b="defaultOfB", c=17
* Not valid is:
    * `-a 3 -b valueOfB` is not valid, since `-b` and `-c` must occur together or not at all. ( see innermost bracket `[]`)
    * `-b valueOfB -c 5` is not valid, since they can only be defined, if also `-a` is given.
    * `-c 5`, since `-b` and `-c` must occur together.
    * `-a 5 -b valueOfB -c af` is invalid, since `-c` must have a value of type int, instead of a string "af".
    * `-a 5 -b -c af` is invalid, since not only `-b` has no value but `-c` has wrong type (string instead of int).

## Usage
    
```
    //                                             variable definitions
    // ================================================================
    
    // option a and its assigned value of type int
    IntegerValue aValue;
    SingleValuedOption aOption;

    // option b and its assigned string value
    StringValue bValue;
    SingleValuedOption bOption;

    // option c and its assigned value of type int
    IntegerValue cValue;
    SingleValuedOption cOption;
    
    // set default values
    int defaultValueA = 12;
    int defaultValueB = "defaultOfB";
    int defaultValueC = 17;
        
    //                                                     parser setup
    // ================================================================
    
    // Create parser
    CommandLineParser clp = new CommandLineParser();

    // Create mandatory value of type int. 
    // Thus value must always be given, if option is present.
    aValue = new IntegerValue( true, defaultValueA );
    // create the option, which is optional altogether,
    // and link option and value.
    aOption = new SingleValuedOption("a", false, aValue);

    // Create optional option "b", whose value is mandatory
    // If "-b" is not present, the bValue will get its default
    // value assigned.
    bValue = new StringValue( true, defaultValueB );
    bOption = new SingleValuedOption("b", false, bValue);

    // Create mandatory option "c" with mandatory value.
    cValue = new IntegerValue( true, defaultValueC );
    cOption = new SingleValuedOption("c", true, cValue);

    //                                               option combination
    // ================================================================
    
    // Every option can have children. A regular option is only valid 
    // (in the sense of parsing), if 
    // - the option itself is valid and
    // - if the option in present then all of its children are valid.
    //
    // (see XoredOption for creating alternative options.)
    
    //   clp is valid if "a" is valid
    clp.addChild(aOption);
    
    //   option aOption is valid, if "a" and its child bChild is valid
    aOption.addChild(bOption);
    
    //   option bOption is valid, if "b" is presents and 
    //   its child cChild is valid
    //   IMPORTANT note: 
    //     Option c and its value is mandatory => c <int>
    //     Option b is optional, but value is mandatory [b <string>]
    //     That means:
    //      -b asdf -c 5   is valid
    //              -c 5   is invalid. C must not occur, if b is not 
    //                     present
    //      -b asdf        is invalid
    //      <empty>        is valid, as it option b to be optional.
    //                     The check for validity of children is 
    //                     only performed if b is present.
    // => [-b <string> -c <int>]
    bOption.addChild(cOption);
```
## Main entry point

The main entry point is `public static void main( String[] args )` and provides the args String array.
```
    // set parser arguments
    clp.setArguments( args );
    
    // validate arguments / start parser
    boolean ok = clp.check(); 
    
    if (ok) { // access values
        aValue.getValue(); 
    }
```

## Value types
Options can be linkes with the following values, supporting different type

* AlternativeStringValue
* BooleanValue
* ConstrainedStringValue
* DoubleValue
* IntegerArrayValue
* StringArrayValue
* StringValue

Please see source code for more documentation. 

The values can also can be equipped with callbacks (during construction) to invoke a callback after successful parsing.

Use the interface `IValue` to create your custom values.

# Further Examples

Please refer to the [units tests](https://github.com/MartinSenne/jCommandLine/tree/master/src/test/java/de/martin_senne/jcommandline) of this project.

# Availability

jCommandLine is deployed to standard maven repository and its artifact and POM is available [here](http://search.maven.org/#search|ga|1|jCommandLine).
