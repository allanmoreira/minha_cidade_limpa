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

	buscaListaMarcacoesCadastradas();

	//Leitura do json DATAPOA
	if (DadosPoa.DadosPoa.length > 0) {
		for (i = 0; i < DadosPoa.DadosPoa.length; i++) {
			var location = DadosPoa.DadosPoa[i];
			AdicionaMarcacao(location);
		}
	}


	setTimeout(function() {
		//Retorna a lista do banco e adiciona os dados novos
		if (resultJsonDenuncias != undefined) {
			var parsed = JSON.parse(resultJsonDenuncias);
			for (i = 0; i < parsed.result.length; i++) {
				var location = parsed.result[i];
				AdicionaMarcacao(location);
			}
		}
	}, 5000);


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


	//#################LEGENDA STATUS
	map.controls[google.maps.ControlPosition.RIGHT_CENTER].push(
		document.getElementById('legend'));

	var iconBase = 'https://maps.google.com/mapfiles/kml/shapes/';
	var icons = {
		Problema: {
			icon: iconBase + 'static/img/icones/vermelho.png'
		},
		Resolvendo: {
			icon: iconBase + 'static/img/icones/azul.png'
		},
		Candidatar: {
			icon: iconBase + 'static/img/icones/cinza.png'
		},
		Pronto: {
			icon: iconBase + 'static/img/icones/verde.png'
		}
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
		HabilitaDivVisuDenuncia(false);
	});

	//Evento que ocorre quando clica na marcação do mapa
	function AdicionaInfoMarker(marker, map, infowindow, strDescricao) {
		google.maps.event.addListener(marker, 'click', function() {
			HabilitaDivVisuDenuncia(true);
			infowindow.setContent(strDescricao);
			infowindow.open(map, marker);
			PreencheCampos();
		});
	}

	//Preenche todos os campos necessário que irá aparecer na divDenuncia 
	//quando for clicado em alguma marcação do map

	function PreencheCampos() {
		idMark = $('input[id$="ipDenuncia"]').val();
		var idMotivo = $('input[id$="ipTitulo"]').val();
		var idEndereco = $('input[id*="ipEndereco"]').val();
		var idDescricao = $('input[id*="ipDadosDigitados"]').val();
		var idImagemCaminho = $('input[id*="ipCaminho"]').val();



		if ($('#ipLiberaVotacao').val().indexOf("VOTOSIM") > -1) {
			$('#btnSalvarCandidato').prop("disabled", true);
			$('div[id$="likesDeslikes"]').css('display', '');
		} else {
			$('div[id$="likesDeslikes"]').css('display', 'none')
			$('#btnSalvarCandidato').prop("disabled", false);

			if ($('#ipJaSeCandidatou').val().indexOf("CANDSIM") > -1) {
				$('#btnSalvarCandidato').prop("disabled", true);
			} else {
				$('#btnSalvarCandidato').prop("disabled", false);
			}
		}


		$('label[id*="txtEndDenuncia"]').text(idEndereco);
		$('label[id*="txtMotivoDenuncia"]').text(idMotivo);
		$('label[id$="txtDescricaoMark"]').text(idDescricao);
		$('img[id$="txtImagemDenuncia"]').attr(idImagemCaminho);

	}


	function SalvarCandidato() {
		var idMark = document.getElementById('ipDenuncia').value;
		if (idMark != "" && idMark != undefined) {

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
						}, 7000);




						$.bootstrapGrowl("Registro incluído com sucesso!", {
							type: 'success',
							align: 'center',
							width: 'auto',
							allow_dismiss: false
						});


						//window.setTimeout('location.reload()', 3000);
						//setTimeout(function() { initMap();}, 1000);

					} else {
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

			//window.setTimeout('location.reload()', 3000);

			//APÓS O RETORNO DE SE CANDIDATAR VERIFICAR SE O STATUS ESTÀ TROCADO.
			//2º APÓS OK chamar esta function (buscaListaMarcacoesCadastradas())	 
			buscaListaMarcacoesCadastradas();
			//3º Executar este script abaixo    		 
			setTimeout(function() {
				//Retorna a lista do banco e adiciona os dados novos
				if (resultJsonDenuncias != undefined) {
					var parsed = JSON.parse(resultJsonDenuncias);
					for (i = 0; i < parsed.result.length; i++) {
						var location = parsed.result[i];
						AdicionaMarcacao(location);
					}
				}
			}, 5000);

		}
	}

	//####################################### LIKES E DESLIKES ###################################################

	function Loader(habilita) {
		if (habilita) {
			$('div[id$="gifLoader"]').css('display', '');
			$('div[id$="divFundoExtra"]').css('display', '');

		} else {
			$('div[id$="gifLoader"]').css('display', 'none');
			$('div[id$="divFundoExtra"]').css('display', 'none');
		}

	}

	$('img[id$="imglikes"]').click(function() {
		var idMark = document.getElementById('ipDenuncia').value;
		if (idMark != "" || idMark != undefined) {
			Loader(true);
			GravaLikesDeslikes(1, idMark);
		}
	});

	$('img[id$="imgDeslikes"]').click(function() {
		var idMark = document.getElementById('ipDenuncia').value;
		if (idMark != "" || idMark != undefined) {
			Loader(true);
			GravaLikesDeslikes(2, idMark);
		}
	});

	function GravaLikesDeslikes(Likes, idMark) {

		$.ajax({
			url: 'LikesDesLikes?idMark=' + idMark + '&like=' + Likes,
			type: 'POST',
			success: function(data) {
				if (data.isValid) {

					$.bootstrapGrowl("Votação computado, obrigado!", {
						type: 'success',
						align: 'center',
						width: 'auto',
						allow_dismiss: false
					});
					HabilitaDivVisuDenuncia(false);
				} else {


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
				Loader(false);
			}
		});


	}

	//########################## FUNCOES E ACOES QUE IRÃO ACONTECER QUANDO HOUVER UMA DENUNCIA ######################	     
	//Evento do click que ocorre quando salva a marcação
	//		 $('button[id$="btnSalvar"]').click(function() {
	//			 //Chama a function que recolhe os dados 
	//		     SalvaDados();
	//		     //Esconde a div
	//		     HabilitaDivCadastro(false);
	//		 });

	//Função que recolhe as informações da denúncia
	function SalvaDados() {
		//Atribui o q tem na src da imagem
		var img = $('#div_imagem_upload').attr('src');
		//Verifica se foi informado alguma imagem
		if (img != undefined && img.indexOf("data:image") > -1) {

			dadosDigitados = "";
			titulo = "";
			//Recupera o texto digitiado
			dadosDigitados = $('textarea[id*="txtComentario"]').val();
			//Recupera o motivo da denuncia
			titulo = $('select[id*="dpMotivo"]').val();

			//Cria o nome do arquivo
			var d = new Date();
			var nameImage = d.getFullYear();
			nameImage += ("00" + (d.getMonth() + 1)).slice(-2);
			nameImage += ("00" + d.getDate()).slice(-2);
			nameImage += ("00" + d.getHours()).slice(-2);
			nameImage += ("00" + d.getMinutes()).slice(-2);
			nameImage += ("00" + d.getSeconds()).slice(-2);

			//Seta o nome do arquivo 
			$('input[name$="upload_imagem_name"]').val(nameImage);

			//Seta o nome com o tipo jpeg, png....
			var formato = img.substring(img.indexOf("/") + 1, img.indexOf(";"));
			//colova o valor na variavel caminho
			var caminho = "../upload_imagens/" + nameImage + "." + formato;
			//Recupera o endereço que foi clicado no mapa
			var endereco = $('label[id*="txtEndereco"]').text();
			//Cria um "string" com todas as informações da denuncia
			var contentString = '<div id=\'content\'>' +
				'<div id=\'siteNotice\'>' +
				'</div>' +
				'<input id=\'ipTitulo\' type=\'hidden\' name=\'ipTitulo\' value=\'' + titulo + '\'>' +
				'<input id=\'ipDenuncia\' type=\'hidden\' name=\'idDenuncia\' value=\'§§§§\'>' +
				'<input id=\'ipEndereco\' type=\'hidden\' name=\'ipEndereco\' value=\'' + endereco + '\'>' +
				'<input id=\'ipCaminho\' type=\'hidden\' name=\'ipCaminho\' value=\'' + caminho + '\'>' +
				'<input id=\'ipCaminhoFotoNova\' type=\'hidden\' name=\'ipCaminhoFotoNova\' value=\'FFDDNN\'>' +
				'<input id=\'ipDadosDigitados\' type=\'hidden\' name=\'ipDadosDigitados\' value=\'' + dadosDigitados + '\'>' +
				'<div id=\'bodyContent\'>' +
				'<p>' + dadosDigitados + '</p>' +
				'</div>' +
				'</div>';
			//Seta os hidden que estão oculto na div divCadastro
			//Com a latitude, longitudo e a string HTML que terá que ser gravada no banco 
			$('#latitudeEsc').val(latitude.lat);
			$('#longitudeEsc').val(latitude.lng);
			$('#htmlEsc').val(contentString);

			//Cria o objeto e adiciona no JSON
			var objDenuncia = {}
			objDenuncia["categoria"] = 'Denuncia';
			objDenuncia["icon"] = 'static/img/icones/vermelho.png';
			objDenuncia["lat"] = "" + latitude.lat + "";
			objDenuncia["lon"] = "" + latitude.lng + "";
			objDenuncia["title"] = "" + titulo + "";
			objDenuncia["html"] = "" + contentString + "";
			//Coloca o caminho da imagem ../upload_imagem/yyyyMMddHHmmss.jpg, png....
			objDenuncia["caminho"] = caminho;

			//Chama a função que salva a denuncia no banco
			salvarMarkBD(objDenuncia);
			//Segura por um tempo para poder recarregar as denuncias do banco 	         
			setTimeout(function() {
				//Retorna a lista do banco e adiciona os dados novos
				if (resultJsonDenuncias != undefined) {
					var parsed = JSON.parse(resultJsonDenuncias);
					for (i = 0; i < parsed.result.length; i++) {
						var location = parsed.result[i];
						AdicionaMarcacao(location);
					}
				}
			}, 7000);
		} else {
			$.bootstrapGrowl("Adicione um imagem, para comprovar!", {
				type: 'danger',
				align: 'center',
				width: 'auto',
				allow_dismiss: false
			});
		}
	}

	//Função que salva a marcação no banco de dados
	function salvarMarkBD(objDenuncia) {
		$.ajax({
			url: 'salvar_marcacao?cam=' + objDenuncia.caminho + '&cat=' + objDenuncia.categoria + '&lat=' + objDenuncia.lat + '&lon=' + objDenuncia.lon + '&tit=' + objDenuncia.title + '&html=' + objDenuncia.html + '&id=' + objDenuncia.id,
			//	             url: 'upload_imagem',
			type: 'POST',
			dataType: 'json',
			data: objDenuncia,
			//	             data: $('#form_upload_imagem').serialize(),
			success: function(data) {
				if (data.isValid) {
					$.bootstrapGrowl("contribuição registrada com sucesso!", {
						type: 'success',
						align: 'center',
						width: 'auto',
						allow_dismiss: false
					});

					markerClick = new google.maps.Marker({
						position: latLongSave,
						icon: 'static/img/icones/vermelho.png',
						map: map
					});

					//Simula o click do button upload
					// $('#form_upload_imagem [type="submit"]').click();
					buscaListaMarcacoesCadastradas();

					//Segura por um tempo para fazer o reload da pagina
					//window.setTimeout('location.reload()', 3000);

					latLongSave = "";
					$('textarea[id*="txtComentario"]').val("");
					return true;
				} else {
					if (!data.usuarioLogado) {
						$.bootstrapGrowl("Usuário não está logado! Faça login ou cadastre-se!", {
							type: 'success',
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
		});

	}



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
		//verificaMostraBotao();
		//$("#container_img").append($('<img />'));
	});

	
	
	$('button[id$="btnSalvar"]').click(function() {
		submit_upload_com_ajax();
		//Esconde a div
		HabilitaDivCadastro(false);
	});
		
		
	
	// faz a requisicao ajax utilizando o formdata, uma especie de hashmap do jquery
	function submit_upload_com_ajax() {

			
		var txtEndereco = $("#EnderecoEsc").val();
		var dpMotivo = $("#dpMotivo").val();
		var txtComentario = $("#txtComentario").val();
		var latitude = $("#latitudeEsc").val();
		var longitude = $("#longitudeEsc").val();
		var icon = 'static/img/icones/vermelho.png';
		var caminho_imagem_upload = document.getElementById("caminho_imagem_upload").files[0];

		var formdata = new FormData();
		formdata.append("txtEndereco", txtEndereco);
		formdata.append("dpMotivo", dpMotivo);
		formdata.append("latitude", latitude);
		formdata.append("longitude", longitude);
		formdata.append("icon", icon);
		formdata.append("txtComentario", txtComentario);
		formdata.append("caminho_imagem_upload", caminho_imagem_upload);

		var xhr = new XMLHttpRequest();

		xhr.open("POST", "upload_imagem", true);

		xhr.send(formdata);

		xhr.onload = function(e) {

			if (this.status == 200) {

				//######### NÂO SEI COMO APLICAR ESTAS VALIDAÇÕES AQUI
				/*
                 	if (data.isValid) {
                         $.bootstrapGrowl("contribuição registrada com sucesso!", {
                             type: 'success',
                             align: 'center',
                             width: 'auto',
                             allow_dismiss: false
                         });

         				//#########OBS SE ESTA FUNCTION FICAR AQUI TEM QUE TIRAR ESTAS 4 LINHAS ABAIXO
         				//#########SENAO MANDAR TODA A FUNCTION PARA O MONTAGEMAP.JS NO LOCAL AONDE ELE ESTÁ FAZENDO ATUALMENTE
                         markerClick = new google.maps.Marker({
                             position: latLongSave,
                             icon: 'static/img/icones/vermelho.png',
                             map: map
                         });
                         
                          buscaListaMarcacoesCadastradas();
                         
                        	//################FALTA LIMPAR O CAMPOS APÓS O RETORNO 
                        	
                         return true;
                     } else {
                         if (!data.usuarioLogado) {
                             $.bootstrapGrowl("Usuário não está logado! Faça login ou cadastre-se!", {
                                 type: 'success',
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
                 
                 */




				// imprime todas as informacoes que o servidor retornou
				alert(this.responseText);

			}

		};
	}




}

//###########################################################################################

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


//google.maps.event.addDomListener(window, 'load', initialize);s