package controle.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import controle.bancoDados.*;
import controle.conversaoDados.*;
import modelos.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class ServletDeControle {
	
	/**
	 * Monta a p�gina home. Sua url � vazia para que somente o endere�o do site apare�a
	 * @param request - Requisi��o do usu�rio
	 * @param response - A resposta do servidor para o usu�rio 
	 * @param session - verifica se o usu�rio est� logado (ainda n�o implementado)
	 * @return
	 */
	@RequestMapping("")
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("index");
		return mv;
	}
	
	/**
	 * O m�todo utiliza um hash para adicionar os objetos que ir�o retornar para a p�gina. 
	 * Os par�metros do hash s�o uma string com o nome que o json reconhecer� o objeto = o objeto. 
	 * A biblioteca Gson, do google, converte o Hash em json.
	 * 
	 * O boolean pf_ou_pj recebe TRUE se PF ou FALSE se PJ
	 * 
	 * @param request - Requisi��o do usu�rio
	 * @param response - A resposta do servidor para o usu�rio 
	 * @throws Exception 
	 */
	@RequestMapping("cadastrar_pessoa_fisica")
	public void cadastrarPessoaFisica(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BancoDados bancoDados = new BancoDados();
		Map <String, Object> map = new HashMap<String, Object>();
    	PessoaFisica pessoaFisica = new PessoaFisica();
    	Data data = new Data();
		boolean isValid = false;
		boolean pfOuPj = true;
		boolean dadosCadastroInvalidos = false;
		boolean usernameInvalido = false;
		
    	String nome = request.getParameter("nome");
    	String cpf = request.getParameter("cpf");
    	String email = request.getParameter("email");
    	String username = request.getParameter("username");
    	String senha = request.getParameter("senha");
    	String dataNascimento = request.getParameter("data_nascim");
    	String telefone = request.getParameter("telefone");
    	
    	// valida se os campos n�o est�o vazios	
    	if(!nome.equals("") && !cpf.equals("") && !email.equals("") && !username.equals("") && 
    			!senha.equals("") && !dataNascimento.equals("")){
    		pessoaFisica.setNome(nome);
    		pessoaFisica.setCpf(cpf);
    		pessoaFisica.setEmail(email);
    		pessoaFisica.setDataNascimento(data.converteStringParaData(dataNascimento));
    		pessoaFisica.setUsername(username);
    		pessoaFisica.setSenha(senha);
    		pessoaFisica.setPfOuPj(pfOuPj);
    		
    		// campo n�o obrigat�rio
    		if(telefone.equals("")){
    			pessoaFisica.setTelefone(null);
    		} else {
    			pessoaFisica.setTelefone(telefone);
    		}
    		
    		try {
    			bancoDados.conectarAoBco();
    			
    			boolean usernameNaoCadastrado = bancoDados.usernameNaoCadastrado(username);
    			
    			if (usernameNaoCadastrado) {
    				int idLogin = bancoDados.geraLoginUsuario(pessoaFisica.getUsername(), pessoaFisica.getSenha(), pessoaFisica.isPfOuPj());
					pessoaFisica.setIdLogin(idLogin);
					bancoDados.cadastrarPessoaFisica(pessoaFisica);
					
					isValid = true;
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
	public void cadastrarPessoaJuridica(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BancoDados bancoDados = new BancoDados();
		Map <String, Object> map = new HashMap<String, Object>();
		boolean isValid = false;
		boolean dadosCadastroInvalidos = false;
		boolean usernameInvalido = false;
		boolean pfOuPj = false;
		
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		
		String nome = request.getParameter("nome");
		String cnpj = request.getParameter("cnpj");
		String telefone = request.getParameter("telefone");
		String email = request.getParameter("email");
		String endereco = request.getParameter("endereco");
		String username = request.getParameter("username");
		String senha = request.getParameter("senha");
		
		if(!nome.equals("") && !cnpj.equals("") && !telefone.equals("") && !email.equals("") && !senha.equals("")){
			
			pessoaJuridica.setNome(nome);
			pessoaJuridica.setCnpj(cnpj);
			pessoaJuridica.setTelefone(telefone);
			pessoaJuridica.setEmail(email);
			pessoaJuridica.setPfOuPj(pfOuPj);
			
			if(endereco.equals("")){
				pessoaJuridica.setEndereco(null);
			} else {
				pessoaJuridica.setEndereco(endereco);
			}
			
			bancoDados.conectarAoBco();
			boolean usernameNaoCadastrado = bancoDados.usernameNaoCadastrado(username);
			
			if (usernameNaoCadastrado) {
				int idLogin = bancoDados.geraLoginUsuario(pessoaJuridica.getUsername(), pessoaJuridica.getSenha(), pessoaJuridica.isPfOuPj());
				pessoaJuridica.setIdLogin(idLogin);
				bancoDados.cadastrarPessoaJuridica(pessoaJuridica);
				
				isValid = true;
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
	
	/*
	@RequestMapping("salvar_marcacao")
	public void salvarMarcacao(HttpServletRequest request, HttpServletResponse response) {
		BancoDados bancoDados = new BancoDados();
		Map <String, Object> map = new HashMap<String, Object>();
    	MarcacaoDepredacao oMarcacao = new MarcacaoDepredacao();
    	Data data = new Data();
		boolean isValid = false;
		boolean pfOuPj = true;
		
		
		String sCat= request.getParameter("cat");
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
    		
    		try {
    			bancoDados.conectarAoBco();
    			int idLogin = bancoDados.geraLoginUsuario(pfOuPj);
    			
    			bancoDados.cadastrarMarcacao(oMarcacao);
    			bancoDados.encerrarConexao();

    			isValid = true;
    		} catch (ClassNotFoundException e) {
    			// Erro ao concetar ao banco de dados
    			// Levanta página Erro 500 (não existe)
    			String erro = "CLASSE = ";
    			erro += e.getMessage();
    			erro+=" \n ";
    			erro += e.getStackTrace();
    			
    		} catch (SQLException e) {
   
     			String erro = "SQL = ";
     			erro += e.getMessage();
    			erro+=" \n ";
    			erro += e.getStackTrace();
    			// Erro ao executar a instrução
    			// Levanta página Erro 500 (não existe)
    		}
//    	}
    	
    	if(isValid) {
	       // map.put("isValid", isValid);
	       // map.put("pessoaFisica", pessoaFisica);
	        
	        //response.setContentType("application/json");
    	   // response.setCharacterEncoding("UTF-8");
    	    //response.getWriter().write(new Gson().toJson(map));
		}
		else {
	 	    //map.put("isValid", isValid);
	        //response.setContentType("application/json");
		    //response.setCharacterEncoding("UTF-8");
		    //response.getWriter().write(new Gson().toJson(map));
	    }
	}
	*/
}
