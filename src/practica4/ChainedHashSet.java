package practica4;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Clase generica que implementa la interfaz Set<T> usando una tabla de
 * dispersión con resolución de colisiones mediante encadenamiento.
 * 
 * Admite elmentos null como validos
 * 
 * Implementa iteradores aunque no incluy e ningun mecanismo para evitar la
 * ejecucion concurrente.
 * 
 * @param <T>
 */
public class ChainedHashSet<T> implements Set<T> {
	/**
	 * Parte privada
	 * 
	 * DEFAULT_CAPACITY: tamaño por defecto de la tabla de dispersión
	 * DEFAULT_LOAD_FACTOR: factor de carga a aprtir del cual se realiza el
	 * rehashing. 1.0 es un 100%.
	 * 
	 * class node: el nodo 
	 * 
	 * table: la tabla de dispersión. 
	 * 
	 * size: numero de
	 * elementos de la tabla 
	 * 
	 * rehashThreshold: umbral a partir del cual se debe
	 * realizar el rehashing (size > rehashThreshold)
	 */
	static int DEFAULT_CAPACITY = 7; 
	static double DEFAULT_LOAD_FACTOR = 3.0; // 300%

	private class Node {
		T data;
		Node next;

		public Node(T item) {
			data = item;
			next = null;
		}
	}

	private Node table[];
	private int size;
	private int rehashThreshold;

	// constructores
	@SuppressWarnings("unchecked")
	public ChainedHashSet(int capacity, double loadFactor) {
		table = new ChainedHashSet.Node[capacity];
		size = 0;
		rehashThreshold = (int) Math.floor(loadFactor * capacity);
	}

	public ChainedHashSet(int capacity) {
		this(capacity, DEFAULT_LOAD_FACTOR);
	}

	public ChainedHashSet(double loadFactor) {
		this(DEFAULT_CAPACITY, loadFactor);
	}

	public ChainedHashSet() {
		this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
	}

	/**
	 * Calcula el valor positivo de la funcion de dispersion para item ajustado
	 * al tamaño, y teniendo en cuenta que puede ser null
	 * 
	 * @param item
	 * @return Valor de dispersion
	 */
	private int hash(Object item) {
		if (item == null)
			return 0;

		return (item.hashCode() & Integer.MAX_VALUE) % table.length;
	}

	/**
	 * Compara si item1 y item2 son iguales teniendo en cuenta que ambos puede
	 * ser null
	 * 
	 * @param item1
	 * @param item2
	 * @return si item1 y item2 son iguales
	 */
	@SuppressWarnings("unchecked")
	private boolean equalsNull(Object item1, Object item2) {
		if (item1 == null)
			return item1 == item2;
		else
			return ((T) item1).equals((T) item2);
	}

	/**
	 * Realiza el rehashing si es necesario, cuando (size > rehashThreshiold). Al
	 * realizarlo dobla el tamaño de la tabla y recalcula el rehashThreshold
	 */
	@SuppressWarnings("unchecked")
	private void rehash() { 
		if (size>=rehashThreshold){
			ChainedHashSet<T> aux=new ChainedHashSet(this.table.length*2);
			for (int i=0; i<table.length;i++){
				if (table[i]!=null){
					Node nodo=table[i];
					while (nodo!=null){
						aux.add(nodo.data);
						nodo=nodo.next;
					}
				}
			}
			table=aux.table;
			rehashThreshold=aux.rehashThreshold;
			aux.clear();			
		}
	}

	public int getCapacity() {
		return table.length;
	}
	
	@Override
	public boolean add(T item) {
		if (contains(item))
			return false;
		rehash();
		int pos=hash(item);
		if (table[pos]!=null){
			Node nodo=table[pos];
			while(nodo.next!=null){
				nodo=nodo.next;
			}
			nodo.next=new Node(item);
		}else
			table[pos]=new Node(item);
		size++;
		return true;
	}

	@Override
	public boolean contains(Object item) {
		int pos=hash(item);
		boolean esta=false;
		if (table[pos]==null)
			return esta;
		Node aux=table[pos];
		while (aux!=null){
			if (equalsNull(aux.data, item)){
				esta=true;
				break;
			}
			else
				aux=aux.next;
		}
		return esta;
	}

