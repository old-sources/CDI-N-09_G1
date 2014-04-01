<%@page import="model.Personne"%>
<jsp:useBean id="authentifiedPersonne"
	class="model.Personne" scope="session"></jsp:useBean>
<header>
	<div>
<<<<<<< HEAD
		<span> <jsp:getProperty property="nom" name="authentifiedPersonne" /> - 
		<jsp:getProperty property="prenom" name="authentifiedPersonne" />
		</span> <a href="/JPA_ServletJPA/TP13_Deconnection">Deconnection</a>
=======
		<span> <jsp:getProperty property="nom"
				name="authentifiedPersonne" /> - <jsp:getProperty property="prenom"
				name="authentifiedPersonne" />
		</span> <a href="TP13_Deconnection">Deconnection</a>
>>>>>>> ae45d89f03438f576d8e1a48acc76932d769685c
	</div>
</header>