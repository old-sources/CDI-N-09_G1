package org.imie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		System.out.println("MesProjets_ Get");
		
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
		
		// recherche du projet à modifier
//		Projet updatedProjet = new Projet();

//		// affectation des nouvelles valeurs
//		String inputPersonne = request.getParameter("inputPersonne");
//		System.out.println("inputPersonne par id : "+inputPersonne);
//		Personne cdp = new Personne();
//		cdp.setId(Integer.valueOf(inputPersonne));
//		cdp = serviceGestionEcole.rechercherPersonne(cdp).get(0);
//		updatedProjet.setPersonne(cdp);
//		System.out.println("inputCdpNom : "+cdp.getNom());
//	
//		String inputProjNom = request.getParameter("inputProjNom");
//		updatedProjet.setProjNom(inputProjNom);
//		System.out.println("inputProjNom : "+inputProjNom);
//		
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//		
//		String inputProjDatedebut = request.getParameter("inputProjDatedebut");
//		System.out.println("inputProjDatedebut : "+inputProjDatedebut);
//		try {
//			Date inputDate = simpleDateFormat.parse(inputProjDatedebut);
//			updatedProjet.setProjDatedebut(inputDate);
//		} catch (ParseException e) {
//			throw new RuntimeException(e);
//		}
//		String inputProjDatedefin = request.getParameter("inputProjDatedefin");
//		System.out.println("inputProjDatedefin : "+inputProjDatedefin);
//		try {
//			Date inputDate = simpleDateFormat.parse(inputProjDatedefin);
//			updatedProjet.setProjDatedefin(inputDate);
//		} catch (ParseException e) {
//			throw new RuntimeException(e);
//		}
//		
//		String inputprojDescription = request.getParameter("inputprojDescription");
//		updatedProjet.setProjDescription(inputprojDescription);
//		System.out.println("inputprojDescription : "+inputprojDescription);
//
//		String inputProjWikiCdp = request.getParameter("inputProjWikiCdp");
//		updatedProjet.setProjWikiCdp(inputProjWikiCdp);
//		System.out.println("inputProjWikiCdp : "+inputProjWikiCdp);
//		
//		String inputprojWikiMembre = request.getParameter("inputprojWikiMembre");
//		updatedProjet.setProjWikiMembre(inputprojWikiMembre);
//		System.out.println("inputprojWikiMembre : "+inputprojWikiMembre);
//		
//		String inputprojAvancemen = request.getParameter("inputprojAvancement");
//		updatedProjet.setProjAvancement(inputprojAvancemen);
//		System.out.println("inputprojAvancement : "+inputprojAvancemen);
//		
//		
//		
//		////////////////////////////////////  delete update create  /////////////////////////////////////
//		
//		
//		
//		if (request.getParameter("delete") != null) {
//			System.out.println("HProjet Post delete");
//			try {
//				Integer inputProjId = Integer.valueOf(request.getParameter("inputProjId"));
//				updatedProjet.setProjId(inputProjId);
//				System.out.println("inputProjId : "+inputProjId);
//				serviceGestionEcole.deleteProjet(updatedProjet);
//			}
//			catch (NumberFormatException e) {
//				// parametres non corrects : pas de suppression
//			}
//		}
//
//		if (request.getParameter("create") != null) {
//			System.out.println("HProjet POST create");
//			serviceGestionEcole.insertProjet(updatedProjet);
//		}
//
//		if (request.getParameter("update") != null) {
//			System.out.println("HProjet POST update");
//			Integer inputProjId = Integer.valueOf(request.getParameter("inputProjId"));
//			System.out.println("inputProjId = "+inputProjId);
//			updatedProjet.setProjId(inputProjId);
//			serviceGestionEcole.updateProjet(updatedProjet);
//		}
//
//		response.sendRedirect("/GTC/HProjet");
	}

}
