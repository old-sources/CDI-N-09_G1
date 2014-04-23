package PtestWS;

import javax.jws.WebMethod;
import javax.jws.WebService;

//import javax.jws.WebMethod;
//import javax.jws.WebService;
//
//public class PCtestWS {
//
//}

@WebService
public class PCtestWS{
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

