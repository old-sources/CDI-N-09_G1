<%@page import="org.apache.taglibs.standard.tag.common.xml.ForEachTag"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr" dir="ltr">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<base href="/GTC/" />
	<link rel=stylesheet type="text/css" href="css/style.css">
	<link href="css/south-street/jquery-ui-1.10.4.custom.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/jquery-1.10.2.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.10.4.custom.js"></script>
	<script type="text/javascript" charset="utf8" src="js/jquery.dataTables.min.js"></script>
	<link rel="stylesheet" type="text/css"	href="css/jquery.dataTables.css">
	<script type="text/javascript" charset="utf8" src="js/jquery.dataTables.yadcf.js"></script>
	<link rel=stylesheet type="text/css" href="css/jquery.dataTables.yadcf.css">
	<title>Menu Personnes</title>
	<!-- recuperation des variables necessaires pour les scripts externes -->
	<script type="text/javascript">
		var _loggedUserRoleId = '${loguedPerson.role.roleId}';
	</script>
	<script src="js/scriptPersonnes.js"></script> <!-- Sources javascript -->
	<SCRIPT type="text/javascript">
		$(document).ready(
			function VerifLogin() {
				console.log("rentré dans VerifLogin");
				$('.loginExiste').hide();
				var result = false;
			
				var listLogin = [];
				var val;
				<%for(Personne pers : (List<Personne>)request.getAttribute("listLogin")){%>
					listLogin.push("<%=pers.getIdentConnexion()%>");
				<%};%>
				var loginEnTest = document.getElementById('inputLogin').value;
				for ( var i = 0; i < listLogin.length; i++) {
					if (loginEnTest == listLogin[i]) {
						result = true;
					}
					;
				}
				;
			
				if (result == true) {
					$('.loginExiste').show();
					$('.loginExiste').val(loginEnTest + " est déja utilisé");
				}
				;
			}
		);
	</SCRIPT>


