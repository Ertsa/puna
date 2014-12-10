package fi.omapizzeria.sivusto.dao;

/**
 * T�m� luokka sis�lt�� virheilmoituksen jos k�ytt�j�nimi l�ytyy jo tietokannasta.
 * @author Aleksi, Joona
 *
 */
public class UsernameVarattuPoikkeus extends Exception {
	private static final long serialVersionUID = 1L;

	public UsernameVarattuPoikkeus() {
		super(
				"Username on jo varattuna jollain toisella k�ytt�j�ll� tietokannassa.");
	}
}