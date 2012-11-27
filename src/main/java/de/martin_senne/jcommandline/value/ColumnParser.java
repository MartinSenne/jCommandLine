//package de.martin_senne.jcommandline.value;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// *
// * @author  Martin Senne
// */
//public class ColumnParser {
//    public static void main(String[] args) {
//        // Pattern comma = Pattern.compile(",");
//        Pattern trying = Pattern.compile("(\\d+-\\d*|\\d*-\\d+|\\d+)(,(\\d+-\\d*|\\d*-\\d+|\\d+))*");
//
//        // Pattern simple = Pattern.compile("(\\d+-\\d*|\\d*-\\d+|\\d+)(,(\\d+-\\d*|\\d*-\\d+|\\d+))*");
//
//        Pattern number = Pattern.compile("(\\d+)");
//        Pattern numberRange = Pattern.compile("(\\d+)-(\\d+)");
//        Pattern numberRangeStep = Pattern.compile("(\\d+)-(\\d+):(\\d)+");
//
//        String test = "1-35:5, 37-49,53,60-61";
//        String[] elements = test.split(",(\\s)*");
//
//        int[] totalSelection = new int[0];
//
//        for (String element : elements) {
//            Matcher matcherNumber = number.matcher(element);
//            Matcher matcherNumberRange = numberRange.matcher(element);
//            Matcher matcherNumberRangeStep = numberRangeStep.matcher(element);
//
//            if ( matcherNumber.matches() ) {
//                totalSelection = IntArrays.concat(totalSelection, Integer.parseInt( matcherNumber.group(1)) );
//            } else if ( matcherNumberRange.matches() ) {
//                int start = Integer.parseInt(matcherNumberRange.group(1));
//                int end = Integer.parseInt(matcherNumberRange.group(2));
//
//                int[] t = IntArrays.range(start, end, 1, true);
//                System.out.println(ArrayOutput.toString(t, " x "));
//                totalSelection = IntArrays.concat(totalSelection, t );
//            } else if ( matcherNumberRangeStep.matches() ) {
//                int start = Integer.parseInt(matcherNumberRangeStep.group(1));
//                int end = Integer.parseInt(matcherNumberRangeStep.group(2));
//                int stepw = Integer.parseInt(matcherNumberRangeStep.group(3));
//                int[] t = IntArrays.range(start, end, stepw, true); // include ends
//                totalSelection = IntArrays.concat(totalSelection, t );
//                System.out.println(ArrayOutput.toString(t, " x "));
//            } else {
//                System.out.println("SYNTAX ERROR!");
//            }
//        }
//
//        for (int i : totalSelection) {
//            System.out.print(i + " ");
//        }
//
//        // System.out.println( ArrayOutput.toString( IntArrays.range2(3, 8, 2 ), ";" ) );
////
////        System.out.println("\n================");
////        System.out.println( ArrayOutput.toString( IntArrays.range(3, 3, 1 ), ", " ) );
////        System.out.println( ArrayOutput.toString( IntArrays.range(3, 5, 1 ), ", " ) );
////        System.out.println( ArrayOutput.toString( IntArrays.range(3, 3, 2 ), ", " ) );
////        System.out.println( ArrayOutput.toString( IntArrays.range(3, 7, 2 ), ", " ) );
////        System.out.println( ArrayOutput.toString( IntArrays.range(3, 8, 2 ), ", " ) );
////        System.out.println("\n================");
////
////        System.out.println( ArrayOutput.toString( IntArrays.range(3, 3, 1, false ), ", " ) );
////        System.out.println( ArrayOutput.toString( IntArrays.range(3, 5, 1, false ), ", " ) );
////        System.out.println( ArrayOutput.toString( IntArrays.range(3, 3, 2, false ), ", " ) );
////        System.out.println( ArrayOutput.toString( IntArrays.range(3, 7, 2, false ), ", " ) );
////        System.out.println( ArrayOutput.toString( IntArrays.range(3, 8, 2, false ), ", " ) );
////        System.out.println("\n================");
////        System.out.println( ArrayOutput.toString( IntArrays.range(3, 3, 1, true ), ", " ) );
////        System.out.println( ArrayOutput.toString( IntArrays.range(3, 5, 1, true ), ", " ) );
////        System.out.println( ArrayOutput.toString( IntArrays.range(3, 3, 2, true ), ", " ) );
////        System.out.println( ArrayOutput.toString( IntArrays.range(3, 7, 2, true ), ", " ) );
////        System.out.println( ArrayOutput.toString( IntArrays.range(3, 8, 2, true ), ", " ) );
//
////        System.out.println( trying.matcher("34-37").matches() );
////        System.out.println( trying.matcher("34-").matches() );
////        System.out.println( trying.matcher("35-").matches() );
////        System.out.println( trying.matcher("35-,24-36,23").matches() );
////        System.out.println( trying.matcher("3--").matches() );
//    }
//}
