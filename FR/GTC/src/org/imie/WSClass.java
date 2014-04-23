
package org.imie;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class WSClass{
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankonet.ws.MonServiceInterface#saluer()
	 */
	@WebMethod
	public String methode() {
		return "Bonjour2";
	}

}

