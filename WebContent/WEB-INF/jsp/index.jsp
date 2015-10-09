<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Nossa Cidade Limpa</title>

<!-- Bootstrap -->
<link rel="stylesheet" href="<c:url value="static/css/bootstrap.min.css"/>" >
<link rel="stylesheet" href="<c:url value="static/css/font-awesome.min.css"/>">
<link rel="stylesheet" href="<c:url value="static/css/animate.min.css"/>" >
<link rel="stylesheet" href="<c:url value="static/css/animate.css"/>"  />
<link rel="stylesheet" href="<c:url value="static/css/style.css"/>" >
<link rel="stylesheet" href="<c:url value="static/css/EstiloSiteAdicional.css"/>">

<style>
#legend {
	background: white;
	padding: 10px;
}
</style>

<script src="http://maps.google.com/maps/api/js?sensor=false&libraries=geometry&v=3.7"> </script>

<script src="http://code.jquery.com/jquery-1.9.0.min.js"></script>
<script src="<c:url value="static/js/jquery-1.10.2.min.js"/>"></script>
<script src="<c:url value="static/js/maplace-0.1.3.min.js"/>"></script>
<script src="<c:url value="static/js/maplace-0.1.3.js"/>"></script>
<script src="<c:url value="static/js/jquery-1.10.2.min.js"/>"></script>
<script src="<c:url value="static/js/jquery-2.1.1.min.js"/>"></script>
<script src="<c:url value="static/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="static/js/wow.min.js"/>"></script>
<script src="<c:url value="static/js/jquery.easing.min.js"/>"></script>
<script src="<c:url value="static/js/functions.js"/>"></script>
<script src="<c:url value="static/js/JSON_DADOSPOA.js"/>"></script>
<script src="<c:url value="static/js/AdicionaisJS.js"/>"></script>

<!-- exemplos de submit com form e sem form -->
<script src="<c:url value="static/js/submit_sem_form.js"/>"></script>
<script src="<c:url value="static/js/submit_com_form.js"/>"></script>

<!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->

<title>Insert title here</title>
</head>
<body>
	<!-- Navigation -->
	<div id="navigation">
		<nav class="navbar navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="row">
				<div class="col-md-2">
					<div class="site-logo">
						<a href="index.html" class="brand"
							style="color: #FFFFFF; text-shadow: 2px -1px 10px rgba(255, 255, 255, .5);">Nossa Cidade Limpa</a>
					</div>
				</div>

				<div class="col-md-10">
					<!-- Brand and toggle get grouped for better mobile display -->
					<div class="navbar-header">
						<button type="button" class="navbar-toggle" data-toggle="collapse"
							data-target="#menu">
							<i class="fa fa-bars"></i>
						</button>
					</div>
					<!-- Collect the nav links, forms, and other content for toggling -->
					<div class="collapse navbar-collapse" id="menu">
						<ul class="nav navbar-nav navbar-right">
							<li style="background-color: #F7E726;" class="active"><a
								href="#home">Home</a></li>
							<li style="background-color: #F7E726;"><a href="#about">Sobre
									nós</a></li>
							<li style="background-color: #F7E726;"><a href="#services">Depoimentos</a></li>
							<li style="background-color: #F7E726;"><a href="#works">Depredações</a></li>
							<li style="background-color: #F7E726;"><a href="#contact">Contatos</a></li>
						</ul>
					</div>
					<!-- /.Navbar-collapse -->
				</div>
			</div>
		</div>
		<!-- /.container --> </nav>
	</div>
	<!-- /Navigation -->

	<div id="home">
		<div class="col-lg-8 col-lg-offset-2">
			<div class="slider">
				<img src="static/img/poa/ponto_turistico.jpg" class="img-responsive" />
			</div>
		</div>
	</div>


	<!-- Section: about -->
	<section id="about">
	<div class="container marginbot-50">
		<div class="row">
			<div class="col-lg-8 col-lg-offset-2">
				<div class="wow flipInY" data-wow-offset="0" data-wow-delay="0.4s">
					<div class="section-heading text-center">
						<h2 class="h-bold">Sobre nós</h2>
						<div class="divider-header"></div>
						<p class="refTexto">Este projeto está direcionado aos moradores, turistas e órgãos públicos que 
estão diretamente envolvidos, situações de depredações do patrimônio público, além de te local com 
 informações e contribuições sobre os problemas que as pessoas enfrentam
na cidade, este sistema disponibilizará aos usuários consultar e informar estes problemas que 
denigrem e afeta a imagem da cidade.</p>

