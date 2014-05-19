package org.imie;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Competence;
import model.Personne;

import org.imie.service.ServiceGestionCompJPALocal;
// import org.imie.service.ServiceGestionEcoleJPALocal;


/**
 * Servlet implementation class TP3_Controller ????
 */
@WebServlet("/Competence/*")
public class HCompetence extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB ServiceGestionCompJPALocal serviceGestionComp;
	
//	@EJB ServiceGestionEcoleJPALocal serviceGestionEcole;
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

		// on crée un modèle vide de type compétence
		Competence searchCompetences = new Competence();
		// on met toutes les competences dans foundCompetences
		//car recherche avec param null ramène la liste de toute les comp
		List<Competence> foundCompetences = serviceGestionComp
				.rechercherCompetence(searchCompetences);

		// Affectation de la liste des enfants comme attributs
		serviceGestionComp.setChildCompetence(foundCompetences);
		// on a initialisé la liste de tous les enfants
		// on la passe en paramètre à la request
		request.setAttribute("foundCompetences", foundCompetences);

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

		// on cree un modèle vide de competence
		Competence modelCompetence = new Competence();
		Competence parentCompetence = new Competence();

		// valeurs passés en request
		// système d'erreur à mettre en place si non initialisé
		if (request.getParameter("inputLibelleComp") != null) {
			String inputLibelComp = request.getParameter("inputLibelleComp");
			modelCompetence.setCompIntitule(inputLibelComp);

		}
		System.out.println("Libelle test pass");
		if (request.getParameter("inputLibelleParent") != null
				&& "" != request.getParameter("inputLibelleParent")) {

			String inputLibelParent = request
					.getParameter("inputLibelleParent");


			// on creer une competence modele semblable au parent
			parentCompetence.setCompIntitule(inputLibelParent);
			// on cherche la competence parent dans la base pour avoir id

			if (serviceGestionComp.rechercherCompetence(parentCompetence)
					.size() > 1) {
				System.out.println("Plusieurs parents trouvés dans base ");
			}
			// pb reformuler
			else if (serviceGestionComp.rechercherCompetence(parentCompetence)
					.size() > 0) {
				System.out.println("longueur : "
						+ serviceGestionComp.rechercherCompetence(
								parentCompetence).size());

				// on a trouvé un parent
				parentCompetence = serviceGestionComp.rechercherCompetence(
						parentCompetence).get(0);

			} else {
				System.out.println("Aucun parent réel trouvé dans base");
			}
			// on a le parent complet a affecter donc son id
			// on l'affecte à la competence à modifier
			// modelCompetence.setCompetence(parentCompetence);
			// System.out.println("competence parent affectée au model :"+parentCompetence.getCompIntitule());
		}

		if (request.getParameter("inputId") != ""
				&& request.getParameter("inputId") != null) {
			// sin on a bien un Id competence
			Integer compId = Integer.valueOf(request.getParameter("inputId"));
			modelCompetence.setCompId(compId);
		} else {
			System.out.println("Attention Id nul");
		}
		

		// ///////////////////////////////////// update / modifie
		if (request.getParameter("update") != null) {
			// fonctionne pour la modif intitulé
			// a valider pour la modif du parent ?
			// compId non nul !!!

			modelCompetence.setCompetence(parentCompetence);
			serviceGestionComp.updateCompetence(modelCompetence);

		}

		
		// ///////////////////////////////////// update / modifie intitulé
		if (request.getParameter("create") != null) {
			// mettre à faux si seulement proposition
			Boolean compValide = true;
			modelCompetence.setCompValide(compValide);
			serviceGestionComp.insertCompetence(modelCompetence);
		}


		// ///////////////////////////////////// update / modifie déplacement /
		// move
		if (request.getParameter("move") != null) {
			// mettre à faux si seulement proposition
			// Boolean compValide = true;
			// modelCompetence.setCompValide(compValide);
			// doit avoir un ID et un pere

			if (parentCompetence != null) {

				if (parentCompetence.getCompId() != null) {
					if (parentCompetence.getCompId() != modelCompetence
							.getCompId()) {

	
						modelCompetence.setCompetence(parentCompetence);
						serviceGestionComp.movedCompetence(modelCompetence);


					} else {
					System.out.println("Parent = enfant deplacement impossible");
					}
				}
			}
		}
		// /////////////////////////////////////
		// ////////////////////////////////// delete 

		if (request.getParameter("delete") != null) {

			if (request.getParameter("inputId") != null) {
				Integer inputCompId = Integer.valueOf(request
						.getParameter("inputId"));
				try {
					// fk_proposition_comp_comp_id" on table "proposition_comp"
					Competence deletedCompetence = new Competence();
					deletedCompetence.setCompId(inputCompId);
					
					System.out.println("delete :"+inputCompId);
					if (deletedCompetence !=  null) {	
					
					serviceGestionComp.deleteCompetence(deletedCompetence);
					} else {
						System.out.println("byzarre"+inputCompId);
						}
					
				} catch (NumberFormatException e) {
					// parametres non corrects : pas de suppression
				}
			}
		} else {
			Competence searchCompetences = new Competence();
			List<Competence> foundCompetences = serviceGestionComp
					.rechercherCompetence(searchCompetences);
			request.setAttribute("foundCompetences", foundCompetences);
		}

		// redirection vers DoGet
		response.sendRedirect("/GTC/Competence/");

	}

}