package org.imie;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Personne;
import model.Promotion;

import org.imie.service.ServiceGestionEcoleJPALocal;

/**
 * Servlet implementation class TP3_Controller
 */
@WebServlet("/TP3_Controller/*")
public class TP3_Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB ServiceGestionEcoleJPALocal serviceGestionEcole;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TP3_Controller() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Pattern pattern = Pattern.compile(".*/TP3_Controller/([0-9]*)");
		Matcher matcher = pattern.matcher(request.getRequestURL());
//		IGestionEcoleService gestionEcoleService = ConcreteSingletonFactory
//				.getInstance().creategGestionEcoleService();
		Personne searchPersonne = new Personne();
		request.setAttribute("promotions",serviceGestionEcole.rechercherPromotion(new Promotion()));
		if (matcher.find()) {
			try {
				searchPersonne.setId(Integer.valueOf(matcher.group(1)));
				List<Personne> foundPersonnes = serviceGestionEcole
						.rechercherPersonne(searchPersonne);
				if (foundPersonnes.size() > 0) {
					request.setAttribute("personneSelected",
							foundPersonnes.get(0));
					request.getRequestDispatcher("/WEB-INF/TP3.jsp").forward(
							request, response);
				} else {
					response.setStatus(404);
				}
				

			} catch (NumberFormatException e) {
				// url mal formée
				response.setStatus(400);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
