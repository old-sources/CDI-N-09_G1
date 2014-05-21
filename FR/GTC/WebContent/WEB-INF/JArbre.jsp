<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="java.util.Date"%>

<!-- Onglet de Gestion des compétences -->
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr" dir="ltr">

<head>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<base href="/GTC/" />

<script type="text/javascript" src="js/jquery-1.10.2.js"></script> <!-- Les sources de la bibliothèque JQuery en local-->
<!--  <script src="http://code.jquery.com/jquery-1.6.2.min.js"></script> Les sources de la bibliothèque JQuery distantes -->

<script type="text/javascript" src="js/jquery-ui-1.10.4.custom.js"></script>

<script type="text/javascript" charset="utf8"
	src="js/jquery.dataTables.min.js"></script>

<script type="text/javascript" charset="utf8"
	src="js/jquery.dataTables.yadcf.js"></script>

<link href="css/south-street/jquery-ui-1.10.4.custom.css"
	rel="stylesheet" type="text/css" />

<link rel=stylesheet type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css"
	href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css">
<link rel=stylesheet type="text/css"
	href="css/jquery.dataTables.yadcf.css">
<link href='http://fonts.googleapis.com/css?family=Capriola' rel='stylesheet' type='text/css'>
<link href='css/arbre.css' rel='stylesheet' type='text/css'>

<title>Gestion de Compétences</title>

<script src="js/scripComp.js"></script> <!-- La source qui contient le code d'envoi en Ajax -->

<script type="text/javascript">

$(document).ready(function(){
        $('.tree li').each(function(){
                if($(this).children('ul').length > 0){
                        $(this).addClass('parent');     
                }
        });
        
        $('.tree li.parent > a').click(function(){
                $(this).parent().toggleClass('active');
                $(this).parent().children('ul').slideToggle('fast');
        });
});
        
</script>

</head>

<body>
	<%@ include file="/WEB-INF/header.jsp"%>

<div id="wrapper">
<h1>JQuery Tree List Demo</h1>

<div class="tree">
<c:forEach items="${foundCompetences}" var="comp">
<ul><li><a><c:out value="${comp.compIntitule}" /></a></li>
<c:forEach items="${comp.competences}" var="compchild">
								<li><a><c:out value="+ ${compchild.compIntitule}" /></a></li>
								
</c:forEach>
</ul>
</c:forEach>
</div>


<div class="tree">
        <ul>
                <li><a>First Level</a>
                        <ul>
                                <li><a>Second Level</a></li>
                                <li><a>Second Level</a></li>
                                <li><a>Second Level</a></li>
                        </ul>
                </li>
                <li><a>First Level</a>
                        <ul>
                                <li><a>Second Level</a>
                                        <ul>
                                                <li><a>Third Level</a></li>
                                                <li><a>Third Level</a></li>
                                                <li><a>Third Level</a>
													<ul>
															<li><a>Fourth Level</a></li>
															<li><a>Fourth Level</a></li>
															<li><a>Fourth Level</a>
																<ul>
																		<li><a>Fifth Level</a></li>
																		<li><a>Fifth Level</a></li>
																		<li><a>Fifth Level</a></li>
																</ul>
															</li>
													</ul>
												</li>
                                        </ul>
                                </li>
                                <li><a>Second Level</a></li>
                        </ul>
                </li>
                <li><a>First Level</a>
                        <ul>
                                <li><a>Second Level</a></li>
                                <li><a>Second Level</a></li>
                        </ul>
                </li>
        </ul>
</div>
</div>

</body>
</html>