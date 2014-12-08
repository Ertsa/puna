package fi.omapizzeria.sivusto.bean;

/**
 * 
 * T�m�n luokka sis�lt�� Juoma olion tietosis�ll�n.
 * @author Joona, Aleksi
 *
 */


/**
 * Juoma.java on Tuote -luokan aliluokka.
 */

public class Juoma extends Tuote {
		


	public Juoma(int id, String nimi, double hinta) {
		this.id = id;
		this.nimi = nimi;
		this.hinta = hinta;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNimi() {
		return nimi;
	}

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	public double getHinta() {
		return hinta;
	}

	public void setHinta(double hinta) {
		this.hinta = hinta;
	}

	@Override
	public String toString() {
		return "Juoma [id=" + id + ", nimi=" + nimi + ", hinta=" + hinta + "]";
	}

}
