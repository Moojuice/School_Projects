package lab3;

//
// PQUNITTEST.JAVA
// A unit test for your priority queue class.
//
class PQUnitTest {
    
    public static void main(String args[]) 
    {
	
	Terminal.startTranscript(args[0]);
	
	
	Terminal.println("*** UNIT TEST ONE ***");
	pqUnitTestOne();
	
	Terminal.println("--------------------------------------------------");
	
	Terminal.println("*** UNIT TEST TWO ***");
	pqUnitTestTwo();
	
	Terminal.println("--------------------------------------------------");
	
	Terminal.println("*** UNIT TEST THREE ***");
	pqUnitTestThree();
	
	Terminal.println("--------------------------------------------------");
	
	Terminal.println("*** UNIT TEST FOUR ***");
	pqUnitTestFour();
	
	Terminal.stopTranscript();
    }
    
    // Start by testing just insertion, min, and extraction.
    static void pqUnitTestOne()
    {
	PriorityQueue<String> q = new PriorityQueue<String>();
	
	// build up the queue
	q.insert(4, "four");
	q.insert(7, "seven");
	q.insert(1, "one");
	q.insert(6, "six");
	q.insert(12, "twelve");
	q.insert(3, "three");
	q.insert(11, "eleven");
	q.insert(5, "five");
	q.insert(8, "eight");
	q.insert(3, "three-prime");
	q.insert(2, "two");
	
	Terminal.println("Initial queue:\n" + q);
	
	// extract the smallest elements from the queue
	for (int j = 0; j < 6; j++)
	    {
		int k = q.min();
		String s = q.extractMin();
		
		Terminal.println("Extract: (" + k + ", " + s + ")");
	    }
	
	Terminal.println("\nAfter extractions:\n" + q);
	
	// add a few more values
	q.insert(8, "eight-prime");
	q.insert(14, "fourteen");
	q.insert(9, "nine");
	
	Terminal.println("After more insertions:\n" + q);
	
	// extract the smallest elements from the queue
	for (int j = 0; j < 6; j++)
	    {
		int k = q.min();
		String s = q.extractMin();
		
		Terminal.println("Extract: (" + k + ", " + s + ")");
	    }
	
	Terminal.println("\nFinal queue:\n" + q);
    }
    
    // Make sure Handles are working, then test decreaseKey.
    static void pqUnitTestTwo()
    {
	PriorityQueue<String> q = new PriorityQueue<String>();
	Handle handles[] = new Handle [20];
	
	// build up the queue
	handles[9] = q.insert(9, "nine");
	handles[7] = q.insert(7, "seven");
	handles[2] = q.insert(2, "two");	
	handles[8] = q.insert(8, "eight");
	handles[1] = q.insert(1, "one");
	handles[6] = q.insert(6, "six");
	handles[10] = q.insert(10, "ten");
	handles[5] = q.insert(5, "five");
	handles[3] = q.insert(3, "three");
	handles[4] = q.insert(4, "four");
	handles[0] = q.insert(3, "element zero");

	// Do the handles point to the correct elements?
	for (int j = 0; j <= 10; j++)
	    {
		Terminal.println("Element " + j  + ": (" +
				 q.handleGetKey(handles[j]) + ", " +
				 q.handleGetValue(handles[j]) + ")");
	    }
	
	// extract the smallest elements from the queue
	for (int j = 0; j < 5; j++)
	    q.extractMin();
	
	Terminal.println("\nAfter extractions: ");
	
	// Do the handles *still* point to the correct elements?
	for (int j = 5; j <= 10; j++)
	    {
		Terminal.println("Element " + j  + ": (" +
				 q.handleGetKey(handles[j]) + ", " +
				 q.handleGetValue(handles[j]) + ")");
	    }
	
	// add some more elements
	handles[12] = q.insert(12, "twelveX");
	handles[15] = q.insert(15, "fifteenX");
	handles[13] = q.insert(13, "thirteenX");
	handles[11] = q.insert(11, "elevenX");
	handles[14] = q.insert(14, "fourteenX");

	// should have no effect
	q.decreaseKey(handles[11], 11);
	q.decreaseKey(handles[11], 15);
	Terminal.println("\nUseless decreases: (" + 
			 q.handleGetKey(handles[11]) +
			 ", " + 
			 q.handleGetValue(handles[11]) + ")");
	
	// now move some keys around
	q.decreaseKey(handles[12], 1);
	q.decreaseKey(handles[6], 3);
	q.decreaseKey(handles[14], 4);
	q.decreaseKey(handles[9], 2);
	
	// should rearrange the heap and fix up the handles
	Terminal.println("Useful decrease: (" + 
			 q.handleGetKey(handles[12]) +
			 ", " + 
			 q.handleGetValue(handles[12]) + ")");
	
	Terminal.println("Useful decrease: (" + 
			 q.handleGetKey(handles[9]) +
			 ", " + 
			 q.handleGetValue(handles[9]) + ")");
	
	Terminal.println("Useful decrease: (" + 
			 q.handleGetKey(handles[6]) +
			 ", " + 
			 q.handleGetValue(handles[6]) + ")");
	
	Terminal.println("Extract: (" +
			 q.min() +
			 ", " + 
			 q.extractMin() + ")");
	
	Terminal.println("Extract: (" +
			 q.min() +
			 ", " + 
			 q.extractMin() + ")");
    }
    
    // a lame PRNG that acts the same for both Java and C++
    static int state = 840287439;
    static int lcRand() {
	int A = 14357867;
	int B = 23341349;

	state = A * state + B;
	if (state < 0)
	    state = -state;
	
	return state;
    }
    
    // A little more exercise.
    static void pqUnitTestThree()
    {
	PriorityQueue<Integer> q = new PriorityQueue<Integer>();
	Handle handles [] = new Handle [1000];
	
	// Insert a bunch of random elements.
	for (int j = 0; j < 1000; j++)
	    handles[j] = q.insert(lcRand(), new Integer(lcRand()));
	
	// Extract the ten smallest elements.
	for (int j = 0; j < 10; j++)
	    Terminal.println("Extract: (" + q.min() + ", " + 
			     q.extractMin() + ")");
	
	Terminal.println("************************************");
	
	// Decrease the keys of random elements to random values.
	for (int j = 0; j < 1000; j++)
	    q.decreaseKey(handles[lcRand() % 1000], lcRand());
	
	// Extract the ten smallest elements.
	for (int j = 0; j < 10; j++)
	    Terminal.println("Extract: (" + q.min() + ", " + 
			     q.extractMin() + ")");
    }
    
    // Write your own *simple* unit test.
    static void pqUnitTestFour()
    {
	Terminal.println("Write your own unit test");
    }
}
