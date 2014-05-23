 package org.imie.filter;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import model.Personne;

import org.imie.service.ServiceGestionEcoleJPALocal;

/**
 * Servlet Filter implementation class TP13_Authent
 */
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST }, urlPatterns = { "/*" })
public class TP13_Authent implements Filter {
	@EJB ServiceGestionEcoleJPALocal serviceGestionEcole;
	Personne searchPersonne = new Personne();
	/**
	 * Default constructor.
	 */
	public TP13_Authent() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		System.out.println("Filtre etape 1");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		//encodage des trames pour support caracteres accentues
		httpServletRequest.setCharacterEncoding("UTF-8");
		httpServletResponse.setCharacterEncoding("UTF-8");
		
		Boolean authentified = false;
		Boolean authentifying = false;
		Boolean resourceToScan = true;
		Boolean requestInterupted = false;
		Personne authPersonne = null;
		Personne searchPersonne = new Personne();
		if (httpServletRequest.getRequestURI().contains("css") || httpServletRequest.getRequestURI().contains("png")) {
			resourceToScan = false;
			System.out.println("Filtre etape 2");
		}
		if ((httpServletRequest.getRequestURI().contains("TP13")) || (httpServletRequest.getRequestURI().contains("Autentification"))) {
			authentifying = true;
			System.out.println("Filtre etape 3");
			//Interception forcée des POST des URL contenant TP13
			if (httpServletRequest.getMethod().equals("POST")
					&& httpServletRequest.getParameter("valider") != null) {
			//if (httpServletRequest.getParameter("valider") != null) {
				System.out.println("Filtre etape 4");
//	NK			String inputNom = request.getParameter("inputNom");
				String inputLogin = request.getParameter("inputLogin");
				String inputPassword = request.getParameter("inputPassword");
			
//	NK			searchPersonne.setNom(inputNom);
				searchPersonne.setIdentConnexion(inputLogin);
				searchPersonne.setPassw(inputPassword);
				
				try {
					authPersonne = serviceGestionEcole.verifierAuthPersonne(searchPersonne);
				} catch (ServiceException e) {
					request.setAttribute("messageException", e.getMessage());
				}
				if (authPersonne != null) {
					System.out.println("Filtre etape 5");
					//AJ : en vue de mettre une redirection validation CGU  =>  if authPersonne.getfdfgdfg
					httpServletRequest.getSession().setAttribute(
							"authentifiedPersonne", authPersonne);
					httpServletRequest.getSession().setAttribute("importImpossibleLoginDouble","false");
					String uri = (String) httpServletRequest.getSession()
							.getAttribute("originURL");
					//test CGU
//					Personne aut2 = new Personne();
//					aut2=serviceGestionEcole.rechercherPersonne(searchPersonne).get(0);
					System.out.println("login et cgu"+authPersonne.getIdentConnexion()+"  "+authPersonne.getCgu());
					if (authPersonne.getCgu()){
						System.out.println("Filtre etape 6");
						System.out.println("filtreCGUtrue");
						httpServletResponse.sendRedirect(uri);
					}else {
						System.out.println("Filtre etape 7");
						System.out.println("filtreCGUfalse");
						httpServletResponse.sendRedirect("/GTC/CGU");
					}
					/////fin test CGU
					//httpServletResponse.sendRedirect(uri);
				} else {
					System.out.println("Filtre etape 8");
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("WEB-INF/Autentification.jsp");
					dispatcher.forward(request, response);
				}
				requestInterupted = true;
			}

		}
		if (httpServletRequest.getSession()
				.getAttribute("authentifiedPersonne") != null) {
			System.out.println("Filtre etape 9");
			authentified = true;
		}
		if (!requestInterupted) {
			System.out.println("Filtre etape 10");
			if (!authentified && !authentifying && resourceToScan) {
				
				String requestParam = httpServletRequest.getQueryString()!=null?"?".concat(httpServletRequest.getQueryString().toString()):"";
				httpServletRequest.getSession().setAttribute("originURL",
						httpServletRequest.getRequestURL().toString().concat(requestParam));
				System.out.println("Filtre etape 11 string request = "+requestParam);
				httpServletResponse.sendRedirect("/GTC/Autentification");
			} else {
				System.out.println("Filtre etape 12");
				//System.out.println("login et cgu"+aut2.getIdentConnexion()+"  "+aut2.getCgu());
				chain.doFilter(request, response);
				
				
			}
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
