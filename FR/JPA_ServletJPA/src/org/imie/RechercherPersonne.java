package org.imie;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Personne;

import org.imie.service.ServiceGestionEcoleJPALocal;

/**
 * Servlet implementation class TestJPA
 */
@WebServlet("/rechercherPersonne")
public class RechercherPersonne extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	ServiceGestionEcoleJPALocal serviceGestionEcole;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RechercherPersonne() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Personne personneSearch = new Personne();
		personneSearch.setNom(request.getParameter("nom"));
		personneSearch.setPrenom(request.getParameter("prenom"));
		List<Personne> personnes = serviceGestionEcole
				.rechercherPersonne(personneSearch);
		request.setAttribute("personnes", personnes);
		request.getRequestDispatcher("/jsp/rechercherPersonne.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
