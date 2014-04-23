package org.imie;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Personne;
import model.Promotion;

import org.imie.service.ServiceGestionEcoleJPALocal;
//
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class MesProjets
 */
@WebServlet("/Admin")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	ServiceGestionEcoleJPALocal serviceGestionEcole;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Admin Get");

		Personne searchPersonne = new Personne();
		request.setAttribute("promotions",
				serviceGestionEcole.rechercherPromotion(new Promotion()));

		// envoi en attribut la personne loguéé "loguedPerson"
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Personne loguedPerson = new Personne();
		loguedPerson = (Personne) httpServletRequest.getSession().getAttribute(
				"authentifiedPersonne");
		request.setAttribute("loguedPerson", loguedPerson);
		// envoie en attribut le role (admin,user,super-admin) de la personne loguée
		int userId = loguedPerson.getRole().getRoleId();
		request.setAttribute("userId", userId);

		// envoie en attribut la liste des personnes presentes en base "foundPersonnes" 
		List<Personne> foundPersonnes = serviceGestionEcole
				.rechercherPersonne(searchPersonne);
		request.setAttribute("foundPersonnes", foundPersonnes);

		// envoie en attribut si il a repéré un import impossible sur l'import de fichiers
		String imp = (String) httpServletRequest.getSession().getAttribute(
				"importImpossibleLoginDouble");
		if (imp.equals("true")) {
			request.setAttribute("importImpossibleLoginDouble", imp);
		}
		;

		// acces à la page admin reservee aux admin et super admin, pour utilisateurs redirection vers home
		if ((loguedPerson.getRole().getRoleId() == 2)
				|| (loguedPerson.getRole().getRoleId() == 3)) {
			request.getRequestDispatcher("/WEB-INF/JAdmin.jsp").forward(
					request, response);
		}
		if (loguedPerson.getRole().getRoleId() == 1) {
			response.sendRedirect("/GTC/Home");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("AdminPost");
//		// récup
//		String inputFile = request.getParameter("file1");
//		System.out.println("inputFile : " + inputFile);
		response.sendRedirect("/GTC/Admin");
	}
}

/////////////////////////////// fin du fichier ////////////////////////////////////////////////////////////////////
////////////////////////////// ne pas effacer : pour l'import de fichier d'un poste client vers serveur ///////////
////if (inputFile != null && !inputFile.isEmpty()) {
		// // System.out.println(inputFile);
		// }

		// / ServletFileUpload servletFileUpload = new ServletFileUpload(new
		// DiskFileItemFactory());

		// List<FileItem> fileItemsList= null;

		// try {
		// fileItemsList = servletFileUpload.parseRequest(request);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		//
		// Iterator it = fileItemsList.iterator();
		// while (it.hasNext()){
		// FileItem fileItem = (FileItem)it.next();
		// if (fileItem.isFormField()){
		// // The file item contains a simple name-value pair of a form field
		// String name = fileItem.getFieldName();
		// String value = fileItem.getString();
		// }
		// else{
		// // The file item contains an uploaded file
		// File saveTo = new File("CHEMIN");
		// String url = saveTo.getAbsolutePath();
		// try {
		// fileItem.write(saveTo);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }
		// }

		// /////////////////////////////////////////////////////////////////////////////////////////////
		//
		// final String UPLOAD_DIRECTORY = "/home/essais/";
		//
		// // process only if its multipart content
		//
		// if (ServletFileUpload.isMultipartContent(request)) {
		//
		// try {
		//
		// List<FileItem> multiparts = new ServletFileUpload(
		//
		// new DiskFileItemFactory()).parseRequest(request);
		//
		// for (FileItem item : multiparts) {
		//
		// if (!item.isFormField()) {
		//
		// String name = new File(item.getName()).getName();
		//
		// item.write(new File(UPLOAD_DIRECTORY + File.separator
		// + name));
		//
		// }
		//
		// }
		//
		// // File uploaded successfully
		//
		// request.setAttribute("message", "File Uploaded Successfully");
		//
		// } catch (Exception ex) {
		//
		// request.setAttribute("message", "File Upload Failed due to "
		// + ex);
		//
		// }
		//
		// } else {
		//
		// request.setAttribute("message",
		//
		// "Sorry this Servlet only handles file upload request");
		//
		// }

		// request.getRequestDispatcher("/result.jsp").forward(request,
		// response);

		// //////////////////////////////////////////////////////////////////////////////////////////////

		// // Check that we have a file upload request
		// isMultipart = ServletFileUpload.isMultipartContent(request);
		// // response.setContentType("text/html");
		// java.io.PrintWriter out = response.getWriter( );
		// //// if( !isMultipart ){
		// //// out.println("<html>");
		// //// out.println("<head>");
		// //// out.println("<title>Servlet upload</title>");
		// //// out.println("</head>");
		// //// out.println("<body>");
		// //// out.println("<p>No file uploaded</p>");
		// //// out.println("</body>");
		// //// out.println("</html>");
		// //// return;
		// //// }
		// DiskFileItemFactory factory = new DiskFileItemFactory();
		// // maximum size that will be stored in memory
		// factory.setSizeThreshold(maxMemSize);
		// // Location to save data that is larger than maxMemSize.
		// factory.setRepository(new File("/home/essais/"));
		//
		// // Create a new file upload handler
		// ServletFileUpload upload = new ServletFileUpload(factory);
		// // maximum file size to be uploaded.
		// upload.setSizeMax( maxFileSize );
		//
		// try{
		// // Parse the request to get file items.
		// List fileItems = upload.parseRequest(request);
		//
		// // Process the uploaded file items
		// Iterator i = fileItems.iterator();
		// //
		// // out.println("<html>");
		// // out.println("<head>");
		// // out.println("<title>Servlet upload</title>");
		// // out.println("</head>");
		// // out.println("<body>");
		// while ( i.hasNext () )
		// {
		// FileItem fi = (FileItem)i.next();
		// if ( !fi.isFormField () )
		// {
		// // Get the uploaded file parameters
		// String fieldName = fi.getFieldName();
		// String fileName = fi.getName();
		// String contentType = fi.getContentType();
		// boolean isInMemory = fi.isInMemory();
		// long sizeInBytes = fi.getSize();
		// // Write the file
		// if( fileName.lastIndexOf("\\") >= 0 ){
		// file = new File( filePath +
		// fileName.substring( fileName.lastIndexOf("\\"))) ;
		// }else{
		// file = new File( filePath +
		// fileName.substring(fileName.lastIndexOf("\\")+1)) ;
		// }
		// fi.write( file ) ;
		// out.println("Uploaded Filename: " + fileName + "<br>");
		// }
		// }
		// // out.println("</body>");
		// // out.println("</html>");
		// }catch(Exception ex) {
		// System.out.println(ex);
		// }
		//
		//

