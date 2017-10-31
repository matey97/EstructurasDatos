package practica1;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import org.junit.Test;


public class MiscJUnit {
	
	private static final String ArrayUtils = null;


	private Random rnd = new Random();

	private long compressData [][] = { 
			{1, 2, 3, 4, 5},
			{3, 7, 5},
			{1, 2, 3, 4, 5, 6},
			{3, 7, 11},
			{10},
			{10},
			{},
			{},
			{ -9, 9},
			{0}
	};
	
	private long[][] createRandomCompressData() {
		
		int size = rnd.nextInt(15);
		long retVal[][] = { new long[size], new long[(size +1)/2]};
		
		for (int i = 0; i < size; i++) {
			retVal[0][i] = rnd.nextLong() % 100;
			retVal[1][i/2] += retVal[0][i]; 
		}
		
		return retVal;
	}
	
	@Test
	public void compressTest() {
		System.out.println("\nTesting compress...");
		for(int i = 0; i < compressData.length; i += 2) 
		{
			System.out.print(Arrays.toString(compressData[i]));
			long result[] = Misc.compress(compressData[i]);
			System.out.println(" -> " + Arrays.toString(result));

			assertArrayEquals(compressData[i+1], result);
		}
		
		
	}
	
	@Test 
	public void compressRandomTest() {
		System.out.println("\nTesting randomly generated arrays...");
		for(int i = 0; i < 10; i++) 
		{
			long data[][] = createRandomCompressData();
			
			System.out.print(Arrays.toString(data[0]));
			long result[] = Misc.compress(data[0]);
			System.out.println(" -> " + Arrays.toString(result));

			assertArrayEquals(data[1], result);
		}
	}
	
	@Test (expected = InvalidParameterException.class)
	public void compressTestExcpetion()
	{
		Misc.compress(null);
	}
	
	//*****************************************
	
	
	
	private String splitData [] = { 
			"primer caso de frase",
			" segundo caso  de frase",
			"tercer caso de   frtase ",
			" cuarto ",
			"",
			"  ",
			"septimo,caso", 
			null,
	};
	
	private String createRandomSplitData() {
		StringBuilder str = new StringBuilder();
		
		int spaces = rnd.nextInt(3);
		for (int i= 0; i < spaces; i++)
			str.append(' ');
		
		int nWords = rnd.nextInt(10);
		for (int j = 0; j < nWords; j++) {
			str.append(Integer.toString((rnd.nextInt(10000))));
			
			spaces = rnd.nextInt(3);
			for (int i= 0; i < spaces; i++)
				str.append(' ');
		}
		
		return str.toString();
	}
	
	private String toStringVector(String[] v) {
		
		if (v == null)
			return "null";
		
		StringBuilder str = new StringBuilder("[");
		
		for (int i = 0; i < v.length-1;i++) {
			str.append('\"');
			str.append(v[i]);
			str.append("\", ");
		}
		if (v.length > 0) {
			str.append('\"');
			str.append(v[v.length-1]);
			str.append('\"');
		}
		str.append(']');
		return str.toString();
	}
	
	@Test
	public void splitTest() {
		System.out.println("\nTesting split...");
		for (String str: splitData) {
			System.out.print("\"" + str + "\" -> ");
			String[] results = Misc.split(str);
			System.out.println(toStringVector(results));
			String[] expected = null;
			if (str != null) 
				expected = str.trim().split("\\s+");
			else 	
				expected = new String[0];
			if (expected.length == 1 && expected[0].length() == 0)
				expected = new String[0];
			
			System.out.println("Expected: " + toStringVector(expected));
			assertArrayEquals(expected , results);
		}
	}	

	@Test
	public void splitRandomText() {
		System.out.println("\nTesting split randomly...");
		
		for (int i=0; i < 20; i++) {
			String str = createRandomSplitData();
			System.out.print("\"" + str + "\" -> ");
			String[] results = Misc.split(str);
			System.out.println(toStringVector(results));
			String [] expected = str.trim().split("\\s+");
			if (expected.length == 1 && expected[0].length() == 0)
				expected = new String[0];

			assertArrayEquals(expected , results);
		}
	}

	//*****************************************

