<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
		<span> page Admin </span><br>
		<BUTTON id="afficheListeUsers" onclick="self.location.href='/GTC/HPersonne'" style="background-color: transparent; border: 0px"><img src="/GTC/images/bricolman2.png" ></BUTTON>
	
		<BUTTON id="afficheListePromotions" onclick="self.location.href='/GTC/HPromotion'" style="background-color: transparent; border: 0px"><img src="/GTC/images/promotions2.png" ></BUTTON>

		<BUTTON id="afficheListeProjets" onclick="self.location.href='/GTC/HProjet'" style="background-color: transparent; border: 0px"><img src="/GTC/images/projets2.png" ></BUTTON>


		<BUTTON id="afficherListeCompetences" onclick="self.location.href='/GTC/Competence'" style="background-color: transparent; border: 0px"><img src="/GTC/images/competences2.png" ></BUTTON>
		
		<BUTTON id="afficherListeRelationTravaille" onclick="self.location.href='/GTC/HTravaille'" style="background-color: transparent; border: 0px"><img src="/GTC/images/relation2.png" ></BUTTON>
		
		
<c:choose>
<c:when test="${loguedPerson.role.roleId==3}">
		<BUTTON id="afficherImportExport" onclick="self.location.href='/GTC/Admin'"style="background-color: transparent; border: 0px"><img src="/GTC/images/imports2.png" ></BUTTON>
								
</c:when>
</c:choose>