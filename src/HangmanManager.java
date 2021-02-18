import java.util.*;

public class HangmanManager {
    /* Contains all currently considered words */
    private Set<String> answerSpace;
    /* All guesses made by the player */
    private SortedSet<Character> guessedCharacters;
    /* The number of guesses remaining */
    private int guessesRemaining;
    /* The pattern that is to be displayed */
    private String pattern;

    /*
     * Constructor
     * Initializes the HangmanManager from dictionary with words of length length. Allows a maximum of max guesses.
     *
     * Throws IllegalArgument exceptions if length is less than one or if max is less than zero.
     *
     * Parameters: dictionary of words to use, length of words to select, number of allowed incorrect guesses
     */
    public HangmanManager(Collection<String> dictionary, int length, int max) {
        /* Exception checking */
        if (length < 1) {
            throw new IllegalArgumentException("Length must be 1 or more.");
        } else if (max < 0) {
            throw new IllegalArgumentException("Maximum incorrect guesses must be more than 0.");
        }

        /* Add all words of the correct length from dictionary */
        answerSpace = new TreeSet<>();
        for (String word : dictionary) {
            if (word.length() == length) {
                answerSpace.add(word);
            }
        }

        guessedCharacters = new TreeSet<>();
        guessesRemaining = max;

        /* Make a pattern of dashes */
        pattern = "-";
        for (int i = 1; i < length; i++) {
            pattern += " -";
        }
    }

    /* Returns a copy of answerSpace */
    public Set<String> words() {
        /* Use the copy constructor */
        return new TreeSet<>(answerSpace);
    }

    /* Returns the number of guesses remaining */
    public int guessesLeft() {
        return guessesRemaining;
    }

    /* Returns a copy of guessedCharacters */
    public SortedSet<Character> guesses() {
        /* Uses the copy constructor */
        return new TreeSet<>(guessedCharacters);
    }

    /* Returns the pattern to the user */
    public String pattern() {
        if (answerSpace.isEmpty())
            throw new IllegalStateException("answer space should not be empty");
        return pattern;
    }

    /*
     * Allows the user to guess a letter of the word and updates the state of HangmanManager accordingly.
     *
     * If there are no possible answers remaining, throws an IllegalStateException (no words were found).
     * If there are not enough guessedCharacters remaining, throws an IllegalStateException.
     * If the character has already been guessed, throws an IllegalArgumentException.
     *
     * Parameter: Letter being guessed
     * Returns: Number of occurrences  of that letter in the pattern
     */
    public int record(char guess) {

        /***** ADD YOUR CODE HERE *******/
        /*** What to do in this method:
         * 1. check for exceptions as described above (lines 74-76)
         * 2. First make sure to update guessedCharacters
         * 3. Build a map of potential results. Use the buildMap() method below
         * 4. Update answerSpace and pattern to show to user
         * 5. Return the number of occurrences of guess in the new pattern
         ***/
        //It should NOT return 0 all the time. Change this!
        return 0;
    }



    /*
     * Helper method for record()
     * Generates a map of index combinations to sets of word that match those index combinations.
     *
     * Example) "answerMap" is a Map with the following values when run with dictionary2.txt
     * and the guess entered was 'a':
     * - - - - = [cool, else, flew, good, hope, ibex]
     * - - - a = [beta]
     * - - a - = [deal]
     * a - - - = [ally]
     *
     * Parameters: character being guessed
     * Returns: map of index combinations to word sets
     */
    private Map<String, Set<String>> buildMap(char guess) {
        Map<String, Set<String>> answerMap = new TreeMap<>();
        for(String word:answerSpace) {
            String key = buildKey(word, guess);
            /* If the key does not exist, add it */
            Set<String> value;
            if(!answerMap.containsKey(key)) {
                value = new TreeSet<>();
            } else {  /* If the key does exist, update it */
                value = answerMap.get(key);
            }
            value.add(word);
            answerMap.put(key, value);
        }
        return answerMap;
    }

    /**
     * Helper method for buildMap
     * This method builds the new pattern of the word given the word and the guess and the current pattern
     * The pattern will be the key for the map
     * (e.g., say the current pattern is "_ _ _ l _"
     * @param word (e.g. "apple")
     * @param guess (e.g. "a")
     * @return returns the pattern of the word (e.g. "a _ _ l _") based on current pattern and guess
     */
    private String buildKey(String word, char guess) {
        StringBuilder sb = new StringBuilder(pattern); //initialize with current pattern
        for(int i = 0; i < word.length(); i++) {
            if(word.charAt(i) == guess) { //match
                sb.setCharAt(i * 2, guess); //change the char at index i * 2 to guess
            }
        }
        return sb.toString();
    }

}

