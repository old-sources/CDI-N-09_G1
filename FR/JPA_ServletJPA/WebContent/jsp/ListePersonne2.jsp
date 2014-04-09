<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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
                   effect: "explode",
                   duration: 1000
                 }
               })

               $('.actionFormulaire').on('click', function(e) {
                   $( "#formulaire" ).dialog("open");
                
                $('#inputId').val($(this).attr("data-id"));   
  				$('#inputNom').val($(this).attr("data-nom"));
  				$('#inputPrenom').val($(this).attr("data-prenom"));
  				$('#inputDateNaiss').val($(this).attr("data-dateNaiss"));
  			 	$('#inputPromotion').val($(this).attr("data-promotionid"));  
  	
				
  			
				  
                });
			 $('#actionModifDansForm').on('click', function(e) {
				 $.post("/JPA_ServletJPA/HPersonne/read/");
				 $( "#formulaire" ).dialog("close");
             });
			 
             
	});
</SCRIPT>
</head>
<body>

<%@ include file="/WEB-INF/TP13.jsp"%>

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
   <form method="POST">
		<div id="formulaire">
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
           
        </div>
     </form>
</body>
</html>
