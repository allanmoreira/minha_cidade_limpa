function submeter_form_cadastro_pessoa_fisica(){
	var matchdata = new RegExp(/((0[1-9]|[12][0-9]|3[01])\/(0[13578]|1[02])\/[12][0-9]{3})|((0[1-9]|[12][0-9]|30)\/(0[469]|11)\/[12][0-9]{3})|((0[1-9]|1[0-9]|2[0-8])\/02\/[12][0-9]([02468][1235679]|[13579][01345789]))|((0[1-9]|[12][0-9])\/02\/[12][0-9]([02468][048]|[13579][26]))/gi);
	var data_nascim = $('#data_nascim').val();
	var cpf = $('#cpf').val();
	
	
	if(!ValidaCPF(cpf)){
		$.bootstrapGrowl("CPF inválido!", {
            type:'danger',
            align:'center',
            width: 'auto',
            allow_dismiss: false
        });
		return false;
	}
	
	if(!data_nascim.match(matchdata)) {
		$.bootstrapGrowl("Informe uma data correta, no formato 31/12/2015!", {
            type:'danger',
            align:'center',
            width: 'auto',
            allow_dismiss: false
        });
		return false;
	}
	else {
		
		$.bootstrapGrowl("Enviando os dados, aguarde...", {
			type:'info',
			align:'center',
			width: 'auto',
			allow_dismiss: false
		});
		
		$.ajax({
			url: 'cadastrar_pessoa_fisica',
			async: true,
			type: 'POST',
			dataType: 'json',
			data: $('#form_cadastrar_pessoa_fisica').serialize(),
			success: function(data){
				if(data.isValid) {
					
					var pessoaFisica = data.pessoaFisica;
										
					$.bootstrapGrowl("Pessoa física " + pessoaFisica.nome + " cadastrada com sucesso!", {
		                type:'success',
		                align:'center',
		                width: 'auto',
		                allow_dismiss: false
		            });
					
					LimpaDadosPessoaFisica();
					// $('#link_login_cadastro').
											
				}
				else {
					$.bootstrapGrowl("Erro ao adicionar pessoa física!", {
			            type:'danger',
			            align:'center',
			            width: 'auto',
			            allow_dismiss: false
			        });
				}
			}
		
		});
		return false;
	}

}
function submeter_form_cadastro_pessoa_juridica(){
	/*
	var matchdata = new RegExp(/((0[1-9]|[12][0-9]|3[01])\/(0[13578]|1[02])\/[12][0-9]{3})|((0[1-9]|[12][0-9]|30)\/(0[469]|11)\/[12][0-9]{3})|((0[1-9]|1[0-9]|2[0-8])\/02\/[12][0-9]([02468][1235679]|[13579][01345789]))|((0[1-9]|[12][0-9])\/02\/[12][0-9]([02468][048]|[13579][26]))/gi);
	var data_nascim = $('#data_nascim').val();
	
	if(!data_nascim.match(matchdata)) {
		$.bootstrapGrowl("Informe uma data correta, no formato 31/12/2015!", {
			type:'danger',
			align:'center',
			width: 'auto',
			allow_dismiss: false
		});
		
	}
	else {
		*/
	
	
	var cnpj =  $('#cnpj').val();
	if(!validarCNPJ(cnpj)){
		$.bootstrapGrowl("CNPJ inválido!", {
			type:'danger',
			align:'center',
			width: 'auto',
			allow_dismiss: false
		});
		
		return false;
	}

		$.bootstrapGrowl("Enviando os dados, aguarde...", {
			type:'info',
			align:'center',
			width: 'auto',
			allow_dismiss: false
		});
		
		$.ajax({
			url: 'cadastrar_pessoa_juridica',
			async: true,
			type: 'POST',
			dataType: 'json',
			data: $('#form_cadastrar_pessoa_juridica').serialize(),
			success: function(data){
				if(data.isValid) {
					
					var pessoaJuridica = data.pessoaJuridica;
					
					
					$.bootstrapGrowl("Pessoa física " + pessoaJuridica.nome + " cadastrada com sucesso!", {
						type:'success',
						align:'center',
						width: 'auto',
						allow_dismiss: false
					});
					LimpaDadosPessoaJuridica();
					// $('#link_login_cadastro').
					
				}
				else {
					$.bootstrapGrowl("Erro ao adicionar pessoa jurídica!", {
			            type:'danger',
			            align:'center',
			            width: 'auto',
			            allow_dismiss: false
			        });
				}
			}
		
		});
		return false;
/*		
	}
*/
	
}

function submeter_form_login(){
	var usuario = $('#username').val();
	var senha = $('#senha').val();
	
	if(usuario == null ) {
		$.bootstrapGrowl("Informe o seu usuário!", {
			type:'danger',
			align:'center',
			width: 'auto',
			allow_dismiss: false
		});
		return false;
	}
	else if(senha == null){
		$.bootstrapGrowl("Informe a sua senha!", {
			type:'danger',
			align:'center',
			width: 'auto',
			allow_dismiss: false
		});
		return false;
	}
	else {

		$.bootstrapGrowl("Enviando os dados, aguarde...", {
			type:'info',
			align:'center',
			width: 'auto',
			allow_dismiss: false
		});
		
		$.ajax({
			url: 'login',
			async: true,
			type: 'POST',
			dataType: 'json',
			data: $('#form_login').serialize(),
			success: function(data){
				if(data.isValid) {
					
					$.bootstrapGrowl("Bem vindo " + data.nomeUsuario + "!", {
						type:'success',
						align:'center',
						width: 'auto',
						allow_dismiss: false
					});
					
					$('a[id$="link_login_cadastro"]').text(data.NomeUsuario + " (Exit) :(");
					LimpaDadosLogin();
					$('div[class="close-modal"]:eq(0)').click();
				}
				else {
					if(data.dadosCadastroInvalidos = true) {
						$.bootstrapGrowl("Usuário ou senha não informados ou inválidos!", {
							type:'danger',
							align:'center',
							width: 'auto',
							allow_dismiss: false
						});
					}
				}
			}
		
		});
		return false;
	}
	
}

function abre_tab_PF() {
	LimpaDadosPessoaFisica();
	  $('#li_login').removeClass('active');
	  $('#li_PJ').removeClass('active');
	  $('#li_PF').addClass('active');
		
	  $('#modal_tab_login').removeClass('active');
	  $('#modal_tab_cadastro_pj').removeClass('active');
	  $('#modal_tab_cadastro_pf').addClass('active');
}

function abre_tab_PJ() {
	LimpaDadosPessoaJuridica();
	  $('#li_login').removeClass('active');
	  $('#li_PF').removeClass('active');
	  $('#li_PJ').addClass('active');
		
	  $('#modal_tab_login').removeClass('active');
	  $('#modal_tab_cadastro_pf').removeClass('active');
	  $('#modal_tab_cadastro_pj').addClass('active');
}

function abre_tab_login() {
	  LimpaDadosPessoaFisica();
	  LimpaDadosPessoaJuridica();
	  $('#li_PJ').removeClass('active');
	  $('#li_PF').removeClass('active');
	  $('#li_login').addClass('active');
	
	  $('#modal_tab_cadastro_pj').removeClass('active');
	  $('#modal_tab_cadastro_pf').removeClass('active');
	  $('#modal_tab_login').addClass('active');
}
