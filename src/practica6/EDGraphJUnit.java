package practica6;

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


public class EDGraphJUnit {
	
	private static Random generator;
	
	/**
	 * Crea grafo de Characteres con pesos dirigido vac�o
	 */
	private EDListGraph<Character, Double> createDirectedGraph()  {
		return  new EDListGraph<Character,Double>(true);
	}
	
	/**
	 * * Crea grafo de Characteres con pesos no dirigido vac�o
	 */
	private EDListGraph<Character, Double> createUnDirectedGraph()  {
		return  new EDListGraph<Character,Double>();
	}
	
	
	@Test
	public void testInsertNode() {
		EDListGraph<Character,Integer> g=new EDListGraph<Character,Integer>();
		System.out.println("Validando  insertNode (T item) \n");
		System.out.println("Insertando en un array vac�o ");
		int pos=0;
		int index1 = g.insertNode('A');
		assertEquals("primero ... ",index1,pos);
		System.out.println("insertado en posici�n "+index1);
		pos++;
		int index2 = g.insertNode('B');
		assertEquals("segundo ... ",index2,pos);
		System.out.println("insertado en posici�n "+index2);
		pos++;
		int index3 = g.insertNode('C');
		assertEquals("tercero ... ", index3,pos);
		System.out.println("insertado en posici�n "+index3);
		pos++;
		int index4 = g.insertNode('D');
		assertEquals("cuarto ... ", index4,pos);
		System.out.println("insertado en posici�n "+index4);
		
		g.removeNode(index2);
		System.out.println("insertado en posici�n "+index2);
		int res = g.insertNode('X');
		System.out.println("insertado en posici�n "+res);
		assertEquals ("borrando e insertando ... ",res, index2);
		
		g.removeNode(index1);
		System.out.println("despues de borrar");
		res = g.insertNode('Y');
		assertEquals ("borrando e insertando al principio ...",res,index1);
		
		g.removeNode(index4);
		res = g.insertNode('Z');
		assertEquals ("borrando e insertando al final ...",res,index4);
		
		System.out.println("Insertando un nodo que ya est� ...");
		res = g.insertNode('C');
		assertEquals ("insertando repetidos ... ",res,index3);
		res = g.insertNode('Y');
		assertEquals ("insertando repetido ...", res, index1);
	}   
	
	
	@Test
	public void testInsertEdge() {
		System.out.println("\nValidando insertEdge (Edge e)...\n");
		EDListGraph<Character,Double> g = createDirectedGraph();
		
		System.out.println("adding an edge in an empty graph ...");
		EDEdge<Double> e = new EDEdge<Double> (0,1,1.0);
		
		System.out.println("trying to add an edge before corresponding nodes ...");
		assertFalse("insertEdge returns false ...",g.insertEdge(e));
		
		Character col1= new Character('A');
		Character col2 = new Character('B');
		Character col3 = new Character('C');
		
		int index1 = g.insertNode(col1);
		int index2 = g.insertNode(col2);
		int index3 = g.insertNode(col3);
		
		e = new EDEdge<Double>(index3,index1,1.0);
		
		EDEdge<Double> checkEdge = g.getEdge(index1, index2);
		assertEquals("arco no existe ...", checkEdge, null);
		
		g.insertEdge(e);
		checkEdge = g.getEdge(index3, index1);
		assertNotEquals("arco existe ...", checkEdge, null);
		System.out.println("retrieved edge ..."+checkEdge.toString());
		assertEquals ("Comprobar origen ...", checkEdge.getSource(), index3);
		Character origen = g.getNodeValue(checkEdge.getSource());
		assertTrue ("Comprobar origen ...", origen.equals('C'));
		assertEquals ("Comprobar destino ...", checkEdge.getTarget(), index1);
		assertTrue ("Comprobar destino ...", g.getNodeValue(checkEdge.getTarget()).equals('A'));
		
		EDEdge<Double> reverse = g.getEdge(index1, index3);
		assertEquals("arco inverso no exise en grafo dirigido ...",reverse,null);
		
		System.out.println("Comprobar que no se insertan arcos repetidos ...");
		assertFalse(g.insertEdge(checkEdge));
		
		EDListGraph<Character,Double> g2 = createUnDirectedGraph();
		
		
		index1 = g2.insertNode(col1);
		index2 = g2.insertNode(col2);
		index3 = g2.insertNode(col3);
		
		e = new EDEdge<Double>(index3,index1,2.0);
		
		checkEdge = g2.getEdge(index1, index2);
		assertEquals("arco no existe ...", checkEdge, null);
		
		g2.insertEdge(e);
		checkEdge = g2.getEdge(index3, index1);
		assertNotEquals("arco existe ...", checkEdge, null);
		assertEquals ("Comprobar origen ...", checkEdge.getSource(), index3);
		assertTrue ("Comprobar origen ...", g2.getNodeValue(checkEdge.getSource()).equals('C'));
		assertEquals ("Comprobar destino ...", checkEdge.getTarget(), index1);
		assertTrue ("Comprobar destino ...", g2.getNodeValue(checkEdge.getTarget()).equals('A'));
		
		
		reverse = g2.getEdge(index1, index3);
		assertNotEquals("arco inverso existe ...",reverse,null);
		assertEquals("arcos inversos iguales ...", checkEdge.getSource(),reverse.getTarget());
		assertEquals("arcos inversos iguales ...", checkEdge.getTarget(), reverse.getSource());
		assertEquals("arcos inversos iguales ...", checkEdge.getWeight(), reverse.getWeight());
		
		System.out.println("Comprobar que no se insertan arcos repetidos ...");
		assertFalse(g2.insertEdge(checkEdge));
		assertFalse(g2.insertEdge(reverse));
	}
    
	
	
