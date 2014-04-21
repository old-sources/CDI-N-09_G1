<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
		<span> page Admin </span><br>
		<BUTTON id="afficheListeUsers" onclick="self.location.href='/GTC/HPersonne'">Gestion des utilisateurs</BUTTON>

		
		<BUTTON id="afficheListePromotions" onclick="self.location.href='/GTC/HPromotion'">Gestion des promotions</BUTTON>

		<BUTTON id="afficheListeProjets" onclick="self.location.href='/GTC/HProjet'">Gestion des projets</BUTTON>


		<BUTTON id="afficherListeCompetences" onclick="self.location.href='/GTC/HCompetence'">Gestion des compétences</BUTTON>
		
		<BUTTON id="afficherListeCompetences" onclick="self.location.href='/GTC/HTravaille'">relations personnes/projets</BUTTON>
		
		
<c:choose>
<c:when test="${loguedPerson.role.roleId==3}">
		<BUTTON id="afficherImportExport" onclick="self.location.href='/GTC/Admin'">Imports / Exports</BUTTON>
								
</c:when>
</c:choose>