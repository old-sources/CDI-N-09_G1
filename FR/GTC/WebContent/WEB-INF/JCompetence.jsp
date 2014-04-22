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

<script type="text/javascript" src="js/jquery-1.10.2.js"></script>
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

<SCRIPT type="text/javascript">
	$(document).ready(function() {

		$('#tableComp').dataTable({
			"bJQueryUI" : true
		}).yadcf([ 
// {column_number : 0},{
//	{column_number : 3,filter_type : "auto_complete",text_data_delimiter : ","}, 
//  {column_number : 5, filter_type : "auto_complete", text_data_delimiter : ","} 
	]);

		$('.onlyadmin').hide();
		if ("${loguedPerson.role.roleId}" != 1) {
			$('.onlyadmin').show();
		}
		$('.actionFormulaireComp').button();

		// ouverture du formulaire avec l'id de la div
		$('#formCompDivId').dialog({ 
			autoOpen : false,
			show : {
				effect : "blind",
				duration : 1000
			},
			hide : {
				effect : "blind",
				duration : 1000
			}
		});

		$('.actionFormulaireComp').on('click', function(e) {
			// ouverture du formulaire avec l'id de la div
			$("#formCompDivId").dialog("open");
			$('#inputId').val($(this).attr("data-compId"));
			$('#inputLibelleComp').val($(this).attr("data-compIntitule"));

			$('#updateDansForm').show();
			$('#deleteDansForm').show();
			$('#creerDansForm').hide();
		});

		$('.actionPagePrincipaleCreer').on('click', function(e) {
			$("#formulairePromotion").dialog("open"); //formulairePromotion div
			$('#inputId').val("");
			$('#inputLibelleComp').val("");
	
			$('#deleteDansForm').hide();
			$('#updateDansForm').hide();
			$('#creerDansForm').show();
		});

		$('.actionRetourPageHome').on('click', function(e) {
			document.location.href = "/GTC/Home";
		});

		
	});

</SCRIPT>
</head>

<body>
	<%@ include file="/WEB-INF/header.jsp"%>
	<h1>Onglet Competence (JM en cours)</h1>
	
	<BUTTON class="actionPagePrincipaleCreer">Créer une promotion</BUTTON>
	
	<!-- 	<div class="tabCompetence" id="tabComp"> -->
	<div class="cell3" id="divProjet">
		<label for="listeCompétences"> compétence école </label>

		<TABLE id="tableComp">
			<THEAD>
				<TR>
					<TH>Id compétence</TH>
					<TH>Intitulé</TH>
					<TH>Père</TH>
					<TH>Enfants</TH>
					<TH>Action</TH>
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
								<c:out value="+" />
								<c:out value="${compchild.compIntitule}" />
							</c:forEach></TD>

 						<TD><BUTTON class="actionFormulaireComp"
								data-compId="${comp.compId}" 
								data-compIntitule="${comp.compIntitule}"
								>Modifier</BUTTON></TD>
					</tr>
				</c:forEach>
			</TBODY>

		</TABLE>
	</div>
	
	<div id="formCompDivId"> 
<!-- forme de la boite de dialogue -->
		<form method="POST" id="formFormulaire">
		<!-- l'ID de la comp est fournie mais restera une donnée cachée -->
			<input type="hidden" name="inputId" id="inputId" /> 
			<div>
			<!-- seule donnée de la compétence -->
				<label for="inputLibelleComp">libelle :</label> <input type="text"
					id="inputLibelleComp" name="inputLibelleComp">
			</div>

			<input type="submit" name="update" id="updateDansForm" value="Modifier"/> 
			<input type="submit" name="create" id="creerDansForm" value="Créer"/> 
			<input type="submit" name="delete" id="deleteDansForm" value="Supprimer"/>

		</form>
	</div>

</body>
</html>