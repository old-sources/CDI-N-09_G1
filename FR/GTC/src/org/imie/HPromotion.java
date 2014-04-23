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
@WebServlet("/HPromotion/*")
public class HPromotion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB ServiceGestionEcoleJPALocal serviceGestionEcole;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HPromotion() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HPromotion Get");
		// a faire quelque soit le pattern

		Promotion searchPromotion = new Promotion();
		request.setAttribute("promotions",
				serviceGestionEcole.rechercherPromotion(new Promotion()));

		List<Promotion> foundPromotions = serviceGestionEcole
				.rechercherPromotion(searchPromotion);
		request.setAttribute("foundPromotions", foundPromotions);
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Personne loguedPerson = new Personne();
		loguedPerson=(Personne) httpServletRequest.getSession().getAttribute("authentifiedPersonne");
		request.setAttribute("loguedPerson", loguedPerson);
		
		request.getRequestDispatcher("/WEB-INF/JPromotion.jsp").forward(
				request, response);
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("HPromotion Post");
		
		// recherche de la promotion à modifier
		Promotion updatedPromotion = new Promotion();

		//updatedPromotion = serviceGestionEcole.rechercherPromotion(updatedPromotion)
			//	.get(0);
		// affectation des nouvelles valeurs
		String inputLibelle = request.getParameter("inputLibelle");
		updatedPromotion.setLibelle(inputLibelle);
		String inputLieu = request.getParameter("inputLieu");
		updatedPromotion.setLieu(inputLieu);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String inputDateDebutString = request.getParameter("inputDateDebut");
		try {
			Date inputDateDebut = simpleDateFormat.parse(inputDateDebutString);
			updatedPromotion.setDateDebut(inputDateDebut);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		String inputDateFinString = request.getParameter("inputDateFin");
		try {
			Date inputDateFin = simpleDateFormat.parse(inputDateFinString);
			updatedPromotion.setDateDebut(inputDateFin);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		if (request.getParameter("create") != null) {
			serviceGestionEcole.insertPromotion(updatedPromotion);
		}

		if (request.getParameter("update") != null) {
			Integer inputId = Integer.valueOf(request.getParameter("inputId"));
			updatedPromotion.setId(inputId);
			serviceGestionEcole.updatePromotion(updatedPromotion);
		}
		
		if (request.getParameter("delete") != null) {
			System.out.println("HPromotion Post delete");
			try {
				Integer inputId = Integer.valueOf(request.getParameter("inputId"));
				updatedPromotion.setId(inputId);
				System.out.println("id : "+inputId);
				serviceGestionEcole.deletePromotion(updatedPromotion);
			}
			catch (NumberFormatException e) {
				// parametres non corrects : pas de suppression
			}
		}

		response.sendRedirect("/GTC/HPromotion/");
	}
}
