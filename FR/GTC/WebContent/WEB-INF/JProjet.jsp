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
<link rel="stylesheet" type="text/css"	href="css/jquery.dataTables.css">
<script type="text/javascript" charset="utf8"
	src="js/jquery.dataTables.yadcf.js"></script>
<link rel=stylesheet type="text/css"
	href="css/jquery.dataTables.yadcf.css">

	<title>Gestion des Projets</title>
	<script src="js/scriptProjet.js"></script> <!-- Sources Ajax -->
</head>
<body>
	<%@ include file="/WEB-INF/header.jsp"%>
	
	<%@include file="/WEB-INF/headerAdmin.jsp" %>
	
	<br>
	<h1>Gestion des projets</h1>

	<BUTTON class="actionPagePrincipaleCreer">Créer un projet</BUTTON>

	<div class="cell3" id="divProjet">

		<label for="listeProjets">projets en cours : </label>

				<TABLE id="tableProjetAdmin">

					<THEAD>
						<TR>
							<TH>Nom du projet</TH>
							<TH>date de début</TH>
							<TH>date de fin</TH>
							<TH>description</TH>
							<TH>chef de projet</TH>
							<TH>membres</TH>
							<TH>avancement</TH>
							<th></th>
						</TR>
					</THEAD>
					<TBODY>
						<c:forEach items="${foundProjets}" var="prj">
							<tr>
								<TD><c:out value="${prj.projNom}" /></TD>
								<TD><fmt:formatDate pattern="dd/MM/yyyy"
										value="${prj.projDatedebut}" /></TD>
								<TD><fmt:formatDate pattern="dd/MM/yyyy"
										value="${prj.projDatedefin}" /></TD>
								<TD><c:out value="${prj.projDescription}" /></TD>
								<TD><c:out value="${prj.chefDeProjet.nom}" /></TD>
								<TD>
									<c:forEach items="${prj.membres}" var="participant">
										<c:out value="${participant.prenom} ${participant.nom}" />
										<br />
									</c:forEach>
								</TD>
								<TD><c:out value="${prj.projAvancement}" /></TD>

								<TD>
									<BUTTON class="actionFormulaire"
										data-projId="${prj.projId}"
										data-projNom="${prj.projNom}"
										data-projDatedebut="${prj.projDatedebut}"
										data-projDatedefin="${prj.projDatedefin}"
										data-projDescription="${prj.projDescription}"
										data-projWikiCdp="${prj.projWikiCdp}"
										data-projWikiMembre="${prj.projWikiMembre}"
										data-projAvancement="${prj.projAvancement}"
										data-chefDeProjetNom="${prj.chefDeProjet.nom}"
										data-chefDeProjetId="${prj.chefDeProjet.id}">Modifier</BUTTON>
								</TD>
							</tr>
						</c:forEach>
					</TBODY>

				</TABLE>

		<!-- 			</div> -->

	</div>


	<div id="formulaire">
		<form method="POST" id="formFormulaire">


			<input type="hidden" name="inputProjId" id="inputProjId" />
<!-- 			<input type="hidden" name="inputCdpId" id="inputCdpId"/> -->
			<div>
				<label for="inputProjNom">nom du projet :</label> <input type="text" id="inputProjNom"
					name="inputProjNom">
			</div>
			<label for="inputProjDatedebut">date de début :</label> <input type="text"
				id="inputProjDatedebut" name="inputProjDatedebut">
			<div>
				<label for="inputProjDatedefin">date de fin</label> <input
					type="text" id="inputProjDatedefin" name="inputProjDatedefin">
			</div>
			
			<div>
				<label for="inputprojDescription">description du projet</label> <input
					type="text" id="inputprojDescription" name="inputprojDescription">
			</div>
			
			<div>
				<label for="inputProjWikiCdp">en charge du projet </label> <input
					type="text" id="inputProjWikiCdp" name="inputProjWikiCdp">
			</div>
			
			<div>
				<label for="inputprojWikiMembre">membres du projet</label> <input
					type="text" id="inputprojWikiMembre" name="inputprojWikiMembre">
			</div>
			
			<div>
				<label for="inputprojAvancement">avancement</label> <input
					type="text" id="inputprojAvancement" name="inputprojAvancement">
			</div>
			
			<div class="cell">
				<label for="inputCdpId">chef de projet : </label> <select
					name="inputCdpId" id="inputCdpId">
					<option value=""></option>
					<c:forEach items="${foundPersonnes}" var="personne">
						<option value="${personne.id}">${personne.nom} ${personne.prenom}
						</option>
					</c:forEach>
				</select>
			</div>
			
			
			<input type="submit" name="update" id="updateDansForm"
				value="Modifier" /> 
				<input type="submit" name="create" id="creerDansForm" value="Créer" />
				<input type="submit" name="delete"
				id="deleteDansForm" value="Supprimer" />
			



		</form>
	</div>


</body>
</html>