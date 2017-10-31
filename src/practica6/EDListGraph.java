package practica6;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


/** Implementation of interface Graph using adjacency lists
 * @param <T> The base type of the nodes
 * @param <W> The base type of the weights of the edges
 */
public class EDListGraph<T,W> implements EDGraph<T,W> {
	@SuppressWarnings("hiding")
	private class Node<T> {
		T data;
		List< EDEdge<W> > lEdges;
		
		Node (T data) {
			this.data = data;
			this.lEdges = new LinkedList< EDEdge<W> >();
		}
		public boolean equals (Object other) {
			if (this == other) return true;
			if (!(other instanceof Node)) return false;
			Node<T> anotherNode = (Node<T>) other;
			return data.equals(anotherNode.data);
		}
	}
	
	// Private data
	private ArrayList<Node<T>> nodes;
	private int size; //real number of nodes
	private boolean directed;
	
	/** Constructor
	 * @param direct <code>true</code> for directed edges; 
	 * <code>false</code> for non directed edges.
	 */

	public EDListGraph() {
		directed = false; //not directed
		nodes =  new ArrayList<Node<T>>();
		size =0;
	}
	
	public EDListGraph (boolean dir) {
		directed = dir;
		nodes =  new ArrayList<Node<T>>();
		size =0;
	}
	
	public int getSize() {
		return size;
	}

	public int nodesSize() {
		return nodes.size();
	}
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	@Override
	
	
	/**
	 * Inserts the item as a new Node of the Graph
	 * The new node is stored in a null position of the array nodes.
	 * If there isn't a null position, then is stored at the end of the array
	 * The method returns the index of the position in the array where the 
	 * new node is stored.
	 * Two nodes cannot have the same item. Then, if already exists a node with
	 * item, it returns the position were it is already stored
	 */
	public int insertNode(T item) {
		int pos=getNodeIndex(item);
		if (pos!=-1)
			return pos;
		Node<T> nuevo=new Node<T>(item);
		pos=0;
		while (pos<nodes.size()){
			if (nodes.get(pos).data==null)
				break;
			pos++;
		}
		if(pos==nodes.size()){
			nodes.add(nuevo);	
		}else{
			nodes.set(pos,nuevo);
		}
		size++;
		return pos;
	}
	
	@Override
	/**
	 * getNodeIndex (T item)  Returns the index of the Node with item in the array of Nodes
	 * It returns -1 if there is no node with that item
	 */
	public int getNodeIndex(T item) {
		for (int i=0; i<nodes.size();i++){
			if(nodes.get(i).data!=null && nodes.get(i).data.equals(item))
				return i;	
		}
		//return nodes.indexOf(item);
		return -1;
	}

	@Override
	public T getNodeValue(int index) throws IndexOutOfBoundsException{
		
		return nodes.get(index).data;
		
	}
	
	
	
