package com.company;
import java.io.Serializable;
import java.util.ArrayList;

//import apple.laf.JRSUIUtils;

//import apple.laf.JRSUIUtils;

import java.util.Arrays;

/**
 * A class to encapsulate the code tree
 */
public class UniformTree extends BTree implements ITree {
    // Constructor: builds a tree from a collection of words
    UniformTree(Byte[] datawords) {
        buildTree(datawords);
    }

    // Constructor: build the tree by reading in from a file
    UniformTree(ReadFileWrapper inFile) {
        readTree(inFile);
    }

    /*************************************************
     *
     * These are the routines to implement.
     * They build the tree from an array of words,
     * and query the tree.
     *
     *************************************************/

    /**
     * Builds the tree
     *
     * @param datawords
     * @return
     */
    private void buildTree(Byte[] datawords) {
        int len = datawords.length;
        System.out.println(Arrays.toString(datawords));
        super.root = expandTree(datawords, 0, len-1);


    }

    private TreeNode expandTree(Byte[] dataWords, int start, int end) {

        if (start == end) {
            return new TreeNode(dataWords[end]);
        } else {
            int mid = (start + end) / 2;
            TreeNode middle = new TreeNode(dataWords[mid]);
            middle.setLeft(expandTree(dataWords, start, mid));
            middle.setRight(expandTree(dataWords, mid + 1, end));
            return middle;
        }
    }

        /**
         * This function takes a code and looks it up in the tree.
         * If the result is a leaf (at the proper depth), it returns
         * the data byte associated with that leaf.  Otherwise it returns null.
         * @param code
         * @param bits
         * @return
         */
        public Byte query(boolean[] code, int bits) {

            TreeNode node = super.root;

            for (int i = 0; i < bits; i++) {
                if (code[i] == true) {
                    node = node.getRight();
                    if (node.isLeaf() && i < bits-1) {
                        return null;
                    }
                } else {
                    node = node.getLeft();
                    if (node.isLeaf() && i < bits-1) {
                        return null;
                    }
                }
            }
            if (node.isLeaf()) {
                return node.data;
            } else {
                return null;
            }


        }



        /**
         * Returns the codeword associated with the symbol key
         * or null if not in the tree.
         * @param key
         * @return
         */
        public boolean[] queryCode(byte key) {
            ArrayList<Boolean> codeArr = new ArrayList<Boolean>();


            return queryCodeHelper(key, this.root, codeArr);
        }

        public boolean[] queryCodeHelper (byte key, TreeNode node, ArrayList<Boolean> codeArr){

            if (node.isLeaf() && node.data != key) {
                return null;
            } else if (node.isLeaf() && node.data == key) {
                boolean[] booleanCodeArr = new boolean[codeArr.size()];
                int i = 0;
                for (Boolean x: codeArr) {
                    booleanCodeArr[i] = codeArr.get(i);
                    i++;
                }
                return booleanCodeArr;
            } else if (key > node.data) {
                codeArr.add(true);
                return queryCodeHelper(key, node.getRight(), codeArr);
            } else {
                codeArr.add(false);
                return queryCodeHelper(key, node.getLeft(), codeArr);

            }
        }


}