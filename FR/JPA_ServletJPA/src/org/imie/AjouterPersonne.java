package org.imie;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Personne;

import org.imie.service.ServiceGestionEcoleJPALocal;

/**
 * Servlet implementation class AjouterPersonne
 */
@WebServlet("/ajouterPersonne")
public class AjouterPersonne extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	ServiceGestionEcoleJPALocal serviceGestionEcole;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AjouterPersonne() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/formulairePersonne.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Personne personne = new Personne();
		personne.setNom(request.getParameter("nom"));
		personne.setPrenom(request.getParameter("prenom"));
		if(request.getParameter("dateNaiss")!=null){
			try {
				personne.setDateNaiss(new SimpleDateFormat("dd/MM/yyy").parse(request.getParameter("dateNaiss")));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		personne = serviceGestionEcole.insertPersonne(personne);
		request.setAttribute("foundPersonne", personne);
		request.getRequestDispatcher("/jsp/formulairePersonne.jsp").forward(
				request, response);
	}

}
