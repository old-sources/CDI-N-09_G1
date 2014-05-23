//<SCRIPT type="text/javascript">
	$(document).ready(function() {

		$('#tableComp').dataTable({
			"bJQueryUI" : true
		}).yadcf([
		// {column_number : 0},{
		//	{column_number : 3,filter_type : "auto_complete",text_data_delimiter : ","}, 
		//  {column_number : 5, filter_type : "auto_complete", text_data_delimiter : ","} 
		]);

		$('.onlyadmin').hide();
		if ("${loguedPerson.role.roleId}" != 1) {
			$('.onlyadmin').show();
		}

		// ouverture du formulaire avec l'id de la div
		$('#formCompDivId').dialog({
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

		// ouverture du formulaire avec l'id de la div
		$('#formMoveComDivId').dialog({
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

		$('.actionFormulaireComp').button();
		$('.actionFormulaireComp').on('click', function(e) {
			// ouverture du formulaire avec l'id de la div
			$("#formCompDivId").dialog("open");
			$('#inputId').val($(this).attr("data-compId"));
			$('#inputLibelleComp').val($(this).attr("data-compIntitule"));
			$('#inputLibelleParent').hide(); // obligatoire ?
			$('#updateDansForm').show();
			$('#deleteDansForm').hide();
			$('#creerDansForm').hide();
		});

		$('.actionCreerCompetence').button();
		$('.actionCreerCompetence').on('click', function(e) {
			// ouverture du formulaire avec l'id de la div
			$("#formCompDivId").dialog("open");
			//$('#inputId').val('');
			$('#inputLibelleComp').val('');
			$('#inputLibelleParent').hide(); // obligatoire ?
			$('#updateDansForm').hide();
			$('#deleteDansForm').hide(); // ??
			$('#creerDansForm').show();
			//$('#moveDansForm').hide();
		});

		$('.actionDeleteComp').button();
		$('.actionDeleteComp').on('click', function(e) {
			// ouverture du formulaire avec l'id de la div
			$("#formCompDivId").dialog("open");
			$('#inputLibelleComp').val($(this).attr("data-compIntitule"));
			$('#inputId').val($(this).attr('data-compId'));
			$('#inputLibelleParent').hide();
			$('#updateDansForm').hide();
			$('#deleteDansForm').show(); // ??
			$('#creerDansForm').hide();
			//$('#moveDansForm').hide();
		});

		$('.actionMoveComp').button();
		$('.actionMoveComp').on('click', function(e) {
			// ouverture du formulaire avec l'id de la div
			$("#formMoveComDivId").dialog("open");
			$('#inputId2').val($(this).attr('data-compId'));
			$('#inputLibelleParent').val($(this).attr('data-compParent'));
			$('#moveDansForm').show();
			$('#inputLibelleParent').show();
		});

		$('.actionRetourPageHome').on('click', function(e) {
			document.location.href = "/GTC/Home";
		});

	});
//</SCRIPT>