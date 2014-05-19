$(document).ready(function() {

	//affichage des elements proteges
	$('.onlyadmin').hide();		
	if (_loggedUserRoleId != 1){
		$('.onlyadmin').show();
	}

	$('#formulaire').dialog({
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

	$('.actionRetourPageAdmin').on('click', function(e) {
		document.location.href = "/GTC/Admin";
	});

	$('#tableProjetAdmin').dataTable({
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

	$('.actionFormulaire').on('click', function(e) {
		$("#formulaire").dialog("open");
		$('#inputCdpId').val($(this).attr("data-chefDeProjetId"));
		$('#inputProjId').val($(this).attr("data-projId"));
		$('#inputProjNom').val($(this).attr("data-projNom"));
		$('#inputprojDescription').val($(this).attr("data-projDescription"));
		$('#inputProjWikiCdp').val($(this).attr("data-projWikiCdp"));
		$('#inputprojWikiMembre').val($(this).attr("data-projWikiMembre"));
		$('#inputprojAvancement').val($(this).attr("data-projAvancement"));

		var dateString1 = new Date($(this).attr("data-projDatedebut"));
		$('#inputProjDatedebut').datepicker({
			defaultDate : dateString1
		});
		$('#inputProjDatedebut').val(dateString1.toLocaleDateString("fr-FR"));
		var dateString2 = new Date($(this).attr("data-projDatedefin"));
		$('#inputProjDatedefin').datepicker({
			defaultDate : dateString2
		});
		$('#inputProjDatedefin').val(dateString2.toLocaleDateString("fr-FR"));
	
		

		$('#updateDansForm').show();
		$('#deleteDansForm').show();
		$('#creerDansForm').hide();
	});

	$('.actionPagePrincipaleCreer').on('click', function(e) {
		$("#formulaire").dialog("open");
		$('#inputProjNom').val("");
		$('#inputprojDescription').val("");
		$('#inputProjWikiCdp').val("");
		$('#inputprojWikiMembre').val("");
		$('#inputprojAvancement').val("");
		$('#inputCdpId').val("");
		
		$('#inputProjDatedebut').datepicker({
			defaultDate : new Date()
		});
		$('#inputProjDatedebut').val("01/08/2014");
		$('#c').datepicker({
			defaultDate : new Date()
		});
		$('#inputProjDatedefin').val("01/08/2014");
		
		$('#deleteDansForm').hide();
		$('#updateDansForm').hide();
		$('#creerDansForm').show();
	});

});
