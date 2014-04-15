package org.imie;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Personne;
import model.Promotion;
import model.Role;

import org.imie.service.ServiceGestionEcoleJPALocal;

/**
 * Servlet implementation class TP3_Controller
 */
@WebServlet("/HPersonne/*")
public class HPersonne extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB ServiceGestionEcoleJPALocal serviceGestionEcole;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HPersonne() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HPersonne Get");
		// a faire quelque soit le pattern

		Personne searchPersonne = new Personne();
		request.setAttribute("promotions",
				serviceGestionEcole.rechercherPromotion(new Promotion()));
		
		List<Personne> foundPersonnes = serviceGestionEcole
				.rechercherPersonne(searchPersonne);
		request.setAttribute("foundPersonnes", foundPersonnes);
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Personne loguedPerson = new Personne();
		loguedPerson=(Personne) httpServletRequest.getSession().getAttribute("authentifiedPersonne");
		request.setAttribute("loguedPerson", loguedPerson);
		
		
		List<Role> listRoles = serviceGestionEcole.rechercherRole(new Role());
		request.setAttribute("listRoles", listRoles);
		
		
		
		request.getRequestDispatcher("/WEB-INF/JPersonne.jsp").forward(
				request, response);
		
	

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("HPersonne Post");
		
		// recherche de la personne à modifier
		Personne updatedPerson = new Personne();

		//updatedPerson = serviceGestionEcole.rechercherPersonne(updatedPerson)
		//		.get(0);
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
			Date inputDateNaiss = simpleDateFormat.parse(inputDateNaissString);
			updatedPerson.setDateNaiss(inputDateNaiss);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		String inputPassword = request.getParameter("inputPassw");
		if (inputPassword != null && !inputPassword.isEmpty()) {
			updatedPerson.setPassw(inputPassword);
		}
		
		String inputEmail = request.getParameter("inputEmail");
		if (inputEmail != null && !inputEmail.isEmpty()) {
			updatedPerson.setEmail(inputEmail);
			System.out.println("inputemailupdatedpersonne : "+updatedPerson.getEmail());
		}
		
		String inputInfos = request.getParameter("inputInfos");
		if (inputInfos != null && !inputInfos.isEmpty()) {
			updatedPerson.setInfos(inputInfos);
		}
		
		String inputLogin = request.getParameter("inputLogin");
		if (inputLogin != null && !inputLogin.isEmpty()) {
			updatedPerson.setIdentConnexion(inputLogin);
		}
		
		String inputRoleId = request.getParameter("inputRole");
		System.out.println("Hpersonne string rolid :"+inputRoleId);
		if (inputRoleId != null && !inputRoleId.isEmpty()) {
			Integer roleId = Integer.valueOf(inputRoleId);
			Role role = new Role();
			role.setRoleId(roleId);
			serviceGestionEcole.rechercherRole(role).get(0);
			updatedPerson.setRole(role);
		}
		
		String inputDisponibilite = request.getParameter("inputDisponibilite");
		if (inputDisponibilite != null && !inputDisponibilite.isEmpty()) {
			updatedPerson.setDisponibilite(Boolean.valueOf(inputDisponibilite));
		}
		
		String identConnexion = request.getParameter("inputIdentConnexion");
		if (identConnexion != null && !identConnexion.isEmpty()) {
			updatedPerson.setIdentConnexion(identConnexion);
		}
		

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

		response.sendRedirect("/GTC/HPersonne");
	}

}
