package lab3;

import java.util.ArrayList;
import java.util.List;

/**
 * A priority queue class supporting operations needed for
 * Dijkstra's algorithm.
 */
class PriorityQueue<T> {
    private ArrayList<PQNode> list; //our list that is used for holding the PQ Nodes
    /**
     * Constructor
     */
    public PriorityQueue() {
    	list = new ArrayList<PQNode>();
    }

    /**
     * @return true iff the queue is empty.
     */
    public boolean isEmpty() {
       return list.isEmpty();
    }

    /**
     * Insert a (key, value) pair into the queue.
     *
     * @return a Handle to this pair so that we can find it later.
     */
    Handle insert(int key, T value) {
    	list.add(new PQNode(key, value)); //we add the node to the back of the queue
    	int i = list.size()-1;
    	Handle result = new Handle(i);
        list.get(i).setHandle(result); //assign the node its handle
    	while (list.get((int)Math.floor(i/2.0)).getKey() > list.get(i).getKey()) { //if the node is smaller than its parent, swap
    	   swap((int)Math.floor(i/2.0), i);
    	   list.get(i).getHandle().setIndex(i); //gives the new child its new handle (used to be parent)
    	   i = (int)Math.floor(i/2.0);
       }
    	list.get(i).getHandle().setIndex(i); //assigns the latest index 
    	return result;
    }
    
    public void heapify(int i) {
    	if (2*i+1 < list.size()) { //left node exists
    		PQNode left = list.get(2*i+1);
    		if(2*i+2 < list.size()) { //right node exists
    			PQNode right = list.get(2*i+2);
    	    	if (left.getKey()<right.getKey()) { //left is smallest
    	    		if(left.getKey()<list.get(i).getKey()) { //swap with left, heapify new left child
    	    			swap(i, 2*i+1);
    	    			heapify(2*i+1);
    	    		}
    	    	}
    	    	else { //right is smallest
    	    		if(right.getKey()<list.get(i).getKey()) { //swap with right, heapify new right child
    	    			swap(i, 2*i+2);
    	    			heapify(2*i+2);
    	    		}
    	    	}
    		}
    		else { //if left exists but right does not. we do this case if the last element is a left node.
    			if (left.getKey()<list.get(i).getKey()) {
    				swap(i, 2*i+1);
    				list.get(i).getHandle().setIndex(i);
    				heapify(2*i+1);
    			}
    		}
    	}
    	
    }
    
    public void swap(int a, int b) {
    	PQNode temp = list.get(a);
    	list.set(a, list.get(b));
    	list.get(a).getHandle().setIndex(a); 
    	list.set(b, temp);
    	list.get(b).getHandle().setIndex(b);
    }
    
    public void sortHeap() {
    	
    }
    
    public void buildHeap() {
    	
    }
    
    /**
     * 
     * @return size of the array
     */
    public int size() {
    	return list.size();
    }
    
    /**
     * 
     * @return our arraylist
     */
    public ArrayList<PQNode> getList() {
    	return list;
    }

    /**
     * @return the min key in the queue.
     */
    public int min() {
       return list.get(0).getKey();
    }

    /**
     * Find and remove the smallest (key, value) pair in the queue.
     *
     * @return the value associated with the smallest key
     */
    public T extractMin() {
    	T result = (T) list.get(0).getValue(); 
    	swap(0, list.size()-1); //swap the first and last element
    	list.get(list.size()-1).getHandle().setIndex(-1); //as a backup, we set the handle to point to -1
    	list.remove(list.size()-1); //remove the min from the list 
    	if (!list.isEmpty()) { 
    		heapify(0); //heapify the new first element 
    	}
        return result;
    }


    /**
     * Decrease the key to the newKey associated with the Handle.
     *
     * If the pair is no longer in the queue, or if its key <= newKey,
     * do nothing and return false.  Otherwise, replace the key with newkey,
     * fix the ordering of the queue, and return true.
     *
     * @return true if we decreased the key, false otherwise.
     */
    public boolean decreaseKey(Handle h, int newKey) {
    	int i = h.getIndex(); 
    	if (i > -1 && i < list.size()) {
    	   if (list.get(i).getKey() > newKey) { //if the key < newKey, proceed 
    		   list.get(i).setKey(newKey);
    		   while (list.get(i).getKey() < list.get((int)Math.floor(i/2.0)).getKey()) { //move upwards if smaller than parent
    			   swap(i, (int)Math.floor(i/2.0)); 			   
    			   i = (int)Math.floor(i/2.0);
    		   }
    		   return true;
    	   }
       }
    	return false; //if pq node has been removed, refers to some index outside (i don't know how this would happen)
    					//or if the key is smaller than newKey
    }

    /**
     * @return the key associated with the Handle.
     */
    public int handleGetKey(Handle h) {
    	if (h.getIndex() > -1 && h.getIndex()<list.size()) {
    		return list.get(h.getIndex()).getKey();
    	}
       	return -1; //same condition
    }

    /**
     * @return the value associated with the Handle.
     */
    public T handleGetValue(Handle h) {
    	if (h.getIndex() > -1 && h.getIndex()<list.size()) {
    		return (T) list.get(h.getIndex()).getValue();
    	}
    	return null; //same condition 
    }

    /**
     * Print every element of the queue in the order in which it appears
     * (i.e. the array representing the heap)
     */
    public String toString() {
    	String s = "";
    	for (int i = 0; i < list.size(); i++) {
    		s +="(" + list.get(i).getKey() +", " + list.get(i).getValue() +")" + " ";
    	}
    	return s;
    }
}
