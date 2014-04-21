<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="java.util.Date"%>
<%@page import="model.Personne"%>

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

<title>Administration</title>
<SCRIPT type="text/javascript">
	$(document).ready(function() {

		$('#divProfil').hide();
		if ("${loguedPerson.role.roleId}" != 1) {
			$('#divProfil').show();
		}

		$('.onlyadmin').hide();
		console.log('1;');
		if ("${loguedPerson.role.roleId}" != 1) {
			$('.onlyadmin').show();
			console.log('2;');
		}

// 		$('#afficheListeUsers').on('click', function(e) {
// 			document.location.href = "/GTC/HPersonne";
// 		});
// 		$('#afficheListePromotions').on('click', function(e) {
// 			document.location.href = "/GTC/HPromotion";
// 		});
// 		$('#afficheListeProjets').on('click', function(e) {
// 			document.location.href = "/GTC/HProjet";
// 		});
// // 		$('#importerUnFichier').on('click', function(e) {
// // 			document.location.href = "/GTC/HImport";
// // 		});
// 		$('#upload').on('click', function(e) {
// 			document.location.href = "/GTC/HProjet";
//		});


	});
</SCRIPT>
</head>
<body>



	<%@ include file="/WEB-INF/header.jsp"%>
	<%@include file="/WEB-INF/headerAdmin.jsp" %>
	<div id="divProfil">
<%--
		<span> page Admin </span><br>
		<BUTTON id="afficheListeUsers">Gestion des utilisateurs</BUTTON>

		<br>
		<br>
		<BUTTON id="afficheListePromotions">Gestion des promotions</BUTTON>
		<br>
		<br>
		<BUTTON id="afficheListeProjets">Gestion des projets</BUTTON>

		<br>
		<br>
		<BUTTON id="afficherListeCompetences">Gestion des compétences</BUTTON>
		<br>
		<br>

 --%>

<c:choose>
									<c:when test="${userId == 3}">
										
								



<br><br><br><h4>mode super-admin : imports/exports de fichiers étudiants</h4>

		<span id="importerUnFichier">importer un fichier
			d'étudiants</span>

		<form METHOD="POST" action="HImport">
			<input type="file" size="10" name="file1"> <br />
			<input type="submit" value="Import" name = "submitUpload">
		</form>
		
		<br><br>
		<span id="importerUnFichier">exporter la liste des étudiants dans un fichier</span>
		<form METHOD="POST" action="HImport">
			<input type="file" size="10" name="file2"> <br />
			<input type="submit" value="Export" name = "submitDownload">
		</form>
		
<%
String impnok = (String) request.getAttribute("importImpossibleLoginDouble");
if (impnok == "true"){
%>
	<span style="color:#FF0000;text-align : center"> <h2>il existe des doublons de login dans le fichier sélectionné - Action annulée </h2></span>
<%
}
%>

</c:when>
</c:choose>


</div>
</body>
</html>
