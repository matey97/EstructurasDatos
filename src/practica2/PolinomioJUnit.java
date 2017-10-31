package practica2;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class PolinomioJUnit {

	static void testOrdenyCeros(List<Monomio> polinomio) {
		int exp = -1;
		assertNotNull(polinomio);
		
		for (Monomio mono: polinomio) {
			assertFalse("Monomio con coeficietne cero", Cero.esCero(mono.coeficiente));
			assertTrue("Monomios desordenados", exp < mono.exponente);
			exp = mono.exponente;
		}
	}
	
	static void testComparacion(double v[], List<Monomio> poli) {
		
		assertNotNull(poli);
		assertTrue("Demasiados monomios", v.length >= poli.size());
		
		Iterator<Monomio> iter = poli.iterator();
		for (int exp=0; exp < v.length; exp++) {
			if (!Cero.esCero(v[exp])) {
				assertTrue("Faltan monomios", iter.hasNext());
				Monomio mono = iter.next();
				
				assertEquals("Exponentes distintos", exp, mono.exponente);
				assertTrue("Coeficientes distintos", Cero.doubleEquals(v[exp], mono.coeficiente));
			}
		}
		
		assertFalse(iter.hasNext());
			
	}
	
	static void testdeepCopy(List<Monomio> poli1, List<Monomio> poli2) {
		assertTrue( poli1 != null && poli2 != null);
		assertEquals("Polinomios de distonto tamaño", poli1.size(), poli2.size());
		Iterator<Monomio> iter1 = poli1.iterator();
		Iterator<Monomio> iter2 = poli2.iterator();
		
		while (iter1.hasNext())
			assertFalse("Monomios son el mismo objecto", iter1.next() == iter2.next());
	}
	
	static double [] sumaVectores(double v1[], double v2[]) {
		double result [] = new double[Math.max(v1.length, v2.length)];
		
		for (int i=0 ; i < v1.length; i++)
			result[i] = v1[i];
		
		for (int i=0 ; i < v2.length; i++)
			result[i] += v2[i];
		
		return result;
	}
	
	static double[] multiplicaMonomio(double v[], Monomio mono) {
		double result[] = new double[v.length + mono.exponente];
		
		for (int i = 0; i < v.length; i++)
			result[i + mono.exponente] = v[i]*mono.coeficiente;
		
		return result;
	}
	
	static double poliData1[][] =  {
		{},
		{1,2,3,4},
		{1,0,-2},
		{0,0,0,0},
		{0,0,0,0, -5},
		{0, 3, 0, 0},
		{6, 0, 0, 0},
		{1, -1, 1, -1},
		{0.00000000001, 0.000000001, -0.0000000001}
	};
	
	@Test
	public void testConstructorDefault()
	{
		Polinomio poli = null;
		System.out.println("\nValidando constructor por defecto...");
		poli = new Polinomio();
		
		System.out.println("Resultado :" + poli);
		assertEquals(0, poli.monomios().size());		
	}
	
	@Test
	public void testConstructorVector() {
		System.out.println("\nValidando constructor a partir de vector...");

		for (double[] v: poliData1) {
			System.out.print(Arrays.toString(v));
			Polinomio poli = new Polinomio(v);
			System.out.println(" -> " + poli);	
			
			testOrdenyCeros(poli.monomios());
			testComparacion(v, poli.monomios());
		}
	}

	@Test
	public void testConstructorCopia() {
		System.out.println("\nValidando constructor a partir de una polinomio...");

		for (double[] v: poliData1) {
			Polinomio poli = new Polinomio(v);
			testOrdenyCeros(poli.monomios());
			testComparacion(v, poli.monomios());
			
			System.out.print(poli);
			Polinomio copia = new Polinomio(poli);
			System.out.println(" -> " + copia);	
			
			testOrdenyCeros(copia.monomios());
			testComparacion(v, copia.monomios());
			testdeepCopy(poli.monomios(), copia.monomios());
		}
	}
	
	double poliData2[][] =  {
			{3, 4, 5},
			{9, 8, 6},
			{5, 10, 7, 6},
			{0, 2, -2},
			{2, 2, 2},
			{-2, -2,},
			{2, 2},
			{},
			{1, 2, 3},
			{-1, 0, -3},
			{0, 0, -5, 0, 0},
			{2, 0, 3},
			{2, 0, 3, 0, 0, 8},
			{0.00000000001, 0.000000001, -0.0000000001}
	};
			
			
	@Test
	public void TestSuma() {
		System.out.println("\nValidando suma de polinomios...");
		for (int i = 0; i < poliData2.length-1; i++) {
			Polinomio poli1 = new Polinomio(poliData2[i]);
			Polinomio poli2 = new Polinomio(poliData2[i+1]);
			
			System.out.println("(this) " + poli1 + "\n   +   " + poli2);
			
			poli1.sumar(poli2);
			
			double expected[] = sumaVectores(poliData2[i], poliData2[i+1]);
			
			System.out.println("  ->   " + poli1 + "\n");
			testOrdenyCeros(poli1.monomios());
			testComparacion(expected, poli1.monomios());
		}
	}
			
	@Test
	public void TestSumaAuto() {
		System.out.println("\nValidando suma de polinomios consigo mismos...");
		for (int i = 0; i < poliData1.length; i++) {
			Polinomio poli1 = new Polinomio(poliData1[i]);
			
			System.out.println("(this) " + poli1 + "\n   +   " + poli1);
			
			poli1.sumar(poli1);
			
			double expected[] = sumaVectores(poliData1[i], poliData1[i]);
			
			System.out.println("  ->   " + poli1 + "\n");
			testOrdenyCeros(poli1.monomios());
			testComparacion(expected, poli1.monomios());
		}
	}
	
	@Test(expected = NullPointerException.class)
	public void TestSumaExpception() {
		System.out.println("\nValidando excpciones en la suma...");
		
		double v[] = {1, 2, 3};
		
		Polinomio poli = new Polinomio (v);	
		poli.sumar(null);
	}
	
	@Test
	public void testEquals() {
		System.out.println("\nValidando igualdad de polinomios a si mismos..");
		for (int i = 0; i < poliData1.length; i++) {
			Polinomio poli1 = new Polinomio(poliData1[i]);
			
			System.out.println(poli1);
			assertEquals(poli1, poli1);
		}
		
		System.out.println("\nValidando igualdad de polinomios a copias de si mismos..");
		for (int i = 0; i < poliData1.length; i++) {
			Polinomio poli1 = new Polinomio(poliData1[i]);
			Polinomio poli2 = new Polinomio(poli1);
			
			System.out.println(poli1 + " == " + poli2);
			assertEquals(poli1, new Polinomio(poli1));
		}
	}
	
	static double[][] poliData3 = {{2, 0}, {0,0}, {2, 2}, {0,2}, {-1, 1}, {-1, 0}, {0.0000000001, 2}};

	@Test
	public void TestMultiplcarMonomio() {
		System.out.println("\nValidando multiplicacion por monomios..");
		for (int i = 0; i < poliData3.length; i++) {
			Monomio mono = new Monomio(poliData3[i][0], (int)poliData3[i][1]);
			
			for (int j = 0; j < poliData1.length; j++) {
				Polinomio poli = new Polinomio(poliData1[j]);
				
				System.out.println("(" + poli + ") " + " * " + mono);
				
				poli.multiplicarMonomio(mono);
				
				System.out.println(" -> " + poli + "\n");

				double expected[] = multiplicaMonomio(poliData1[j], mono);
							
				testComparacion(expected, poli.monomios());
				
			}
		}	
	}
}
