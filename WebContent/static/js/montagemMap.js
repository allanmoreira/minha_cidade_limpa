var map;
var street;
var latitude = {
	lat: "",
	lng: ""
};
var markerClick;
var dadosDigitados;
var titulo;
var latLongSave;
var idMark;
var resultJsonDenuncias;
var ispf;

initMap();
//Inicialização do map
function initMap() {
	var myLatlng = {
		lat: -30.0582296,
		lng: -51.2304058
	};
	//var myLatlng = DadosPoa;
	var geocoder = new google.maps.Geocoder;
	var infowindow = new google.maps.InfoWindow;
	var marker = new google.maps.Marker;
	var map = new google.maps.Map(document.getElementById('gmap'), {
		center: myLatlng,
		zoom: 12,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	});
	

	
/*
	//Leitura do json DATAPOA
	if (DadosPoa.DadosPoa.length > 0) {
		for (i = 0; i < DadosPoa.DadosPoa.length; i++) {
			var location = DadosPoa.DadosPoa[i];
			AdicionaMarcacao(location);
		}
	}
*/
	if(resultJsonDenuncias != undefined){
		
		var parsed = JSON.parse(resultJsonDenuncias);
		for (i = 0; i < parsed.result.length; i++) {
			var location = parsed.result[i];
			AdicionaMarcacao(location);
		}
	}else{
		buscaListaMarcacoesCadastradas();
		setTimeout(function() {
			//Retorna a lista do banco e adiciona os dados novos
			if (resultJsonDenuncias != undefined) {
				var parsed = JSON.parse(resultJsonDenuncias);
				for (i = 0; i < parsed.result.length; i++) {
					var location = parsed.result[i];
					AdicionaMarcacao(location);
				}
			}
		}, 4000);

	}
	


	marker.addListener('click', function() {
		infowindow.open(map, markerClick);
	});

	map.addListener('click', function(event) {
		//$('img[id*="gifLoader"]').css('display', 'block');
		latitude.lat = event.latLng.A;
		latitude.lng = event.latLng.F;
		geocodeLatLng(geocoder, map, infowindow, latitude);
	});



	//Adicionando marcações no mapa
	function AdicionaMarcacao(location) {
		var point = new google.maps.LatLng(location.lat, location.lon);
		var marker = new google.maps.Marker({
			position: point,
			map: map,
			title: location.title,
			icon: location.icon
		});
		marker.setMap(map);

		AdicionaInfoMarker(marker, map, infowindow, location.html);
	};


	

	function CenterControl(controlDiv, map) {

		// Set CSS for the control border.
		var controlUI = document.createElement('div');
		controlUI.style.backgroundColor = '#fff';
		controlUI.style.border = '2px solid #fff';
		controlUI.style.borderRadius = '3px';
		controlUI.style.boxShadow = '0 2px 6px rgba(0,0,0,.3)';
		controlUI.style.cursor = 'pointer';
		controlUI.style.marginBottom = '22px';
		controlUI.style.textAlign = 'center';
		controlUI.title = 'Click to recenter the map';
		controlDiv.appendChild(controlUI);

		// Set CSS for the control interior.
		var controlText = document.createElement('div');
		controlText.style.color = 'rgb(25,25,25)';
		controlText.style.fontFamily = 'Roboto,Arial,sans-serif';
		controlText.style.fontSize = '16px';
		controlText.style.lineHeight = '38px';
		controlText.style.paddingLeft = '5px';
		controlText.style.paddingRight = '5px';
		controlText.innerHTML = 'Center Map';
		controlUI.appendChild(controlText);

	}
	//################# FIM LEGENDA STATUS

	function geocodeLatLng(geocoder, map, infowindow, latitude) {
		var latlng = {
			lat: parseFloat(latitude.lat),
			lng: parseFloat(latitude.lng)
		};
		latLongSave = {
			lat: parseFloat(latitude.lat),
			lng: parseFloat(latitude.lng)
		};
		geocoder.geocode({
			'location': latlng
		}, function(results, status) {
			if (status === google.maps.GeocoderStatus.OK) {
				if (results[1]) {
					map.setZoom(11);
					street = results[0].formatted_address.toString();
					
					$('label[id*="txtEndereco"]').text(street);
					$('#EnderecoEsc').val(street);
					$('#longitudeEsc').val(latLongSave.lng);
					$('#latitudeEsc').val(latLongSave.lat);
					
					HabilitaDivCadastro(true);

				} else {
					window.alert('No results found');
				}
			} else {
				window.alert('Geocoder failed due to: ' + status);
			}
		});
	}






	
	

	//Função que mostra e esconde a div fundo e cadastro
	function HabilitaDivVisuDenuncia(bMostraDiv) {
		if (bMostraDiv) {
			$('div[id$="divFundo"]').css('display', '');
			$('div[id$="divDenuncia"]').css('display', '')
		} else {
			$('label[id*="txtEndDenuncia"]').text("");
			$('label[id*="txtMotivoDenuncia"]').text("");
			$('input[id$="txtDescricaoMark"]').text("");
			$('label[id*="txtImagemDenuncia"]').text("");

			$('div[id$="divFundo"]').css('display', 'none');
			$('div[id$="divDenuncia"]').css('display', 'none')
			$('div[id$="gmap"] [class="gm-style-iw"]').parent().children(':eq(2)').click()
		}
	}
	
	//Evento que ocorre quando clica na marcação do mapa
	function AdicionaInfoMarker(marker, map, infowindow, strDescricao) {
		google.maps.event.addListener(marker, 'click', function() {
			infowindow.setContent(strDescricao);
			infowindow.open(map, marker);
			PreencheCampos();
		});
	}

	
	function verificaBeneficioCadastrado()
	{
		if(document.getElementById('txtBeneficioDenuncia').innertHTML == '')
		{
			return true;
		}
		else
		{	
			return false;
		}
	}
	
	
	//Preenche todos os campos necessário que irá aparecer na divDenuncia 
	//quando for clicado em alguma marcação do map

	function PreencheCampos() {
		idMark = $('input[id$="ipDenuncia"]').val();
		var idMotivo = $('input[id$="ipTitulo"]').val();
		var idEndereco = $('input[id*="ipEndereco"]').val();
		var idDescricao = $('input[id*="ipDadosDigitados"]').val();
		var idImagemCaminho = $('input[id*="ipCaminho"]').val();
		var idBeneficio =$('input[id*="ipBeneficioDenuncia"]').val(); 

		
		if ($('#ipLiberaVotacao').val().indexOf("VOTOSIM") > -1) {
			$('#btnSalvarCandidato').prop("disabled", true);
			$('#btnSalvarCandidato').css('display','none');
			
			$('#btnResolvido').css('display','none');
			$('#caminho_imagem_uploadR').css('display','none');	
			$('div[id$="likesDeslikes"]').css('display', '');
			
			
		} else {
			$('div[id$="likesDeslikes"]').css('display', 'none')
			$('#btnSalvarCandidato').prop("disabled", false);

			if ($('#ipJaSeCandidatou').val().indexOf("CANDSIM") > -1) {
				$('#btnResolvido').css('display','');
				$('#caminho_imagem_uploadR').css('display','');
				//Esconde o botão e bloqueia
				$('#btnSalvarCandidato').css('display','none');
				$('#btnSalvarCandidato').prop("disabled", true);
			} else {
				$('#btnSalvarCandidato').css('display','');
				$('#btnSalvarCandidato').prop("disabled", false);				
				$('#btnResolvido').css('display','none');
				$('#caminho_imagem_uploadR').css('display','none');

			}
		}
		
		
		//###### PARTE DO GIOVANNI
		//verifica se tem beneficio cadastrado para poder mostrar
	//	var temBeneficio = verificaBeneficioCadastrado();
		if(idBeneficio.indexOf("NAOBENEF") == -1){//Tem beneficio
			$('#txtBeneficioDenuncia').css('display','');
			$('#txtBeneficioDenuncia').text(idBeneficio);
		}else{
			$('#txtBeneficioDenuncia').css('display','none');
			$('#txtBeneficiotext').css('display','none');
		}
		
		if(lg == undefined ) // Não tem ninguem logado		
		{	
		//	$('#caminho_imagem_upload').css('display','none');
			$('#caminho_imagem_uploadR').css('display','none');
			$('#btnResolvido').css('display','none');
			$('#btnSalvarCandidato').css('display','none');
			$('div[id$="likesDeslikes"]').css('display', 'none');
			$('#txtBeneficiotext').css('display','none');
			$('#btnSalvarBeneficio').css('display','none');	
			
		}else if (lg.PF){// eh pessoa fisica
			//$('div[id$="likesDeslikes"]').css('display', '');
			
			$('#btnSalvarBeneficio').css('display','none');	
			$('#txtBeneficiotext').css('display','none');
		}else if (!lg.PF){//eh pessoa juridica
			//Esconde as divs likes e deslikes
			$('div[id$="likesDeslikes"]').css('display', 'none');
			$('#btnSalvarCandidato').css('display','none');
			$('#btnResolvido').css('display','none');
			$('#caminho_imagem_uploadR').css('display','none');
			
			if(idBeneficio.indexOf("NAOBENEF") > -1){//não tem beneficio	
				//Habilita o botao do beneficio
				$('#btnSalvarBeneficio').css('display','');
				$('#txtBeneficiotext').css('display','');		
				//Esconde o campo label
				$('#txtBeneficioDenuncia').css('display','none');
			}else{
				$('#btnSalvarBeneficio').css('display','none');
				$('#txtBeneficiotext').css('display','none');
				$('#txtBeneficioDenuncia').text(idBeneficio);
			}			
			
		}
		
		
		
		if(document.getElementById('btnSalvarCandidato').disabled == false){
			$('div[id$="likesDeslikes"]').css('display', 'none');
			}
		
	
				
		//Finalizaou o processo de denuncia 
		if(idImagemCaminho.indexOf("_R") > -1){
			$('#btnSalvarCandidato').css('display','none');
			$('#caminho_imagem_uploadR').css('display','none');
			$('#btnResolvido').css('display','none');
			$('#btnSalvarBeneficio').css('display','none');
			$('#txtBeneficiotext').css('display','none');
		}
		  	         
		//var imgAlterado = "\'<c:url value='"+ idImagemCaminho +"'/>\'";  
		$('label[id*="txtEndDenuncia"]').text(idEndereco);
		$('label[id*="txtMotivoDenuncia"]').text(idMotivo);
		$('label[id$="txtDescricaoMark"]').text(idDescricao);
	     $('img[id$="txtImagemDenuncia"]').attr('src',idImagemCaminho);
	     
		//$('img[id$="txtImagemDenuncia"]').attr(idImagemCaminho);
	 
	     HabilitaDivVisuDenuncia(true);
	     
	}



function buscaListaMarcacoesCadastradas() {}
$.ajax({
	url: 'lista_marcacoes_cadastradas',
	type: 'POST',
	dataType: 'json',
	contentType: 'application/json; charset=utf-8',
	success: function(data) {
		if (data.isValid) {
			var listaMarcacoes = data.listaMarcacoesCadastradas;
			resultJsonDenuncias = '{ "result" : [';
			for (var i = 0; i < listaMarcacoes.length; i++) {

				resultJsonDenuncias += '{';
				try {
					resultJsonDenuncias += '"title":"' + listaMarcacoes[i].tipoDepredacao + "";
				} catch (err) {
					resultJsonDenuncias += '", "title":"';
				}
				try {
					resultJsonDenuncias += '", "categoria":"' + listaMarcacoes[i].descricao + "";
				} catch (err) {
					resultJsonDenuncias += '", "categoria":"';
				}
				try {
					resultJsonDenuncias += '", "lat":"' + listaMarcacoes[i].posLat + "";
				} catch (err) {
					resultJsonDenuncias += '", "lat":"';
				}
				try {
					resultJsonDenuncias += '", "lon":"' + listaMarcacoes[i].posLon + "";
				} catch (err) {
					resultJsonDenuncias += '", "lon":"';
				}
				try {
					resultJsonDenuncias += '", "icon":"' + RetornaIconeStatus(listaMarcacoes[i].status) + "";
				} catch (err) {
					resultJsonDenuncias += '", "icon":"';
				}
				try {

					resultJsonDenuncias += '", "html":"' + listaMarcacoes[i].html.toString().trim().replace(new RegExp("\'", "g"), "\"") + "";
				} catch (err) {
					resultJsonDenuncias += '", "html":"';
				}

				resultJsonDenuncias += '" }';
				resultJsonDenuncias += i < listaMarcacoes.length - 1 ? ',' : '';

			}
			resultJsonDenuncias += ' ]}';
			return true;
		} else {
			return false;
		}
	}

});




function RetornaIconeStatus(status) {
	switch (status) {
		case "1":
			return 'static/img/icones/vermelho.png';
		case "2":
			return 'static/img/icones/azul.png';
		case "3":
			return 'static/img/icones/cinza.png'
		case "4":
			return 'static/img/icones/verde.png'
		default:
			return "";
	}
}


	
	
}


	//Click para fechar a div de cadastro
	$('button[id*="btnFechar"]').click(function() {
		HabilitaDivCadastro(false);
	});



	//Click para fechar a div de cadastro
	$('button[id*="btnFecharInfoDenuncia"]').click(function() {
		HabilitaDivVisuDenuncia(false);
	});


	//Função que mostra e esconde a div fundo e cadastro
	function HabilitaDivCadastro(bMostraDiv) {
		if (bMostraDiv) {
			$('img[id$="gifLoader"]').css('display', 'none');
			$('div[id$="divFundo"]').css('display', '');
			$('div[id$="divCadastro"]').css('display', '')
		} else {
			$('textarea[id*="txtComentario"]').val("");
			$('#caminho_imagem_upload').val("");
			var $image = $('#div_imagem_upload')
			$image.removeAttr('src').replaceWith($image.clone());
			$('div[id$="divFundo"]').css('display', 'none');
			$('div[id$="divCadastro"]').css('display', 'none')
		}
	}

	//Função que mostra e esconde a div fundo e cadastro
	function HabilitaDivVisuDenuncia(bMostraDiv) {
		if (bMostraDiv) {
			$('div[id$="divFundo"]').css('display', '');
			$('div[id$="divDenuncia"]').css('display', '')
		} else {
			$('label[id*="txtEndDenuncia"]').text("");
			$('label[id*="txtMotivoDenuncia"]').text("");
			$('input[id$="txtDescricaoMark"]').text("");
			$('label[id*="txtImagemDenuncia"]').text("");

			$('div[id$="divFundo"]').css('display', 'none');
			$('div[id$="divDenuncia"]').css('display', 'none')
			$('div[id$="gmap"] [class="gm-style-iw"]').parent().children(':eq(2)').click()
		}
	}




	//################################ DIV CANDIDATAR-SE PARA RESOLVER O PROBLEMA DA DENUNCIA

	//Evento do click do botão salvar candidato da  divDenuncia
	$('button[id$="btnSalvarCandidato"]').click(function() {
		//Chama a function q irá chamar a servlet candidatarse
		SalvarCandidato();
		event.stopImmediatePropagation();
		return false;
	});

	


	function SalvarCandidato() {
		var idMark = document.getElementById('ipDenuncia').value;
		if (idMark != "" && idMark != undefined) {
			Loader(true);

			//GIOVANNE AQUI VC FAZ I CONTATO POR AJAX COM PARA CADASTRAR A PESSOA NA DENUNCIA
			// * Tem que ver como a pessoa irá fazer para se cadastrar.
			// * idMark    <--- Esta variável ja contém o ID da marcação;
			// * Tem que alocar um espaço na div junto com o da pessoa, para a empresa se candidatar a
			//colocar o beneficio que irá disponibilizar para o cidadão

			//CHAMAR O MÉTODO AJAX

			$.ajax({
				url: 'candidatarse?idmarcacao=' + idMark,
				type: 'POST',
				success: function(data) {
					if (data.isValid) {
					
						  formatacaoJSON(data.listaMarcacoesCadastradas)
	                      
	                      setTimeout(function() {
	                       	initMap();
            
	          				Loader(false);
	          				HabilitaDivVisuDenuncia(false);
	          				
	          				$.bootstrapGrowl("Registro incluído com sucesso!", {
								type: 'success',
								align: 'center',
								width: 'auto',
								allow_dismiss: false
							});
	  
	             			}, 4000);
	                      
						//window.setTimeout('location.reload()', 3000);
						//setTimeout(function() { initMap();}, 1000);

					} else {
						Loader(false);
						HabilitaDivVisuDenuncia(false);
						if (!data.usuarioLogado) {
							$.bootstrapGrowl("Usuário não está logado! Faça login ou cadastre-se!", {
								type: 'success',
								align: 'center',
								width: 'auto',
								allow_dismiss: false
							});

						} else if (data.jaTemCandidato) {
							$.bootstrapGrowl("Esta depredação já tem um candidato!", {
								type: 'danger',
								align: 'center',
								width: 'auto',
								allow_dismiss: false
							});
						} else if (data.jaSeCadastrou) {
							$.bootstrapGrowl("Candidato cadastrado para outra denúncia!", {
								type: 'danger',
								align: 'center',
								width: 'auto',
								allow_dismiss: false
							});
						} else {
							$.bootstrapGrowl("Erro ao se candidatar, entre em contato conosco!", {
								type: 'danger',
								align: 'center',
								width: 'auto',
								allow_dismiss: false
							});
						}


					}
				}
			});

		
	

		}
	}

	
	











	function Loader(habilita) {
		if (habilita) {
			$('div[id$="gifLoader"]').css('display', '');
			$('div[id$="divFundoExtra"]').css('display', '');

		} else {
			$('div[id$="gifLoader"]').css('display', 'none');
			$('div[id$="divFundoExtra"]').css('display', 'none');
		}

	}




	//####################################### LIKES E DESLIKES ###################################################



	function GravaLikesDeslikes(Likes, idMark) {

		$.ajax({
			url: 'LikesDesLikes?idMark=' + idMark + '&like=' + Likes,
			type: 'POST',
			success: function(data) {
				if (data.isValid) {

									
					  formatacaoJSON(data.listaMarcacoesCadastradas)
                      
                      setTimeout(function() {
                       	initMap();
                                        	
          				Loader(false);
          				HabilitaDivVisuDenuncia(false);
          				
          				$.bootstrapGrowl("Votação computado, obrigado!", {
          						type: 'success',
    							align: 'center',
    							width: 'auto',
    							allow_dismiss: false
          				});
          			  
             			}, 4000);
                      
					return false;
					
				} else {

					Loader(false);
					if (!data.usuarioLogado) {
						$.bootstrapGrowl("Usuário não está logado! Faça login ou cadastre-se!", {
							type: 'danger',
							align: 'center',
							width: 'auto',
							allow_dismiss: false
						});
					} else if (data.jaVotou) {
						$.bootstrapGrowl("Usuário já participou desta votação!", {
							type: 'danger',
							align: 'center',
							width: 'auto',
							allow_dismiss: false
						});
					}
				}
		
			}
		});


	}

	



	$('img[id$="imgDeslikes"]').on("click",function(event) {
		var idMark = document.getElementById('ipDenuncia').value;
		if (idMark != "" || idMark != undefined) {
			Loader(true);
			GravaLikesDeslikes(2, idMark);
	
		}
		event.stopImmediatePropagation();
		return false;
	});
	
	$('img[id$="imglikes"]').on("click", function(event) {
		var idMark = document.getElementById('ipDenuncia').value;
		if (idMark != "" || idMark != undefined) {
			Loader(true);
			GravaLikesDeslikes(1, idMark);
		}
		event.stopImmediatePropagation();
		return false;

	});
	

