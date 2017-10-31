package practica3;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;


public class ListJUnit {
	private static Random generator;
	
	/**
	 * Crea una lista de enteros vacía
	 */
	private List<Integer> createList()  {
		return  new EDHeaderDoubleLinkedList2<Integer>();
	}
	
	/**
	 * Crea y rellena una lista de enteros
	 */
	private List<Integer> createFillList() {
		List<Integer> l = createList();
		for (int i=0; i<20; i++)
			l.add(i);
		return l;
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		generator = new Random();
	}

	
	@Test
	public void testConstructor() {
		System.out.println("\nValidando constructor por defecto...\n");
		List<Integer> l = createList();
		
		System.out.println("-> " + l);
		assertEquals("Size:", 0, l.size());
		assertEquals("isEmpty: ", true, l.isEmpty());
	}

	
	@Test
	public void testAdd() {
		System.out.println("\nValidando metodo add(T)...\n");
		List<Integer> l = createList();
		
		System.out.println("inicial -> " + l);
		for (int i = 0; i< 20; i++) {
			assertEquals("add" + i , true, l.add(i));
			System.out.println("add(" + i + ") -> " + l);
			assertEquals("add + empty", false, l.isEmpty());
			assertEquals("add + size", i+1 ,l.size());
		}
	}
	
	@Test
	public void testClear() {
		System.out.println("\nValidando metodo clear...\n");

		List<Integer> l = createFillList();
		System.out.println("inicial -> " + l);
		l.clear();
		System.out.println("despues -> " + l);
		assertEquals("clear + size:", 0, l.size());
		assertEquals("clear + isEmpty: ", true, l.isEmpty());
	}
	
