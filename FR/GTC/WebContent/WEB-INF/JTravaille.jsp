<%@page import="java.util.List"%>
<%@page import="model.Personne"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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


<title>Liste Promotion</title>
<SCRIPT type="text/javascript">
	$(document).ready(function() {
		$('.onlyadmin').hide();
		if ("${loguedPerson.role.roleId}" != 1) {
			$('.onlyadmin').show();
		}

		$('#formulaireRelation').dialog({
			autoOpen : false,
			show : {
				effect : "blind",
				duration : 1000
			},
			hide : {
				effect : "blind",
				duration : 1000
			}
		})

		$('.actionFormulaireRelation').on('click', function(e) {
			$("#formulaireRelation").dialog("open");
			$('#inputPersonneLogin').val($(this).attr("data-personneid"));
			$('#inputProjet').val($(this).attr("data-projetid"));
			
			$('#updateDansForm').show();
			$('#deleteDansForm').show();
			$('#creerDansForm').hide();
		});

		$('.actionPagePrincipaleCreer').on('click', function(e) {
			$("#formulaireRelation").dialog("open");
			$('#inputPersonneLogin').val("");
			$('#inputProjet').val("");

			$('#deleteDansForm').hide();
			$('#updateDansForm').hide();
			$('#creerDansForm').show();
		});

		$('.actionRetourPageAdmin').on('click', function(e) {
			document.location.href = "/GTC/Admin";
		});

		$('#tableRelation').dataTable({
			"bJQueryUI" : true
		}).yadcf([]);

	});
</SCRIPT>




</head>
<body>
	<%@ include file="header.jsp"%>
	<%@include file="/WEB-INF/headerAdmin.jsp" %>	
	
	<br>
	<h1>Gestion des relations personnes / projets</h1>
	
	
	<BUTTON class="actionPagePrincipaleCreer">Créer une relation</BUTTON>
	<div id="tableRelationPPAdmin">
		<table id="tableRelation">
			<thead>
				<tr>
					<th>personne</th>
					<th>projet</th>
					<th></th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${foundTravailles}" var="trv">
					<tr>

						<td><c:out value="${trv.personne.identConnexion}" /></td>
						<td><c:out value="${trv.projet.projNom}" /></td>
						<TD><BUTTON class="actionFormulaireRelation"
								data-personneid="${trv.personne.id}" 
								data-personnelogin="${trv.personne.identConnexion}"
								data-projetnom="${trv.projet.projNom}" 
								data-projetid="${trv.projet.projId}"
								>Modifier</BUTTON></TD>

					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>


	<div id="formulaireRelation">
		<form method="POST" id="formFormulaire">

			<input type="hidden" name="inputPersonneId" id="inputPersonneId" />
			<input type="hidden" name="inputProjetId" id="inputProjetId" />
			
			<div class="cell">
				<label for="inputPersonneLogin">login : </label> <select
					name="inputPersonneLogin" id="inputPersonneLogin">
					<option value=""></option>
					<c:forEach items="${foundPersonnes}" var="personne">
						<option value="${personne.id}">${personne.identConnexion}
							</option>
					</c:forEach>
				</select>
			</div>
			<div class="cell">
				<label for="inputProjet">projet : </label> <select
					name="inputProjet" id="inputProjet">
					<option value=""></option>
					<c:forEach items="${foundTravailles}" var="trv">
						<option value="${trv.projet.projId}">${trv.projet.projNom}
							</option>
					</c:forEach>
				</select>
			</div>
			

			<input type="submit" name="update" id="updateDansForm"
				value="Modifier" /> <input type="submit" name="create"
				id="creerDansForm" value="Créer" /> <input type="submit"
				name="delete" id="deleteDansForm" value="Supprimer" />


		</form>
	</div>


</body>

</html>