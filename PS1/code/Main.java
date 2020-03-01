package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        int output = MysteryFunction(5, 5);
//        System.out.printf("The answer is: " + output + ".");
        int x = 1/2;
        System.out.println(x);
    }

    static int MysteryFunction(int argA, int argB) {
        int c = 1;
        int d = argA; //5
        int e = argB; //5

        while (e > 0) {
            if (2 * (e / 2) != e) {
                c = c * d; // 5
            }
            d = d * d; //25, 625,
            e = e / 2; // 2, 1
        }
        return c;
    }
}
