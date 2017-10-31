package practica5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

//IMPLEMENTS A PRIORITY QUEUE USING A MINHEAP

public class EDPriorityQueue<E>  {

	private static final int DEFAULT_INITIAL_CAPACITY = 11;
	
	private E [] data;
    private int size;	
	//optional reference to comparator object
	private Comparator<E> comparator;
	
	//Methods
	//Constructor
	
	public EDPriorityQueue() {
		this(DEFAULT_INITIAL_CAPACITY, null);
	}
	public EDPriorityQueue(Comparator<E> comp) {
		this(DEFAULT_INITIAL_CAPACITY, comp);
	}
	
	public EDPriorityQueue(int capacity) {
		this(capacity, null);
	}
	
	public EDPriorityQueue(int capacity, Comparator<E> comp) {
		if (capacity < 1) 
			throw new IllegalArgumentException();
		this.data = (E[]) new Object[capacity];
		this.comparator = comp;
	}
	
	public EDPriorityQueue(Collection<E> c) {
		this.data = (E[]) new Object[c.size()];
		size = c.size();
		int i=0;
		for (E elem: c) {data[i]=elem; i++;}
		heapify();
	}
	
	//private methods
	/** compares two objects and returns a negative number if object
	 * left is less than object right, zero if are equal, and a positive number if
	 * object left is greater than object right
	 */
	private int compare(E left, E right) {
		if (comparator != null) { //A comparator is defined
			return comparator.compare(left,right);
		}
		else {
			return (((Comparable<E>) left).compareTo(right));
		}
	}
	
	/** Exchanges the object references in the data field at indexes i and j
	 * 
	 * @param i  index of firt object in data
	 * @param j  index of second objet in data
	 */
	private void swap (int i, int j) {
		E elem_i= this.data[i];
		this.data[i]=data[j];
		this.data[j]=elem_i;
	}
	
	/** sink Hunde el elemento en la posicion p hasta que se cumple la propiedad de orden del
	 * mont�culo o es un nodo hoja
	 * @param p Posici�n en el vector del elemento que hay que "hundir"
	 */
	private void sink (int p) {
		int min=p;
		int left=2*p+1;
		if (left<size()){
			if (compare(data[p],data[left])>0)
				min=left;
			int right=left+1;
			if (right<size())
				if (compare(data[right],data[min])<0)
					min=right;
		}
		if (min!=p){
			swap(p,min);
			sink(min);
		}
		
	}
	
	/** floating Sube (o flota) el elemento de la posici�n p hasta que se cumple la propiedad de 
	 * orden del mont�culo o es la ra�z
	 * @param p Posici�n en el vector del elemento a subir
	 */
	private void floating (int p) {
		if (p>0){
			int dad=(p-1)/2;
			if(compare(data[p],data[dad])<0){
				swap(p,dad);
				floating(dad);
			}
		}
	}
	
	/** Duplica el tama�o del vector que almacena los elementos del mont�culo*/
	private void grow () {
		int i=0;
		E[] aux=data;
		data=(E[]) new Object[data.length*2];
		for (E elem : aux){
			data[i]=elem;
			i++;
		}
		
	}
	
	/** Construye un mont�culo sobre el vector data
	 * 
	 */
	private void heapify() {
		for(int i=(size()-1)/2;i>=0;i--)
			sink(i);			
	}
	

	/**Public methods*/
	public boolean isEmpty() {
		return (this.size == 0);
	}
	
	
	public int size() {
		return this.size;
	}
	
	public Object[] toArray() {
		Object[] array = new Object[this.data.length];
		for (int i=0; i<this.size; i++)
			array[i] = this.data[i];
		return array;
		 
	}
	

	/** REturns the smallest entry, WITHOUT REMOVING IT.
	 * If the queue is empty, returns NoSuchElementException
	 * @return E smallest entry
	 */
	public E element() throws NoSuchElementException {
		if (isEmpty()) throw new NoSuchElementException();
		return data[0];
	}
	
	
	/**Devuelve el �ndice en el vector donde est� almacenado el elemento item o -1 si no est�
	 * 
	 * @param item Elemento que hay que buscar
	 * @return posici�n en el vector donde est� elem, -1 si no est�
	 */
	public int indexOf(E item) {
		for(int i=0; i<size();i++){
			if (compare(data[i],item)==0)
				return i;
		}
		return -1;
	}
	
	/** Inserts an item into the priority_queue.
	 */
	public void offer(E item) {
		if (data.length==size()){
			grow();
		}
		data[size]=item;
		size++;
		floating(size-1);
	}
	
		

	/** Removes the smallest entry and returns it if the priority queue is not empty.
	 * Otherwise, returns NoSuchElementException
	 * @return E smallest element in the queue
	 */
	public E remove() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException();
		swap(0,size-1);
		E item=data[size-1];
		size--;
		sink(0);
		return item;
	}
	
	

	/** Removes a single instance of the element item
	 * 
	 * @param item Element to be removed 
	 * @return the value of the element removed
	 * @throws NoSuchElementException If there no such item in the collection
	 */
	public E remove (E item) throws NoSuchElementException {
		int i=indexOf(item);
		if (i==-1)
			throw new NoSuchElementException();
		swap(i,size-1);
		E itemRemoved=data[size-1];
		size--;
		sink(i);
		return itemRemoved;
		
	}


}