//###########################################################################################


	//############################# PARTE DO ALLAN #######################################


	function readURL(input) {
		if (input != "" && input.files[0]) {
			var reader = new FileReader();

			reader.onload = function(e) {
				$("#div_imagem_upload").attr('src', e.target.result)
			};
			reader.readAsDataURL(input.files[0]);
		}
	}

	function verificaMostraBotao() {
		$('input[type=file]').each(function(index) {
			if ($('input[type=file]').eq(index).val() != "") {

				//alert($('#caminho_imagem_upload').val());
				readURL(this);
				//$('.hide').show();
			}
		});
	}

	$('#caminho_imagem_upload').on("change", function() {
		verificaMostraBotao();
	});

	$('#caminho_imagem_upload').on("click", function() {
		$('#caminho_imagem_upload').change(verificaMostraBotao);

	});

		
	//###################### PARTE DO VINICIUS
	$('#caminho_imagem_uploadR').on("change", function() {
		verificaMostraBotaoR();
	});

	$('#caminho_imagem_uploadR').on("click", function() {
		$('#caminho_imagem_uploadR').change(verificaMostraBotaoR);
	});
	
	
	function readURLR(input) {
		if (input != "" && input.files[0]) {
			var reader = new FileReader();

			reader.onload = function(e) {
				$("#txtImagemDenuncia").attr('src', e.target.result)
			};
			reader.readAsDataURL(input.files[0]);
		}
	}

	function verificaMostraBotaoR() {
		$('input[type=file]').each(function(index) {
			if ($('input[type=file]').eq(index).val() != "") {

				//alert($('#caminho_imagem_upload').val());
				readURLR(this);
				//$('.hide').show();
			}
		});
	}
	
	

	//cadastrar marcacao com problema
	$('button[id$="btnSalvar"]').click(function() {
		
		 if (lg != undefined && lg.PF){
			 //Chama a function q irá chamar a servlet candidatarse
				submit_upload_com_ajax(1);
		 }else{
			 $.bootstrapGrowl("Somente pessoa física!", {
                 type: 'danger',
                 align: 'center',
                 width: 'auto',
                 allow_dismiss: false
             });
		 }
		
		return false;

	});

	//para usar o upload da foto do problema resolvido
	$('button[id$="btnResolvido"]').click(function() {

		 if (lg != undefined && lg.PF){
			 //Chama a function q irá chamar a servlet candidatarse
				submit_upload_com_ajax(4);
		 }else{
			 $.bootstrapGrowl("Somente pessoa física!", {
                type: 'danger',
                align: 'center',
                width: 'auto',
                allow_dismiss: false
            });
		 }
		return false;

	});
		
		
	
	// faz a requisicao ajax utilizando o formdata, uma especie de hashmap do jquery
	function submit_upload_com_ajax(status) {

		//################### QUANDO TENTO ASSIM: O BOTAO SALVAR PARA DE FUNCIONAR :S
		var idMark1 =$('input[id$="ipDenuncia"]').val();
		var txtEndereco = $("#EnderecoEsc").val();
		var dpMotivo = $("#dpMotivo").val();
		var txtComentario = $("#txtComentario").val();
		var latitude = $("#latitudeEsc").val();
		var longitude = $("#longitudeEsc").val();
		if(status == 1 /* status 1 cadastrar problema */){
			var icon = 'static/img/icones/vermelho.png';
			var caminho_imagem_upload = document.getElementById("caminho_imagem_upload").files[0];
		} else if(status == 4 /* status 4 resolver problema */){
			var icon = 'static/img/icones/verde.png';
			var caminho_imagem_upload = document.getElementById("caminho_imagem_uploadR").files[0];
		}

		if( (txtComentario != undefined && txtComentario != "") || status == 4  ){
			if(caminho_imagem_upload != undefined){
				Loader(true);
				var formdata = new FormData();
				formdata.append("txtEndereco", txtEndereco);
				formdata.append("dpMotivo", dpMotivo);
				formdata.append("latitude", latitude);
				formdata.append("longitude", longitude);
				formdata.append("icon", icon);
				formdata.append("txtComentario", txtComentario);
				formdata.append("caminho_imagem_upload", caminho_imagem_upload);
				formdata.append("status", status);
				//alert(idMark);
				formdata.append("idMark", idMark);

				var xhr = new XMLHttpRequest();

				xhr.open("POST", "upload_imagem", true);

				xhr.send(formdata);

				xhr.onload = function(e) {

					if (this.status == 200) {				
						if (xhr.readyState == 4) {
							var responseJson = eval('(' + xhr.responseText + ')');
							
							
							if (responseJson.isValid) {
		                                                         
		                        formatacaoJSON(responseJson.listaMarcacoesCadastradas)
		                        
		                        setTimeout(function() {
		            				//LIMPAR OS CAMPOS		                        	
		                        	initMap();		                        		                        	
		                        	Loader(false);
		                        	HabilitaDivCadastro(false);   
		                        	if(status == 4 ){
		                        		HabilitaDivVisuDenuncia(false);			            					
		                        	}
		                        	 
		            				
		            				$.bootstrapGrowl("contribuição registrada com sucesso!", {
		                                  type: 'success',
		                                  align: 'center',
		                                  width: 'auto',
		                                  allow_dismiss: false
		                              });
		            				  
		               				}, 5000);
		                        
		                        
		                       	//################FALTA LIMPAR O CAMPOS APÓS O RETORNO 
		                        latLongSave = "";
		    					$('textarea[id*="txtComentario"]').val("");
		                       	
		                        return true;
		                    } else {
		                    	Loader(false);
		                    	if (!responseJson.usuarioLogado) {
		                            $.bootstrapGrowl("Usuário não está logado! Faça login ou cadastre-se!", {
		                                type: 'danger',
		                                align: 'center',
		                                width: 'auto',
		                                allow_dismiss: false
		                            });

		                        } else if (!responseJson.usuarioCerto) {
		                            $.bootstrapGrowl("Usuário não candidatou-se para resolver este problema!", {
		                                type: 'danger',
		                                align: 'center',
		                                width: 'auto',
		                                allow_dismiss: false
		                            });

		                        } else {
		                            $.bootstrapGrowl("Erro ao salvar denúncio, entre em contato conosco!", {
		                                type: 'danger',
		                                align: 'center',
		                                width: 'auto',
		                                allow_dismiss: false
		                            });
		                        }
		                        return false;
		                    }
				        }
					}
				};	
				
			}else{
				 $.bootstrapGrowl("Carregue uma foto de denúncia!", {
	                   type: 'danger',
	                   align: 'center',
	                   width: 'auto',
	                   allow_dismiss: false
	               });
			}
		}else{
			   $.bootstrapGrowl("Preenche um comentário sobre a denúncia!", {
                   type: 'danger',
                   align: 'center',
                   width: 'auto',
                   allow_dismiss: false
               });
		}
	}
	
	
	
	
	
	 function Loader(habilita){
		 	    	 if(habilita){
		 	    		 $('div[id$="gifLoader"]').css('display','');
		 	    		 $('div[id$="divFundoExtra"]').css('display', ''); 
		 	    		 
		 	    	 }else{
		 	    		 $('div[id$="gifLoader"]').css('display','none');
		 	    		 $('div[id$="divFundoExtra"]').css('display', 'none'); 	    		 
		 	    	 }
		 	    	 
	 }
	 
	 
	 
	 
	 
	 //############## PARTE DO GIOVANNI
	 

	//Evento do click do botão salvar beneficio da  divDenuncia
	 $('button[id$="btnSalvarBeneficio"]').click(function() {

			 //Chama a function q irá chamar a servlet candidatarse
			    SalvarBeneficio();
		event.stopImmediatePropagation();
	    return false;
	 });


	function SalvarBeneficio() {
				 var idMark = document.getElementById('ipDenuncia').value;
		    	 var descricaoBeneficio = $('#txtBeneficiotext').val();

		    	 if(idMark != "" && idMark != undefined ){
		    		 if(descricaoBeneficio != "" && descricaoBeneficio != undefined){
		    		  Loader(true);
		    		 $.ajax({
			             	url: 'incluirBeneficio?idmarcacao=' + idMark + '&descricao=' + descricaoBeneficio,
			             	type: 'POST',
			             	success: function(data) 
			             		{
			             		if (data.isValid){
			             			
			             		   formatacaoJSON(data.listaMarcacoesCadastradas)
			                        
			                        setTimeout(function() {
			            				//LIMPAR OS CAMPOS		                        	
			                        	initMap();		                        		                        	
			                        	Loader(false);
			                        	HabilitaDivCadastro(false);   
			                        	HabilitaDivVisuDenuncia(false);
			            				
			               				$.bootstrapGrowl("beneficio registrado com sucesso!", {
			                                  type: 'success',
			                                  align: 'center',
			                                  width: 'auto',
			                                  allow_dismiss: false
			                              });
			            				  
			               				}, 4000);
	 
		    		                }
			             		else{
			             			Loader(false);
			             			if (!data.usuarioLogado) {
				                         $.bootstrapGrowl("Usuário não está logado! Faça login ou cadastre-se!", {
				                             type: 'success',
				                             align: 'center',
				                             width: 'auto',
				                             allow_dismiss: false
				                         });

				                     }else if (data.jaTemBeneficio){
				                    	  $.bootstrapGrowl("Esta depredação já tem um Beneficio!", {
					                             type: 'danger',
					                             align: 'center',
					                             width: 'auto',
					                             allow_dismiss: false
					                         });
				                    }
			             			else {
				                         $.bootstrapGrowl("Erro ao se candidatar, entre em contato conosco!", {
				                             type: 'danger',
				                             align: 'center',
				                             width: 'auto',
				                             allow_dismiss: false
				                         });
				                     }
			             			
			             			
			             		}
			             }
		    		 });
		    		 }else{
		    			 $.bootstrapGrowl("Informe o beneficio!", {
			                   type: 'danger',
			                   align: 'center',
			                   width: 'auto',
			                   allow_dismiss: false
			               });
		    			 
		    		 }
		    		 	  		 
		    	 }	     
		     }



