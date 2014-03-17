package lab2;

import java.util.*;

/**
 * Record type for string hash table
 *
 * A record associates a certain string (the key value) with
 * a list of sequence positions at which that string occurs
 *
 * Change anything you want about this except the methods we have already.
 */
public class Record {
    private final String key;
    private final ArrayList<Integer> positions;
    private int position;
    private boolean deleted;
    private int hashNum;

    /**
     * Constructs a Record with the given string
     *
     * @param s key of the Record
     */
    public Record(String s) {
        key = s;
        positions = new ArrayList<Integer>(1);
        position = -1;
        deleted = false;
        hashNum = toHashKey(s);
    }

    public void insert(int index) {
    	position = index;
    	deleted = false;
    }
    
    public void delete() {
    	deleted = true;
    }
    public void giveHash(int hash) {
    	hashNum = hash;
    }
   
    public boolean isDeleted() {
    	return deleted;
    }
    
    public int getPosition() {
    	return position;
    }
    
    public int getHash() {
    	return hashNum;
    }
    /**
     * Returns the key associated with this Record.
     */
    public String getKey() {
        return key;
    }
    
    /**
     * Adds a new position to the positions list.
     *
     * @param position of the key
     */
    public void addPosition(Integer position) {
        positions.add(position);
    }

    /**
     * Returns how many positions we have
     */
    public int getPositionsCount() {
        return positions.size();
    }

    /**
     * Positions accessor
     */
    public Integer getPosition(int index) {
        return positions.get(index);
    }
    public int toHashKey(String s) {
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
    
}