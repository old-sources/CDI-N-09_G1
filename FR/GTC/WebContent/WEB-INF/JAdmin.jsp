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

<title>Administration</title>
<SCRIPT type="text/javascript">
	function rechercherPersonne() {
		
		$.ajax({
			url : 'HPersonne',
			type : 'GET',
			data : {
			},
			dataType : 'html',
			success : function(code_html, statut) {
				$('#resultatRecherche').children().remove();
				$('#resultatRecherche').append(code_html);
			}
		});
	}

	$(document).ready(
			function() {
				$('.onlyadmin').hide();
				if ("${loguedPerson.role.roleId}" != 1) {
					$('.onlyadmin').show();
				}
				$('#afficheListeUsers').on('click', function(e) {
					rechercherPersonne();
				});
				rechercherPersonne();
				
			});
</SCRIPT>
</head>
<body>
	<%@ include file="/WEB-INF/header.jsp"%>
	<div id="divProfil">

		<span> page Admin </span>
		<BUTTON id="afficheListeUsers">afficher la liste des
			utilisateurs</BUTTON>
		<DIV id="resultatRecherche"></DIV>
</body>
</html>
