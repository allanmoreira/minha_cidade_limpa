package controle.servlet;

import java.io.File;
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

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

@Controller
@MultipartConfig
public class ServletDeControle {

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
				bancoDados.encerrarConexao();
				
//				if (autorizaLogar) {
					
					if(login.isPF()){
						bancoDados.conectarAoBco();
						pessoaFisica = bancoDados.buscarPessoaFisica(login.getIdLogin());
						dataNascimento = pessoaFisica.getDataNascimentoString();
						nomeUsuarioLogado = pessoaFisica.getNome();
						bancoDados.encerrarConexao();
						// para caso a página seja atualizada
						session.setAttribute("pessoaFisica", pessoaFisica);
					}
					else{
						bancoDados.conectarAoBco();
						pessoaJuridica = bancoDados.buscarPessoaJuridica(login.getIdLogin());
						nomeUsuarioLogado = pessoaJuridica.getNome();
						bancoDados.encerrarConexao();
						// para caso a página seja atualizada
						session.setAttribute("pessoaJuridica", pessoaJuridica);
					}
					
					session.setAttribute("usuarioLogado", login);
					// utilizado para peencher o campo caso a pagina seja atualizada
					session.setAttribute("nomeUsuarioLogado", nomeUsuarioLogado);
					isValid = true;
//				}
				
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
					Login login = new Login();
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
			boolean usernameNaoCadastrado = bancoDados
					.usernameNaoCadastrado(username);

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

			if (usernameNaoCadastrado) {
				
				Login usuarioSessao = (Login) session.getAttribute("usuarioLogado");
				pessoaJuridica.setIdLogin(usuarioSessao.getIdLogin());	
				
				bancoDados.editarDadosLogin(pessoaJuridica.getIdLogin(), pessoaJuridica.getUsername(), pessoaJuridica.getSenha());
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

				boolean usernameNaoCadastrado = bancoDados.usernameNaoCadastrado(username);

				if (usernameNaoCadastrado) {
					Login usuarioSessao = (Login) session.getAttribute("usuarioLogado");
					pessoaFisica.setIdLogin(usuarioSessao.getIdLogin());					
					bancoDados.editarDadosLogin(pessoaFisica.getIdLogin(), pessoaFisica.getUsername(), pessoaFisica.getSenha());
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

			oMarcacao.setDescricao(sCat);
			oMarcacao.setStatus("1");
			oMarcacao.setTipoDepredacao(sTipo);
			oMarcacao.setPosLat(sLat);
			oMarcacao.setPosLon(sLong);
			oMarcacao.setHtml(sHtml);
			oMarcacao.setCadidatoResolverProblema(false);
			oMarcacao.setDataMarcacao(sData);

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

	}
	
	@RequestMapping(value = { "upload_imagem"}, method = RequestMethod.POST)
	@ResponseBody
	protected void uploadImagem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("meu debug");
		
		final String DIRETORIO = System.getProperty("user.dir") + "/upload_imagens/";
		 
	    // Se quiser limitar o tamanho da imagem:
	    final int TAMANHO_MAX   = 1024 * 1024 * 10;  // 10MB
        
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        
        if (isMultipart) {
            try {
            	
            	// Cria diret�rio se nao existir (.../upload_imagens/)
                File path = new File(DIRETORIO);
                if (!path.exists()) {
                	path.mkdir();
                }
                System.out.println("1");
            	Part file = request.getPart("file"); // Retrieves <input type="file" name="file">
                System.out.println("2");
            	InputStream input;
            	
            	System.out.println("2,5");
            	input = file.getInputStream();
                System.out.println("3");
            	OutputStream output = new FileOutputStream(new File(DIRETORIO, file.getName() + "111.png"));
            	
            	System.out.println(System.getProperty("user.dir"));
            	
            	try {
                    IOUtils.copy(input, output);
                } finally {
                    IOUtils.closeQuietly(input);
                    IOUtils.closeQuietly(output);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
        	System.out.println("meu debug = n�o � multpart!");
        }
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

		Login usuarioSessao = (Login) session.getAttribute("usuarioLogado");
		int idMarcacao = (int) session.getAttribute("idMark");

		if (usuarioSessao != null) {
			usuarioLogado = true;
		
			//request.setCharacterEncoding("charset=UTF-8");
			
			//COLOCAR O CAMPO QUE CONTEM O CAMINHO DA IMAGEM

			try {
				
				bancoDados.conectarAoBco();
				pf = bancoDados.buscarPessoaFisica(usuarioSessao.getIdLogin());
			//	bancoDados.encerrarConexao();

				bancoDados.setCandidatura(pf.getIdPessoaFisica(), idMarcacao);

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

	}
	
	
	
	
	
}
