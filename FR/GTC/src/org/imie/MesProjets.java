package org.imie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Personne;
import model.Projet;

import org.imie.service.ServiceGestionEcoleJPALocal;
import org.imie.service.ServiceGestionProjetJPALocal;

/**
 * Servlet implementation class MesProjets
 */
@WebServlet("/MesProjets")
public class MesProjets extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB ServiceGestionEcoleJPALocal serviceGestionEcole;
	@EJB ServiceGestionProjetJPALocal serviceGestionProjet;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MesProjets() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MesProjets Get");
		
HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		
		Personne loguedPerson = new Personne();
		loguedPerson=(Personne) httpServletRequest.getSession().getAttribute("authentifiedPersonne");
		request.setAttribute("loguedPerson", loguedPerson);
	
		//Recherche de la personne en cours dans la base
		Personne recherche = new Personne();
		recherche.setId(loguedPerson.getId());
		List<Personne> personnes = serviceGestionEcole.rechercherPersonne(recherche);
		if (personnes.size() == 1) {

			//recuperation de TOUS les projets dont la personne est chef 
			List<Projet> projetsEnTantQueChefDeProjet = personnes.get(0).getProjetsCDP();
			//passage dans la requete des projets dont la personne est chef 
			request.setAttribute("projetsCdp", projetsEnTantQueChefDeProjet);
			
			//recuperation de TOUS les projets auxquels la personne participe 
			List<Projet> projetsEnTantQueUser0 = personnes.get(0).getProjets();			
			//ne garder dans tous les projets que ceux dont la personne n'est pas chef		
			List<Projet>  projetsEnTantQueUser = new ArrayList<Projet>();
			boolean dedans;
			for (Projet prj2 : projetsEnTantQueUser0){
				dedans = false;
				for (Projet prj3 : projetsEnTantQueChefDeProjet){
					if (prj2 == prj3) {dedans=true;};
				}
				if (!dedans) {
					projetsEnTantQueUser.add(prj2);
				}
			}
			request.setAttribute("projetsUser", projetsEnTantQueUser);
		}

		//lecture de la liste des utilisateurs pour invitation à un projet
		List<Personne> usersList = serviceGestionEcole.rechercherPersonne(new Personne());
		request.setAttribute("usersList", usersList);

		
		System.out.println(loguedPerson.getNom());
		
		if (loguedPerson.getCgu()){
			request.getRequestDispatcher("/WEB-INF/JMesProjets.jsp").forward(
					request, response);
		}else {
			//httpServletRequest.getSession().setAttribute("authentifiedPersonne",null);
			response.sendRedirect("/GTC/CGU");
		}

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MesProjets Post");
		
		// recherche du projet à modifier, par son Id
		Projet updatedProjet = new Projet();
		Integer inputProjId = Integer.valueOf(request.getParameter("inputProjId"));
		updatedProjet.setProjId(inputProjId);
		updatedProjet = serviceGestionProjet.rechercherProjet(updatedProjet).get(0);
		System.out.println("Projet a modifier : "+ updatedProjet.getProjNom());
		
		// ajout d'un nouvel utilisateur
		String inputNewMember = request.getParameter("inputNewMemberId");
		if((request.getParameter("invite") != null)
				&(!"".equals(inputNewMember))){
			Personne newMember = new Personne();
			newMember.setId(Integer.valueOf(inputNewMember));
			newMember = serviceGestionEcole.rechercherPersonne(newMember).get(0);
			//ajout du nouveau membre dans la liste
			Set<Personne> membres = updatedProjet.getMembres();
			membres.add(newMember);
			updatedProjet.setMembres(membres);
			System.out.println("nouveau membre pret a etre enregistre: "+ newMember.getNom());
		}
	
		//quitter un projet
		String inputMemberQuit = request.getParameter("inputMemberId");
		if((request.getParameter("quitProjet") != null)
				&(!"".equals(inputMemberQuit))){
			Personne deserter = new Personne();
			deserter.setId(Integer.valueOf(inputMemberQuit));
			deserter = serviceGestionEcole.rechercherPersonne(deserter).get(0);
			//suppression du membre dans la liste
			Set<Personne> membres = updatedProjet.getMembres();
			membres.remove(deserter);
			updatedProjet.setMembres(membres);
//			System.out.println("nouveau membre pret a etre enregistre: "+ deserter.getNom());
		}
	
		
		
		
		////////////////////////////////////  delete update create  /////////////////////////////////////

		//mise a jour du projet
		if (request.getParameter("invite") != null) {
			System.out.println("MesProjets POST invite");
			serviceGestionProjet.updateProjet(updatedProjet);
		}

		response.sendRedirect("/GTC/MesProjets");
	}

}
