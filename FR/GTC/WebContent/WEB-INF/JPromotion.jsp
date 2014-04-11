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
			$('#inputNom').val($(this).attr("data-nom"));
			$('#inputPrenom').val($(this).attr("data-prenom"));

			var dateString = new Date($(this).attr("data-dateNaiss"));
			$('#inputDateNaiss').datepicker({
				defaultDate : dateString
			});
			$('#inputDateNaiss').val(dateString.toLocaleDateString("fr-FR"));
			$('#inputPromotion').val($(this).attr("data-promotionid"));
			$('#updateDansForm').attr('value',"Modifier");
			$('#deleteDansForm').show();
		});

		$('.actionPagePrincipaleCreer').on('click', function(e) {
			$("#formulaire").dialog("open");
			$('#inputNom').val("");
			$('#inputPrenom').val("");
			$('#inputDateNaiss').datepicker({defaultDate : new Date()});
			$('#inputDateNaiss').val("01/01/1980");
			$('#inputPromotion').val("");
			$('#updateDansForm').attr('value',"Créer");
			$('#deleteDansForm').hide();
		});
		
	});
</SCRIPT>




</head>
<body>
	<%@ include file="header.jsp"%>
	<a href="ListePromotion/create">Créer une promotion</a>
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
			<TD><BUTTON class="actionFormulairePromotion" data-prm="${prm}">Modifier</BUTTON></TD>
			
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
				<input type="text" id="inputDateNaiss" name="inputDateNaiss">
			</div>
	
				<input type="submit" name="update" id="updateDansForm" value="Modifier" />
				<input type="submit" name="delete" id="deleteDansForm" value="Supprimer" />

		</form>
	</div>


</body>

</html>