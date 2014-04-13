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

import model.Personne;
import model.Possede;
import model.Promotion;

import org.imie.service.ServiceGestionEcoleJPALocal;

/**
 * Servlet implementation class Profil
 */
@WebServlet("/Profil")
public class Profil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB ServiceGestionEcoleJPALocal serviceGestionEcole;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Profil Get");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		
		Personne loguedPerson = (Personne) httpServletRequest.getSession().getAttribute("authentifiedPersonne");
		request.setAttribute("loguedPerson", loguedPerson);
		
		request.setAttribute("promotions",serviceGestionEcole.rechercherPromotion(new Promotion()));
		
		Integer[] tempArray = new Integer[]{0,1,2,3,4,5};
		List<Integer> list5int = Arrays.asList(tempArray);;
		request.setAttribute("list5int", list5int);
		
		Possede possede = new Possede();
		possede.setPersonne(loguedPerson);
		request.setAttribute("possedes", serviceGestionEcole.rechercherPossede(new Possede()));
		
		System.out.println(loguedPerson.getNom());
		request.getRequestDispatcher("/WEB-INF/JProfil.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