</head>
<body>
	<%@ include file="/WEB-INF/header.jsp"%>
	<%@include file="/WEB-INF/headerAdmin.jsp"%>
	<br>
	<%
		String lg = (String) request.getAttribute("loginDouble");
		if (lg != null) {
	%>
	<span style="color: #FF0000; text-align: center"><h2>le login <%=lg%> est déja pris - Action annulée</h2></span>
	<%
		}
	%>

	<br>
	<h1>Gestion des utilisateurs</h1>
	<div id="liste">
		<BUTTON class="actionPagePrincipaleCreer">Créer une personne</BUTTON>
		<div class="tablePersonneAdmin">
			<TABLE id="tablePersonne">
				<THEAD>
					<TR>
						<TH>Nom</TH>
						<TH>Prenom</TH>
						<TH>login</TH>
						<TH>date de naissance</TH>
						<TH>promotion</TH>
						<TH>password</TH>
						<TH>email</TH>
						<TH>infos</TH>
						<TH>disponible</TH>
						<th>projets</th>
						<TH>droits</TH>
						<TH></TH>
					</TR>
				</THEAD>
				<TBODY>
					<c:forEach var="personne" items="${foundPersonnes}">
						<TR>
							<TD><c:out value="${personne.nom}" /></TD>
							<TD><c:out value="${personne.prenom}" /></TD>
							<td><c:out value="${personne.identConnexion}" /></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${personne.dateNaiss}" /></td>
							<td><c:out value="${personne.promotion.libelle}" /></td>
							<td><c:out value="${personne.passw}" /></td>
							<td><c:out value="${personne.email}" /></td>
							<td><c:out value="${personne.infos}" /></td>
							<td>
								<c:choose>
									<c:when test="${personne.disponibilite}">
										<input type="checkbox" checked="checked" disabled> disponible
									</c:when>
									<c:otherwise>
										<input type="checkbox" disabled>pas dispo
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:set var="insererBr" value="false" scope="page" />
								<c:forEach items="${personne.projets}" var="projet">
									<c:if test="${insererBr==true}">
										<br>
										<c:set var="insererBr" value="false" scope="page" />
									</c:if>
									<c:set var="insererBr" value="true" scope="page" />
									<c:out value="- ${projet.projNom}" />
								</c:forEach>
							</td>
							<td><c:out value="${personne.role.roleIntitule}" /></td>
							<TD><c:choose>
									<c:when test="${personne.role.roleId == 3 && loguedPerson.role.roleId==2}">
										<BUTTON class="actionFormulaire" disabled="disabled">Modifier</BUTTON>
									</c:when>
									<c:otherwise>
										<BUTTON class="actionFormulaire" data-id="${personne.id}"
											data-nom="${personne.nom}" data-prenom="${personne.prenom}"
											data-dateNaiss="${personne.dateNaiss}"
											data-promotionlibelle="${personne.promotion.libelle}"
											data-promotionid="${personne.promotion.id}"
											data-passw="${personne.passw}" data-email="${personne.email}"
											data-infos="${personne.infos}"
											data-roleid="${personne.role.roleId}"
											data-identConnexion="${personne.identConnexion}"
											data-disponibilite="${personne.disponibilite}"
											data-cgu="${personne.cgu}"
											>Modifier</BUTTON>
									</c:otherwise>
								</c:choose>
							</TD>
						</TR>
					</c:forEach>
				</TBODY>
			</TABLE>
		</div>
	</div>

	<div id="formulaire">
		<form method="POST" id="formFormulaire">
			<input type="hidden" name="inputId" id="inputId" /> 
			<input type="hidden" name="inputRoleId" id="inputRoleId" /> 
			<input type="hidden" name="inputCgu" id="inputCgu" />
			<input type="hidden" name="inputIdentConnexion" id="inputIdentConnexion" />

			<div>
				<label for="inputNom">nom :</label> 
				<input type="text" id="inputNom" name="inputNom" required>
			</div>
				<label for="inputPrenom">prenom :</label> 
				<input type="text" id="inputPrenom" name="inputPrenom" required>
			<div>
				<label for="inputLogin">login :</label> 
				<input type="text" id="inputLogin" name="inputLogin" onkeyup="VerifLogin()" required>
				<br>
				<input type="text" class="loginExiste" style="background-color: transparent; border: 0px; color: #FF0000">
			</div>
			<div>
				<label for="inputDateNaiss">date de naissance</label> 
				<input type="text" id="inputDateNaiss" name="inputDateNaiss">
			</div>
			<div class="cell">
				<label for="inputPromotion">promotion : </label> 
				<select name="inputPromotion" id="inputPromotion">
					<option value=""></option>
					<c:forEach items="${promotions}" var="promotion">
						<option value="${promotion.id}">${promotion.libelle}
							${promotion.lieu}</option>
					</c:forEach>
				</select>
			</div>

			<div>
				<label for="inputPassw">password :</label> <input type="text"
					id="inputPassw" name="inputPassw" required>
			</div>
			<div>
				<label for="inputEmail">email :</label> <input type="text"
					id="inputEmail" name="inputEmail">
			</div>
			<div>
				<label for="inputInfos">infos :</label> <input type="text"
					id="inputInfos" name="inputInfos">
			</div>
			<div>
				<label for="inputDisponibilite">dispo :</label> <input
					type="checkbox" name="inputDisponibilite" id="inputDisponibilite"
					value="dispo">
			</div>
			<div class="cell">
				<label for="inputRole">droits : </label> <select name="inputRole"
					id="inputRole">
					<option value=""></option>
					<c:forEach items="${listRoles}" var="rol">
						<c:choose>
							<c:when test="${loguedPerson.role.roleId == 3}">
								<option value="${rol.roleId}">${rol.roleIntitule}</option>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${rol.roleId != 3}">
										<option value="${rol.roleId}">${rol.roleIntitule}</option>
									</c:when>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
			<input type="submit" name="update" id="updateDansForm"	value="Modifier" /> 
			<input type="submit" name="create"	id="creerDansForm" value="Créer" /> 
			<input type="submit" name="delete" id="deleteDansForm" value="Supprimer" />
		</form>
	</div>
</body>
</html>
