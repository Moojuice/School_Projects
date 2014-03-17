package lab3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestLab3 {

    private static final Random RAND = new Random();

    @Test
    public void basicInsertionMinExtractionTest() {
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

        assertEquals(1, q.min());
        assertEquals("one", q.extractMin());
        assertEquals(2, q.min());
        assertEquals("two", q.extractMin());
        assertEquals(3, q.min());
        q.extractMin();
        assertEquals(3, q.min());
        q.extractMin();
        assertEquals(4, q.min());
        assertEquals("four", q.extractMin());
        assertEquals(5, q.min());
        assertEquals("five", q.extractMin());
        
    
        q.insert(1, "one");
        q.insert(15, "fifteen");
        q.insert(5, "five");
        assertEquals(1, q.min());
        assertEquals("one", q.extractMin());
        assertEquals(5, q.min());
        assertEquals("five", q.extractMin());
    }

    @Test
    public void basicHandleAndDecreaseTest() {
        PriorityQueue<String> q = new PriorityQueue<String>();
        Map<String, Handle> valuesToHandles = new HashMap<String, Handle>();
        Map<String, Integer> valuesToKeys = new HashMap<String, Integer>();
        int seq = 0;
        // Generate random numbres for the keys.
        for (int i = 0; i < 20; i++) {
            int key = RAND.nextInt(100);
            String val = Integer.toString(seq); // guarantees that the value is unique.
            seq++;
            valuesToKeys.put(val, key);
        }
        // Keep track of handles and build queue.
        for (Map.Entry<String, Integer> entry : valuesToKeys.entrySet()) {
            int key = entry.getValue();
            String value = entry.getKey();
            Handle h = q.insert(key, value);
            assertEquals(key, q.handleGetKey(h));
            assertEquals(value, q.handleGetValue(h));
            valuesToHandles.put(value, h); 
        }
        
        // Remove some of the elements.
        for (int i = 0; i < 5; i++) {
            String val = q.extractMin();
            valuesToKeys.remove(val);
            valuesToHandles.remove(val);
        }
        
        // Make sure the handles are still valid.
        for (Map.Entry<String, Integer> entry : valuesToKeys.entrySet()) {
            int key = entry.getValue();
            String value = entry.getKey();
            Handle h = valuesToHandles.get(value);
            assertEquals(value, q.handleGetValue(h));
        }
        
        // Add some more elements.
        int limit = seq + 5;
        for (int i = seq; i < limit; i++) {
            int key = RAND.nextInt(100);
            String val = Integer.toString(seq);
            seq++;
            valuesToKeys.put(val, key);
            Handle h = q.insert(key, val);
            valuesToHandles.put(val, h);
        }
        
        // Decrease keys unnecessarily and verify that they still work.
        for (Map.Entry<String, Integer> entry : valuesToKeys.entrySet()) {
            int key = entry.getValue();
            String value = entry.getKey();
            Handle h = valuesToHandles.get(value);
            q.decreaseKey(h, key + 1);
            assertEquals(key, q.handleGetKey(h));
            assertEquals(value, q.handleGetValue(h));
        }

        // Change keys randomly, and check that the values remain attached to handles.
        for (Map.Entry<String, Integer> entry : valuesToKeys.entrySet()) {
            int key = entry.getValue();
            String value = entry.getKey();
            Handle h = valuesToHandles.get(value);
            int newKey = RAND.nextInt(100);
            q.decreaseKey(h, newKey);
            if (newKey < key) {
                assertEquals(newKey, q.handleGetKey(h));
            } else {
                assertEquals(key, q.handleGetKey(h));
            }
            assertEquals(value, q.handleGetValue(h));
        }
    }
    
    @Test
    public void biggerPriorityQueueTest() {
        
        PriorityQueue<String> q = new PriorityQueue<String>();
        Map<String, Handle> valuesToHandles = new HashMap<String, Handle>();
        Map<String, Integer> valuesToKeys = new HashMap<String, Integer>();
        int seq = 0;
        // Generate random numbres for the keys.
        for (int i = seq; i < 10000; i++) {
            int key = RAND.nextInt(1000000);
            String val = Integer.toString(seq); // guarantees that the value is unique.
            seq++;
            valuesToKeys.put(val, key);
        }
        // Keep track of handles and build queue.
        for (Map.Entry<String, Integer> entry : valuesToKeys.entrySet()) {
            int key = entry.getValue();
            String value = entry.getKey();
            Handle h = q.insert(key, value);
            assertEquals(key, q.handleGetKey(h));
            assertEquals(value, q.handleGetValue(h));
            valuesToHandles.put(value, h);
        }

        // Remove some of the elements.
        for (int i = 0; i < 5000; i++) {
            String val = q.extractMin();
            valuesToKeys.remove(val);
            valuesToHandles.remove(val);
        }

        // Add some more elements.
        for (int i = seq; i < 15000; i++) {
            int key = RAND.nextInt(1000000);
            String val = Integer.toString(seq);
            seq++;
            valuesToKeys.put(val, key);
            Handle h = q.insert(key, val);
            valuesToHandles.put(val, h);
        }

        // Change keys randomly, and check that the values remain attached to handles.
        for (Map.Entry<String, Integer> entry : valuesToKeys.entrySet()) {
            int key = entry.getValue();
            String value = entry.getKey();
            Handle h = valuesToHandles.get(value);
            int newKey = RAND.nextInt(1000000);
            q.decreaseKey(h, newKey);
            if (newKey < key) {
                assertEquals(newKey, q.handleGetKey(h));
            } else {
                assertEquals(key, q.handleGetKey(h));
            }
            assertEquals(value, q.handleGetValue(h));
        }

        // Make sure the handles are still valid.
        for (Map.Entry<String, Integer> entry : valuesToKeys.entrySet()) {
            int key = entry.getValue();
            String value = entry.getKey();
            Handle h = valuesToHandles.get(value);
            assertEquals(value, q.handleGetValue(h));
        }
    }
    
    @Test
    public void trivialShortestPathsTest() {
        Multigraph g = new Multigraph();
        Vertex a = new Vertex(0);
        Vertex b = new Vertex(1);
        Vertex c = new Vertex(2);
        Vertex d = new Vertex(3);
        Edge aToB = new Edge(0, a, b, 1);
        Edge bToC = new Edge(1, b, c, 1);
        Edge cToD = new Edge(2, c, d, 1);
        Edge aToD = new Edge(3, a, d, 1);
        a.addEdge(aToB);
        a.addEdge(aToD);
        b.addEdge(bToC);
        c.addEdge(cToD);
        g.addVertex(a);
        g.addVertex(b);
        g.addVertex(c);
        g.addVertex(d);
        ShortestPaths sp = new ShortestPaths(g, 0);
        int[] one = {3};
        int[] two = {0};
        int[] three = {0, 1};
        assertArrayEquals(one, sp.returnPath(d.id()));
        assertArrayEquals(two, sp.returnPath(b.id()));
        assertArrayEquals(three, sp.returnPath(c.id()));
    }
    @Test
    public void handleTest()
    {
    PriorityQueue<String> q = new PriorityQueue<String>();
    Handle catHandle = q.insert(3, "Cat");
    Handle dogHandle = q.insert(2, "Dog");
    assertEquals("Dog", q.handleGetValue(dogHandle));
    assertEquals("Cat", q.handleGetValue(catHandle));
    q.decreaseKey(catHandle, 1);
    assertEquals("Dog", q.handleGetValue(dogHandle));
    assertEquals("Cat", q.handleGetValue(catHandle));
    }
    
    @Test
    public void lessTrivialShortestPathsTest() {
        Multigraph gr = new Multigraph();
        Vertex a = new Vertex(0);
        Vertex b = new Vertex(1);
        Vertex c = new Vertex(2);
        Vertex d = new Vertex(3);
        Vertex e = new Vertex(4);
        Vertex f = new Vertex(5);
        Vertex g = new Vertex(6);
        Vertex h = new Vertex(7);
        Vertex i = new Vertex(8);
        Vertex j = new Vertex(9);
        Vertex k = new Vertex(10);
        Vertex l = new Vertex(11);
        Vertex m = new Vertex(12);
        Vertex n = new Vertex(13);
        Vertex o = new Vertex(14);
        Vertex p = new Vertex(15);
        Vertex q = new Vertex(16);
        Vertex r = new Vertex(17);
        Edge aToB = new Edge(0, a, b, 1);
        Edge aToC = new Edge(1, a, c, 3);
        Edge aToD = new Edge(2, a, d, 2);
        Edge aToE = new Edge(3, a, e, 5);
        Edge aToF = new Edge(4, a, f, 7);
        Edge aToG = new Edge(5, a, g, 8);
        Edge aToH = new Edge(6, a, h, 6);
        Edge aToI = new Edge(7, a, i, 1);
        Edge bToQ = new Edge(23, b, q, 2);
        Edge bToJ = new Edge(8, b, j, 4);
        Edge cToJ = new Edge(9, c, j, 1);
        Edge cToK = new Edge(10, c, k, 1);
        Edge dToK = new Edge(11, d, k, 3);
        Edge dToL = new Edge(12, d, l, 5);
        Edge eToL = new Edge(13, e, l, 3);
        Edge eToM = new Edge(14, e, m, 1);
        Edge fToM = new Edge(15, f, m, 2);
        Edge fToN = new Edge(16, f, n, 9);
        Edge gToN = new Edge(17, g, n, 6);
        Edge gToO = new Edge(18, g, o, 10);
        Edge hToO = new Edge(19, h, o, 2);
        Edge hToP = new Edge(20, h, p, 1);
        Edge iToP = new Edge(21, i, p, 2);
        Edge iToQ = new Edge(22, i, q, 3);
        
        a.addEdge(aToB);
        a.addEdge(aToC);
        a.addEdge(aToD);
        a.addEdge(aToE);
        a.addEdge(aToF);
        a.addEdge(aToG);
        a.addEdge(aToH);
        a.addEdge(aToI);
        
        b.addEdge(bToQ);
        b.addEdge(bToJ);
        c.addEdge(cToJ);
        c.addEdge(cToK);
        d.addEdge(dToK);
        d.addEdge(dToL);
        e.addEdge(eToL);
        e.addEdge(eToM);
        f.addEdge(fToM);
        f.addEdge(fToN);
        g.addEdge(gToN);
        g.addEdge(gToO);
        h.addEdge(hToO);
        h.addEdge(hToP);
        i.addEdge(iToP);
        i.addEdge(iToQ);
        
    
        gr.addVertex(a);
        gr.addVertex(b);
        gr.addVertex(c);
        gr.addVertex(d);
        gr.addVertex(e);
        gr.addVertex(f);
        gr.addVertex(g);
        gr.addVertex(h);
        gr.addVertex(i);
        gr.addVertex(j);
        gr.addVertex(k);
        gr.addVertex(l);
        gr.addVertex(m);
        gr.addVertex(n);
        gr.addVertex(o);
        gr.addVertex(p);
        gr.addVertex(q);
        gr.addVertex(r);
        
        ShortestPaths sp = new ShortestPaths(gr, 0);
        int[] base = {};
        assertArrayEquals(base, sp.returnPath(a.id())); //testing for an empty set of paths for start = end
        assertArrayEquals(base, sp.returnPath(r.id())); //testing for empty set when vertex not connected to s
        
        int[] one = {0};
        int[] two = {1};
        int[] three = {2};
        int[] four = {3};
        int[] five = {4};
        int[] six = {5};
        int[] seven = {6};
        int[] eight = {7};
        int[] nine = {1,9};
        int[] ten = {1,10};
        int[] eleven = {2,12};
        int[] twelve = {3,14};
        int[] thirteen = {5,17};
        int[] fourteen = {6,19};
        int[] fifteen = {7,21};
        int[] sixteen = {0,23};
        
        assertArrayEquals(one, sp.returnPath(b.id()));
        assertArrayEquals(two, sp.returnPath(c.id()));
        assertArrayEquals(three, sp.returnPath(d.id()));
        assertArrayEquals(four, sp.returnPath(e.id()));
        assertArrayEquals(five, sp.returnPath(f.id()));
        assertArrayEquals(six, sp.returnPath(g.id()));
        assertArrayEquals(seven, sp.returnPath(h.id()));
        assertArrayEquals(eight, sp.returnPath(i.id()));
        assertArrayEquals(nine, sp.returnPath(j.id()));
        assertArrayEquals(ten, sp.returnPath(k.id()));
        assertArrayEquals(eleven, sp.returnPath(l.id()));
        assertArrayEquals(twelve, sp.returnPath(m.id()));
        assertArrayEquals(thirteen, sp.returnPath(n.id()));
        assertArrayEquals(fourteen, sp.returnPath(o.id()));
        assertArrayEquals(fifteen, sp.returnPath(p.id()));
        assertArrayEquals(sixteen, sp.returnPath(q.id()));
        
    }
    

    // THIS IS NOT AN EXHAUSTIVE TEST.  It creates a random graph and then just checks to make
    // sure that the return path is a real path.  If you create a brute force algorithm,
    // you can check this more easily.  Or you can check it by hand (though you could just use
    // the normal class for that).
    @Test
    public void randomizedSmallShortestPathsTest() {
        Multigraph g = new Multigraph();
        int maxWeight = 10;
        List<Vertex> v = new ArrayList<Vertex>();
        List<Edge> e = new ArrayList<Edge>();
        // Just your basic randomized graph algorithm.
        for (int i = 0; i < 100; i++) {
            v.add(new Vertex(i));
        }
        int seq = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (j != i && RAND.nextDouble() < 0.4) {
                	int weight = RAND.nextInt(maxWeight) + 1;
                    Edge newEdge = new Edge(seq++, v.get(i), v.get(j), weight); //CHANGE WEIGHT
                    v.get(i).addEdge(newEdge);
                    e.add(newEdge);
                }
            }
        }
        for (Vertex vertex : v) {
            g.addVertex(vertex);
        }
        ShortestPaths sp = new ShortestPaths(g, 0);
        for (int i = 1; i < 100; i++) {
            int[] returnPath = sp.returnPath(i);
            Vertex current = v.get(0);
            for (int j = 0; j < returnPath.length; j++) {
                Edge currentEdge = e.get(returnPath[j]);
                assertEquals(current, currentEdge.from());
                current = currentEdge.to();
            }
        }
    }
    public static void main(String[] args) {
    	Result result = JUnitCore.runClasses(TestLab3.class);
    	System.out.println("---------------");
    	for (Failure failure : result.getFailures()) {
      		System.out.println(failure.toString());
            System.out.println(failure.getTrace());
      	}
      	if (result.getFailures().size() == 0) {
      		System.out.println("All tests passed!!");
      	} else {
            System.out.println("SOME TESTS FAILED :(");
        }
    }
}
