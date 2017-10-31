package practica2;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Polinomio {

	// La lista de monomios
	private List<Monomio> datos = new LinkedList<Monomio>();

	/**
	 * Constructor por defecto. La lista de monomios está vacía
	 */
	public Polinomio() {
	};

	/**
	 * Constructor a partir de un vector. Toma los coeficientes de los monomios
	 * de los valores almcenados en el vector, y los exponentes son las
	 * posiciones dentro del vector. Si <code>v[1]</code>v[i] contiene
	 * <code>a</code> el monomio contruido será aX^i.
	 *  
	 * Por ejemplo:
	 * 
	 * v = [-1, 0, 2] -> 2X^2 -1X^0
	 * 
	 * @param v
	 */
	public Polinomio(double v[]) {
		for (int i=0;i<v.length;i++){
			if (Cero.esCero(v[i]))
				continue;
			Monomio nuevo=new Monomio(v[i],i);
			datos.add(nuevo);
		}
	}

	/**
	 * Constructor copia
	 * 
	 * @param otro
	 * @throws <code>NullPointerException</code>
	 *             si el parámetro es nulo
	 */
	public Polinomio(Polinomio otro) {
		if (otro == null)
			throw new NullPointerException();

		for (Monomio item : otro.datos)
			datos.add(new Monomio(item));
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		Iterator<Monomio> iter = datos.iterator();

		boolean primero = true;

		while (iter.hasNext()) {
			Monomio item = iter.next();

			if (item.coeficiente < 0) {
				str.append('-');
				if (!primero)
					str.append(' ');
			} else if (!primero)
				str.append("+ ");

			str.append(Math.abs(item.coeficiente));
			if (item.exponente > 0)
				str.append('X');
			if (item.exponente > 1)
				str.append("^" + item.exponente);

			if (iter.hasNext())
				str.append(' ');

			primero = false;
		}

		if (primero)
			str.append("0.0");

		return str.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Polinomio other = (Polinomio) obj;

		if (this.datos.size() != other.datos.size())
			return false;

		Iterator<Monomio> iter1 = this.datos.iterator();
		Iterator<Monomio> iter2 = other.datos.iterator();

		while (iter1.hasNext())
			if (!(iter1.next().equals(iter2.next())))
				return false;

		return true;
	}

	/**
	 * Devuelve la lista de monomios
	 *
	 */
	public List<Monomio> monomios() {
		return datos;
	}

	/**
	 * Suma un polinomio sobre <code>this</code>, es decir, modificando el
	 * polinomio local. Debe permitir la auto autosuma, es decir,
	 * <code>polinomio.sumar(polinomio)</code> debe dar un resultado correcto.
	 * Devuelve el resultado de la suma, es decir <code>this</code>.
	 * @param otro
	 * @return <code>this<\code>
	 * @throws <code>NullPointeExcepction</code> en caso de que el parámetro sea <code>null</code>.
	 */
	public void sumar(Polinomio otro) {
		if (otro==null)
			throw new NullPointerException();
		
		ListIterator<Monomio> iter1=this.datos.listIterator();
		ListIterator<Monomio> iter2=otro.datos.listIterator();
		
		int expo1,expo2;
		Monomio mon1,mon2;
		while (iter1.hasNext() && iter2.hasNext()){
			mon1=iter1.next();
			mon2=iter2.next();
			expo1=mon1.exponente;
			expo2=mon2.exponente;
			if (expo1==expo2){
				if(!Cero.esCero(mon2.coeficiente+mon1.coeficiente))
					iter1.set(new Monomio(mon1.coeficiente+mon2.coeficiente,expo1));
				else
					iter1.remove();
			}else if (expo1>expo2){
				iter1.previous();
				iter1.add(mon2);
			}else if (expo1<expo2){
				iter2.previous();
			}
				
		}
		while (iter2.hasNext()){
			mon2=iter2.next();
			iter1.add(mon2);
		}
	}

	/** Multiplica el polinomio <code>this</code> por un monomio. 
	 * @param mono
	 */
	public void multiplicarMonomio(Monomio mono) {
		if (Cero.esCero(mono.coeficiente))
			datos.clear();

		for (Monomio m : datos) {
			m.coeficiente *= mono.coeficiente;
			m.exponente += mono.exponente;
		}
			}
}
