package practica2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author morales
 *
 *         Clases que almacena el contenido de un cajero. Este se componene de
 *         una caja de billetes de billetes disponibles para ser reintegrados.
 *         Se puedeñ añadir más billetes en cualquier momento y el cajero puede
 *         ftaccionarlos si es necesario. Los billetes son representados como
 *         enteros y almacenados internamente en una cola con prioridad.
 * 
 *         La clase define una lista estática con los billetes legales.
 */
public class Cajero {

	/**
	 * Lista estática con los billetes de valor legal.
	 */
	static private List<Integer> billetesLegales = Arrays.asList(1, 2, 5, 10, 20, 50, 100, 200, 500);

	/**
	 * Cola que almacena todos los billetes disponibles en el cajero.
	 */
	private PriorityQueue<Integer> caja = new PriorityQueue<Integer>();

	/**
	 * Valor total de los billetes almacenados
	 */
	private int saldo = 0;

	/**
	 * Toma una billete legal y la fracciona en billetes de menor valor.
	 * 
	 * Devuelve una lista de billetes cuyo valor es igual al del parámetro. Si
	 * el billete vale 1, la lista sólo contiene un billete cone se valor.
	 * 
	 * @param billete Billete de valor legal
	 * @return Lista con los billetes.
	 * @throwns <code>IllegalArgumentException</code> si el parámetro no es un
	 *          billete legal.
	 */
	static public List<Integer> fraccionar(int billete) {
		int b = billetesLegales.indexOf(billete);

		if (b == -1)
			throw new IllegalArgumentException();

		List<Integer> retVal = new LinkedList<Integer>();

		if (billete == 1)
			retVal.add(1);
		else {
			int restante = billete;
			b--;
			while (restante != 0) {
				retVal.add(billetesLegales.get(b));
				restante -= billetesLegales.get(b);
				if (restante < billetesLegales.get(b))
					b--;
			}

		}

		return retVal;
	}

	/**
	 * Devuelve el valor de todos los billetes del cajero
	 * 
	 * @return El saldo del cajero
	 */
	public int saldo() {
		return saldo;
	}

	/**
	 * Toma una lista de billetes y los almacena en el cajero.
	 * 
	 * @param fajo Lista de billetes legales con los que recargar el cajero.
	 * @throws <code>IllegalArgumentExcepetion<\code>
	 *             Si alguno de los billetes tiene una valor ilegal.
	 */
	public void recargar(List<Integer> fajo) {
		for (Integer billete : fajo) {
			if (!billetesLegales.contains(billete))
				throw new IllegalArgumentException();

			caja.add(billete);
			saldo += billete;
		}
	}

	/**
	 * Reintegra una lista de billetes por la contidad solicitada.
	 * 
	 * Extrae del cajero una lista de billetes cuyo valor total es el de la suma
	 * solicitada. Tomará del cajero los billetes más pequeños disponibles. En
	 * caso de que no tuviera billetes lo suficiente pequeños los fraccionará.
	 * La lista estará ordenada por valos de menor a mayor.
	 * 
	 * @param cantidad
	 * @return Lista de billetes legales ordenados de menor a mayor.
	 *         <code>null</code> si no hay suficiente saldo para procesar el
	 *         reintegro.
	 */
	public List<Integer> reintegrar(int cantidad) {
		if (saldo<cantidad)
			return null;
		List<Integer> listaBilletes= new LinkedList<Integer>();
		List<Integer> fraccionados=new LinkedList<Integer>();
		int totalAcumulado=0;
		int billete;
		
		while(totalAcumulado<cantidad){
			billete=caja.remove();
			totalAcumulado+=billete;
			if (totalAcumulado>cantidad){
				fraccionados=fraccionar(billete);
				caja.addAll(fraccionados);
				totalAcumulado-=billete;
			}else
				listaBilletes.add(billete);
		}
		saldo-=cantidad;
		
		PriorityQueue<Integer> aux= new PriorityQueue<Integer>();
		aux.addAll(listaBilletes);
		listaBilletes.clear();
		while(!aux.isEmpty()){
			listaBilletes.add(aux.remove());
		}
		return listaBilletes;
	}
}
