/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<>();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteVerticesGraph.toString()
    
    /*
     * Testing Vertex...
     */
    // Testing strategy for Vertex
 // TODO: add tests for getLabel()
    // Partition for vertex.addtarget(source, weight) -> boolean
    //   vertex: empty, contains multiple sources
    //   source: exists as a source, doesn't exist
    //   include tests for adding a source that exists but with
    //   a different weight
    //
    // Partition for vertex.remove(otherVertex) -> previousWeight
    //   vertex: empty, contains multiple sources and targets
    //   otherVertex: doesn't exist, 
    //                exists as a source,
    //                exists as a target
    //   include tests for otherVertex exists as a source and a target
    //
    // Partition for vertex.addsource(target, weight) -> boolean
    //   vertex: empty, contains multiple targets
    //   target: exists as a target, doesn't exist
    //   include tests for adding a target that exists but with
    //   a different weight
    //
    // Partition for vertex.removeSource(source) -> previousWeight
    //   vertex: empty, contains multiple sources
    //   source: exists as a source, doesn't exist
    //
    // Partition for vertex.removeTarget(target) -> previousWeight
    //   vertex: empty, contains multiple targets
    //   target: exists as a target, doesn't exist
    //
    // Partition for vertex.setSource(source, weight) -> previousWeight
    //   vertex: empty, contains multiple sources
    //   source: exists as a source, doesn't exist
    //   weight: 0, > 0
    //   include tests where previousWeight = weight, ie, the weight
    //   being changed is the same as the input weight
    //
    // Partition for vertex.setTarget(target, weight) -> previousWeight
    //   vertex: empty, contains multiple targets
    //   target: exists as a target, doesn't exist
    //   weight: 0, > 0
    //   Include tests where previousWeight = weight
    // 
    // Partition for vertex.getSources() -> sources
    //   vertex: empty, 
    //           contains multiple sources,
    //           contains multiple targets but no sources
    //
    // Partition for vertex.getTargets() -> targets
    //   vertex: empty,
    //           contains multiple targets,
    //           contains multiple sources but no targets
    //
    // Partition for vertex.isTarget(otherVertex) -> boolean
    //   vertex: empty, contains multiple targets
    //   otherVertex: exists as a target, doesn't exist
    //
    // Partition for vertex.isSource(otherVertex) -> boolean
    //   vertex: empty, contains multiple sources
    //   otherVertex: exists as a source, doesn't exist
    //
    //TODO: change tests to include new implementation
    // Partition for vertex.toString()
    //   vertex: empty, 
    //           contains sources but no targets,
    //           contains targets but no sources,
    //           contains targets and sources
    //
    // Exhaustive Cartesian coverage of partitions
    
    //Tests for addSource()
    @Test
    //covers empty vertex,
    //       source doesn't exist
    public void testAddSourceEmptyVertex(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String source = "source";
        final int weight = 1;
        Map<String, Integer> sources = vertex.getSources();
        
        final int initialNumSources = sources.size();
        final boolean sourceAdded = vertex.addSource(source, weight);
        final int currentNumSources = sources.size();
        
        assertTrue("Expected source added successfully", sourceAdded);
        assertNotEquals("Expected vertex to have sources", 
                Collections.emptyMap(), sources);
        assertEquals("Expected num of sources to increase by 1",
                initialNumSources + 1, currentNumSources);
        assertTrue("Expected the correct source label", 
                sources.containsKey(source));
        assertEquals("Expected the correct weight added", 
                (Integer)weight, sources.get(source));
    }
    @Test
    //covers vertex contains multiple sources,
    //       source exists
    public void testAddSourceExists(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String source1 = "source1";
        final String source2 = "source2";
        final int weight = 1;
        Map<String, Integer> sources = vertex.getSources();
        
        final boolean source1Added = vertex.addSource(source1, weight);
        final boolean source2Added = vertex.addSource(source2, weight);
        final int intialNumSources = sources.size();
        final boolean source1AddedAgain = vertex.addSource(source1, weight);
        final int currentNumSources = sources.size();
        
        assertTrue("Expected source1 added", source1Added);
        assertTrue("Expected source2 added", source2Added);
        assertNotEquals("Expected vertex to have sources", 
                Collections.emptyMap(), sources);
        assertEquals("Expected vertex to have 2 source vertices", 
                2, intialNumSources);
        assertFalse("Expected source1 not added again", source1AddedAgain);
        assertEquals("Expected same number of source vertices", 
                intialNumSources, currentNumSources);        
    }
    @Test
    //covers vertex contains multiple sources,
    //       source exists
    //       adding source with a different weight
    public void testAddSourceExistsDiffWeight(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String source1 = "source1";
        final String source2 = "source2";
        final int weight1 = 1;
        final int weight2 = 2;
        Map<String, Integer> sources = vertex.getSources();
        
        final boolean source1Added = vertex.addSource(source1, weight1);
        final boolean source2Added = vertex.addSource(source2, weight1);
        final int intialNumSources = sources.size();
        final boolean source1AddedAgain = vertex.addSource(source1, weight2);
        final int currentNumSources = sources.size();
        
        assertTrue("Expected source1 added", source1Added);
        assertTrue("Expected source2 added", source2Added);
        assertNotEquals("Expected vertex to have sources", 
                Collections.emptyMap(), sources);
        assertEquals("Expected vertex to have 2 source vertices", 
                2, intialNumSources);
        assertFalse("Expected source1 not added again", source1AddedAgain);
        assertEquals("Expected same number of source vertices", 
                intialNumSources, currentNumSources);        
    }
    
    //Tests for addTarget()
    @Test
    //covers empty vertex,
    //       target doesn't exist
    public void testAddTargetEmptyVertex(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String target = "target";
        final int weight = 1;
        Map<String, Integer> targets = vertex.getTargets();
        
        final int initialNumTargets = targets.size();
        final boolean targetAdded = vertex.addTarget(target, weight);
        final int CurrentNumTargets = targets.size();
        
        assertTrue("Expected target added", targetAdded);
        assertNotEquals("Expected vertex to have targets",
                Collections.emptyMap(), targets);
        assertEquals("Expected num of targets to increase by 1",
                initialNumTargets + 1, CurrentNumTargets);
        assertTrue("Expected correct target label", 
                targets.containsKey(target));
        assertEquals("Expected correct weight added", 
                (Integer)weight, targets.get(target));
    }
    @Test
    //covers vertex contains multiple targets
    //       target exists
    public void testAddTargetExists(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String target1 = "target1";
        final String target2 = "target2";
        final int weight = 1;
        Map<String, Integer> targets = vertex.getTargets();
        
        final boolean target1Added = vertex.addTarget(target1, weight);
        final boolean target2Added = vertex.addTarget(target2, weight);
        final int initialNumTargets = targets.size();
        final boolean target1AddedAgain = vertex.addTarget(target1, weight);
        final int CurrentNumTargets = targets.size();
        
        assertTrue("Expected target1 added", target1Added);
        assertTrue("Expected target2 added", target2Added);
        assertNotEquals("Expected vertex to have targets",
                Collections.emptyMap(), targets);
        assertEquals("Expected vertex to have 2 target vertices", 
                2, initialNumTargets);
        assertFalse("Expected target1 not added again", target1AddedAgain);
        assertEquals("Expected same number of target vertices",
                initialNumTargets, CurrentNumTargets);
    }

    @Test
    //covers vertex contains multiple targets
    //       target exists
    //       add target with a different weight
    public void testAddTargetExistsDiffWeight(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String target1 = "target1";
        final String target2 = "target2";
        final int weight1 = 1;
        final int weight2 = 2;
        Map<String, Integer> targets = vertex.getTargets();
        
        final boolean target1Added = vertex.addTarget(target1, weight1);
        final boolean target2Added = vertex.addTarget(target2, weight1);
        final int initialNumTargets = targets.size();
        final boolean target1AddedAgain = vertex.addTarget(target1, weight2);
        final int CurrentNumTargets = targets.size();
        
        assertTrue("Expected target1 added", target1Added);
        assertTrue("Expected target2 added", target2Added);
        assertNotEquals("Expected vertex to have targets",
                Collections.emptyMap(), targets);
        assertEquals("Expected vertex to have 2 target vertices", 
                2, initialNumTargets);
        assertFalse("Expected target1 not added again", target1AddedAgain);
        assertEquals("Expected same number of target vertices",
                initialNumTargets, CurrentNumTargets);
    }
    //Tests for remove()
    @Test
    //covers empty vertex
    //       otherVertex doesn't exist
    public void testRemoveEmptyVertex(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String otherVertex = "othervertex";
        Map<String, Integer> sources = vertex.getSources();
        Map<String, Integer> targets = vertex.getTargets();
        
        final int initialNumSources = sources.size();
        final int initialNumTargets = targets.size();
        final int previousWeight = vertex.remove(otherVertex); 
        final int currentNumSources = sources.size();
        final int currentNumTargets = targets.size();
        
        assertEquals("Expected empty vertex to have no source vertices", 
                0, initialNumSources);
        assertEquals("Expected empty vertex to have no target vertices", 
                0, initialNumTargets);
        assertEquals("Expected no previous weight", 
                0, previousWeight);
        assertEquals("Expected no change in sources", 
                Collections.emptyMap(), sources);
        assertEquals("Expected no change in targets", 
                Collections.emptyMap(), targets);
        assertEquals("Expected no new source vertices", 
                initialNumSources, currentNumSources);
        assertEquals("Expected no new target vertices", 
                initialNumTargets, currentNumTargets);
    }
    @Test
    //covers vertex contains multiple sources
    //       otherVertex exists as a source
    public void testRemoveExistsAsSource(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String otherVertex = "source1";
        final String source2 = "source2";
        final int weight = 1;
        Map<String, Integer> sources = vertex.getSources();
        Map<String, Integer> targets = vertex.getTargets();
        
        final boolean otherVertexAdded = vertex.addSource(otherVertex, weight);
        final boolean source2Added = vertex.addSource(source2, weight);
        
        final int initialNumSources = sources.size();
        final int previousWeight = vertex.remove(otherVertex); 
        final int currentNumSources = sources.size();
        
        assertTrue("Expected otherVertex added", otherVertexAdded);
        assertTrue("Expected source2 added", source2Added);
        assertEquals("Expected vertex to have 2 source vertices", 
                2, initialNumSources);
        assertEquals("Expected correct previous weight", 
                weight, previousWeight);
        assertEquals("Expected num of sources to decrease by 1", 
                initialNumSources - 1, currentNumSources);
        assertFalse("Expected otherVertex removed", sources.containsKey(otherVertex));
        assertEquals("Expected no change in targets", 
                Collections.emptyMap(), targets);
    }
    @Test
    //covers vertex contains multiple targets
    //       otherVertex exists as a target
    public void testRemoveExistsAsTarget(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String otherVertex = "othervertex";
        final String target2 = "target2";
        final int weight = 1;
        Map<String, Integer> targets = vertex.getTargets();
        Map<String, Integer> sources = vertex.getSources();
        
        final boolean otherVertexAdded = vertex.addTarget(otherVertex, weight);
        final boolean target2Added = vertex.addTarget(target2, weight);
        
        final int initialNumTargets = targets.size();
        final int previousWeight = vertex.remove(otherVertex); 
        final int currentNumTargets = targets.size();
        
        assertTrue("Expected otherVertex added", otherVertexAdded);
        assertTrue("Expected target2 added", target2Added);
        assertEquals("Expected vertex to have 2 target vertices", 
                2, initialNumTargets);
        assertEquals("Expected correct previous weight", 
                weight, previousWeight);
        assertEquals("Expected num of targets to decrease by 1", 
                initialNumTargets - 1, currentNumTargets);
        assertFalse("Expected otherVertex removed", targets.containsKey(otherVertex));
        assertEquals("Expected no change in sources", 
                Collections.emptyMap(), sources);
    }
    @Test
    //covers vertex contains multiple sources and targets
    //       otherVertex exists as a source and a target
    public void testRemoveExistsAsBoth(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String otherVertex = "othervertex";
        final String target = "target";
        final String source = "source";
        final int weight = 1;
        Map<String, Integer> targets = vertex.getTargets();
        Map<String, Integer> sources = vertex.getSources();
        
        final boolean otherVertexTarget = vertex.addTarget(otherVertex, weight);
        final boolean targetAdded = vertex.addTarget(target, weight);
        final boolean otherVertexSource = vertex.addSource(otherVertex, weight);
        final boolean sourceAdded = vertex.addSource(source, weight);

        final int initialNumSources = sources.size();
        final int initialNumTargets = targets.size();
        final int previousWeight = vertex.remove(otherVertex);  
        final int currentNumSources = sources.size();
        final int currentNumTargets = targets.size();
        
        assertTrue("Expected otherVertex added as target", otherVertexTarget);
        assertTrue("Expected target added", targetAdded);
        assertTrue("Expected otherVertex added as source", otherVertexSource);
        assertTrue("Expected source added", sourceAdded);
        assertEquals("Expected vertex to have 2 source vertices", 
                2, initialNumSources);
        assertEquals("Expected vertex to have 2 target vertices", 
                2, initialNumTargets);
        assertEquals("Expected correct previous weight", 
                weight, previousWeight);
        assertEquals("Expected num of sources to decrease by 1", 
                initialNumSources - 1, currentNumSources);
        assertEquals("Expected num of targets to decrease by 1", 
                initialNumTargets - 1, currentNumTargets);
        assertFalse("Expected otherVertex removed as source", sources.containsKey(otherVertex));
        assertFalse("Expected otherVertex removed as target", targets.containsKey(otherVertex));
    }
    
    //Tests for removeSource()
    @Test
    //covers empty vertex,
    //       source doesn't exist
    public void testRemoveSourceEmptyVertex(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String source = "source";
        Map<String, Integer> sources = vertex.getSources();
        
        final int initialNumSources = sources.size();
        final int previousWeight = vertex.removeSource(source); 
        final int currentNumSources = sources.size();
        
        assertEquals("Expected empty vertex to have no source vertices", 
                0, initialNumSources);
        assertEquals("Expected no previous weight", 
                0, previousWeight);
        assertEquals("Expected no change in sources", 
                Collections.emptyMap(), sources);
        assertEquals("Expected no new source vertices", 
                initialNumSources, currentNumSources);
    }
    @Test
    //covers vertex contains multiple sources
    //       source exists
    public void testRemoveSourceExists(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String source1 = "source1";
        final String source2 = "source2";
        final String source3 = "source3";
        final int weight = 1;
        Map<String, Integer> sources = vertex.getSources();
        
        final boolean source1Added = vertex.addSource(source1, weight);
        final boolean source2Added = vertex.addSource(source2, weight);
        final boolean source3Added = vertex.addSource(source3, weight);
        
        final int initialNumSources = sources.size();
        final int previousWeight = vertex.removeSource(source1);
        final int currentNumSources = sources.size();
        
        assertTrue("Expected source1 added", source1Added);
        assertTrue("Expected source2 added", source2Added);
        assertTrue("Expected source3 added", source3Added);
        assertEquals("Expected vertex to have 3 source vertices", 
                3, initialNumSources);
        assertEquals("Expected correct previous weight", 
                weight, previousWeight);
        assertEquals("Expected num of sources to decrease by 1", 
                initialNumSources - 1, currentNumSources);
        assertFalse("Expected source1 removed", sources.containsKey(source1));
    }

    
    //Tests for removeTarget()
    @Test
    //covers empty vertex,
    //       target doesn't exist
    public void testRemoveTargetEmptyVertex(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String target = "target";
        Map<String, Integer> targets = vertex.getTargets();
        
        final int initialNumTargets = targets.size();
        final int previousWeight = vertex.removeTarget(target); 
        final int currentNumTargets = targets.size();
        
        assertEquals("Expected empty vertex to have no target vertices", 
                0, initialNumTargets);
        assertEquals("Expected no previous weight", 
                0, previousWeight);
        assertEquals("Expected no change in targets", 
                Collections.emptyMap(), targets);
        assertEquals("Expected no new targets", 
                initialNumTargets, currentNumTargets);
    }
    @Test
    //covers vertex contains multiple targets
    //       target exists
    public void testRemoveTargetExists(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String target1 = "target1";
        final String target2 = "target2";
        final String target3 = "target3";
        final int weight = 1;
        Map<String, Integer> targets = vertex.getTargets();
        
        final boolean target1Added = vertex.addTarget(target1, weight);
        final boolean target2Added = vertex.addTarget(target2, weight);
        final boolean target3Added = vertex.addTarget(target3, weight);
        
        final int initialNumTargets = targets.size();
        final int previousWeight = vertex.removeTarget(target1);
        final int currentNumTargets = targets.size();
        
        assertTrue("Expected target1 added", target1Added);
        assertTrue("Expected target2 added", target2Added);
        assertTrue("Expected target3 added", target3Added);
        assertEquals("Expected vertex to have 3 target vertices", 
                3, initialNumTargets);
        assertEquals("Expected correct previous weight", 
                weight, previousWeight);
        assertEquals("Expected num of targets to decrease by 1", 
                initialNumTargets - 1, currentNumTargets);
        assertFalse("Expected target1 removed", targets.containsKey(target1));
    }
    
    //Tests for setSource()
    @Test
    //covers empty vertex
    //       source doesn't exist
    //       weight > 0
    //Behaviourally equivalent to addSource()
    public void testSetSourceEmptyVertex(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String source = "source";
        final int weight = 1;
        Map<String, Integer> sources = vertex.getSources();
        
        final int initialNumSources = sources.size();
        final int previousWeight = vertex.setSource(source, weight);
        final int currentNumSources = sources.size();
        
        assertEquals("Expected empty vertex to have no sources", 
                0, initialNumSources);
        assertEquals("Expected no previous weight", 
                0, previousWeight);
        assertNotEquals("Expected vertex to have sources", 
                Collections.emptyMap(), sources);
        assertEquals("Expected num of sources to increase by 1",
                initialNumSources + 1, currentNumSources);
        assertTrue("Expected the correct source label", 
                sources.containsKey(source));
        assertEquals("Expected the correct weight added", 
                (Integer)weight, sources.get(source));  
    }
    
    @Test
    //covers empty vertex
    //       source doesn't exist
    //       weight = 0
    public void testSetSourceEmptyVertexZeroWeight(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String source = "source";
        final int weight = 0;
        Map<String, Integer> sources = vertex.getSources();
        
        final int initialNumSources = sources.size();
        final int previousWeight = vertex.setSource(source, weight);
        final int currentNumSources = sources.size();
        
        assertEquals("Expected empty vertex to have no sources", 
                0, initialNumSources);
        assertEquals("Expected no previous weight", 
                0, previousWeight);
        assertEquals("Expected empty vertex to have no sources", 
                Collections.emptyMap(), sources);
        assertEquals("Expected no new source vertices",
                initialNumSources, currentNumSources);
    }

    @Test
    //covers vertex contains multiple targets
    //       source exists
    //       weight > 0
    public void testSetSourceExists(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String source1 = "source1";
        final String source2 = "source2";
        final String source3 = "source3";
        final int weight1 = 1;
        final int weight2 = 2;
        Map<String, Integer> sources = vertex.getSources();
        
        final boolean source1Added = vertex.addSource(source1, weight1);
        final boolean source2Added = vertex.addSource(source2, weight1);
        final boolean source3Added = vertex.addSource(source3, weight1);
        
        final int initialNumSources = sources.size();
        final int previousWeight = vertex.setSource(source1, weight2);
        final int currentNumSources = sources.size();
        
        
        assertTrue("Expected source1 added", source1Added);
        assertTrue("Expected source2 added", source2Added);
        assertTrue("Expected source3 added", source3Added);
        assertEquals("Expected vertex to have 3 source vertices", 
                3, initialNumSources);
        assertEquals("Expected correct previous weight", 
                weight1, previousWeight);
        assertEquals("Expected same number of source vertices", 
                initialNumSources, currentNumSources);
        assertEquals("Expected source1 to change weight", 
                (Integer)weight2, sources.get(source1));
    }
    
    @Test
    //covers vertex contains multiple targets
    //       source exists
    //       weight = 0
    //Behaviourally equivalent to removeSource()
    public void testSetSourceExistsZeroWeight(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String source1 = "source1";
        final String source2 = "source2";
        final String source3 = "source3";
        final int weight = 1;
        final int weight0 = 0;
        Map<String, Integer> sources = vertex.getSources();
        
        final boolean source1Added = vertex.addSource(source1, weight);
        final boolean source2Added = vertex.addSource(source2, weight);
        final boolean source3Added = vertex.addSource(source3, weight);
        
        final int initialNumSources = sources.size();
        final int previousWeight = vertex.setSource(source1, weight0);
        final int currentNumSources = sources.size();
        
        
        assertTrue("Expected source1 added", source1Added);
        assertTrue("Expected source2 added", source2Added);
        assertTrue("Expected source3 added", source3Added);
        assertEquals("Expected vertex to have 3 source vertices", 
                3, initialNumSources);
        assertEquals("Expected correct previous weight", 
                weight, previousWeight);
        assertEquals("Expected num of sources to decrease by 1", 
                initialNumSources - 1, currentNumSources);
        assertFalse("Expected source1 removed", sources.containsKey(source1));
    }
    
    @Test
    //covers vertex contains multiple targets
    //       source exists
    //       weight > 0
    //       weight = previousWeight
    public void testSetSourceExistsSameWeight(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String source1 = "source1";
        final String source2 = "source2";
        final String source3 = "source3";
        final int weight = 1;
        Map<String, Integer> sources = vertex.getSources();
        
        final boolean source1Added = vertex.addSource(source1, weight);
        final boolean source2Added = vertex.addSource(source2, weight);
        final boolean source3Added = vertex.addSource(source3, weight);
        
        final int initialNumSources = sources.size();
        final int previousWeight = vertex.setSource(source1, weight);
        final int currentNumSources = sources.size();
        
        
        assertTrue("Expected source1 added", source1Added);
        assertTrue("Expected source2 added", source2Added);
        assertTrue("Expected source3 added", source3Added);
        assertEquals("Expected vertex to have 3 source vertices", 
                3, initialNumSources);
        assertEquals("Expected no previous weight", 
                0, previousWeight);
        assertEquals("Expected same number of source vertices", 
                initialNumSources, currentNumSources);
        assertEquals("Expected source1 not to change weight", 
                (Integer)weight, sources.get(source1));
    }
    

    //Tests for setTarget()
    @Test
    //covers empty vertex
    //       source doesn't exist
    //       weight > 0
    //Behaviourally equivalent to addTarget()
    public void testSetTargetEmptyVertex(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String target = "target";
        final int weight = 1;
        Map<String, Integer> targets = vertex.getTargets();
        
        final int initialNumTargets = targets.size();
        final int previousWeight = vertex.setTarget(target, weight);
        final int currentNumTargets = targets.size();
        
        assertEquals("Expected empty vertex to have no targets", 
                0, initialNumTargets);
        assertEquals("Expected no previous weight", 
                0, previousWeight);
        assertNotEquals("Expected vertex to have targets", 
                Collections.emptyMap(), targets);
        assertEquals("Expected num of targets to increase by 1",
                initialNumTargets + 1, currentNumTargets);
        assertTrue("Expected the correct target label", 
                targets.containsKey(target));
        assertEquals("Expected the correct weight added", 
                (Integer)weight, targets.get(target));  
    }
    
    @Test
    //covers empty vertex
    //       target doesn't exist
    //       weight = 0
    public void testSetTargetEmptyVertexZeroWeight(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String target = "target";
        final int weight = 0;
        Map<String, Integer> targets = vertex.getTargets();
        
        final int initialNumTargets = targets.size();
        final int previousWeight = vertex.setTarget(target, weight);
        final int currentNumTargets = targets.size();
        
        assertEquals("Expected empty vertex to have no targets", 
                0, initialNumTargets);
        assertEquals("Expected no previous weight", 
                0, previousWeight);
        assertEquals("Expected empty vertex to have no targets", 
                Collections.emptyMap(), targets);
        assertEquals("Expected no new target vertices",
                initialNumTargets, currentNumTargets);
    }

    @Test
    //covers vertex contains multiple targets
    //       target exists
    //       weight > 0
    public void testSetTargetExists(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String target1 = "target1";
        final String target2 = "target2";
        final String target3 = "target3";
        final int weight1 = 1;
        final int weight2 = 2;
        Map<String, Integer> targets = vertex.getTargets();
        
        final boolean target1Added = vertex.addTarget(target1, weight1);
        final boolean target2Added = vertex.addTarget(target2, weight1);
        final boolean target3Added = vertex.addTarget(target3, weight1);
        
        final int initialNumTargets = targets.size();
        final int previousWeight = vertex.setTarget(target1, weight2);
        final int currentNumTargets = targets.size();
        
        
        assertTrue("Expected target1 added", target1Added);
        assertTrue("Expected target2 added", target2Added);
        assertTrue("Expected target3 added", target3Added);
        assertEquals("Expected vertex to have 3 target vertices", 
                3, initialNumTargets);
        assertEquals("Expected correct previous weight", 
                weight1, previousWeight);
        assertEquals("Expected same number of target vertices", 
                initialNumTargets, currentNumTargets);
        assertEquals("Expected target1 to change weight", 
                (Integer)weight2, targets.get(target1));
    }
    
    @Test
    //covers vertex contains multiple targets
    //       target exists
    //       weight = 0
    //Behaviourally equivalent to removeTarget()
    public void testSetTargetExistsZeroWeight(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String target1 = "target1";
        final String target2 = "target2";
        final String target3 = "target3";
        final int weight = 1;
        final int weight0 = 0;
        Map<String, Integer> targets = vertex.getTargets();
        
        final boolean target1Added = vertex.addTarget(target1, weight);
        final boolean target2Added = vertex.addTarget(target2, weight);
        final boolean target3Added = vertex.addTarget(target3, weight);
        
        final int initialNumTargets = targets.size();
        final int previousWeight = vertex.setTarget(target1, weight0);
        final int currentNumTargets = targets.size();
        
        
        assertTrue("Expected target1 added", target1Added);
        assertTrue("Expected target2 added", target2Added);
        assertTrue("Expected target3 added", target3Added);
        assertEquals("Expected vertex to have 3 target vertices", 
                3, initialNumTargets);
        assertEquals("Expected correct previous weight", 
                weight, previousWeight);
        assertEquals("Expected num of targets to decrease by 1", 
                initialNumTargets - 1, currentNumTargets);
        assertFalse("Expected target1 removed", targets.containsKey(target1));
    }
    
    @Test
    //covers vertex contains multiple targets
    //       target exists
    //       weight > 0
    //       weight = previousWeight
    public void testSetTargetExistsSameWeight(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String target1 = "target1";
        final String target2 = "target2";
        final String target3 = "target3";
        final int weight = 1;
        Map<String, Integer> targets = vertex.getTargets();
        
        final boolean target1Added = vertex.addTarget(target1, weight);
        final boolean target2Added = vertex.addTarget(target2, weight);
        final boolean target3Added = vertex.addTarget(target3, weight);
        
        final int initialNumTargets = targets.size();
        final int previousWeight = vertex.setTarget(target1, weight);
        final int currentNumTargets = targets.size();
        
        
        assertTrue("Expected target1 added", target1Added);
        assertTrue("Expected target2 added", target2Added);
        assertTrue("Expected target3 added", target3Added);
        assertEquals("Expected vertex to have 3 target vertices", 
                3, initialNumTargets);
        assertEquals("Expected no previous weight", 
                0, previousWeight);
        assertEquals("Expected same number of target vertices", 
                initialNumTargets, currentNumTargets);
        assertEquals("Expected target1 not to change weight", 
                (Integer)weight, targets.get(target1));
    }
    
    //Tests for getSources()
    @Test
    //covers empty vertex
    public void testGetSourcesEmptyVertex(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        Map<String, Integer> sources = vertex.getSources();
        
        assertEquals("Expected empty vertex to have no sources", 
                Collections.emptyMap(), sources);
    }

    @Test
    //covers vertex contains multiple sources
    public void testGetSourcesMultiple(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String source1 = "source1";
        final String source2 = "source2";
        final int weight = 1;
        Map<String, Integer> sources = vertex.getSources();
        
        final boolean source1Added = vertex.addSource(source1, weight);
        final boolean source2Added = vertex.addSource(source2, weight);
        
        assertTrue("Expected source1 added", source1Added);
        assertTrue("Expected source2 added", source2Added);
        assertNotEquals("Expected vertex to have sources", 
                Collections.emptyMap(), sources);
        assertEquals("Expected vertex to have 2 sources", 
                2, sources.size());
        assertTrue("Expected vertex to have source1 and source2", 
                Arrays.asList(source1, source2).containsAll(sources.keySet()));
    }

    @Test
    //covers vertex contains multiple targets but no sources
    public void testGetSourcesMultipleTargets(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String target1 = "target1";
        final String target2 = "target2";
        final int weight = 1;
        Map<String, Integer> sources = vertex.getSources();
        
        final boolean target1Added = vertex.addTarget(target1, weight);
        final boolean target2Added = vertex.addTarget(target2, weight);
        
        assertTrue("Expected target1 added", target1Added);
        assertTrue("Expected target2 added", target2Added);
        assertEquals("Expected vertex to have no sources", 
                Collections.emptyMap(), sources);
    }
    
    //Tests for getTargets()
    @Test
    //covers empty vertex
    public void testGetTargetsEmptyVertex(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        Map<String, Integer> targets = vertex.getTargets();
        
        assertEquals("Expected empty vertex to have no targets", 
                Collections.emptyMap(), targets);
    }

    @Test
    //covers vertex contains multiple targets
    public void testGetTargetsMultiple(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String target1 = "target1";
        final String target2 = "target2";
        final int weight = 1;
        Map<String, Integer> targets = vertex.getTargets();
        
        final boolean target1Added = vertex.addTarget(target1, weight);
        final boolean target2Added = vertex.addTarget(target2, weight);
        
        assertTrue("Expected target1 added", target1Added);
        assertTrue("Expected target2 added", target2Added);
        assertNotEquals("Expected vertex to have targets", 
                Collections.emptyMap(), targets);
        assertEquals("Expected vertex to have 2 targets", 
                2, targets.size());
        assertTrue("Expected vertex to have target1 and target2", 
                Arrays.asList(target1, target2).containsAll(targets.keySet()));
    }

    @Test
    //covers vertex contains multiple sources but no targets
    public void testGetTargetsMultipleTargets(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String source1 = "source1";
        final String source2 = "source2";
        final int weight = 1;
        Map<String, Integer> targets = vertex.getTargets();
        
        final boolean source1Added = vertex.addSource(source1, weight);
        final boolean source2Added = vertex.addSource(source2, weight);
        
        assertTrue("Expected source1 added", source1Added);
        assertTrue("Expected source2 added", source2Added);
        assertEquals("Expected vertex to have no targets", 
                Collections.emptyMap(), targets);
    }
    
    //Tests for isTarget()
    @Test
    //covers empty vertex
    //       otherVertex not a target
    public void testIsTargetEmptyVertex(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String otherVertex = "othervertex";
        
        final boolean isTarget = vertex.isTarget(otherVertex);
        assertFalse("Expected empty vertex to have no targets", isTarget);
    }
    
    @Test
    //covers vertex contains multiple targets
    //       otherVertex not a target
    public void testIsTargetMultipleTargets(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String target1 = "target1";
        final String target2 = "target2";
        final String otherVertex = "othervertex";
        final int weight = 1;
        
        final boolean target1Added = vertex.addTarget(target1, weight);
        final boolean target2Added = vertex.addTarget(target2, weight);
        
        final boolean isTarget = vertex.isTarget(otherVertex);

        assertTrue("Expected target1 added", target1Added);
        assertTrue("Expected target2 added", target2Added);
        assertFalse("Expected otherVertex not to be a target", isTarget);
    }
    
    @Test
    //covers vertex contains multiple targets
    //       otherVertex exists as a target
    public void testIsTargetTrue(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String target1 = "target1";
        final String target2 = "target2";
        final int weight = 1;
        
        final boolean target1Added = vertex.addTarget(target1, weight);
        final boolean target2Added = vertex.addTarget(target2, weight);
        
        final boolean isTarget = vertex.isTarget(target1);

        assertTrue("Expected target1 added", target1Added);
        assertTrue("Expected target2 added", target2Added);
        assertTrue("Expected target1 to be a target", isTarget);
    }
    
    //Tests for isSource()
    @Test
    //covers empty vertex
    //       otherVertex not a source
    public void testIsSourceEmptyVertex(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String otherVertex = "othervertex";
        
        final boolean isSource = vertex.isSource(otherVertex);
        assertFalse("Expected empty vertex to have no sources", isSource);
    }
    
    @Test
    //covers vertex contains multiple sources
    //       otherVertex not a source
    public void testIsSourceMultipleSources(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String source1 = "source1";
        final String source2 = "source2";
        final String otherVertex = "othervertex";
        final int weight = 1;
        
        final boolean source1Added = vertex.addSource(source1, weight);
        final boolean source2Added = vertex.addSource(source2, weight);
        
        final boolean isSource = vertex.isSource(otherVertex);

        assertTrue("Expected source1 added", source1Added);
        assertTrue("Expected source2 added", source2Added);
        assertFalse("Expected otherVertex not to be a source", isSource);
    }
    
    @Test
    //covers vertex contains multiple sources
    //       otherVertex exists as a source
    public void testIsSourceTrue(){
        final Vertex<String> vertex = new Vertex<>("vertex");
        final String source1 = "source1";
        final String source2 = "source2";
        final int weight = 1;
        
        final boolean source1Added = vertex.addSource(source1, weight);
        final boolean source2Added = vertex.addSource(source2, weight);
        
        final boolean isSource = vertex.isSource(source1);

        assertTrue("Expected source1 added", source1Added);
        assertTrue("Expected source2 added", source2Added);
        assertTrue("Expected source1 to be a source", isSource);
    }

