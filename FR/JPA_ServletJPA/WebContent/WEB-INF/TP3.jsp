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
<link rel=stylesheet type="text/css" href="TP5.css">
</head>
<body>
	<%@ include file="TP13.jsp"%>
	<form method="POST">
		<div id="formPersonne">
			<div class="row">
				<div class="cell">
					<label for="">Nom</label>
				</div>
				<div class="cell">
					<input type="text" name="inputNom" value="${personneSelected.nom}">
				</div>
			</div>
			<div class="row">
				<div class="cell">
					<label for="">Prenom</label>
				</div>
				<div class="cell">
					<input type="text" name="inputPrenom"
						value="${personneSelected.prenom}">
				</div>

			</div>
			<div class="row">

				<div class="cell">
					<label for="">Date de naissance</label>
				</div>
				<div class="cell">
					<input type="text" name="inputDateNaiss"
						value="<fmt:formatDate pattern="dd/MM/yyyy"
							value="${personneSelected.dateNaiss}"/>">
				</div>
			</div>
			<div>
				<div class="cell">
					<label for="">promotion</label>
				</div>
				<div class="cell">
					<select name="inputPromotion">
						<option value=""></option>
						<c:forEach items="${promotions}" var="promotion">
							<c:choose>
								<c:when
									test="${promotion.id==personneSelected.promotion.id}">
									<option value="${promotion.id}" selected>
										${promotion.libelle}</option>
								</c:when>
								<c:otherwise>
									<option value="${promotion.id}">${promotion.libelle}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="row">

				<div class="cell">
					<label for="">password</label>
				</div>
				<div class="cell">
					<input type="password" name="inputPassword">
				</div>

			</div>
		</div>
		<input type="hidden" name="inputId" value="${personneSelected.id}" />
		<c:choose>
			<c:when test="${empty personneSelected.id}">
				<input type="submit" name="create" value="Créer" />
			</c:when>
			<c:otherwise>
				<input type="submit" name="update" value="Modifier" />
			</c:otherwise>
		</c:choose>
	</form>
	<a href="TP4_Controller/read/">retour liste</a>
</body>
</html>