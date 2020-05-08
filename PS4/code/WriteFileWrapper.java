package com.company;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class WriteFileWrapper {

    String fileName;
    FileOutputStream outFile;
    int buffer = 0;
    int bSize = 0;

    WriteFileWrapper(String name){
        fileName = name;
        try {
            outFile = new FileOutputStream(name);
        } catch(Exception e) {
            System.out.println("Error opening file to write.");
        }
    }

    void writeBit(boolean bit) throws IOException {
        buffer = buffer << 1;
        if (bit) {
            buffer = buffer + 1;
        }
        bSize++;
        if (bSize == 8){
            outFile.write((byte)buffer);
            bSize = 0;
            buffer = 0;
        }
    }

    void write(byte b) throws IOException {
        for (int i=7; i>=0; i--){
            boolean bit = ((b & (1 << i)) > 0);
            writeBit(bit);
        }
    }

    void write(char c) throws IOException {
        ByteBuffer b = ByteBuffer.allocate(2);
        b.putChar(c);
        write(b.get(1));
        write(b.get(0));
    }

    void writeInt(int i) throws IOException {
        ByteBuffer b = ByteBuffer.allocate(4);
        b.putInt(i);
        write(b.get(0));
        write(b.get(1));
        write(b.get(2));
        write(b.get(3));
    }

    void flushBuffer() throws IOException {
        int padding = (8 - bSize);
        buffer = buffer << padding;
        outFile.write((byte)buffer);
    }

    void close(){
        try {
            outFile.close();
        } catch(Exception e){
            System.out.println("Error closing file.");
        }
    }

}
