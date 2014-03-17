package lab2;

import java.util.Iterator;
import java.util.LinkedList;


/**
 * A hash table mapping Strings to their positions in the pattern sequence.
 *
 * Fill in the methods for this class.
 */
public class StringTable {
	public Record[] hashTable; //our hashtable 
	public int elementsInserted = 0; //the counter for the number of elements we have inserted = n
	
    /**
     * Create an empty table of size n
     *
     * @param n size of the table
     */
    public StringTable(int n) {
    	hashTable = new Record[n];
    }
    /**
     * Create an empty table.  You should use this construction when you are
     * implementing the doubling procedure.
     */
    public StringTable() {
    	hashTable = new Record[8];
    }

    /**
     * Insert a Record r into the table.
     *
     * If you get two insertions with the same key value, return false.
     *
     * @param r Record to insert into the table.
     * @return boolean true if the insertion succeeded, false otherwise
     */
    public boolean insert(Record r) { //CHANGE THIS TO >=
    	if ((double)elementsInserted/size() > .25) { //if n/m (load factor) is greater than 1/4, then we double the size of the array
    		doubleSize(r); 
			return true; //we take care of inserting the element when we double, we could have done it here too by not returning true
    	}
    	int k = r.getHash(); 
    	int h1 = baseHash(k); 
    	if (hashTable[h1] == null || hashTable[h1].isDeleted()) { //we check if the base case doesn't collide
    		hashTable[h1] = r;
    		elementsInserted++;
    		r.insert(h1); //stores the index position in the record itself
    		return true;
    	}
    	if (hashTable[h1].getHash() == r.getHash() && hashTable[h1].equals(r)) { //if we are inserting the same key twice, we don't need to make any changes
    		return true;
    	}
    	int h2 = stepHash(k); //if we collided, we need to step hash
    	int result = (h1 + h2)%hashTable.length;
    	int count = 1;
		while (count < hashTable.length) { 
			if (hashTable[result] == null || hashTable[result].isDeleted()) {
				hashTable[result] = r;
	    		elementsInserted++;
	    		r.insert(result); //adds the index 
				return true;
			}
			if (hashTable[result].getHash() == r.getHash() && hashTable[result].equals(r)) {
				return true;
			}
			result = (result + h2)%(hashTable.length);
			count++;
		}
    	return false;
    }

    /**
     * Delete a record from the table.
     *
     * Note: You'll have to find the record first unless you keep some
     * extra information in the Record structure.
     *
     * @param r Record to remove from the table.
     */
    public void remove(Record r) {
       Record a = find(r.getKey());
       if (a != null) {
    	   hashTable[a.getPosition()].delete(); //we use a delete boolean (it's deleted)
    	   elementsInserted--;
       }
    }

    /**
     * Find a record with a key matching the input.
     *
     * @param key to find
     * @return the matched Record or null if no match exists.
     */
    public Record find(String key) { 
    	int k = toHashKey(key); 
   		int h1 = baseHash(k); 
   		if(hashTable[h1] != null && hashTable[h1].getHash() == k && hashTable[h1].getKey().equals(key) && !hashTable[h1].isDeleted()) {
			return hashTable[h1];
		}
   		int h2 = stepHash(k);
   		int result = (h1 + h2)%hashTable.length;
   		int count = 1;
   			while (count < hashTable.length) { 
   				if (hashTable[result] != null && hashTable[result].getHash() == k && hashTable[result].getKey().equals(key) && !hashTable[result].isDeleted()) {
   					return hashTable[result];
   				}
   				result = (result + h2)%(hashTable.length);
   				count++;
   			}
   		return null;
    }

    /**
     * Return the size of the hash table (i.e. the number of elements
     * this table can hold)
     *
     * @return the size of the table
     */
    public int size() {
    	return hashTable.length;
    }

    /**
     * Return the hash position of this key.  If it is in the table, return
     * the postion.  If it isn't in the table, return the position it would
     * be put in.  This value should always be smaller than the size of the
     * hash table.
     *
     * @param key to hash
     * @return the int hash
     */
    public int hash(String key) {
        int k = toHashKey(key);
    	int h1 = baseHash(k);
    	if (hashTable[h1] == null || hashTable[h1].getKey().equals(key) || hashTable[h1].isDeleted()) {
    		return h1;
    	}
    	else {
    		int h2 = stepHash(k);
    		int result = (h1 + h2)%(size());
    		int count = 1;
    		while (hashTable[result] != null && count < size() && !hashTable[result].isDeleted() && !hashTable[result].getKey().equals(key)) { //YESSS NICE FIX BRAH
    			result = (result + h2)%(size());
    			count++;
    		}
       		return result;
    	}
    }
    
   
    /**
     * Convert a String key into an integer that serves as input to hash functions.
     * This mapping is based on the idea of a linear-congruential pseuodorandom
     * number generator, in which successive values r_i are generated by computing
     *    r_i = (A * r_(i-1) + B) mod M
     * A is a large prime number, while B is a small increment thrown in so that
     * we don't just compute successive powers of A mod M.
     *
     * We modify the above generator by perturbing each r_i, adding in the ith
     * character of the string and its offset, to alter the pseudorandom
     * sequence.
     *
     * @param s String to hash
     * @return int hash
     */
    int toHashKey(String s) {
        int A = 1952786893;
        int B = 367253;
        int v = B;

        for (int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            v = A * (v + (int) c + j) + B;
        }

        if (v < 0) {
            v = -v;
        }
        return v;
    }

    /**
     * Computes the base hash of a hash key
     *
     * @param hashKey
     * @return int base hash
     */
    int baseHash(int hashKey) {
    	double A = (Math.sqrt(5) - 1)/2;
    	return (int) Math.floor(size()*((A*hashKey)-Math.floor(A*hashKey)));
    }

    /**
     * Computes the step hash of a hash key
     *
     * @param hashKey
     * @return int step hash
     */
    int stepHash(int hashKey) {
        double B = (Math.sqrt(11)/4);
        int h2 = (int) Math.floor(size()*((B*hashKey)-Math.floor(B*hashKey)));
        if (h2%2 == 0) {
        	return 1 + h2;
        }
        else {
        	return h2;
        }
    }
    
    /**
     * Our doubling procedure, creates a new hash table, rehashes/reinserts all of the old values into the  new one
     * @param r
     */
    public void doubleSize(Record r) {
    	StringTable newHashTable = new StringTable(size()*2);
		for (int i = 0; i<hashTable.length; i++) {
			if (hashTable[i]!=null && !hashTable[i].isDeleted()) {
				newHashTable.insert(hashTable[i]);
			}
		}
		newHashTable.insert(r);
		elementsInserted++; //updates elements inserted, could do this, or you could, you know elementsInserted = newHashTable.elementsInserted
		hashTable = newHashTable.hashTable;
    }
}

