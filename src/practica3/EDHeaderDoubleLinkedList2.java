package practica3;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;




/**
 * Implementación de la interface List<T> usando nodos doblemenete enlazados de
 * forma circular y con nodo cabecera.
 * 
 * La clase admite nulls como elementos de la lista, por lo tanto deberá tratar
 * correctamente su inserción, búsqueda y borrado.
 *
 * @param <T>
 *            Tipo básico de la lista
 */
public class EDHeaderDoubleLinkedList2<T> implements List<T> {

	/**
	 * Definición de nodo
	 */
	private class Node {
		public T data;
		public Node next;
		public Node prev;

		public Node(T element) {
			data = element;
			next = null;
			prev = null;
		}
	}

	// Enlace al nodo cabecera. A él se enlazan el primero y el último
	private Node header;
	// Número de elementos de la clase
	private int size;

	/**
	 * Constructor por defecto
	 */
	public EDHeaderDoubleLinkedList2() {
		header=new Node(null);
		header.next=header;
		header.prev=header;
		size=0;
	}

	/**
	 * Constructor que copía todos los elementos de otra clase
	 * 
	 * @param c
	 */
	public EDHeaderDoubleLinkedList2(Collection<T> c) {
		this();
		addAll(c);
	}

	/**
	 * Compara si dos elementos son iguales, teniendo en cuenta que uno o ambos
	 * pueden ser null
	 *
	 * @return Si ambos elementos son iguales
	 */
	private boolean compareNull(T one, Object item) {
		if (one == null)
			return one == item;
		else
			return one.equals(item);
	}

	/**
	 * Busca el primer nodo cuyo contenido se item. Se salta el nodo cabecera
	 * 
	 * @param item
	 * @return Devuelve el nodo o null si no encuetra un nodo con ese contenido
	 */
	private Node findNode(Object item) {
		if (isEmpty())
			return null;
		else{
			Node aux=header.next;
			for(int i=0;i<=size;i++){
				if (compareNull(aux.data, item))
					return aux;
				else
					aux=aux.next;
			}
			return null;
		}
	}

	/**
	 * Devuelve el nodo de la lista que ocupa la posición index.
	 * 
	 * @param index
	 * @return El nodo de la posición index Si index es size devuelve el nodo
	 *         cabecera.
	 * @throws IndexOutofBoundsExcpetion
	 *             Si index es negativo o mayor que size
	 */
	private Node findIndex(int index) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();

		Node aux = header;
		if (index < size / 2)
			for (int i = 0; i <= index; i++)
				aux = aux.next;
		else
			for (int i = size; i > index; i--)
				aux = aux.prev;

		return aux;
	}

	/**
	 * Crea e inserta un nuevo nodo antes que el nodo n. El dato contenido en el
	 * nuevo nodo será item. Incrementa el tamaño de size.
	 * 
	 * @param n, nodo anets del cual se debe insertas
	 * @param item, dato del nuevo nodo
	 * @return el nuevo nodo
	 */
	private Node insertBefore(Node n, T item) {
		Node nuevo=new Node(item);
		if (isEmpty()){
			nuevo.next=header;
			nuevo.prev=header;
			header.next=header.prev=nuevo;
		}
		Node aux=header.next;
		for(int i=0; i<=size; i++){ // <= para coger a header
			if (aux==n){
				nuevo.next=aux;
				nuevo.prev=aux.prev;
				aux.prev=nuevo;
				nuevo.prev.next=nuevo;
				break;
			}
			aux=aux.next;
		}
		size++;
		return nuevo;
		
	}

	/** Borra el nodo n. Decrementa el valor de size
	 * @param n
	 */
	private void removeNode(Node n) {
		if (contains(n.data)){
			Node paraBorrar=findNode(n.data);
			paraBorrar.prev.next=paraBorrar.next;
			paraBorrar.next.prev=paraBorrar.prev;
			size--;
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return header.next == header;
	}

	@Override
	public void clear() {
		header.next = header.prev = header;
		size = 0;
	}

	@Override
	public boolean add(T item) {
		insertBefore(header, item);
		return true;
	}

	@Override
	public void add(int index, T element) {
		Node pos=findIndex(index);
		insertBefore(pos,element);
	}

	@Override
	public boolean remove(Object item) {
		Node pos=findNode(item);
		if (pos==null)
			return false;
		removeNode(pos);
		return true;
		
	}

	@Override
	public T remove(int index) {
		if (index == size)
			throw new IndexOutOfBoundsException();

		Node n = findIndex(index);
		removeNode(n);
		return n.data;
	}

	@Override
	public boolean contains(Object item) {
		return findNode(item) != null;
	}

	@Override
	public T get(int index) {
		if (index == size)
			throw new IndexOutOfBoundsException();

		Node n = findIndex(index);
		return n.data;
	}

	@Override
	public T set(int index, T element) {
		if (index == size)
			throw new IndexOutOfBoundsException();

		Node n = findIndex(index);
		T old = n.data;
		n.data = element;
		return old;
	}

	@Override
	public int indexOf(Object item) {
		Node aux = header.next;
		int i = 0;

		while (aux != header) {
			if (compareNull(aux.data, item))
				return i;
			aux = aux.next;
			i++;
		}

		return -1;
	}

	@Override
	public int lastIndexOf(Object item) {
		Node aux = header.prev;
		int i = size-1;

		while (aux != header) {
			if (compareNull(aux.data, item))
				return i;
			aux = aux.prev;
			i--;
		}

		return -1;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object item : c)
			if (!contains(item))
				return false;
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		for(T item:c){
			insertBefore(header,item);
		}
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		Node n = findIndex(index);

		for (T item : c)
			insertBefore(n, item);

		return !c.isEmpty();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		int oldSize = size;
		for (Object item : c)
			remove(item);

		return oldSize != size;

	}

	@Override
	public boolean retainAll(Collection<?> c) {
		Node aux = header.next;
		int oldSize = size;

		while (aux != header) {
			if (!c.contains(aux.data)) {
				aux = aux.next;
				removeNode(aux.prev);
			} else
				aux = aux.next;
		}

		return oldSize != size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof List<?>))
			return false;

		List<T> other = (List<T>) obj;

		if (size() != other.size())
			return false;

		Node aqui = header.next;
		for (T alli : other) {
			if (!compareNull(aqui.data, alli))
				return false;
			else
				aqui = aqui.next;
		}

		return true;
	}
	
	@Override
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append("[" );
		if (size != 0) {
			Node aux = header.next;
			str.append(aux.data == null ? "null" : aux.data.toString());
			while (aux.next != header) { 
				aux = aux.next;
				str.append(", ");
				str.append(aux.data == null ? "null" : aux.data.toString());
			}
		}
		str.append(" ], size=");
		str.append(size);
		
		return str.toString();
	}
	
	@Override
	public Object[] toArray() {
		Object retVal[] = new Object[size];

		Node aux = header.next;
		int i = 0;
		while (aux != header)
			retVal[i++] = aux.data;
		return retVal;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <U> U[] toArray(U[] a) {
		U[] retVal;
		if (a.length > size)
			retVal = a;
		else
			retVal = (U[]) new Object[size];

		Node aux = header.next;
		int i = 0;
		while (aux != header)
			retVal[i++] = (U) aux.data;

		return retVal;
	}
	


	@Override
	public Iterator<T> iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public ListIterator<T> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

}