	@Override
	public boolean remove(Object item) {
		if (!contains(item))
			return false;
		int pos=hash(item);
		Node aux=table[pos];
		Node ant=null;
		while (aux!=null){
			if (equalsNull(aux.data,item)){
				if(ant==null){
					table[pos]=aux.next;
				}else{
					ant.next=aux.next;
				}
				break;
			}else{
				ant=aux;
				aux=aux.next;
			}
		}
		size--;
		return true;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void clear() {
		table = new ChainedHashSet.Node[table.length];
		size=0;
	}

	// Los siguientes métodos asumen la existencia de iteradores.

	@Override
	public boolean containsAll(Collection<?> c) {
		if (c.isEmpty())
			return true;
		if (isEmpty())
			return false;
		for (Object item : c) {
			if (!contains(item))
				return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addAll(Collection<? extends T> c) {
		int oldSize = size();
		for (Object item : c)
			add((T) item);

		return (size() != oldSize);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean cambia=false;
		T item;
		for (int i=0; i<table.length; i++){
			Node nodo=table[i];
			while(nodo!=null){
				item=nodo.data;
				nodo=nodo.next;
				if (!c.contains(item)){				
					remove(item);
					cambia=true;
				}				
			}
		}
		return cambia;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		int oldSize = size();
		for (Object item : c)
			remove(item);

		return size() != oldSize;
	}

	@Override
	public Object[] toArray() {
		Object ret[] = new Object[size];

		int i = 0;
		for (Object item : this) {
			ret[i] = item;
			i++;
		}

		return ret;

	}

	@SuppressWarnings({ "unchecked", "hiding" })
	@Override
	public <T> T[] toArray(T[] a) {
		T[] ret = null;

		if (a.length > size) {
			ret = a;
			for (int i = size; i < ret.length; i++)
				ret[i] = null;
		} else
			ret = (T[]) new Object[size];

		int i = 0;
		for (Object item : this) {
			ret[i] = (T) item;
			i++;
		}

		return ret;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(toArray());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChainedHashSet<T> other = (ChainedHashSet<T>) obj;

		if (size() != other.size())
			return false;

		for (T item : other) {
			if (!contains(item))
				return false;
		}

		return true;
	}

	public String toString() {
		StringBuilder str = new StringBuilder("[ ");
		for (int row = 0; row < table.length; row++) {
			if (table[row] != null) {
				str.append("{" + row + ": ");
				str.append(table[row].data);
				Node aux = table[row].next;
				while (aux != null) {
					str.append(", " + aux.data);
					aux = aux.next;
				}
				str.append("} ");
			}
		}

		str.append("] (size: " + size + ", capacity: " + table.length + ")");
		return str.toString();
	}

	// Implementación de los iteradores

	private class LocalIterator implements Iterator<T> {
		private class TableNode {
			public Node node = null;
			public int row = -1;

			public TableNode(Node n, int r) {
				node = n;
				row = r;
			}
		}

		private TableNode current;
		private TableNode last = null;
		private TableNode prevLast = null;
		private boolean erasable = false;

		private TableNode findNext(TableNode n) {
			TableNode ret = null;
			if (n == null)
				ret = new TableNode(table[0], 0);
			else
				ret = new TableNode(n.node.next, n.row);

			while ((ret.row < table.length) && (ret.node == null)) {
				ret.row++;
				if (ret.row == table.length)
					break;
				ret.node = table[ret.row];
			}

			return (ret.row == table.length) ? null : ret;
		}

		public LocalIterator() {
			current = findNext(null);
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();

			prevLast = last;
			last = current;
			current = findNext(current);
			erasable = true;

			return last.node.data;
		}

		@Override
		public void remove() {
			if (!erasable)
				throw new IllegalStateException();

			if ((prevLast == null) || (prevLast.row != last.row))
				table[last.row] = last.node.next;
			else
				prevLast.node.next = last.node.next;
			last = prevLast;

			erasable = false;
			size--;
		}

	}

	@Override
	public Iterator<T> iterator() {
		return new LocalIterator();
	}

}
