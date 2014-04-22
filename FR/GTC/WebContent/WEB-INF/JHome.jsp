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

<script type="text/javascript" charset="utf8"
	src="js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css">
<script type="text/javascript" charset="utf8"
	src="js/jquery.dataTables.yadcf.js"></script>
<link rel=stylesheet type="text/css"
	href="css/jquery.dataTables.yadcf.css">

<title>Tableau de bord</title>
<SCRIPT type="text/javascript">
	$(document).ready(function() {

		$('.onlyadmin').hide();
		if ("${loguedPerson.role.roleId}" != 1) {
			$('.onlyadmin').show();
		}
		//$('.actionFormulaire').button();

		var dateString = new Date("${loguedPerson.dateNaiss}");
		$('#inputDateNaiss').datepicker({
			defaultDate : dateString
		});
		$('#inputDateNaiss').val(dateString.toLocaleDateString("fr-FR"));

		$('#formulaire').dialog({
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

		$('#tableProjet').dataTable({
			"bJQueryUI" : true
		}).yadcf([ {
			column_number : 0
		}, {
			column_number : 3,
			filter_type : "auto_complete",
			text_data_delimiter : ","
		}, {
			column_number : 5,
			filter_type : "auto_complete",
			text_data_delimiter : ","
		} ]);

	});
</SCRIPT>


</head>
<body>
	<%@ include file="/WEB-INF/header.jsp"%>
	<div id="divProfil">
		<form method="POST" id="formPrincipale">



			<input type="hidden" name="inputId" id="inputId"
				value="${loguedPerson.id}" />


			<div class="cell2">
				<label for="listeProjets">projets en cours : </label>
				
					<TABLE id="tableProjet">

						<THEAD>
							<TR>
								<TH>Nom du projet</TH>
								<TH>date de début</TH>
								<TH>date de fin</TH>
								<TH>description</TH>
								<TH>chef de projet</TH>
								<TH>membres</TH>
								<TH>avancement</TH>
								<!-- 							<TH></TH> -->
							</TR>
						</THEAD>
						<TBODY>
							<c:forEach items="${projets}" var="prj">
								<tr>
									<TD><c:out value="${prj.projNom}" /></TD>
									<TD><fmt:formatDate pattern="dd/MM/yyyy"
											value="${prj.projDatedebut}" /></TD>
									<TD><fmt:formatDate pattern="dd/MM/yyyy"
											value="${prj.projDatedefin}" /></TD>
									<TD><c:out value="${prj.projDescription}" /></TD>
									<TD><c:out value="${prj.personne.nom}" /></TD>
									<TD>
										<c:forEach items="${prj.travailles}" var="participant">
											<c:out value="${participant.personne.prenom} ${participant.personne.nom}" />
											<br />
										</c:forEach>
									</TD>
									<TD><c:out value="${prj.projAvancement}" /></TD>
								</tr>
							</c:forEach>
						</TBODY>

					</TABLE>
			
			</div>
		</form>
	</div>

</body>
</html>