//    @Test
//    //covers empty vertex
//    public void testToStringEmptyVertex(){
//        final Vertex<String> vertex = new Vertex<>("vertex");
//        final String label = vertex.getLabel();
//        String stringRep = vertex.toString();
//        
//        String regex = String.format("label: %s, sources: %d, targets: %d",
//                label, 0, 0);
//        
//        assertTrue("Expected correct vertex string rep",stringRep.matches(regex));
//    }
//    @Test
//    //covers vertex contains sources but no targets
//    public void testToStringMultipleSourcesNoTargets(){
//        final Vertex<String> vertex = new Vertex<>("vertex");
//        
//        vertex.addSource("source1", 1);
//        vertex.addSource("source2", 1);
//        
//        final String label = vertex.getLabel();
//        final int numSources = vertex.getSources().size();
//        String stringRep = vertex.toString();
//        
//        String regex = String.format("label: %s, sources: %d, targets: %d",
//                label, numSources, 0);
//        
//        assertTrue("Expected correct vertex string rep",stringRep.matches(regex));
//    }
//    @Test
//    //covers vertex contains targets but no sources
//    public void testToStringMultipleTargetsNoSources(){
//        final Vertex<String> vertex = new Vertex<>("vertex");
//        
//        vertex.addTarget("target1", 1);
//        vertex.addTarget("target2", 1);
//        
//        final String label = vertex.getLabel();
//        final int numTargets = vertex.getTargets().size();
//        String stringRep = vertex.toString();
//        
//        String regex = String.format("label: %s, sources: %d, targets: %d",
//                label, 0, numTargets);
//        
//        assertTrue("Expected correct vertex string rep",stringRep.matches(regex));
//    }
//    @Test
//    //covers vertex contains sources and targets
//    public void testToStringMultipleTargetsSources(){
//        final Vertex<String> vertex = new Vertex<>("vertex");
//        
//        vertex.addSource("source1", 1);
//        vertex.addSource("source2", 1);
//        vertex.addTarget("target1", 1);
//        vertex.addTarget("target2", 1);
//        
//        final String label = vertex.getLabel();
//        final int numSources = vertex.getSources().size();
//        final int numTargets = vertex.getTargets().size();
//        String stringRep = vertex.toString();
//        
//        String regex = String.format("label: %s, sources: %d, targets: %d",
//                label, numSources, numTargets);
//        
//        assertTrue("Expected correct vertex string rep",stringRep.matches(regex));
//    }
}  

