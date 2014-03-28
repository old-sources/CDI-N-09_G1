<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="model.Personne"%>
<jsp:useBean id="authentifiedPersonne"
	class="model.Personne" scope="session"></jsp:useBean>
<header>
	<div>
		<span> <jsp:getProperty property="nom"
				name="authentifiedPersonne" /> - <jsp:getProperty property="prenom"
				name="authentifiedPersonne" />
		</span> <a href="/JPA_ServletJPA/TP13_Deconnection">Deconnection</a>
	</div>
</header>