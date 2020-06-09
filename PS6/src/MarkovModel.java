import java.util.Random;
import java.util.Hashtable;
import java.util.ArrayList;

/**
 * This is the main class for your Markov Model.
 *
 * Assume that the text will contain ASCII characters in the range [1,255].
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
public class MarkovModel {

	// Use this to generate random numbers as needed
	private Random generator = new Random();

	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;

	public Hashtable<String, int[]> HTfreqOfChar;
	public Hashtable<String, Integer> HTfreqOfString;
	public int order;

	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public MarkovModel(int order, long seed) {
		// Initialize your class here
		this.HTfreqOfChar = new Hashtable<>();
		this.HTfreqOfString = new Hashtable<>();
		this.order = order;
		// Initialize the random number generator
		generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 */
	public void initializeText(String text) {
		// Build the Markov model here
		// add keys, which is strings of length order
		// iterate through text

		for (int i = 0; i < text.length() - this.order; i++) {
			String kString = text.substring(0 + i, this.order + i);
			if (this.HTfreqOfChar.containsKey(kString)) {
				char c = text.charAt(this.order + i);
				int ascii = (int) c;
				int[] arr = this.HTfreqOfChar.get(kString);
				arr[ascii]++;
				int freq = this.HTfreqOfString.get(kString);
				freq++;
				this.HTfreqOfString.replace(kString, freq);
			} else {
				this.HTfreqOfChar.put(kString, new int[256]);
				char c = text.charAt(this.order + i);
				int ascii = (int) c;
				int[] arr = this.HTfreqOfChar.get(kString);
				arr[ascii]++;
				this.HTfreqOfString.put(kString, 1);
			}
		}

	}

	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) {
		if (kgram.length() == this.order && this.HTfreqOfString.containsKey(kgram)) {
			return this.HTfreqOfString.get(kgram);
		}
		return 0;
	}

	/**
	 * Returns the number of times the character c appears immediately after the specified kgram.
	 */
	public int getFrequency(String kgram, char c) {
		if (kgram.length() == this.order && this.HTfreqOfString.containsKey(kgram)) {
			int ascii = (int) c;
			int[] arr = this.HTfreqOfChar.get(kgram);
			return arr[ascii];
		}
		return 0;
	}

	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 */
	public char nextCharacter(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.

		if (this.HTfreqOfChar.containsKey(kgram)) {
			// create array of frequency of char after kgram,
			int[] arr = this.HTfreqOfChar.get(kgram);
			ArrayList<Character> freqArr = new ArrayList<>();
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] != 0) {
					int freq = arr[i];
					for (int j = 0; j < freq; j++) {
						char c = (char) i;
						freqArr.add(c);
					}
				}
			}
			int randomNum = generator.nextInt(freqArr.size());
			return freqArr.get(randomNum);
		}
		return NOCHARACTER;
	}
}
