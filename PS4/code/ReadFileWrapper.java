package com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ReadFileWrapper {

    String fileName;
    FileInputStream inFile;
    int buffer = 0;
    int bSize = 0;

    ReadFileWrapper(String name){
        fileName = name;
        try {
            inFile = new FileInputStream(name);
        } catch(Exception e) {
            System.out.println("Error opening file to write.");
        }
    }


    byte read() throws IOException {
        byte c = 0;
        for (int i=7; i>=0; i--){
            boolean bit = readBit();
            c = (byte)(c << 1);
            if (bit) {
                c = (byte)(c + 1);
            }
        }
        return c;
    }

    boolean emptyFile() throws IOException {
        if (bSize > 0) return false;
        return (inFile.available() <= 0);
    }

    boolean readBit() throws IOException {
        if (bSize == 0){
            if (emptyFile()) throw new IOException();
            buffer = (byte)inFile.read();
            bSize = 8;
        }

        boolean bit = ((buffer & (1 << bSize-1)) > 0);
        bSize--;
        return bit;
    }

    int size() throws IOException {
        return inFile.available();
    }

    int readInt() throws IOException {
        ByteBuffer b = ByteBuffer.allocate(4);
        b.put(0, read());
        b.put(1, read());
        b.put(2, read());
        b.put(3, read());
        return b.getInt();
    }

    char readChar() throws IOException {
        ByteBuffer b = ByteBuffer.allocate(2);
        b.put(1, read());
        b.put(0, read());
        return b.getChar();
    }

    void close(){
        try {
            inFile.close();
        } catch(Exception e){
            System.out.println("Error closing file.");
        }
    }

}
