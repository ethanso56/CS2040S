package com.company;

/**
 * The Optimization class contains a static routine to find the maximum in an array that changes direction at most once.
 */
public class Optimization {

    /**
     * A set of test cases.
     */
    static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, -100, -80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83}
    };

    /**
     * Returns the maximum item in the specified array of integers which changes direction at most once.
     *
     * @param dataArray an array of integers which changes direction at most once.
     * @return the maximum item in data Array
     */

        public static int searchMax(int[] dataArray) {
            // Running time: logn
            int start = 0;
            int end = dataArray.length-1;
            // if length is 1
            if (dataArray.length == 1) {
                return dataArray[0];
            // if length is 2
            } else if (dataArray.length == 2) {
                if (dataArray[0] > dataArray[1]) {
                    return dataArray[0];
                } else {
                    return dataArray[1];
                }
                // if decreasing or v shape
            } else if (dataArray[1] < dataArray[0]) {
                if (dataArray[0] > dataArray[dataArray.length-1]) {
                    return dataArray[0];
                } else {
                    return dataArray[dataArray.length-1];
                }
            } else {
                // if increasing or n shape
                while (start <= end) {
                    int mid = start + (end - start)/2;
                    if (start == end) {
                        return dataArray[start];
                    } else if (start == end - 1) {
                        if (dataArray[start] < dataArray[end]) {
                            return dataArray[end];
                        } else {
                            return dataArray[start];
                        }
                    } else if (dataArray[mid] > dataArray[mid-1] && dataArray[mid] > dataArray[mid+1]) {
                        return dataArray[mid];
                    } else if (dataArray[mid] < dataArray[mid-1]) {
                        end = mid-1;
                    } else {
                        start = mid + 1;
                    }
                }
                return dataArray[start];
            }
        }


    /**
     * A routine to test the searchMax routine.
     */
    public static void main(String[] args) {
        for (int[] testCase : testCases) {
            System.out.println(searchMax(testCase));
        }
    }
}
