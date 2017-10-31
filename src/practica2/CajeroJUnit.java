package practica2;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import practica2.Cajero;

public class CajeroJUnit {
	
	static Set<Integer> testLegalesData = new HashSet<Integer>(Arrays.asList(1,2,5,10,20,50,100,200,500));
	static int cajeroFraccionarData[] = {500, 200, 100, 50, 20, 10, 5, 2}; 
	
	static void testLegales(List<Integer> fajo) {
		for (Integer b: fajo)
			assertTrue("Billete de valor ilegal", testLegalesData.contains(b));
	}
	
	static void testValor(List<Integer> fajo, int total) {
		int sum = 0;
		for (int b: fajo)
			sum += b;
		
		assertEquals("Cantidad incorrecta", total, sum);
	}
	
	static void testOrdenado(List<Integer> fajo) {
		Iterator<Integer> iter = fajo.iterator();
		
		int ant = iter.next();
		while (iter.hasNext()) {
			int act = iter.next();
			assertTrue("Fajo desordenado", ant <= act);
			ant = act;
		}
	}
	
	static void testMenores(List<Integer> fajo, int maximo) {
		for(int b: fajo)
			assertTrue("Billete demasiado grande", b < maximo);
	}
	
	@Test
	public void testFraccionar() {
		System.out.println("\nValidando fraccionar()...");

		for (int billete: cajeroFraccionarData) {
			System.out.print("["+billete+"] -> ");
			List<Integer> fajo = Cajero.fraccionar(billete);
			System.out.println(fajo);
			testLegales(fajo);
			testMenores(fajo, billete);
		}
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testFraccionarException() {
		System.out.println("\nValidando exception de fraccionar()...");

		Cajero.fraccionar(99);
		Cajero.fraccionar(3);
		Cajero.fraccionar(25);
		Cajero.fraccionar(250);
		Cajero.fraccionar(75);

	}
	
	@Test
	public void testCosntructor() {
		System.out.println("\nValidando constructor Cajero()...");
		Cajero caja = new Cajero();
		assertEquals("Saldo no nulo", 0, caja.saldo()); 
 	}
	
	@Test
	public void testRecargar() {
		System.out.println("\nValidando recargar() ...");
		Cajero caja = new Cajero();
		int total = 0;
		
		List<Integer> fajo = new LinkedList<>(testLegalesData);
		int fajoTotal = 0;
		for (int b: fajo) 
			fajoTotal += b;
		
		while (!fajo.isEmpty()) {
			System.out.print("{" + total + "} + " + fajo + ": " + fajoTotal +  " -> ");
			total += fajoTotal;
			
			caja.recargar(fajo);
			System.out.println("{" + caja.saldo() + "}");
			assertEquals("Saldo de recarga incorrecto", total, caja.saldo());

			fajoTotal -= fajo.get(0);
			fajo.remove((int)0);
		}
	}
		
	@Test (expected=IllegalArgumentException.class)
	public void testRecargarException() {
		System.out.println("\nValidando exception de recargar()...");

		List<Integer> fajo = Arrays.asList(100, 99, 50, 3, 200, 8, 10, 250, 50);
		Cajero caja = new Cajero();
		
		caja.recargar(fajo);
	}
	
	@Test 
	public void testReintegrar() {
		System.out.println("\nValidando reintegrar()...");
		List<Integer> fajo = new LinkedList<>();
		int total = 0;
		
		for (int i=0; i < 20; i++) {
			fajo.add(500);
			total += 500;
		}
		
		Cajero caja = new Cajero();
		caja.recargar(fajo);
		System.out.println("Recargando " + total);
		
		for (int cantidad = 20; cantidad <= 600; cantidad += 30) {
			System.out.print("{" + caja.saldo() + "} - " + cantidad + " -> ");
			fajo = caja.reintegrar(cantidad);
			System.out.println(fajo +": {" + caja.saldo() + "}");
			total -= cantidad;
			
			testLegales(fajo);
			testValor(fajo, cantidad);
			testOrdenado(fajo);
			assertEquals("Saldo de la caja incorrecto", total, caja.saldo());
			
		}
		
		for (int cantidad = 1; cantidad <= 100; cantidad += 3) {
			System.out.print("{" + caja.saldo() + "} - " + cantidad + " -> ");
			fajo = caja.reintegrar(cantidad);
			System.out.println(fajo +": {" + caja.saldo() + "}");
			total -= cantidad;
			
			testLegales(fajo);
			testValor(fajo, cantidad);
			testOrdenado(fajo);
			assertEquals("Saldo de la caja incorrecto", total, caja.saldo());
			
		}
	}
	
	@Test
	public void testReintegrarSinSaldo() {
		System.out.println("\nValidando reintegrar() con saldo insuciente...");
		
		Cajero caja = new Cajero();
		
		System.out.print("{" + caja.saldo() + "} - " + 30 + " -> ");
		List<Integer> fajo = caja.reintegrar(30);
		System.out.println(fajo +": {" + caja.saldo() + "}");
		
		assertNull("Reintegra mas de lo que hay", fajo);
		
		caja.recargar(Arrays.asList(20, 50));
		
		System.out.print("{" + caja.saldo() + "} - " + 80 + " -> ");
		fajo = caja.reintegrar(80);
		System.out.println(fajo +": {" + caja.saldo() + "}");
		
	}
	
}
