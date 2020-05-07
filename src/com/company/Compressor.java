package com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Compresses the file using the specified tree.
 */
public class Compressor {

    /**
     * Compresses the specified file, and writes it to the specified output file
     * @param inName
     * @param outName
     * @param tree
     */
    static private void compressFile(String inName, String outName, ITree tree){

        // Begin a block of code that handles exceptions
        try{
            // Open the files for reading and writing.
            ReadFileWrapper inFile = new ReadFileWrapper(inName);
            WriteFileWrapper outFile = new WriteFileWrapper(outName);

            // First, write the tree to the output file
            tree.writeTree(outFile);

            // Second, write the number of bytes in the input file
            outFile.writeInt(inFile.size());

            // Read in the file, one character at a time.
            // For each character, normalize it, removing punctuation and capitalization.
            while (!inFile.emptyFile())
            {
                // Read a character
                byte c = inFile.read();

                // Look up the code in the tree
                boolean[] code = tree.queryCode(c);

                // Now write the code to disk, one bit at a time.
                for (int bits=0; bits < code.length; bits++){
                    outFile.writeBit(code[bits]);
                }

            }
            // Finish writing any bits that are left
            outFile.flushBuffer();

            // Close the files.
            if (inFile != null){
                inFile.close();
            }
            if (outFile != null) {
                outFile.close();
            }
        }// end of try block
        catch(Exception e){
            System.out.println("Error parsing file.");
        }
    }

    /**
     * main
     * @param args
     */
    public static void main(String[] args){
        // Parse the input file
        FileParser parse = new FileParser("/Users/ethanso/Desktop/CS2040S/ps4/misc/hamlet.txt");
        // the tree
        HashMap<Byte, Integer> words = parse.getWords();
        WeightedTree codeTree = new WeightedTree(words);
        // Compress the file
        compressFile("hamlet.txt", "out3.txt", codeTree);
    }
}

