$(document).ready(
	function() {
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
	
		$('.actionFormulaire').on('click', function(e) {
				$("#formulaire").dialog("open");
				$('#inputId').val(
						$(this).attr("data-id"));
				$('#inputRoleId').val($(this).attr("data-roleid"));
				$('#inputNom').val($(this).attr("data-nom"));
				$('#inputPrenom').val($(this).attr("data-prenom"));
				var dateString = new Date($(this)
						.attr("data-dateNaiss"));
				$('#inputDateNaiss').datepicker({
					defaultDate : dateString
				});
				$('#inputDateNaiss').val(dateString.toLocaleDateString("fr-FR"));
				$('#inputPromotion').val($(this).attr("data-promotionid"));
				$('#inputPassw').attr(
						"disabled", false);
				$('#inputPassw').val($(this).attr("data-passw"));
				
				$('#inputEmail').val($(this).attr("data-email"));
				$('#inputInfos').val($(this).attr("data-infos"));
				$('.loginExiste').hide();
				if (($(this).attr("data-disponibilite") == "true")|| ($(this).attr("data-disponibilite") == "TRUE")) {
					document.getElementById('inputDisponibilite').checked = true;
				} else {
					document.getElementById('inputDisponibilite').checked = false;
				};
				$('#inputRole').val($(this).attr("data-roleId"));
				$('#inputLogin').val($(this).attr("data-identConnexion"));
				$('#inputLogin').attr("disabled", "disabled");
				$('#inputIdentConnexion').val($(this).attr("data-identConnexion"));
				$('#inputCgu').val($(this).attr("data-cgu"));
				console.log("data-cgu vaut :"+$(this).attr("data-cgu"));
				$('#updateDansForm').show();
				$('#deleteDansForm').show();
				$('#creerDansForm').hide();
				$('#updateDansForm').attr("disabled", false);
				$('#deleteDansForm').attr("disabled", false);
		});
	
		$('.actionPagePrincipaleCreer').on('click',function(e) {
				$("#formulaire").dialog("open");
				$('#inputNom').val("");
				$('#inputPrenom').val("");
				$('#inputLogin').val("");
				$('#inputLogin').attr(
						"disabled", false);
				$('#inputDateNaiss').datepicker({
					defaultDate : new Date()
				});
				$('#inputDateNaiss').val("01/01/1980");
				$('#inputPromotion').val("");
				$('#inputEmail').val("");
				document.getElementById('inputDisponibilite').checked = true;
				$('.loginExiste').hide();
				$('#inputRole').val("");
				$('#inputInfos').val("");
				$('#inputPassw').attr(
						"disabled", "disabled");
				$('#inputPassw').val("p@ssword");
				$('#inputRole').val("1");
				$('#inputCgu').val("false");
				$('#deleteDansForm').hide();
				$('#deleteDansForm').attr(
						"disabled", "disabled");
				$('#updateDansForm').hide();
				$('#updateDansForm').attr(
						"disabled", "disabled");
				$('#creerDansForm').show();
		});
	
		$('.actionRetourPageAdmin').on('click', function(e) {
			document.location.href = "/GTC/Admin";
		});
	
		$('#tablePersonne').dataTable({
			"bJQueryUI" : true
		}).yadcf([]);
	
		$('#lgdble').val("${loginDouble}");
	
	}
);


