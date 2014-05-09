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
	
	//	request.setAttribute("travailles", loguedPerson.getTravailles());
	
//		Projet prj = new Projet();
//		prj.setPersonne(loguedPerson);
		List<Projet> projets = serviceGestionProjet.rechercherProjet(new Projet());
		request.setAttribute("projets", projets);
		
		
		
		System.out.println(loguedPerson.getNom());
		
		if (loguedPerson.getCgu()){
			request.getRequestDispatcher("/WEB-INF/JHome.jsp").forward(
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
		System.out.println("Home Post");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Personne loguedPerson = new Personne();
		loguedPerson=(Personne) httpServletRequest.getSession().getAttribute("authentifiedPersonne");
		request.setAttribute("loguedPerson", loguedPerson);
		
		// recherche de la personne à modifier
		Personne updatedPerson = new Personne();
		
		//updatedPerson = serviceGestionEcole.rechercherPersonne(updatedPerson).get(0);
		// affectation des nouvelles valeurs
		
		
		
		String inputNom = request.getParameter("inputNom");
		updatedPerson.setNom(inputNom);
		System.out.println("nom : "+inputNom);
	
		String inputPrenom = request.getParameter("inputPrenom");
		updatedPerson.setPrenom(inputPrenom);
		System.out.println("prenom : "+inputPrenom);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String inputDateNaissString = request.getParameter("inputDateNaiss");
		System.out.println("datenaiss : "+inputDateNaissString);
		try {
			//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//			String inputDateNaissString = request.getParameter("inputDateNaiss");
//			//Date inputDateNaiss = simpleDateFormat.parse(inputDateNaissString);
//			updatedPerson.setDateNaiss(inputDateNaiss);
			Date inputDateNaiss = simpleDateFormat.parse(inputDateNaissString);
			updatedPerson.setDateNaiss(inputDateNaiss);
			System.out.println("datenaiss : "+inputDateNaissString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		String inputPassword = request.getParameter("inputPassw");
		if (inputPassword != null && !inputPassword.isEmpty()) {
			updatedPerson.setPassw(inputPassword);
		}
		
		String inputPromotionString = request.getParameter("inputPromotion");
		if (!inputPromotionString.isEmpty()) {
			Integer inputPromotion = Integer.valueOf(inputPromotionString);
			Promotion searchPromotion = new Promotion();
			searchPromotion.setId(inputPromotion);
			searchPromotion = serviceGestionEcole.rechercherPromotion(
					searchPromotion).get(0);
			updatedPerson.setPromotion(searchPromotion);
		} else {
			updatedPerson.setPromotion(null);
		}

		// + categorie : admin, user, super admin
		
		String inputEmail = request.getParameter("inputEmail");
		updatedPerson.setEmail(inputEmail);
		System.out.println("email : "+inputEmail);
		
		if (request.getParameter("delete") != null) {
			System.out.println("HPersonne Post delete");
			try {
				Integer inputId = Integer.valueOf(request.getParameter("inputId"));
				updatedPerson.setId(inputId);
				System.out.println("id : "+inputId);
				serviceGestionEcole.deletePersonne(updatedPerson);
			}
			catch (NumberFormatException e) {
				// parametres non corrects : pas de suppression
			}
		}
		
		String inputInfos = request.getParameter("inputInfos");
		updatedPerson.setInfos(inputInfos);
		System.out.println("infos : "+inputInfos);
		
		updatedPerson.setIdentConnexion(loguedPerson.getIdentConnexion());
		
		updatedPerson.setDisponibilite(loguedPerson.getDisponibilite());
		
		updatedPerson.setRole(loguedPerson.getRole());
		
		

		if (request.getParameter("create") != null) {
			serviceGestionEcole.insertPersonne(updatedPerson);
		}

		if (request.getParameter("update") != null) {
			System.out.println("Profil POST update");
			Integer inputId = Integer.valueOf(request.getParameter("inputId"));
			System.out.println("InputId = "+inputId);
			updatedPerson.setId(inputId);
			updatedPerson = serviceGestionEcole.updatePersonne(updatedPerson);
			//HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			httpServletRequest.getSession().setAttribute("authentifiedPersonne",updatedPerson);
		}

		
		System.out.println("HPersonne POST update");
		Integer inputId = Integer.valueOf(request.getParameter("inputId"));
		System.out.println("InputId = "+inputId);
		updatedPerson.setId(inputId);
		serviceGestionEcole.updatePersonne(updatedPerson);
		
		
		response.sendRedirect("/GTC/Profil");
	}
}


