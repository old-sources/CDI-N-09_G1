<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="java.util.Date"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr"
	dir="ltr">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<base href="/GTC/" />
<link rel=stylesheet type="text/css" href="css/style.css">
<link href="css/south-street/jquery-ui-1.10.4.custom.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4.custom.js"></script>

<title>Tableau de bord</title>
<SCRIPT type="text/javascript">
	$(document).ready(function() {

		$('.onlyadmin').hide();		
		if ("${loguedPerson.role.roleId}" != 1){
			$('.onlyadmin').show();
			}
		$('.actionFormulaire').button();

		var dateString = new Date("${loguedPerson.dateNaiss}");
		$('#inputDateNaiss').datepicker({
			defaultDate : dateString
		});
		$('#inputDateNaiss').val(dateString.toLocaleDateString("fr-FR"));

		$('#formulaire').dialog({
			autoOpen : false,
			show : {
				effect : "blind",
				duration : 1000
			},
			hide : {
				effect : "blind",
				duration : 1000
			}
		});

	

		
	});
</SCRIPT>


</head>
<body>
	<%@ include file="/WEB-INF/header.jsp"%>
	<div id="divProfil">
		<form method="POST" id="formPrincipale">



			<input type="hidden" name="inputId" id="inputId"
				value="${loguedPerson.id}" />


			<div class="cell">
				<label for="listeProjets">projets en cours : </label>

				<TABLE id="tableProjets">
					<THEAD>
						<TR>
							<TH>Nom du projet</TH>
							<TH>date de début</TH>
							<TH>date de fin</TH>
							<TH>description</TH>
							<TH>chef de projet</TH>
							<TH>membres</TH>
							<TH>avancement</TH>
							<TH>projet affecté à</TH>
							<TH></TH>
						</TR>
					</THEAD>
					<TBODY>
						<c:forEach items="${projets}" var="prj">
						<tr>
							<TD><c:out value="${prj.projNom}" /></TD>
							<TD><c:out value="${prj.projDatedebut}" /></TD>
							<TD><c:out value="${prj.projDatedefin}" /></TD>
							<TD><c:out value="${prj.projDescription}" /></TD>
							<TD><c:out value="${prj.projWikiCdp}" /></TD>
							<TD><c:out value="${prj.projWikiMembre}" /></TD>
							<TD><c:out value="${prj.projAvancement}" /></TD>
							<TD><c:out value="${prj.personne.nom}" /></TD>
						</tr>
						</c:forEach>
					</TBODY>

				</TABLE>
			</div>
		</form>
	</div>


			<!-- 			<div id="liste"> -->
			<!-- 				<BUTTON class="actionPagePrincipaleCreer">Créer une -->
			<!-- 					personne</BUTTON> -->
			<!-- 				<TABLE id="tablePersonne"> -->

			<!-- 					<TBODY> -->
			<%-- 						<c:forEach var="personne" items="${foundPersonnes}"> --%>
			<!-- 							<TR> -->
			<%-- 								<TD><c:out value="${personne.nom}" /></TD> --%>
			<%-- 								<TD><c:out value="${personne.prenom}" /></TD> --%>
			<%-- 								<td><fmt:formatDate pattern="dd/MM/yyyy" --%>
			<%-- 										value="${personne.dateNaiss}" /></td> --%>
			<%-- 								<td><c:out value="${personne.promotion.libelle}" /></td> --%>


			<!-- 								<TD><BUTTON class="actionFormulaire" -->
			<%-- 										data-id="${personne.id}" data-nom="${personne.nom}" --%>
			<%-- 										data-prenom="${personne.prenom}" --%>
			<%-- 										data-dateNaiss="${personne.dateNaiss}" --%>
			<%-- 										data-promotionlibelle="${personne.promotion.libelle}" --%>
			<%-- 										data-promotionid="${personne.promotion.id}">Modifier</BUTTON></TD> --%>

			<!-- 							</TR> -->
			<%-- 						</c:forEach> --%>
			<!-- 					</TBODY> -->
			<!-- 				</TABLE> -->
			<!-- 			</div> -->


			<!-- 			<div> -->

			<%-- 				<BUTTON class="actionFormulaire" data-id="${personne.id}">ajouter --%>
			<!-- 					une compétence (non géré)</BUTTON> -->

			<!-- 				<BUTTON class="actionFormulaire" name="update" -->
			<!-- 					onclick="javascript:location.reload();">Modifier (non fini -->
			<!-- 					+ si plusieurs compétences toutes ont le meme input name -->
			<!-- 					reconcevoir)</BUTTON> -->
			<!-- 			</div> -->
			<!-- 		</form> -->
			<!-- 	</div> -->
			<!-- 	<div id="formulaire"> -->
			<!-- 		<form method="POST" id="formFormulaire"> -->


			<!-- 			<input type="hidden" name="inputId" id="inputId" /> -->
			<!-- 			<div> -->
			<!-- 				<label for="inputNom">nom :</label> <input type="text" id="inputNom" -->
			<!-- 					name="inputNom"> -->
			<!-- 			</div> -->
			<!-- 			<label for="inputPrenom">prenom :</label> <input type="text" -->
			<!-- 				id="inputPrenom" name="inputPrenom"> -->
			<!-- 			<div> -->
			<!-- 				<label for="inputDateNaiss">date de naissance</label> <input -->
			<!-- 					type="text" id="inputDateNaiss" name="inputDateNaiss"> -->
			<!-- 			</div> -->
			<!-- 			<div class="cell"> -->
			<!-- 				<label for="inputPromotion">promotion : </label> <select -->
			<!-- 					name="inputPromotion" id="inputPromotion"> -->
			<!-- 					<option value=""></option> -->
			<%-- 					<c:forEach items="${promotions}" var="promotion"> --%>
			<%-- 						<option value="${promotion.id}">${promotion.libelle}</option> --%>
			<%-- 					</c:forEach> --%>
			<!-- 				</select> -->
			<!-- 			</div> -->
			<!-- 			<input type="submit" name="create" id="creerDansForm" value="Créer" /> -->
			<!-- 			<input type="submit" name="update" id="updateDansForm" -->
			<!-- 				value="Modifier" /> <input type="submit" name="delete" -->
			<!-- 				id="deleteDansForm" value="Supprimer" /> -->




			<!-- 		</form> -->
			<!-- 	</div> -->
</body>
</html>
