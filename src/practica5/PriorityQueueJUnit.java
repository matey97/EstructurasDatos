package practica5;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;


public class PriorityQueueJUnit {
	private static final int DEFAULT_CAPACITY = 30;
	private static Random generator;
	
	/**
	 * Crea una lista de enteros vacía
	 */
	private EDPriorityQueue<Integer> createQueue()  {
		return  new EDPriorityQueue<Integer>();
	}
	
	/**
	 * Crea y rellena una lista de enteros
	 */
	private EDPriorityQueue<Integer> createFillQueue1() {
		EDPriorityQueue<Integer> l = createQueue();
		for (int i=0; i<DEFAULT_CAPACITY; i++) {
			int elem = generator.nextInt(i+1);
			l.offer(elem);
		}
		return l;
	}
	
	private EDPriorityQueue<Integer> createFillQueue2() {
		EDPriorityQueue<Integer> l = createQueue();
		for (int i=DEFAULT_CAPACITY; i>0; i--)
			l.offer(i);
		return l;
	}
	
	private boolean checkStructure(EDPriorityQueue<Integer> q) {
		if (q.size() ==0 || q.size()==1) return true;
		boolean ok=true;
		Object [] v = q.toArray();

		int i=0;
		while (i<q.size()/2 && ok) {
			int l=2*i+1;
			if ((Integer) v[i] <= (Integer) v[l]) {
				int d=l+1;
				if (d<q.size()) {
					if ((Integer) v[i]> (Integer) v[d]) {  ok=false;}
				}
			}
			else {ok=false;}
			i++;
		}
		return ok;
	}
	
	private boolean checkMin(EDPriorityQueue<Integer> q) throws NoSuchElementException {
		
		if (q.isEmpty()) throw new NoSuchElementException();
		
		Object [] v =  q.toArray();
		
		if (q.size() > 1) {		
			int min = (Integer) v[0];		
			for (int i=1; i<q.size(); i++) {
				if ((Integer) v[i]<min) {
					min=(Integer) v[i];
				}
					
			}
			return (min == (Integer) v[0]);
		}
		return true;
	}
	
	private String toString (Object[] objects, int n) {
		StringBuilder s = new StringBuilder();
		s.append("[");
		for (int i=0; i<n; i++) {
			s.append(objects[i]);
			s.append(" ");
		}
		s.append("]");
		return s.toString();
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		generator = new Random();
	}

	
	@Test
	public void testConstructor() {
		System.out.println("\nValidando constructor por defecto...\n");
		EDPriorityQueue<Integer> l = createQueue();
		
		
		System.out.println("queue: "+ this.toString(l.toArray(),l.size()));
		System.out.println("size "+l.size());
		assertEquals("Size:", 0, l.size());
		assertTrue("isEmpty: ", l.isEmpty());
	}

	@Test
	public void testOffer() {
		System.out.println("\nValidando metodo offer(T)...\n");
		EDPriorityQueue<Integer> q = createQueue();
		PriorityQueue<Integer> q2 = new PriorityQueue<Integer>();
		int count=0;
		
		assertEquals(q.size(),count);
		
		for (int i = 0; i< DEFAULT_CAPACITY; i ++) {
			int elem = generator.nextInt(i+1);
			q.offer(elem);
			q2.offer(elem);
			System.out.println("offer ... "+elem);
			System.out.println("queue: "+ this.toString(q.toArray(),q.size()));
			System.out.println("size "+q.size());
			assertFalse("offer + empty",  q.isEmpty());
			assertEquals("offer + size", i+1 ,q.size());
			assertEquals("tama�o ...", q.size(),q2.size());
			assertTrue("testStructure", this.checkStructure(q));
			assertTrue("testMin", this.checkMin(q));
		}
		
		Object[] v = q2.toArray();
		Object[] w = q.toArray();
		Object[] v2 = Arrays.copyOfRange(v,0,q.size());
		Object[] w2 = Arrays.copyOfRange(w,0,q.size());
		
		/*System.out.print("v2: [");
		for (int i=0; i<v2.length; i++) System.out.print(v2[i]+" ");
		System.out.println("]");
		System.out.print("w2: [");
		for (int i=0; i<w2.length; i++) System.out.print(w2[i]+" ");
		System.out.println("]");*/
		
		assertArrayEquals("vectores .. ",v2,w2);
		
		while (!q2.isEmpty()) {
			assertEquals(q2.element(), q.element());
			q2.remove();
			q.remove();
			System.out.println("borrando ...");
			System.out.println("queue: "+ this.toString(q.toArray(),q.size()));
			assertEquals(q2.size(),q.size());
		}
		
		assertTrue("vacia ", q.isEmpty());
		assertEquals(q.size(),count);
		System.out.println("cola vac�a ...");
		
		for (int i = DEFAULT_CAPACITY; i> 0; i--) {
			q.offer(i);
			q2.offer(i);
			System.out.println("offer ... "+i);
			System.out.println("queue: "+ this.toString(q.toArray(),q.size()));
			System.out.println("size "+q.size());
			
			assertFalse("offer + empty", q.isEmpty());
			assertEquals("offer + size", DEFAULT_CAPACITY-i+1 ,q.size());
			assertEquals(q.size(),q2.size());
			assertTrue("testStructure", this.checkStructure(q));
			assertTrue("testMin", this.checkMin(q));
			
		}
		
		v = q2.toArray();
		w = q.toArray();
		v2 = Arrays.copyOfRange(v,0,q.size());
		w2 = Arrays.copyOfRange(w,0,q.size());
		assertArrayEquals("vectores", v2,w2);
		
		while (!q2.isEmpty()) {
			assertEquals(q2.element(), q.element());
			q2.remove();
			q.remove();
			System.out.println("borrando ...");
			System.out.println("queue: "+ this.toString(q.toArray(),q.size()));
			assertEquals(q2.size(),q.size());
		}
		assertTrue("vacia ", q.isEmpty());
		assertEquals(count,q.size());
	}
	