	@Test
	public void testGetNodeIndex() {
		System.out.println("\nValidando metodo NodeIndex(T)...\n");
		EDListGraph<Character,Double> g = createDirectedGraph();
		
		Character c = 'A';
		assertEquals("Comprobando nodo que no est� ...", g.getNodeIndex(c),-1);
		
		int index = g.insertNode(c);
		assertEquals("Comprobando nodo que est� ...", g.getNodeIndex(c),index);
		
		int index2 = g.insertNode('B');
		int index3 = g.insertNode('C');
		int index4 = g.insertNode('D');
		
		c = 'E';
		assertEquals("Comprobando nodo que no est�  ", g.getNodeIndex(c), -1);
		assertEquals("Comprobando nodo el primero ... ", g.getNodeIndex('A'), index);
		assertEquals("Comprobando el �ltimo ....",g.getNodeIndex('D'), index4);
		
		System.out.println("Buscando despu�s de borrar ... ");
		g.removeNode(index);
		assertEquals("Comprobando el �ltimo ....",g.getNodeIndex('D'), index4);
		g.removeNode(index3);
		assertEquals("Comprobando el �ltimo ....",g.getNodeIndex('D'), index4);
		
		assertEquals("Buscar el borrado ....",g.getNodeIndex('A'), -1);
		assertEquals("Buscar el borrado ....",g.getNodeIndex('C'), -1);
		
		int index5= g.insertNode('X');
		assertEquals("Volviendo a insertar ....",g.getNodeIndex('X'), index5);
		assertEquals("Comprobando el �ltimo ....",g.getNodeIndex('D'), index4);
		
		g.removeNode(index4);
		assertEquals("Comprobando el �ltimo ....",g.getNodeIndex('D'), -1);
		
	}
	
	@Test
	public void testGetEdge() {
		System.out.println("\nValidando metodo getEdge(int, int)...\n");
		EDListGraph<Character,Double> g = createDirectedGraph();
		
		assertEquals ("Comprobando arco que no est� con grafo vac�o ",g.getEdge(0, 3), null);
		assertEquals ("Comprobando con valores negativos ", g.getEdge(-1, 3), null);
		
		Character c = 'A';
		int index1 = g.insertNode(c);
		int index2 = g.insertNode('B');
		int index3 = g.insertNode('C');
		int index4 = g.insertNode('D');
		
		g.insertEdge(new EDEdge<Double>(index1, index4));
		g.insertEdge(new EDEdge<Double>(index1,index2));
		g.insertEdge(new EDEdge<Double>(index2,index1));
		g.insertEdge(new EDEdge<Double>(index2,index3));
		
		
		assertEquals ("Comprobando arco que no est� ",g.getEdge(index1, 6), null);
		assertEquals ("Comprobando arco contrario que no est� ",g.getEdge(index4, index1), null);
		EDEdge<Double> edge = g.getEdge(index1, index4);
		assertNotEquals ("Comprobando arco que si est� ", edge, null);
		assertEquals ("Comprobando arco que si est� ..", edge.getSource(),index1);
		assertEquals ("Comprobando arco que si est� ..", edge.getTarget(),index4);
		edge = g.getEdge(index1, index2);
		assertNotEquals ("Comprobando arco que si est� ", edge, null);
		assertEquals ("Comprobando arco que si est� ..", edge.getSource(),index1);
		assertEquals ("Comprobando arco que si est� ..", edge.getTarget(),index2);
		
		
		System.out.println("Con grafos no dirigidos ... ");
	    EDListGraph<Character,Double> g2 = createUnDirectedGraph();
	    
	    index1 = g2.insertNode('A');
		index2 = g2.insertNode('B');
		index3 = g2.insertNode('C');
		
		g2.insertEdge(new EDEdge<Double>(index1,index2));
		g2.insertEdge(new EDEdge<Double>(index2,index3));
		
		edge = g2.getEdge(index2, index1);
		assertNotEquals ("Comprobando arco inverso si que est� ", edge, null);
		assertEquals ("Comprobando arco que si est� ..", edge.getSource(),index2);
		assertEquals ("Comprobando arco que si est� ..", edge.getTarget(),index1);
	}
	