	private int joinData[][] = {
			{4, 6, 2, 1}, { 9, 5, 6, 2, 0, 1}, { 0, 1, 2, 4, 5, 6, 9},
			{ 1, 2, 3, 4, 5 }, {1, 2, 3, 4, 5}, {1 ,2, 3, 4, 5 },
			{ 2, 2, 1, 1, }, {}, { 1, 2 },
			{}, {}, {},
			{5, 3, 4, 5, 3}, {}, {3, 4, 5},
			{}, {5, 3, 4, 5, 3}, {3, 4, 5},
			{ 1, 1, 1, 1}, {1, 1}, {1},
			null, null, {},
			null, {1, 2}, {1, 2},
			{1, 2}, null, {1, 2} 
		};
	
	
	private List<Integer> createRandomJoinData() {
		List<Integer> retVal = new ArrayList<>();
		
		int nelem = rnd.nextInt(10);
		
		for (int i=0; i < nelem; i++) 
			retVal.add(rnd.nextInt(1000));
		
		return retVal;
	}
	
	private <T> void assertEqualsCollection(Collection<T> expected, Collection<T> value) {
		Iterator<T> iter1 = expected.iterator();
		Iterator<T> iter2 = value.iterator();
				
		assertEquals(expected.size(), value.size());
		
		while (iter1.hasNext()) 
			assertEquals(iter1.next(), iter2.next());
	}
	
	private List<Integer> toList(int v[]) {
		List<Integer> retVal = new ArrayList<Integer>();
		
		if (v != null)
			for (int i: v) retVal.add(i);
		
		return retVal;
	}
	
	@Test
	public void joinTest() {
		System.out.println("\nTesting  join...");

		for (int i= 0; i < joinData.length; i += 3) {
			List<Integer> l1 = toList(joinData[i]);
			List<Integer> l2 = toList(joinData[i+1]);
			System.out.print(l1.toString() +  " + " + l2.toString());
			List<Integer> result = Misc.join(l1, l2);
			System.out.println(" -> " + result.toString());
			
			TreeSet<Integer> expected = new TreeSet<Integer>();
			expected.addAll(l1);
			expected.addAll(l2);
			
			assertEqualsCollection(expected, result);		
		}
	}
	
	@Test
	public void joinRandomTest() {
		System.out.println("\nTesting join randomly...");
		
		for (int i= 0; i < joinData.length; i += 3) {
			List<Integer> l1 = createRandomJoinData();
			List<Integer> l2 = createRandomJoinData();
			System.out.print(l1.toString() +  " + " + l2.toString());
			List<Integer> result = Misc.join(l1, l2);
			System.out.println(" -> " + result.toString());

			TreeSet<Integer> expected = new TreeSet<Integer>();
			expected.addAll(l1);
			expected.addAll(l2);
			
			assertEqualsCollection(expected, result);		
		}
	}
	
	
	@Test(expected = NullPointerException.class)
	public void joinExceptionTest() {
		System.out.println("\nTesting join exception...");
		List<Integer> l1 = toList(joinData[0]);
		List<Integer> l2 =  toList(joinData[0]);
		l2.add(null);
		
		Misc.join(l1,l2);
		Misc.join(l2,l1);
		
	}
	
	//*****************************************
	private String[][] equivalentTestData  = {
			{"a", "b", "c"}, {"b", "c", "a"},
			{"a", "b", "c"}, {"a", "b", "c", "d"},
			{"d", "a", "b", "c"}, {"a", "b", "c"},
			{"d", "a", "b", "c"}, {"a", "b", "c", "c"},
			
			{"b", "a", "b", "c"}, {"a", "b", "c", "c"},
			{"a", "a", "b", }, {"b", "b", "a"},
			{}, {},
			{"a"}, {},
			
			{}, {""},
			null, null,
			null, {},
			{}, null,
			
			{"a", null, "b"}, {"b", null, "a"},
			{"a", null, "a"}, {null, "a", null}
			};
	
	
	private boolean[] equivalentTestDataExpected = {
		true, false, false, false,
		false, false, true, false,
		false, true, false, false,
		true, false
	};
	
	@Test
	public void equivalenteTest() {
		System.out.println("\nTesting  equivalents...");

		for (int i = 0; i < equivalentTestData.length; i += 2)	{
			List<String> l1 = (equivalentTestData[i] == null) ? null : Arrays.asList(equivalentTestData[i]); 
			List<String> l2 = (equivalentTestData[i+1] == null) ? null : Arrays.asList(equivalentTestData[i+1]);
			boolean result = Misc.equivalent(l1, l2);
	
			System.out.println(toStringVector(equivalentTestData[i]) + " * " + 
							   toStringVector(equivalentTestData[i+1]) + " -> " + result);
			assertEquals(equivalentTestDataExpected[i/2], result);
		}
	}
}
