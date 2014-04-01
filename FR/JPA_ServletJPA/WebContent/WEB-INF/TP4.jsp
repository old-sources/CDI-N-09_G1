<?xml version="1.0" encoding="UTF-8" ?>
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
<title>Hello World</title>
</head>
<body>
	<%@ include file="TP13.jsp"%>
	<a href="TP4_Controller/create">Créer une personne</a>
	<table id="tablePersonne">
		<thead>
			<tr>
				<th>nom</th>
				<th>prenom</th>
				<th>date naissance</th>
				<th>promotion</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		
		<c:forEach items="${foundPersonnes}" var="per">
			<tr>
			<td><c:out value="${per.nom}"/></td>
			<td><c:out value="${per.prenom}"/></td>
			<td><fmt:formatDate pattern="dd/MM/yyy" value="${per.dateNaiss}"/></td>
			<td><c:out value="${per.promotion.libelle}"/></td>
			<td><a href="TP4_Controller/read/${per.id}">selection </a>
				<a href="TP4_Controller/delete/${per.id}">suppression </a></td>
			</tr>
		</c:forEach>

		</tbody>
	</table>

</body>

</html>