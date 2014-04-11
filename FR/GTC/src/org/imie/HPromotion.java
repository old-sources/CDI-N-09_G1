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
		request.getRequestDispatcher("/WEB-INF/JPromotion.jsp").forward(
				request, response);
		
		
//		// declaration pattern create
//		Pattern patternCreate = Pattern.compile(".*/HPromotion/create");
//		Matcher matcherCreate = patternCreate.matcher(request.getRequestURL());
//		// declaration pattern read
//		Pattern patternRead = Pattern
//				.compile(".*/HPromotion/read/([0-9]*)");
//		Matcher matcherRead = patternRead.matcher(request.getRequestURL());
//		// declaration pattern delete
//		Pattern patternDelete = Pattern
//				.compile(".*/HPromotion/delete/([0-9]*)");
//		Matcher matcherDelete = patternDelete.matcher(request.getRequestURL());
//
//		// url en create
//		if (matcherCreate.find()) {
//			request.getRequestDispatcher("/WEB-INF/ModifPromotion.jsp").forward(request,
//					response);
//		}
//		//url de delete
//		else if (matcherDelete.find()) {
//			try {
//				searchPromotion.setId(Integer.valueOf(matcherDelete.group(1)));
//				List<Promotion> foundPromotions = serviceGestionEcole.rechercherPromotion(searchPromotion);
//				if (foundPromotions.size() > 0) {
//					serviceGestionEcole.deletePromotion(foundPromotions.get(0));
//
//				} else {
//					response.setStatus(404);
//				}
//
//			} catch (NumberFormatException e) {
//				// url mal formée : pas de suppression
//			}
//			List<Promotion> foundPromotions = serviceGestionEcole
//					.rechercherPromotion(new Promotion());
//			request.setAttribute("foundPromotions", foundPromotions);
//			request.getRequestDispatcher("/WEB-INF/ModifPromotion.jsp").forward(
//					request, response);
//		}
//		// url en read
//		else if (matcherRead.find()) {
//			try {
//				searchPromotion.setId(Integer.valueOf(matcherRead.group(1)));
//				List<Promotion> foundPromotions = serviceGestionEcole
//						.rechercherPromotion(searchPromotion);
//				if (foundPromotions.size() > 0) {
//					request.setAttribute("promotionSelected",
//							foundPromotions.get(0));
//					request.getRequestDispatcher("/WEB-INF/jsp/ModifPromotion.jsp").forward(
//							request, response);
//				} else {
//					response.setStatus(404);
//				}
//
//			} catch (NumberFormatException e) {
//				// url mal formée : on renvoie toutes les promotions
//				List<Promotion> foundPromotions = serviceGestionEcole
//						.rechercherPromotion(searchPromotion);
//				request.setAttribute("foundPromotions", foundPromotions);
//				request.getRequestDispatcher("/WEB-INF/JPromotion.jsp").forward(
//						request, response);
//			}
//		}

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

		updatedPromotion = serviceGestionEcole.rechercherPromotion(updatedPromotion)
				.get(0);
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

		response.sendRedirect("/GTC/HPromotion/");
	}

}
