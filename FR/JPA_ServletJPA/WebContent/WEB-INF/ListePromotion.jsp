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
<base href="/JPA_ServletJPA/" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link rel=stylesheet type="text/css" href="css/style.css">
<title>Liste Promotion</title>

</head>
<body>
	<%@ include file="TP13.jsp"%>
	<a href="ListePromotion/create">Créer une promotion</a>
	<table id="tablePromotion">
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
			<td><a href="TP4_Controller/read/${prm.id}">selection </a>
				<a href="TP4_Controller/delete/${prm.id}">suppression </a></td>
			</tr>
		</c:forEach>

		</tbody>
	</table>

</body>

</html>