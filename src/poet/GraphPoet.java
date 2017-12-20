/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 * 
 * Immutable data type implementation
 */
public class GraphPoet {
    private final Graph<String> affinityGraph;
    private final List<String> corpusWords;
    // Abstraction function:
    //   represents a poetry generator that uses a corpus of text
    //   to turn an input string into poetry.
    //
    // Representation invariant:
    //   graph is a non-null Graph object with words as vertices
    //   and number of adjacencies(weight) as edges.
    //
    // Safety from rep exposure:
    //   - All fields are private and final
    //   - graph is a mutable type, no references to it are provided
    //     for the client to mutate
    //   - corpusWords is a mutable list, getCorpusWords() returns
    //     a read-only view preventing the client from 
    //     making any mutations
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        corpusWords = extractWordsFromFile(corpus);
        affinityGraph = generateAffinityGraph(corpusWords);
        checkRep();
    }
    private void checkRep() {
        assert affinityGraph != null;
    }
    /** Returns a list of words in lowercase, separation done at whitespace */
    private List<String> extractWordsFromFile(File corpus) throws IOException {
        List<String> words = new ArrayList<>();
        try (Scanner s = new Scanner(new BufferedReader(new FileReader(corpus)))) {
            while (s.hasNext()) {
                words.add(s.next().toLowerCase());
            }
        }
        assert words != Collections.EMPTY_LIST;
        return words;
    }
    /**
     * Generates a word affinity graph
     * 
     * @return a graph whose vertices are lowercase words
     *         and edges are the counts of adjacencies
     */
    private Graph<String> generateAffinityGraph(List<String> words){
        Graph<String> graph = Graph.empty();
        
        for (int i = 0; i < words.size(); i++) {
            String source = words.get(i);
            graph.add(source);
            if ((i + 1) >= words.size()) {
                break;
            }
            String target = words.get(i + 1);
            int prev = graph.set(source, target, 1);
            graph.set(source, target, prev + 1);
        }
        return graph;
    }
    /** 
     * Returns the words in the affinity graph generated from the corpus
     * 
     * Words are defined as non-empty case-insensitive strings of non-space 
     * non-newline characters. They are delimited in the corpus by spaces, newlines, 
     * or the ends of the file.
     * 
     * @return list of words in the order they appear in the corpus. All words are
     *         in lowercase
     */
    public List<String> getCorpusWords() {
        return Collections.unmodifiableList(corpusWords);
    }
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        String[] inputWords = input.split("\\s");
        StringBuilder poem = new StringBuilder(input);
        int fromIndex = 0;
        
        for (int i = 0; i < inputWords.length; i++) {
            if (i + 1 >= inputWords.length) {
                break;
            }
            Map<String, Integer> word1Targets = 
                    affinityGraph.targets(inputWords[i].toLowerCase());
            Map<String, Integer> word2Sources =
                    affinityGraph.sources(inputWords[i+1].toLowerCase());
            Set<String> probableBridges = word1Targets.keySet();
            
            List<String> allBridges = probableBridges.stream()
                    .filter(possibleBridge -> word2Sources.containsKey(possibleBridge))
                    .collect(Collectors.toList());
            
            if (!allBridges.isEmpty()) {
                Random rand = new Random();
                int  n = rand.nextInt(allBridges.size());
                String bridge = allBridges.get(n);
                // get the index of word 2 from the poem
                int insertAt = poem.indexOf(inputWords[i+1], fromIndex);
                // insert the bridge word before that word
                poem.insert(insertAt, bridge + " ");
            }
        }
        checkRep();
        return poem.toString();
    }
    
    @Override public String toString() {
        return affinityGraph.toString();
    }
}
