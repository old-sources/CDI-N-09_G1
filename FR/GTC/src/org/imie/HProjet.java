package org.imie;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
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
 * Servlet implementation class TP3_Controller
 */
@WebServlet("/HProjet/*")
public class HProjet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB ServiceGestionEcoleJPALocal serviceGestionEcole;
	@EJB ServiceGestionProjetJPALocal serviceGestionProjet;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HProjet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HProjet Get");

		// on met tous les projets dans foundProjets
		Projet searchProjet = new Projet();
		List<Projet> foundProjets = serviceGestionProjet.rechercherProjet(searchProjet);
		request.setAttribute("foundProjets", foundProjets);
		
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
		
		request.getRequestDispatcher("/WEB-INF/JProjet.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("HProjet Post");
		
		// recherche du projet à modifier, par son Id. si pas d'Id, on est en creation de projet
		Projet updatedProjet = new Projet();
		if ((request.getParameter("delete") != null)
		||(request.getParameter("update") != null)){
			Integer inputProjId = Integer.valueOf(request.getParameter("inputProjId"));
			updatedProjet.setProjId(inputProjId);
			updatedProjet = serviceGestionProjet.rechercherProjet(updatedProjet).get(0);
			System.out.println("Projet a modifier : "+ updatedProjet.getProjNom());
		}

		// affectation des nouvelles valeurs ----------------------------------------------------
		//choix du chef de projet
		String inputCdp = request.getParameter("inputCdpId");
		if(!"".equals(inputCdp)){
			System.out.println("inputPersonne par id : "+inputCdp);
			Personne cdp = new Personne();
			cdp.setId(Integer.valueOf(inputCdp));
			cdp = serviceGestionEcole.rechercherPersonne(cdp).get(0);
			updatedProjet.setChefDeProjet(cdp);
			//ajout du cdp dans les membres du projet
			Set<Personne> membres = updatedProjet.getMembres();
			if(membres!=null){
				membres.add(cdp);
			}else{
				membres= new HashSet<Personne>();
			}
			updatedProjet.setMembres(membres);
			System.out.println("inputCdpNom : "+cdp.getNom());
		}
	
		//choix du nom de projet
		String inputProjNom = request.getParameter("inputProjNom");
		updatedProjet.setProjNom(inputProjNom);
		System.out.println("inputProjNom : "+inputProjNom);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		//date de debut de projet
		String inputProjDatedebut = request.getParameter("inputProjDatedebut");
		System.out.println("inputProjDatedebut : "+inputProjDatedebut);
		try {
			Date inputDate = simpleDateFormat.parse(inputProjDatedebut);
			updatedProjet.setProjDatedebut(inputDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		//date de fin de projet
		String inputProjDatedefin = request.getParameter("inputProjDatedefin");
		System.out.println("inputProjDatedefin : "+inputProjDatedefin);
		try {
			Date inputDate = simpleDateFormat.parse(inputProjDatedefin);
			updatedProjet.setProjDatedefin(inputDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		
		//description du projet
		String inputprojDescription = request.getParameter("inputprojDescription");
		updatedProjet.setProjDescription(inputprojDescription);
		System.out.println("inputprojDescription : "+inputprojDescription);

		String inputProjWikiCdp = request.getParameter("inputProjWikiCdp");
		updatedProjet.setProjWikiCdp(inputProjWikiCdp);
		System.out.println("inputProjWikiCdp : "+inputProjWikiCdp);
		
		String inputprojWikiMembre = request.getParameter("inputprojWikiMembre");
		updatedProjet.setProjWikiMembre(inputprojWikiMembre);
		System.out.println("inputprojWikiMembre : "+inputprojWikiMembre);
		
		String inputprojAvancemen = request.getParameter("inputprojAvancement");
		updatedProjet.setProjAvancement(inputprojAvancemen);
		System.out.println("inputprojAvancement : "+inputprojAvancemen);
		
		
		////////////////////////////////////  delete update create  /////////////////////////////////////
		
		
		
		if (request.getParameter("delete") != null) {
			System.out.println("HProjet Post delete");
			serviceGestionProjet.deleteProjet(updatedProjet);
		}

		if (request.getParameter("create") != null) {
			System.out.println("HProjet POST create");
			serviceGestionProjet.insertProjet(updatedProjet);
		}

		if (request.getParameter("update") != null) {
			System.out.println("HProjet POST update");
			serviceGestionProjet.updateProjet(updatedProjet);
		}

		response.sendRedirect("/GTC/HProjet");
	}

}
