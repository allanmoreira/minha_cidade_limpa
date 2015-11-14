package controle.bancoDados;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

import modelos.Login;
import modelos.MarcacaoDepredacao;
import modelos.PessoaFisica;
import modelos.PessoaJuridica;

public class BancoDados {
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public void conectarAoBco() throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.jdbc.Driver");
		// String url =
		// "jdbc:mysql://localhost:3306/minha_cidade_limpa_bd?zeroDateTimeBehavior=convertToNull";
		String url = "jdbc:mysql://104.131.19.51:3306/minha_cidade_limpa_bd?zeroDateTimeBehavior=convertToNull";

		String usuario = "trab_ger_proj";
		String senha = "cidadelimpa20152";

		connection = DriverManager.getConnection(url, usuario, senha);

		// teste para commit
	}

	public void encerrarConexao() throws SQLException {
		preparedStatement.close();
		connection.close();
	}

	public void cadastrarPessoaFisica(PessoaFisica pessoaFisica)
			throws SQLException {
		String sql = "insert into pessoa_fisica " + "(nome, " + "cpf, "
				+ "email, " + "data_nascimento, " + "telefone, " + "id_login) "

				+ "values (?, ?, ?, ?, ?, ?)";

		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, pessoaFisica.getNome());
		preparedStatement.setString(2, pessoaFisica.getCpf());
		preparedStatement.setString(3, pessoaFisica.getEmail());
		preparedStatement.setDate(4, pessoaFisica.getDataNascimento());
		preparedStatement.setString(5, pessoaFisica.getTelefone());
		preparedStatement.setInt(6, pessoaFisica.getIdLogin());

		preparedStatement.executeUpdate();
	}

	public PessoaFisica buscarPessoaFisica(int idLogin) throws SQLException {
		PessoaFisica pf = new PessoaFisica();

		String sql = "select * from pessoa_fisica pf, login l " + "where "
				+ "l.id_login = pf.id_login and " + "l.id_login = ?";

		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, idLogin);

		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		pf.setNome(resultSet.getString("nome"));
		pf.setCpf(resultSet.getString("cpf"));
		pf.setEmail(resultSet.getString("email"));
		pf.setDataNascimento(resultSet.getDate("data_nascimento"));
		pf.setTelefone(resultSet.getString("telefone"));
		pf.setIdLogin(resultSet.getInt("id_login"));
		pf.setUsername(resultSet.getString("username"));
		pf.setIdPessoaFisica(resultSet.getInt("id_pessoa_fisica"));

		resultSet.close();

		return pf;
	}

	/***
	 * Cadastro de den�ncias feitas por pessoas
	 * 
	 * @param oMarcacao
	 * @throws SQLException
	 */
	public int cadastrarMarcacao(MarcacaoDepredacao oMarcacao) throws SQLException {
		String sql = "insert into marcacao_depredacao "
				+ "(id_marcacao_depredacao, "
				+ "tipo_depredacao, "
				+ "descricao, "
				+ "data_marcacao, "
				+ "cadidato_resolver_problema, "
				+ "id_pessoa_fisica_fez_narcacao, "
				+ "html_depredacao, "
				+ "lat, "
				+ "lon,"
				+ "status_marcacao,"
				+ "img_denuncia,"
				+ "img_denuncia_final) "				
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, oMarcacao.getIdMarcacaoDepredacao());
        preparedStatement.setString(2, oMarcacao.getTipoDepredacao());
        preparedStatement.setString(3, oMarcacao.getDescricao());
        preparedStatement.setString(4, oMarcacao.getDataMarcacao());
        preparedStatement.setBoolean(5, oMarcacao.isCadidatoResolverProblema());
        preparedStatement.setInt(6, oMarcacao.getIdPessoaFisicaFezMarcacao());
        preparedStatement.setString(7, oMarcacao.getHtml());
        preparedStatement.setString(8,oMarcacao.getPosLat());
        preparedStatement.setString(9,oMarcacao.getPosLon());
        preparedStatement.setString(10,oMarcacao.getStatus());
        preparedStatement.setString(11,oMarcacao.getImgDenunciaIni());
        preparedStatement.setString(12,oMarcacao.getImgDenunciaFinal());
     
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        int idMarcacao = resultSet.getInt(1);
        
        return idMarcacao;
	}
	
	

	/**
	 * Cadastra os dados de login do usu�rio. Est� vazio porque n�o
	 * consegui terminar, e porque n�o possui os campos de cadastro na
	 * interface.
	 * 
	 * @param idPessoaFisica
	 * @return idLogin
	 * @throws SQLException
	 */
	public int geraLoginUsuario(String username, String senha, boolean pfOuPj)
			throws SQLException {
		String sql = "insert into login " + "(username, " + "senha, "
				+ "is_pf) "

				+ "values (?, ?, ?)";

		preparedStatement = connection.prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, senha);
		preparedStatement.setBoolean(3, pfOuPj);

		preparedStatement.executeUpdate();
		ResultSet resultSet = preparedStatement.getGeneratedKeys();
		resultSet.next();
		int idLogin = resultSet.getInt(1);

		return idLogin;
	}

	/**
	 * Valida se o usu�rio n�o existe no banco de dados
	 * 
	 * @param ussername
	 *            do novo usu�rio
	 * @return TRUE se usu�rio n�o cadastrado, e FALSE se usu�rio
	 *         cadastrado
	 * @throws SQLException
	 */
	public boolean usernameNaoCadastrado(String username) throws SQLException {

		String sql = "select username " + "from login " + "where username = ?";

		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, username);

		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			return false;
		}

		return true;
	}

	public void cadastrarPessoaJuridica(PessoaJuridica pessoaJuridica)
			throws SQLException {
		String sql = "insert into pessoa_juridica " + "(nome, " + "cnpj, "
				+ "email, " + "endereco, " + "telefone, " + "id_login) "

				+ "values (?, ?, ?, ?, ?, ?)";

		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, pessoaJuridica.getNome());
		preparedStatement.setString(2, pessoaJuridica.getCnpj());
		preparedStatement.setString(3, pessoaJuridica.getEmail());
		preparedStatement.setString(4, pessoaJuridica.getEndereco());
		preparedStatement.setString(5, pessoaJuridica.getTelefone());
		preparedStatement.setInt(6, pessoaJuridica.getIdLogin());

		preparedStatement.executeUpdate();
	}

	public boolean login(Login login) throws SQLException {
		String sql = "select * from login " + "where username = ? and "
				+ "senha = ?";

		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, login.getUsername());
		preparedStatement.setString(2, login.getSenha());

		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			return true;
		}

		return false;
	}

	public Login dadosUsuarioLogado(Login login) throws SQLException {
		String sql = "select * from login " + "where username = ? and "
				+ "senha = ?";

		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, login.getUsername());
		preparedStatement.setString(2, login.getSenha());

		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		login.setIdLogin(resultSet.getInt("id_login"));
		login.setPF(resultSet.getBoolean("is_pf"));

		return login;
	}

	public PessoaJuridica buscarPessoaJuridica(int idLogin) throws SQLException {
		PessoaJuridica pj = new PessoaJuridica();

		String sql = "select * from pessoa_juridica pj, login l " + "where "
				+ "l.id_login = pj.id_login and " + "l.id_login = ?";

		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, idLogin);

		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		pj.setNome(resultSet.getString("nome"));
		pj.setCnpj(resultSet.getString("cnpj"));
		pj.setEmail(resultSet.getString("email"));
		pj.setEndereco(resultSet.getString("endereco"));
		pj.setTelefone(resultSet.getString("telefone"));
		pj.setIdLogin(resultSet.getInt("id_login"));
		pj.setUsername(resultSet.getString("username"));
		resultSet.close();

		return pj;
	}

	public void editarDadosLogin(int idLogin, String username, String senha)
			throws SQLException {
		String sql = "update login " + "set username = ?, " + "senha = ? "
				+ "where id_login = ?";

		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, senha);
		preparedStatement.setInt(3, idLogin);
		preparedStatement.executeUpdate();
	}

	public void editarCadastroPessoaFisica(PessoaFisica pessoaFisica)
			throws SQLException {
		String sql = "update pessoa_fisica " + "set nome = ?, " + "cpf = ?, "
				+ "email = ?, " + "data_nascimento = ?, " + "telefone = ? "
				+ "where id_login = ?";

		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, pessoaFisica.getNome());
		preparedStatement.setString(2, pessoaFisica.getCpf());
		preparedStatement.setString(3, pessoaFisica.getEmail());
		preparedStatement.setDate(4, pessoaFisica.getDataNascimento());
		preparedStatement.setString(5, pessoaFisica.getTelefone());
		preparedStatement.setInt(6, pessoaFisica.getIdLogin());

		preparedStatement.executeUpdate();
	}

	public void editarCadastroPessoaJuridica(PessoaJuridica pessoaJuridica)
			throws SQLException {
		String sql = "update pessoa_juridica " + "set nome = ?, "
				+ "cnpj = ?, " + "email = ?, " + "endereco = ?, "
				+ "telefone = ? " + "where id_login = ? ";

		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, pessoaJuridica.getNome());
		preparedStatement.setString(2, pessoaJuridica.getCnpj());
		preparedStatement.setString(3, pessoaJuridica.getEmail());
		preparedStatement.setString(4, pessoaJuridica.getEndereco());
		preparedStatement.setString(5, pessoaJuridica.getTelefone());
		preparedStatement.setInt(6, pessoaJuridica.getIdLogin());

		preparedStatement.executeUpdate();
	}

	public ArrayList<MarcacaoDepredacao> listaMarcacoesCadastradas()
			throws SQLException {
		ArrayList<MarcacaoDepredacao> listaMarcacoesCadastradas = new ArrayList<MarcacaoDepredacao>();
		String sql = "select * from marcacao_depredacao";

		preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			MarcacaoDepredacao md = new MarcacaoDepredacao();
			md.setIdMarcacaoDepredacao(resultSet
					.getInt("id_marcacao_depredacao"));
			md.setTipoDepredacao(resultSet.getString("tipo_depredacao"));
			md.setDescricao(resultSet.getString("descricao"));
			md.setDataMarcacao(resultSet.getString("data_marcacao"));
			md.setIdPessoaFisicaFezMarcacao(resultSet
					.getInt("id_pessoa_fisica_fez_narcacao"));
			md.setHtml(resultSet.getString("html_depredacao"));
			md.setPosLat(resultSet.getString("lat"));
			md.setPosLon(resultSet.getString("lon"));
			md.setStatus(resultSet.getString("status_marcacao"));

			md.setImgDenunciaFinal(resultSet.getString("img_denuncia_final"));
			md.setImgDenunciaIni(resultSet.getString("img_denuncia"));

			listaMarcacoesCadastradas.add(md);
		}
		return listaMarcacoesCadastradas;
	}

	public MarcacaoDepredacao buscaMarcacaoEspeficifica(int idMark)
			throws SQLException {
		int idMarcacao = 0;

		String sql = "select *  from marcacao_depredacao "
				+ "where id_marcacao_depredacao = ? ";

		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, idMark);
		ResultSet resultSet = preparedStatement.executeQuery();
		MarcacaoDepredacao md = new MarcacaoDepredacao();
		while (resultSet.next()) {

			md.setIdMarcacaoDepredacao(resultSet
					.getInt("id_marcacao_depredacao"));
			md.setTipoDepredacao(resultSet.getString("tipo_depredacao"));
			md.setDescricao(resultSet.getString("descricao"));
			md.setDataMarcacao(resultSet.getString("data_marcacao"));
			md.setIdPessoaFisicaFezMarcacao(resultSet
					.getInt("id_pessoa_fisica_fez_narcacao"));
			md.setHtml(resultSet.getString("html_depredacao"));
			md.setPosLat(resultSet.getString("lat"));
			md.setPosLon(resultSet.getString("lon"));
			md.setStatus(resultSet.getString("status_marcacao"));

			md.setImgDenunciaFinal(resultSet.getString("img_denuncia_final"));
			md.setImgDenunciaIni(resultSet.getString("img_denuncia"));
		}
		resultSet.close();

		return md;
	}

	/* Aqui verifica se esse usuario nao esta cadastrado para outra depredacao */
	public boolean VerificaSeTemCandidato(int idMarcacao) throws SQLException {

		String sql = "select id_pessoa_fisica "
				+ "from cadidatura_resolucao_problema "
				+ "where id_marcacao_depredacao = ?";

		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, idMarcacao);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			return true;
		}
		return false;
	}

	/* Aqui verifica se esse usuario nao esta cadastrado para outra depredacao */
	public boolean verificaRelacaoCandidatura(int idPessoa, int idMarcacao)
			throws SQLException {

		String sql = "select * " + "from cadidatura_resolucao_problema "
				+ "where id_pessoa_fisica = ? and "
				+ "id_marcacao_depredacao <> ? ";

		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, idPessoa);
		preparedStatement.setInt(2, idMarcacao);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			return true;
		}
		return false;
	}

	/* aqui cadastra no banco a relacao usuario x problema */
	public boolean setCandidatura(int idPessoa, int idMarcacao)
			throws SQLException {

		String sql = "insert into cadidatura_resolucao_problema "
				+ "(id_pessoa_fisica, " + "id_marcacao_depredacao) "

				+ "values (?, ?)";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, idPessoa);
		preparedStatement.setInt(2, idMarcacao);
		preparedStatement.executeUpdate();
		return true;

	}

	/* aqui cadastra no banco a relacao usuario x problema */
	public boolean UpdateStatus(int idMarcacao, int status, String html)
			throws SQLException {

		String sql = "update marcacao_depredacao set "
				+ " status_marcacao = ?, " 
				+ " html_depredacao = ?"
				+ " where id_marcacao_depredacao = ? ";

		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, status);
		preparedStatement.setString(2, html);
		preparedStatement.setInt(3, idMarcacao);
		String sSql = preparedStatement.toString();

		preparedStatement.executeUpdate();

		
		return true;

	}

	// ################# VOTACAO ####################
	
	public boolean GravaVotacao(int idMarcacao, int idPessoa, int ivoto)
			throws SQLException {
		boolean bResp = false;
		try {

			String sql = " insert into voto_depredacao "
					+ " (id_pessoa_fisica, " 
					+ " id_marcacao_depredacao,"
					+ " like_marcacao) " 
					+ " values (?, ?, ?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idPessoa);
			preparedStatement.setInt(2, idMarcacao);
			preparedStatement.setInt(3, ivoto);

			preparedStatement.executeUpdate();

			bResp = true;

		} catch (Exception e) {
			bResp = false;
		}

		return bResp;

	}
	
	
	
	public boolean VerificaSeUsuarioJaVotou(int idMarcacao, int idPessoa)throws SQLException {

			String sql = " select * from  voto_depredacao "
					+ " where id_pessoa_fisica = ? " 
					+ " and id_marcacao_depredacao = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idPessoa);
			preparedStatement.setInt(2, idMarcacao);
			preparedStatement.executeQuery();


			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return true;
			}
		return false;
	}
	
	
	public int BuscaQuantVotacao(int idMarcacao, int TipoLikes)throws SQLException {
		int Quant= 0;
		String sql = " select COUNT(*) as QUANT from voto_depredacao "
				+ " where id_marcacao_depredacao = ? and like_marcacao = ?";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, idMarcacao);
		preparedStatement.setInt(2, TipoLikes);


		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			Quant = resultSet.getInt("QUANT");
		}
		return Quant;
}



/* VERIFICACOES PARA ADD BENEFICIO */

/* Aqui verifica se esse usuario nao esta cadastrado para outra depredacao */
	public boolean VerificaSeTemBeneficio(int idMarcacao) throws SQLException {

		String sql = "select COUNT(id_marcacao_depredacao) "
				+ "from beneficio_empresa "
				+ "where id_marcacao_depredacao = ?";

		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, idMarcacao);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			return true;
		}
		return false;
	}


public void cadastrarBeneficio(BeneficioEmpresa oBeneficio)
			throws SQLException {
		String sql = "insert into beneficio_empresa "
				+ "(id_marcacao_depredacao,"
				+ "id_pessoa_juri_fez_narcacao, "
				+ "descricao, "
				+ "values (?, ?, ?)";

		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, oBeneficio.getIdMarcacaoDepredacao());
		preparedStatement.setString(2, oBeneficio.isCadidatoResolverProblema());
		preparedStatement.setString(3, oBeneficio.getDescricao());

		String dados = preparedStatement.toString();

		preparedStatement.executeUpdate();
	}










}
