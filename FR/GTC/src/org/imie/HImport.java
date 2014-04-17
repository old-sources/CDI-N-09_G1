package org.imie;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Personne;
import model.Promotion;
import model.Role;

import org.imie.service.ServiceGestionEcoleJPALocal;

/**
 * Servlet implementation class HImport
 */
@WebServlet("/HImport")
public class HImport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB ServiceGestionEcoleJPALocal serviceGestionEcole;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HImport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HImport obj = new HImport();
		obj.run();
		response.sendRedirect("/GTC/Admin");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
//	public void runKWYOUNG() {
//		 
//		//String csvFile = "/home/imie/filrouge/data/test.csv";
//		//String csvFile = "/home/imie/Documents/saves/test.csv";
//		BufferedReader br = null;
//		String line = "";
//		String cvsSplitBy = ";";
//	 
//		try {
//	 
//			br = new BufferedReader(new FileReader(csvFile));
//			while ((line = br.readLine()) != null) {
//	 
//			        // use comma as separator
//				String[] country = line.split(cvsSplitBy);
//				System.out.println("line affichee"+line);
//				System.out.println("Country [code= " + country[4] 
//	                                 + " , name=" + country[5] + "]");
//	 
//			}
//	 
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (br != null) {
//				try {
//					br.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	 
//		System.out.println("Done");
//	  }
	
	public void run() {
		 
		String csvFile = "/home/imie/filrouge/data/test.csv";
		//String csvFile = "/home/imie/Documents/saves/test.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
		
	 
		try {
	 
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				Personne personne = new Personne();
			        // use ";" as separator
				String[] pers = line.split(cvsSplitBy);
				//System.out.println("line affichee"+pers);
				personne.setNom(pers[0]);
				personne.setPrenom(pers[1]);
				personne.setIdentConnexion(pers[2]);
				personne.setPassw(pers[3]);
				personne.setInfos(pers[4]);
				personne.setEmail(pers[5]);
				personne.setDisponibilite(Boolean.valueOf(pers[6]));
		    	
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String inputDateNaissString = pers[7];
				inputDateNaissString = inputDateNaissString.substring(0, inputDateNaissString.length()-1) ;
				inputDateNaissString = inputDateNaissString.substring(1, inputDateNaissString.length()) ;
				inputDateNaissString = inputDateNaissString.substring(0, inputDateNaissString.length()-1) ;
				inputDateNaissString = inputDateNaissString.substring(1, inputDateNaissString.length()) ;
				System.out.println("datenaiss : "+inputDateNaissString);
				try {
					Date inputDateNaiss = simpleDateFormat.parse(inputDateNaissString);
					personne.setDateNaiss(inputDateNaiss);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
		    
		    
				String inputPromotionString = pers[8];
				if (!inputPromotionString.isEmpty()) {
					Integer inputPromotion = Integer.valueOf(inputPromotionString);
					Promotion searchPromotion = new Promotion();
					searchPromotion.setId(inputPromotion);
					System.out.println("searchpromotion.getid : "+searchPromotion.getId());
					List <Promotion> list = serviceGestionEcole.rechercherPromotion(searchPromotion);
					searchPromotion = serviceGestionEcole.rechercherPromotion(searchPromotion).get(0);
					System.out.println("promotion2");
					personne.setPromotion(searchPromotion);
					System.out.println("promotion3");
				} else {
					personne.setPromotion(null);
				}
				
				
		    	
		    	String inputRoleId = pers[9];
		    	
				System.out.println("Hpersonne string rolid :"+inputRoleId);
				if (inputRoleId != null && !inputRoleId.isEmpty()) {
					System.out.println("inputroleid : "+inputRoleId);
					inputRoleId = inputRoleId.substring(0, inputRoleId.length()-1) ;
					System.out.println("inputroleid : "+inputRoleId);
					Integer roleId= Integer.valueOf(inputRoleId);
					Role role = new Role();
					role.setRoleId(roleId);
					serviceGestionEcole.rechercherRole(role).get(0);
					personne.setRole(role);
				}
				
				
				
				
				
				
		    	serviceGestionEcole.insertPersonne(personne);
				}
			
			
			
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	 
		System.out.println("Done");
	  }
	 
	 
	

}
