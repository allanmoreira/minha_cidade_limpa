	 
	var map;
	var street; 
	var latitude = {lat:"",lng:""};
	var markerClick;
	var dadosDigitados;
	var titulo;

	initMap();
	//Inicialização do map
	function initMap() {
	var myLatlng = {lat: -30.0582296, lng:-51.2304058};
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
	if (DadosPoa.DadosPoa.length > 0) {
	        for (i=0; i<DadosPoa.DadosPoa.length; i++) {
	            var location = DadosPoa.DadosPoa[i];
		            AdicionaMarcacao(location); 
	        }
	}
	 //Adicionando marcações no mapa
	function AdicionaMarcacao(location) {
	    var point = new google.maps.LatLng(location.lat, location.lon); 
	    var marker = new google.maps.Marker({
	    position:point,
	    map: map,
	    title: location.title,
	    icon: location.icon
		});
		marker.setMap(map);

	    AdicionaInfoMarker(marker, map, infowindow, location.html);
	};
	//Adicionando div com info de endereço
	function AdicionaInfoMarker(marker, map, infowindow, strDescricao) {
	    google.maps.event.addListener(marker, 'click', function () {
	        infowindow.setContent(strDescricao);
	        infowindow.open(map, marker);
	    });
	}

	  
	//#################LEGENDA STATUS
	map.controls[google.maps.ControlPosition.RIGHT_CENTER].push(
	  document.getElementById('legend'));

	var iconBase = 'https://maps.google.com/mapfiles/kml/shapes/';
	var icons = {
	  Problema: {
	    icon: iconBase + 'img/icones/vermelho.ico'
	  },
	  Resolvendo: {
	    icon: iconBase + 'img/icones/azul.ico'
	  },
	  Analizando: {
	    icon: iconBase + 'img/icones/cinza.ico'
	  },
	  Pronto: {
	    icon: iconBase + 'img/icones/verde.ico'
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
	  var latlng = {lat: parseFloat(latitude.lat), lng: parseFloat(latitude.lng)};
	  geocoder.geocode({'location': latlng}, function(results, status) {
	    if (status === google.maps.GeocoderStatus.OK) {
	      if (results[1]) {
	        map.setZoom(11);
	        markerClick = new google.maps.Marker({
	          position: latlng,
	          icon: 'img/icones/vermelho.png',
	          map: map
	        });

	       street = results[0].formatted_address.toString();
			$('label[id*="txtEndereco"]').text(street);
			HabilitaDivCadastro(true);

	 		//infowindow.setContent(results[0].formatted_address);
			//infowindow.open(map, markerClick);
			
	      } else {
	        window.alert('No results found');
	      }
	    } else {
	      window.alert('Geocoder failed due to: ' + status);
	    }
	  });
	 }
	 
	 marker.addListener('click', function() {
	  infowindow.open(map, markerClick); 
	});
	/*  map.addListener('click', function(event) {
		$('img[id*="gifLoader"]').css('display','block');
		latitude.lat =event.latLng.A;
		latitude.lng =event.latLng.F;
		geocodeLatLng(geocoder, map, infowindow, latitude);
	  });
	  */

	 //Click para fechar a div de cadastro
	$('button[id*="btnFechar"]').click(function(){
		HabilitaDivCadastro(false);
	});

	 //Click para fechar a div de cadastro
	$('button[id*="btnSalvar"]').click(function(){
		SalvaDados();
		HabilitaDivCadastro(false);
	});

	//Função que as informações da denúncia
	function SalvaDados(){
		dadosDigitados = "";
		titulo = "";
		dadosDigitados = $('textarea[id*="txtComentario"]').val();
		titulo = $('select[id*="dpMotivo"]').val();
		var contentString = '<div id="content">'+
	      '<div id="siteNotice">'+
	      '</div>'+
	      '<h1 id="firstHeading" class="firstHeading">'+ titulo +'</h1>'+
	      '<div id="bodyContent">'+
	      '<p>'+ dadosDigitados +'</p>'+
	      '</div>'+
	      '</div>';


	    //Cria o objeto e adiciona no JSON
		var objDenuncia = {}
		objDenuncia["categoria"] = 'Denuncia';
		objDenuncia["icon"] = 'img/icones/vermelho.png';
		objDenuncia["lat"] = ""+ latitude.lat + "";
		objDenuncia["lon"] = ""+ latitude.lng + "";
		objDenuncia["title"] = "" + titulo + "";
		objDenuncia["html"] = "" + contentString + "";
		objDenuncia["id"] = DadosPoa.DadosPoa.length + 1;
		
		//Chama a função que salva a denuncia no banco
		salvarMarkBD(objDenuncia);

	}

	//Função que salva a marcação no banco de dados
	function salvarMarkBD(objDenuncia){
		$.ajax({
			url: 'salvar_marcacao?cat='+ objDenuncia.categoria +'&lat='+objDenuncia.lat+'&lon='+objDenuncia.lon+'&tit='+objDenuncia.title+'&html='+objDenuncia.html+'&id='+objDenuncia.id,
			type: 'POST',
			dataType: 'json',
//			data: {'submit':true},
			data: objDenuncia,
			success: function(data){
				if(data.isValid) {
					//Se salvar correto no banco de dados, 
					//retorna nesta condição e salva a marcação no map 
					var obj = data.pessoaFisica;
										
					DadosPoa.DadosPoa.push(objDenuncia); 
					marker.setMap(map);
			    	AdicionaInfoMarker(markerClick, map, infowindow, contentString);					
				
					return true;
				
				}
				else {
					alert("Usuário não está logado! Faça login ou cadastre-se!");
					
					return false;
				}
			}
	
		});
	}

	 //Função que mostra e esconde a div fundo e cadastro
	  function HabilitaDivCadastro(bMostraDiv){
			if(bMostraDiv){
				$('img[id*="gifLoader"]').css('display','none');
				$('div[id*="divFundo"]').css('display','');
				$('div[id*="divCadastro"]').css('display','')
			}
			else{
				$('div[id*="divFundo"]').css('display','none');	
				$('div[id*="divCadastro"]').css('display','none') 
			}
		}  
	  }
	
	function buscaListaMarcacoesCadastradas(){
		$.ajax({
			url: 'lista_marcacoes_cadastradas',
			type: 'POST',
			dataType: 'json',
//			data: {'submit':true},
			data: objDenuncia,
			success: function(data){
				if(data.isValid) {
					var listaMarcacoes = data.listaMarcacoesCadastradas;
					$.each(listaMarcacoes, function(i){
						
						aletrt(listaMarcacoes[i].idMarcacao);
						
					});
				
				}
				else {
					alert("Usuário não está logado! Faça login ou cadastre-se!");
					
					return false;
				}
			}
	
		});
	}
	  
	//google.maps.event.addDomListener(window, 'load', initialize);
