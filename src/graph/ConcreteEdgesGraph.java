/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
//TODO: strengthen the postcondition for all methods
//involving vertices, ie returns labels in lowercase
public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    
    // Abstraction function:
    //   represents all edges in a graph as edges with vertices they connect with 
    // Representation invariant:
    //   vertices is a set of non-empty Strings
    //   edges is a list of weighted Edges made by a pair of vertices
    //   
    //   An edge must be connected to at least v number of vertices, eg
    //     2 edges require at least 3 vertices, 5 edges require at least 4 vertices
    //     minimum number of vertices required for n number of edges:
    //     https://math.stackexchange.com/questions/1954096/whats-the-minimum-number-of-vertices-in-a-simple-graph-with-e-edges
    //  vertices.size >= Math.ceil(Math.sqrt(2*edges.size) + 0.5)
    //  
    //
    // Safety from rep exposure:
    //   All fields are private and final
    //   vertices and edges are mutable types, so operations use 
    //   immutable wrappers to avoid sharing the rep's objects to clients
    
    // TODO constructor
    public ConcreteEdgesGraph(){
    }
    // TODO checkRep
    private void checkRep(){
        final int sizeOfEdges = edges.size();
        final int sizeOfVertices = vertices.size();
        int minNumberOfVertices = 
                sizeOfEdges == 0 ? 0: (int)Math.ceil(Math.sqrt(2 * sizeOfEdges) + 0.5);
        
        assert sizeOfVertices >= minNumberOfVertices;  
    }
    @Override public boolean add(String vertex) {
        return vertexInSet(vertex, vertices()) ? false : vertices.add(vertex);
    }
    //helper method
    //checks if a vertex is in a given set of vertices
    //case-insensitive
    //TODO: improve checking performance
    private boolean vertexInSet(String vertex, Set<String> set){
        for ( String label: set ) {
            if ( label.equalsIgnoreCase(vertex) ) {
                return true;
            }
        }
        return false;
    }
    
    @Override public int set(String source, String target, int weight) {
        assert weight >= 0;
        assert !(source.equalsIgnoreCase(target));
        
        int previousWeight = 0;
        int indexOfEdge = indexOfEdgeInEdges(source, target);//-1 if edge doesn't exist
        
        //creates, updates, removes an edge
        if ( weight > 0 ) {
            Edge newEdge = new Edge(source, target, weight);
            if ( indexOfEdge < 0 ) {
                add(source);//add vertices if not in graph
                add(target);
                edges.add(newEdge);
            } else {
                previousWeight = edges.get(indexOfEdge).getWeight();
                edges.set(indexOfEdge, newEdge);
            }
        } else if ( weight == 0 && indexOfEdge >= 0) {
            previousWeight = edges.get(indexOfEdge).getWeight();
            edges.remove(indexOfEdge);
        }
        checkRep();
        return previousWeight;
    }
    //helper code
    /**
     * Checks if an edge exists in this graph
     * 
     * An edge exists in the graph if the source and target
     * specified match with an edge's source and target in this graph
     * The check is case-insensitive, for example
     * "vertex" will match an edge with labels "Vertex" or "vertex".
     *   
     * @param source string to compare an edge's source with
     * @param target string to compare an edge's target with
     * @return the index i for all 0 <= i, < edges.size()
     *             of the edge whose source and target
     *             match source and target, 
     *             -1 if no edge match was found
     */
    private int indexOfEdgeInEdges(String source, String target){
        for(int i = 0;  i < edges.size(); i++){
            Edge edge = edges.get(i);
            if (edge.getSource().equalsIgnoreCase(source) &&
                    edge.getTarget().equalsIgnoreCase(target)){
                return i;
            }
        }
        return -1;
    }
    
    
    @Override public boolean remove(String vertex) {
        final int initialSizeEdges = edges.size();
        final int initialSizeVertices = vertices.size();
        
        Predicate<Edge> vertexInEdge = (Edge edge) -> 
              ( ( edge.getSource().equalsIgnoreCase(vertex) ) ||
                ( edge.getTarget().equalsIgnoreCase(vertex) ) ) ;
        Predicate<String> vertexInVertices = vertice -> vertice.equalsIgnoreCase(vertex);
        
        boolean removedEdge = edges.removeIf(vertexInEdge);
        boolean removedVertice = vertices.removeIf(vertexInVertices);
        
        //NB a vertex can exist without being in an edge
        //if removedEdge, then removedVertice
        if(removedVertice){
            assert initialSizeVertices != vertices.size();
            assert initialSizeVertices - 1 == vertices.size();
        }
        if(removedEdge){
            assert initialSizeEdges != edges.size();
            assert removedVertice;
        }
        checkRep();
        return initialSizeVertices - 1 == vertices.size();
    }
    
    @Override public Set<String> vertices() {
        return Collections.unmodifiableSet(vertices);
    }
    
    @Override public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = edges.stream()
                .filter(edge -> edge.getTarget().equalsIgnoreCase(target))
                .collect(Collectors.toMap(Edge::getSource, Edge::getWeight));
        
        checkRep();
        return Collections.unmodifiableMap(sources);
    }
    
    @Override public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = edges.stream()
                .filter(edge -> edge.getSource().equalsIgnoreCase(source))
                .collect(Collectors.toMap(Edge::getTarget, Edge::getWeight));
        
        checkRep();
        return Collections.unmodifiableMap(targets);
    }
    
    // TODO toString()
    /**
     * Returns the string representation for a concreteEdgesGraph
     * 
     * ConcreteEdgesGraph represents all edges in a graph.
     * An edge is made up of a pair of vertices, so the
     * string representation should contain the total number
     * of vertices that connect to make the total number of
     * edges
     * 
     * @return string containing number of edges and vertices,
     *         with the following structure:
     *              "number of edges: e, number of vertices: v", 
     *         where e,v = 0 for empty objects and
     *         e,v > 0 for non-empty objects            
     */
    @Override public String toString(){
        return "number of edges: " + edges.size() +
               ", number of vertices: " + vertices.size();
    }
}

