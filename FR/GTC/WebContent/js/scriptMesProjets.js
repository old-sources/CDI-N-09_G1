$(document).ready(function() {

	//masquer si liste vide
	var list = "${projetsCdp}";
	if (list ==  '[]') {
		$('#listeProjets1').hide();
	};

	//masquer si liste vide
	var list = "${projetsUser}";
	if (list ==  '[]') {
		$('#listeProjets2').hide();
	};

	$('.tableProjet').dataTable({
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
	
	//gestion affichage du formulaire d'invitation a un projet
	$('#formInvitDivId').dialog({
		autoOpen : false,
		show : {
			effect : "blind",
			duration : 1000
		},
		hide : {
			effect : "blind",
			duration : 1000
		},
		
	});

	//donnees pour affichage a l'ouverture du formulaire
	//formulaire d'invitation a un projet
	$('.actionFormulaireInvit').on('click', function(e) {
		// ouverture du formulaire avec l'id de la div
		$("#formInvitDivId").dialog("open");
		//donnees masquees
		$('#inputProjId').val($(this).attr("data-projId"));
		//donnees lecture seule
		$('#affProjNom').val($(this).attr("data-projNom"));
		$('#affProjNom').attr("readonly", "true");
		$('#affProjDescription').val($(this).attr("data-projDescription"));
		$('#affProjDescription').attr("readonly", "true");
		
		//affichage des boutons du formulaire
		$('#inviteDansForm').show();
		$('#quitDansForm').hide();
//		$('#updateDansForm').show();
//		$('#deleteDansForm').show();
//		$('#updateDansForm').attr("disabled", false);
//		$('#deleteDansForm').attr("disabled", false);
	});

	//donnees pour affichage a l'ouverture du formulaire
	//formulaire pour quitter un projet
//	$('.actionFormulaireQuit').on('click', function(e) {
//		// ouverture du formulaire avec l'id de la div
//		$("#formQuitDivId").dialog("open");
//		//donnees masquees
//		$('#inputProjId').val($(this).attr("data-projId"));
//		$('#inputMembreId').val($(this).attr("data-membreId"));
//		//donnees lecture seule
//		$('#affProjNom').val($(this).attr("data-projNom"));
//		$('#affProjNom').attr("readonly", "true");
//		
//		//affichage des boutons du formulaire
//		$('#quitDansForm').show();
//	});

});
