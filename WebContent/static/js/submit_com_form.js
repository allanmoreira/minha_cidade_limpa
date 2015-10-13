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
					//Simula click na div modal para fecha-la
					$('div[class="close-modal"]:eq(0)').click();
											
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
					LimpaDadosPessoaJuridica();
					//Simula click na div modal para fecha-la
					$('div[class="close-modal"]:eq(0)').click();

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

function submeter_form_login(){
	var textoBotao = $('form[id*="form_login"] button').text();
	if(textoBotao.indexOf("Env") > -1){
		LogarUsuario();
	}
	else{
		DeslogarUsuario();
	}
}

function DeslogarUsuario(){
	
	//Implementar o método que faz a chamada do ajax para deslogar
	
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

}

function LogarUsuario(){
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
					
					//Limpar as variaveis
					LimpaDadosLogin();
					//Aterar o titulo do texto do botao
					$('form[id*="form_login"] button').text("Sair");
					//Alterar a Li com o nome da pessoa e indicativo de Exit				
					$('a[id$="link_login_cadastro"]').text(data.nomeUsuario + " (Exit) :(");
					//Simula click na div modal para fecha-la
					$('div[class="close-modal"]:eq(0)').click();
					//Após se deslogar aparecerá as li para cadastro pf e pj
					$('li[id*="li_PJ"]').hide();
					//Bloqueio os campos para logar com outro usuario
					$('form[id*="form_login"] [id$="name"]').prop('disabled', true);
					$('form[id*="form_login"] [id$="senha"]').prop('disabled', true);
					
					if(data.TipoUsuario){
						$('li[id*="li_PF"] a').text("Alteraçao de dados pessoa física");
						//Aterar o titulo do texto do botao
						$('form[id*="form_cadastrar_pessoa_fisica"] button').text("Alterar");
						$('li[id*="li_PF"]').hide();
						
						$('form[id*="form_cadastrar_pessoa_fisica"] [id$="telefone"]').val(data.stelefone);
						$('form[id*="form_cadastrar_pessoa_fisica"] [id$="data_nascim"]').val(data.sdatanascimento);
						$('form[id*="form_cadastrar_pessoa_fisica"] [id$="cpf"]').val(data.scpf);
						$('form[id*="form_cadastrar_pessoa_fisica"] [id$="email"]').val(data.semail);
						$('form[id*="form_cadastrar_pessoa_fisica"] [id$="username"]').val(data.susername);
						$('form[id*="form_cadastrar_pessoa_fisica"] [id$="senha"]').val(data.ssenha);
						$('form[id*="form_cadastrar_pessoa_fisica"] [id$="nome"]').val(data.snomeUsuario);					
						
					}else{
						
						
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
