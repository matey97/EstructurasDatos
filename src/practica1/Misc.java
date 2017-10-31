package practica1;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.LinkedList;

public class Misc {

	/**
	 * Sume los elementos del vector par pares, almacenando el resultado en el
	 * primero, y borrando el segundo
	 * 
	 * @param v
	 *            vector de <code>long</code>
	 * @return El vector comprimido
	 */
	public static long[] compress(long v[]) {

		if (v == null)
			throw new InvalidParameterException();
		
		long retVal[];
		
		if (v.length%2==1){
			retVal = new long[(v.length) / 2 + 1];
		}else{
			retVal = new long[(v.length) / 2];
		}
		

		for (int i = 0; i < v.length - 1; i += 2)
			retVal[i / 2] = v[i] + v[i + 1];

		if (v.length % 2 == 1)
			retVal[retVal.length - 1] = v[v.length - 1];

		return retVal;
	}

	// *****************************************
	/**
	 * Cuenta el número de tokens diferentes de una vector de <code>char</code>.
	 * Un token es cualquier grupo de caracteres delimitados por espacios en
	 * blanco, o el principio y el final del fichero
	 * 
	 * @param s
	 * @return
	 */
	public static int count_tokens(char s[]) {
		boolean inToken=false;
		int retVal = 0;

		for (int i = 0; i < s.length; i++) {
			if (!inToken && s[i] != ' ') {
				retVal++;
				inToken = true;
			} else if (inToken && s[i] == ' ')
				inToken = false;
		}

		return retVal;
	}

	/**
	 * Toma un <code>String </code> que contiene un texto formado por palabras
	 * separadas por un cantidad variable de espacios en blanco. Devuelve un
	 * vector de <code>String</code> con las palabras separadas, en el que cada
	 * elemento contiene una única palabra. Todos los espacios en blanco son
	 * descartados. Una cadena vacía, sólo con espacios en blanco o
	 * <code>null</code>produce como resultado un vector de tamaño cero.
	 * 
	 * @param s
	 * @return
	 */
	public static String[] split(String s) {
		if (s == null)
			return new String[0];
		char auxS[] = s.toCharArray();

		// Cuenta el númoro de palabras, y crea el vector resultado
	
		String retVal[] = new String[count_tokens(auxS)];

		int first = 0, last = 0;
		int w = 0;
		boolean inToken = false;

		// Separa los tokens y los copia en el vector resultado
		for (int i = 0; i < auxS.length; i++) {
			if (!inToken && auxS[i] != ' ') {
				first = i;
				inToken = true;
			} else if (inToken && ( i==auxS.length-1 || (auxS[i] == ' '))) {
				inToken = false;

				if (i == auxS.length - 1 && auxS[i] != ' ')
					last = i + 1;
				else
					last = i;

				retVal[w] = new String(auxS, first, last - first);
				w++;
			}
		}

		return retVal;
	}

	// *****************************************
	/**
	 * Añade los elementos de una lista de enteros <code>dest</code> en
	 * <code>l</code>. La addición se produce por valor y no se insertan
	 * elementos repetidos.
	 * 
	 * @param dest
	 * @param l
	 */
	private static void addSorted(List<Integer> dest, List<Integer> l) {
		if (l == null)
			return;
		for (int i = 0; i < l.size(); i++) {
			int item = l.get(i);

			if (dest.indexOf(item)==-1){
				int j = 0;
				while ((j < dest.size()) && (item > dest.get(j)))
					j++;

				dest.add(j, item);
			}
		}
	}

	/**
	 * Toma dos listas de <code>Integer</code>, y devuelve una nueva lista con
	 * todos elementos de la ambas listas, ordenados de menor a mayor y sin
	 * repeticiones. Los parámetros <code>null</code> se interpretan como listas
	 * vacías, y el resultado de unir dos parametrós <code>null</code> es una
	 * lista vacía.
	 * 
	 * @param l1
	 * @param l2
	 * @return
	 */
	public static List<Integer> join(List<Integer> l1, List<Integer> l2) {
		List<Integer> retVal = new LinkedList<Integer>();

		Misc.addSorted(retVal, l1);
		Misc.addSorted(retVal, l2);

		return retVal;
	}

	// *****************************************
	/**
	 * Comprueba si dos listas de <code>String </code> son equivalentes. Es decir, contienen
	 * los mismos elementos, y en caso de elementos repetidos, el mismo número
	 * de veces en ambas listas. No es necesario que estén en el mismo orden.
	 * Los parámetros <code>null</code> se interpretan como listas vacías.
	 * 
	 * @param l1
	 * @param l2
	 * @return
	 */
	public static boolean equivalent(List<String> l1, List<String> l2) {
		if (l1 == l2)
			return true;
		
		if ((l1==null || l2==null))
			return false;

		if (l1.size() != l2.size())
			return false;

		List<String> aux = new LinkedList<String>(l1);

		for (int i = 0; i < l2.size(); i++) {
			int pos = aux.indexOf(l2.get(i));

			if (pos == -1)
				return false;
			else
				aux.remove(pos); 
		}

		return true;
	}
}
