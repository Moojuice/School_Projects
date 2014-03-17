package lab3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import lab3.Vertex.EdgeIterator;

/**
 * Compute shortest paths in a graph.
 *
 * Your constructor should compute the actual shortest paths and
 * maintain all the information needed to reconstruct them.  The
 * returnPath() function should use this information to return the
 * appropriate path of edge ID's from the start to the given end.
 *
 * Note that the start and end ID's should be mapped to vertices using
 * the graph's get() function.
 */
class ShortestPaths {
	Multigraph graph; //graph G
	int s; //starting id
	PriorityQueue queue = new PriorityQueue(); //the queue we use for Dijkstra's
	HashMap<Vertex, Handle> verticesToHandles = new HashMap<Vertex, Handle>(); //vertices and their handles
	HashMap<Integer, Integer> predecessorPath = new HashMap<Integer, Integer>(); //vertices to predecessor edge
	HashMap<Integer, Integer> predecessorVertex = new HashMap<Integer, Integer>(); //vertices to predecessor vertex
	/* IT SHOULD BE KNOWN
	 * the reason why I did all of these maps is because I assumed that any extensions to vertex would not be saved. Otherwise
	 * it would be a lot easier and quicker
	 */
	
	
    /**
     * Constructor
     */
    public ShortestPaths(Multigraph G, int startId) {
    	graph = G;
    	s = startId;
    	/**
    	 * Initialize-Single-Source 
    	 * We assign the startId key 0 and everything else an absurdly high key
    	 * NOTE: for some reason Integer.Max_VALUE was giving negative numbers sometimes
    	 */
       for (int i = 0; i < G.nVertices(); i++) {
    	   Handle a;
    	   if (G.get(i).id() == startId) {
    		   a = queue.insert(0, G.get(i));
    	   }
    	   else {
    		   a = queue.insert(999999999, G.get(i));
    	   }
    	   verticesToHandles.put(G.get(i), a); //assign vertices to handles
       }
       /**
        * Dijkstra's
        */
       while (!queue.isEmpty()) { 
    	  int du = queue.min(); //d[u]
    	  Vertex u = (Vertex) queue.extractMin(); //extract u 
    	  EdgeIterator a = u.adj();
    	  /**
    	   * Relaxation step
    	   */
    	  while (a.hasNext()) { //relax every edge of u
    		  Edge w = a.next();
    		  Vertex v = w.to(); //v
    		  int dv = queue.handleGetKey(verticesToHandles.get(v)); //d[v]	
    		  if (dv!=-1) { //if the element has not been extracted yet, regular relaxation
    			  if (dv > du + w.weight()) { //if d[v] > d[u] + w(u,v)
        			  queue.decreaseKey(verticesToHandles.get(v), du + w.weight());
        			  predecessorVertex.put(v.id(), u.id()); //u is the predecessor of v
        			  predecessorPath.put(v.id(), w.id()); //we save these for returnPath
        		 }	
    		  }
    	  }
       }
    }
    
    /**
     * Calculates the list of edge ID's forming a shortest path from the start
     * vertex to the specified end vertex.
     *
     * @return the array
     */
    public int[] returnPath(int endId) { 
    	ArrayList<Integer> result = new ArrayList<Integer>();
    	if (!predecessorVertex.containsKey(endId)) {//if the element has no predecessors, then return an empty array 
    		return new int[0];
    	} else {
    		result.add(predecessorPath.get(endId)); //we add the first predecessor path
    		int predecessor = predecessorVertex.get(endId); //the predecessor vertex
    		while (predecessor != s) { //until we reach s
    			result.add(predecessorPath.get(predecessor)); //add the next predecessor path
    			predecessor = predecessorVertex.get(predecessor); //update the predecessor
    		}
    	}
    	int[] actualResult = new int[result.size()]; 
    	for (int i = 0; i < result.size(); i++) { //now we need to reverse the order and return an int array
    		actualResult[i] = result.get(result.size()-1-i);
    	}
    	return actualResult;
    }
}
