package fi.omapizzeria.sivusto.service;

import java.util.ArrayList;
import java.util.List;

import fi.omapizzeria.sivusto.bean.Juoma;
import fi.omapizzeria.sivusto.bean.Pizza;
import fi.omapizzeria.sivusto.dao.DAOPoikkeus;
import fi.omapizzeria.sivusto.dao.TuoteDAO;

/**
 * T�m� luokka v�litt�� pyynt�j� MenuServletin ja TuoteDAO:n v�lill�
 * @author Aleksi, Joona
 *
 */

public class PizzaService {

	public PizzaService() throws DAOPoikkeus {
		super();
	}

	/**
	 * V�litt�� TuoteDAO:lle pyynn�n hakea pitsa ja t�ytelistat, sek� kokoaa ne yhdeksi listaksi.
	 * @return palauttaa TuoteDAO:lta saaduista tiedoista kootun pizzalistan.
	 * @throws DAOPoikkeus
	 */
	// hakee pizzalistan dao:lta
	public List<Pizza> haePizzaLista() throws DAOPoikkeus {
		
		TuoteDAO dao = new TuoteDAO();

		ArrayList<Pizza> pizzalista = dao.haePizzat();
		
		//suorittaan t�ytehaun tietokannasta ja yhdist�� ne pizzaolioon
		for(Pizza p : pizzalista) {
			p.setTaytteet(dao.haeTaytteetPizzalle(p.getId()));
		}
		return pizzalista;
	}
	
	/**
	 * V�litt�� TuoteDAO:lle pyynn�n hakea juomalistan.
	 * @return palauttaa TuoteDAO:lta saadun juomalistan.
	 * @throws DAOPoikkeus
	 */
	// hakee juomalistan dao:lta
	public List<Juoma> haeJuomaLista() throws DAOPoikkeus {
		
		TuoteDAO dao = new TuoteDAO();
		
		ArrayList<Juoma> juomalista = dao.haeJuomat();
		
		return juomalista;
	}
 
	/**
	 * T�m� metodi v�litt�� hakupyynn�n TuoteDAO:lle, asiakkaan lis�tess� yksitt�isen tuotteen ostoskoriin.
	 * @param id Tuotteen id.
	 * @return Palauttaa id:n perusteella tuodun pizzan.
	 * @throws DAOPoikkeus
	 */
	
	public Pizza tuoPizza(int id) throws DAOPoikkeus {
		
		TuoteDAO dao = new TuoteDAO();
		
		Pizza pizza = dao.haePizza(id);
		pizza.setTaytteet(dao.haeTaytteetPizzalle(pizza.getId()));
		
		return pizza;
	}
	
	/**
	 * T�m� metodi v�litt�� hakupyynn�n TuoteDAO:lle, asiakkaan lis�tess� yksitt�isen tuotteen ostoskoriin.
	 * @param id Tuotteen id.
	 * @return Palauttaa id:n perusteella tuodun juoman.
	 * @throws DAOPoikkeus
	 */
	
	public Juoma tuoJuoma(int id) throws DAOPoikkeus {
		
		TuoteDAO dao = new TuoteDAO();
		
		Juoma juoma = dao.haeJuoma(id);
		
		return juoma;
	}
	
}
