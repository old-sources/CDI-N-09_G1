package org.imie;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Personne;
import model.Projet;
import model.Promotion;
import model.Travaille;

import org.imie.service.ServiceGestionEcoleJPALocal;

/**
 * Servlet implementation class TP3_Controller
 */
@WebServlet("/HTravaille/*")
public class HTravaille extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB ServiceGestionEcoleJPALocal serviceGestionEcole;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HTravaille() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HTravaille Get");
		// a faire quelque soit le pattern

		List<Travaille> foundTravailles = serviceGestionEcole
				.rechercherTravaille(new Travaille());
		request.setAttribute("foundTravailles", foundTravailles);
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Personne loguedPerson = new Personne();
		loguedPerson=(Personne) httpServletRequest.getSession().getAttribute("authentifiedPersonne");
		request.setAttribute("loguedPerson", loguedPerson);
		request.setAttribute("foundTravailles",foundTravailles);
		List<Personne> foundPersonnes = serviceGestionEcole
				.rechercherPersonne(new Personne());
		request.setAttribute("foundPersonnes", foundPersonnes);
		
		
		request.getRequestDispatcher("/WEB-INF/JTravaille.jsp").forward(
				request, response);

	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("HTravaille Post");
		
		// recherche de la Travaille à modifier
		Travaille updatedTravaille = new Travaille();

		//updatedTravaille = serviceGestionEcole.rechercherTravaille(updatedTravaille)
		//		.get(0);
		// affectation des nouvelles valeurs
		Integer inputProjetId = Integer.valueOf(request.getParameter("inputProjetId"));
		Projet projet = new Projet();
		projet.setProjId(inputProjetId);
		updatedTravaille.setProjet(serviceGestionEcole.rechercherProjet(projet).get(0));
		
		Integer inputPersonneId = Integer.valueOf(request.getParameter("inputPersonneId"));
		Personne personne = new Personne();
		projet.setProjId(inputPersonneId);
		updatedTravaille.setPersonne(serviceGestionEcole.rechercherPersonne(personne).get(0));
		

		if (request.getParameter("create") != null) {
			serviceGestionEcole.insertTravaille(updatedTravaille);
		}

		if (request.getParameter("update") != null) {
			Integer inputTravailleId = Integer.valueOf(request.getParameter("inputTravailleId"));
			updatedTravaille.setTrvId(inputTravailleId);
			serviceGestionEcole.updateTravaille(updatedTravaille);
		}

		response.sendRedirect("/GTC/HPromotion/");
	}

}
