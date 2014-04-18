package org.imie;

import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Competence;
import model.Personne;
//import model.Projet;
//import model.Promotion;

import org.imie.service.ServiceGestionEcoleJPALocal;

/**
 * Servlet implementation class TP3_Controller ????
 */
@WebServlet("/Competence/*")
public class HCompetence extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	ServiceGestionEcoleJPALocal serviceGestionEcole;

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
		List<Competence> foundCompetences = serviceGestionEcole
				.rechercherCompetence(searchCompetences);
		
		//mis en commentaire car passé en méthode
//		// Affectation de la liste des enfants comme attributs
//		for (Competence comp : foundCompetences) {
//			Competence searchCompChild = new Competence(); // initialisation modèle : compétence vide
//			searchCompChild.setCompetence(comp); // affectation du père
//			List<Competence> resultChild = serviceGestionEcole.rechercherCompetence(searchCompChild); 
//			//System.out.println("HCompetence Post Child");
//			// recherche de toute les compétences ayant comp pour père
//			// affectation de cette liste à comp
//			comp.setCompetences(resultChild);
//		}	
		serviceGestionEcole.setChildCompetence(foundCompetences);
	
		request.setAttribute("foundCompetences", foundCompetences);

		// // on passe tous les profils en request pour ??
		// Personne searchPersonne = new Personne();
		// List<Personne> foundPersonnes = serviceGestionEcole
		// .rechercherPersonne(searchPersonne);
		// request.setAttribute("foundPersonnes", foundPersonnes);

		// loguedPerson passé en request
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Personne loguedPerson = new Personne();
		loguedPerson = (Personne) httpServletRequest.getSession().getAttribute(
				"authentifiedPersonne");
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

		// on liste toutes les compétences
		Competence searchCompetence = new Competence();
		
		List<Competence> foundCompetences = serviceGestionEcole
				.rechercherCompetence(searchCompetence);
		
		// Affectation de la liste des enfants comme attributs 
		for (Competence comp : foundCompetences) {
			Competence searchCompChild = new Competence(); // compétence vide
			searchCompChild.setCompetence(comp); // affectation du père
			List<Competence> resultChild = serviceGestionEcole.rechercherCompetence(searchCompChild); 
			// recherche de toute les compétences ayant comp pour père
			// affectation de cette liste à comp
			comp.setCompetences(resultChild);
		}		
		
		System.out.println("HCompetence Post attribution request");
		
		request.setAttribute("foundCompetences", foundCompetences);

		// affectation des nouvelles valeurs
		// ////////////////////////////////// delete update create
		// /////////////////////////////////////
		// if (request.getParameter("delete") != null) {
		// System.out.println("HCompetence Post delete");
		// try {
		// Integer inputCompId =
		// Integer.valueOf(request.getParameter("inputCompId"));
		// updatedCompetence.setCompId(inputCompId);
		// System.out.println("inputCompId : "+inputCompId);
		// serviceGestionEcole.deleteCompetence(updatedCompetence);
		// }
		// catch (NumberFormatException e) {
		// // parametres non corrects : pas de suppression
		// }
		// }

		// if (request.getParameter("create") != null) {
		// System.out.println("HCompetence POST create");
		// serviceGestionEcole.insertCompetence(updatedCompetence);
		// }
		//
		// if (request.getParameter("update") != null) {
		// System.out.println("HCompetence POST update");
		// Integer inputCompId =
		// Integer.valueOf(request.getParameter("inputCompId"));
		// System.out.println("inputCompId = "+inputCompId);
		// updatedCompetence.setCompId(inputCompId);
		// serviceGestionEcole.updateCompetence(updatedCompetence);
		// }

		response.sendRedirect("/GTC/HCompetence");
	}

}
