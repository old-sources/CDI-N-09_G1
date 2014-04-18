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

import org.imie.service.ServiceGestionEcoleJPALocal;

import model.Personne;
import model.Promotion;

/**
 * Servlet implementation class MesProjets
 */
@WebServlet("/Admin")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB ServiceGestionEcoleJPALocal serviceGestionEcole;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MesProjets Get");

		Personne searchPersonne = new Personne();
		request.setAttribute("promotions",
				serviceGestionEcole.rechercherPromotion(new Promotion()));
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Personne loguedPerson = new Personne();
		loguedPerson=(Personne) httpServletRequest.getSession().getAttribute("authentifiedPersonne");
		request.setAttribute("loguedPerson", loguedPerson);
		
		List<Personne> foundPersonnes = serviceGestionEcole
				.rechercherPersonne(searchPersonne);
		request.setAttribute("foundPersonnes", foundPersonnes);
		if ((loguedPerson.getRole().getRoleId() == 2) || (loguedPerson.getRole().getRoleId() == 3)){
		request.getRequestDispatcher("/WEB-INF/JAdmin.jsp").forward(
				request, response);
		}
		if (loguedPerson.getRole().getRoleId() == 1) {
			response.sendRedirect("/GTC/Home");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
System.out.println("HPersonne Post");
		
		// recherche de la personne à modifier
		Personne updatedPerson = new Personne();
		
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
			Date inputDateNaiss = simpleDateFormat.parse(inputDateNaissString);
			updatedPerson.setDateNaiss(inputDateNaiss);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

//		String inputPassword = request.getParameter("inputPassword");
//		if (inputPassword != null && !inputPassword.isEmpty()) {
//			updatedPerson.setPassw(inputPassword);
//		}

		// + categorie : admin, user, super admin
		
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

		if (request.getParameter("create") != null) {
			serviceGestionEcole.insertPersonne(updatedPerson);
		}

		if (request.getParameter("update") != null) {
			System.out.println("HPersonne POST update");
			Integer inputId = Integer.valueOf(request.getParameter("inputId"));
			System.out.println("InputId = "+inputId);
			updatedPerson.setId(inputId);
			serviceGestionEcole.updatePersonne(updatedPerson);
		}

		response.sendRedirect("/GTC/Admin");
	}
	

}
