function submit_com_form (){
	var matchdata = new RegExp(/((0[1-9]|[12][0-9]|3[01])\/(0[13578]|1[02])\/[12][0-9]{3})|((0[1-9]|[12][0-9]|30)\/(0[469]|11)\/[12][0-9]{3})|((0[1-9]|1[0-9]|2[0-8])\/02\/[12][0-9]([02468][1235679]|[13579][01345789]))|((0[1-9]|[12][0-9])\/02\/[12][0-9]([02468][048]|[13579][26]))/gi);
	var data_nascim = $('#data_nascim').val();
	
	if(!data_nascim.match(matchdata)) {
		alert("Informe uma data correta, no formato 31/12/2015!");
	}
	else {
		$.ajax({
			url: 'cadastrar_pessoa_fisica',
			async: true,
			type: 'POST',
			dataType: 'json',
			data: $('#form_cadastrar_pessoa_fisica').serialize(),
			success: function(data){
				if(data.isValid) {
					
					var pessoaFisica = data.pessoaFisica;
					
					alert("Pessoa física " + pessoaFisica.nome + " cadastrada com sucesso!")
											
				}
				else {
					alert("Erro ao adicionar pessoa física")
				}
			}
		
		});
		return false;
	}

}
