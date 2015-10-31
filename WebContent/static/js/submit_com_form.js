function submeter_form_cadastro_pessoa_fisica(){
	var matchdata = new RegExp(/((0[1-9]|[12][0-9]|3[01])\/(0[13578]|1[02])\/[12][0-9]{3})|((0[1-9]|[12][0-9]|30)\/(0[469]|11)\/[12][0-9]{3})|((0[1-9]|1[0-9]|2[0-8])\/02\/[12][0-9]([02468][1235679]|[13579][01345789]))|((0[1-9]|[12][0-9])\/02\/[12][0-9]([02468][048]|[13579][26]))/gi);
	var data_nascim = $('form[id*="form_cadastrar_pessoa_fisica"] [id$="data_nascim"]').val();
	var cpf =  $('form[id*="form_cadastrar_pessoa_fisica"] [id$="cpf"]').val();
	var email = $('form[id*="form_cadastrar_pessoa_fisica"] [id$="email"]').val();
	var usuario = $('form[id*="form_cadastrar_pessoa_fisica"] [id$="username"]').val();
	var senha =  $('form[id*="form_cadastrar_pessoa_fisica"] [id$="senha"]').val();
	var nome =  $('form[id*="form_cadastrar_pessoa_fisica"] [id$="nome"]').val();
	var dadosemBranco =""
		
	if(nome == "" || nome == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="nome";
	}	
	if(data_nascim =="" || data_nascim == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="data nasc.";
	}
	if(cpf =="" || cpf == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="cpf";
	}
	if(email =="" || email == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="email";
	}	
	if(usuario =="" || usuario == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="usuário";
	}
	if(senha =="" ||senha == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="senha";
	}
	
	if(dadosemBranco !=""){
		$.bootstrapGrowl(dadosemBranco + " em branco!", {
            type:'danger',
            align:'center',
            width: 'auto',
            allow_dismiss: false
        });
		return false;
	}
	
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
	if(!verificarData(data_nascim)){
		$.bootstrapGrowl("Data de nascimento incorreta!", {
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
		
		$(function() {
	        $('#form_upload_imagem').ajaxForm({
	            success: function(msg) {
	                alert("File has been uploaded successfully");
	            },
	            error: function(msg) {
	                $("#upload-error").text("Couldn't upload file");
	            }
	        });
	    });
		
		/*
		$.ajax({
			url: 'upload_imagem',
//			url: 'cadastrar_pessoa_fisica',
			async: true,
			type: 'POST',
			dataType: 'json',
			data: $('#form_upload_imagem').serialize(),
			success: function(data){
				if(data.isValid) {
					
					var pessoaFisica = data.pessoaFisica;
										
					$.bootstrapGrowl("Pessoa física " + pessoaFisica.nome + " cadastrada com sucesso!", {
		                type:'success',
		                align:'center',
		                width: 'auto',
		                allow_dismiss: false
		            });
					$('#link_login_cadastro').text(pessoaFisica.nome);
					$('#link_login_cadastro').attr("href", "javascript:abre_modal_editar_cadastro();");
					
					//Alterar a Li com o nome da pessoa e opção de editar dados
					$('#link_login_cadastro').text(pessoaFisica.nome);
					// Altera o link para o modal de edição do cadastro
					$('#link_login_cadastro').attr("href", "javascript:abre_modal_editar_cadastro_pessoa_fisica();");
					// Adiciona o link para logout
					$('#li_login_cadastro').after('<li id="li_logout"><a id="link_logout" href="javascript:submeter_form_logout()">Sair :(</a></li>');
					// Fecha o modal de login
					$('#modal_login_cadastro').modal('hide');
					//Limpar as variaveis do cadastro de PF
					$('#form_cadastrar_pessoa_fisica')[0].reset();
				}
				else {
					if(data.usernameInvalido){
						$.bootstrapGrowl("UserName já utilizado!", {
				            type:'danger',
				            align:'center',
				            width: 'auto',
				            allow_dismiss: false
				        });
						return false;
					}else{
						$.bootstrapGrowl("Erro ao adicionar pessoa física!", {
				            type:'danger',
				            align:'center',
				            width: 'auto',
				            allow_dismiss: false
				        });
						return false;						
					}
				}
			}
		
		});
		return false;
		*/
	}

}
function submeter_form_cadastro_pessoa_juridica(){
		
	var endereco = $('form[id*="form_cadastrar_pessoa_juridica"] [id$="endereco"]').val();
	var cnpj =  $('form[id*="form_cadastrar_pessoa_juridica"] [id$="cnpj"]').val();
	var email = $('form[id*="form_cadastrar_pessoa_juridica"] [id$="email"]').val();
	var usuario = $('form[id*="form_cadastrar_pessoa_juridica"] [id$="username"]').val();
	var senha =  $('form[id*="form_cadastrar_pessoa_juridica"] [id$="senha"]').val();
	var nome =  $('form[id*="form_cadastrar_pessoa_juridica"] [id$="nome"]').val();
	var dadosemBranco =""
		
	if(nome == "" || nome == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="nome";
	}	
	if(cnpj =="" || cnpj == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="cnpj";
	}
	if(endereco =="" || endereco == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="endereço";
	}
	if(email =="" || email == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="email";
	}	
	if(usuario =="" || usuario == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="usuário";
	}
	if(senha =="" ||senha == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="senha";
	}
	
	
	if(dadosemBranco !=""){
		$.bootstrapGrowl(dadosemBranco + " em branco!", {
            type:'danger',
            align:'center',
            width: 'auto',
            allow_dismiss: false
        });
		return false;
	}
	
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
						
						//Alterar a Li com o nome da pessoa e opção de editar dados
						$('#link_login_cadastro').text(pessoaJuridica.nome);
						// Altera o link para o modal de edição do cadastro
						$('#link_login_cadastro').attr("href", "javascript:abre_modal_editar_cadastro_pessoa_juridica();");
						// Adiciona o link para logout
						$('#li_login_cadastro').after('<li id="li_logout"><a id="link_logout" href="javascript:submeter_form_logout()">Sair :(</a></li>');
						// Fecha o modal de login
						$('#modal_login_cadastro').modal('hide');
						//Limpar as variaveis do cadastro de PF
						$('#form_cadastrar_pessoa_juridica')[0].reset();

					}
					else {
						if(data.usernameInvalido){
							$.bootstrapGrowl("UserName já utilizado!", {
					            type:'danger',
					            align:'center',
					            width: 'auto',
					            allow_dismiss: false
					        });
							return false;
						}else{
							$.bootstrapGrowl("Erro ao adicionar pessoa jurídica!", {
					            type:'danger',
					            align:'center',
					            width: 'auto',
					            allow_dismiss: false
					        });
							return false;						
						}
					}
				}
			
			});
		return false;
