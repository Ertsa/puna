package fi.omapizzeria.sivusto.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fi.omapizzeria.sivusto.bean.Juoma;
import fi.omapizzeria.sivusto.bean.Ostos;
import fi.omapizzeria.sivusto.bean.Ostoskori;
import fi.omapizzeria.sivusto.bean.Pizza;
import fi.omapizzeria.sivusto.bean.Tuote;
import fi.omapizzeria.sivusto.dao.DAOPoikkeus;
import fi.omapizzeria.sivusto.service.PizzaService;

/**
 * Servlet implementation class MenuServlet
 */
@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public MenuServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// luodaan listat
		List<Pizza> pizza;
		List<Juoma> juoma;

		try {
			// tietokannasta tuotteet listoihin
			PizzaService p = new PizzaService();
			pizza = p.haePizzaLista();
			juoma = p.haeJuomaLista();
		} catch (DAOPoikkeus e) {
			throw new ServletException(e);
		}
		// listat requestin attribuutiksi
		request.setAttribute("plista", pizza);
		request.setAttribute("jlista", juoma);

		// ohjataan .jsp:lle
		request.getRequestDispatcher("menu.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		Ostoskori kori = (Ostoskori) session.getAttribute("kori");

		
		
		if (request.getParameter("action").equals("add")) {
			if (kori == null) {
				kori = new Ostoskori();
			}

			// haetaan ID formista
			String sid = request.getParameter("id");
			int id = Integer.parseInt(sid);

			String maara = request.getParameter("lkm");
			int lkm = Integer.parseInt(maara);

			
			
			// oregano/valkosipuli valinta checkboxista
			String klikattuOregano = request.getParameter("oregano");
			String klikattuValkosipuli = request.getParameter("valkosipuli");

			boolean oregano = false;
			boolean valkosipuli = false;
			
			
			// tsekataan ett� onko oregano/valkosipuli valittu
			if (klikattuOregano != null) {
				oregano = true;
			}
			if (klikattuValkosipuli != null) {
				valkosipuli = true;
			}
			
			

			// haetaan pizzaID:ll� pizzan tiedot tietokannasta
			try {
				
				PizzaService pService = new PizzaService();
				
				Pizza uusiPizza = pService.tuoPizza(id);
				
				double rivihinta = lkm * uusiPizza.getHinta();
				
					//asetetaan mausteet pizzaolioon
				uusiPizza.setOregano(oregano);
				uusiPizza.setValkosipuli(valkosipuli);
				
				
				Tuote uusituote = uusiPizza;
				
				kori.lisaaOstoskoriin(uusituote, lkm, rivihinta);
				
				System.out.println(uusituote);
				
				//Hinnat sessioon
				double hinta = kori.getTilauksenHinta();
				request.getSession().setAttribute("hinta", hinta);
				request.getSession().setAttribute("ohinta", hinta*0.9);

			} catch (DAOPoikkeus e1) {
				e1.printStackTrace();
			}

			request.getSession().setAttribute("kori", kori);
			response.sendRedirect("/PizzeriaTyyni/menu");
			System.out.println(kori.getOstokset());

		}

		
		
		
		
		if (request.getParameter("action").equals("jadd")) {
			if (kori == null) {
				kori = new Ostoskori();
			}

			// haetaan ID formista
			String sid = request.getParameter("id");
			int id = Integer.parseInt(sid);

			String maara = request.getParameter("lkm");
			int lkm = Integer.parseInt(maara);

			
			
			
			// haetaan pizzaID:ll� pizzan tiedot tietokannasta
			try {
				PizzaService pService = new PizzaService();
				Juoma uusiJuoma = pService.tuoJuoma(id);
				double rivihinta = lkm * uusiJuoma.getHinta();
				
				Tuote uusiTuote = uusiJuoma;
				
				kori.lisaaOstoskoriin(uusiTuote, lkm, rivihinta);
				
				double hinta = kori.getTilauksenHinta();
				request.getSession().setAttribute("hinta", hinta);
				request.getSession().setAttribute("ohinta", hinta*0.9);

			} catch (DAOPoikkeus e1) {
				e1.printStackTrace();
			}

			request.getSession().setAttribute("kori", kori);
			response.sendRedirect("/PizzeriaTyyni/menu");
			System.out.println(kori.getOstokset());

		}
		
	
	
		
		
		// ostoskorista tuotteen poistaminen
		if (request.getParameter("action").equals("del")) {

			// haetaan oId ostoskorista
			
			String poistettavaRivi = request.getParameter("rivinumero");
			int poistettavaRiviINT = Integer.parseInt(poistettavaRivi);

			kori.poistaOstoskorista(poistettavaRiviINT);

			double hinta = kori.getTilauksenHinta();
			request.getSession().setAttribute("hinta", hinta);
			request.getSession().setAttribute("ohinta", hinta*0.9);
		
			// tallennetaan sessioon
			request.getSession().setAttribute("kori", kori);
			// siirryt��n takaisin koriin
			response.sendRedirect("/PizzeriaTyyni/kori.jsp");
			System.out.println(kori.getOstokset());

		}
		
		if (request.getParameter("action").equals("userDel")) {

			// haetaan oId ostoskorista
			String poistettavaRivi = request.getParameter("rivinumero");
			int x = Integer.parseInt(poistettavaRivi);

			
			kori.poistaOstoskorista(x);
				
				double hinta = kori.getTilauksenHinta();
				request.getSession().setAttribute("hinta", hinta);
				request.getSession().setAttribute("ohinta", hinta*0.9);
			
			// tallennetaan sessioon
			request.getSession().setAttribute("kori", kori);
			// siirryt��n takaisin koriin
			response.sendRedirect("menu");
			System.out.println(kori.getOstokset());
			
		}
		
		
		
		if (request.getParameter("action").equals("clear")) {
			
			try {
				session.invalidate();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.sendRedirect("menu");
		}
		if (request.getParameter("action").equals("tyhjkori")) {
			
			try {
				session.invalidate();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.sendRedirect("/PizzeriaTyyni/kori.jsp");
		}
		
	}
}