<%@page import="java.util.List"%>
<%@page import="model.Personne"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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


<title>Liste Promotion</title>
<SCRIPT type="text/javascript">
	$(document).ready(function() {
		$('.onlyadmin').hide();
		if ("${loguedPerson.role.roleId}" != 1){
			$('.onlyadmin').show();
			}
		$('.actionFormulairePromotion').button();

		$('#formulairePromotion').dialog({
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

		$('.actionFormulairePromotion').on('click', function(e) {
			$("#formulairePromotion").dialog("open");
			$('#inputId').val($(this).attr("data-id"));
			$('#inputLibelle').val($(this).attr("data-libelle"));
			$('#inputLieu').val($(this).attr("data-lieu"));

			var dateString = new Date($(this).attr("data-dateD"));
			$('#inputDateDebut').datepicker({defaultDate : dateString});
			$('#inputDateDebut').val(dateString.toLocaleDateString("fr-FR"));

			dateString = new Date($(this).attr("data-dateF"));
			$('#inputDateFin').datepicker({defaultDate : dateString});
			$('#inputDateFin').val(dateString.toLocaleDateString("fr-FR"));

			$('#updateDansForm').show();
			$('#deleteDansForm').show();
			$('#creerDansForm').hide();
		});

		$('.actionPagePrincipaleCreer').on('click', function(e) {
			$("#formulairePromotion").dialog("open");
			$('#inputId').val("");
			$('#inputLibelle').val("");
			$('#inputDateDebut').datepicker({defaultDate : new Date()});
			$('#inputDateDebut').val("");
			$('#inputDateFin').datepicker({defaultDate : new Date()});
			$('#inputDateFin').val("");
			
			$('#deleteDansForm').hide();
			$('#updateDansForm').hide();
			$('#creerDansForm').show();
		});

		$('.actionRetourPageAdmin').on('click', function(e) {
			document.location.href="/GTC/Admin";
		});
		
	});
</SCRIPT>




</head>
<body>
	<%@ include file="header.jsp"%>
	<BUTTON class="actionRetourPageAdmin">Retour page admin</BUTTON>
	<br><br><br>
	<BUTTON class="actionPagePrincipaleCreer">Créer une promotion</BUTTON>
	<table id="tablePersonne">
		<thead>
			<tr>
				<th>libelle</th>
				<th>lieu</th>
				<th>date de debut</th>
				<th>date de fin</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		
		<c:forEach items="${foundPromotions}" var="prm">
			<tr>
			
			<td><c:out value="${prm.libelle}"/></td>
			<td><c:out value="${prm.lieu}"/></td>
			<td><fmt:formatDate pattern="dd/MM/yyyy" value="${prm.dateDebut}"/></td>
			<td><fmt:formatDate pattern="dd/MM/yyyy" value="${prm.dateFin}"/></td>
			<TD><BUTTON class="actionFormulairePromotion" data-id="${prm.id}" data-libelle="${prm.libelle}" data-lieu="${prm.lieu}" data-dateD="${prm.dateDebut}" data-dateF="${prm.dateFin}">Modifier</BUTTON></TD>
			
			</tr>
		</c:forEach>

		</tbody>
	</table>


<div id="formulairePromotion">
		<form method="POST" id="formFormulaire">

			<input type="hidden" name="inputId" id="inputId" />
			<div>
				<label for="inputLibelle">libelle :</label> 
				<input type="text" id="inputLibelle" name="inputLibelle">
			</div>
			<label for="inputLieu">lieu :</label> 
			<input type="text"	id="inputLieu" name="inputLieu">
			<div>
				<label for="inputDateDebut">date de début</label> 
				<input type="text" id="inputDateDebut" name="inputDateDebut">
			</div>
			<div>
				<label for="inputDateFin">date de fin</label> 
				<input type="text" id="inputDateFin" name="inputDateFin">
			</div>
	
				<input type="submit" name="create" id="creerDansForm" value="Créer" />
				<input type="submit" name="update" id="updateDansForm" value="Modifier" />
				<input type="submit" name="delete" id="deleteDansForm" value="Supprimer" />


		</form>
	</div>


</body>

</html>