/*		
	}
*/
	
}








function submeter_form_editar_cadastro_pessoa_fisica(){
	var matchdata = new RegExp(/((0[1-9]|[12][0-9]|3[01])\/(0[13578]|1[02])\/[12][0-9]{3})|((0[1-9]|[12][0-9]|30)\/(0[469]|11)\/[12][0-9]{3})|((0[1-9]|1[0-9]|2[0-8])\/02\/[12][0-9]([02468][1235679]|[13579][01345789]))|((0[1-9]|[12][0-9])\/02\/[12][0-9]([02468][048]|[13579][26]))/gi);
	var endereco = $('form[id*="form_editar_cadastro_pessoa_fisica"] [id$="endereco_editar_pf"]').val();
	var cnpj =  $('form[id*="form_editar_cadastro_pessoa_fisica"] [id$="cpf_editar_pf"]').val();
	var email = $('form[id*="form_editar_cadastro_pessoa_fisica"] [id$="email_editar_pf"]').val();
	var usuario = $('form[id*="form_editar_cadastro_pessoa_fisica"] [id$="username_editar_pf"]').val();
	var senha =  $('form[id*="form_editar_cadastro_pessoa_fisica"] [id$="senha_editar_pf"]').val();
	var nome =  $('form[id*="form_editar_cadastro_pessoa_fisica"] [id$="nome_editar_pf"]').val();
	var data =  $('form[id*="form_editar_cadastro_pessoa_fisica"] [id$="data_nascim_editar_pf"]').val();
	var dadosemBranco =""
		
	if(nome == "" || nome == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="nome";
	}
	if(data =="" || data == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="data";
	}
	if(cpf =="" || cpf == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="cpf";
	}
	if(endereco =="" || endereco == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="endereço";
	}
	if(email =="" || email == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="email";
	}	
	if(usuario =="" || usuario == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="usuário";
	}
	if(senha =="" ||senha == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="senha";
	}
	
	
	if(dadosemBranco !=""){
		$.bootstrapGrowl(dadosemBranco + " em branco!", {
            type:'danger',
            align:'center',
            width: 'auto',
            allow_dismiss: false
        });
		return false;
	}
	
	if(!ValidaCPF(cpf)){
		$.bootstrapGrowl("CPF inválido!", {
            type:'danger',
            align:'center',
            width: 'auto',
            allow_dismiss: false
        });
		return false;
	}
	
	if(!data.match(matchdata)) {
		$.bootstrapGrowl("Informe uma data correta, no formato 31/12/2015!", {
            type:'danger',
            align:'center',
            width: 'auto',
            allow_dismiss: false
        });
		return false;
	}
	if(!verificarData(data)){
		$.bootstrapGrowl("Data de nascimento incorreta!", {
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
		url: 'alterar_cadastro_pf',
		async: true,
		type: 'POST',
		dataType: 'json',
		data: $('#form_editar_cadastro_pessoa_fisica').serialize(),
		success: function(data){
			if(data.isValid) {
				
				var pessoaJuridica = data.pessoaJuridica;
				
				$.bootstrapGrowl("Alteração relizada com sucesso!", {
					type:'success',
					align:'center',
					width: 'auto',
					allow_dismiss: false
				});
				
			}
			else {
				if(data.usernameInvalido){
					$.bootstrapGrowl("UserName já utilizado!", {
			            type:'danger',
			            align:'center',
			            width: 'auto',
			            allow_dismiss: false
			        });
					return false;
				}else{
					$.bootstrapGrowl("Erro ao adicionar pessoa física!", {
			            type:'danger',
			            align:'center',
			            width: 'auto',
			            allow_dismiss: false
			        });
					return false;						
				}
			}
		}
	
	});
	
	return false;
	
}


function submeter_form_editar_cadastro_pessoa_juridico(){

	var endereco = $('form[id*="form_editar_cadastro_pessoa_juridica"] [id$="endereco_editar_pj"]').val();
	var cnpj =  $('form[id*="form_editar_cadastro_pessoa_juridica"] [id$="cnpj_editar_pj"]').val();
	var email = $('form[id*="form_editar_cadastro_pessoa_juridica"] [id$="email_editar_pj"]').val();
	var usuario =  $('form[id*="form_editar_cadastro_pessoa_juridica"] [id$="username_editar_pj"]').val()
	var senha =  $('form[id*="form_editar_cadastro_pessoa_juridica"] [id$="senha_editar_pj"]').val();
	var nome =   $('form[id*="form_editar_cadastro_pessoa_juridica"] [id$="nome_editar_pj"]').val();
	var dadosemBranco =""
		
	if(nome == "" || nome == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="nome";
	}	
	if(cnpj =="" || cnpj == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="cnpj";
	}
	if(endereco =="" || endereco == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="endereço";
	}
	if(email =="" || email == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="email";
	}	
	if(usuario =="" || usuario == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="usuário";
	}
	if(senha =="" ||senha == undefined){ 
		if(dadosemBranco != "") dadosemBranco +=",";
		dadosemBranco +="senha";
	}
	
	
	if(dadosemBranco !=""){
		$.bootstrapGrowl(dadosemBranco + " em branco!", {
            type:'danger',
            align:'center',
            width: 'auto',
            allow_dismiss: false
        });
		return false;
	}
	
	
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
			url: 'alterar_cadastro_pj',
			async: true,
			type: 'POST',
			dataType: 'json',
			data: $('#form_editar_cadastro_pessoa_juridica').serialize(),
			success: function(data){
				if(data.isValid) {
					var pessoaJuridica = data.pessoaJuridica;
									
					$.bootstrapGrowl("Alteração realizada com sucesso!", {
						type:'success',
						align:'center',
						width: 'auto',
						allow_dismiss: false
					});
					
				}
				else {
					if(data.usernameInvalido){
						$.bootstrapGrowl("UserName já utilizado!", {
				            type:'danger',
				            align:'center',
				            width: 'auto',
				            allow_dismiss: false
				        });
						return false;
					}else{
						$.bootstrapGrowl("Erro ao adicionar pessoa jurídica!", {
				            type:'danger',
				            align:'center',
				            width: 'auto',
				            allow_dismiss: false
				        });
						return false;						
					}
				}
			}		
		});
		return false;
}



