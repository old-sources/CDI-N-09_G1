<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet type="text/css" href="./TP12.css">
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