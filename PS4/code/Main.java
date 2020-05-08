package com.company;

import java.util.Arrays;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
	// write your code here
        FileParser fp = new FileParser("/Users/ethanso/Desktop/CS2040S/ps4/misc/hello.txt");
        HashMap<Byte, Integer> words = fp.getWords();
        Byte[] b = new Byte[words.size()];
        int i = 0;
        for (Byte by : words.keySet()) {
            b[i] = by;
            i++;
        }
        UniformTree x = new UniformTree(b);
        x.printTree();
        System.out.println(x.root.getLeft().getLeft().getLeft().getLeft().getLeft());
        System.out.println(Arrays.toString(x.queryCode((byte) 108)));


    }
}