<p class="refTexto">Através do sistemas os moradores da cidade poderá organizar-se em grupos, para concertar 
limpar o patrimônio público, também ajudará os cidadãos a terem um contato direto com o 
os órgãos públicos, que por sua vez terão maior agilidade com a visualização de mapa dos 
problemas de depredações da cidade.</p>

<p class="refTexto">Contrário do que atualmente acontece, em que as prefeituras não conseguem atender a demanda 
de limpezas. As pessoas poderão "adotar" por tempo determinado a manutenção do patrimônio, 
a fim de usufrui de seus benefício. Logo contamos com a sua colaboração para melhor desenvolvimento  
e manutenção de nossa cidade.</p>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>


	</section>
	<!-- /Section: about -->

	<!-- Section: services -->
	<section id="services" class="home-section color-dark bg-white">
	<div class="container marginbot-50">
		<div class="row">
			<div class="col-lg-8 col-lg-offset-2">
				<div class="wow flipInY" data-wow-offset="0" data-wow-delay="0.1s">
					<div class="section-heading text-center">
						<h2 class="h-bold">Depoimentos</h2>
						<div class="divider-header"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="container">


					<div class="row">
								<div class="col-md-4">
									<div class="wow flipInY" data-wow-offset="0" data-wow-delay="0.8s">
										<div>
											<img src="static/img/acoes/monumento_restaurado.jpg" 
											class="img_Comentarios" />
										</div>
										<h2>Homenagem aos Mortos em Combate ao Comunismo</h2>
										<p>Conjunto de obras, recentemente foi depredado, graças a iniciativa da SINDUSCON, as obras foram restauradas.</p>
									</div>
								</div>
								<div class="col-md-4">
									<div class="wow flipInY" data-wow-offset="0" data-wow-delay="1.2s">
										<div >
										<img src="static/img/acoes/giuseppe_e_anita.jpg" 
											class="img_Comentarios" />
									
												</div>
										<h2>Líderes da Revolução Farroupilha</h2>
										<p>Imponente monumento histórico de Porto Alegre, inaururado em 1936, atualmente, apresenta-se em situações vergonhosas.</p>
									</div>
								</div>
								<div class="col-md-4">
									<div class="wow flipInY" data-wow-offset="0" data-wow-delay="1.6s">
										<div class="service-icon">
											<img src="static/img/acoes/poa_limpa.jpg" class="img_Comentarios" />
																			</div>
										<h2>Por uma POA limpa</h2>
										<p>Policias ajudam no combate com as depredações patrimoniais.</p>
									</div>
								</div>
							</div>
						
							

	</div>
	</section>
	<!-- Section: services -->

	<!-- Section: works -->
	<section id="works">
	<div class="container marginbot-50">
		<div class="row">
			<div class="col-lg-8 col-lg-offset-2">
				<div class="wow flipInY" data-wow-offset="0" data-wow-delay="0.1s">
					<div class="section-heading text-center">
						<h2 class="h-bold">Ações contra depredações</h2>
						<div class="divider-header"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="map">

			<div id="gmap"
				style="max-width: 972px; height: 300px; border: 5px solid orange; margin: 0px auto;"></div>
			<div id="legend">
				Status:
				<div style="text-align: left;">
					<div>
						<img src="<c:url value="static/img/icones/vermelho.png"/>" style="max-width: 20px" />:Problema
					</div>
					<div>
						<img src="<c:url value="static/img/icones/verde.png"/>" style="max-width: 20px" />:Pronto
					</div>
					<div>
						<img src="<c:url value="static/img/icones/azul.png"/>" style="max-width: 20px" />:Resolvendo
					</div>
					<div>
						<img src="<c:url value="static/img/icones/cinza.png"/>" style="max-width: 20px" />:Analizando
					</div>
				</div>

			</div>
			<!--	<iframe src="https://maps.google.com/maps?f=q&amp;source=s_q&amp;hl=en&amp;geocode=&amp;q=Kuningan,+Jakarta+Capital+Region,+Indonesia&amp;aq=3&amp;oq=kuningan+&amp;sll=37.0625,-95.677068&amp;sspn=37.410045,86.572266&amp;ie=UTF8&amp;hq=&amp;hnear=Kuningan&amp;t=m&amp;z=14&amp;ll=-6.238824,106.830177&amp;output=embed">
							</iframe> -->
		</div>


	</div>

	</section>
	<!-- Section: services -->


	<div id="contact">

		<div class="container marginbot-50">
			<div class="row">
				<div class="col-lg-8 col-lg-offset-2">
					<div class="wow flipInY" data-wow-offset="0" data-wow-delay="0.1s">
						<div class="section-heading text-center">
							<h2 class="h-bold">Contatos</h2>
							<div class="divider-header"></div>
						</div>
					</div>
				</div>
			</div>
		</div>

			<div class="espacamento text-center">
						<p>
						<label>Bombeiros:<b>193</b></label>
						<br />
						<label>Polícia Civil:<b>197</b></label>	
						<br />
						<label>Disque Pichação:<b>153</b></label>
						<br /> 
						<label>Disque-denúncia:<b>181</b></label>	
						<br />
						<label>Brigada Militar:<b>190</b></label>
						<br />
						<label>Secretaria dos Direitos Humanos:<b>100</b></label>	
						<br />			
						</p>
			</div>		
	
		



