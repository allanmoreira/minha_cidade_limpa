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
	
	@RequestMapping("")
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("index");
		return mv;
	}
	
	/**
	 * O método utiliza um hash para adicionar os objetos que irão retornar para a página. 
	 * Os parâmetros do hash é uma string com o nome que o json reconhecerá o objeto = o objeto. 
	 * A biblioteca Gson, do google, converte o Hash em json.
	 * 
	 * @param request - Requisição do usuário
	 * @param response - A resposta do servidor para o usuário 
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
		
    	String nome = request.getParameter("nome");
    	String dataNascimento = request.getParameter("data_nascim");
    	String cpf = request.getParameter("cpf");
    	String telefone = request.getParameter("telefone");
    	String email = request.getParameter("email");
    	
    	// valida se os campos não estão vazios
//    	if(!nome.equals("") && !dataNascimento.equals("") && !cpf.equals("") && !telefone.equals("") & !email.equals("")){
    		pessoaFisica.setNome(nome);
    		pessoaFisica.setCpf(cpf);
    		pessoaFisica.setTelefone(telefone);
    		pessoaFisica.setEmail(email);
    		pessoaFisica.setDataNascimento(data.converteStringParaData(dataNascimento));
    		
    		try {
    			bancoDados.conectarAoBco();
    			int idLogin = bancoDados.geraLoginUsuario(pfOuPj);
    			
    			pessoaFisica.setIdLogin(idLogin);
    			bancoDados.cadastrarPessoaFisica(pessoaFisica);
    			bancoDados.encerrarConexao();

    			isValid = true;
    		} catch (ClassNotFoundException e) {
    			// Erro ao concetar ao banco de dados
    			// Levanta página Erro 500 (não existe)
    			
    		} catch (SQLException e) {
    			// Erro ao executar a instrução
    			// Levanta página Erro 500 (não existe)
    		}
//    	}
    	
    	if(isValid) {
	        map.put("isValid", isValid);
	        map.put("pessoaFisica", pessoaFisica);
	        
	        response.setContentType("application/json");
    	    response.setCharacterEncoding("UTF-8");
    	    response.getWriter().write(new Gson().toJson(map));
		}
		else {
	 	    map.put("isValid", isValid);
	        response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(new Gson().toJson(map));
	    }
	}
	
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
}
