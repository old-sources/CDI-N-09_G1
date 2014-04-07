<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<SCRIPT type="text/javascript">
	$(document).ready(
			function() {
				$('.actionModifier').button();
				$('.actionSupprimer').button();



			});
</SCRIPT>
<TABLE>
	<THEAD>
		<TR>
			<TH>Nom</TH>
			<TH>Prenom</TH>
			<TH>dateNaiss</TH>
		</TR>
	</THEAD>
	<TBODY>
		<c:forEach var="personne" items="${personnes}">
			<TR>
				<TD><c:out value="${personne.nom}" /></TD>
				<TD><c:out value="${personne.prenom}" /></TD>
				<TD><c:out value="${personne.dateNaiss}" /></TD>
				<TD><BUTTON class="actionModifier" data-id="${personne.id}">Modifier</BUTTON></TD>
				<TD><BUTTON class="actionSupprimer" data-id="${personne.id}">Supprimer</BUTTON></TD>
			</TR>
		</c:forEach>
	</TBODY>
</TABLE>