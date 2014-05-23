<ul>
	<!-- <p>-------</p> -->
	<!-- listcompchild = 1 liste d'enfants  -->
	<%-- <c:forEach items="${listcompchild}" var="node2"> --%>
	<p>entree fichier</p>
	
	<c:out value="${compchild3.compIntitule}" />
	
	
	<li><a> <c:forEach items="${compchild3.competences}" var="node2">
				
				
	 		<ul>
				<li><a>
				<c:out value="${node2.compIntitule}" />
				<p>enfant3</p>
				</a></li>
				</ul>

			</c:forEach>
	</a></li>
	<p>sortie fichier</p>
	<!-- <p>-------</p> -->
</ul>
<!-- http://stackoverflow.com/questions/4638653/displaying-tree-on-jsp-page -->