package org.imie;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Personne;

/**
 * Servlet implementation class TP13_header
 */
@WebServlet("/TP13_header")
public class TP13_header extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TP13_header() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter printWriter = response.getWriter();
		Personne personne = (Personne) request.getSession().getAttribute(
				"authentifiedPersonne");

		printWriter.println("<header>");
		printWriter.println("<div>");

		printWriter.println("<span>" + personne.getNom() + " - "
				+ personne.getPrenom() + "</span>");
		printWriter.println("<a href=\"./TP13_Deconnection\">Deconnection</a>");
		printWriter.println("</div>");
		printWriter.println("</header>");
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