	@Test
	public void testBreathFirstSearch() {
		System.out.println("\nValidando metodo breathFristSearch(int)...\n");
		EDListGraph<Character,Double> g = createUnDirectedGraph();
		
		int indexa = g.insertNode('A');
		int indexb = g.insertNode('B');
		int indexc = g.insertNode('C');
		int indexd = g.insertNode('D');
		int indexe = g.insertNode('E');
		int indexf = g.insertNode('F');
		int indexg = g.insertNode('G');
		
		g.insertEdge(new EDEdge<Double>(indexa, indexb));
		g.insertEdge(new EDEdge<Double>(indexa,indexc));
		g.insertEdge(new EDEdge<Double>(indexa,indexd));
		
		System.out.println("Comprobando con nodo no valido ");
		int x[] = g.breathFirstSearch(10);
		assertEquals("vector de tama�o 0 ...", x.length, 0);
	
		int w[] = g.breathFirstSearch(-2);
		assertEquals("vector de tama�o 0 ...", w.length, 0);
		
		System.out.println("Comprobando con un grafo no conexo ... ");
		int v[] = g.breathFirstSearch(indexa);
		assertEquals ("tama�o vector ... ", v.length, 7);
		for (int i=0; i<=indexd; i++) {
			assertEquals ("Comprobando camino correcto ...",v[i],indexa);
		}
		for (int i=indexe; i<v.length; i++) {
			assertEquals ("Comprobando camino correcto ...",v[i],-1);
		}
		
		g.insertEdge(new EDEdge<Double>(indexd, indexc));
		g.insertEdge(new EDEdge<Double>(indexc,indexb));
		g.insertEdge(new EDEdge<Double>(indexb,indexg));
		g.insertEdge(new EDEdge<Double>(indexg, indexc));
		g.insertEdge(new EDEdge<Double>(indexg,indexe));
		g.insertEdge(new EDEdge<Double>(indexd,indexe));
		g.insertEdge(new EDEdge<Double>(indexe,indexf));
		
		System.out.println("Grafo conexo ... ");
		v = g.breathFirstSearch(indexa);
		assertEquals ("tama�o vector ... ", v.length, 7);
		assertEquals ("Comprobando camino correcto ...",v[0],indexa);
		
		for (int i=1; i<v.length; i++) {
			assertNotEquals("no es -1 ", v[i], -1);

			int longitud=0;
			
			int j = i;
			while (j!=indexa) {
					longitud++;
					j=v[j];
			}	
			//System.out.println("i= "+i+" long= "+longitud);
			if (i==indexb || i==indexc || i==indexd)
				assertEquals("longitud del camino ...", longitud, 1);
			else if (i == indexe || i==indexg)
				assertEquals("longitud del camino ...", longitud, 2);
			else
				assertEquals("longitud del camino ...", longitud, 3);
		}
		
		System.out.println("Comprobar que la longitud del vector resultante es correcta ");
		g.removeNode(indexb);
		v= g.breathFirstSearch(indexa);
		assertEquals ("tama�o vector ... ", v.length, 7);
		assertEquals ("Comprobando camino correcto ...",v[indexb],-1);
		
		for (int i=1; i<v.length; i++) {

           if (v[i]!=-1) {
			int longitud=0;
			
			int j = i;
			while (j!=indexa) {
					longitud++;
					j=v[j];
			}	
			//System.out.println("i= "+i+" long= "+longitud);
			if (i==indexb || i==indexc || i==indexd)
				assertEquals("longitud del camino ...", longitud, 1);
			else if (i == indexe || i==indexg)
				assertEquals("longitud del camino ...", longitud, 2);
			else
				assertEquals("longitud del camino ...", longitud, 3);
           }
		}
	}
}