	@Override
	/** 
	 * insertEdge (EDEdge edge) Inserts the edge in the graph. If the graph is not directed, the method 
	 * inserts also the reserve edge
	 * Returns true if the edge has been inserted and false otherwise
	 */
	public boolean insertEdge(EDEdge<W> edge) {
		int origen= edge.getSource();
		int destino= edge.getTarget();
		W peso= edge.getWeight();
		if (origen<0 || destino <0 || origen>nodes.size() || destino>nodes.size())
			return false;
		if (nodes.get(origen).equals(null) || nodes.get(destino).equals(null))
			return false;
		if(directed){ //Dirigido
			if(nodes.get(origen).lEdges.contains(new EDEdge<W>(origen,destino,peso)))
				return false;
			else{
				nodes.get(origen).lEdges.add(new EDEdge<W>(origen,destino,peso));
				return true;
			}
		}else{  //No dirigido
			if(nodes.get(origen).lEdges.contains(new EDEdge<W>(origen,destino,peso)))
				return false;
			if(nodes.get(destino).lEdges.contains(new EDEdge<W>(destino,origen,peso)))
				return false;
			nodes.get(origen).lEdges.add(new EDEdge<W>(origen,destino,peso));
			nodes.get(destino).lEdges.add(new EDEdge<W>(destino,origen,peso));
			return true;
		}
	}
	
	
	@Override
	/**
	 * Given the indexes source and dest, returns the EDEdge <source,dest>. If it does not exist such edge, returns null.
	 */
	public EDEdge<W> getEdge(int source, int dest) {
		if (source<0 || dest<0 || source>nodes.size() || dest>nodes.size())
			return null;
		if (nodes.get(source).equals(null) || nodes.get(dest).equals(null))
			return null;
		for(EDEdge<W> edge : nodes.get(source).lEdges){
			if (edge.getTarget()==dest)
				return edge;
		}
		return null;
	}
	
	
	@Override
	public EDEdge<W> removeEdge(int source, int target, W weight) {
		if (source <0 || source >= nodes.size() || target<0 || target >= nodes.size()) return null;
		if (nodes.get(source).data!=null && nodes.get(target).data!=null) {
			EDEdge<W> edge = new EDEdge<W>(source, target, weight);
			Node<T> node = nodes.get(source);
			int i = node.lEdges.indexOf(edge);
			if (i != -1) {
				edge = node.lEdges.remove(i);
				if (!directed) {
					EDEdge<W> reverse = new EDEdge<W>(target,source,weight);
					nodes.get(target).lEdges.remove(reverse);
				}
				return edge;
			}	
		}
		return null;	
	}

	@Override
	public T removeNode(int index) {
		if (index >=0 && index < nodes.size()){
			if (!directed) {
				Node<T> node = nodes.get(index);
				for (EDEdge<W> edge: node.lEdges ) {
					int target = edge.getTarget();
					W label = edge.getWeight();
					EDEdge<W> other = new EDEdge<W>(target,index,label);
					nodes.get(target).lEdges.remove(other);
				}
			}
			else { //directed
				for (int i=0; i<nodes.size(); i++) {
					if (i!=index && nodes.get(i).data !=null) {
						Node<T> node = nodes.get(i);
						for (EDEdge<W> edge: node.lEdges) {
							if (index == edge.getTarget()) //any weight/label
								node.lEdges.remove(edge);
						}
					}
				}
			}
			
			Node<T> node = nodes.get(index);
			node.lEdges.clear();
			T ret = node.data;
			node.data = null; //It is not remove, data is set to null
			nodes.set(index, node);
			size--;
			System.out.println("Borrada posicion: "+index);
			return ret;
		}
		return null;
	}
	
	
	
		
	@Override
	/**
	 * Breath Search of the graph starting in node with index start
	 * It returns an array of integers. Each position of the array corresponds to a node of the graph.
	 * If array[i] = j, means that we get to node i using the edge <j,i>, i.e., we get to i from node j.
	 * If array[i] = -1, it was not possible to get to node i in the actual search
	 */
	public int[] breathFirstSearch (int start) {		
		if(start<0 || start>nodes.size() || nodes.get(start).data==null)
			return new int[0];
		
		int vector[]=new int[nodes.size()];
		for (int i=0; i<vector.length;i++)
			vector[i]=-1;
		vector[start]=start;
		
		Queue<Integer> q=new LinkedList<Integer>();
		q.add(start);
		while (!q.isEmpty()){
			int n=q.remove();
			for(EDEdge<W> e: nodes.get(n).lEdges){
				int destino=e.getTarget();
				if(vector[destino]==-1){
					vector[destino]=n;
					q.add(destino);
				}
			}
		}
		return vector;
	}
	
		
	
	public void printGraphStructure() {
		//System.out.println("Vector size= " + nodes.length);
		System.out.println("Vector size " + nodes.size());
		System.out.println("Nodes: "+ this.getSize());
		for (int i=0; i<nodes.size(); i++) {
			System.out.print("pos "+i+": ");
	        Node<T> node = nodes.get(i);
			System.out.print(node.data+" -- ");
			Iterator<EDEdge<W>> it = node.lEdges.listIterator();
			while (it.hasNext()) {
					EDEdge<W> e = it.next();
					System.out.print("("+e.getSource()+","+e.getTarget()+", "+e.getWeight()+")->" );
			}
			System.out.println();
		}
	}

	
}
