package org.imie;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.imie.service.ServiceGestionEcoleJPALocal;

import model.Personne;
import model.Possede;
import model.Projet;
import model.Promotion;

/**
 * Servlet implementation class MesProjets
 */
@WebServlet("/MesProjets")
public class MesProjets extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB ServiceGestionEcoleJPALocal serviceGestionEcole;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MesProjets() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MesProjets Get");
		
HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		
		Personne loguedPerson = new Personne();
		loguedPerson=(Personne) httpServletRequest.getSession().getAttribute("authentifiedPersonne");
		request.setAttribute("loguedPerson", loguedPerson);
	
		request.setAttribute("travailles", loguedPerson.getTravailles());
	
		Projet prj = new Projet();
		prj.setPersonne(loguedPerson);
		List<Projet> projets = serviceGestionEcole.rechercherProjet(prj);
		request.setAttribute("projets", projets);
		
		
		
		System.out.println(loguedPerson.getNom());

		request.getRequestDispatcher("/WEB-INF/JMesProjets.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
