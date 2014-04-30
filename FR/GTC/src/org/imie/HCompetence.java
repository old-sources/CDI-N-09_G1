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

		// on crée un modèle vide de type compétence
		Competence searchCompetences = new Competence();
		// on met toutes les competences dans foundCompetences
		List<Competence> foundCompetences = serviceGestionEcole
				.rechercherCompetence(searchCompetences);

		// Affectation de la liste des enfants comme attributs
		serviceGestionEcole.setChildCompetence(foundCompetences);
		// on a initialisé la liste de tous les enfants
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

		System.out.println("HCompetence Post");

		// on cree un modèle vide de competence
		Competence modelCompetence = new Competence();
		Competence parentCompetence = new Competence();

		System.out.println("HCompetence Post attribution request");
		// valeurs passés en request
		// système d'erreur à mettre en place si non initialisé
		if (request.getParameter("inputLibelleComp") != null) {
			String inputLibelComp = request.getParameter("inputLibelleComp");
			modelCompetence.setCompIntitule(inputLibelComp);
			System.out.println("Libelle Comp non nul");
			System.out.println(modelCompetence.getCompIntitule());
		}
		System.out.println("Libelle test pass");
		if (request.getParameter("inputLibelleParent") != null
				&& "" != request.getParameter("inputLibelleParent")) {
			System.out.println("Parent model non nul");
			String inputLibelParent = request
					.getParameter("inputLibelleParent");
			System.out.println(inputLibelParent);

			// on creer une competence modele semblable au parent
			parentCompetence.setCompIntitule(inputLibelParent);
			// on cherche la competence parent dans la base pour avoir id

			if (serviceGestionEcole.rechercherCompetence(parentCompetence)
					.size() > 1) {
				System.out.println("Plusieurs parents trouvés dans base ");
			}
			// pb reformuler
			else if (serviceGestionEcole.rechercherCompetence(parentCompetence)
					.size() > 0) {
				System.out.println("longueur : "
						+ serviceGestionEcole.rechercherCompetence(
								parentCompetence).size());

				parentCompetence = serviceGestionEcole.rechercherCompetence(
						parentCompetence).get(0);
				System.out.println("Parent réel trouvé dans base "
						+ parentCompetence.getCompId());
			} else {
				System.out.println("Aucun parent réel trouvé dans base");
			}
			// on a le parent complet a affecter donc son id
			// on l'affecte à la competence à modifier
			// modelCompetence.setCompetence(parentCompetence);
			// System.out.println("competence parent affectée au model :"+parentCompetence.getCompIntitule());
		}
		System.out.println("parent test passé");

		if (request.getParameter("inputId") != ""
				&& request.getParameter("inputId") != null) {
			System.out.println("Id non nul" + request.getParameter("inputId"));
			Integer compId = Integer.valueOf(request.getParameter("inputId"));
			modelCompetence.setCompId(compId);
		} else {
			System.out.println("Attention Id nul");
			System.out.println("req" + request.getParameter("inputId"));
			System.out.println("req" + request.getParameter("inputId2"));
			System.out.println("req" + request.getParameter("inputId3"));
		}

		System.out.println("HCompetence Post avant update");
		// ///////////////////////////////////// update / modifie
		if (request.getParameter("update") != null) {
			// fonctionne pour la modif intitulé
			// a valider pour la modif du parent ?
			// compId non nul !!!
			System.out.println("HCompetence Post dans update"
					+ request.getParameter("update"));
			modelCompetence.setCompetence(parentCompetence);
			serviceGestionEcole.updateCompetence(modelCompetence);

		}
		System.out.println("HCompetence Post avant create");
		// ///////////////////////////////////// update / modifie intitulé
		if (request.getParameter("create") != null) {
			System.out.println("HCompetence Post dans create/insert");
			// mettre à faux si seulement proposition
			Boolean compValide = true;
			modelCompetence.setCompValide(compValide);
			serviceGestionEcole.insertCompetence(modelCompetence);
			System.out.println("insertion reussie");
		}
		System.out.println("HCompetence Post avant move");
		// ///////////////////////////////////// update / modifie déplacement /
		// move
		if (request.getParameter("move") != null) {
			System.out.println("HCompetence Post dans move");
			// mettre à faux si seulement proposition
			// Boolean compValide = true;
			// modelCompetence.setCompValide(compValide);
			// doit avoir un ID et un pere
			System.out.println("HCompetence Post move updateCompetence");
			if (parentCompetence != null) {
				System.out.println("HCompetence Post move parent non nul: "
						+ parentCompetence.getCompIntitule());
				if (parentCompetence.getCompId() != null) {
					if (parentCompetence.getCompId() != modelCompetence
							.getCompId()) {
						System.out
								.println("HCompetence Post move id parent non nul: "
										+ parentCompetence.getCompId());
						// modelCompetence =
						// serviceGestionEcole.rechercherCompetence(
						// modelCompetence).get(0);
						modelCompetence.setCompetence(parentCompetence);
						serviceGestionEcole.movedCompetence(modelCompetence);
						// serviceGestionEcole.movedCompetence(modelCompetence,parentCompetence);
						System.out.println("HCompetence Post après move");
					}
					System.out.println("Parent = enfant deplacement impossible");
				}
			}
		}
		// /////////////////////////////////////
		// ////////////////////////////////// delete update create
		System.out.println("HCompetence Post avant delete");
		if (request.getParameter("delete") != null) {

			System.out.println("HCompetence Post delete avant test ID");
			// if (request.getParameter("inputCompId") != null) {
			if (request.getParameter("inputId") != null) {
				System.out.println("Dans IF HCompetence Post delete");
				Integer inputCompId = Integer.valueOf(request
						.getParameter("inputId"));
				System.out.println("CompId = " + inputCompId);
				try {
					// fk_proposition_comp_comp_id" on table "proposition_comp"
					Competence deletedCompetence = new Competence();
					deletedCompetence.setCompId(inputCompId);
					System.out.println("inputCompId : " + inputCompId);
					serviceGestionEcole.deleteCompetence(deletedCompetence);
				} catch (NumberFormatException e) {
					// parametres non corrects : pas de suppression
				}
			}
		} else {
			Competence searchCompetences = new Competence();
			List<Competence> foundCompetences = serviceGestionEcole
					.rechercherCompetence(searchCompetences);
			request.setAttribute("foundCompetences", foundCompetences);
		}

		// redirection vers DoGet
		response.sendRedirect("/GTC/Competence/");

	}

}