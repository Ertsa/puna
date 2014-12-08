package fi.omapizzeria.sivusto.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fi.omapizzeria.sivusto.bean.WebUser;

/**
 * Servlet implementation class BaseServlet
 */
@WebServlet("/login")
public class SiteServlet extends HttpServlet {
	public static final String FRONT_PAGE = "etusivu.jsp";
	private static final String INSIDE_PAGE = "admin.jsp";
	public static final String SESSION_ATTR_WEBUSER = "kayttajatiedot";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SiteServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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
}