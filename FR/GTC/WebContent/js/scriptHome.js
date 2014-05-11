$(document).ready(function() {

	$('.onlyadmin').hide();
	if ("${loguedPerson.role.roleId}" != 1) {
		$('.onlyadmin').show();
	}
	//$('.actionFormulaire').button();

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

});
