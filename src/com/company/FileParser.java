package com.company;
// This class uses the following two packages (for manipulating files)
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

// The new class is declared here, and it extends VectorTextFile
public class FileParser {

	// Filename
	String fileName = null;

	// Array of bytes in the file
	HashMap<Byte, Integer> wordList;

	/**
	 * Constructor
	 * @param name filename with which to create this vector
	 */
	FileParser(String name) {
		fileName = name;
		wordList = new HashMap<Byte, Integer>(100);
		ParseFile(fileName);
	}

	/**
	 * Returns the byte and counts in the file.
	 * @return
	 */
	HashMap<Byte, Integer> getWords() {
		return wordList;
	}

	/**
	 * Parses the file and counts the bytes
	 * @param fileName
	 */
	private void ParseFile(String fileName)
	{
		// Declare and initialize local variables
		FileInputStream inputStream = null;
		int iSize = 0;

		// Begin a block of code that handles exceptions
		try{
			// Open the file as a stream
			inputStream = new FileInputStream(fileName);

			// Determine the size of the file, in bytes
			iSize = inputStream.available();

			// Read in the file, one character at a time.
			// Read each character.
			for (int i=0; i<iSize; i++)
			{
				// Read a character
				byte c = (byte)inputStream.read();

				if (wordList.containsKey(c))
				{
					int count = wordList.get(c)+1;
					wordList.put(c, count);
				}
				else
				{
					wordList.put(c, 1);
				}
			}
			// If the file is open, then close it.
			if (inputStream != null){
				inputStream.close();
			}
		}// end of try block
		catch(Exception e){
			System.out.println("Error parsing file.");
		}
	}
}
