$(document).ready(function() {

	$('#tableProjet').dataTable({
		"bJQueryUI" : true
	}).yadcf([ {
		column_number : 0
	}, {
		column_number : 3,
		filter_type : "auto_complete",
		text_data_delimiter : ","
	}, {
		column_number : 5,
		filter_type : "auto_complete",
		text_data_delimiter : ","
	} ]);

	//gestion affichage du formulaire pour postuler pour un projet
	$('#formProjDivId').dialog({
		autoOpen : false,
		show : {
			effect : "blind",
			duration : 1000
		},
		hide : {
			effect : "blind",
			duration : 1000
		}
	});

	//donnees pour affichage a l'ouverture du formulaire
	//formulaire pour postuler a un projet
	$('.actionFormulairePostuler').on('click', function(e) {
		// ouverture du formulaire avec l'id de la div
		$("#formProjDivId").dialog("option", "title", "Postuler pour un projet");
		$("#formProjDivId").dialog("open");
		//donnees masquees
		$('#inputProjId').val($(this).attr("data-projId"));
		$('#inputMembreId').val($(this).attr("data-membreId"));
		//donnees lecture seule
		$('#affProjNom').val($(this).attr("data-projNom"));
		$('#affProjNom').attr("readonly", "true");
		$('#affProjDescription').val($(this).attr("data-projDescription"));
		$('#affProjDescription').attr("readonly", "true");
	});

});
