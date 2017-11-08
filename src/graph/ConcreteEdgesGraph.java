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
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   represents all edges in a graph as a pair of connected vertices
    //   with a source to target direction that carries weight 
    // Representation invariant:
    //   vertices is a set of objects of type L
    //   edges is a list of distinct weighted Edges made by 
    //      distinct pairs of vertices(no pair of vertices exists more than once).   
    //   An edge must be connected to at least v number of vertices, for example,
    //     2 edges require at least 3 vertices, 5 edges require at least 4 vertices
    //     vertices.size() >= Math.ceil(Math.sqrt(2*edges.size) + 0.5)
    //     (source) https://math.stackexchange.com/a/1954272
    //
    // Safety from rep exposure:
    //   All fields are private and final
    //   vertices and edges are mutable types, so operations use defensive copies and
    //   immutable wrappers to avoid sharing the rep's objects to clients
    
    public ConcreteEdgesGraph(){
    }
    private void checkRep(){
        final int sizeOfEdges = edges.size();
        final int sizeOfVertices = vertices.size();
        int minNumberOfVertices = 
                sizeOfEdges == 0 ? 0: (int)Math.ceil(Math.sqrt(2 * sizeOfEdges) + 0.5);
        
        assert sizeOfVertices >= minNumberOfVertices;  
    }
    /** Returns true if vertex label is added*/
    @Override public boolean add(L vertex) {
        return vertices.add(vertex);
    }    
    @Override public int set(L source, L target, int weight) {
        assert weight >= 0;
        assert !(source.equals(target));
        
        
        int indexOfEdge = indexOfEdgeInEdges(source, target);
        int previousWeight = 0;
        final Edge<L> previousEdge;
        
        if ( weight > 0 ) {
            Edge<L> newEdge = new Edge<>(source, target, weight);
            if ( indexOfEdge < 0 ) {
                add(source);
                add(target);
                edges.add(newEdge);
            } else {
                previousEdge = edges.set(indexOfEdge, newEdge);
                previousWeight = previousEdge.getWeight();
            }
        } else if ( weight == 0 && indexOfEdge >= 0) {
            previousEdge = edges.remove(indexOfEdge);
            previousWeight = previousEdge.getWeight();
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
    private int indexOfEdgeInEdges(L source, L target){        
        for(int i = 0;  i < edges.size(); i++){
            Edge<L> edge = edges.get(i);
            if (edge.getSource().equals(source) &&
                    edge.getTarget().equals(target)){
                return i;
            }
        }
        return -1;
    }
    
    
    @Override public boolean remove(L vertex) {
        final int initialSizeEdges = edges.size();
        final int initialSizeVertices = vertices.size();
        
        Predicate<Edge<L>> vertexInEdge = (Edge<L> edge) -> 
              ( ( edge.getSource().equals(vertex) ) ||
                ( edge.getTarget().equals(vertex) ) ) ;
        Predicate<L> vertexInVertices = v -> v.equals(vertex);
        
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
    /** Returns an read-only view of this ConcreteEdgesGraph's vertices */
    @Override public Set<L> vertices() {
        return Collections.unmodifiableSet(vertices);
    }

    /** Returns a map of a target's sources */
    @Override public Map<L, Integer> sources(L target) {
        return edges.stream()
                .filter(edge -> edge.getTarget().equals(target))
                .collect(Collectors.toMap(Edge::getSource, Edge::getWeight));
    }
    /** Returns a map of a source's targets */
    @Override public Map<L, Integer> targets(L source) {
        return edges.stream()
                .filter(edge -> edge.getSource().equals(source))
                .collect(Collectors.toMap(Edge::getTarget, Edge::getWeight));
    }

    /**
     * Returns the string representation for a concreteEdgesGraph
     * 
     * ConcreteEdgesGraph represents all edges in a graph.
     * An edge is made up of a pair of vertices with a weight.
     * The string returned looks like: 
     *      edge1.getSource() -> edge1.getTarget(): edge1.getWeight
     *      edge2.getSource() -> edge2.getTarget(): edge2.getWeight
     * for all edges in this graph
     *  
     * @return string rep of this graph containing all the edges
     *         making up the graph, "Empty Graph" if graph has no edges
     */
    @Override public String toString(){
        if ( edges.isEmpty() ) {
            return "Empty Graph";
        }
        return edges.stream()
                .map(edge -> edge.toString())
                .collect(Collectors.joining("\n"));
    }
}

/**
 * Immutable type that represents an edge in a graph.
 * 
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L>{
    private final L source;
    private final L target;
    private final int weight;
    // Abstraction function:
    //   represents an edge connecting from source to target with weight
    // Representation invariant:
    //   source is a non-null L
    //   target is a non-null L
    //   source != target
    //   weight > 0
    // Safety from rep exposure:
    //   All fields are private and final
    //   source and target are of type L, required to be immutable
    //   int is a primitive type so guaranteed immutable
    //   setWeight() creates a new Edge object
    
    public Edge(final L source, final L target, final int weight){
        assert weight > 0;
        
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }
    private void checkRep(){
        assert source != null;
        assert target != null;
        assert weight > 0;
        assert !(source.equals(target));
    }
    //observers
    /** Returns this Edge's source*/   
    public L getSource(){
        return source;
    }
    /**Returns this Edge's target*/
    public L getTarget(){
        return target;
    }
    /**Returns this Edge's weight*/
    public int getWeight(){
        return weight;
    }
    
    //producers
    /**
     * Changes the weight of this Edge
     * 
     * @param newWeight an int, requires newWeight > 0
     * @return a new Edge with newWeight 
     */
    public Edge<L> setWeight(int newWeight){
        checkRep();
        return new Edge<>(source, target, newWeight);
    }
    /** Returns th string representation of a weighted edge
     * 
     * An edge is made up of two vertices, so the rep
     * should contain the source vertex and the target vertex
     * that make the edge, including its weight
     * 
     * @return String containing source, target and weight of this edge
     *         with the following structure:
     *              getSource() -> getTarget(): getWeight()
     */    
    @Override public String toString(){
        return getSource().toString() + 
                " -> " + 
                getTarget().toString() + 
                ": " + 
                getWeight();
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
        Edge<?> thatEdge = (Edge<?>)that;
        return this.getSource().equals(thatEdge.getSource()) &&
               this.getTarget().equals(thatEdge.getTarget()) &&
               this.getWeight() == thatEdge.getWeight();
    }
    @Override public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + getSource().hashCode();
        result = prime * result + getTarget().hashCode();
        result = prime * result + (int) getWeight();
        return result;
    }
}
