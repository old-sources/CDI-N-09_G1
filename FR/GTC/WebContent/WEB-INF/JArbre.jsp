<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="java.util.Date"%>

<!-- Onglet de Gestion des compétences -->
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr" dir="ltr">

<head>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<base href="/GTC/" />

<script type="text/javascript" src="js/jquery-1.10.2.js"></script> <!-- Les sources de la bibliothèque JQuery en local-->
<!--  <script src="http://code.jquery.com/jquery-1.6.2.min.js"></script> Les sources de la bibliothèque JQuery distantes -->

<script type="text/javascript" src="js/jquery-ui-1.10.4.custom.js"></script>

<script type="text/javascript" charset="utf8"
	src="js/jquery.dataTables.min.js"></script>

<script type="text/javascript" charset="utf8"
	src="js/jquery.dataTables.yadcf.js"></script>

<link href="css/south-street/jquery-ui-1.10.4.custom.css"
	rel="stylesheet" type="text/css" />

<link rel=stylesheet type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css"
	href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css">
<link rel=stylesheet type="text/css"
	href="css/jquery.dataTables.yadcf.css">

<title>Gestion de Compétences</title>

<script src="js/scriptComp.js"></script> <!-- La source qui contient le code d'envoi en Ajax -->
</head>

<body>
	<%@ include file="/WEB-INF/header.jsp"%>
	<h1>Onglet Competence (JM en cours)</h1>

	<BUTTON class="actionCreerCompetence">Créer une compétence</BUTTON>

	<!-- 	<div class="tabCompetence" id="tabComp"> celle3-->
<!-- 	<div class="cell3" id="divProjet">  -->
	<div class="cell" id="divComp"> 
		<label for="listeCompétences"> Compétence école </label>

		<TABLE id="tableComp">
			<THEAD>
				<TR><c:forEach items="${foundCompetences}" var="comp">
				<TH><c:out value="${comp.compId}" /></TH>
				</c:forEach>
					<TH>Id compétence</TH>
					<TH>Intitulé</TH>
					<TH>Père</TH>
					<TH>Enfants</TH>
					<TH>Modif</TH>
					<TH>Suppression</TH>
					<TH>Déplacement</TH>
				</TR>
			</THEAD>
			<TBODY>
				<c:forEach items="${foundCompetences}" var="comp">
					<tr>
						<TD><c:out value="${comp.compId}" /></TD>
						<TD><c:out value="${comp.compIntitule}" /> <%-- getter et setteur  automatiques JPA <c:out value="(${comp.getCompIntitule()})" />	--%>
						</TD>
						<TD><c:out value="${comp.competence.getCompIntitule()}" /></TD>
						<TD><c:forEach items="${comp.competences}" var="compchild">
								<c:out value="+ ${compchild.compIntitule}" />
								<br>
							</c:forEach></TD>

						<TD><BUTTON class="actionFormulaireComp"
								data-compId="${comp.compId}"
								data-compIntitule="${comp.compIntitule}"
								data-compParent="${comp.competence}">Modifier</BUTTON></TD>

						<TD><BUTTON class="actionDeleteComp"
								data-compId="${comp.compId}"
								data-compIntitule="${comp.compIntitule}">Supprimer</BUTTON></TD>

						<TD><BUTTON class="actionMoveComp"
								data-compId="${comp.compId}"
								data-compParent="${comp.competence}">Déplacer</BUTTON></TD>
					</tr>
				</c:forEach>
			</TBODY>

		</TABLE>
	</div>

	<div id="formCompDivId"> 
<!-- id de la div récupérée par scriptComp -->
<!-- passé dans actionFormulaireComp par $("#formCompDivId").dialog("open"); -->
<!-- https://fr.wikipedia.org/wiki/Ajax_%28informatique%29 -->
<!-- forme de la boite de dialogue -->
		<form method="POST" id="formFormulaire"> 
		<!-- id formFormulaire récupérée où ? nullepart -->
		<!-- l'ID de la comp est fournie mais restera une donnée cachée -->
			<input type="hidden" name="inputId" id="inputId" />
			<div> <!-- Libelle -->
				<!-- seule donnée de la compétence -->
				<label for="inputLibelleComp">libelle :</label> <input type="text"
					id="inputLibelleComp" name="inputLibelleComp">
			</div> <!-- Libelle -->


			<input type="submit" name="update" id="updateDansForm"
				value="Modifier" /> <input type="submit" name="create"
				id="creerDansForm" value="Créer" /> <input type="submit"
				name="delete" id="deleteDansForm" value="Supprimer" />
		</form>
	</div>


	<div id="formMoveComDivId">
		<form method="POST" id="formFormulaire2"> 
			<input type="hidden" name="inputId" id="inputId2"/>
			<div>
				<!-- donnée parent de la compétence -->
				<label for="inputLibelleParent">parent :</label> <input type="text"
					id="inputLibelleParent" name="inputLibelleParent">
			</div>

			<input type="submit" name="move" id="moveDansForm" value="Deplacer" />
		</form>
	</div>

</body>
</html>