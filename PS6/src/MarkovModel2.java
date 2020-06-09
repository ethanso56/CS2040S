import java.util.HashMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;


public class MarkovModel2 {

    // Use this to generate random numbers as needed
    private Random generator = new Random();
    private Map<String, Map<String, Integer>> map = new HashMap<>();

    // This is a special symbol to indicate no string
    public static final String NOSTRING = "";

    /**
     * Constructor for MarkovModel class
     *
     * @param seed the seed used by the random number generator
     */
    public MarkovModel2(long seed) {
        generator.setSeed(seed);
    }

    /**
     * Builds a Markov Model based on the specified text string
     * @param text
     */
    public void initializeText(String text) {
        // We iterate through each substring and increment the count
        // of each word after the word we are currently at

        StringBuilder sb = new StringBuilder(text);

        while (sb.indexOf(" ") != -1) {
            int index = sb.indexOf(" ");
            String word = sb.substring(0, index);
            String secondWord;
            if (sb.indexOf(" ", index + 1) == -1)
                secondWord = sb.substring(index + 1);
            else
                secondWord = sb.substring(index + 1, sb.indexOf(" ", index + 1));

            if (map.containsKey(word)) {
                Map<String, Integer> inner = map.get(word);
                if (inner.containsKey(secondWord)) {
                    int count = inner.get(secondWord);
                    inner.put(secondWord, count + 1);
                } else {
                    inner.put(secondWord, 1);
                }
            } else {
                Map<String, Integer> inner = new HashMap<>();
                inner.put(secondWord, 1);
                map.put(word, inner);
            }

            // We then remove the word we already seen
            sb.delete(0, index + 1);
        }

    }

    /**
     * Returns the number of times the specific kgram appeared in the text
     * @param kgram
     * @return
     */
    public int getFrequency(String kgram) {
        int count = 0;
        if (map.containsKey(kgram)) {
            for (int freq: map.get(kgram).values())
                count += freq;
        }
        return count;
    }

    /**
     * Returns the number of times the string word appears after the kgram
     * @param kgram
     * @param word
     * @return
     */

    public int getFrequency(String kgram, String word) {
        if (map.containsKey(kgram)) {
            Map<String, Integer> inner = map.get(kgram);
            if (inner.containsKey(word)) {
                return inner.get(word);
            }
        }
        return 0;
    }

    /**
     * Generate the next word from the Markov Model.
     * Returns NOSTRING if the kgram is not in the table, or is there is no
     * @param kgram
     * @return
     */
    public String nextWord(String kgram) {

    }

}
