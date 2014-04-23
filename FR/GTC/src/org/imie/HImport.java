package org.imie;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
import model.Possede;
import model.Promotion;
import model.Role;

import org.imie.service.ServiceGestionEcoleJPALocal;

/**
 * Servlet implementation class HImport
 */
@WebServlet("/HImport")
public class HImport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	ServiceGestionEcoleJPALocal serviceGestionEcole;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HImport() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/GTC/Admin");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		if (request.getParameter("submitUpload") != null) {

			String inputFile = request.getParameter("file1");
			String csvFile = "/home/imie/filrouge/data/".concat(inputFile);
			// maison : String csvFile = "/home/imie/Documents/saves/test.csv";
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";
			boolean trouve = false;
			// verfication de non-doublon sur le login
			try {

				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					Personne personne = new Personne();
					// use ";" as separator
					String[] pers = line.split(cvsSplitBy);
					personne.setIdentConnexion(pers[2]);
					if ((pers[2]).equals(serviceGestionEcole
							.rechercherPersonne(personne).get(0)
							.getIdentConnexion())) {
						trouve = true;
					}
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

			if (!trouve) {
				// action d'insertion
				try {

					br = new BufferedReader(new FileReader(csvFile));
					while ((line = br.readLine()) != null) {
						Personne personne = new Personne();
						// use ";" as separator
						String[] pers = line.split(cvsSplitBy);
						personne.setNom(pers[0]);
						System.out.println("setnom : " + pers[0]);
						personne.setPrenom(pers[1]);
						System.out.println("setprenom : " + pers[1]);
						personne.setIdentConnexion(pers[2]);
						personne.setPassw(pers[3]);
						personne.setInfos(pers[4]);
						personne.setEmail(pers[5]);
						personne.setDisponibilite(Boolean.valueOf(pers[6]));

						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						String inputDateNaissString = pers[7];
						System.out.println("datenaiss : "
								+ inputDateNaissString);
						try {
							Date inputDateNaiss = simpleDateFormat
									.parse(inputDateNaissString);
							personne.setDateNaiss(inputDateNaiss);
						} catch (ParseException e) {
							throw new RuntimeException(e);
						}

						String inputPromotionString = pers[8];
						if (!inputPromotionString.isEmpty()) {
							Integer inputPromotion = Integer
									.valueOf(inputPromotionString);
							Promotion searchPromotion = new Promotion();
							searchPromotion.setId(inputPromotion);
							System.out.println("searchpromotion.getid : "
									+ searchPromotion.getId());
							searchPromotion = serviceGestionEcole
									.rechercherPromotion(searchPromotion)
									.get(0);
							System.out.println("promotion2");
							personne.setPromotion(searchPromotion);
							System.out.println("promotion3");
						} else {
							personne.setPromotion(null);
						}

						String inputRoleId = pers[9];
						System.out.println("Hpersonne string rolid :"
								+ inputRoleId);
						if (inputRoleId != null && !inputRoleId.isEmpty()) {
							System.out.println("inputroleid : " + inputRoleId);
							Integer roleId = Integer.valueOf(inputRoleId);
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
				httpServletRequest.getSession().setAttribute(
						"importImpossibleLoginDouble", "false");

			} else {
				httpServletRequest.getSession().setAttribute(
						"importImpossibleLoginDouble", "true");
			}
		}

		// ////////////////////////////// export de fichier 		// ////////////////////

		if (request.getParameter("submitDownload") != null) {
			String inputFile = request.getParameter("file2");
			String csvFile = "/home/imie/filrouge/data/".concat(inputFile);

			FileWriter writer = new FileWriter(csvFile);

			writer.append("Nom");
			writer.append(',');
			writer.append("Prenom");
			writer.append(',');
			writer.append("IdentConnexion");
			writer.append(',');
			writer.append("Passw");
			writer.append(',');
			writer.append("Infos");
			writer.append(',');
			writer.append("Email");
			writer.append(',');
			writer.append("Disponibilite");
			writer.append(',');
			writer.append("DateNaiss");
			writer.append(',');
			writer.append("Promotion");
			writer.append(',');
			writer.append("Role");
			writer.append('\n');
			Personne personne = new Personne();
			List<Personne> personnes = serviceGestionEcole
					.rechercherPersonne(new Personne());

			while (personnes.size() > 0) {
				int numElt = personnes.size() - 1;
				personne = personnes.get(numElt);
				personnes.remove(numElt);

				writer.append(personne.getNom());
				writer.append(',');
				writer.append(personne.getPrenom());
				writer.append(',');
				writer.append(personne.getIdentConnexion());
				writer.append(',');
				writer.append(personne.getPassw());
				writer.append(',');
				writer.append(personne.getInfos());
				writer.append(',');
				writer.append(personne.getEmail());
				writer.append(',');
				writer.append(String.valueOf(personne.getDisponibilite()));
				writer.append(',');
				writer.append(personne.getDateNaiss().toString());
				writer.append(',');
				if (personne.getPromotion() != null) {
					writer.append(String.valueOf(personne.getPromotion()
							.getId()));
				} else {
					writer.append("");
				}
				writer.append(',');
				if (personne.getRole() != null) {
					writer.append(String
							.valueOf(personne.getRole().getRoleId()));
				} else {
					writer.append("");
				}
				writer.append('\n');
			}
			// generate whatever data you want

			writer.flush();
			writer.close();
		}

		response.sendRedirect("/GTC/Admin");

	}

}

// ////////////////////////////////////////////////
