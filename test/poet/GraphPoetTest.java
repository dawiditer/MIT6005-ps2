/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    // Partitions for GraphPoet(corpus)
    //   corpus contains: one word, one line, multiple lines
    //   include words whose adjacency count > 1
    // 
    // Partitions for poem(input) -> poeticOutput
    //   input: one word, multiple words
    //        : word pairs with adjacency count > 1
    //
    // Exhaustive Cartesian coverage of partitions
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    private static final GraphPoet instantiateGraph(String source) {
        try {
            final File corpus = new File(source);
            GraphPoet graphPoet = new GraphPoet(corpus);
            return graphPoet;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    final GraphPoet graphOneWord = instantiateGraph("test/poet/TestOneWord.txt");
    final GraphPoet graphOneLine = instantiateGraph("test/poet/TestOneLine.txt");
    final GraphPoet graphMultipleLines = instantiateGraph("test/poet/TestMultipleLines.txt");
    
    // Tests for GraphPoet()
    @Test
    // covers corpus contains one word
    public void testGraphPoet_OneWord() {        
        List<String> corpusWords = graphOneWord.getCorpusWords();
        
        assertEquals("Expected corpus to contain one word",
                1, corpusWords.size());
        assertTrue("Expected word in lowercase",
                corpusWords.contains("here!"));
    }
    @Test
    // covers corpus contains one line
    //        word adjacency count > 1
    public void testGraphPoet_OneLine() {
        List<String> corpusWords = graphOneLine.getCorpusWords();
        
        assertEquals("Expected all words in corpus",
                13, corpusWords.size());
        assertTrue("Expected words in lowercase",
                corpusWords.contains("to"));
    }
    @Test
    // covers corpus contains multiple line
    //        word adjacency count > 1
    public void testGraphPoet_MultipleLine() {
        List<String> corpusWords = graphMultipleLines.getCorpusWords();
        
        assertNotEquals("Expected non-empty list", Collections.EMPTY_LIST, corpusWords);
        assertTrue("Expected words in lowercase",
                corpusWords.contains("don't"));
        assertTrue("Expected words in lowercase",
                corpusWords.contains("angelou"));
    }
    
    // Tests for poem()
    @Test
    // covers one word
    public void testPoem_OneWord() {
        String input = "Like";
        String output = graphMultipleLines.poem(input);
        
        assertEquals("Expected unchanged input", input, output);
    }
    @Test
    // covers multiple words
    public void testPoem_MultipleWords() {
        String input = "Seek to explore new and exciting synergies!";
        String output = graphOneLine.poem(input);
        String expected = "Seek to explore strange new life and exciting synergies!";
        
        assertEquals("Expected poetic output with words in input unchanged",
                expected, output);
    }

    @Test
    // covers word pairs with adjacency count > 1   
    public void testPoem_MultipleAdjacencies() {
        String input = "you MAY me";
        String output = graphMultipleLines.poem(input);
        
        assertNotEquals("Expected a bridge word inserted",
                input, output);
        assertTrue("Expected input words unchanged",
                output.contains("you") && output.contains("MAY"));
        assertTrue("Expected correct bridge word", 
                output.contains("write")
                || output.contains("trod")
                || output.contains("kill")
                || output.contains("cut")
                || output.contains("shoot"));
    }
}