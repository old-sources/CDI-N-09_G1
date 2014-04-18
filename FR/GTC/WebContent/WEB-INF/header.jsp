
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
		<span> </span> <a href="/GTC/Profil">profil</a> <span> </span> 
		<a href="/GTC/MesProjets">Mes Projets et Mes Compétences</a> <span> </span> 
		<a href="/GTC/Home">Projets</a>
		<a href="/GTC/Competence">Compétences</a>
<!-- 		<div class="onlyadmin"> -->
			<span> </span> <a href="/GTC/Admin" class="onlyadmin">admin</a>
<!-- 		</div> -->
	</div>
</header>