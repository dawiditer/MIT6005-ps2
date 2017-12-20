/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for static methods of Graph.
 * 
 * To facilitate testing multiple implementations of Graph, instance methods are
 * tested in GraphInstanceTest.
 */
public class GraphStaticTest {
    
    // Testing strategy
    //   empty()
    //     no inputs, only output is empty graph
    //     observe with vertices()
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testEmptyVerticesEmpty() {
        assertEquals("expected empty() graph to have no vertices",
                Collections.emptySet(), Graph.empty().vertices());
    }
    
    // TODO test other vertex label types in Problem 3.2
    //using integers
    @Test
    public void testIntegerAsLabel(){
        Graph<Integer> graph = Graph.empty();
        final int vertex1 = 1;
        final int vertex2 = 2;
        final int vertex3 = 3;
        
        final boolean addedVertex1 = graph.add(vertex1);
        final boolean addedVertex2 = graph.add(vertex2);
        
        final int weight1 = 2;
        final int weight2 = 1;
        
        final int prevWeight1 = graph.set(vertex1, vertex2, weight1);
        final int prevWeight2 = graph.set(vertex3, vertex1, weight2);   
        final int initialNumVertices = graph.vertices().size();
        final boolean removedVertex2 = graph.remove(vertex2);
        
        assertTrue("Expected vertex1 added", addedVertex1);
        assertTrue("Expected vertex2 added", addedVertex2);
        assertEquals("Expected no previous weight", 0, prevWeight1);
        assertEquals("Expected no previous weight", 0, prevWeight2);
        assertEquals("Expected correct num vertices", 3, initialNumVertices);
        assertTrue("Expected vertex2 removed", removedVertex2);
        assertEquals("Expected one vertex removed", initialNumVertices - 1, graph.vertices().size());
        System.out.println(graph);
  
    }
}
