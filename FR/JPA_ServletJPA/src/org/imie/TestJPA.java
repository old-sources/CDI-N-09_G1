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
/// aaa test2 git
/**
 * Servlet implementation class TestJPA
 */
@WebServlet("/TestJPA")
public class TestJPA extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB ServiceGestionEcoleJPALocal serviceGestionEcole;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestJPA() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Personne personne = new Personne();
//		personne.setNom("m");
		List<Personne> personnes = serviceGestionEcole.rechercherPersonne(new Personne());
		PrintWriter writer = response.getWriter();
		for (Personne personne : personnes ){
			writer.println(personne.getNom());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
