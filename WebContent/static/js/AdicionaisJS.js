
function verificarData(vdata) {
    var inicio = vdata;
    var fim = DataHoje();
    if (inicio.length != 10 || fim.length != 10) return;

    if (gerarData(fim) <= gerarData(inicio)) {
    	return false;
    }
    return true;
}


function gerarData(str) {
    var partes = str.split("/");
    return new Date(partes[2], partes[1] - 1, partes[0]);
}

function DataHoje(){
	var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!

    var yyyy = today.getFullYear();
    if(dd<10){
        dd='0'+dd
    } 
    if(mm<10){
        mm='0'+mm
    } 
    var today = dd+'/'+mm+'/'+yyyy;
    return today;
}



function formatar(mascara, documento){
	var ehNumero = verificaNumero(documento.value.replace("-","").replace("/","").replace(".","").replace("-","").replace("/","").replace(".","").replace(".","").replace(".",""));
	if(ehNumero){
		var i = documento.value.length;
	    var saida = mascara.substring(0,1);
	    var texto = mascara.substring(i)

	    if (texto.substring(0,1) != saida){
	    	documento.value += texto.substring(0,1);
	    }	
	}else{
		documento.value = documento.value.substring(0,documento.value.length - 1) + "";		
	}
	
    
}

function verificaNumero(v){
return !isNaN(parseFloat(v)) && isFinite(v)	
}



function ValidaCPF(strCPF){
	strCPF = strCPF.replace(".","").replace("-","").replace(".","");
	 var Soma; 
	 var Resto; 
	 Soma = 0; 
	 if (strCPF == "00000000000") return false;
	 if(strCPF == '') return false;

	 
	 for (i=1; i<=9; i++) 
		 Soma = Soma + parseInt(strCPF.substring(i-1, i)) * (11 - i); 
	 	 Resto = (Soma * 10) % 11; 
	
	  if ((Resto == 10) || (Resto == 11)) Resto = 0; 
	  if (Resto != parseInt(strCPF.substring(9, 10)) ) return false;
	  
	  Soma = 0; 
	  for (i = 1; i <= 10; i++) 
		  Soma = Soma + parseInt(strCPF.substring(i-1, i)) * (12 - i); 
	  
	  Resto = (Soma * 10) % 11; if ((Resto == 10) || (Resto == 11)) Resto = 0; 
	  
	  if (Resto != parseInt(strCPF.substring(10, 11) ) ) return false; 
	  
	  return true; 
}


function validarCNPJ(cnpj) {

    cnpj = cnpj.replace(".","").replace("/","").replace(".","").replace("-","").replace(".","");

    if(cnpj == '') return false;

    if (cnpj.length != 14)
        return false;

    // LINHA 10 - Elimina CNPJs invalidos conhecidos
    if (cnpj == "00000000000000" || 
        cnpj == "11111111111111" || 
        cnpj == "22222222222222" || 
        cnpj == "33333333333333" || 
        cnpj == "44444444444444" || 
        cnpj == "55555555555555" || 
        cnpj == "66666666666666" || 
        cnpj == "77777777777777" || 
        cnpj == "88888888888888" || 
        cnpj == "99999999999999")
        return false; // LINHA 21

    // Valida DVs LINHA 23 -
    tamanho = cnpj.length - 2
    numeros = cnpj.substring(0,tamanho);
    digitos = cnpj.substring(tamanho);
    soma = 0;
    pos = tamanho - 7;
    for (i = tamanho; i >= 1; i--) {
      soma += numeros.charAt(tamanho - i) * pos--;
      if (pos < 2)
            pos = 9;
    }
    resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
    if (resultado != digitos.charAt(0))
        return false;

    tamanho = tamanho + 1;
    numeros = cnpj.substring(0,tamanho);
    soma = 0;
    pos = tamanho - 7;
    for (i = tamanho; i >= 1; i--) {
      soma += numeros.charAt(tamanho - i) * pos--;
      if (pos < 2)
            pos = 9;
    }
    resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
    if (resultado != digitos.charAt(1))
          return false; // LINHA 49

    return true; // LINHA 51

}



function LimpaDadosPessoaFisica(){
	$('input[id*="nome"]:eq(0)').val("");
	$('input[id*="data_nascim"]:eq(0)').val("");
	$('input[id*="cpf"]:eq(0)').val("");
	$('input[id*="telefone"]:eq(0)').val("");
	$('input[id*="email"]:eq(0)').val("");
	$('input[id*="username"]:eq(1)').val("");
	$('input[id*="senha"]:eq(1)').val("");
}

function LimpaDadosPessoaJuridica(){
	$('input[id*="nome"]:eq(1)').val("");
	$('input[id*="cnpj"]:eq(0)').val("");
	$('input[id*="telefone"]:eq(1)').val("");
	$('input[id*="email"]:eq(1)').val("");
	$('input[id*="endereco"]:eq(0)').val("");
	$('input[id*="username"]:eq(2)').val("");
	$('input[id*="senha"]:eq(2)').val("");
			
}

function LimpaDadosLogin(){
	$('input[id*="username"]:eq(0)').val("");
	$('input[id*="senha"]:eq(0)').val("");
}
