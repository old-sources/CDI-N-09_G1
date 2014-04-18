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

import model.Competence;
import model.Personne;
import model.Projet;
import model.Promotion;

import org.imie.service.ServiceGestionEcoleJPALocal;

/**
 * Servlet implementation class TP3_Controller ????
 */
@WebServlet("/HCompetence/*")
public class HCompetence extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB ServiceGestionEcoleJPALocal serviceGestionEcole;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HCompetence() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HCompetence Get"); // console verif

		// on met toutes les competences dans foundCompetences
		Competence searchCompetences = new Competence();
		List<Competence> foundCompetences = serviceGestionEcole.rechercherCompetence(searchCompetences);
		request.setAttribute("foundCompetences", foundCompetences);
		
		// on passe tous les profils en request pour la liste de la popup modif projet
		Personne searchPersonne = new Personne();
		List<Personne> foundPersonnes = serviceGestionEcole
				.rechercherPersonne(searchPersonne);
		request.setAttribute("foundPersonnes", foundPersonnes);
		
		// loguedPerson passé en request
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Personne loguedPerson = new Personne();
		loguedPerson=(Personne) httpServletRequest.getSession().getAttribute("authentifiedPersonne");
		request.setAttribute("loguedPerson", loguedPerson);
		
		request.getRequestDispatcher("/WEB-INF/JCompetence.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("HCompetence Post");
		
		// recherche du projet à modifier
		Competence updatedCompetence = new Competence();

		// affectation des nouvelles valeurs
	
		////////////////////////////////////  delete update create  /////////////////////////////////////
		
		
		
		if (request.getParameter("delete") != null) {
			System.out.println("HCompetence Post delete");
			try {
				Integer inputCompId = Integer.valueOf(request.getParameter("inputCompId"));
				updatedCompetence.setCompId(inputCompId);
				System.out.println("inputProjId : "+inputCompId);
				serviceGestionEcole.deleteCompetence(updatedCompetence);
			}
			catch (NumberFormatException e) {
				// parametres non corrects : pas de suppression
			}
		}

		if (request.getParameter("create") != null) {
			System.out.println("HProjet POST create");
			serviceGestionEcole.insertCompetence(updatedCompetence);
		}

		if (request.getParameter("update") != null) {
			System.out.println("HProjet POST update");
			Integer inputProjId = Integer.valueOf(request.getParameter("inputProjId"));
			System.out.println("inputProjId = "+inputProjId);
			updatedCompetence.setCompId(inputProjId);
			serviceGestionEcole.updateCompetence(updatedCompetence);
		}

		response.sendRedirect("/GTC/HCompetence");
	}

}
