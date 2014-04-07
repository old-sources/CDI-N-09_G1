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

<title>Liste Personne</title>

</head>
<body>


<SCRIPT type="text/javascript">
	$(document).ready(
			function() {
				$('.actionModifier').button();
				$('.actionSupprimer').button();



			});
</SCRIPT>
<TABLE id="tablePersonne">
	<THEAD>
		<TR>
			<TH>Nom</TH>
			<TH>Prenom</TH>
			<TH>dateNaiss</TH>
			<TH></TH>
			<th></th>
		</TR>
	</THEAD>
	<TBODY>
		<c:forEach var="personne" items="${personnes}">
			<TR>
				<TD><c:out value="${personne.nom}" /></TD>
				<TD><c:out value="${personne.prenom}" /></TD>
				<TD><c:out value="${personne.dateNaiss}" /></TD>
				<TD><BUTTON class="actionModifier" data-id="${personne.id}">Modifier</BUTTON></TD>
				<TD><BUTTON class="actionSupprimer" data-id="${personne.id}">Supprimer</BUTTON></TD>
			</TR>
		</c:forEach>
	</TBODY>
</TABLE>
</body>
</html>