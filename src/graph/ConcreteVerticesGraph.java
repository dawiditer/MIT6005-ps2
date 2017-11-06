/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//TODO: improve overall performance
/**
 * An implementation of Graph.
 * 
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph implements Graph<String> {
   /**
    * Warning: For this ConcreteVerticesGraph to be case-insensitive, all vertex labels
    * are stored in lowercase and should be accessed as such. 
    * sources() and targets() return a Map<String, Integer>, 
    * which can lead to subtle bugs due to the equality used in storing the string labels. 
    * Two vertices, v1 and v2,  are considered the same if their labels l1 and l2
    * l1.equalsIgnoreCase(l2) == true. 
    * Therefore "Hi, "hi", and "HI" are considered as one vertex, 
    * requiring only one label stored, the lowercase form in this case. 
    * An access like vertices().contains("Hi") will lead to bugs, so use with caution. 
    * 
    * <p>The implementation involves a lot of checking and defensive copies
    * which is costly in terms of both performance and memory. This is 
    * because we are storing a mutable type to a list, so methods such
    * as contains() and get() can't be used to access the vertices. 
    * It is a requirement that we not add new fields and we stick to the
    * rep provided. 
    * I tried using a lookup table without violating the conditions, which
    * would store the vertices' labels in a list that matches the positions 
    * of their relative vertices to have constant time access, found that
    * challenging.
    * If allowed to alter the rep, I'd use a map:
    *       Map<String, Vertex>
    *  this would be performance friendly, constant time access and mutating
    *  any vertex would not affect the rep.
    *  
    */
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   represents a directed weighted graph as multiple vertices 
    //   connecting as source to target pairs with each pair having a
    //   weight.
    //   
    // Representation invariant:
    //   only one instance of a vertex can exist in vertices
    // Safety from rep exposure:
    //   vertices is a mutable list, so operation make defensive
    //   copies and use immutable views to avoid sharing the rep
    //   A Vertex is a mutable type, operations use defensive copies 
    //   to avoid sharing the rep
    
  
    public ConcreteVerticesGraph(){
    }
    private void checkRep(){        
        assert vertices().size() == vertices.size();
    }
    //helper method
    /**
     * Returns the index of a vertex in list of vertices
     * @param label the string label of the vertex being searched
     * @return the index, i, of the vertex having label such that
     *         vertices.get(i).getLabel() == label, or -1 if
     *         no vertex was found
     */
    private int indexInVertices(String label){
        for(int i = 0; i < vertices.size(); i++){
            if ( vertices.get(i).getLabel().equals(label) ) {
                return i;
            }
        }
        return -1;
    }
    //end of helper method
    @Override public boolean add(String vertex) {
        vertex = vertex.toLowerCase();
        
        if ( vertices().contains(vertex) ) {
            return false;
        }
        Vertex vertexObj = new Vertex(vertex);    
        final boolean vertexAdded = vertices.add(vertexObj);
        checkRep();
        return vertexAdded;
    }
    
    @Override public int set(String source, String target, int weight) {
        source = source.toLowerCase();
        target = target.toLowerCase();
        assert source != target;
        assert weight >= 0;
        
        final Vertex sourceVertex;
        final Vertex targetVertex;
        
        Set<String> verticeLabels = vertices();
        if ( verticeLabels.contains(source) ) {
            int sourceIndex = indexInVertices(source);
            sourceVertex = vertices.get(sourceIndex);
        } else {
            sourceVertex = new Vertex(source);
            vertices.add(sourceVertex);
        }
        
        if ( verticeLabels.contains(target) ) {
            int targetIndex = indexInVertices(target);
            targetVertex = vertices.get(targetIndex);
        } else {
            targetVertex = new Vertex(target);
            vertices.add(targetVertex);
        }
        
        int sourcePrevWeight = sourceVertex.setTarget(target, weight);
        int targetPrevWeight = targetVertex.setSource(source, weight);
        assert sourcePrevWeight == targetPrevWeight;
        
        checkRep();
        return sourcePrevWeight;
    }
    
    @Override public boolean remove(String vertex) {
        vertex = vertex.toLowerCase();
        
        if ( !( vertices().contains(vertex)) ) {
            return false;
        }
        int vertexIndex = indexInVertices(vertex);
        assert vertexIndex != -1;
        final Vertex removedVertex = vertices.remove(vertexIndex);
        assert removedVertex.getLabel() == vertex;
        
        for( Vertex v: vertices ) {
            v.remove(vertex);
        }
        return removedVertex != null;
    }
    @Override public Set<String> vertices() {
        return vertices.stream()
                .map(Vertex::getLabel)
                .collect(Collectors.toSet());
    }
    /** Returns an immutable view of source vertices to a target, all string labels in lowercase */
    @Override public Map<String, Integer> sources(String target) {
        target = target.toLowerCase();
        final int targetIndex = indexInVertices(target);
        if ( targetIndex < 0 ) {
            return Collections.emptyMap();
        }
        Vertex targetVertex = vertices.get(targetIndex);
        
        return Collections.unmodifiableMap(targetVertex.getSources());
    }
    /** Returns an immutable view of target vertices from a target, all string labels in lowercase */
    @Override public Map<String, Integer> targets(String source) {
        source = source.toLowerCase();
        final int sourceIndex = indexInVertices(source);
        if ( sourceIndex < 0 ) {
            return Collections.emptyMap();
        }
        Vertex sourceVertex = vertices.get(sourceIndex);
        
        return Collections.unmodifiableMap(sourceVertex.getTargets());
    }
    
    // TODO toString()
    
}

