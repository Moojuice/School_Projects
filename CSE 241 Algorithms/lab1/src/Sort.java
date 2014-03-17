package lab1;

import java.util.Random;

public class Sort {
    //================================= sort =================================
    //
    // Input: array A of XYPoints refs 
    //        lessThan is the function used to compare points
    //
    // Output: Upon completion, array A is sorted in nondecreasing order
    //         by lessThan.
    //
    // DO NOT USE ARRAYS.SORT.  WE WILL CHECK FOR THIS.  YOU WILL GET A 0.
    // I HAVE GREP AND I AM NOT AFRAID TO USE IT.
    //=========================================================================
    public static void msort(XYPoint[] A, Comparator lessThan) {
    	//randomizedQuickSort(A, lessThan, 0, A.length -1);
    	msortHelper(A, lessThan, 0, A.length -1); //we choose to run our msort, because our quicksort dies with long arrays
    } 
    /**
     * Our quicksort method, includes two recursive calls and a partition
     * @param A our array
     * @param lessThan 
     * @param p 
     * @param r
     */
    public static void quickSort(XYPoint[] A, Comparator lessThan, int p, int r) {
    	if (p<r) {
//    		Random ran = new Random(); //randomization of the partition
//    		int z = ran.nextInt(r - p + 1) + p;
//    		swap(A[z], A[r]);
    		int q = partition(A, lessThan, p, r);
    		quickSort(A, lessThan, p, q-1);
    		quickSort(A, lessThan, q+1, r);
    	}
    }
    public static void randomizedQuickSort(XYPoint[] A, Comparator lessThan, int p, int r) {
    	if (p<r) {
    		int q = randomizedPartition(A, lessThan, p, r);
    		randomizedQuickSort(A, lessThan, p, q-1);
    		randomizedQuickSort(A, lessThan, q+1, r);
    	}
    }
    public static int randomizedPartition(XYPoint[] A, Comparator lessThan, int p, int r) {
    	Random ran = new Random();
    	int i = ran.nextInt(r - p +1) + p;
    	swap(A[r], A[i]);
    	return partition(A, lessThan, p, r);
    }
    /**
     * Swaps a and b
     * @param a
     * @param b
     */
    public static void swap(XYPoint a, XYPoint b) { 
    	XYPoint temp = a;
    	a = b;
    	b = temp;
    }
    /**
     * Partitions the array, checking every index, smaller values to the left, larger to the right, returns partition's index
     * @param A
     * @param lessThan
     * @param p
     * @param r
     * @return
     */
    public static int partition(XYPoint[] A, Comparator lessThan, int p, int r) { 
    	XYPoint x = A[r]; 
    	int i = p-1;
    	for (int j = p; j <= r-1; j++) {
    		if (lessThan.comp(A[j], x)) {
    			i++;
    			swap(A[i], A[j]);
    		}
    	}
    	swap(A[i +1], A[r]);
    	return i + 1;
    }
    /**
     * What we use to mergesort, splits the array in half recursively, sorting, then merging back
     * @param A
     * @param lessThan
     * @param p
     * @param r
     */
    public static void msortHelper(XYPoint[] A, Comparator lessThan, int p, int r) {
    	if (p<r) {
    		int q = (p + r)/2;
    		msortHelper(A, lessThan, p, q);
    		msortHelper(A, lessThan, q+1, r);
    		merge(A, lessThan, p, q, r);
    	}
    }
    /**
     * Our merge method
     * @param A
     * @param lessThan
     * @param p
     * @param q
     * @param r
     */
    public static void merge(XYPoint[] A, Comparator lessThan, int p, int q, int r) {
    	int m = q - p + 1;
    	int n = r - q;
    	XYPoint[] L = new XYPoint[m+1];
    	XYPoint[] R = new XYPoint[n+1];
    	for (int i = 0; i < m; i++) {
    		L[i] = A[p + i];
    	}
    	for (int j = 0; j < n; j++) {
    		R[j] = A[q + j +1];
    	}
    	L[m] = new XYPoint(Integer.MAX_VALUE, Integer.MAX_VALUE);
    	R[n] = new XYPoint(Integer.MAX_VALUE, Integer.MAX_VALUE); 
    	int i = 0;
    	int j = 0;
    	for (int k = p; k <= r; k++) {
    		if (lessThan.comp(L[i], R[j])) {
    			A[k] = L[i];
    			i++;
    		}
    		else {
    			A[k] = R[j];
    			j++;
    		}	
    	}	
    } 
}
