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
    
    <!-- //////////// -->
    <link rel="stylesheet" href="<c:url value="static/css/EstiloSiteAdicional.css"/>">
    <!-- //////////// -->

    <title>Nossa Cidade Limpa</title>

    <!-- Bootstrap Core CSS - Uses Bootswatch Flatly Theme: http://bootswatch.com/flatly/ -->
	<link rel="stylesheet" href="<c:url value="static_startbootstrap-freelancer-1.0.3/css/bootstrap.min.css"/>">
	
    <!-- Custom CSS -->
    <link rel="stylesheet" href="<c:url value="static_startbootstrap-freelancer-1.0.3/css/freelancer.css"/>">

    <!-- Custom Fonts -->
    <link rel="stylesheet" href="<c:url value="static_startbootstrap-freelancer-1.0.3/font-awesome/css/font-awesome.min.css"/>">
    <link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet" type="text/css">
	
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body id="page-top" class="index">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#page-top">Nossa Cidade Limpa</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li class="hidden">
                        <a href="#page-top"></a>
                    </li>
                    <li >
                        <a href="#about">Sobre nós</a>
                    </li>
                    <li >
                        <a href="#depredacoes">Depredações</a>
                    </li>
                    <li >
                        <a href="#depoimentos">Ações</a>
                    </li>
                    <li>
                    	<a id="link_login_cadastro" href="javascript:abre_modal_login_cadastro()">Login ou Cadastro</a>
                    </li>
                    <c:choose>
                    	<c:when test="${usuarioLogado != null }">
                    		<li >
		                        <a id="link_logout" href="logout">Sair :(</a>
		                   </li>
                    	</c:when>
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
	        
        
    <!-- modal login -->
    <div class="portfolio-modal modal fade" id="modal_login_cadastro" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-content">
			<div class="col-lg-8 col-lg-offset-2">
				<div class="col-xs-12 ">
					<ul class="nav nav-tabs" data-toggle="tabs">
						<li id="li_login" class="active"><a href="javascript:abre_tab_login()"><i class="fa fa-check"></i> Login</a></li>
						<li id="li_PF"><a href="javascript:abre_tab_PF()" title="" data-original-title="Cadastro de Pessoa Física"><i class="fa fa-user"></i> Cadastro de Pessoa Física</a></li>
						<li id="li_PJ" ><a href="javascript:abre_tab_PJ()" data-toggle="tooltip" title="" data-original-title="Cadastro de Pessoa Jurídica"><i class="fa fa-bank"></i> Cadastro de Pessoa Jurídica</a></li>
					</ul>
				</div>
			</div>

			<div class="close-modal" data-dismiss="modal">
                <div class="lr">
                    <div class="rl">
                    </div>
                </div>
            </div>
            
             <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2">
                        <div class="modal-body">
                             <div class="tab-content">
                             	<div class="tab-pane active" id="modal_tab_login">
                             		<div class="row">
						                <div class="col-lg-8 col-lg-offset-2">
						                
						                    <form name="form_login" id="form_login" accept-charset="iso-8859-1,utf-8">
						                        
						                        <div class="row control-group">
						                            <div class="form-group col-xs-12 floating-label-form-group controls">
						                                <label>Usuário</label>
						                                <input type="text" class="form-control" placeholder="Usuário" id="username" name="username" >
						                                
						                            </div>
						                        </div>
						                        <div class="row control-group">
						                            <div class="form-group col-xs-12 floating-label-form-group controls">
						                                <label>Senha</label>
						                                <input type="password" class="form-control" placeholder="Senha" id="senha" name="senha" >
						                                
						                            </div>
						                        </div>
						                        
						                        <br>
						                        <div id="success"></div>
						                        <div class="row">
						                            <div class="form-group col-xs-12">
						                                <button type="button" onclick="submeter_form_login()" class="btn btn-success btn-lg">Enviar</button>
						                            </div>
						                        </div>
						                    </form>
						                </div>
						            </div>
                             	</div>
                             	<div class="tab-pane" id="modal_tab_cadastro_pf">
                             		<div class="row">
						                <div class="col-lg-8 col-lg-offset-2">
						                
						                    <form name="form_cadastrar_pessoa_fisica" id="form_cadastrar_pessoa_fisica" accept-charset="iso-8859-1,utf-8">
						                        <div class="row control-group">
						                            <div class="form-group col-xs-12 floating-label-form-group controls">
						                                <label>Nome</label>
						                                <input type="text" class="form-control" placeholder="Nome" id="nome" name="nome" >
						                                
						                            </div>
						                        </div>
						                        <div class="row control-group">
						                            <div class="form-group col-xs-12 floating-label-form-group controls">
						                                <label>Data de Nascimento</label>
						                                <input type="text" class="form-control" placeholder="Data de Nascimento" id="data_nascim" name="data_nascim" OnKeyUp="formatar('##/##/####', this);" maxlength="10">
						                                
						                            </div>
						                        </div>
						                        <div class="row control-group">
						                            <div class="form-group col-xs-12 floating-label-form-group controls">
						                                <label>CPF</label>
						                                <input type="text" class="form-control" placeholder="CPF" id="cpf" name="cpf" OnKeyUp="formatar('###.###.###-##', this);" maxlength="14" >
						                                
						                            </div>
						                        </div>
						                        <div class="row control-group">
						                            <div class="form-group col-xs-12 floating-label-form-group controls">
						                                <label>Telefone</label>
						                                <input type="text" class="form-control" placeholder="xx-xxxx-xxxx" id="telefone" name="telefone"  OnKeyUp="formatar('##-####-#####', this);" maxlength="13" >
						                                
						                            </div>
						                        </div>
						                        <div class="row control-group">
						                            <div class="form-group col-xs-12 floating-label-form-group controls">
						                                <label>Email</label>
						                                <input type="text" class="form-control" placeholder="Email" id="email" name="email" >
						                                
						                            </div>
						                        </div>
						                        <div class="row control-group">
						                            <div class="form-group col-xs-12 floating-label-form-group controls">
						                                <label>Usuário</label>
						                                <input type="text" class="form-control" placeholder="Usuário" id="username" name="username" >
						                                
						                            </div>
						                        </div>
						                        <div class="row control-group">
						                            <div class="form-group col-xs-12 floating-label-form-group controls">
						                                <label>Senha</label>
						                                <input type="password" class="form-control" placeholder="Senha" id="senha" name="senha" >
						                                
						                            </div>
						                        </div>
						                        
						                        <br>
						                        <div id="success"></div>
						                        <div class="row">
						                            <div class="form-group col-xs-12">
						                                <button type="button" onclick="javascript:submeter_form_cadastro_pessoa_fisica()" class="btn btn-success btn-lg">Enviar</button>
						                            </div>
						                        </div>
						                    </form>
						                </div>
						            </div>
                             	</div>
                             	
                             	<div class="tab-pane" id="modal_tab_cadastro_pj">
                             		<div class="row">
						                <div class="col-lg-8 col-lg-offset-2">
						                
						                    <form name="form_cadastrar_pessoa_juridica" id="form_cadastrar_pessoa_juridica" accept-charset="iso-8859-1,utf-8">
						                        <div class="row control-group">
						                            <div class="form-group col-xs-12 floating-label-form-group controls">
						                                <label>Nome</label>
						                                <input type="text" class="form-control" placeholder="Nome" id="nome" name="nome" >
						                                
						                            </div>
						                        </div>
						                        <div class="row control-group">
						                            <div class="form-group col-xs-12 floating-label-form-group controls">
						                                <label>CNPJ</label>
						                                <input type="text" class="form-control" placeholder="CNPJ" id="cnpj" name="cnpj" OnKeyUp="formatar('##.###.###/####-##', this);" maxlength="18" >
						                                
						                            </div>
						                        </div>
						                        <div class="row control-group">
						                            <div class="form-group col-xs-12 floating-label-form-group controls">
						                                <label>Telefone</label>
						                                <input type="text" class="form-control" placeholder="Telefone" id="telefone" name="telefone" OnKeyUp="formatar('##-####-#####', this);" maxlength="13">
						                                
						                            </div>
						                        </div>
						                        <div class="row control-group">
						                            <div class="form-group col-xs-12 floating-label-form-group controls">
						                                <label>Email</label>
						                                <input type="text" class="form-control" placeholder="Email" id="email" name="email" >
						                                
						                            </div>
						                        </div>
						                        <div class="row control-group">
						                            <div class="form-group col-xs-12 floating-label-form-group controls">
						                                <label>Endereço</label>
						                                <input type="text" class="form-control" placeholder="Endereco" id="endereco" name="endereco" >
						                                
						                            </div>
						                        </div>
						                        <div class="row control-group">
						                            <div class="form-group col-xs-12 floating-label-form-group controls">
						                                <label>Usuário</label>
						                                <input type="text" class="form-control" placeholder="Usuário" id="username" name="username" >
						                                
						                            </div>
						                        </div>
						                        <div class="row control-group">
						                            <div class="form-group col-xs-12 floating-label-form-group controls">
						                                <label>Senha</label>
						                                <input type="password" class="form-control" placeholder="Senha" id="senha" name="senha" >
						                                
						                            </div>
						                        </div>
						                        
						                        <br>
						                        <div id="success"></div>
						                        <div class="row">
						                            <div class="form-group col-xs-12">
						                                <button type="button" onclick="submeter_form_cadastro_pessoa_juridica()" class="btn btn-success btn-lg">Enviar</button>
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
        </div>
    </div>
     
    <!-- fim modal login -->
    
    <!-- Header -->
    <header>
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <img class="img-responsive" src="static_startbootstrap-freelancer-1.0.3/img/usina_gasometro_novo.png" alt="">
                    <div class="intro-text">
                        <span class="name">Nossa Cidade Limpa</span>
                        <hr class="star-light">
                        <span class="skills">Escolha e preserve um patrimônio público.</span>
                    </div>
                </div>
            </div>
        </div>
    </header>
    
    <!-- sobre nós -->
    <section  id="about">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2>Sobre Nós</h2>
                    <hr class="star-primary">
                </div>
            </div>
            <div class="row">
                <div class="col-lg-4 col-lg-offset-2">
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
					
               
                </div>
                <div class="col-lg-4">
                </div>
                
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
                <div class="col-lg-8 col-lg-offset-2">
                    <div class="container">
						<div class="map">
				
							<div id="gmap"
								style="max-width: 972px; height: 300px; border: 5px solid orange; margin: 0px auto;"></div>
							<div id="legend" style="background-color: rgba(128, 128, 128, 0.89)!important;" >
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
                </div>
            </div>
        </div>
    </section>

    <!-- Portfolio Grid Section -->
    <section  id="depoimentos">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2>Ações</h2>
                    <hr class="star-primary">
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4 portfolio-item">
                    <a href="#portfolioModal1" class="portfolio-link" data-toggle="modal">
                        <div class="caption">
                            <div class="caption-content">
                                <i class="fa fa-search-plus fa-3x"></i>
                            </div>
                        </div>
                        <img src="static/img/acoes/giuseppe_e_anita.jpg" class="img-responsive" alt="">
                    </a>
                </div>
                <div class="col-sm-4 portfolio-item">
                    <a href="#portfolioModal2" class="portfolio-link" data-toggle="modal">
                        <div class="caption">
                            <div class="caption-content">
                                <i class="fa fa-search-plus fa-3x"></i>
                            </div>
                        </div>
                     <img src="static/img/acoes/monumento_restaurado.jpg" class="img-responsive" alt="">
                    </a>
                </div>
                <div class="col-sm-4 portfolio-item">
                    <a href="#portfolioModal3" class="portfolio-link" data-toggle="modal">
                        <div class="caption">
                            <div class="caption-content">
                                <i class="fa fa-search-plus fa-3x"></i>
                            </div>
                        </div>
                        <img src="static/img/acoes/poa_limpa.jpg" class="img-responsive" alt="">
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
                        <p>Allan Moreira 
                        <br />
                        Giovanni Caprio
                        <br />
                        Renan Souza
                        <br />
                        Vinicius Azevedo
                        <br />
                        Willen Ávila
                        </p>
                    </div>
                    <div class="footer-col col-md-4">
                        <h3>Encontre-nos na Web</h3>
                        <ul class="list-inline">
                            <li>
                                <a href="#" class="btn-social btn-outline"><i class="fa fa-fw fa-facebook"></i></a>
                            </li>
                            <li>
                                <a href="#" class="btn-social btn-outline"><i class="fa fa-fw fa-google-plus"></i></a>
                            </li>
                            <li>
                                <a href="#" class="btn-social btn-outline"><i class="fa fa-fw fa-twitter"></i></a>
                            </li>
                            <li>
                                <a href="#" class="btn-social btn-outline"><i class="fa fa-fw fa-linkedin"></i></a>
                            </li>
                            <li>
                                <a href="#" class="btn-social btn-outline"><i class="fa fa-fw fa-dribbble"></i></a>
                            </li>
                        </ul>
                    </div>
                    <div class="footer-col col-md-4">
                        <h3>Origem Nossa Cidade Limpa</h3>
                        <p>Nosso projeto, surgiu da disciplina de Gerência de Projetos, lecionada na PUCRS.
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <div class="footer-below">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                       Gerência de Projetos 2015/2  PUCRS - Porto Alegre - RS.
                    </div>
                </div>
            </div>
        </div>
    </footer>

    <!-- Scroll to Top Button (Only visible on small and extra-small screen sizes) -->
    <div class="scroll-top page-scroll visible-xs visible-sm">
        <a class="btn btn-primary" href="#page-top">
            <i class="fa fa-chevron-up"></i>
        </a>
    </div>

    <!-- Portfolio Modals -->
    <div class="portfolio-modal modal fade" id="portfolioModal2" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-content">
            <div class="close-modal" data-dismiss="modal">
                <div class="lr">
                    <div class="rl">
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2">
                        <div class="modal-body">
                            <h2>Homenagem aos Mortos em Combate ao Comunismo</h2>
                            <hr class="star-primary">
                            <img src="static/img/acoes/monumento_restaurado.jpg" class="img-responsive img-centered" alt="">
                            <p>Conjunto de obras, recentemente foi depredado, graças a iniciativa da SINDUSCON, as obras foram restauradas.
                            <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i> Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="portfolio-modal modal fade" id="portfolioModal1" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-content">
            <div class="close-modal" data-dismiss="modal">
                <div class="lr">
                    <div class="rl">
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2">
                        <div class="modal-body">
                            <h2>Líderes da Revolução Farroupilha</h2>
                            <hr class="star-primary">
                            <img src="static/img/acoes/giuseppe_e_anita.jpg" class="img-responsive img-centered" alt="">
                           	<p>Imponente monumento histórico de Porto Alegre, inaururado em 1936, atualmente, apresenta-se em situações vergonhosas.</p>
								
                            <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i> Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="portfolio-modal modal fade" id="portfolioModal3" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-content">
            <div class="close-modal" data-dismiss="modal">
                <div class="lr">
                    <div class="rl">
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2">
                        <div class="modal-body">
                            <h2>Por uma POA limpa</h2>
                            <hr class="star-primary">
                            <img src="static/img/acoes/poa_limpa.jpg" class="img-responsive img-centered" alt="">
                          		<p>Policias ajudam no combate contra as depredações patrimoniais.</p>
								
                            <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i> Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="portfolio-modal modal fade" id="portfolioModal4" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-content">
            <div class="close-modal" data-dismiss="modal">
                <div class="lr">
                    <div class="rl">
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2">
                        <div class="modal-body">
                            <h2>Homenagem aos Mortos em Combate ao Comunismo</h2>
                            <hr class="star-primary">
                            <img src="static/img/acoes/monumento_restaurado.jpg" class="img-responsive img-centered" alt="">
                            <p>Conjunto de obras, recentemente foi depredado, graças a iniciativa da SINDUSCON, as obras foram restauradas. <a href="https://sellfy.com/p/8Q9P/jV3VZ/">Flat Icons</a>. On their website, you can download their free set with 16 icons, or you can purchase the entire set with 146 icons for only $12!</p>
                            <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i> Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="portfolio-modal modal fade" id="portfolioModal5" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-content">
            <div class="close-modal" data-dismiss="modal">
                <div class="lr">
                    <div class="rl">
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2">
                        <div class="modal-body">
                            <h2>Homenagem aos Mortos em Combate ao Comunismo</h2>
                            <hr class="star-primary">
                            <img src="static/img/acoes/giuseppe_e_anita.jpg" class="img-responsive img-centered" alt="">
                            <p>Conjunto de obras, recentemente foi depredado, graças a iniciativa da SINDUSCON, as obras foram restauradas. <a href="https://sellfy.com/p/8Q9P/jV3VZ/">Flat Icons</a>. On their website, you can download their free set with 16 icons, or you can purchase the entire set with 146 icons for only $12!</p>
                                 <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i> Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="portfolio-modal modal fade" id="portfolioModal6" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-content">
            <div class="close-modal" data-dismiss="modal">
                <div class="lr">
                    <div class="rl">
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2">
                        <div class="modal-body">
                            <h2>Homenagem aos Mortos em Combate ao Comunismo</h2>
                            <hr class="star-primary">
                            <img src="static/img/acoes/giuseppe_e_anita.jpg" class="img-responsive img-centered" alt="">
                            <p>Conjunto de obras, recentemente foi depredado, graças a iniciativa da SINDUSCON, as obras foram restauradas. <a href="https://sellfy.com/p/8Q9P/jV3VZ/">Flat Icons</a>. On their website, you can download their free set with 16 icons, or you can purchase the entire set with 146 icons for only $12!</p>
                            <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i> Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- jQuery -->
    <script src="<c:url value="static_startbootstrap-freelancer-1.0.3/js/jquery.js"/>"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="<c:url value="static_startbootstrap-freelancer-1.0.3/js/bootstrap.min.js"/>"></script>

    <!-- Plugin JavaScript -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
    <script src="<c:url value="static_startbootstrap-freelancer-1.0.3/js/classie.js"/>"></script>
    <script src="<c:url value="static_startbootstrap-freelancer-1.0.3/js/cbpAnimatedHeader.js"/>"></script>

    <!-- Contact Form JavaScript -->
    <script src="<c:url value="static_startbootstrap-freelancer-1.0.3/js/jqBootstrapValidation.js"/>"></script>
    <script src="<c:url value="static_startbootstrap-freelancer-1.0.3/js/contact_me.js"/>"></script>

    <!-- Custom Theme JavaScript -->
    <script src="<c:url value="static_startbootstrap-freelancer-1.0.3/js/freelancer.js"/>"></script>
    
    <!-- js do projeto -->
    <!--
    <link rel="stylesheet" href="<c:url value="static/css/bootstrap.min.css"/>" >
	<link rel="stylesheet" href="<c:url value="static/css/font-awesome.min.css"/>">
	<link rel="stylesheet" href="<c:url value="static/css/animate.min.css"/>" >
	<link rel="stylesheet" href="<c:url value="static/css/animate.css"/>"  />
	<link rel="stylesheet" href="<c:url value="static/css/style.css"/>" >
	<link rel="stylesheet" href="<c:url value="static/css/EstiloSiteAdicional.css"/>">
	-->
	<script src="http://maps.google.com/maps/api/js?sensor=false&libraries=geometry&v=3.7"> </script>
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
