<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link rel="shortcut icon"
	href="<c:url value="static_startbootstrap-freelancer-1.0.3/img/icone_limpeza.jpg" />">

<!-- //////////// -->
<link rel="stylesheet"
	href="<c:url value="static/css/EstiloSiteAdicional.css"/>">
<!-- //////////// -->

<title>Nossa Cidade Limpa</title>

<!-- Bootstrap Core CSS - Uses Bootswatch Flatly Theme: http://bootswatch.com/flatly/ -->
<link rel="stylesheet"
	href="<c:url value="static_startbootstrap-freelancer-1.0.3/css/bootstrap.min.css"/>">

<!-- Custom CSS -->
<link rel="stylesheet"
	href="<c:url value="static_startbootstrap-freelancer-1.0.3/css/freelancer.css"/>">

<!-- Custom Fonts -->
<link rel="stylesheet"
	href="<c:url value="static_startbootstrap-freelancer-1.0.3/font-awesome/css/font-awesome.min.css"/>">
<link href="http://fonts.googleapis.com/css?family=Montserrat:400,700"
	rel="stylesheet" type="text/css">
<link
	href="http://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic"
	rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body id="page-top" class="index">


	<div id="divFundoExtra"
		style="min-width: 100%; height: 100%; position: fixed; background-color: black; opacity: 0.95; z-index: 99999999; display: none;"
		class="navbar navbar-fixed-top" role="navigation"></div>
	<div id="gifLoader"
		style="top: 40%; left: 50%; position: fixed; z-index: 999999999; display: none;"
		class="navbar navbar-fixed-top" role="navigation">
		<img id="loading-image" src="static/img/icones/loader.gif"
			alt="Loading..." />
	</div>

	<!-- Navigation -->
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header page-scroll">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#page-top">Nossa Cidade Limpa</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<li class="hidden"><a href="#page-top"></a></li>
					<li><a href="#about">Sobre nós</a></li>
					<li><a href="#depredacoes">Depredações</a></li>
					<li><a href="#depoimentos">Ações</a></li>

					<!-- mantém a interface de login mesmo ao atualizar a página -->
					<c:choose>
						<c:when test="${usuarioLogado != null }">
							<c:choose>
								<c:when test="${usuarioLogado.PF == true }">
									<li id="li_login_cadastro"><a id="link_login_cadastro"
										href="javascript:abre_modal_editar_cadastro_pessoa_fisica()">${nomeUsuarioLogado }</a>
									</li>
								</c:when>
								<c:otherwise>
									<li id="li_login_cadastro"><a id="link_login_cadastro"
										href="javascript:abre_modal_editar_cadastro_pessoa_juridica()">${nomeUsuarioLogado }</a>
									</li>
								</c:otherwise>
							</c:choose>

							<li id="li_logout"><a id="link_logout"
								href="javascript:submeter_form_logout()">Sair :(</a></li>
						</c:when>
						<c:otherwise>
							<li id="li_login_cadastro"><a id="link_login_cadastro"
								href="javascript:abre_modal_login_cadastro()">Login ou
									Cadastro</a></li>
						</c:otherwise>
					</c:choose>

				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>



	<div id="divFundo"
		style="min-width: 100%; height: 100%; position: fixed; background-color: black; opacity: 0.75; z-index: 9999; display: none;"
		class="navbar navbar-fixed-top" role="navigation"></div>


	<div id="divDenuncia" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">

			<div class="modal-content">
				<div class="modal-header">
					<!-- <button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					-->
					<h4 class="modal-title" id="myModalLabel">Dados da Denúncia</h4>

				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
							<label><b><u>Endereço:</u></b></label><br /> <label type="text"
								id="txtEndDenuncia" class="estiloPrin"></label>
						</div>

						<div class="col-md-12">
							<label><b><u>Motivo da Denúncia:</u></b></label><br /> <label
								type="text" id="txtMotivoDenuncia" class="estiloPrin"></label> <br />
							<label><b><u>Informativo:</u></b></label><br /> <label
								type="text" id="txtDescricaoMark" class="estiloPrin"></label><br />

							<label><b><u>Beneficio:</u></b></label>
							<div class="form-group">

								<label type="text" id="txtBeneficioDenuncia" class="estiloPrin"
									style="display: none;"></label>
								<textarea id="txtBeneficiotext" name="txtBeneficiotext"
									maxlength="40" class="cad_comentario cad_borda" rows="5"
									cols="40" style="display: none; height: 45px"></textarea>

							</div>
						</div>

						<div class="col-md-12">
							<div style="text-align: center;">
								<div id="container_img" >
									<img id="txtImagemDenuncia" alt="" style="width: 70%;" />
									<div id="divAnuncio" style="display: none">
										<label><b><u>Faça upload da imagem após	concerto da denúncia. </u></b></label><br />
									</div>

								</div>
								<div id="likesDeslikes" style="display: none;">
									<label><b>Votação: Esta denúncia é verídica? &nbsp;<img
											id="imglikes" style="cursor: pointer;"
											src="static/img/icones/likes.png" alt=""> <img
											id="imgDeslikes" style="cursor: pointer;"
											src="static/img/icones/deslikes.png" alt="">
									</b></label><br />

								</div>
								<input type="file" name="caminho_imagem_uploadR"
								id="caminho_imagem_uploadR" class="btn btn-warning" src=""
								style="width: 50%;     margin: 0 auto;"></input>
								
							</div>
						</div>


						<div class="col-md-12">
							
						</div>


					</div>
				</div>


				<div class="modal-footer">
				&nbsp;
					<button type="button" class="btn btn-success pull-right"
						id="btnResolvido" style="width: 85px;">Finalizar</button>
					&nbsp;
					<button type="button" class="btn btn-primary pull-right"
						id="btnFecharInfoDenuncia" style="width: 75px;">Fechar</button>
					&nbsp;
					<button type="button" class="btn btn-success pull-right"
						id="btnSalvarCandidato" style="width: 110px;">Candidatar</button>
					&nbsp;
					<button type="button" class="btn btn-success pull-right"
						id="btnSalvarBeneficio" style="width: 132px;">Cad.
						Benefício</button>
					&nbsp;
				</div>
			</div>
		</div>
	</div>

	<!-- NOVA div cadastrar denúncia -->
	<div class="modal fade" id="divCadastro" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Cadastro de Denúncia</h4>
				</div>
				<div class="modal-body">
					<div class="row">

						<form method="POST" id="form_upload_imagem"
							enctype="multipart/form-data" action="upload_imagem">

							<input type="hidden" name="upload_imagem_name"></input> <input
								type="hidden" id="EnderecoEsc" name="EnderecoEsc"></input> <input
								type="hidden" id="longitudeEsc" name="longitudeEsc"></input> <input
								type="hidden" id="latitudeEsc" name="latitudeEsc"></input>

							<div class="form-group">
								<div class="col-md-12">
									<label><b>Endereço:</b></label><br /> <label id="txtEndereco"
										name="txtEndereco"></label>
								</div>
							</div>

							<div class="col-md-6">
								<label><b>Motivo da Denúncia:</b></label> <select id="dpMotivo"
									name="dpMotivo" class="form-control">
									<option value="Estátua">Estátua</option>
									<option value="Parede">Parede</option>
									<option value="Lago">Lago</option>
									<option value="Banco">Banco</option>
								</select> <label><b>Informativo:</b></label>
								<div class="form-group">
									<textarea id="txtComentario" name="txtComentario" rows="5""></textarea>
								</div>
							</div>

							<div class="col-md-6">
								<div id="container_img">
									<img id="div_imagem_upload" alt=""
										style="width: 100%; max-width: 280px;" />
								</div>
							</div>

							<div class="col-md-12"></div>

							<div class="col-md-12">
								<input type="file" name="caminho_imagem_upload"
									style="width: 100%; float: left;" id="caminho_imagem_upload"
									class="btn btn-success" src=""></input> &nbsp;

							</div>
						</form>


					</div>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary" id="btnFechar">Fechar</button>
					<!-- <button class="btn btn-success" id="btnSalvar" >Salvar</button> -->
					<button class="btn btn-success" id="btnSalvar">Salvar</button>
				</div>
			</div>
		</div>
	</div>
	<!-- fim div cadastrar denúncia -->

	<!-- NOVO modal editar PF -->
	<div class="modal fade" id="modal_editar_cadastro_pessoa_fisica"
		tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Editar Cadastro de
						Pessoa Física</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-lg-8 col-lg-offset-2">
							<div class="modal-body">
								<div class="tab-content">
									<div class="tab-pane active" id="modal_tab_editar_cadastro">
										<div class="row">
											<div class="col-lg-8 col-lg-offset-2">

												<form name="form_editar_cadastro_pessoa_fisica"
													id="form_editar_cadastro_pessoa_fisica"
													accept-charset="iso-8859-1,utf-8">
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Nome</label> <input type="text"
																class="form-control" placeholder="Nome"
																id="nome_editar_pf" name="nome_editar_pf"
																value="${pessoaFisica.nome }">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Data de Nascimento</label> <input type="text"
																class="form-control" placeholder="Data de Nascimento"
																id="data_nascim_editar_pf" name="data_nascim_editar_pf"
																OnKeyUp="formatar('##/##/####', this);" maxlength="10"
																value="${pessoaFisica.dataNascimento }">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>CPF</label> <input type="text"
																class="form-control" placeholder="CPF"
																id="cpf_editar_pf" name="cpf_editar_pf"
																OnKeyUp="formatar('###.###.###-##', this);"
																maxlength="14" value="${pessoaFisica.cpf }">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Telefone</label> <input type="text"
																class="form-control" placeholder="xx-xxxx-xxxx"
																id="telefone_editar_pf" name="telefone_editar_pf"
																OnKeyUp="formatar('##-####-#####', this);"
																maxlength="13" value="${pessoaFisica.telefone }">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Email</label> <input type="text"
																class="form-control" placeholder="Email"
																id="email_editar_pf" name="email_editar_pf"
																value="${pessoaFisica.email }">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Usuário</label> <input type="text"
																class="form-control" placeholder="Usuário"
																id="username_editar_pf" name="username_editar_pf"
																value="${pessoaFisica.username }">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Senha</label> <input type="password"
																class="form-control" placeholder="Senha"
																id="senha_editar_pf" name="senha_editar_pf"
																value="${pessoaFisica.senha }">

														</div>
													</div>

													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Senha</label> <input type="password"
																id="senha_editar_pf1" class="form-control"
																name="senha_editar_pf1" value="${pessoaFisica.senha }">

														</div>
													</div>


													<br>
													<div id="success"></div>
													<div class="row">
														<div class="form-group col-xs-12">
															
														</div>
													</div>
												</form>

											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
				<button type="button"		onclick="javascript:submeter_form_editar_cadastro_pessoa_fisica()"
																class="btn btn-success btn-lg">Enviar</button>
																&nbsp;
					<button type="button" class="btn btn-primary btn-lg"
						data-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>
	<!-- fim modal editar PF -->

	<!-- modal editar PF -->
	<div class="portfolio-modal modal fade"
		id="modal_editar_cadastro_pessoa_fisica" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-content">
			<div class="col-lg-8 col-lg-offset-2">
				<div class="col-xs-12 ">
					<ul class="nav nav-tabs" data-toggle="tabs">
						<li id="li_editar_dados_"><a href="javascript:void(0);"
							title="" data-original-title="Editar Cadastro"><i
								class="fa fa-pencil"></i> Editar Cadastro de Pessoa Física</a></li>
					</ul>
				</div>
			</div>

			<div class="close-modal" data-dismiss="modal">
				<div class="lr">
					<div class="rl"></div>
				</div>
			</div>

			<div class="container"></div>
		</div>
	</div>
	<!-- fim modal editar cadastro PF -->

	<!-- NOVO modal editar cadastro PJ -->
	<div class="modal fade" id="modal_editar_cadastro_pessoa_juridica"
		tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Editar Cadastro de
						Pessoa Jurídica</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-lg-8 col-lg-offset-2">
							<div class="modal-body">
								<div class="tab-content">
									<div class="tab-pane active">
										<div class="row">
											<div class="col-lg-8 col-lg-offset-2">
												<!-- valida qual form mostrar, se PF ou PJ -->

												<form name="form_editar_cadastro_pessoa_juridica"
													id="form_editar_cadastro_pessoa_juridica"
													accept-charset="iso-8859-1,utf-8">
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Nome</label> <input type="text"
																class="form-control" placeholder="Nome"
																id="nome_editar_pj" name="nome_editar_pj"
																value="${pessoaJuridica.nome }">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>CNPJ</label> <input type="text"
																class="form-control" placeholder="CNPJ"
																id="cnpj_editar_pj" name="cnpj_editar_pj"
																OnKeyUp="formatar('##.###.###/####-##', this);"
																maxlength="18" value="${pessoaJuridica.cnpj }">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Telefone</label> <input type="text"
																class="form-control" placeholder="Telefone"
																id="telefone_editar_pj" name="telefone_editar_pj"
																OnKeyUp="formatar('##-####-#####', this);"
																maxlength="13" value="${pessoaJuridica.telefone }">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Email</label> <input type="text"
																class="form-control" placeholder="Email"
																id="email_editar_pj" name="email_editar_pj"
																value="${pessoaJuridica.email }">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Endereço</label> <input type="text"
																class="form-control" placeholder="Endereco"
																id="endereco_editar_pj" name="endereco_editar_pj"
																value="${pessoaJuridica.endereco }">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Usuário</label> <input type="text"
																class="form-control" placeholder="Usuário"
																id="username_editar_pj" name="username_editar_pj"
																value="${pessoaJuridica.username }">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Senha</label> <input type="password"
																class="form-control" placeholder="Senha"
																id="senha_editar_pj" name="senha_editar_pj"
																value="${pessoaJuridica.senha }">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Senha</label> <input type="password"
																class="form-control" placeholder="Senha"
																id="senha_editar_pj1" name="senha_editar_pj1"
																value="${pessoaJuridica.senha }">

														</div>
													</div>


													<br>
													<div id="success"></div>
													<div class="row">
														<div class="form-group col-xs-12">
															
														</div>
													</div>
												</form>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
				<button type="button"
																onclick="submeter_form_editar_cadastro_pessoa_juridico()"
																class="btn btn-success btn-lg">Enviar</button>
																&nbsp;
					<button type="button" class="btn btn-primary btn-lg"
						data-dismiss="modal">Fechar</button>
								&nbsp;
				</div>
			</div>
		</div>
	</div>
	<!-- NOVO fim modal editar cadastro PJ -->

	<!-- modal login -->
	<div class="modal fade" id="modal_login_cadastro" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<ul class="nav nav-tabs" data-toggle="tabs">
						<li id="li_login" class="active"><a
							href="javascript:abre_tab_login()"><i class="fa fa-check"></i>
								Login</a></li>
						<li id="li_PF"><a href="javascript:abre_tab_PF()" title=""
							data-original-title="Cadastro de Pessoa Física"><i
								class="fa fa-user"></i> Cadastro de Pessoa Física</a></li>
						<li id="li_PJ"><a href="javascript:abre_tab_PJ()"
							data-toggle="tooltip" title=""
							data-original-title="Cadastro de Pessoa Jurídica"><i
								class="fa fa-bank"></i> Cadastro de Pessoa Jurídica</a></li>
					</ul>
				</div>

				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
							<div class="modal-body">
								<div class="tab-content">
									<div class="tab-pane active" id="modal_tab_login">
										<div class="row">

											<form name="form_login" id="form_login"
												accept-charset="iso-8859-1,utf-8">

												<div class="row control-group">
													<div
														class="form-group col-xs-12 floating-label-form-group controls">
														<label>Usuário</label> <input type="text"
															class="form-control" placeholder="Usuário" id="username"
															name="username">

													</div>
												</div>
												<div class="row control-group">
													<div
														class="form-group col-xs-12 floating-label-form-group controls">
														<label>Senha</label> <input type="password"
															class="form-control" placeholder="Senha" id="senha"
															name="senha">

													</div>
												</div>

												<br>
												<div id="success"></div>
												<div class="row">
													<div class="form-group col-xs-12">
														<button type="button" onclick="submeter_form_login()"
															class="btn btn-success btn-lg">Enviar</button>
													</div>
												</div>
											</form>
										</div>
									</div>
									<div class="tab-pane" id="modal_tab_cadastro_pf">
										<div class="row">
											<div class="col-md-12">

												<form name="form_cadastrar_pessoa_fisica"
													id="form_cadastrar_pessoa_fisica"
													accept-charset="iso-8859-1,utf-8">
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Nome</label> <input type="text"
																class="form-control" placeholder="Nome" id="nome"
																name="nome">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Data de Nascimento</label> <input type="text"
																class="form-control" placeholder="Data de Nascimento"
																id="data_nascim" name="data_nascim"
																OnKeyUp="formatar('##/##/####', this);" maxlength="10">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>CPF</label> <input type="text"
																class="form-control" placeholder="CPF" id="cpf"
																name="cpf" OnKeyUp="formatar('###.###.###-##', this);"
																maxlength="14">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Telefone</label> <input type="text"
																class="form-control" placeholder="Telefone"
																id="telefone" name="telefone"
																OnKeyUp="formatar('##-####-#####', this);"
																maxlength="13">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Email</label> <input type="text"
																class="form-control" placeholder="Email" id="email"
																name="email">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Usuário</label> <input type="text"
																class="form-control" placeholder="Usuário" id="username"
																name="username">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Senha</label> <input type="password"
																class="form-control" placeholder="Senha" id="senha"
																name="senha">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Senha</label> <input type="password"
																class="form-control" placeholder="Senha" id="senha1"
																name="senha1">

														</div>
													</div>

													<br>
													<div id="success"></div>
													<div class="row">
														<div class="form-group col-xs-12">
															<button type="button"
																onclick="javascript:submeter_form_cadastro_pessoa_fisica()"
																class="btn btn-success btn-lg">Enviar</button>
														</div>
													</div>
												</form>
											</div>
										</div>
									</div>

									<div class="tab-pane" id="modal_tab_cadastro_pj">
										<div class="row">
											<div class="col-md-12">

												<form name="form_cadastrar_pessoa_juridica"
													id="form_cadastrar_pessoa_juridica"
													accept-charset="iso-8859-1,utf-8">
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Nome</label> <input type="text"
																class="form-control" placeholder="Nome" id="nome"
																name="nome">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>CNPJ</label> <input type="text"
																class="form-control" placeholder="CNPJ" id="cnpj"
																name="cnpj"
																OnKeyUp="formatar('##.###.###/####-##', this);"
																maxlength="18">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Telefone</label> <input type="text"
																class="form-control" placeholder="Telefone"
																id="telefone" name="telefone"
																OnKeyUp="formatar('##-####-#####', this);"
																maxlength="13">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Email</label> <input type="text"
																class="form-control" placeholder="Email" id="email"
																name="email">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Endereço</label> <input type="text"
																class="form-control" placeholder="Endereco"
																id="endereco" name="endereco">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Usuário</label> <input type="text"
																class="form-control" placeholder="Usuário" id="username"
																name="username">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Senha</label> <input type="password"
																class="form-control" placeholder="Senha" id="senha"
																name="senha">

														</div>
													</div>
													<div class="row control-group">
														<div
															class="form-group col-xs-12 floating-label-form-group controls">
															<label>Senha</label> <input type="password"
																class="form-control" placeholder="Senha" id="senha1"
																name="senha1">

														</div>
													</div>
													<br>
													<div id="success"></div>
													<div class="row">
														<div class="form-group col-xs-12">
															<button type="button"
																onclick="submeter_form_cadastro_pessoa_juridica()"
																class="btn btn-success btn-lg">Enviar</button>
														</div>
													</div>
												</form>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-primary btn-lg"
						data-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>
	<!-- fim modal login -->

	<!-- Header -->
	<header>
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<img class="img-responsive"
						src="static_startbootstrap-freelancer-1.0.3/img/usina_gasometro_novo.png"
						alt="">
					<div class="intro-text">
						<span class="name">Nossa Cidade Limpa</span>
						<hr class="star-light">
						<span class="skills">Escolha e preserve um patrimônio
							público.</span>
					</div>
				</div>
			</div>
		</div>

	</header>

	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Modal title</h4>
				</div>
				<div class="modal-body">...</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary">Save changes</button>
				</div>
			</div>
		</div>
	</div>

	<!-- sobre nós -->
	<section id="about">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 text-center">
					<h2>Sobre Nós</h2>
					<hr class="star-primary">
				</div>
			</div>
			<div class="row">
				<div class="col-lg-4 col-lg-md-4">
					<p class="refTexto">Este projeto está direcionado aos
						moradores, turistas e órgãos públicos que estão diretamente
						envolvidos, situações de depredações do patrimônio público, além
						de te local com informações e contribuições sobre os problemas que
						as pessoas enfrentam na cidade, este sistema disponibilizará aos
						usuários consultar e informar estes problemas que denigrem e afeta
						a imagem da cidade.</p>

					<p class="refTexto">Através do sistemas os moradores da cidade
						poderá organizar-se em grupos, para concertar limpar o patrimônio
						público, também ajudará os cidadãos a terem um contato direto com
						o os órgãos públicos, que por sua vez terão maior agilidade com a
						visualização de mapa dos problemas de depredações da cidade.</p>

					<p class="refTexto">Contrário do que atualmente acontece, em
						que as prefeituras não conseguem atender a demanda de limpezas. As
						pessoas poderão "adotar" por tempo determinado a manutenção do
						patrimônio, a fim de usufrui de seus benefício. Logo contamos com
						a sua colaboração para melhor desenvolvimento e manutenção de
						nossa cidade.</p>


				</div>
				<div class="col-lg-4"></div>

			</div>
		</div>
	</section>
	<!-- sobre nós -->

	<!-- Contact Section -->
	<section class="success" id="depredacoes">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 text-center">
					<h2>Depredações</h2>
					<hr class="star-light">
				</div>
			</div>

			<div class="row">

				<div class=" text-center">
					<p>
						<img id="setagif" src="static/img/icones/right_arrow.png">
						&nbsp; Clique no mapa, abaixo, para registrar a denúncia de
						depredação.
					</p>

				</div>




				<div class="col-lg-8 col-lg-offset-2">

					<div class="container">
						<div class="map">

							<div id="gmap"
								style="max-width: 972px; height: 300px; border: 5px solid #2c3e50; margin: 0px auto;"></div>

							<!-- 	<div id="legend"
								style="background-color: rgba(128, 128, 128, 0.89) !important;">
								Status:
								<div style="text-align: left;">
									<div>
										<img src="<c:url value="static/img/icones/vermelho.png"/>"
											style="max-width: 20px" />:Problema
									</div>
									<div>
										<img src="<c:url value="static/img/icones/verde.png"/>"
											style="max-width: 20px" />:Pronto
									</div>
									<div>
										<img src="<c:url value="static/img/icones/azul.png"/>"
											style="max-width: 20px" />:Resolvendo
									</div>
									<div>
										<img src="<c:url value="static/img/icones/cinza.png"/>"
											style="max-width: 20px" />:Candidatar
									</div>
								</div>
 -->
						</div>
						<!--	<iframe src="https://maps.google.com/maps?f=q&amp;source=s_q&amp;hl=en&amp;geocode=&amp;q=Kuningan,+Jakarta+Capital+Region,+Indonesia&amp;aq=3&amp;oq=kuningan+&amp;sll=37.0625,-95.677068&amp;sspn=37.410045,86.572266&amp;ie=UTF8&amp;hq=&amp;hnear=Kuningan&amp;t=m&amp;z=14&amp;ll=-6.238824,106.830177&amp;output=embed">
										</iframe> -->

						<div
							style="width: 100%; max-width: 972px; background: #2c3e50; text-align: center; margin: 0 auto;">
							<img src="<c:url value="static/img/icones/vermelho.png"/>"
								style="max-width: 20px" />:Problema &nbsp; <img
								src="<c:url value="static/img/icones/verde.png"/>"
								style="max-width: 20px" />:Pronto &nbsp; <img
								src="<c:url value="static/img/icones/azul.png"/>"
								style="max-width: 20px" />:Resolvendo &nbsp; <img
								src="<c:url value="static/img/icones/cinza.png"/>"
								style="max-width: 20px" />:Candidatar &nbsp;

						</div>
					</div>



				</div>
			</div>
		</div>
	</div>
	</section>

	<!-- Portfolio Grid Section -->
	<section id="depoimentos">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 text-center">
					<h2>Ações</h2>
					<hr class="star-primary">
				</div>
			</div>
			<div class="row">
				<div class="col-sm-4 portfolio-item">
					<a href="#portfolioModal1" class="portfolio-link"
						data-toggle="modal">
						<div class="caption">
							<div class="caption-content">
								<i class="fa fa-search-plus fa-3x"></i>
							</div>
						</div> <img src="static/img/acoes/giuseppe_e_anita.jpg"
						class="img-responsive" alt="">
					</a>
				</div>
				<div class="col-sm-4 portfolio-item">
					<a href="#portfolioModal2" class="portfolio-link"
						data-toggle="modal">
						<div class="caption">
							<div class="caption-content">
								<i class="fa fa-search-plus fa-3x"></i>
							</div>
						</div> <img src="static/img/acoes/monumento_restaurado.jpg"
						class="img-responsive" alt="">
					</a>
				</div>
				<div class="col-sm-4 portfolio-item">
					<a href="#portfolioModal3" class="portfolio-link"
						data-toggle="modal">
						<div class="caption">
							<div class="caption-content">
								<i class="fa fa-search-plus fa-3x"></i>
							</div>
						</div> <img src="static/img/acoes/poa_limpa.jpg" class="img-responsive"
						alt="">
					</a>
				</div>
			</div>
		</div>
	</section>



	<!-- Footer -->
	<footer class="text-center">
		<div class="footer-above">
			<div class="container">
				<div class="row">
					<div class="footer-col col-md-4">
						<h3>Desenvolvedores</h3>
						<p>
							Allan Moreira <br /> Giovanni Caprio <br /> Renan Souza <br />
							Vinicius Azevedo <br /> Willen Ávila
						</p>
					</div>
					<div class="footer-col col-md-4">
						<h3>Encontre-nos na Web</h3>
						<ul class="list-inline">
							<li><a href="#" class="btn-social btn-outline"><i
									class="fa fa-fw fa-facebook"></i></a></li>
							<li><a href="#" class="btn-social btn-outline"><i
									class="fa fa-fw fa-google-plus"></i></a></li>
							<li><a href="#" class="btn-social btn-outline"><i
									class="fa fa-fw fa-twitter"></i></a></li>
							<li><a href="#" class="btn-social btn-outline"><i
									class="fa fa-fw fa-linkedin"></i></a></li>
							<li><a href="#" class="btn-social btn-outline"><i
									class="fa fa-fw fa-dribbble"></i></a></li>
						</ul>
					</div>
					<div class="footer-col col-md-4">
						<h3>Origem do projeto</h3>
						<p>O trabalho tem como objetivo o aprendizado sobre
							metodologias, técnicas e ferramentas de gerenciamento de projetos
							de software, tais como planejamento, execução, acompanhamento,
							controle e encerramento de um projeto de software.</p>
					</div>
				</div>
			</div>
		</div>
		<div class="footer-below">
			<div class="container">
				<div class="row">
					<div class="col-lg-12">Gerência de Projetos de Software
						2015/2 PUCRS - Porto Alegre - RS.</div>
				</div>
			</div>
		</div>
	</footer>

	<!-- Scroll to Top Button (Only visible on small and extra-small screen sizes) -->
	<div class="scroll-top page-scroll visible-xs visible-sm">
		<a class="btn btn-primary" href="#page-top"> <i
			class="fa fa-chevron-up"></i>
		</a>
	</div>

	<!-- Portfolio Modals -->
	<div class="portfolio-modal modal fade" id="portfolioModal2"
		tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-content">
			<div class="close-modal" data-dismiss="modal">
				<div class="lr">
					<div class="rl"></div>
				</div>
			</div>
			<div class="container">
				<div class="row">
					<div class="col-lg-8 col-lg-offset-2">
						<div class="modal-body">
							<h2>Homenagem aos Mortos em Combate ao Comunismo</h2>
							<hr class="star-primary">
							<img src="static/img/acoes/monumento_restaurado.jpg"
								class="img-responsive img-centered" alt="">
							<p>
								Conjunto de obras, recentemente foi depredado, graças a
								iniciativa da SINDUSCON, as obras foram restauradas.
								<button type="button" class="btn btn-default"
									data-dismiss="modal">
									<i class="fa fa-times"></i> Close
								</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="portfolio-modal modal fade" id="portfolioModal1"
		tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-content">
			<div class="close-modal" data-dismiss="modal">
				<div class="lr">
					<div class="rl"></div>
				</div>
			</div>
			<div class="container">
				<div class="row">
					<div class="col-lg-8 col-lg-offset-2">
						<div class="modal-body">
							<h2>Líderes da Revolução Farroupilha</h2>
							<hr class="star-primary">
							<img src="static/img/acoes/giuseppe_e_anita.jpg"
								class="img-responsive img-centered" alt="">
							<p>Imponente monumento histórico de Porto Alegre, inaururado
								em 1936, atualmente, apresenta-se em situações vergonhosas.</p>

							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<i class="fa fa-times"></i> Close
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="portfolio-modal modal fade" id="portfolioModal3"
		tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-content">
			<div class="close-modal" data-dismiss="modal">
				<div class="lr">
					<div class="rl"></div>
				</div>
			</div>
			<div class="container">
				<div class="row">
					<div class="col-lg-8 col-lg-offset-2">
						<div class="modal-body">
							<h2>Por uma POA limpa</h2>
							<hr class="star-primary">
							<img src="static/img/acoes/poa_limpa.jpg"
								class="img-responsive img-centered" alt="">
							<p>Policias ajudam no combate contra as depredações
								patrimoniais.</p>

							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<i class="fa fa-times"></i> Close
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="portfolio-modal modal fade" id="portfolioModal4"
		tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-content">
			<div class="close-modal" data-dismiss="modal">
				<div class="lr">
					<div class="rl"></div>
				</div>
			</div>
			<div class="container">
				<div class="row">
					<div class="col-lg-8 col-lg-offset-2">
						<div class="modal-body">
							<h2>Homenagem aos Mortos em Combate ao Comunismo</h2>
							<hr class="star-primary">
							<img src="static/img/acoes/monumento_restaurado.jpg"
								class="img-responsive img-centered" alt="">
							<p>
								Conjunto de obras, recentemente foi depredado, graças a
								iniciativa da SINDUSCON, as obras foram restauradas. <a
									href="https://sellfy.com/p/8Q9P/jV3VZ/">Flat Icons</a>. On
								their website, you can download their free set with 16 icons, or
								you can purchase the entire set with 146 icons for only $12!
							</p>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<i class="fa fa-times"></i> Close
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="portfolio-modal modal fade" id="portfolioModal5"
		tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-content">
			<div class="close-modal" data-dismiss="modal">
				<div class="lr">
					<div class="rl"></div>
				</div>
			</div>
			<div class="container">
				<div class="row">
					<div class="col-lg-8 col-lg-offset-2">
						<div class="modal-body">
							<h2>Homenagem aos Mortos em Combate ao Comunismo</h2>
							<hr class="star-primary">
							<img src="static/img/acoes/giuseppe_e_anita.jpg"
								class="img-responsive img-centered" alt="">
							<p>
								Conjunto de obras, recentemente foi depredado, graças a
								iniciativa da SINDUSCON, as obras foram restauradas. <a
									href="https://sellfy.com/p/8Q9P/jV3VZ/">Flat Icons</a>. On
								their website, you can download their free set with 16 icons, or
								you can purchase the entire set with 146 icons for only $12!
							</p>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<i class="fa fa-times"></i> Close
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="portfolio-modal modal fade" id="portfolioModal6"
		tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-content">
			<div class="close-modal" data-dismiss="modal">
				<div class="lr">
					<div class="rl"></div>
				</div>
			</div>
			<div class="container">
				<div class="row">
					<div class="col-lg-8 col-lg-offset-2">
						<div class="modal-body">
							<h2>Homenagem aos Mortos em Combate ao Comunismo</h2>
							<hr class="star-primary">
							<img src="static/img/acoes/giuseppe_e_anita.jpg"
								class="img-responsive img-centered" alt="">
							<p>
								Conjunto de obras, recentemente foi depredado, graças a
								iniciativa da SINDUSCON, as obras foram restauradas. <a
									href="https://sellfy.com/p/8Q9P/jV3VZ/">Flat Icons</a>. On
								their website, you can download their free set with 16 icons, or
								you can purchase the entire set with 146 icons for only $12!
							</p>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<i class="fa fa-times"></i> Close
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- jQuery -->
	<script
		src="<c:url value="static_startbootstrap-freelancer-1.0.3/js/jquery.js"/>"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="<c:url value="static_startbootstrap-freelancer-1.0.3/js/bootstrap.min.js"/>"></script>

	<!-- Plugin JavaScript -->
	<script
		src="http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
	<script
		src="<c:url value="static_startbootstrap-freelancer-1.0.3/js/classie.js"/>"></script>
	<script
		src="<c:url value="static_startbootstrap-freelancer-1.0.3/js/cbpAnimatedHeader.js"/>"></script>

	<!-- Contact Form JavaScript -->
	<script
		src="<c:url value="static_startbootstrap-freelancer-1.0.3/js/jqBootstrapValidation.js"/>"></script>
	<%-- <script src="<c:url value="static_startbootstrap-freelancer-1.0.3/js/contact_me.js"/>"></script> --%>

	<!-- Custom Theme JavaScript -->
	<script
		src="<c:url value="static_startbootstrap-freelancer-1.0.3/js/freelancer.js"/>"></script>

	<!-- js do projeto -->
	<!--
    <link rel="stylesheet" href="<c:url value="static/css/bootstrap.min.css"/>" >
	<link rel="stylesheet" href="<c:url value="static/css/font-awesome.min.css"/>">
	<link rel="stylesheet" href="<c:url value="static/css/animate.min.css"/>" >
	<link rel="stylesheet" href="<c:url value="static/css/animate.css"/>"  />
	<link rel="stylesheet" href="<c:url value="static/css/style.css"/>" >
	<link rel="stylesheet" href="<c:url value="static/css/EstiloSiteAdicional.css"/>">
	-->
	<script
		src="http://maps.google.com/maps/api/js?sensor=false&libraries=geometry&v=3.7">
		
	</script>
	<script src="<c:url value="static/js/maplace-0.1.3.min.js"/>"></script>
	<script src="<c:url value="static/js/maplace-0.1.3.js"/>"></script>
	<script src="<c:url value="static/js/wow.min.js"/>"></script>
	<script src="<c:url value="static/js/jquery.easing.min.js"/>"></script>
	<script src="<c:url value="static/js/functions.js"/>"></script>
	<script src="<c:url value="static/js/JSON_DADOSPOA.js"/>"></script>
	<script src="<c:url value="static/js/AdicionaisJS.js"/>"></script>
	<script src="<c:url value="static/js/submit_sem_form.js"/>"></script>
	<script src="<c:url value="static/js/submit_com_form.js"/>"></script>
	<script src="<c:url value="static/js/montagemMap.js"/>"></script>
	<script src="<c:url value="static/js/bootstrap_growl.js"/>"></script>

</body>
</html>