function formatacaoJSON(ListaMarcacao) {
	
	var listaMarcacoes = ListaMarcacao;
	resultJsonDenuncias = '{ "result" : [';
	for (var i = 0; i < listaMarcacoes.length; i++) {

		resultJsonDenuncias += '{';
		try {
			resultJsonDenuncias += '"title":"' + listaMarcacoes[i].tipoDepredacao + "";
		} catch (err) {
			resultJsonDenuncias += '", "title":"';
		}
		try {
			resultJsonDenuncias += '", "categoria":"' + listaMarcacoes[i].descricao + "";
		} catch (err) {
			resultJsonDenuncias += '", "categoria":"';
		}
		try {
			resultJsonDenuncias += '", "lat":"' + listaMarcacoes[i].posLat + "";
		} catch (err) {
			resultJsonDenuncias += '", "lat":"';
		}
		try {
			resultJsonDenuncias += '", "lon":"' + listaMarcacoes[i].posLon + "";
		} catch (err) {
			resultJsonDenuncias += '", "lon":"';
		}
		try {
			resultJsonDenuncias += '", "icon":"' + RetornaIconeStatusD(listaMarcacoes[i].status) + "";
		} catch (err) {
			resultJsonDenuncias += '", "icon":"';
		}
		try {

			resultJsonDenuncias += '", "html":"' + listaMarcacoes[i].html.toString().trim().replace(new RegExp("\'", "g"), "\"") + "";
		} catch (err) {
			resultJsonDenuncias += '", "html":"';
		}

		resultJsonDenuncias += '" }';
		resultJsonDenuncias += i < listaMarcacoes.length - 1 ? ',' : '';

	}
	resultJsonDenuncias += ' ]}';
}

function RetornaIconeStatusD(status) {
	switch (status) {
		case "1":
			return 'static/img/icones/vermelho.png';
		case "2":
			return 'static/img/icones/azul.png';
		case "3":
			return 'static/img/icones/cinza.png'
		case "4":
			return 'static/img/icones/verde.png'
		default:
			return "";
	}
}





//google.maps.event.addDomListener(window, 'load', initialize);s