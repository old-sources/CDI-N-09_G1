<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr"
	dir="ltr">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<base href="/JPA_ServletJPA/" />
<link rel=stylesheet type="text/css" href="css/style.css">
  <link href="css/south-street/jquery-ui-1.10.4.custom.css"
	rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4.custom.js"></script>
<script type="text/javascript" src="js/jquery.ui.datepicker-fr.js"></script>
<title>Formulaire Personne</title>

</head>
<body>



<SCRIPT type="text/javascript">
	$(document).ready(function() {
		//console.log($.datepicker.regional['fr']);
		//$( "#datepicker" ).datepicker( $.datepicker.regional[ "fr" ] );
		$('#inputDateNaiss').datepicker($.datepicker.regional['fr']);
		$('#actionAjouterForm').button();
		$('#actionModifierForm').button();
		$('#dataModified').button().hide();
		$('#actionAjouterForm').on('click', function(e) {
			$.ajax({
				url : 'ajouterPersonne',
				type : 'POST',
				data : {
					nom : $('#inputNom').val(),
					prenom : $('#inputPrenom').val(),
					dateNaiss : $('#inputDateNaiss').val()
				},
				dataType : 'html',
				success : function(code_html, statut) {
					$('#dataModified').click();
					//$('#formulaire').dialog('close');

				},
				error : function(resultat, statut, erreur) {
				},
				complete : function(resultat, statut) {
				}
			});
		});
		$('#actionModifierForm').on('click', function(e) {
			$.ajax({
				url : 'modifierPersonne/'+$(this).attr('data-id'),
				type : 'POST',
				data : {
					nom : $('#inputNom').val(),
					prenom : $('#inputPrenom').val(),
					dateNaiss : $('#inputDateNaiss').val()
				},
				dataType : 'html',
				success : function(code_html, statut) {
					$('#dataModified').click();
					//$('#formulaire').dialog('close');
				},
				error : function(resultat, statut, erreur) {
				},
				complete : function(resultat, statut) {
				}
			});
		});
	});
</SCRIPT>
<div>
	<label for="inputNom">nom</label> <input type="text" id="inputNom"
		value="${foundPersonne.nom}">
</div>
<div>
	<label for="inputPrenom">prenom</label> <input type="text"
		id="inputPrenom" value="${foundPersonne.prenom}">
</div>
<div>
	<label for="inputDateNaiss">date de naissance</label> <input
		type="text" id="inputDateNaiss" value="${foundPersonne.dateNaiss}">
</div>
<div>
	<c:choose>
		<c:when test="${empty foundPersonne}">
			<button id="actionAjouterForm">Ajouter</button>
		</c:when>
		<c:otherwise>
			<button id="actionModifierForm" data-id="${foundPersonne.id}">Modifier</button>
		</c:otherwise>
	</c:choose>
	<button id="dataModified"></button>
</div>

</body>
</html>