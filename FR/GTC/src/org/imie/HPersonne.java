package org.imie;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import model.Travaille;

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

	private static String[] push(String[] array, String push) {
	    String[] longer = new String[array.length + 1];
	    for (int i = 0; i < array.length; i++)
	        longer[i] = array[i];
	    longer[array.length] = push;
	    return longer;
	}
	private String valLoginDouble;
	
	
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
		
		List<Travaille> foundTravailles = serviceGestionEcole
				.rechercherTravaille(new Travaille());
		request.setAttribute("foundTravailles", foundTravailles);
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Personne loguedPerson = new Personne();
		loguedPerson=(Personne) httpServletRequest.getSession().getAttribute("authentifiedPersonne");
		request.setAttribute("loguedPerson", loguedPerson);
		
		
		List<Role> listRoles = serviceGestionEcole.rechercherRole(new Role());
		request.setAttribute("listRoles", listRoles);
		
		
		Integer[] tempArray = new Integer[]{0,1,2,3,4,5};
		List<Integer> list5int = Arrays.asList(tempArray);;
		request.setAttribute("list5int", list5int);
		
		// pour l'affichage en cours de saisie sur le login disponibilité -- pas fonctionnel
		//ArrayList<String> listLogin = new ArrayList<String>();
		//String[] listLogin = new String[0];
		ArrayList<Personne> listLogin0 = new ArrayList<Personne>(foundPersonnes);

		request.setAttribute("listLogin", listLogin0);
		
		request.setAttribute("loginDouble",valLoginDouble);
		
		
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
		
		boolean trouve=false;
		String inputLogin = request.getParameter("inputLogin");
		if (inputLogin != null && !inputLogin.isEmpty()) {
			Personne searchPersonne = new Personne();
			request.setAttribute("promotions",
					serviceGestionEcole.rechercherPromotion(new Promotion()));
			List<Personne> foundPersonnes = serviceGestionEcole
					.rechercherPersonne(searchPersonne);
			for (Personne pers : foundPersonnes){
				System.out.println("*****for pers.getidco= "+pers.getIdentConnexion()+" inputLogin= "+inputLogin);
				if (inputLogin.equals(pers.getIdentConnexion())){
					System.out.println("est passé dans login trouvé");
					trouve = true;
				}
			}
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
		
//		String inputDisponibilite = request.getParameter("inputDisponibilite");
//		if (inputDisponibilite != null && !inputDisponibilite.isEmpty()) {
//			updatedPerson.setDisponibilite(Boolean.valueOf(inputDisponibilite));
//		}
		if (request.getParameter("inputDisponibilite") == null) {
			updatedPerson.setDisponibilite(false);
		}else{
			updatedPerson.setDisponibilite(true);
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
			if (!trouve){
				serviceGestionEcole.insertPersonne(updatedPerson);
				valLoginDouble = null;
			}else {
				valLoginDouble = inputLogin;
	//			request.setAttribute("loginDouble",inputLogin);
			}
		//	request.setAttribute("loginDouble","abc");
			//valLoginDouble="abc";
		}

		if (request.getParameter("update") != null) {
			valLoginDouble = null;
				System.out.println("HPersonne POST update");
				Integer inputId = Integer.valueOf(request.getParameter("inputId"));
				System.out.println("InputId = "+inputId);
				updatedPerson.setId(inputId);
				serviceGestionEcole.updatePersonne(updatedPerson);
			
		}

		response.sendRedirect("/GTC/HPersonne");
	}

}
