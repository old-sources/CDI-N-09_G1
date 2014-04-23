package org.imie;

import javax.jws.WebMethod;
import javax.jws.WebService;

//import javax.jws.WebMethod;
//import javax.jws.WebService;
//
//public class testWS {
//
//}

@WebService
public class testWS{
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

