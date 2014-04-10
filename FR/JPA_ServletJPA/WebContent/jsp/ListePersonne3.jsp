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
<base href="/JPA_ServletJPA/" />
<link rel=stylesheet type="text/css" href="css/style.css">
  <link href="css/south-street/jquery-ui-1.10.4.custom.css"
	rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4.custom.js"></script>

<title>Liste Personne</title>
<SCRIPT type="text/javascript">
	$(document).ready(function() {
		
			$('.actionFormulaire').button();
			 $('#inputDateNaiss').datepicker();
			  
			 $('#formulaire').dialog({
                 autoOpen: false,
                 show: {
                   effect: "blind",
                   duration: 1000
                 },
                 hide: {
                   effect: "blind",
                   duration: 1000
                 }
               })

               $('.actionFormulaire').on('click', function(e) {
                   $( "#formulaire" ).dialog("open");
                $('#inputId').val($(this).attr("data-id"));   
  				$('#inputNom').val($(this).attr("data-nom"));
  				$('#inputPrenom').val($(this).attr("data-prenom"));
//   				var today = new Date();
// 				var dateString = today.format("dd/MM/yyyy");
//   				$('#inputDateNaiss').val($(this).attr("data-dateNaiss").format("dd/MM/yyyy"));
				//$.datepicker.formatDate( "yy-mm-dd", new Date( 2007, 1 - 1, 26 ) );
// 				var now = new Date();
// 				alert(now);

// Date inputDateNaiss = simpleDateFormat.parse(inputDateNaissString);

				var dateString = new Date($(this).attr("data-dateNaiss"));
			//	var dateString2 = dateString.format("dd/MM/yyyy");
			//	$('#inputDateNaiss').val($(this).attr("data-dateNaiss"));
 				$('#inputDateNaiss').val(dateString.toLocaleDateString("fr-FR"));
  			 	$('#inputPromotion').val($(this).attr("data-promotionid"));  
				});
             
// 			 $('#actionModifDansForm').on('click', function(e) {

// 				 $.ajax({
// 						url : 'HPersonne/update/',
// 						type : 'POST',
// 						data : {
// 							inputId : $('#inputId').val(),
// 							inputNom : $('#inputNom').val(),
// 							inputPrenom : $('#inputPrenom').val(),
// 							inputDateNaiss : $('#inputDateNaiss').val(),
// 							inputPromotion : $('#inputPromotion').val(),
// 							update : 1 
// 						},
// 						dataType : 'html',
// 						success : function(code_html, statut) {
// 						},
// 						error : function(resultat, statut, erreur) {
// 						},
// 						complete : function(resultat, statut) {
// 						}
// 					});
				 
// 				 $("#formulaire").dialog("close");
				
//              })
			 
 			
	
});	
</SCRIPT>


</head>
<body>

<%@ include file="/WEB-INF/TP13.jsp"%>
<div id="liste">
<a href="TP4_Controller/create">Créer une personne</a>
<TABLE id="tablePersonne">
	<THEAD>
		<TR>
			<TH>Nom</TH>
			<TH>Prenom</TH>
			<TH>date de naissance</TH>
			<TH>promotion</TH>
			<TH></TH>
			<th></th>
		</TR>
	</THEAD>
	<TBODY>
		<c:forEach var="personne" items="${foundPersonnes}">
			<TR>
				<TD><c:out value="${personne.nom}" /></TD>
				<TD><c:out value="${personne.prenom}" /></TD>
				<td><fmt:formatDate pattern="dd/MM/yyyy" value="${personne.dateNaiss}"/></td>
				<td><c:out value="${personne.promotion.libelle}"/></td>
				
				
			
			<!--  <td><a href="TP4_Controller/read/${personne.id}">selection </a>
				<a href="TP4_Controller/delete/${personne.id}">suppression </a></td>
			-->	
				
				<TD><BUTTON class="actionFormulaire" data-id="${personne.id}" data-nom="${personne.nom}" data-prenom="${personne.prenom}" data-dateNaiss="${personne.dateNaiss}" data-promotionlibelle="${personne.promotion.libelle}" data-promotionid="${personne.promotion.id}">Modifier</BUTTON></TD> 
				
				<TD><BUTTON class="actionSupprimer" data-id="${personne.id}">Supprimer</BUTTON></TD>
			</TR>
		</c:forEach>
	  </TBODY>
	</TABLE>
	</div>
	<div id="formulaire">
   <form method="POST" id="formFormulaire">
		

			<input type="hidden" name="inputId" id="inputId" />
            <div>
                <label for="inputNom" >nom :</label>
                <input type="text" id="inputNom" name="inputNom">
          	</div>
                <label for="inputPrenom">prenom :</label>
                <input type="text" id="inputPrenom" name="inputPrenom">
          	 <div>
                <label for="inputDateNaiss">date de naissance</label>
                <input type="text" id="inputDateNaiss" name="inputDateNaiss">
        	</div>
        	<div class="cell">
        			<label for="inputPromotion">promotion : </label>
					<select name="inputPromotion" id="inputPromotion">
						<option value=""></option>
						<c:forEach items="${promotions}" var="promotion">
									<option value="${promotion.id}">${promotion.libelle}</option>	
						</c:forEach>
					</select>
				</div>
            <input type="submit" name="update" id="actionModifDansForm" value="Modifier"/>
<%--             <input type="submit" name="update" id="actionModifDansForm" form="formFormulaire" value="Modifier"/> 
onclick="myFunction()" 

           <input type="button" name="update" id="actionModifDansForm" value="Modifier">--%> 
    
     </form>
</div>
</body>
</html>
