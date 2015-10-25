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
	     //Leitura do json DATAPOA
	     if (resultJsonDenuncias != undefined && resultJsonDenuncias.result.length > 0) {
	         for (i = 0; i < resultJsonDenuncias.result.length; i++) {
	             var location = resultJsonDenuncias.result.length[i];
	             AdicionaMarcacao(location);
	         }
	     } else if (DadosPoa.DadosPoa.length > 0) {
	         for (i = 0; i < DadosPoa.DadosPoa.length; i++) {
	             var location = DadosPoa.DadosPoa[i];
	             AdicionaMarcacao(location);
	         }
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

	     //Adicionando div com info de endereço
	     function AdicionaInfoMarker(marker, map, infowindow, strDescricao) {
	         google.maps.event.addListener(marker, 'click', function() {
	             HabilitaDivVisuDenuncia(true);
	             infowindow.setContent(strDescricao);
	             infowindow.open(map, marker);
	             
	             idMark = $('input[id$="ipDenuncia"]').val();
		         var idMotivo = $('input[id$="ipTitulo"]').val();
		         var idEndereco = $('input[id*="ipEndereco"]').val();
		         var idDescricao = $('input[id*="ipDadosDigitados"]').val();
		         var idImagemCaminho = $('input[id*="ipCaminho"]').val();

		         $('label[id*="txtEndDenuncia"]').text(idEndereco);
		         $('label[id*="txtMotivoDenuncia"]').text(idMotivo);
		         $('label[id$="txtDescricaoMark"]').text(idDescricao);
		         $('label[id*="txtImagemDenuncia"]').text(idImagemCaminho);
	            // PreecheCampos();

	         });
	     }

	     function PreencheCampos() {
	         idMark = $('input[id$="ipDenuncia"]').val();
	         var idMotivo = $('input[id$="ipTitulo"]').val();
	         var idEndereco = $('input[id*="ipEndereco"]').val();
	         var idDescricao = $('input[id*="ipDadosDigitados"]').val();
	         var idImagemCaminho = $('input[id*="ipCaminho"]').val();

	         $('label[id*="txtEndDenuncia"]').text(idEndereco);
	         $('label[id*="txtMotivoDenuncia"]').text(idMotivo);
	         $('label[id$="txtDescricaoMark"]').text(idDescricao);
	         $('label[id*="txtImagemDenuncia"]').text(idImagemCaminho);
	     }
	     
	    
	     


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
	         Analizando: {
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
		 $('button[id*="btnSalvar"]').click(function() {
		     SalvaDados();
		     HabilitaDivCadastro(false);
		 });


		 //Click para fechar a div de cadastro
		 $('button[id*="btnFecharInfoDenuncia"]').click(function() {
			 HabilitaDivVisuDenuncia(false);
		 });

		 //Click para fechar a div de cadastro
		 $('button[id*="btnSalvarCandidato"]').click(function() {
		     SalvarCandidato();
		     HabilitaDivVisuDenuncia(false);
		 });

	   

		 
		//Função que mostra e esconde a div fundo e cadastro
	     function HabilitaDivCadastro(bMostraDiv) {
	         if (bMostraDiv) {
	             $('img[id*="gifLoader"]').css('display', 'none');
	             $('div[id*="divFundo"]').css('display', '');
	             $('div[id*="divCadastro"]').css('display', '')
	         } else {
	             $('textarea[id*="txtComentario"]').val("");
	             $('div[id*="divFundo"]').css('display', 'none');
	             $('div[id*="divCadastro"]').css('display', 'none')
	         }
	     }

		 //Função que mostra e esconde a div fundo e cadastro
		 function HabilitaDivVisuDenuncia(bMostraDiv) {
		     if (bMostraDiv) {
		         $('div[id*="divFundo"]').css('display', '');
		         $('div[id*="divDenuncia"]').css('display', '')
		     } else {
		         $('label[id*="txtEndDenuncia"]').text("");
		         $('label[id*="txtMotivoDenuncia"]').text("");
		         $('input[id$="txtDescricaoMark"]').text("");
		         $('label[id*="txtImagemDenuncia"]').text("");

		         $('div[id*="divFundo"]').css('display', 'none');
		         $('div[id*="divDenuncia"]').css('display', 'none')
		         $('div[id$="gmap"] [class="gm-style-iw"]').parent().children(':eq(2)').click()
		     }
		 }
	 
	 
	     
	     
	     
	     function SalvarCandidato() {
	         //GIOVANNE AQUI VC FAZ I CONTATO POR AJAX COM PARA CADASTRAR A PESSOA NA DENUNCIA
	         // * Tem que ver como a pessoa irá fazer para se cadastrar.
	         // * idMark    <--- Esta variável ja contém o ID da marcação;
	         // * Tem que alocar um espaço na div junto com o da pessoa, para a empresa se candidatar a
	         //colocar o beneficio que irá disponibilizar para o cidadão

	     }

	     //Função que as informações da denúncia
	     function SalvaDados() {
	         dadosDigitados = "";
	         titulo = "";
	         dadosDigitados = $('textarea[id*="txtComentario"]').val();
	         titulo = $('select[id*="dpMotivo"]').val();

	         //VINICIUS COLOCAR OS DADOS DO CAMINHO AQUI
	         var caminho = "";

	         var endereco = $('label[id*="txtEndereco"]').text();
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


	         //Cria o objeto e adiciona no JSON
	         var objDenuncia = {}
	         objDenuncia["categoria"] = 'Denuncia';
	         objDenuncia["icon"] = 'static/img/icones/vermelho.png';
	         objDenuncia["lat"] = "" + latitude.lat + "";
	         objDenuncia["lon"] = "" + latitude.lng + "";
	         objDenuncia["title"] = "" + titulo + "";
	         objDenuncia["html"] = "" + contentString + "";


	         objDenuncia["caminho"] = caminho;

	         //Chama a função que salva a denuncia no banco
	         salvarMarkBD(objDenuncia);


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

	     //Função que salva a marcação no banco de dados
	     function salvarMarkBD(objDenuncia) {
	         $.ajax({
	             contentType: 'application/x-www-form-urlencoded; charset=ISO-8859-1',
	             url: 'salvar_marcacao?cam=' + objDenuncia.caminho + '&cat=' + objDenuncia.categoria + '&lat=' + objDenuncia.lat + '&lon=' + objDenuncia.lon + '&tit=' + objDenuncia.title + '&html=' + objDenuncia.html + '&id=' + objDenuncia.id,
	             type: 'POST',
	             dataType: 'json',
	             //			data: {'submit':true},
	             data: objDenuncia,
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

	                     latLongSave = "";
	                     $('textarea[id*="txtComentario"]').val("");
	                     marker.setMap(map);
	                     AdicionaInfoMarker(markerClick, map, infowindow, objDenuncia.html);
	                     buscaListaMarcacoesCadastradas(objDenuncia);
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
	 }

	 function buscaListaMarcacoesCadastradas(objDenuncia) {

	     $.ajax({
	         url: 'lista_marcacoes_cadastradas',
	         type: 'POST',
	         dataType: 'json',
	         //			data: {'submit':true},
	         data: objDenuncia,
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

	                         resultJsonDenuncias += '", "html":"' + listaMarcacoes[i].html.replace("Â§Â§Â§Â§", listaMarcacoes[i].idMarcacaoDepredacao).replace("FFDDNN",listaMarcacoes[i].setImgDenunciaFinal ).replace("§§§§",listaMarcacoes[i].idMarcacaoDepredacao) + "";
	                     } catch (err) {
	                         resultJsonDenuncias += '", "html":"';
	                     }


	                     
	                     resultJsonDenuncias += '" }';
	                     resultJsonDenuncias += i < listaMarcacoes.length - 1 ? ',' : '';

	                 }
	                 resultJsonDenuncias += ' ]}';
	                 return true;
	             } else {
	                 alert("Nenhum registro encontrado!");

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
	 

	
	 //google.maps.event.addDomListener(window, 'load', initialize);s