	@Test
	public void testElementAndRemove() {
		System.out.println("\nValidando metodos element() y remove() ...\n");
		
		EDPriorityQueue<Integer> q = createFillQueue1();
		int size = q.size();
		
		System.out.println("queue: "+ this.toString(q.toArray(),q.size()));
		
		assertFalse("no vacia .. ", q.isEmpty());
		
		while (!q.isEmpty()) {
			//System.out.println("Element ... "+q.element());
			System.out.println("borrando ... "+q.element());
			Object[] v = q.toArray();
			assertEquals((Integer) v[0], q.element());
			q.remove();
			size--;
			assertEquals("size ", size, q.size() );
			
			
			System.out.println("queue: "+ this.toString(q.toArray(),q.size()));
			
			if (q.size()>0) assertTrue("testMin", this.checkMin(q));
			assertTrue("testStructure", this.checkStructure(q));
		}
		
		System.out.println("Cola vac�a?");
		assertTrue(q.isEmpty());
		assertEquals(q.size(),0);
		
		q = createFillQueue2();
		size = q.size();
		System.out.println("queue: "+ this.toString(q.toArray(),q.size()));
		assertFalse("no vacia .. ", q.isEmpty());
		
		while (!q.isEmpty()) {
			Object[] v = q.toArray();
			
			int elem = q.element();
			assertEquals((Integer) v[0], q.element());
			System.out.println("borrando ... "+q.element());
			
			q.remove();
			size--;
			assertEquals("size ", size, q.size() );
			
			System.out.println("queue: "+ this.toString(q.toArray(),q.size()));
			if (q.size()>0) {
				
				assertFalse((q.element()).equals((Integer) v[0]) );
				assertTrue("testMin", this.checkMin(q));
			}
			assertTrue("testStructure", this.checkStructure(q));
		}
		
		assertTrue(q.isEmpty());
		assertEquals(q.size(),0);
		
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testElementException() {
		System.out.println("\nValidando excpeciones del metodo Element...\n");
		System.out.println("Element con cola vac�a ...");
		EDPriorityQueue<Integer> q= new EDPriorityQueue<Integer>();
		assertTrue(q.isEmpty());
		q.element();
		System.out.println("...excepcion no producida");
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testRemoveException() {
		System.out.println("\nValidando excpeciones del metodo Remove...\n");
		System.out.println("Remove con cola vac�a ...");
		EDPriorityQueue<Integer> q= new EDPriorityQueue<Integer>();
		assertTrue(q.isEmpty());
		q.remove();
		System.out.println("...excepcion no producida");
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testRemoveElementExceptionEmpty() {
		System.out.println("\nValidando excpeciones del metodo Remove(T ..) ...\n");
		System.out.println("Remove(x) con cola vac�a ...");
		EDPriorityQueue<Integer> q= new EDPriorityQueue<Integer>();
		assertTrue(q.isEmpty());
		int x=5;
		q.remove(x);
		System.out.println("...excepcion no producida");
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testRemoveElementException() {
		System.out.println("\nValidando excpeciones del metodo Remove(T ..) ...\n");
		System.out.println("Remove(x), x no est� ...");
		EDPriorityQueue<Integer> q= createFillQueue2();
		assertFalse(q.isEmpty());
		int size=q.size();
		q.remove(size*2);
		System.out.println("...excepcion no producida");
	}
	
	
	@Test
	public void testRemoveElement() {
		System.out.println("\nValidando  metodo remove(T ...)...\n");

		EDPriorityQueue<Integer> q1= new EDPriorityQueue<Integer>();
		PriorityQueue<Integer> q2 = new PriorityQueue<Integer>();
		for (int i=DEFAULT_CAPACITY; i>0; i--) {
			q1.offer(i);
			q2.offer(i);
		}
		System.out.println(this.toString(q1.toArray(),q1.size()));
		System.out.println("remove(primero) ...");
		assertEquals(q1.element(),q2.element());
		int size =q1.size();
		int x =q1.element();
		q1.remove(x);
		q2.remove(x);
		System.out.println(this.toString(q1.toArray(),q1.size()));
		assertEquals(size-1, q1.size());
		assertEquals(q1.size(),q2.size());
		this.checkMin(q1);
		this.checkStructure(q1);
		Object[] v = q1.toArray();
		Object[] w = q2.toArray();
		Object[] v2 = Arrays.copyOfRange(v,0,q1.size());
		Object[] w2 = Arrays.copyOfRange(w,0,q1.size());
		assertArrayEquals(v2,w2);
		
		System.out.println("remvove(ultimo) ....");
		size =q1.size();
		v=q1.toArray();
		int ultimo=(Integer) v[size-1];
		q1.remove(ultimo);
		q2.remove(ultimo);
		System.out.println(this.toString(q1.toArray(),q1.size()));
		
		assertEquals(size-1, q1.size());
		assertEquals(q1.size(),q2.size());
		this.checkMin(q1);
		this.checkStructure(q1);
		v = q1.toArray();
		w = q2.toArray();
		v2 = Arrays.copyOfRange(v,0,q1.size());
		w2 = Arrays.copyOfRange(w,0,q1.size());
		assertArrayEquals(v2,w2);
		
		
		size =q1.size();
		v=q1.toArray();
		int elem=(Integer) v[5];
		System.out.println("remove (cualquier otro) ...: "+elem);
		//Object[] extract1 = Arrays.copyOfRange(v, 0, 4);
		q1.remove(elem);
		q2.remove(elem);
		System.out.println(this.toString(q1.toArray(),q1.size()));
		
		assertEquals(size-1, q1.size());
		assertEquals(q1.size(),q2.size());
		this.checkMin(q1);
		this.checkStructure(q1);
		v = q1.toArray();
		w = q2.toArray();
		v2 = Arrays.copyOfRange(v,0,q1.size());
		w2 = Arrays.copyOfRange(w,0,q1.size());
		assertArrayEquals(v2,w2);
		
	}
	
	
	
	@Test
	public void testGrow() {
		System.out.println("\nValidando metodo grow...\n");

		EDPriorityQueue<Integer> q = this.createQueue();
		Object[] v = q.toArray();
		int capacity = v.length;
		int size =0;
		for (int i = 0; i< DEFAULT_CAPACITY; i ++) {
			int elem = generator.nextInt(i+1);
			q.offer(elem);
			if (size == capacity) {
				v = q.toArray();
				System.out.println("capacidad vector ... "+v.length);
				assertEquals(v.length,2*capacity);
			}
			size++;
		}
	}
	
	@Test
	public void testIndexOf() {
		System.out.println("\nValidando metodo IndexOf(T)...\n");

		EDPriorityQueue<Integer> q = this.createQueue();
		int size =0;
		assertEquals("con cola vac�a ...", -1, q.indexOf(size));
		
		for (int i = 0; i< DEFAULT_CAPACITY; i ++) {
			int elem = generator.nextInt(i+1);
			q.offer(elem);
			size++;
		}
		
		Object[] v = q.toArray();
		System.out.println(this.toString(q.toArray(),q.size()));
		System.out.println("buscando ... "+v[0]);
		assertEquals("primero ... ", 0, q.indexOf((Integer) v[0]));
		
		
		q = this.createFillQueue2();
		v = q.toArray();
		System.out.println(this.toString(q.toArray(),q.size()));
		System.out.println("buscando ... "+v[DEFAULT_CAPACITY-1]);
		assertEquals("ultimo ... ", DEFAULT_CAPACITY-1, q.indexOf((Integer) v[DEFAULT_CAPACITY-1]));
		
		int centro = DEFAULT_CAPACITY/2;
		
		System.out.println("buscando ... "+v[centro]);
		assertEquals("centro ... ", centro, q.indexOf((Integer) v[centro]));
		
		int x = 999;
		System.out.println("buscando ... "+x);
		assertEquals("no esta ... ",-1,q.indexOf(x));
	}
	
	@Test
	public void testHeapify() {
		System.out.println("\nValidando metodo heapify...\n");
		List<Integer> l = new LinkedList<Integer>();
		
		EDPriorityQueue<Integer> q = new EDPriorityQueue<Integer>(l);
		assertTrue("sin elementos ...",q.isEmpty());
		
		for (int i=0; i<DEFAULT_CAPACITY; i++) {
			int elem = generator.nextInt(i+1);
			l.add(elem);
		}
			
		System.out.println("colecci�n inicial: "+l.toString());
		q = new EDPriorityQueue<Integer>(l);
		System.out.println("monticulo: "+this.toString(q.toArray(),q.size()));
		assertTrue("con elementos ...", this.checkMin(q));
		assertTrue(this.checkStructure(q));
		
	}
	
	
	
}
