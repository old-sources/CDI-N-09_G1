package org.imie;

import java.io.IOException;
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
 * Servlet implementation class Profil
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB ServiceGestionEcoleJPALocal serviceGestionEcole;
	@EJB ServiceGestionProjetJPALocal serviceGestionProjet;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Home Get");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		
		Personne loguedPerson = new Personne();
		loguedPerson=(Personne) httpServletRequest.getSession().getAttribute("authentifiedPersonne");
		request.setAttribute("loguedPerson", loguedPerson);
	
		//recherche de tous les projets
		List<Projet> projets = serviceGestionProjet.rechercherProjet(new Projet());
		request.setAttribute("projets", projets);
		
//		if (loguedPerson.getCgu()){
			request.getRequestDispatcher("/WEB-INF/JHome.jsp").forward(
					request, response);
//		}else {
//			response.sendRedirect("/GTC/CGU");
//		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Home Post");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Personne loguedPerson = new Personne();
		loguedPerson=(Personne) httpServletRequest.getSession().getAttribute("authentifiedPersonne");
		request.setAttribute("loguedPerson", loguedPerson);
		
		// recherche du projet à modifier, par son Id
		Projet updatedProjet = new Projet();
		System.out.println(request.getParameter("inputProjId"));
		Integer inputProjId = Integer.valueOf(request.getParameter("inputProjId"));
		updatedProjet.setProjId(inputProjId);
		updatedProjet = serviceGestionProjet.rechercherProjet(updatedProjet).get(0);
		System.out.println("Projet a modifier : "+ updatedProjet.getProjNom());
		
		// ajout d'un nouvel utilisateur
		String inputNewMember = request.getParameter("inputMembreId");
		if((request.getParameter("postuler") != null)
				&(!"".equals(inputNewMember))){
			Personne newMember = new Personne();
			newMember.setId(Integer.valueOf(inputNewMember));
			newMember = serviceGestionEcole.rechercherPersonne(newMember).get(0);
			//ajout du nouveau membre dans la liste
			Set<Personne> membres = updatedProjet.getMembres();
			membres.add(newMember);
			updatedProjet.setMembres(membres);
		}
	
		
		////////////////////////////////////  delete update create  /////////////////////////////////////

		//mise a jour du projet, ajout de membre
		if ((request.getParameter("postuler") != null)){
			System.out.println("Home POST update");
			serviceGestionProjet.updateProjet(updatedProjet);
		}

		response.sendRedirect("/GTC/Home");
	}

}


