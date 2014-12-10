package fi.omapizzeria.sivusto.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fi.omapizzeria.sivusto.bean.Juoma;
import fi.omapizzeria.sivusto.bean.Pizza;
import fi.omapizzeria.sivusto.bean.Tayte;
import fi.omapizzeria.sivusto.bean.Tuote;
import fi.omapizzeria.sivusto.bean.WebUser;
import fi.omapizzeria.sivusto.dao.DAOPoikkeus;
import fi.omapizzeria.sivusto.service.AdminService;


/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
	public static final String FRONT_PAGE = "kirjaudu.jsp";
	private static final String INSIDE_PAGE = "WEB-INF/admin.jsp";
	public static final String SESSION_ATTR_WEBUSER = "kayttajatiedot";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
			
			
		// pizzat listaan
		ArrayList<Tuote> pizzat;
		ArrayList<Tayte> taytteet;
		ArrayList<Tuote> juomat;
		try {
			// servicelt� pizzat, juomat ja taytelista
			AdminService aService = new AdminService();

			pizzat = aService.haePizzalistaAdmin();
			taytteet = aService.haeTaytelistaAdmin();
			juomat = aService.haeJuomalistaAdmin();

		} catch (DAOPoikkeus e) {
			throw new ServletException(e);
		}

		// listat requestin attribuutiksi
		request.setAttribute("plista", pizzat);
		request.setAttribute("tayte", taytteet);
		request.setAttribute("jlista", juomat);

		
		
	
		
	
		
		// otetaan k�ytt�j�tiedot sessiosta
				WebUser user = (WebUser) request.getSession().getAttribute(
						SESSION_ATTR_WEBUSER);
				if (user == null) // jos k�ytt�j�tietoja ei l�ydy, heitet��n etusivulle
					request.getRequestDispatcher(FRONT_PAGE).forward(request, response);
				else
					// mik�li k�ytt�j�tiedot l�ytyv�t, p��stet��n sis��n
					request.getRequestDispatcher(INSIDE_PAGE)
							.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// jos tuleva parametri action on del ajetaan poisto else lis�ys

		if (request.getParameter("action").equals("add")) {

			// infot formista
			String syotettyNimi = request.getParameter("pizza");
			String syotettyHinta = request.getParameter("hinta");
			int id = 0;
			String valitutTaytteet[];

			// hinta stringist� doubleksi
			double d = Double.parseDouble(syotettyHinta);

			// html:st� tulleet infot pitsaksi
			Pizza p = new Pizza(id, syotettyNimi, d);

			// Checkboxeista tulleet t�ytteet listaksi
			valitutTaytteet = request.getParameterValues("tayte");

			try {
				AdminService aService = new AdminService();
				// olio lisaysmetodiin
				aService.viePizza(p, valitutTaytteet);
			} catch (DAOPoikkeus e) {
				throw new ServletException(e);
			}

			// pitsa lis�tty -> redirect added parameterill�
			response.sendRedirect("admin?added=true");

		}
		
		if (request.getParameter("action").equals("add2")) {

			// infot formista
			String syotettyNimi = request.getParameter("juoma");
			String syotettyHinta = request.getParameter("hinta");
			int id = 0;

			// hinta stringist� doubleksi
			double d = Double.parseDouble(syotettyHinta);

			// infot olioksi
			Juoma j = new Juoma(id, syotettyNimi, d);

			try {
				// uus pizzadao
				AdminService aService = new AdminService();
				// olio lisaysmetodiin
				aService.vieJuoma(j);
			} catch (DAOPoikkeus e) {
				throw new ServletException(e);
			}

			// pitsa lis�tty -> redirect added parameterill�
			response.sendRedirect("admin?added2=true");

		}

		if (request.getParameter("action").equals("del")) {

			// id formista
			String syotettyId = request.getParameter("id");
			String nimi = null;
			double d = 0;

			// int stringist� intiksi
			int id = Integer.parseInt(syotettyId);

			// infot olioksi
			Pizza p = new Pizza(id, nimi, d);
			System.out.println(p);

			try {
				// uus pizzadao
				AdminService aService = new AdminService();
				// olio poistometodiin
				aService.poistaPizza(p);
			} catch (DAOPoikkeus e) {
				throw new ServletException(e);
			}

			// pitsa poistettu -> redirect removed parameterill�
			response.sendRedirect("admin?removed=true");

		}

		if (request.getParameter("action").equals("del2")) {

			// id formista
			String syotettyId = request.getParameter("id");
			String nimi = null;
			double d = 0;

			// int stringist� intiksi
			int id = Integer.parseInt(syotettyId);

			// infot olioksi
			Juoma j = new Juoma(id, nimi, d);

			try {
				// uus pizzadao
				AdminService aService = new AdminService();
				// olio poistometodiin
				aService.poistaJuoma(j);;
			} catch (DAOPoikkeus e) {
				throw new ServletException(e);
			}

			// pitsa poistettu -> redirect removed parameterill�
			response.sendRedirect("admin?removed2=true");

		}

		if (request.getParameter("action").equals("piilota")) {

			// id formista
			String syotettyId = request.getParameter("id");
			String nimi = null;
			double d = 0;

			// int stringist� intiksi
			int id = Integer.parseInt(syotettyId);

			// infot olioksi
			Juoma j = new Juoma(id, nimi, d);

			try {
				// uus pizzadao
				AdminService aService = new AdminService();

				// olio poistometodiin
				aService.piilotaJuoma(j);
			} catch (DAOPoikkeus e) {
				throw new ServletException(e);
			}

			// pitsa piilotettu -> redirect removed parameterill�
			response.sendRedirect("admin?piilotettu2=true");
		}

		if (request.getParameter("action").equals("piilota2")) {

			// id formista
			String syotettyId = request.getParameter("id");
			String nimi = null;
			double d = 0;

			// int stringist� intiksi
			int id = Integer.parseInt(syotettyId);

			// infot olioksi
			Pizza p = new Pizza(id, nimi, d);

			try {
				// uus pizzadao
				AdminService aService = new AdminService();

				// olio poistometodiin
				aService.piilotaPizza(p);
			} catch (DAOPoikkeus e) {
				throw new ServletException(e);
			}

			// pitsa piilotettu -> redirect removed parameterill�
			response.sendRedirect("admin?piilotettu=true");
		}

		if (request.getParameter("action").equals("palauta")) {

			// id formista
			String syotettyId = request.getParameter("id");
			String nimi = null;
			double d = 0;

			// int stringist� intiksi
			int id = Integer.parseInt(syotettyId);

			// infot olioksi
			Juoma j = new Juoma(id, nimi, d);

			try {
				// uus pizzadao
				AdminService aService = new AdminService();
				// olio poistometodiin
				aService.palautaJuoma(j);
			} catch (DAOPoikkeus e) {
				throw new ServletException(e);
			}

			// pitsa piilotettu -> redirect removed parameterill�
			response.sendRedirect("admin?palautettu2=true");
		}

		if (request.getParameter("action").equals("palauta2")) {

			// id formista
			String syotettyId = request.getParameter("id");
			String nimi = null;
			double d = 0;

			// int stringist� intiksi
			int id = Integer.parseInt(syotettyId);

			// infot olioksi
			Pizza p = new Pizza(id, nimi, d);

			try {
				// uus pizzadao
				AdminService aService = new AdminService();
				// olio poistometodiin
				aService.palautaPizza(p);
			} catch (DAOPoikkeus e) {
				throw new ServletException(e);
			}

			// pitsa piilotettu -> redirect removed parameterill�
			response.sendRedirect("admin?palautettu=true");
		}

	}

}