	@Test
	public void testGet() {
		System.out.println("\nValidando metodo get...\n");
		List<Integer> l = createFillList();
		System.out.println("inicial -> " + l);

		for (int i=0; i < 20; i++) {
			System.out.println("get(" + i +") ->"+ l.get(i)   );
			assertEquals("get(" + i +")", i, (int)l.get(i));
			
		}
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetExceptionDown() {
		System.out.println("\nValidando excpeciones del metodo get...\n");
		List<Integer> l = createFillList();
		System.out.println("inicial -> " + l);
		System.out.println("l.get(-1)");
		l.get(-1);
		System.out.println("...excepcion no producida");
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetExceptionUp() {
		System.out.println("\nValidando excepciones del metodo get...\n");
		List<Integer> l = createFillList();
		System.out.println("inicial -> " + l);
		
		System.out.println("l.get(20)");
		l.get(20);
		System.out.println("...excepcion no producida");

	}
	
	@Test
	public void testAddInt() {
		System.out.println("\nValidando  metodo add(int, T)...\n");

		List<Integer> l = createList();
		System.out.println("inicial -> " + l);
		
		List<Integer> truth = new LinkedList<Integer>();
		for (int i = 0; i< 20; i++) {
			int pos = generator.nextInt(i+1);
			System.out.print("add("+ pos +", "+ i +")");
			l.add(pos, i);
			System.out.println(" -> " + l);
			
			assertEquals("add + empty", false, l.isEmpty());
			assertEquals("add + size", i+1 ,l.size());
			truth.add(pos, i);
			assertEquals("add + truth", truth.get(pos), l.get(pos));
		}
		
		for(int i=0; i < l.size(); i++)
			assertEquals("add + truth", truth.get(i), l.get(i));
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testAddExceptionDown() {
		System.out.println("\nValidando excepciones del metodo add(int, T)...\n");

		List<Integer> l = createFillList();
		System.out.println("inicial -> " + l);

		System.out.println("l.add(-1, 0)");
		l.add(-1, 0);
		System.out.println("...excepcion no producida");

	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testAddExceptionUp() {
		System.out.println("\nValidando excepciones del metodo add...\n");
		List<Integer> l = createFillList();
		System.out.println("inicial -> " + l);

		System.out.println("l.add(21, 0)");
		l.add(21, 0);
		System.out.println("...excepcion no producida");

	}
	
	@Test
	public void testContains() {
		System.out.println("\nValidando metodo contains...\n");

		List<Integer> l = createList();
		
		for (int i = 0; i< 20; i ++) {
			int pos = generator.nextInt(i+1); 
			l.add(pos, i*2);
		}
		System.out.println("inicial -> " + l);
		
		for(int i=0; i < 20; i++) {
			System.out.println("contains(" + i + ") -> " + l.contains(i));
			if (i%2 == 0)
				assertEquals("contains", true, l.contains(i));
			else
				assertEquals("contains", false, l.contains(i)); 
		}
	}
	
	@Test
	public void testRemove() {
		System.out.println("\nValidando metodo remove(T)...\n");

		List<Integer> l = createList();

		for (int i = 0; i< 20; i ++) {
			int pos = generator.nextInt(i+1); 
			l.add(pos, i);
		}
		System.out.println("inicial -> " + l);
		
		for(int i=0; i < 20; i += 2) {
			System.out.print("remove(" + i + ")");
			assertEquals("remove("+i+")", true, l.remove((Integer)i));
			System.out.println(" -> " + l);
			assertEquals("remove + contains", false, l.contains(i));
			assertEquals("remove + size", 20-(i/2)-1, l.size());
		}
		
		for(int i=0; i < 20; i += 2) {
			System.out.println("contains(" + i + ") -> " + l.contains(i));

			assertEquals("contains", true, l.contains(i+1));
		}
	}
	
	@Test
	public void testRemoveInt() {
		System.out.println("\nValidando metodo remove(int)...\n");
		List<Integer> l = createList();
		System.out.println("inicial -> " + l);

		List<Integer> truth = new LinkedList<Integer>();
		for (int i = 0; i< 20; i ++) {
			int pos = generator.nextInt(i+1);
			System.out.print("add(" + pos + ", " + i +")");
			l.add(pos, i);
			System.out.println(" -> " + l);
			truth.add(pos,i);
		}
		
		for(int i=0; i < 10; i++) {
			int pos = generator.nextInt(l.size()); 
			int el = l.get(pos);
			System.out.print("remove(" + pos + ")");
			assertEquals("remove("+i+")", el, (int)l.remove(pos));
			System.out.println(" -> " + l);
			assertEquals("remove + contains", false, l.contains(el));
			assertEquals("remove + size", 20-i-1, l.size());
			truth.remove(pos);
		}
		
		for(int i: truth) {
			System.out.println("contains(" + i + ") -> " + l.contains(i));
			assertEquals("contains", true, l.contains(i));
		}	
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveExceptionDown() {
		System.out.println("\nValidando excepciones del metodo remove(int)...\n");

		List<Integer> l = createFillList();
		System.out.println("inicial -> " + l);

		System.out.println("l.remove(-1)");
		l.remove(-1);
		System.out.println("...excepcion no producida");

	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveExceptionUp() {
		System.out.println("\nValidando excepciones del metodo remove(int)...\n");
		List<Integer> l = createFillList();
		System.out.println("inicial -> " + l);


		System.out.println("l.remove(-1)");
		l.remove(21);
		System.out.println("...excepcion no producida");
	}
	
	@Test
	public void testSet() {
		System.out.println("\nValidando  metodo remove(int)...\n");
		List<Integer> l = createList();
		
		for (int i = 0; i< 20; i ++) {
			int pos = generator.nextInt(i+1); 
			l.add(pos, i);
		}			
		System.out.println("inicial -> " + l);
		
		for(int i=0; i < 20; i++) {
			int pos = generator.nextInt(l.size()); 
			int el = l.get(pos);
			System.out.print("set(" + pos + ", " + (el+1000) + ")");
			assertEquals("set", el, (int)l.set(pos, el+1000));
			System.out.println(" -> "+ l);
			assertEquals("set + get", el+1000, (int)l.get(pos));
			assertEquals("set + contains", false, l.contains(el));			
			assertEquals("set + size", 20, l.size());
		}
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetExceptionDown() {
		System.out.println("\nValidando excepciones del metodo Set(int, T)...\n");
		List<Integer> l = createFillList();
		System.out.println("inicial -> " + l);


		System.out.println("l.set(-1)");
		l.set(-1, 0);
		System.out.println("...excepcion no producida");

	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetExceptionUp() {
		System.out.println("\nValidando excepciones del metodo Set(int, T)...\n");
		List<Integer> l = createFillList();
		System.out.println("inicial -> " + l);

		System.out.println("l.set(20)");
		l.set(20, 0);
		System.out.println("...excepcion no producida");

	}
	
	@Test
	public void testIndexOf() {
		System.out.println("\nValidando metodo indeoxOf(T)...\n");
		List<Integer> l = createFillList();
		System.out.println("inicial -> " + l);
		
		for(int i=-10; i < 30 ; i++) {
			System.out.println("indexOf(" + i + ") -> " + l.indexOf(i));
			if (i >= 0 && i < 20) {
				assertEquals("indexOf("+i+")", i, l.indexOf(i));
			} else {
				assertEquals("indexOf("+i+")", -1, l.indexOf(i));
			}
	
		}
	}
	
	@Test
	public void testAddAll() {
		System.out.println("\nValidando metodo AddAll(Collection)...\n");
		List<Integer> l = createList();
		System.out.println("inicial -> " + l);

		List<Integer> set = new LinkedList<Integer>();
		
		for (int i = 0; i < 20; i++)
			set.add(i);
		
		System.out.print("AddAll(" + set + ")");
		assertEquals("addAll(set)", true, l.addAll(set));
		System.out.println(" -> " + l);
		assertEquals("addAll + size", 20, l.size());
		
		for (int i = 0; i < l.size(); i++)
			assertEquals("addAll + get ", (int)l.get(i), i);
		
	}

	@Test
	public void testAddAllIndex() {
		List<Integer> l = createList();

		List<Integer> set = new LinkedList<Integer>();
		
		for (int i = 0; i < 100; i++)
			set.add(i);
		
		assertEquals("addAll(0, set)", true, l.addAll(0,set));
		assertEquals("addAll + size", 100, l.size());
		
		for (int i = 0; i < l.size(); i++)
			assertEquals("addAllint + get ", (int)l.get(i), i);
		
		set.clear();
		for (int i = 100; i < 200; i++)
			set.add(i);
			
		assertEquals("addAll(50, set)", true, l.addAll(50, set));
		assertEquals("addAll + size", 200, l.size());
		
		for (int i = 0; i < 50; i++)
			assertEquals("addAllint + get (II)", (int)l.get(i), i);
		
		for (int i = 50; i <150 ; i++)
			assertEquals("addAllint + get (III)", (int)l.get(i), i+50);
		
		for (int i = 150; i <200 ; i++) {
			assertEquals("addAllint + get (IV)", (int)l.get(i), i-100);
		}
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testAddAllDown() {
		List<Integer> l = createList();

		List<Integer> set = new LinkedList<Integer>();
		set.add(10);
		
		for (int i = 0; i < 100; i++)
			l.add(i);
		
		l.addAll(-1, set);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testAddAllUP() {
		List<Integer> l = createList();

		List<Integer> set = new LinkedList<Integer>();
		set.add(10);
		
		for (int i = 0; i < 100; i++)
			l.add(i);
		
		l.addAll(101, set);
	}
	
	@Test
	public void testContainsAll() {
		Collection<Integer> set = new HashSet<Integer>();
		List<Integer> l = createList();
		
		for (int i = 0; i < 100; i++)
			l.add(i);
		
        for(int i = 0; i < 150; i+=2) {
        	set.add(i);
        	boolean yet = (i < 100);
        	assertEquals("containsAll (I)", yet, l.containsAll(set));
        }    
	}
	
	@Test
	public void testRemoveAll() {
		Collection<Integer> set = new HashSet<Integer>();
		List<Integer> l = createList();
		
		for (int i = 0; i < 100; i++)
			l.add(i);
		
		
        for(int i = 0; i < 150; i+=2)
        	set.add(i);
        
        assertEquals("removeAll(I)", true, l.removeAll(set));
        assertEquals("removeAll(I) + size", 50 , l.size());
        for (int i=0; i < 100; i++)
        	assertEquals("removeAll (I) + contains", i%2==1, l.contains(i));
        
        assertEquals("removeAll(II)", false, l.removeAll(set));
        assertEquals("removeAll(II) + size", 50 , l.size());
	}
	
	@Test
	public void testRetainAll() {
		Collection<Integer> set = new HashSet<Integer>();
		List<Integer> l = createList();

		
		for (int i = 0; i < 100; i++)
			l.add(i);
		
		
        for(int i = 0; i < 150; i+=2)
        	set.add(i);
        
        assertEquals("retainAll(I)", true, l.retainAll(set));
        assertEquals("retainAll(I) + size", 50 , l.size());
        
        for (int i=0; i < 50; i++)
        	assertEquals("retainAll (I) + get", i*2, (int)l.get(i));

 
	}
}
