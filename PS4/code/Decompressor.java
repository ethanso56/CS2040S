package com.company;

public class Decompressor {

    // Maximum code length
    final static int MAX_CODE = 64;

    /**
     * Decompress the specified file
     * @param inName
     * @param outName
     */
    static private void decompressFile(String inName, String outName){

        // Begin a block of code that handles exceptions
        try{
            // Open the two files
            ReadFileWrapper inFile = new ReadFileWrapper(inName);
            WriteFileWrapper outFile = new WriteFileWrapper(outName);

            // Frist, read the tree in from the file
            ITree tree = new WeightedTree(inFile);

            // Now, read in the size of the remainder of the file
            int size = inFile.readInt();

            // Read all the characters in the file.
            for (int i=0; i<size; i++)
            {
                // Store the codeword here
                boolean[] code = new boolean[MAX_CODE];
                // This is the character we have found so far
                Byte c = null;
                // This is how many bits we have read so far in this codeword
                int bits = 0;
                // Keep reading in bits, until we find a codeword
                while (c == null){
                    code[bits] = inFile.readBit();
                    bits++;
                    // Check if this codeword is in the tree, using the specified number of bits
                    c = tree.query(code, bits);
                }

                // Write the byte you have discovered to the output file
                outFile.write((c.byteValue()));
            }
            // Close the files
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
     * Decompresses the input file and writes it to the output file.
     * @param args
     */
    public static void main(String[] args){
        decompressFile("out3.txt", "new3.txt");
    }
}

