$(document).ready(function() {

	$('.onlyadmin').hide();
	if ("${loguedPerson.role.roleId}" != 1) {
		$('.onlyadmin').show();
	};

// 		var list = "${projetsCdp}";
// 		if (list[0] == null) {
// 			$('#listeProjets1').hide();
// 		};


	var list = "${projetsCdp}";
	if (list ==  '[]') {
		$('#listeProjets1').hide();
	};

	var list = "${projetsUser}";
	if (list ==  '[]') {
		$('#listeProjets2').hide();
	};

	var dateString = new Date("${loguedPerson.dateNaiss}");
	$('#inputDateNaiss').datepicker({
		defaultDate : dateString
	});
	$('#inputDateNaiss').val(dateString.toLocaleDateString("fr-FR"));

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

	$('.actionFormulaire').on('click', function(e) {
		$("#formulaire").dialog("open");
		$('#inputCdpId').val($(this).attr("data-personneid"));
		$('#inputProjId').val($(this).attr("data-projId"));
		$('#inputProjNom').val($(this).attr("data-projNom"));
		$('#inputprojDescription').val($(this).attr("data-projDescription"));
		$('#inputProjWikiCdp').val($(this).attr("data-projWikiCdp"));
		$('#inputprojWikiMembre').val($(this).attr("data-projWikiMembre"));
		$('#inputprojAvancement').val($(this).attr("data-projAvancement"));
		$('#inputPersonne').val($(this).attr("data-personneid"));

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

});
