<?xml version="1.0" encoding="UTF-8" ?>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet type="text/css" href="/JPA_ServletJPA/TP5.css">
</head>
<body>
	<jsp:useBean id="personneSelected"
		class="model.Personne" scope="request" />
	<jsp:useBean id="promotions" class="java.util.ArrayList"
		scope="request" />
	<%@ include file="TP13.jsp"%>
	<%-- TODO inclusion du header --%>
	<%
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"dd/MM/yyyy");
	%>
	<form method="POST">
		<div id="formPersonne">
			<div class="row">
				<div class="cell">
					<label for="">nom</label>
				</div>
				<div class="cell">
					<input type="text" name="inputNom"
					value="<%=personneSelected.getNom()%>">    
<%-- 						<c:out value="${personneSelected.getNom()}"/>  --%>
				</div>
			</div>
			<div class="row">
				<div class="cell">
					<label for="">prenom</label>
				</div>
				<div class="cell">
					<input type="text" name="inputPrenom"
						value="<%=personneSelected.getPrenom()%>">
				</div>

			</div>
			<div class="row">

				<div class="cell">
					<label for="">Date naissance</label>
				</div>
				<div class="cell">
					<input type="text" name="inputDateNaiss"
						value="<%=personneSelected.getDateNaiss()!=null?simpleDateFormat.format(personneSelected.getDateNaiss()):""%>">
				</div>
			</div>
			<div>
				<div class="cell">
					<label for="">promotion</label>
				</div>
				<div class="cell">
					<select name="inputPromotion">
						<option value=""></option>
						<%
							for (int i = 0; i < promotions.size(); i++) {
								Promotion promotion = (Promotion) promotions.get(i);
								Boolean selected = false;
								if (personneSelected.getPromotion() != null
										&& personneSelected.getPromotion().getId()
												.equals(promotion.getId())) {
									selected = true;
								}
						%>
						<option value="<%=promotion.getId()%>"
							<%=selected ? "selected" : ""%>>
							<%=promotion.getLibelle()%></option>
						<%
							}
						%>
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
		<input type="hidden" name="inputId"
			value="<%=personneSelected.getId()%>" /> <input type="submit"
			name="<%=personneSelected.getId() != null ? "update" : "create"%>"
			value="Valider" />
	</form>
	<a href="/JPA_ServletJPA/TP4_Controller/read/">retour liste</a>
</body>
</html>