function submeter_form_logout(){	
	$.ajax({
		url: 'logout',
		// async: true,
		type: 'POST',
		dataType: 'json',
		data: {'submit':true},
		success: function(data){
			if(data.isValid) {
				// Apaga os formulários de edição de PF e de PJ
				$('#form_editar_cadastro_pessoa_fisica')[0].reset();
				$('#form_editar_cadastro_pessoa_juridica')[0].reset();
				// Remove o link para logoff
				$('#link_logout').remove();
				//Alterar a Li para o padrão
				$('#link_login_cadastro').text("Login ou Cadastro");
				// Altera o link para o modal de login/cadastro
				$('#link_login_cadastro').attr("href", "javascript:abre_modal_login_cadastro();");
				
				$.bootstrapGrowl("Logoff efetuado com sucesso!", {
					type:'success',
					align:'center',
					width: 'auto',
					allow_dismiss: false
				});
			}
			else {
				$.bootstrapGrowl("Houve um erro ao fazer logoff!", {
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
	//Aterar o titulo do texto do botao
	$('form[id*="form_login"] button').text("Enviar");
	//Alterar a Li com Login e Cadastro				
	$('a[id$="link_login_cadastro"]').text("LOGIN OU CADASTRO");
	//Limpar as variaveis
	LimpaDadosLogin();
	//Simula click na div modal para fecha-la
	$('div[class="close-modal"]:eq(0)').click();
	//Após se deslogar aparecerá as li para cadastro pf e pj
	$('li[id*="li_PF"]').show();
	$('li[id*="li_PJ"]').show();
	//Habilita os campos para fazer novamente o login					
	$('form[id*="form_login"] [id$="name"]').prop('disabled', false);
	$('form[id*="form_login"] [id$="senha"]').prop('disabled', false);
	*/
}


function submeter_form_login(){
	var usuario = $('div[id*="modal_tab_login"] [id$="name"] ').val();
	var senha = $('div[id*="modal_tab_login"] [id$="senha"] ').val();
	
	if(usuario == "" || usuario == undefined ) {
		$.bootstrapGrowl("Informe o seu usuário!", {
			type:'danger',
			align:'center',
			width: 'auto',
			allow_dismiss: false
		});
		return false;
	}
	else if(senha == "" || senha == undefined){
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
					var pessoaFisica = data.pessoaFisica;
					var pessoaJuridica = data.pessoaJuridica;
					
					// se for pessoa fisica...
					if(pessoaFisica != null) {
						$.bootstrapGrowl("Bem vindo " + pessoaFisica.nome + "!", {
							type:'success',
							align:'center',
							width: 'auto',
							allow_dismiss: false
						});
						
						//Alterar a Li com o nome da pessoa e opção de editar dados
						$('#link_login_cadastro').text(pessoaFisica.nome);
						// Altera o link para o modal de edição do cadastro
						$('#link_login_cadastro').attr("href", "javascript:abre_modal_editar_cadastro_pessoa_fisica();");
						// Adiciona o link para logout
						$('#li_login_cadastro').after('<li id="li_logout"><a id="link_logout" href="javascript:submeter_form_logout()">Sair :(</a></li>');
						// Fecha o modal de login
						$('#modal_login_cadastro').modal('hide');
						
						//Limpar as variaveis do login
						$('#form_login')[0].reset();
						
						
						//preenche campos de edição de cadastro com os dados do usuário
						$('#nome_editar_pf').val(pessoaFisica.nome);
						$('#data_nascim_editar_pf').val((data.dtNascimento!= undefined) ? data.dtNascimento: "" );
						$('#cpf_editar_pf').val(pessoaFisica.cpf);
						$('#telefone_editar_pf').val(pessoaFisica.telefone);
						$('#email_editar_pf').val(pessoaFisica.email);
						$('#username_editar_pf').val(pessoaFisica.username);
						$('#senha_editar_pf').val(data.login.senha);
					}
					// senão, é pessoa juridica
					else {
						$.bootstrapGrowl("Bem vindo " + pessoaJuridica.nome + "!", {
							type:'success',
							align:'center',
							width: 'auto',
							allow_dismiss: false
						});
						
						//Alterar a Li com o nome da pessoa e opção de editar dados
						$('#link_login_cadastro').text(pessoaJuridica.nome);
						// Altera o link para o modal de edição do cadastro
						$('#link_login_cadastro').attr("href", "javascript:abre_modal_editar_cadastro_pessoa_juridica();");
						// Adiciona o link para logout
						$('#li_login_cadastro').after('<li id="li_logout"><a id="link_logout" href="javascript:submeter_form_logout()">Sair :(</a></li>');
						// Fecha o modal de login
						$('#modal_login_cadastro').modal('hide');
						
						//Limpar as variaveis do login
						$('#form_login')[0].reset();
						
						$('#nome_editar_pj').val(pessoaJuridica.nome);
						$('#cnpj_editar_pj').val(pessoaJuridica.cnpj);
						$('#telefone_editar_pj').val(pessoaJuridica.telefone);
						$('#email_editar_pj').val(pessoaJuridica.email);
						$('#endereco_editar_pj').val(pessoaJuridica.endereco);
						$('#username_editar_pj').val(pessoaJuridica.username);
						$('#senha_editar_pj').val(data.login.senha);
					}
					
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

function abre_modal_login_cadastro() {
	$('#modal_login_cadastro').modal('show');
}

function abre_modal_editar_cadastro_pessoa_juridica() {
	$('#modal_editar_cadastro_pessoa_juridica').modal('show');
}

function abre_modal_editar_cadastro_pessoa_fisica() {
	$('#modal_editar_cadastro_pessoa_fisica').modal('show');
}

function readURL(input) {
    if (input != "" && input.files[0]) {
        var reader = new FileReader();
       
        reader.onload = function (e) {
        $("#div_imagem_upload").attr('src', e.target.result)
    };
    reader.readAsDataURL(input.files[0]);
    }
} 

function verificaMostraBotao(){
	$('input[type=file]').each(function(index){
        if ($('input[type=file]').eq(index).val() != ""){

        	//alert($('#caminho_imagem_upload').val());
            readURL(this);
            //$('.hide').show();
        }
	});
}

$('#caminho_imagem_upload').on("change", function(){
  verificaMostraBotao();
});

$('#caminho_imagem_upload').on("click", function(){
    $('#caminho_imagem_upload').change(verificaMostraBotao);
	//verificaMostraBotao();
    //$("#container_img").append($('<img />'));
});



