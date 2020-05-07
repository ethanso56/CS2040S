
import org.w3c.dom.ls.LSOutput;

import java.security.Key;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is a simple example for how to use the sorting classes.
 * It sorts three numbers, and measures how long it takes.
 */
public class SortTestExample {

    public static void main(String[] args){
        // Create three key value pairs
        KeyValuePair[] testArray = new KeyValuePair[100];
//        testArray[0] = new KeyValuePair(10, 20);
//        testArray[1] = new KeyValuePair(9, 20);
//        testArray[2] = new KeyValuePair(8, 20);
//        testArray[3] = new KeyValuePair(7, 20);
//        testArray[4] = new KeyValuePair(6, 20);
//        testArray[5] = new KeyValuePair(5, 20);
//        testArray[6] = new KeyValuePair(4, 20);
//        testArray[7] = new KeyValuePair(3, 20);
//        testArray[8] = new KeyValuePair(2, 20);
//        testArray[9] = new KeyValuePair(1, 20);
//        //KeyValuePair[] testArray = SortingTester.generateArrWithRandomKeys(10);
        for (int i = 0; i < 100; i++) {
            testArray[i] = new KeyValuePair(i, 1);
        }

//        // Create a stopwatch
        StopWatch watch = new StopWatch();
        ISort sortingObject = new SorterD();

//        // Do the sorting
        watch.start();
        sortingObject.sort(testArray);
        watch.stop();

//        for (int i = 0; i < 100; i++) {
//            System.out.println(testArray[i]);
//        }

        System.out.println("Time: " + watch.getTime());


        //System.out.println(SortingTester.testArrays(1000, 100));

    }



}
