<%@page import="model.Personne"%>
<%@page import="model.Promotion"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr"
	dir="ltr">

<head>
<base href="/JPA_ServletJPA/" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet type="text/css" href="css/style.css">

</head>
<body>
	<%@ include file="TP13.jsp"%>
	<form method="POST">
		<div id="formPromotion">
			<div class="row">
				<div class="cell">
					<label for="">Libelle</label>
				</div>
				<div class="cell">
					<input type="text" name="inputLibelle" value="${promotionSelected.libelle}">
				</div>
			</div>
			<div class="row">
				<div class="cell">
					<label for="">Lieu</label>
				</div>	
				<div class="cell">
					<input type="text" name="inputLieu"
						value="${promotionSelected.lieu}">
				</div>

			</div>
			<div class="row">

				<div class="cell">
					<label for="">Date de debut</label>
				</div>
				<div class="cell">
					<input type="text" name="inputDateDebut"
						value="<fmt:formatDate pattern="dd/MM/yyyy"
							value="${promotionSelected.dateDebut}"/>">
				</div>
			<div class="row">

				<div class="cell">
					<label for="">Date de fin</label>
				</div>
				<div class="cell">
					<input type="text" name="inputDateFin"
						value="<fmt:formatDate pattern="dd/MM/yyyy"
							value="${promotionSelected.dateFin}"/>">
				</div>
			</div>
			
		</div>
		<input type="hidden" name="inputId" value="${promotionSelected.id}" />
		<c:choose>
			<c:when test="${empty promotionSelected.id}">
				<input type="submit" name="create" value="Créer" />
			</c:when>
			<c:otherwise>
				<input type="submit" name="update" value="Modifier" />
			</c:otherwise>
		</c:choose>
	</form>
	<a href="HPromotion/read/">retour liste</a>
</body>
</html>