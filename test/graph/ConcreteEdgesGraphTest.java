/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<>();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   concreteEdgesGraph: empty, contains multiple vertices and edges
    @Test
    //covers empty graph
    public void testToStringEmptyGraph(){
        Graph<String> graph = emptyInstance();
        String emptyGraph = "Empty Graph";
        
        assertTrue("Expected 'Empty Graph'", graph.toString().equals(emptyGraph));
    }
    @Test
    //covers graph containes vertices and edges
    public void testToStringMultiple(){
        Graph<String> graph = emptyInstance();

        final String source1 = "source1";
        //final String source2 = "source2";
        final String target1 = "target1";
       // final String target2 = "target2";
        final int weight = 1;
        
        graph.set(source1, target1, weight);
       // graph.set(source2, target2, weight);
       // graph.set(source1, target2, weight);
       // graph.set(source2, target1, weight);
        
        String regex = "source1 -> target1: 1";
        assertTrue("Expected correct syntax", 
                graph.toString().matches(regex));
    }
    
    // TODO tests for ConcreteEdgesGraph.toString()
    // TODO: include tests for postcondition, ie, structure of the string
   
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //  Partition for edge.getSource() -> source
    //     has only one input, edge
    //     has only one output, source 
    //
    //  Partition for edge.getTarget() -> target
    //     has only one input, edge
    //     has only one output, target
    //
    //  Partition for edge.getWeight() -> weight
    //     has only one input, edge
    //     has only one output, weight
    //
    //  Partition for edge.setWeight(newWeight) -> newEdge
    //     newWeight != edge.getWeight
    //     newWeight == edge.getWeight
    //     include reference equality checks
    //     and immutability tests to make sure
    //     no rep exposure via mutation
    //
    //  Partition for edge.toString() -> stringRep
    //      has only one input, edge
    //      the output string should contain
    //      the source, the target and the weight of this edge
    //
    //  Partition for thisEdge.equals(thatEdge)
    //      thisEdge equal to thatEdge (observational equality)
    //      thisEdge not equal to thatEdge
    //   include tests for reflexive, symmetric and transitive properties
    //  
    //  Partition for edge.hashCode()
    //      use two edges for comparison; thisEdge, thatEdge
    //      thisEdge equals() thatEdge
    //      thisEdge not equals() thatEdge
    //
    @Test
    public void testGetSource(){
        final String source = "vertex1";
        Edge<String> edge = new Edge<>(source, "vertex2", 3);
        
        assertEquals("Expected source vertex", source, edge.getSource()); 
    }
    @Test
    public void testGetTarget(){
        final String target = "vertex2";
        Edge<String> edge = new Edge<>("vertex1", target, 3);
        
        assertEquals("Expected target vertex", target, edge.getTarget());
    }
    @Test
    public void testGetWeight(){
        final int weight = 3;
        Edge<String> edge = new Edge<>("vertex1", "vertex2", weight);
        
        assertEquals("Expected correct weight", weight, edge.getWeight());
    }
    @Test
    //covers newWeight != edge.getWeight
    public void testSetWeightNotEqual(){
        final int weight = 3;
        Edge<String> edge = new Edge<>("vertex1", "vertex2", weight);
        final int newWeight = 1;
        Edge<String> edgeNewWeight = edge.setWeight(newWeight);
        
        assertFalse("Expected new edge after setting weight", edge == edgeNewWeight);
        assertEquals("Expected edge to be immutable", weight, edge.getWeight());
        assertEquals("Expected new edge to have new weight", 
                newWeight, edgeNewWeight.getWeight());        
    }
    @Test
    //covers newWeight = edge.getWeight
    public void testSetWeightEqual(){
        final int weight = 3;
        Edge<String> edge = new Edge<>("vertex1", "vertex2", weight);
        Edge<String> edgeNewWeight = edge.setWeight(weight);
        
        assertFalse("Expected new edge after setting weight", edge == edgeNewWeight);
        assertEquals("Expected new edge to have new weight", 
                weight, edgeNewWeight.getWeight());
    }
    @Test
    public void testEdgeToString(){
        final String source = "vertex1";
        final String target = "vertex2";
        final int weight = 3;
        Edge<String> edge = new Edge<>(source, target, weight);
        
        final String edgeRep = edge.toString();
        
        assertNotEquals("Expected non-empty string", "", edgeRep);
        assertTrue("Expected correct source, target and weight",
                contains(edgeRep, source, target, weight));
        
    }
    //helper method    
    // TODO: include tests for postcondition, ie, structure of the string
    /**
     * Checks whether the string representation of an edge contains the correct values
     * 
     * @param edgeRep string returned by edge.toString()
     * @param source the source vertex of this edge
     * @param target the target vertex of this edge
     * @param weight the weight of this edge
     * @return true if edgeRep contains source, target and weight 
     */
    private static boolean contains(String edgeRep, String source,
            String target, int weight){
        //see: concreteEdgesGraphContains()
        final String regex = String.format("(%s(.|\\n)*%s(.|\\n)*%d)",
                source, target, weight);
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(edgeRep);
        return matcher.find();
    }
    
    //Tests for edge.equals()
    @Test
    //covers thisEdge equal to thatEdge
    public void testEqualsEdgesEqual(){
        Edge<String> edge1 = new Edge<>("vertex1", "vertex2", 1);
        Edge<String> edge2 = new Edge<>("vertex1", "vertex2", 1);
        Edge<String> edge3 = new Edge<>("vertex1", "vertex2", 1);
        
        //reflexive,
        assertTrue("Expected egde1 equal to itself", edge1.equals(edge1));
        assertTrue("Expected egde2 equal to itself", edge2.equals(edge2));
        assertTrue("Expected egde3 equal to itself", edge3.equals(edge3));
        //symmetric
        assertTrue("Expected edge1 equal to edge2", edge1.equals(edge2));
        assertTrue("Expected edge2 equal to edge1", edge2.equals(edge1));
        //transitive
        assertEquals("Expected edge1 transitively equal to edge3",
                edge1.equals(edge2) && edge3.equals(edge2), edge1.equals(edge3));
    }
    @Test
    //covers thisEdge not equal to thatEdge
    public void testEqualsEdgesNotEqual(){
        Edge<String> edge1 = new Edge<>("vertex", "vertex1", 1);
        Edge<String> edge2 = new Edge<>("vertex1", "vertex", 1);
        
        assertFalse("Expected edges not equal", edge1.equals(edge2));
        assertFalse("Expected edges not equal", edge2.equals(edge1));
    }
    //Tests for edge.hashCode()
    @Test
    //covers thisEdge equals() thatEdge
    //       thisEdge source and target == thatEdge source and target
    public void testHashCodeEqualEdges(){
        Edge<String> edge1 = new Edge<>("vertex1", "vertex2", 1);
        Edge<String> edge2 = new Edge<>("vertex1", "vertex2", 1);
        
        int hashCodeEdge1 = edge1.hashCode();
        int hashCodeEdge2 = edge2.hashCode();
        
        assertEquals("Expected equal edges", edge1.equals(edge2), edge2.equals(edge1));
        assertEquals("Expected equal hashcode for equal edges", hashCodeEdge1, hashCodeEdge2);
    }
    
    @Test
    //covers thisEdge not equals() thatEdge
    public void testHashCodeEdgesNotEqual(){
        Edge<String> edge1 = new Edge<>("vertex1", "vertex2", 1);
        Edge<String> edge2 = new Edge<>("vertex2", "vertex1", 1);
        
        int hashCodeEdge1 = edge1.hashCode();
        int hashCodeEdge2 = edge2.hashCode();
        
        assertFalse("Expected unequal objects", edge1.equals(edge2));
        assertNotEquals("Expected different hashcodes for unequal edges", 
                hashCodeEdge1, hashCodeEdge2);
    }
}
