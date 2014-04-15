package org.imie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Personne;
import model.Projet;
import model.Travaille;

import org.imie.service.ServiceGestionEcoleJPALocal;

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
		List<Projet> projetsEnTantQueChefDeProjet = serviceGestionEcole.rechercherProjet(prj);
		request.setAttribute("projetsCdp", projetsEnTantQueChefDeProjet);
		
		Travaille trv = new Travaille();
		trv.setPersonne(loguedPerson);
		List<Travaille> travailleSur = serviceGestionEcole.rechercherTravaille(trv);
		List<Projet>  projetsEnTantQueUser0 = new ArrayList<Projet>();
		for (Travaille trv2 : travailleSur){
			projetsEnTantQueUser0.add(trv2.getProjet());			
		}
		//request.setAttribute("projetsUser", projetsEnTantQueUser0);
		
		List<Projet>  projetsEnTantQueUser = new ArrayList<Projet>();
		boolean dedans;
		for (Projet prj2 : projetsEnTantQueUser0){
			dedans = false;
			for (Projet prj3 : projetsEnTantQueChefDeProjet){
				if (prj2 == prj3) {dedans=true;};
			}
			if (!dedans) {
				projetsEnTantQueUser.add(prj2);
			}
		}
		request.setAttribute("projetsUser", projetsEnTantQueUser);
		
		
		
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
