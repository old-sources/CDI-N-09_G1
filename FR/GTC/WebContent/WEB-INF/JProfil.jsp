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

<script type="text/javascript" charset="utf8"
	src="js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css">
<script type="text/javascript" charset="utf8"
	src="js/jquery.dataTables.yadcf.js"></script>
<link rel=stylesheet type="text/css"
	href="css/jquery.dataTables.yadcf.css">

<title>Profil</title>
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

		console.log("inputdatenaiss : "+dateString);
		
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



			<input type="hidden" name="inputId" value="${loguedPerson.id}"/>
			<input type="hidden" name="inputRoleId" value="${loguedPerson.role.roleId}"/>
			<div>
				<label for="inputNom">nom :</label> <input type="text" id="inputNom"
					name="inputNom" value="${loguedPerson.nom}">
			</div>
			<label for="inputPrenom">prenom :</label> <input type="text"
				id="inputPrenom" name="inputPrenom" value="${loguedPerson.prenom}">
			<div>
				<label for="inputDateNaiss">date de naissance</label> <input
					type="text" id="inputDateNaiss" name="inputDateNaiss"
<%-- 					value=fmt:formatDate pattern="dd/MM/yyyy" value="${personne.dateNaiss}" --%>
					>
			</div>
			<div>
				<label for="inputPassw">Password</label> <input type="password"
					id="inputPassw" name="inputPassw" value="${loguedPerson.passw}">
			</div>
			<div class="cell">
				<label for="inputPromotion">promotion : </label> <select
					name="inputPromotion">
					<option value=""></option>
					<c:forEach items="${promotions}" var="promotion">
						<c:choose>
							<c:when test="${promotion.id==loguedPerson.promotion.id}">
								<option value="${promotion.id}" selected>
									${promotion.libelle} ${promotion.lieu}</option>
							</c:when>
							<c:otherwise>
								<option value="${promotion.id}">${promotion.libelle}
									${promotion.lieu}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
			<div>

				<label for="inputemail">email</label> 
				<input type="email" id="inputEmail" name="inputEmail" value="${loguedPerson.email}">
			</div>
			<div>
				<label for="inputInfos">Autres informations</label> <input
					type="text" id="inputInfos" name="inputInfos"
					value="${loguedPerson.infos}">
			</div>
			<div class="cell">
				<label for="inputCompetences">compétences : </label>
				<c:forEach items="${possedes}" var="poss">

					<span> ${poss.competence.compIntitule} </span>
					<select name="inputValueCompetence">
						<option value=""></option>
						<c:forEach items="${list5int}" var="niv">
							<c:choose>
								<c:when test="${niv==poss.compNiveau}">
									<option value="${niv}" selected>${niv}</option>
								</c:when>
								<c:otherwise>
									<option value="${niv}">${niv}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</c:forEach>
			</div>



			<div>

				<BUTTON class="actionFormulaire" data-id="${personne.id}">ajouter une compétence (non géré)</BUTTON>

				<BUTTON class="actionFormulaire" name="update" onclick="javascript:location.reload();">Modifier (ne met pas a jour les compétences)</BUTTON>
			</div>
		</form>
	</div>
	
</body>
</html>