/**
 * Mutable datatype that represents a vertex in a graph.
 * 
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */

/**
 * Mutable datatype that represents a vertex in a mutable weighted directed graph
 * 
 */
class Vertex {
    private final String label;
    private final Map<String, Integer> sources = new HashMap<>();
    private final Map<String, Integer> targets = new HashMap<>();
    
    // Abstraction Function:
    //   represents a vertex in a graph that connects to other vertices as a
    //   source and/or a target. A weighted connection between 2 vertices
    //   represents an edge, and direction is represented by the connection
    //   from a source vertex to the target vertex
    //
    // Representation Invariant:
    //   A vertex label must be in a non-empty lowercase string
    //   A vertex cannot be its own source
    //   A vertex cannot be its own target
    //   All sources and targets must be distinct vertices
    //   A connection must have a weight > 0
    //
    // Safety from Exposure:
    //   All fields are  private and final
    //   label is a string so guaranteed immutable
    //   sources and targets are mutable, so operations use defensive copies
    //   and immutable views to prevent sharing the rep objects with clients
    
    public Vertex(final String label){
        assert label == label.toLowerCase();//fail fast approach
        this.label = label;        
    }
    private void checkRep(){
        final Set<String> sourceLabels = sources.keySet();
        final Set<String> targetLabels = targets.keySet();
        
        assert this.label == this.label.toLowerCase();
        assert !sourceLabels.contains(this.label);
        assert !targetLabels.contains(this.label);
    }
    //helper code
    private void checkInputLabel(final String inputLabel){
        assert inputLabel != null && inputLabel != "";
        assert inputLabel == inputLabel.toLowerCase();
        assert inputLabel != this.label;
    }
    
    
    // TODO: include a method that adds a vertex as both a source and 
    //  target, to make it easier for the client when adding a
    //  such a connection
    /** Returns the string label of this vertex */
    public String getLabel(){
        return this.label;
    }
    /**
     * Adds a source connection to this vertex.
     * 
     * @param source string label of the source vertex to this target vertex
     * @param weight positive integer weight of this connection,
     *               requires weight > 0
     * @return true if source is successfully added with weight to
     *         this vertex
     */
    public boolean addSource(final String source, final int weight){
        checkInputLabel(source);
        assert weight > 0;
        
        if ( sources.putIfAbsent(source, weight) == null ){
            checkRep();
            return true;
        }
        return false;
    }
    /**
     * Adds a target connection from this vertex
     * 
     * @param target string label of the target vertex from this vertex
     * @param weight positive integer weight of this connection,
     *               requires weight > 0
     * @return true if target is successfuly added with weight to
     *         this vertex
     */
    public boolean addTarget(final String target, final int weight){
        checkInputLabel(target);
        assert weight > 0;
        
        if ( targets.putIfAbsent(target, weight) == null ) {
            checkRep();
            return true;
        }
        return false;
    }

