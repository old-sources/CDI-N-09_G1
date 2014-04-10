package org.imie;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Personne;
import model.Promotion;

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

		// declaration pattern create
		Pattern patternCreate = Pattern.compile(".*/HPersonne/create");
		Matcher matcherCreate = patternCreate.matcher(request.getRequestURL());
		// declaration pattern read
		Pattern patternRead = Pattern
				.compile(".*/HPersonne/read/([0-9]*)");
		Matcher matcherRead = patternRead.matcher(request.getRequestURL());
		// declaration pattern delete
		Pattern patternDelete = Pattern
				.compile(".*/HPersonne/delete/([0-9]*)");
		Matcher matcherDelete = patternDelete.matcher(request.getRequestURL());

		// url en create
		if (matcherCreate.find()) {
			request.getRequestDispatcher("/WEB-INF/TP3.jsp").forward(request,
					response);
		}
		//url de delete
		else if (matcherDelete.find()) {
			try {
				searchPersonne.setId(Integer.valueOf(matcherDelete.group(1)));
				List<Personne> foundPersonnes = serviceGestionEcole.rechercherPersonne(searchPersonne);
				if (foundPersonnes.size() > 0) {
					serviceGestionEcole.deletePersonne(foundPersonnes.get(0));

				} else {
					response.setStatus(404);
				}

			} catch (NumberFormatException e) {
				// url mal formée : pas de suppression
			}
			List<Personne> foundPersonnes = serviceGestionEcole
					.rechercherPersonne(new Personne());
			request.setAttribute("foundPersonnes", foundPersonnes);
			request.getRequestDispatcher("/WEB-INF/TP4.jsp").forward(
					request, response);
		}
		// url en read
		else if (matcherRead.find()) {
			try {
				searchPersonne.setId(Integer.valueOf(matcherRead.group(1)));
				List<Personne> foundPersonnes = serviceGestionEcole
						.rechercherPersonne(searchPersonne);
				if (foundPersonnes.size() > 0) {
					request.setAttribute("personneSelected",
							foundPersonnes.get(0));
					request.getRequestDispatcher("/jsp/ModifierPersonne.jsp").forward(
							request, response);
				} else {
					response.setStatus(404);
				}

			} catch (NumberFormatException e) {
				// url mal formée : on renvoie toutes les personnes
				List<Personne> foundPersonnes = serviceGestionEcole
						.rechercherPersonne(searchPersonne);
				request.setAttribute("foundPersonnes", foundPersonnes);
				request.getRequestDispatcher("/jsp/ListePersonne3.jsp").forward(
						request, response);
			}
		}

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

		updatedPerson = serviceGestionEcole.rechercherPersonne(updatedPerson)
				.get(0);
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

//		String inputPassword = request.getParameter("inputPassword");
//		if (inputPassword != null && !inputPassword.isEmpty()) {
//			updatedPerson.setPassw(inputPassword);
//		}

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

		response.sendRedirect("/JPA_ServletJPA/HPersonne/read/");
	}

}
