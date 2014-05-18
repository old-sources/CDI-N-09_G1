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

	<title>Mes Projets</title>
	<script src="js/scriptMesProjets.js"></script> <!-- Sources Ajax -->
<SCRIPT type="text/javascript">
</SCRIPT>


</head>
<body>
	<%@ include file="/WEB-INF/header.jsp"%>
	
	
	
	<div id="divMesProjets">
		<div class="cell2" id="listeProjets1">
			<label for="listeProjets1">Projets en tant que chef de projet : </label>
			<TABLE class="tableProjet">
				<THEAD>
					<TR>
						<TH>Nom du projet</TH>
						<TH>date de début</TH>
						<TH>date de fin</TH>
						<TH>description</TH>
						<TH>chef de projet</TH>
						<TH>membres</TH>
						<TH>avancement</TH>
						<TH></TH>
					</TR>
				</THEAD>
				<TBODY>
					<c:forEach items="${projetsCdp}" var="prj">
						<tr>
							<TD><c:out value="${prj.projNom}" /></TD>
							<TD><fmt:formatDate pattern="dd/MM/yyyy"
									value="${prj.projDatedebut}" /></TD>
							<TD><fmt:formatDate pattern="dd/MM/yyyy"
									value="${prj.projDatedefin}" /></TD>
							<TD><c:out value="${prj.projDescription}" /></TD>
							<TD><c:out value="${prj.chefDeProjet.nom}" /></TD>
							<TD>
								<c:set var="insererBr" value="false" scope="page" />
								<c:forEach items="${prj.membres}" var="participant">
									<c:if test="${participant.id!=prj.chefDeProjet.id}">
										<c:if test="${insererBr==true}">
											<br>
											<c:set var="insererBr" value="false" scope="page" />
										</c:if>
										<c:out value="${participant.prenom} ${participant.nom}" />
										<c:set var="insererBr" value="true" scope="page" />
									</c:if>
								</c:forEach>
							</TD>
							<TD><c:out value="${prj.projAvancement}" /></TD>
							<TD>
								<BUTTON class="actionFormulaireInvit"
									data-projId="${prj.projId}"
									data-projNom="${prj.projNom}"
									data-projDescription="${prj.projDescription}"
									>Inviter un utilisateur</BUTTON>
							</TD>
						</tr>
					</c:forEach>
				</TBODY>
			</TABLE>
		</div>
		<br><br><br>
		<div class="cell2" id="listeProjets2">
			<label for="listeProjets2">Projets en tant que membre : </label>
			<TABLE class="tableProjet">
				<THEAD>
					<TR>
						<TH>Nom du projet</TH>
						<TH>date de début</TH>
						<TH>date de fin</TH>
						<TH>description</TH>
						<TH>chef de projet</TH>
						<TH>membres</TH>
						<TH>avancement</TH>
						<TH></TH>
					</TR>
				</THEAD>
				<TBODY>
					<c:forEach items="${projetsUser}" var="prj">
						<tr>
							<TD><c:out value="${prj.projNom}" /></TD>
							<TD><fmt:formatDate pattern="dd/MM/yyyy"
									value="${prj.projDatedebut}" /></TD>
							<TD><fmt:formatDate pattern="dd/MM/yyyy"
									value="${prj.projDatedefin}" /></TD>
							<TD><c:out value="${prj.projDescription}" /></TD>
							<TD><c:out value="${prj.chefDeProjet.nom}" /></TD>
							<TD>
								<c:set var="insererBr" value="false" scope="page" />
								<c:forEach items="${prj.membres}" var="participant">
									<c:if test="${participant.id!=prj.chefDeProjet.id}">
										<c:if test="${insererBr==true}">
											<br>
											<c:set var="insererBr" value="false" scope="page" />
										</c:if>
										<c:out value="${participant.prenom} ${participant.nom}" />
										<c:set var="insererBr" value="true" scope="page" />
									</c:if>
								</c:forEach>
							</TD>
							<TD><c:out value="${prj.projAvancement}" /></TD>
							<TD>
								<BUTTON class="actionFormulaireQuit"
									data-projId="${prj.projId}"
									data-projNom="${prj.projNom}"
									data-membreId="${loguedPerson.id}" >Quitter le projet</BUTTON>
							</TD>
						</tr>
					</c:forEach>
				</TBODY>
			</TABLE>
		</div>
	</div>
<!-- formulaire d'ajout d' utilisateurs à un projet -->
<!-- donnees du formulaire preparees dans scriptMesProjets.js -->
	<div id="formInvitDivId" title="Inviter à un projet"> 
		<form method="POST" id="formInvit"> 
			<!-- passage des ID necessaires au traitement des donnees du formulaire -->
			<input type="hidden" name="inputMembreId" id="inputMembreId" />
			<input type="hidden" name="inputProjId" id="inputProjId" />
			<div>
				<label for="affProjNom">Nom du projet:</label>
				<textarea id="affProjNom"></textarea>
				<br>
				<c:if test="${participant.id!=prj.chefDeProjet.id}">
					<label for="affProjDescription">Description:</label>
					<textarea id="affProjDescription"></textarea>
					<br>
				</c:if>
			</div>
			<!-- affichage liste des utilisateurs -->
			<div class="cell">
				<label for="inputNewMemberId">Choisir un nouveau membre: </label>
				<select name="inputNewMemberId" id="inputNewMemberId">
					<option value=""></option>
					<c:forEach items="${usersList}" var="user">
						<option value="${user.id}">${user.identConnexion}</option>
					</c:forEach>
				</select>
			</div>
			<input type="submit" name="invite" id="inviteDansForm" value="Inviter" />
			<input type="submit" name="quitProjet" id="quitDansForm" value="Valider" />
		</form>
	</div>
<!-- formulaire pour quitter un projet -->
<!-- donnees du formulaire preparees dans scriptMesProjets.js -->
<!--	<div id="formQuitDivId" title="Quitter le projet"> 
		<form method="POST" id="formQuit"> 
			<!-- passage des ID necessaires au traitement des donnees du formulaire -->
<!--			<input type="hidden" name="inputMembreId" id="inputMembreId" />
			<input type="hidden" name="inputProjId" id="inputProjId" />
			<div>
				<label for="affProjNom">Nom du projet:</label>
				<textarea id="affProjNom"></textarea>
				<br>
				<p>Etes-vous sûr de vouloir quitter le projet?
				</p>
			</div>
			<input type="submit" name="quitProjet" id="quitDansForm" value="Valider" />
		</form>
	</div> -->
</body>
</html>
