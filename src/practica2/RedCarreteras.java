package practica2;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedCarreteras {
	private Map<String, Map<String, Integer>> red;

	/**
	 * Constructor
	 * 
	 * Crea una red de carreteras vacías.
	 */
	public RedCarreteras() {
		red=new HashMap<String,Map<String,Integer>>();
	}

	/**
	 * Valida que un tramo sea correcto.
	 * 
	 * Es decir, que ninguna de las dos ciudades sea null, y que la distancia sea
	 * mayor de cero. No se admiten tramos de una ciudad con sigo misma. En el
	 * caso de que un tramo no sea válido produce una excepción.
	 * 
	 * @param una,
	 *            una ciudad
	 * @param otra,
	 *            la otra ciudad
	 * @param distancia,
	 *            la distancia del tramo
	 * @throws InvalidParamenterException
	 *             si el tramo no es válido.
	 */
	private void validarTramo(String una, String otra, int distancia) {
		if (una == null || otra == null || una.equals(otra) || distancia < 1)
			throw new InvalidParameterException();
	}

	/**
	 * Devuelve un conjunto con todas las ciudades de la red.
	 */
	public Set<String> ciudades() {
		return red.keySet();
	}

	/**
	 * Añade un tramo a la red.
	 * 
	 * Valida que el tramo sea valido. Si alguna de las dos ciudades no existiá
	 * previamente en la red, la añade. El tramo se añadirá dos veces, una para
	 * cada ciudad. En caso de que el tramo ya existiera remplazará el valor de
	 * la distancia.
	 * 
	 * @return Distancia previa del tramo, -1 si el tramo no exitía.
	 * @throws InvalidParamenterException
	 *             si el tramo no es válido.
	 */
	public int anadeTramo(String una, String otra, int distancia) {
		validarTramo(una, otra, distancia);
	
		int antiguaDist=-1;
		
		if (!red.containsKey(una)) 							//Si no estan en el diccionario principal los añado
			red.put(una, new HashMap<String,Integer>());
	
		if (!red.containsKey(otra))
			red.put(otra,new HashMap<String,Integer>());
		
		if (red.get(una).containsKey(otra))					//Compruebo si una esta relacionada con otra, lo que indicaria que ese tramo ya estaba.
			antiguaDist=red.get(una).get(otra);				//Por tanto, guardo distancia anigua
		red.get(una).put(otra, distancia);					//Añado/Sobreescribo tramo para una
		red.get(otra).put(una, distancia);					//Añado/Sobreescribo tramo para otra
	
		return antiguaDist;
	}

	/**
	 * Comprueba si existe un tramo entre dos ciudades.
	 * 
	 * @return La distancia del tramo, o -1 si no existe
	 */
	public int existeTramo(String una, String otra) {
		if (!red.containsKey(una) || !red.containsKey(otra))
			return -1;

		Integer aux = red.get(una).get(otra);

		return (aux == null ? -1 : aux);
	}

	/**
	 * Borra el tramo entre dos ciudades si existe.
	 * 
	 * @return La distancia del tramo, o -1 si no existía.
	 */
	public int borraTramo(String una, String otra) {
		int d = existeTramo(una, otra);

		if (d < 0)
			return d;

		red.get(una).remove(otra);
		red.get(otra).remove(una);

		return d;
	}

	/**
	 * Comprueba si un camino es posible.
	 * 
	 * Un camino es una lista de ciudades. El camino es posible si existe un
	 * tramo entre cada para de ciudades consecutivas. Si dos ciudades
	 * consecutivas del camino son la misma el camino es posible y la distancia
	 * añadida es 0. Si el camino incluye una sóla ciudad de la red el resultado es 0.
	 * 
	 * @return La distancia total del camino, es decir la suma de las distancias
	 *         de los tramos, o -1 si el camino no es posible o no incluye ninguna ciudad.
	 */
	public int compruebaCamino(List<String> camino) {
		if(camino.size()==0)
			return -1;
		if(camino.size()==1 && red.containsKey(camino.get(0)))	//Si solo hay una ciudad y esta en el diccionario
			return 0;
		
		int distancia=0,distPosible;
		
		for(int i=0;i<camino.size()-1;i++){
			if (camino.get(i)==null)				
				return -1;
			if (camino.get(i).equals(camino.get(i+1)))	//En caso de la ciudad en posicion i igual a posicion i+1
				continue;								//Continua con una nueva iteracion
			distPosible=existeTramo(camino.get(i),camino.get(i+1));	//Si devuelve -1 no existe el tramo
			if (distPosible!=-1)
				distancia+=distPosible;
			else
				return -1;
			
		}
		return distancia;
	}

}
