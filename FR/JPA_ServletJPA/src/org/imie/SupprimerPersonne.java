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
 * Servlet implementation class ModifierPersonne
 */
@WebServlet("/supprimerPersonne/*")
public class SupprimerPersonne extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	ServiceGestionEcoleJPALocal serviceGestionEcole;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SupprimerPersonne() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// declaration pattern create

		Pattern patternUpdate = Pattern.compile(".*/supprimerPersonne/([0-9]*)");
		Matcher matcherUpdate = patternUpdate.matcher(request.getRequestURL());

		Personne searchPersonne = new Personne();
		request.setAttribute("promotions",
				serviceGestionEcole.rechercherPromotion(new Promotion()));
		
		if (matcherUpdate.find()) {
			try {
				searchPersonne.setId(Integer.valueOf(matcherUpdate.group(1)));
				List<Personne> foundPersonnes = serviceGestionEcole.rechercherPersonne(searchPersonne);
				if (foundPersonnes.size() > 0) {
					serviceGestionEcole.deletePersonne(foundPersonnes.get(0));

				} else {
					response.setStatus(404);
				}
				
				
				

			} catch (NumberFormatException e) {
				// url mal formée : pas de suppression
			}
			request.getRequestDispatcher("/jsp/formulairePersonne.jsp")
					.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	}

}