/**
 * TODO specification
 * Immutable type that represents an edge in a graph.
 * 
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge {
    
    // TODO fields
    private final String source;
    private final String target;
    private final int weight;
    // Abstraction function:
    //   represents an edge connecting from source to target with weight
    // Representation invariant:
    //   source is a non-null non-empty String
    //   target is a non-null non-empty String
    //   source != target
    //   weight > 0
    // Safety from rep exposure:
    //   All fields are private and final
    //   source and target are Strings so guaranteed immutability
    //   int is a primitive type so guaranteed immutability
    //   setWeight() creates a new Edge object
    
    // TODO constructor
    public Edge(final String source, final String target, final int weight){
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }
    // TODO checkRep
    private void checkRep(){
        assert source != null && source != "";
        assert target != null && target != "";
        assert weight > 0;
        assert !(source.equalsIgnoreCase(target));
    }
    //observers
    /** Returns this Edge's source*/   
    public String getSource(){
        checkRep();
        return source;
    }
    /**Returns this Edge's target*/
    public String getTarget(){
        checkRep();
        return target;
    }
    /**Returns this Edge's weight*/
    public int getWeight(){
        checkRep();
        return weight;
    }
    // TODO: compare operation
    
    //producers
    /**
     * Changes the weight of this Edge
     * 
     * @param newWeight an int, requires newWeight > 0
     * @return a new Edge with newWeight 
     */
    public Edge setWeight(int newWeight){
        checkRep();
        return new Edge(source, target, newWeight);
    }
    
    // TODO toString()
    /** Returns th string representation of a weighted edge
     * 
     * An edge is made up of two vertices, so the rep
     * should contain the source vertex and the target vertex
     * that make the edge, including its weight
     * 
     * @return String containing source, target and weight of this edge
     *         with the following structure:
     *              "source: s, target: t, weight: w"
     */    
    @Override public String toString(){
         return "source: " + getSource() + ", " +
                "target: " + getTarget() + ", " +
                "weight: " + getWeight();
    }
    /** Checks if two Edge objects are equal
     * @param that object to compare
     * @return true if this.source = that.source and
     *                 this.target = that.target and
     *                 this.weight = that.weight
     *         comparison is case-insensitive
     */
    @Override public boolean equals(Object that){
        if (! (that instanceof Edge)) {
            return false;
        }
        Edge thatEdge = (Edge)that;
        return this.getSource().equalsIgnoreCase(thatEdge.getSource()) &&
               this.getTarget().equalsIgnoreCase(thatEdge.getTarget()) &&
               this.getWeight() == thatEdge.getWeight();
    }
    @Override public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + getSource().toLowerCase().hashCode();//for case-insensitivity
        result = prime * result + getTarget().toLowerCase().hashCode();
        result = prime * result + (int) getWeight();
        return result;
    }
}
