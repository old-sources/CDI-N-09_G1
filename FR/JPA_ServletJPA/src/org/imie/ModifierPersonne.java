package org.imie;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * Servlet implementation class ModifierPersonne
 */
@WebServlet("/modifierPersonne/*")
public class ModifierPersonne extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	ServiceGestionEcoleJPALocal serviceGestionEcole;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifierPersonne() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// declaration pattern create

		Pattern patternUpdate = Pattern.compile(".*/modifierPersonne/([0-9]*)");
		Matcher matcherUpdate = patternUpdate.matcher(request.getRequestURL());
		
		Personne searchPersonne = new Personne();
		request.setAttribute("promotions",
				serviceGestionEcole.rechercherPromotion(new Promotion()));
		

		if (matcherUpdate.find()) {
			try {
				searchPersonne.setId(Integer.valueOf(matcherUpdate.group(1)));
				List<Personne> foundPersonnes = serviceGestionEcole
						.rechercherPersonne(searchPersonne);
				if (foundPersonnes.size() > 0) {
					request.setAttribute("foundPersonne",foundPersonnes.get(0));
				}

			} catch (NumberFormatException e) {
				// url mal formée : pas de suppression
			}
			request.getRequestDispatcher("/jsp/formulairePersonne.jsp")
					.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Pattern patternUpdate = Pattern.compile(".*/modifierPersonne/([0-9]*)");
		Matcher matcherUpdate = patternUpdate.matcher(request.getRequestURL());
		
		Personne searchPersonne = new Personne();
		request.setAttribute("promotions",
				serviceGestionEcole.rechercherPromotion(new Promotion()));

		if (matcherUpdate.find()) {
			try {
				Integer id = Integer.valueOf(matcherUpdate.group(1));
				String nom = request.getParameter("nom");
				String prenom = request.getParameter("prenom");
				
				searchPersonne.setId(Integer.valueOf(matcherUpdate.group(1)));
				List<Personne> foundPersonnes = serviceGestionEcole
						.rechercherPersonne(searchPersonne);
				if (foundPersonnes.size() > 0) {
					Personne personne = foundPersonnes.get(0);
					personne.setNom(nom);
					personne.setPrenom(prenom);
					if(request.getParameter("dateNaiss")!=null){
						try {
							personne.setDateNaiss(new SimpleDateFormat("dd/MM/yyy").parse(request.getParameter("dateNaiss")));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					personne = serviceGestionEcole.updatePersonne(personne);
					request.setAttribute("foundPersonne", personne);
					
				}

				
				
				

			} catch (NumberFormatException e) {
				// url mal formée : pas de suppression
			}
			request.getRequestDispatcher("/jsp/formulairePersonne.jsp")
					.forward(request, response);
		}
	}

}
