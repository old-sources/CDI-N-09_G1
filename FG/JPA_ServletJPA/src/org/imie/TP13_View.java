package org.imie;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TP13_View
 */
@WebServlet("/TP13_View")
public class TP13_View extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TP13_View() {
        super();
        // TODO Auto-generated constructors stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		buildIHM(request, response);

	}

	/**
	 * @param request
	 * @param response
	 * @param printWriter
	 * @throws IOException 
	 */
	private void buildIHM(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter printWriter=response.getWriter();
		printWriter.println("<!DOCTYPE html>");
		printWriter.println("<html>");
		printWriter.println("<head>");
		printWriter
				.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		printWriter.println("<link rel=stylesheet type=\"text/css\" href=\"./TP12.css\">");
		printWriter.println("</head>");
		printWriter.println("<body>");
		printWriter.println("<form method=\"POST\">");
		printWriter.println("<div class=\"form\">");

		printWriter.println("<div class=\"row\">");

		printWriter.println("<div class=\"cell\">");
		printWriter.println("<label for=\"\">nom</label>");
		printWriter.println("</div>");
		printWriter.println("<div class=\"cell\">");
		printWriter.println("<input type=\"text\" name=\"inputNom\" />");
		printWriter.println("</div>");

		printWriter.println("</div>");
		printWriter.println("<div class=\"row\">");

		printWriter.println("<div class=\"cell\">");
		printWriter.println("<label for=\"\">password</label>");
		printWriter.println("</div>");
		printWriter.println("<div class=\"cell\">");
		printWriter.println("<input type=\"password\" name=\"inputPassword\">");
		printWriter.println("</div>");

		printWriter.println("</div>");

		printWriter.println("</div>");
		printWriter
				.println("<input type=\"submit\" name=\"valider\" value=\"Valider\"/>");
		printWriter.println("</form>");
		String message = (String) request.getAttribute("messageException");
		if (message!=null){
			printWriter.println("<span>message : "+message+"</span>");
		}
		printWriter.println("</body>");
		printWriter.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		buildIHM(request, response);
	}

}
