import org.junit.*;     // JUnit tools

import java.util.*;     // Collections
import java.io.*;       // File access

public class HangmanManagerTest {

    /* Loads the words in fileName and returns the set of all words in that file*/
    private Set<String> getDictionary(String fileName) {
        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            Set<String> dictionary = new HashSet<>();
            while(fileScanner.hasNext()) {
                dictionary.add(fileScanner.next());
            }
            return dictionary;
        } catch(FileNotFoundException e) {
            Assert.fail("Something went wrong.");      //Something went wrong
        }
        /* Should never be reached. */
        return new HashSet<>();
    }

    /* Creates HangmanManager with only "zzzz" in the dictionary */
    private HangmanManager getDummyManager(int max) {
        Set<String> d = new HashSet<>();
        d.add("zzzz");
        return new HangmanManager(d, 4, max);
    }

    /* Checks that the number of guesses remaining decreases correctly */
    @Test
    public void guessesLeftTest() {
        HangmanManager h = getDummyManager(10);
        Assert.assertEquals("Guesses left did not match the expected value.", h.guessesLeft(), 10);
        h.record('a');
        Assert.assertEquals("Guesses left did not match the expected value.", h.guessesLeft(), 9);
        h.record('b');
        h.record('c');
        Assert.assertEquals("Guesses left did not match the expected value.", h.guessesLeft(), 7);
    }

    /* Checks that guessed characters are added to the list of guessed characters */
    @Test
    public void guessesTest() {
        HangmanManager h = getDummyManager(10);
        h.record('a');
        h.record('b');
        h.record('c');
        h.record('d');
        h.record('e');
        Set<Character> set = h.guesses();
        if (set.size() != 5 || !set.contains('a') || !set.contains('b') || !set.contains('c')
                || !set.contains('d') || !set.contains('e')) {
            Assert.fail("Content of guesses is not the letters guessed.");
        }
    }

    /* Checks that the content of the current dictionary matches what is expected as letters are guessed */
    @Test
    public void recordTest() {
        HangmanManager h = new HangmanManager(getDictionary("dictionary2.txt"), 4, 10);
        h.record('e');
        Set<String> s = new HashSet<>();
        s.add("ally");
        s.add("cool");
        s.add("good");
        Assert.assertEquals("Content of remaining words did not match expected value.", h.words(), s);
        h.record('o');
        s.remove("ally");
        Assert.assertEquals("Content of remaining words did not match expected value.", h.words(), s);
    }

    /* Checks that exceptions are thrown when a letter is reguessed and when no guesses remain */
    @Test
    public void recordFailTest() {
        HangmanManager h = getDummyManager(2);
        h.record('a');          // 1 guess left
        try {                   // a already guessed, throw exception
            h.record('a');
            Assert.fail();
        } catch (IllegalArgumentException e) {}
        h.record('b');          // 0 guesses left
        try {
            h.record('c');
            Assert.fail();
        } catch (IllegalStateException e) {}
    }

    /* Checks that the pattern matches what is expected */
    @Test
    public void patternTest() {
        HangmanManager h = new HangmanManager(getDictionary("dictionary2.txt"), 4, 10);
        Assert.assertEquals(h.pattern(), "- - - -");
        int numOccur = h.record('e');
        Assert.assertEquals(h.pattern(), "- - - -");
        Assert.assertEquals(0, numOccur);
        Assert.assertEquals("With wrong guess, guessesLeft should decrease by 1", 9, h.guessesLeft());

        int numOccur2 = h.record('o');
        Assert.assertEquals(h.pattern(), "- o o -");
        Assert.assertEquals(2, numOccur2);
        Assert.assertEquals("With correct guess, guessesLeft shouldn't decrease" , 9, h.guessesLeft());
    }


}
