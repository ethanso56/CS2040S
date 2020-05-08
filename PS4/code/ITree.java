package com.company;

public interface ITree {
    // Write the tree to the specified file
    void writeTree(WriteFileWrapper outFile);

    // Return a codeword for the specified symbol
    boolean[] queryCode(byte key);

    // Return the symbol for the specified codeword
    Byte query(boolean[] code, int bits);

    // Return data in the tree (preorder, inorder, postorder)
    String preorder();
    String inorder();
    String postorder();

    // Prints tree in ASCII format
    void printTree();
}
