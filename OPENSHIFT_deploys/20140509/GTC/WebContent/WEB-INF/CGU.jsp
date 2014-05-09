<%@page import="model.Personne"%>
<jsp:useBean id="authentifiedPersonne" class="model.Personne"
	scope="session"></jsp:useBean>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CGU</title>
</head>
<body>
	<div id="CGU">
		<span> Bonjour <jsp:getProperty property="prenom"
				name="authentifiedPersonne" /> <jsp:getProperty property="nom"
				name="authentifiedPersonne" />,<br> c'est votre première connexion sur
			TeamSkills <br> 
			pour être autorisé à vous connecter vous devez valider les
			Conditions générales d'utilisation <br>
			* CGU n°1 <br>
			* CGU n°2<br>
			* etc<br>
		</span>
	</div>
	<form method="POST" id="FormCGU">
		<div id="CGUCheckbox">
			<input
					type="checkbox" name="checkboxCGU"
					value="box"/>j'accepte les conditions générales d'utilisation
			<br>	
			<input type="submit" name="valideCGU"	value="Valider ma décision" />
		</div>
	</form>
</body>
</html>