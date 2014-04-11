<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4.custom.js"></script>
<link href="styles/south-street/jquery-ui-1.10.4.custom.css"
	rel="stylesheet" type="text/css" />

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
<SCRIPT type="text/javascript">
	function rechercherPersonne(personne) {
		var personne = personne || {};
		$.ajax({
			url : 'rechercherPersonne',
			type : 'GET',
			data : {
				nom : personne.nom,
				prenom : personne.prenom
			},
			dataType : 'html',
			success : function(code_html, statut) {
				$('#resultatRecherche').children().remove();
				$('#resultatRecherche').append(code_html);
				$('#resultatRecherche').find('.actionModifier').on(
						'click',
						function(e) {
							$.ajax({
								url : 'modifierPersonne/'
										+ $(this).attr("data-id"),
								type : 'GET',
								dataType : 'html',
								success : function(code_html, statut) {
									$('#formulaire').children().remove();
									$('#formulaire').append(code_html);
									$('#formulaire').dialog('open');
									$('#formulaire').find('#dataModified').on(
											'click',
											function() {
												$('#formulaire')
														.dialog('close');
												rechercherPersonne();
											});

								},
								error : function(resultat, statut, erreur) {
								},
								complete : function(resultat, statut) {
								}
							});
						});
				$('#resultatRecherche').find('.actionSupprimer').on(
						'click',
						function(e) {
							$.ajax({
								url : 'supprimerPersonne/'
										+ $(this).attr("data-id"),
								type : 'GET',
								dataType : 'html',
								success : function(code_html, statut) {
												rechercherPersonne();

								},
								error : function(resultat, statut, erreur) {
								},
								complete : function(resultat, statut) {
								}
							});
						});
			},
			error : function(resultat, statut, erreur) {
			},
			complete : function(resultat, statut) {
			}
		});

	}
	$(document).ready(
			function() {
				rechercherPersonne();

				$('#actionAjouter').button();
				$('#actionRechercher').button();
				$('#formulaire').dialog({
					autoOpen : false,
					modal : true,
					show : {
						effect : "blind",
						duration : 1000
					},
					hide : {
						effect : "explode",
						duration : 1000
					}
				});

				$('#actionAjouter').on(
						'click',
						function(e) {
							$.ajax({
								url : 'ajouterPersonne',
								type : 'GET',
								dataType : 'html',
								success : function(code_html, statut) {
									$('#formulaire').children().remove();
									$('#formulaire').append(code_html);
									$('#formulaire').dialog('open');
									$('#formulaire').find('#dataModified').on(
											'click',
											function() {
												$('#formulaire')
														.dialog('close');
												rechercherPersonne();
											});

								},
								error : function(resultat, statut, erreur) {
								},
								complete : function(resultat, statut) {
								}
							});

						});
				$('#actionRechercher').on('click', function(e) {
					rechercherPersonne({
						nom : $("#inputRechercheNom").val(),
						prenom : $("#inputRecherchePrenom").val()
					});
				});

			});
</SCRIPT>
</head>
<body>
	<DIV id="recherche">
		<DIV id="formulaireRecherche">
			<LABEL for="inputRechercheNom">nom : </LABEL> <INPUT type="text"
				id="inputRechercheNom" /> <LABEL for="inputRecherchePrenom">prenom
				: </LABEL> <INPUT type="text" id="inputRecherchePrenom" />
			<BUTTON id="actionRechercher">Rechercher</BUTTON>
			<BUTTON id="actionAjouter">Ajouter</BUTTON>
		</DIV>
		<DIV id="resultatRecherche"></DIV>
	</DIV>
	<DIV id="formulaire"></DIV>
</body>
</html>