package org.imie;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.imie.service.ServiceGestionEcoleJPALocal;

import model.Personne;

/**
 * Servlet implementation class CGU
 */
@WebServlet("/CGU")
public class CGU extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB ServiceGestionEcoleJPALocal serviceGestionEcole;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CGU() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/CGU.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("valideCGU") != null) {
			if (request.getParameter("checkboxCGU") != null) {
				Personne loguedPerson = new Personne();
				loguedPerson=(Personne) request.getSession().getAttribute("authentifiedPersonne");
				loguedPerson.setCgu(true);
				serviceGestionEcole.updatePersonne(loguedPerson);
				request.getSession().setAttribute("authentifiedPersonne",loguedPerson);
				
			}else{
				request.getSession().setAttribute("authentifiedPersonne",null);
			}
		}
		response.sendRedirect("/GTC/Home");
		
	}

}
