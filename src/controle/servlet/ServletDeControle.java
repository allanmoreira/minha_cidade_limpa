package controle.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controle.bancoDados.*;
import controle.conversaoDados.*;
import modelos.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@MultipartConfig
public class ServletDeControle {
	
//	private static final long serialVersionUID = 1298516959968350334L;
	@Autowired
    ServletContext context; 
	/*
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Entra no INIT");
		super.init(config); //added this line then it worked
		System.out.println(config.getServletContext());
	}
	*/
	/**
	 * Monta a p�gina home. Sua url � vazia para que somente o endere�o do
	 * site apare�a
	 * 
	 * @param request
	 *            - Requisi��o do usu�rio
	 * @param response
	 *            - A resposta do servidor para o usu�rio
	 * @param session
	 *            - verifica se o usu�rio est� logado (ainda n�o
	 *            implementado)
	 * @return
	 */
	

	@RequestMapping("index")
	public ModelAndView homeNovoTemplate(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		return mv;
	}
	
	@RequestMapping("lista_marcacoes_cadastradas")
	public void ListaMarcacoesCadastradas(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		BancoDados bancoDados = new BancoDados();
		ArrayList<MarcacaoDepredacao> listaMarcacoesCadastradas = new ArrayList<MarcacaoDepredacao>();
		boolean isValid = false;
		
		try {
			bancoDados.conectarAoBco();
			listaMarcacoesCadastradas = bancoDados.listaMarcacoesCadastradas();
			isValid = true;
			bancoDados.encerrarConexao();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		map.put("isValid", isValid);
		map.put("listaMarcacoesCadastradas", listaMarcacoesCadastradas);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));
	}

	@RequestMapping("login")
	public void login(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		BancoDados bancoDados = new BancoDados();
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isValid = false;
		boolean dadosCadastroInvalidos = false;
		boolean usernameInvalido = false;
		PessoaFisica pessoaFisica = null;
		PessoaJuridica pessoaJuridica = null;
		String nomeUsuarioLogado = null;
		String dataNascimento = "";
		
		Login login = new Login();

		String username = request.getParameter("username");
		String senha = request.getParameter("senha");
		login.setUsername(username);
		login.setSenha(senha);

		try {
			// valida se os campos nao estao vazios
			if (username != null && senha != null) {
				bancoDados.conectarAoBco();
				boolean autorizaLogar = bancoDados.login(login);
				login = bancoDados.dadosUsuarioLogado(login);
							
					if(login.isPF()){
					
						pessoaFisica = bancoDados.buscarPessoaFisica(login.getIdLogin());
						dataNascimento = pessoaFisica.getDataNascimentoString();
						nomeUsuarioLogado = pessoaFisica.getNome();
						// para caso a página seja atualizada
						session.setAttribute("pessoaFisica", pessoaFisica);
					}
					else{
		
						pessoaJuridica = bancoDados.buscarPessoaJuridica(login.getIdLogin());
						nomeUsuarioLogado = pessoaJuridica.getNome();

						// para caso a página seja atualizada
						session.setAttribute("pessoaJuridica", pessoaJuridica);
					}
					
			bancoDados.encerrarConexao();
			session.setAttribute("usuarioLogado", login);
			// utilizado para peencher o campo caso a pagina seja atualizada
			session.setAttribute("nomeUsuarioLogado", nomeUsuarioLogado);
			isValid = true;
				
			}

		} catch (ClassNotFoundException e) {
			// Erro ao concetar ao banco de dados
			// Levanta p�gina Erro 500 (n�o existe)
			System.out.println(e.getMessage());

		} catch (SQLException e) {
			// Erro ao executar a instru��o
			// Levanta p�gina Erro 500 (n�o existe)
			System.out.println(e.getMessage());
		}
		
		map.put("dtNascimento", dataNascimento);
		map.put("pessoaJuridica", pessoaJuridica);
		map.put("pessoaFisica", pessoaFisica);
		map.put("isValid", isValid);
		map.put("login", login);
		map.put("dadosInvalidos", dadosCadastroInvalidos);
		map.put("usernameInvalido", usernameInvalido);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));
	}
	
	@RequestMapping("logout")
	public void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isValid = true;
		
		// termina com a sess�o
		session.invalidate();
		
		map.put("isValid", isValid);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));
	}

	/**
	 * O m�todo utiliza um hash para adicionar os objetos que ir�o retornar
	 * para a p�gina. Os par�metros do hash s�o uma string com o nome que
	 * o json reconhecer� o objeto = o objeto. A biblioteca Gson, do google,
	 * converte o Hash em json.
	 * 
	 * O boolean pf_ou_pj recebe TRUE se PF ou FALSE se PJ
	 * 
	 * @param request
	 *            - Requisi��o do usu�rio
	 * @param response
	 *            - A resposta do servidor para o usu�rio
	 * @throws Exception
	 */
	@RequestMapping("cadastrar_pessoa_fisica")
	public void cadastrarPessoaFisica(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		BancoDados bancoDados = new BancoDados();
		Map<String, Object> map = new HashMap<String, Object>();
		PessoaFisica pessoaFisica = new PessoaFisica();
		Login login = new Login();
		Data data = new Data();
		boolean isValid = false;
		boolean isPF = true;
		boolean dadosCadastroInvalidos = false;
		boolean usernameInvalido = false;
		String nomeUsuarioLogado = null;

		String nome = request.getParameter("nome");
		String cpf = request.getParameter("cpf");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String senha = request.getParameter("senha");
		String dataNascimento = request.getParameter("data_nascim");
		String telefone = request.getParameter("telefone");

		// valida se os campos n�o est�o vazios
		if (!nome.equals("") && !cpf.equals("") && !email.equals("")
				&& !username.equals("") && !senha.equals("")
				&& !dataNascimento.equals("")) {
			pessoaFisica.setNome(nome);
			pessoaFisica.setCpf(cpf);
			pessoaFisica.setEmail(email);
			pessoaFisica.setDataNascimento(data.converteStringParaData(dataNascimento));
			pessoaFisica.setUsername(username);
			pessoaFisica.setSenha(senha);
			pessoaFisica.setPF(isPF);

			// campo n�o obrigat�rio
			if (telefone.equals("")) {
				pessoaFisica.setTelefone(null);
			} else {
				pessoaFisica.setTelefone(telefone);
			}

			try {
				bancoDados.conectarAoBco();

				boolean usernameNaoCadastrado = bancoDados.usernameNaoCadastrado(username);

				if (usernameNaoCadastrado) {
					int idLogin = bancoDados.geraLoginUsuario(pessoaFisica.getUsername(), pessoaFisica.getSenha(), pessoaFisica.isPF());
					pessoaFisica.setIdLogin(idLogin);
					bancoDados.cadastrarPessoaFisica(pessoaFisica);

					isValid = true;
					// Pega os dados do usuario logado. Ta meio gambiarra, pode ser melhor.
		
					login.setUsername(pessoaFisica.getUsername());
					login.setSenha(pessoaFisica.getSenha());
					login = bancoDados.dadosUsuarioLogado(login);
					
					pessoaFisica = bancoDados.buscarPessoaFisica(idLogin);
					nomeUsuarioLogado = pessoaFisica.getNome();
					
					// para caso a página seja atualizada
					session.setAttribute("pessoaFisica", pessoaFisica);
					// loga o usuario
					session.setAttribute("usuarioLogado", login);
					// utilizado para peencher o campo caso a pagina seja atualizada
					session.setAttribute("nomeUsuarioLogado", nomeUsuarioLogado);
				} else {
					usernameInvalido = true;
				}
				bancoDados.encerrarConexao();

			} catch (ClassNotFoundException e) {
				// Erro ao concetar ao banco de dados
				// Levanta p�gina Erro 500 (n�o existe)

			} catch (SQLException e) {
				// Erro ao executar a instru��o
				// Levanta p�gina Erro 500 (n�o existe)
			}
		} else {
			dadosCadastroInvalidos = true;
		}

		map.put("isValid", isValid);
		map.put("pessoaFisica", pessoaFisica);
		map.put("dadosInvalidos", dadosCadastroInvalidos);
		map.put("usernameInvalido", usernameInvalido);
		map.put("login", login);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));
	}

	@RequestMapping("cadastrar_pessoa_juridica")
	public void cadastrarPessoaJuridica(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		BancoDados bancoDados = new BancoDados();
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isValid = false;
		boolean dadosCadastroInvalidos = false;
		boolean usernameInvalido = false;
		boolean isPF = false;
		String nomeUsuarioLogado = null;
		Login login = new Login();
		PessoaJuridica pessoaJuridica = new PessoaJuridica();

		String nome = request.getParameter("nome");
		String cnpj = request.getParameter("cnpj");
		String telefone = request.getParameter("telefone");
		String email = request.getParameter("email");
		String endereco = request.getParameter("endereco");
		String username = request.getParameter("username");
		String senha = request.getParameter("senha");

		if (!nome.equals("") && !cnpj.equals("") && !telefone.equals("")
				&& !email.equals("") && !senha.equals("")) {

			pessoaJuridica.setNome(nome);
			pessoaJuridica.setCnpj(cnpj);
			pessoaJuridica.setTelefone(telefone);
			pessoaJuridica.setEmail(email);
			pessoaJuridica.setPF(isPF);

			if (endereco.equals("")) {
				pessoaJuridica.setEndereco(null);
			} else {
				pessoaJuridica.setEndereco(endereco);
			}

			bancoDados.conectarAoBco();
			boolean usernameNaoCadastrado = bancoDados.usernameNaoCadastrado(username);

			if (usernameNaoCadastrado) {
				pessoaJuridica.setUsername(username);
				pessoaJuridica.setSenha(senha);
				int idLogin = bancoDados.geraLoginUsuario(
						pessoaJuridica.getUsername(),
						pessoaJuridica.getSenha(), pessoaJuridica.isPF());
				pessoaJuridica.setIdLogin(idLogin);
				bancoDados.cadastrarPessoaJuridica(pessoaJuridica);

				isValid = true;
				
				// Pega os dados do usuario logado. Ta meio gambiarra, pode ser melhor.
				
				login.setUsername(pessoaJuridica.getUsername());
				login.setSenha(pessoaJuridica.getSenha());
				login = bancoDados.dadosUsuarioLogado(login);
				
				pessoaJuridica = bancoDados.buscarPessoaJuridica(login.getIdLogin());
				nomeUsuarioLogado = pessoaJuridica.getNome();
				
				// para caso a página seja atualizada
				session.setAttribute("pessoaJuridica", pessoaJuridica);
				// loga o usuario
				session.setAttribute("usuarioLogado", login);
				// utilizado para peencher o campo caso a pagina seja atualizada
				session.setAttribute("nomeUsuarioLogado", nomeUsuarioLogado);
				///////////////////////////////////////////////////////
				
			} else {
				usernameInvalido = true;
			}

			bancoDados.encerrarConexao();

		} else {
			dadosCadastroInvalidos = true;
		}

		map.put("isValid", isValid);
		map.put("pessoaJuridica", pessoaJuridica);
		map.put("dadosInvalidos", dadosCadastroInvalidos);
		map.put("usernameInvalido", usernameInvalido);
		map.put("login", login);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));
	}
	
	
	@RequestMapping("alterar_cadastro_pj")
	public void updateCadastroPJ(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		BancoDados bancoDados = new BancoDados();
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isValid = false;
		boolean dadosCadastroInvalidos = false;
		boolean usernameInvalido = false;
		boolean isPF = false;
		String nomeUsuarioLogado = null;

		PessoaJuridica pessoaJuridica = new PessoaJuridica();

		String nome = request.getParameter("nome_editar_pj");
		String cnpj = request.getParameter("cnpj_editar_pj");
		String telefone = request.getParameter("telefone_editar_pj");
		String email = request.getParameter("email_editar_pj");
		String endereco = request.getParameter("endereco_editar_pj");
		String username = request.getParameter("username_editar_pj");
		String senha = request.getParameter("senha_editar_pj");
		
		System.out.println(">"+username+"<");

		if (!nome.equals("") && !cnpj.equals("") && !telefone.equals("")
				&& !email.equals("") && !senha.equals("")) {

			pessoaJuridica.setNome(nome);
			pessoaJuridica.setCnpj(cnpj);
			pessoaJuridica.setTelefone(telefone);
			pessoaJuridica.setEmail(email);
			pessoaJuridica.setPF(isPF);
			pessoaJuridica.setUsername(username);
			pessoaJuridica.setSenha(senha);
			
			System.out.println(">"+pessoaJuridica.getUsername()+"<");

			if (endereco.equals("")) {
				pessoaJuridica.setEndereco(null);
			} else {
				pessoaJuridica.setEndereco(endereco);
			}

			bancoDados.conectarAoBco();
			boolean usernameNaoCadastrado = bancoDados.usernameNaoCadastrado(username);
			Login usuarioSessao = (Login) session.getAttribute("usuarioLogado");
			
			if (usernameNaoCadastrado || username.equals(usuarioSessao.getUsername())) {
				
			
				pessoaJuridica.setIdLogin(usuarioSessao.getIdLogin());	
				if(!username.equals(usuarioSessao.getUsername()) || !senha.equals(usuarioSessao.getSenha())){
					bancoDados.editarDadosLogin(pessoaJuridica.getIdLogin(), pessoaJuridica.getUsername(), pessoaJuridica.getSenha());
				}
				bancoDados.editarCadastroPessoaJuridica(pessoaJuridica);
				
				pessoaJuridica.setUsername(username);
				
				
				int idLogin = bancoDados.geraLoginUsuario(pessoaJuridica.getUsername(), pessoaJuridica.getSenha(), pessoaJuridica.isPF());
				pessoaJuridica.setIdLogin(idLogin);
				
				
				bancoDados.cadastrarPessoaJuridica(pessoaJuridica);
				isValid = true;
				
				// Pega os dados do usuario logado. Ta meio gambiarra, pode ser melhor.
				Login login = new Login();
				login.setUsername(pessoaJuridica.getUsername());
				login.setSenha(pessoaJuridica.getSenha());
				login = bancoDados.dadosUsuarioLogado(login);
				pessoaJuridica = bancoDados.buscarPessoaJuridica(login.getIdLogin());
				nomeUsuarioLogado = pessoaJuridica.getNome();
				
				// para caso a página seja atualizada
				session.setAttribute("pessoaJuridica", pessoaJuridica);
				// loga o usuario
				session.setAttribute("usuarioLogado", login);
				// utilizado para peencher o campo caso a pagina seja atualizada
				session.setAttribute("nomeUsuarioLogado", nomeUsuarioLogado);
				///////////////////////////////////////////////////////
				
			} else {
				usernameInvalido = true;
			}

			bancoDados.encerrarConexao();

		} else {
			dadosCadastroInvalidos = true;
		}

		map.put("isValid", isValid);
		map.put("pessoaJuridica", pessoaJuridica);
		map.put("dadosInvalidos", dadosCadastroInvalidos);
		map.put("usernameInvalido", usernameInvalido);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));
	}

	
	@RequestMapping("alterar_cadastro_pf")
	public void updateCadastroPF(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		BancoDados bancoDados = new BancoDados();
		Map<String, Object> map = new HashMap<String, Object>();
		PessoaFisica pessoaFisica = new PessoaFisica();
		Data data = new Data();
		boolean isValid = false;
		boolean isPF = true;
		boolean dadosCadastroInvalidos = false;
		boolean usernameInvalido = false;
		String nomeUsuarioLogado = null;

		String nome = request.getParameter("nome_editar_pf");
		String cpf = request.getParameter("cpf_editar_pf");
		String email = request.getParameter("email_editar_pf");
		String username = request.getParameter("username_editar_pf");
		String senha = request.getParameter("senha_editar_pf");
		String dataNascimento = request.getParameter("data_nascim_editar_pf");
		String telefone = request.getParameter("telefone_editar_pf");

		// valida se os campos n�o est�o vazios
		if (!nome.equals("") && !cpf.equals("") && !email.equals("")
				&& !username.equals("") && !senha.equals("")
				&& !dataNascimento.equals("")) {
			pessoaFisica.setNome(nome);
			pessoaFisica.setCpf(cpf);
			pessoaFisica.setEmail(email);
			pessoaFisica.setDataNascimento(data.converteStringParaData(dataNascimento));
			pessoaFisica.setUsername(username);
			pessoaFisica.setSenha(senha);
			pessoaFisica.setPF(isPF);
			
			// campo n�o obrigat�rio
			if (telefone.equals("")) {
				pessoaFisica.setTelefone(null);
			} else {
				pessoaFisica.setTelefone(telefone);
			}

			try {
				bancoDados.conectarAoBco();
				Login usuarioSessao = (Login) session.getAttribute("usuarioLogado");
				

				boolean usernameNaoCadastrado = bancoDados.usernameNaoCadastrado(username);

				if (usernameNaoCadastrado || username.equals(usuarioSessao.getUsername())) {
					
					pessoaFisica.setIdLogin(usuarioSessao.getIdLogin());	
				
					if(!username.equals(usuarioSessao.getUsername()) || !senha.equals(usuarioSessao.getSenha())){
						bancoDados.editarDadosLogin(pessoaFisica.getIdLogin(), pessoaFisica.getUsername(), pessoaFisica.getSenha());
					}
					
					bancoDados.editarCadastroPessoaFisica(pessoaFisica);

					isValid = true;
					// Pega os dados do usuario logado. Ta meio gambiarra, pode ser melhor.
					Login login = new Login();
					login.setUsername(pessoaFisica.getUsername());
					login.setSenha(pessoaFisica.getSenha());
					login = bancoDados.dadosUsuarioLogado(login);
					
					pessoaFisica = bancoDados.buscarPessoaFisica(pessoaFisica.getIdLogin());
					nomeUsuarioLogado = pessoaFisica.getNome();
					
					// para caso a página seja atualizada
					session.setAttribute("pessoaFisica", pessoaFisica);
					// altera na sessão os dados do usuário logado
					session.setAttribute("usuarioLogado", login);
					// utilizado para peencher o campo caso a pagina seja atualizada
					session.setAttribute("nomeUsuarioLogado", nomeUsuarioLogado);
				}else {
					usernameInvalido = true;
				}
				bancoDados.encerrarConexao();

			} catch (ClassNotFoundException e) {
				// Erro ao concetar ao banco de dados
				// Levanta p�gina Erro 500 (n�o existe)

			} catch (SQLException e) {
				// Erro ao executar a instru��o
				// Levanta p�gina Erro 500 (n�o existe)
			}
		} else {
			dadosCadastroInvalidos = true;
		}

		map.put("isValid", isValid);
		map.put("pessoaFisica", pessoaFisica);
		map.put("dadosInvalidos", dadosCadastroInvalidos);
		map.put("usernameInvalido", usernameInvalido);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));
	}
	
			
	

	@RequestMapping("salvar_marcacao")
	public void salvarMarcacao(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws IOException {
		BancoDados bancoDados = new BancoDados();
		Map<String, Object> map = new HashMap<String, Object>();
		MarcacaoDepredacao oMarcacao = new MarcacaoDepredacao();
		PessoaFisica pf = new PessoaFisica();
		// Data data = new Data();
		boolean isValid = false;
		boolean pfOuPj = true;
		boolean usuarioLogado = false;
		boolean denunciaAnonima = false; // precisa criar campo no html dando
											// op��o de den�ncia
											// an�nima!!!!

		Login usuarioSessao = (Login) session.getAttribute("usuarioLogado");

		if (usuarioSessao != null) {
			usuarioLogado = true;
		
			//request.setCharacterEncoding("charset=UTF-8");
			
			String sCat = request.getParameter("cat");
			String sCaminho = request.getParameter("cam");
			String sTipo = request.getParameter("tit");
			String sLat = request.getParameter("lat");
			String sLong = request.getParameter("lon");
			String sHtml = request.getParameter("html");
			String sData = DateTime.now().toString("yyyyMMdd");
			oMarcacao.setDataMarcacao(sData);
			oMarcacao.setDescricao(sCat);
			oMarcacao.setStatus("1");
			oMarcacao.setTipoDepredacao(sTipo);
			oMarcacao.setPosLat(sLat);
			oMarcacao.setPosLon(sLong);
			oMarcacao.setHtml(sHtml);
			oMarcacao.setCadidatoResolverProblema(false);
	

			oMarcacao.setImgDenunciaIni(sCaminho);
			oMarcacao.setImgDenunciaFinal("");
			
			
			//COLOCAR O CAMPO QUE CONTEM O CAMINHO DA IMAGEM

			try {
				
				bancoDados.conectarAoBco();
				pf = bancoDados.buscarPessoaFisica(usuarioSessao.getIdLogin());
			//	bancoDados.encerrarConexao();

				oMarcacao.setIdPessoaFisicaFezMarcacao(pf.getIdPessoaFisica());				
				
			//	bancoDados.conectarAoBco();
				bancoDados.cadastrarMarcacao(oMarcacao);
				bancoDados.encerrarConexao();
				
								

				isValid = true;
			} catch (ClassNotFoundException e) {
				// Erro ao concetar ao banco de dados
				// Levanta página Erro 500 (não existe)
				String erro = "CLASSE = ";
				erro += e.getMessage();
				erro += " \n ";
				erro += e.getStackTrace();

			} catch (SQLException e) {

				String erro = "SQL = ";
				erro += e.getMessage();
				erro += " \n ";
				erro += e.getStackTrace();
				// Erro ao executar a instrução
				// Levanta página Erro 500 (não existe)
			}
		} else {
			isValid = false;
		}

		map.put("isValid", isValid);
		map.put("usuarioLogado", usuarioLogado);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));

	}    @RequestMapping(value="upload_imagem", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }
    
           
    	
	@RequestMapping("candidatarse")
	public void candidatarse(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws IOException {
		BancoDados bancoDados = new BancoDados();
		Map<String, Object> map = new HashMap<String, Object>();
		PessoaFisica pf = new PessoaFisica();
		// Data data = new Data();
		boolean isValid = false;
		boolean pfOuPj = true;
		boolean usuarioLogado = false;
		boolean jaTemCandidato = true;
		boolean jaSeCadastrou = true;
		ArrayList<MarcacaoDepredacao> listaMarcacoesCadastradas = new ArrayList<MarcacaoDepredacao>();
		Login usuarioSessao = (Login) session.getAttribute("usuarioLogado");
		String idMark = request.getParameter("idmarcacao");
		int idMarkInt = Integer.parseInt(idMark.toString().trim());
		
		if (usuarioSessao != null) {
			usuarioLogado = true;
	
			try {
				
				bancoDados.conectarAoBco();

				if(!bancoDados.VerificaSeTemCandidato(idMarkInt)){
					jaTemCandidato= false;
					pf = bancoDados.buscarPessoaFisica(usuarioSessao.getIdLogin());
					if(!bancoDados.verificaRelacaoCandidatura(pf.getIdPessoaFisica(),idMarkInt)){
						//Recupera a marcação do banco 
						MarcacaoDepredacao md = bancoDados.buscaMarcacaoEspeficifica(idMarkInt);
						String shtml = md.getHtml();
						if(shtml.contains("CANDNAO")){
							shtml = shtml.replace("CANDNAO","CANDSIM");
						}
						bancoDados.UpdateStatus(idMarkInt,2,shtml);
						jaSeCadastrou = false;				
						bancoDados.setCandidatura(pf.getIdPessoaFisica(), idMarkInt);		
						listaMarcacoesCadastradas = bancoDados.listaMarcacoesCadastradas();	
						
						bancoDados.encerrarConexao();
						isValid = true;
					}				
				}
	
			} catch (ClassNotFoundException e) {
				// Erro ao concetar ao banco de dados
				// Levanta página Erro 500 (não existe)
				String erro = "CLASSE = ";
				erro += e.getMessage();
				erro += " \n ";
				erro += e.getStackTrace();

			} catch (SQLException e) {

				String erro = "SQL = ";
				erro += e.getMessage();
				erro += " \n ";
				erro += e.getStackTrace();
				// Erro ao executar a instrução
				// Levanta página Erro 500 (não existe)
			}
		} else {
			isValid = false;
		}

		map.put("isValid", isValid);
		map.put("usuarioLogado", usuarioLogado);
		map.put("jaTemCandidato", jaTemCandidato);
		map.put("jaSeCadastrou", jaSeCadastrou);
		map.put("listaMarcacoesCadastradas", listaMarcacoesCadastradas);
		
		

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));
	}

	
	
	
	
	@RequestMapping("LikesDesLikes")
	public void LikesDeslikes(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws IOException {
		BancoDados bancoDados = new BancoDados();
		Map<String, Object> map = new HashMap<String, Object>();
		PessoaFisica pf = new PessoaFisica();
		boolean isValid = false;
		boolean usuarioLogado = false;
		boolean jaVotou = true;
		int VotoLikes= 0 ;
		int VotoDeslikes = 0;
		ArrayList<MarcacaoDepredacao> listaMarcacoesCadastradas = new ArrayList<MarcacaoDepredacao>();
		
		
		Login usuarioSessao = (Login) session.getAttribute("usuarioLogado");
		String idMark = request.getParameter("idMark");
		String likes = request.getParameter("like");
		
		int idMarkInt = Integer.parseInt(idMark.toString().trim());
		int iLikes = Integer.parseInt(likes);
		
		if (usuarioSessao != null) {
			usuarioLogado = true;
	
			try {
				
				bancoDados.conectarAoBco();
				
				
				pf = bancoDados.buscarPessoaFisica(usuarioSessao.getIdLogin());
				jaVotou= bancoDados.VerificaSeUsuarioJaVotou(idMarkInt, pf.getIdPessoaFisica());
				if(!jaVotou){
						bancoDados.GravaVotacao(idMarkInt,pf.getIdPessoaFisica(),iLikes);
						VotoLikes = bancoDados.BuscaQuantVotacao(idMarkInt,1);
						VotoDeslikes = bancoDados.BuscaQuantVotacao(idMarkInt,2);
						MarcacaoDepredacao md = bancoDados.buscaMarcacaoEspeficifica(idMarkInt);
						String shtml = md.getHtml();
						
						if(VotoDeslikes >= 2 || VotoLikes >= 2){
							if(shtml.contains("VOTOSIM")){
								shtml = shtml.replace("VOTOSIM","VOTONAO");
							}
							bancoDados.UpdateStatus(idMarkInt,3,shtml);
						}
						
						listaMarcacoesCadastradas = bancoDados.listaMarcacoesCadastradas();	
						isValid = true;
				}
				bancoDados.encerrarConexao();
	
			} catch (ClassNotFoundException e) {
				// Erro ao concetar ao banco de dados
				// Levanta página Erro 500 (não existe)
				String erro = "CLASSE = ";
				erro += e.getMessage();
				erro += " \n ";
				erro += e.getStackTrace();

			} catch (SQLException e) {

				String erro = "SQL = ";
				erro += e.getMessage();
				erro += " \n ";
				erro += e.getStackTrace();
				// Erro ao executar a instrução
				// Levanta página Erro 500 (não existe)
			}
		} else {
			isValid = false;
		}

		map.put("isValid", isValid);
		map.put("usuarioLogado", usuarioLogado);
		map.put("jaVotou", jaVotou);
		map.put("listaMarcacoesCadastradas", listaMarcacoesCadastradas);

		
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));
	}

	
	
	
	
	  @RequestMapping(value="upload_imagem")
	  public void cadastrarMarcacao(@RequestParam("caminho_imagem_upload") MultipartFile multipartFile,
		            							 HttpServletResponse response, 
	        									 HttpServletRequest request, 
	        									 HttpSession session) throws IOException, ServletException{
	    	      							 		  
		  	MarcacaoDepredacao marcacao = new MarcacaoDepredacao();
			Map<String, Object> map = new HashMap<String, Object>();
			ArrayList<MarcacaoDepredacao> listaMarcacoesCadastradas = new ArrayList<MarcacaoDepredacao>();
			BancoDados bancoDados = new BancoDados();
			boolean isValid = false;
			boolean usuarioLogado = false;
			boolean usuarioCerto = false;
			int idMarcacao = 0;
			int idMarkInt = 0;
			PessoaFisica pf = new PessoaFisica();
			PessoaJuridica pj = new PessoaJuridica();
			Login usuarioSessao = (Login) session.getAttribute("usuarioLogado");
			
			//Verifica se tem alguem logado
			if (usuarioSessao != null) {
				usuarioLogado = true;
				
				String status = request.getParameter("status").toString().trim();
				String idMark = request.getParameter("idMark").toString().trim();
				// pega os outros dados do form
				String txtEndereco = request.getParameter("txtEndereco");
				String dpMotivo = request.getParameter("dpMotivo");
				String txtComentario = request.getParameter("txtComentario");
				String txtlatitude = request.getParameter("latitude");
				String txtlongitude = request.getParameter("longitude");
				String txtIcon = request.getParameter("icon");
				String sData = DateTime.now().toString("yyyyMMdd");
				
				
				if(status.equals("4")){
					idMarkInt = Integer.parseInt(idMark.toString().trim());
				
				}else{
															
				marcacao.setDataMarcacao(sData);
				marcacao.setTipoDepredacao(dpMotivo);
				marcacao.setDescricao(txtComentario);
				marcacao.setPosLat(txtlatitude);
				marcacao.setPosLon(txtlongitude);
				marcacao.setStatus(status);
				marcacao.setCadidatoResolverProblema(false);
				
				}
			
				try {
					
					bancoDados.conectarAoBco();
						
					//Cadastra a maracação
					if (status.equals("1")) {
						
						
							//Buscou a pessoa logada no banco
							pf = bancoDados.buscarPessoaFisica(usuarioSessao.getIdLogin());
							//Colocou o ID da pessoa na marcação
							marcacao.setIdPessoaFisicaFezMarcacao(pf.getIdPessoaFisica()); 			
						
						idMarcacao = bancoDados.cadastrarMarcacao(marcacao);
						marcacao.setIdMarcacaoDepredacao(idMarcacao);
					
					} else /* status 4 */ {
					
						MarcacaoDepredacao md = bancoDados.buscaMarcacaoEspeficifica(idMarkInt);

						pf = bancoDados.buscarPessoaFisica(usuarioSessao.getIdLogin());
						//######### TEM QUE VERIFICAR SE A PESSOA Q ESTA FINALIZANDO EH A MSM Q ESTA CANDIDATADA A RESOLVER O PROBLEMA
						
						if (bancoDados.verificaCandidatoCerto(pf.getIdPessoaFisica(), md.getIdMarcacaoDepredacao())) {
							
							usuarioCerto = true;
						
							//#####################################
							
							String shtml = md.getHtml();
							
							//if(shtml.contains("VOTOSIM")){
							//	shtml = shtml.replace("VOTOSIM","VOTONAO");
							//}
							//if(shtml.contains("CANDNAO")){
							//	shtml = shtml.replace("CANDNAO","CANDSIM");
							//}
							
							if(shtml.contains(idMark + "_A")){
								shtml = shtml.replace((idMark + "_A"),(idMark + "_R"));
							}
							
							bancoDados.UpdateStatus(idMarkInt,4,shtml);
							
							listaMarcacoesCadastradas = bancoDados.listaMarcacoesCadastradas();	
							
							bancoDados.encerrarConexao();
							isValid = true;
							
						}
					}
					
				} catch (ClassNotFoundException e) {
					System.out.println("Erro de conexão ao banco de dados!" );
				} catch (SQLException e1) {
					System.out.println("Erro ao salvar marcação no banco de dados:");
					System.out.println(e1.getMessage());
				}
				
				String nomeArquivo;
				
				if(status.equals("1"))
					nomeArquivo = idMarcacao + "_A";
				else
					nomeArquivo = idMark + "_R";
				
				String nomeCompletoArquivoComCaminho = uploadArquivo(multipartFile, nomeArquivo);
				nomeCompletoArquivoComCaminho = nomeCompletoArquivoComCaminho.replace("\\","\\\\");
				//############### Não esquecer de com o nome na imagem ############

				if(status.equals("1")){
					String contentString = "<div id=\\'content\\'> ";
					contentString +=  "<div id=\\'siteNotice\\'>" ;
					contentString += " </div>";
					contentString +=  "<input id=\\'ipTitulo\\'  type=\\'hidden\\' name=\\'ipTitulo\\' value=\\' "+ dpMotivo +"\\'>";
					contentString +=  "<input id=\\'ipDenuncia\\'  type=\\'hidden\\' name=\\'idDenuncia\\' value=\\' "+ idMarcacao + "\\'>";
					contentString +=  "<input id=\\'ipEndereco\\'  type=\\'hidden\\' name=\\'ipEndereco\\' value=\\' "+ txtEndereco +"\\'>";
					contentString +=  "<input id=\\'ipCaminho\\'  type=\\'hidden\\' name=\\'ipCaminho\\' value=\\'" + nomeCompletoArquivoComCaminho + "\\'>"; 
					contentString +=  "<input id=\\'ipLiberaVotacao\\' type=\\'hidden\\' name=\\'ipLiberaVotacao\\' value=\\'VOTOSIM\\'>";
					contentString +=  "<input id=\\'ipJaSeCandidatou\\' type=\\'hidden\\' name=\\'ipJaSeCandidatou\\' value=\\'CANDNAO\\'>";
					contentString +=  "<input id=\\'ipCaminhoFotoNova\\'  type=\\'hidden\\' name=\\'ipCaminhoFotoNova\\' value=\\'FFDDNN\\'> ";
					contentString +=  "<input id=\\'ipBeneficioDenuncia\\'  type=\\'hidden\\' name=\\'ipBeneficioDenuncia\\' value=\\'NAOBENEF\\'> ";
					contentString +=  "<input id=\\'ipDadosDigitados\\'  type=\\'hidden\\' name=\\'ipDadosDigitados\\' value=\\' "+ txtComentario  +"\\'>";
					contentString +=  "<div id=\\'bodyContent\\'>";
					contentString +=  "<p> "+ txtComentario  +" </p>";
					contentString +=  "</div>";
					contentString +=  "</div>";
				
					marcacao.setHtml(contentString.trim());
					

					
					try {
						bancoDados.UpdateStatus(idMarcacao,Integer.parseInt(status),contentString);
						listaMarcacoesCadastradas = bancoDados.listaMarcacoesCadastradas();	
						isValid =true;
						bancoDados.encerrarConexao();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
						
//	       	resposta a requisicao
	   		map.put("isValid", isValid);
			map.put("usuarioLogado", usuarioLogado);
			map.put("usuarioCerto", usuarioCerto);
			map.put("listaMarcacoesCadastradas", listaMarcacoesCadastradas);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(new Gson().toJson(map));
			
	  }
	  
	  /**
	   * 
	   * @param multipartFile (o arquivo da requisicao)
	   * @param nomeArquivo (o nome desejado para o arquivo)
	   * @return o nome completo do arquivo
	   */
	  private String uploadArquivo(MultipartFile multipartFile, String nomeArquivo) {
		  String nomeCompletoArquivoComCaminho = null;

		  if (!multipartFile.isEmpty()) {
			  try {

				  File path = new File("static" + File.separator + "upload_imagens");
				  File pathSalvarArquivo = new File(context.getRealPath("") + File.separator + "static" + File.separator + "upload_imagens");
				  System.out.println("CAMINHO IMAGEM: " + path);
				
				 System.out.println("CAMINHO IMAGEM: " + path); if
				 (!path.exists()) { path.mkdir();
				 System.out.println("Diretorio criado: " + path); }
				 
				  String extensao = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
				
				  byte[] bytes = multipartFile.getBytes();
				  nomeCompletoArquivoComCaminho = pathSalvarArquivo + File.separator + nomeArquivo + extensao;
				  BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(nomeCompletoArquivoComCaminho)));

				// new BufferedOutputStream(new FileOutputStream(new
				// File("upload_imagens/" + name + extension)));
				  stream.write(bytes);
				  stream.close();
				  System.out.println("Upload do arquivo [" + nomeArquivo + "] efetuado com sucesso!");
				  
				  nomeCompletoArquivoComCaminho = path + File.separator + nomeArquivo + extensao;
			} catch (Exception e) {
				System.out.println("Falha ao executar o upload do arquivo [" + nomeArquivo + "]. Motivo:");
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Falha ao fazer upload porque o arquivo esta vazio!");
		}

		return nomeCompletoArquivoComCaminho;
	}

/* FUNCAO QUE RECEBE O CADASTRO DO BENEFICIO */

@RequestMapping("incluirBeneficio")
	public void incluirBeneficio(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws IOException {
		BancoDados bancoDados = new BancoDados();
		Map<String, Object> map = new HashMap<String, Object>();
		PessoaJuridica pj = new PessoaJuridica();
		// Data data = new Data();
		boolean isValid = false;
		boolean pfOuPj = true;
		boolean usuarioLogado = false;
		boolean jaTemBeneficio = true;
		boolean jaSeCadastrou = true;
		Login usuarioSessao = (Login) session.getAttribute("usuarioLogado");
		String idMark = request.getParameter("idmarcacao");
		String descricaoBeneficio = request.getParameter("descricao");
		int idMarkInt = Integer.parseInt(idMark.toString().trim());
		ArrayList<MarcacaoDepredacao> listaMarcacoesCadastradas = new ArrayList<MarcacaoDepredacao>();
		
		if (usuarioSessao != null) {
			usuarioLogado = true;
	
			try {
				
				bancoDados.conectarAoBco();

				if(!bancoDados.VerificaSeTemBeneficio(idMarkInt))
				{
					jaTemBeneficio= false;
					pj = bancoDados.buscarPessoaJuridica(usuarioSessao.getIdLogin());
					
					jaSeCadastrou = false;				
					
					BeneficioEmpresa oBeneficio = new BeneficioEmpresa();
						oBeneficio.setDescricaoBeneficio(descricaoBeneficio);
						oBeneficio.setIdMarcacaoDepredacao(idMarkInt);
						oBeneficio.setIdPessoaJuridica(pj.getIdPessoaJuridica());
						oBeneficio.setAprovado(1);
						bancoDados.cadastrarBeneficio(oBeneficio);		
						
						MarcacaoDepredacao md = bancoDados.buscaMarcacaoEspeficifica(idMarkInt);
						String shtml = md.getHtml();
						
						if(shtml.contains("NAOBENEF")){
							shtml = shtml.replace("NAOBENEF",descricaoBeneficio);
						}
						
						int statusMark = Integer.parseInt(md.getStatus().toString().trim());
						bancoDados.UpdateStatus(idMarkInt,statusMark,shtml);
						listaMarcacoesCadastradas = bancoDados.listaMarcacoesCadastradas();	
											
						
					bancoDados.encerrarConexao();
					isValid = true;
					}				
				}
	
			catch (ClassNotFoundException e) {
				// Erro ao concetar ao banco de dados
				// Levanta pÃ¡gina Erro 500 (nÃ£o existe)
				String erro = "CLASSE = ";
				erro += e.getMessage();
				erro += " \n ";
				erro += e.getStackTrace();

			} catch (SQLException e) {

				String erro = "SQL = ";
				erro += e.getMessage();
				erro += " \n ";
				erro += e.getStackTrace();
				// Erro ao executar a instruÃ§Ã£o
				// Levanta pÃ¡gina Erro 500 (nÃ£o existe)
			}
		} else {
			isValid = false;
		}

		map.put("isValid", isValid);
		map.put("usuarioLogado", usuarioLogado);
		map.put("jaTemBeneficio", jaTemBeneficio);
		map.put("listaMarcacoesCadastradas", listaMarcacoesCadastradas);
			
		

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));
	}



	
	
	
}