<!--/ AQUI ESTA A DIV QUE O ALLAN CRIOU, UTILIZAR COMO REFERÊNCIA	
		<div class="container">
			<div class="row contact-wrap">
				<div class="status alert alert-success" style="display: none"></div>
				<form class="contact-form"
					name="form_cadastrar_pessoa_fisica"
					id="form_cadastrar_pessoa_fisica" 
					method="post">
						<div class="col-sm-5 col-sm-offset-1">
							<div class="form-group">
								<label>Nome</label> 
								<input type="text" name="nome" id="nome" class="form-control">
							</div>
							<div class="form-group">
								<label>Data de Nascimento</label> 
								<input type="text" name="data_nascim" id="data_nascim" class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
							</div>
							<div class="form-group">
								<label>CPF</label> 
								<input type="text" name="cpf" id="cpf" class="form-control">
							</div>
							<div class="form-group">
								<label>Telefone</label> <input type="text"
									name="telefone" id="telefone" class="form-control">
							</div>
							<div class="form-group">
								<label>Email</label> <input type="email" name="email" id="email"
									class="form-control" required="required">
							</div>
							
							<div class="form-group">
								<button type="button" name="botao_submit" id="botao_submit" 
										onclick="submit_com_form()" class="btn btn-primary btn-lg">Salvar</button>
							</div>
						</div>
				</form>
			</div>
		</div>
	</div>
-->



	<div id="divFundo"
		style="min-width: 100%; height: 100%; position: fixed; background-color: black; opacity: 0.75; z-index: 9999; display: none;"
		class="navbar navbar-fixed-top" role="navigation"></div>
	<form class="contact-form"
			name="form_cadastrar_pessoa_fisica"
			id="form_cadastrar_pessoa_fisica" 
			method="post">
		<div id="divCadastro"
			class="abseed_conteudo_sub abseed_Div_Casastro navbar navbar-fixed-top"
			style="max-width: 350px; width: 100%; min-height: 350px; position: fixed; top: 25%; margin-left: 10px; margin-top: 0px; margin: 0px auto; z-index: 9999; padding: 0px; border-radius: 5px; display: none;"
			role="navigation">
			<div class="abseed_conteudo_borda"
				style="text-align: center; line-height: 8px; font-size =14px; color: #ffffff; padding: 10px;">
				Cadastro de Voluntários</div>
			<div style="padding: 10px;">
				<!-- INCLUIR CAMPOS  -->
				<div class="col-sm-5 col-sm-offset-1">
					<div class="form-group">
						<label><b>Endereço:</b></label><br /> <label type="text"
							id="txtEndereco"></label>
					</div>
					<div class="form-group">
						<label><b>Motivo da Denúncia:</b></label><br />
						<div>
							<select class="cad_drop cad_borda " id="dpMotivo">
								<option value="Estátua">Estátua</option>
								<option value="Parede">Parede</option>
								<option value="Lago">Lago</option>
								<option value="Banco">Banco</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<textarea name="comentário" id="txtComentario"
							class="cad_comentario cad_borda" rows="5" cols="40"></textarea>
					</div>
	
				</div>
			</div>
			<div style="float: right; padding: 0px 10px 10px;">
				<!-- INCLUIR BOTOES  -->
				<button class="btn" id="btnFechar">Fechar</button>
				<button class="btn" id="btnSalvar">Salvar</button>
			</div>
		</div>
	</form>

	<script src="<c:url value="static/js/montagemMap.js"/>"></script>
	<script src="<c:url value="static/js/teste.js"/>"></script>
	

</body>
