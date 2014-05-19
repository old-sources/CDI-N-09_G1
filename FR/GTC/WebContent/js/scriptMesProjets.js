$(document).ready(function() {

	//affichage des elements proteges
	$('.onlyadmin').hide();		
	if ("${loguedPerson.role.roleId}" != 1){
		$('.onlyadmin').show();
	}

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
	
	//gestion affichage du formulaire d'invitation a un projet ou pour quitter un projet
	$('#formMesProjDivId').dialog({
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
	//formulaire d'invitation a un projet
	$('.actionFormulaireInvit').on('click', function(e) {
		// ouverture du formulaire avec l'id de la div
		$("#formMesProjDivId").dialog("option", "title", "Inviter à un projet");
		$("#formMesProjDivId").dialog("open");
		//donnees masquees
		$('#inputProjId').val($(this).attr("data-projId"));
		//donnees lecture seule
		$('#affProjNom').val($(this).attr("data-projNom"));
		$('#affProjNom').attr("readonly", "true");
		$('#affProjDescription').val($(this).attr("data-projDescription"));
		$('#affProjDescription').attr("readonly", "true");
		//gestion affichage des elements utiles pour cette vue
		$('.inviteProj').show();
		$('.quitProj').hide();
		
		//affichage des boutons du formulaire
		$('#inviteDansForm').attr("disabled", false);
		$('#quitDansForm').attr("disabled", true);
	});

	//donnees pour affichage a l'ouverture du formulaire
	//formulaire pour quitter un projet
	$('.actionFormulaireQuit').on('click', function(e) {
		// ouverture du formulaire avec l'id de la div
		$("#formMesProjDivId").dialog("option", "title", "Quitter le projet");
		$("#formMesProjDivId").dialog("open");
		//donnees masquees
		$('#inputProjId').val($(this).attr("data-projId"));
		$('#inputMembreId').val($(this).attr("data-membreId"));
		//donnees lecture seule
		$('#affProjNom').val($(this).attr("data-projNom"));
		$('#affProjNom').attr("readonly", "true");
		//gestion affichage des elements utiles pour cette vue
		$('.inviteProj').hide();
		$('.quitProj').show();
		//affichage des boutons du formulaire
		$('#inviteDansForm').attr("disabled", true);
		$('#quitDansForm').attr("disabled", false);
	});

});
