package com.company;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to encapsulate the code tree */
public abstract class BTree {
    // An id to use for nodes
    int edgeId = 1;
    // Each node in the tree is given a unique id
    int nodeId = 0;
    // Number of keys in the tree
    int numKeys = 0;
    // Root of the tree
    TreeNode root = null;
    // Maximum code length
    final static int MAX_CODE = 64;

    // A simple class to hold data symbols and weights
    // It is sortable by data symbol.
    protected static class Pair implements Comparable<Pair> {
        public byte data;
        public int weight;

        Pair(byte w, int i){
            data = w;
            weight = i;
        }

        public int compareTo(Pair other){
            return (data - other.data);
        }
    }

    // A class that encapsulates a tree node
    protected class TreeNode implements Comparable<TreeNode> {
        // Tree neighbors
        protected TreeNode left;
        protected TreeNode right;

        // Tree node data
        protected byte data;
        protected int weight;
        protected int id;

        // Constructor
        TreeNode(){
            id = nodeId++;
        }

        // New node with data d
        TreeNode(byte d){
            id = nodeId++;
            data = d;
        }

        // New node with data d and weight w
        TreeNode(byte d, int w){
            id = nodeId++;
            data = d;
            weight = w;
        }


        // Compares two tree nodes
        public int compareTo(TreeNode other){return data - other.data;}

        // Simple access routines
        public TreeNode getLeft(){return left;}
        public TreeNode getRight(){return right;}
        public void setLeft(TreeNode l){left = l;}
        public void setRight(TreeNode r){right = r;}
        public String toString(){return (" " + (char)(int) data + "" + weight + " ");}
        public String getId(){return id +"." + toString();}

        // Check if it is a leaf
        boolean isLeaf(){
            return (left == null) && (right == null);
        }
    }

    /*************************************************
     *
     * These are the routines to implement.
     * They build the tree from an array of words,
     * and query the tree.
     *
     *************************************************/
    // NOTE: buildTree omitted due to differing signature
    abstract Byte query(boolean[] code, int bits);
    abstract boolean[] queryCode(byte key);

    /******************************************************
     *
     *  Routines for reading and writing the tree from the file
     *
     *******************************************************/
    // Writes the tree to the output file
    public void writeTree(WriteFileWrapper outFile){
        try{
            writeTree(root, outFile);
        } catch(Exception e){
            System.out.println("Error writing tree to file.");
        }
    }

    // Writes the tree to the output file
    void writeTree(TreeNode node, WriteFileWrapper outFile) throws IOException {
        if (node.isLeaf()) {
            outFile.writeBit(true);
            outFile.write(node.data);
        }
        else {
            outFile.writeBit(false);
            writeTree(node.left, outFile);
            writeTree(node.right, outFile);
        }
    }

    // Read the tree from disk
    void readTree(ReadFileWrapper inFile){
        try {
            root = readTreeRecursive(inFile);
        } catch(Exception e){
            System.out.println("Error reading file.");
        }
    }

    // Read the tree from disk
    TreeNode readTreeRecursive(ReadFileWrapper inFile) throws IOException {
        boolean bit = inFile.readBit();
        if (bit) { // then this is a leaf
            byte c = inFile.read();
            return new TreeNode(c);
        }
        else { // this is not a leaf
            TreeNode node = new TreeNode();
            TreeNode left = readTreeRecursive(inFile);
            TreeNode right = readTreeRecursive(inFile);
            node.setLeft(left);
            node.setRight(right);
            return node;
        }
    }

    /******************************************************
     *
     *  Routines return order traversal of tree from root
     *
     *******************************************************/
    public String preorder() { return orderTraversal(root, 0); }
    public String inorder() { return orderTraversal(root, 1); }
    public String postorder() { return orderTraversal(root, 2); }
    private String orderTraversal(TreeNode node, int order) {
        String data = "";
        if (node.left == null && node.right == null) data = node.toString();

        String leftData = "";
        if (node.left != null) leftData = "<" + orderTraversal(node.left, order);

        String rightData = "";
        if (node.right != null) rightData = "<" + orderTraversal(node.right, order);

        if (order == 0) {
            return data+leftData+rightData;
        } else if (order == 1) {
            return leftData+data+rightData;
        } else {
            return leftData+rightData+data;
        }
    }

     /******************************************************
     *
     * Routine to print tree in ASCII format
     * Code referenced: https://stackoverflow.com/a/29704252
     *
     *******************************************************/

    public void printTree() {
        printTree(root);
    }

    private void printTree(TreeNode root)
    {
        List<List<String>> lines = new ArrayList<List<String>>();

        List<TreeNode> level = new ArrayList<TreeNode>();
        List<TreeNode> next = new ArrayList<TreeNode>();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<String>();

            nn = 0;

            for (TreeNode n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    // String aa = (char)(int) n.data + "";
                    String aa = n.toString();
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.getLeft());
                    next.add(n.getRight());

                    if (n.getLeft() != null) nn++;
                    if (n.getRight() != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<TreeNode> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
    }
}
