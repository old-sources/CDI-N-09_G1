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
		}
		if (httpServletRequest.getRequestURI().contains("TP13")) {
			authentifying = true;
			//Interception forcée des POST des URL contenant TP13
			if (httpServletRequest.getMethod().equals("POST")
					&& httpServletRequest.getParameter("valider") != null) {
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
						System.out.println("filtreCGUtrue");
						httpServletResponse.sendRedirect(uri);
					}else {
						System.out.println("filtreCGUfalse");
						httpServletResponse.sendRedirect("/GTC/CGU");
					}
					/////fin test CGU
					//httpServletResponse.sendRedirect(uri);
				} else {
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("TP13_View");
					dispatcher.forward(request, response);
				}
				requestInterupted = true;
			}

		}
		if (httpServletRequest.getSession()
				.getAttribute("authentifiedPersonne") != null) {
			authentified = true;
		}
		if (!requestInterupted) {
			if (!authentified && !authentifying && resourceToScan) {
				
				String requestParam = httpServletRequest.getQueryString()!=null?"?".concat(httpServletRequest.getQueryString().toString()):"";
				httpServletRequest.getSession().setAttribute("originURL",
						httpServletRequest.getRequestURL().toString().concat(requestParam));
				httpServletResponse.sendRedirect("/GTC/TP13_Controller");
			} else {
				
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
