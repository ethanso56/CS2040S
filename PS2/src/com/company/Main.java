package com.company;

import static org.junit.Assert.assertEquals;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int[] arr = {1};
        System.out.println(searchMax(arr));
//        int[] jobs = {1, 3, 5, 7, 9, 11, 10, 8, 6, 4};
//        int processors = 10;
//        int queryLoad = 10;
//        System.out.println(LoadBalancing.feasibleLoad(jobs, queryLoad, processors));
    }

    public static int searchMax(int[] dataArray) {
        // Running time: logn
        int start = 0;
        int end = dataArray.length-1;
        // if length is 1
        if (dataArray.length == 1) {
            return dataArray[0];
        } else {
            // if decreasing or v shape
            if (dataArray[1] < dataArray[0]) {
                if (dataArray[0] > dataArray[dataArray.length-1]) {
                    return dataArray[0];
                } else {
                    return dataArray[dataArray.length-1];
                }
            } else {
                // if increasing or n shape
                while (start < end) {
                    int mid = start + (end - start)/2;
                    if (dataArray[mid] > dataArray[mid-1] && dataArray[mid] > dataArray[mid+1]) {
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
    }


//    public static int searchMax(int[] dataArray) {
//        // find peak
//        // if increasing/n = binary search
//        // if decreasing/v = compare start, end
//        int start = 0;
//        int end = dataArray.length-1;
//
//        if (dataArray[1] < dataArray[0]) {
//            if (dataArray[0] > dataArray[dataArray.length-1]) {
//                return dataArray[0];
//            } else {
//                return dataArray[dataArray.length-1];
//            }
//        } else {
//            while (start < end) {
//                int mid = start + (end - start)/2;
//                if (dataArray[mid] > dataArray[mid-1] && dataArray[mid] > dataArray[mid+1]) {
//                    return dataArray[mid];
//                } else if (dataArray[mid] < dataArray[mid-1]) {
//                    end = mid-1;
//                } else {
//                    start = mid + 1;
//                }
//            }
//            return dataArray[start];
//        }
//


}