    /**
     * Removes a vertex from this vertex, if it was a source, target or both
     * 
     * @param vertex string label of the vertex being removed
     * @return the previous weight of the vertex connection to this vertex
     *         zero if no such connection exists
     */
    public int remove(final String vertex) {
        checkInputLabel(vertex);
        int sourcePrevWeight = removeSource(vertex);
        int targetPrevWeight = removeTarget(vertex);
        
        if ( sourcePrevWeight > 0 && targetPrevWeight > 0 ) {
            assert sourcePrevWeight == targetPrevWeight;
        }
        return sourcePrevWeight == 0 ? targetPrevWeight : sourcePrevWeight;
    }
    /**
     * Removes a source connection to this vertex
     * 
     * @param source string label of the source vertex being removed
     * @return the previous weight from source to this vertex,
     *         zero if no such source exists
     */
    public int removeSource(final String source){
        checkInputLabel(source);
        
        Integer previousWeight = sources.remove(source);
        
        checkRep();
        return previousWeight == null ? 0 : previousWeight;
    }
    /**
     * Removes a target connection from this vertex
     * @param target string label of the target vertex being removed
     * @return the previous weight to target from this vertex,
     *         zero if no such target exists
     */
    public int removeTarget(final String target){
        checkInputLabel(target);
        
        Integer previousWeight = targets.remove(target);
        
        checkRep();
        return previousWeight == null ? 0 : previousWeight;
    }
    /**
     * Adds, removes or updates a source connection to this vertex
     * 
     * if weight > 0, add a source if it didn't exist otherwise update
     * the weight of that source (vertex not modified if the current weight
     * is equal to the input weight)
     * if weight = 0, remove the source if it existed, otherwise this vertex
     * is not modified:
     *      add:    weight > 0, source doesn't exist
     *      remove: weight = 0, source exists
     *      update: weight > 0, source exists
     *      no change: source exists, weight = previousWeight  
     *  
     * @param source string label of the source vertex to set
     * @param weight non-negative integer to set source connection to
     * @return the previous weight from source to this vertex,
     *         zero if no such source exists
     */
    public int setSource(final String source, final int weight){
        checkInputLabel(source);
        assert weight >= 0;
        final int previousWeight;
        
        if ( weight == 0 ) {
            previousWeight = removeSource(source); 
        } else if ( addSource(source, weight) || sources.get(source) == (Integer)weight) {
            previousWeight = 0;
        } else {
            previousWeight = sources.replace(source, weight);
        }
        checkRep();
        return previousWeight;
    }
    /**
     * Adds, removes or updates a target connection from this vertex
     * 
     * if weight > 0, add a target if it didn't exist, otherwise update
     * the weight of the target (vertex not modified if the current weight
     * is equal to the input weight)
     * if weight = 0, remove the target if it existed, otherwise this vertex
     * is not modified:
     *      add:    weight > 0, target doesn't exist
     *      remove: weight = 0, target exists
     *      update: weight > 0, target exists
     * 
     * @param target string label of the target vertex to set
     * @param weight non-negative integer to set target connection to
     * @return the previous weight from source to this vertex,
     *         zero if no such target exists
     */
    public int setTarget(final String target, final int weight){
        checkInputLabel(target);
        assert weight >= 0;
        final int previousWeight;
        
        if ( weight == 0 ) {
            previousWeight = removeTarget(target);
        } else if ( addTarget(target, weight) || targets.get(target) == (Integer)weight ) {
            previousWeight = 0;
        } else {
            previousWeight = targets.replace(target, weight);
        }
        checkRep();
        return previousWeight;
    }

    /** Returns an immutable view of this vertex's sources as a label, vertex pair*/
    public Map<String, Integer> getSources(){
        return Collections.unmodifiableMap(sources);
    }
    /** Returns an immutable view of this vertex's targets as a label, vertex pair*/
    public Map<String, Integer> getTargets(){
        return Collections.unmodifiableMap(targets);
    }
    /**
     * Checks if a vertex is a target from this vertex
     * 
     * A vertex is a target from this vertex if this vertex is a 
     * source to that vertex, similar to this implementation:
     *      getTargets().contains(vertex)
     *      
     * @param vertex string label of the vertex being checked
     * @return true if vertex is a target from this vertex
     */
    public boolean isTarget(final String vertex){
        return targets.containsKey(vertex);
    }
    /**
     * Checks if a vertex is a source to this vertex
     * 
     * A vertex is a source to this vertex if this vertex is a 
     * target from that vertex, similar to this implementation:
     *      getSources().contains(vertex)
     *      
     * @param vertex string label of the vertex being checked
     * @return true if vertex is a source to this vertex
     */
    public boolean isSource(final String vertex){
        return sources.containsKey(vertex);
    }
    /**
     * Returns a string rep of this vertex
     * 
     * A vertex is defined by the its label, its source vertices and
     * its targets vertices.The string returned is therefore equal to:
     *   "label: getLabel(), sources: getSources().size(), targets: getTargets().size()"
     *   
     * @return string rep of this vertex, containing its label, the number
     *        of sources and targets it has, with source: 0 if no source vertices
     *        exist and targets: 0 if no target vertices exist
     */
    @Override public String toString(){
        return String.format("label: %s, sources: %d, targets: %d", 
                getLabel(), getSources().size(), getTargets().size());
    }
}
