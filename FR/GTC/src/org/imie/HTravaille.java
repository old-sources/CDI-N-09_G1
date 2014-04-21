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
import model.Promotion;
import model.Travaille;

import org.imie.service.ServiceGestionEcoleJPALocal;

/**
 * Servlet implementation class TP3_Controller
 */
@WebServlet("/HTravaille/*")
public class HTravaille extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB ServiceGestionEcoleJPALocal serviceGestionEcole;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HTravaille() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HTravaille Get");
		// a faire quelque soit le pattern

		List<Travaille> foundTravailles = serviceGestionEcole
				.rechercherTravaille(new Travaille());
		request.setAttribute("foundTravailles", foundTravailles);
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Personne loguedPerson = new Personne();
		loguedPerson=(Personne) httpServletRequest.getSession().getAttribute("authentifiedPersonne");
		request.setAttribute("loguedPerson", loguedPerson);
		request.setAttribute("foundTravailles",foundTravailles);
		List<Personne> foundPersonnes = serviceGestionEcole
				.rechercherPersonne(new Personne());
		request.setAttribute("foundPersonnes", foundPersonnes);
		
		
		request.getRequestDispatcher("/WEB-INF/JTravaille.jsp").forward(
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
