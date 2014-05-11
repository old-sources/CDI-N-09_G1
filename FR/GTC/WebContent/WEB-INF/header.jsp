
<%@page import="model.Personne"%>
<jsp:useBean id="authentifiedPersonne" class="model.Personne"
	scope="session"></jsp:useBean>
<header>
	<div id="BarreId">

		<span> <jsp:getProperty property="nom"
				name="authentifiedPersonne" /> - <jsp:getProperty property="prenom"
				name="authentifiedPersonne" />
		</span> <a href="TP13_Deconnection">Deconnection</a>
	</div>
	<div id="BarreMenu">
		<a href="/GTC/Home">Accueil</a>
		<a href="/GTC/MesProjets">Mes Projets</a> <span> </span> 
		<span> </span> <a href="/GTC/Profil">Mon Profil</a> <span> </span> 
		<a href="/GTC/Competence">Compétences</a>
			<span> </span> <a href="/GTC/Admin" class="onlyadmin">Admin</a>
	</div>
</header>