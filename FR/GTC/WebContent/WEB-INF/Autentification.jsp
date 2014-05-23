<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet type="text/css" href="./TP12.css">
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-51009350-1', 'integration-teamskills.rhcloud.com');
  ga('send', 'pageview');

</script>

</head>
<body>
	<p>
		<img src="./images/bandeauTeamskills.png" alt="Bandeau
			Teamskills (n'arrive pas a s'afficher)" />
	</p>
	<form method="POST">
		<div class="form">

			<div class="row">

				<div class="cell">

					<label for="">Login</label>
				</div>
				<div class="cell">

					<input type="text" name= "inputLogin" />
				</div>

			</div>
			<div class="row">

				<div class="cell">
					<label for="">password</label>
				</div>
				<div class="cell">
					<input type= "password" name="inputPassword">
				</div>

			</div>

		</div>
		<input type="submit" name="valider" value="Valider "/>
	</form>
	<%
		String message = (String) request.getAttribute("messageException");
		if (message != null) {
	%><span>message : credentials incorrects</span>
	<%
		}
	%>

<span>
<p>
<br>
compte super admin : admin / imie
<br>
compte admin : huury / mdp2
<br>
compte user : mateo / mdp0
</p>

</span>
</body>
</html>