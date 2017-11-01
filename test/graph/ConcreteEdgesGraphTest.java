/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
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
        return new ConcreteEdgesGraph();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   TODO
    //   concreteEdgesGraph: empty, contains multiple vertices and edges
    //   the output string should contain the 
    //   correct number of edges and vertices
    
    
    // TODO tests for ConcreteEdgesGraph.toString()
    // TODO: include tests for postcondition, ie, structure of the string
    @Test
    //covers empty graph
    public void testToStringEmptyGraph(){
        Graph<String> concreteEdgesGraph = emptyInstance();
        final String rep = concreteEdgesGraph.toString();
        List<Integer> intsInRep = extractIntegers(rep);
        final int repNumEdges = intsInRep.get(0);
        final int repNumVertices = intsInRep.get(1);
        

        assertNotEquals("Expected non-empty string rep", "", rep);
        assertEquals("Expected empty graph to have no edges", 0, repNumEdges);
        assertEquals("Expected empty graph to have no vertices", 0, repNumVertices);
    }    
    
    @Test
    //covers graph contains multiple vertices and edges
    public void testToStringNonEmptyGraph(){
        Graph<String> concreteEdgesGraph = emptyInstance();
        
        //create 3 vertices with 2 edges
        concreteEdgesGraph.set("Vertex1", "Vertex2", 1);
        concreteEdgesGraph.set("Vertex2", "Vertex3", 1);
        final int actualNumEdges = 2;
        final int actualNumVertices = 3;
        
        final String rep = concreteEdgesGraph.toString();
        List<Integer> intsInRep = extractIntegers(rep);
        final int repNumEdges = intsInRep.get(0);
        final int repNumVertices = intsInRep.get(1);
        
        // TODO: check if assertNotEquals is a good implementation
        assertNotEquals("Expected non-empty string rep", "", rep);
        assertEquals("Expected correct number of edges", actualNumEdges, repNumEdges);
        assertEquals("Expected correct number of vertices", actualNumVertices, repNumVertices);
       
    }
    
    //helper code
    /**
     * Extracts integers in a string, maintaining their order of occurrence
     * 
     * @param rep string containing integers
     * @return a list of integers, lst, such that 
     *         lst.get(0) = numEdges, lst.get(1) = numVertices,
     *         and lst.size() = 2
     */
    private static List<Integer> extractIntegers(String rep){
        Pattern p = Pattern.compile("(\\b\\d\\b)");
        Matcher m = p.matcher(rep);
        List<Integer> intsInRep = new ArrayList<>();
        
        int count = 0;
        while(m.find()){
            if(++count > 2){
                throw new AssertionError("Expected only 2 integres");
            }
            intsInRep.add(Integer.valueOf(m.group())); 
        }
        return intsInRep;
    }
    
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //  TODO
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
    // TODO tests for operations of Edge
    @Test
    public void testGetSource(){
        final String source = "Vertex1";
        Edge edge = new Edge(source, "Vertex2", 3);
        
        assertEquals("Expected source vertex", source, edge.getSource()); 
    }
    @Test
    public void testGetTarget(){
        final String target = "Vertex2";
        Edge edge = new Edge("Vertex1", target, 3);
        
        assertEquals("Expected target vertex", target, edge.getTarget());
    }
    @Test
    public void testGetWeight(){
        final int weight = 3;
        Edge edge = new Edge("Vertex1", "Vertex2", weight);
        
        assertEquals("Expected correct weight", weight, edge.getWeight());
    }
    @Test
    //covers newWeight != edge.getWeight
    public void testSetWeightNotEqual(){
        final int weight = 3;
        Edge edge = new Edge("Vertex1", "Vertex2", weight);
        final int newWeight = 1;
        Edge edgeNewWeight = edge.setWeight(newWeight);
        
        assertFalse("Expected new edge after setting weight", edge == edgeNewWeight);
        assertEquals("Expected edge to be immutable", weight, edge.getWeight());
        assertEquals("Expected new edge to have new weight", 
                newWeight, edgeNewWeight.getWeight());        
    }
    @Test
    //covers newWeight = edge.getWeight
    public void testSetWeightEqual(){
        final int weight = 3;
        Edge edge = new Edge("Vertex1", "Vertex2", weight);
        Edge edgeNewWeight = edge.setWeight(weight);
        
        assertFalse("Expected new edge after setting weight", edge == edgeNewWeight);
        assertEquals("Expected new edge to have new weight", 
                weight, edgeNewWeight.getWeight());
    }
    @Test
    public void testEdgeToString(){
        final String source = "Vertex1";
        final String target = "Vertex2";
        final int weight = 3;
        Edge edge = new Edge(source, target, weight);
        
        final String edgeRep = edge.toString();
        
        assertNotEquals("Expected non-empty string", "", edgeRep);
        assertTrue("Expected correct source, target and weight",
                contains(edgeRep, source, target, weight));
        
    }
    //helper method    
    // TODO: separate checks for source, target and weight
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
}
