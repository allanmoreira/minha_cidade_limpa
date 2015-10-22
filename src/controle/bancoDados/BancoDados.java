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
//        String url = "jdbc:mysql://localhost:3306/minha_cidade_limpa_bd?zeroDateTimeBehavior=convertToNull";
		String url = "jdbc:mysql://104.131.19.51:3306/minha_cidade_limpa_bd?zeroDateTimeBehavior=convertToNull";

        String usuario = "trab_ger_proj";
        String senha = "cidadelimpa20152";
        
        connection = DriverManager.getConnection(url, usuario, senha);
    }

    public void encerrarConexao() throws SQLException {
        preparedStatement.close();
        connection.close();
    }

	public void cadastrarPessoaFisica(PessoaFisica pessoaFisica) throws SQLException {
		String sql = "insert into pessoa_fisica "
				+ "(nome, "
				+ "cpf, "
				+ "email, "
				+ "data_nascimento, "
				+ "telefone, "
				+ "id_login) "
				
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
		
		String sql = "select * from pessoa_fisica pf, login l "
				+ "where "
				+ "l.id_login = pf.id_login and "
				+ "l.id_login = ?";
		
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
        
        resultSet.close();
        
        return pf;
	}
	
	/***
	 * Cadastro de den�ncias feitas por pessoas
	 * @param oMarcacao
	 * @throws SQLException
	 */
	public void cadastrarMarcacao(MarcacaoDepredacao oMarcacao) throws SQLException {
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
				+ "status) "				
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, oMarcacao.getIdMarcacaoDepredacao());
        preparedStatement.setString(2, oMarcacao.getTipoDepredacao());
        preparedStatement.setString(3, oMarcacao.getDescricao());
        preparedStatement.setString(4, oMarcacao.getDataMarcacao());
        preparedStatement.setBoolean(5, oMarcacao.isCadidatoResolverProblema());
        preparedStatement.setInt(6, 0);
        preparedStatement.setString(7, oMarcacao.getHtml());
        preparedStatement.setString(8,oMarcacao.getPosLat());
        preparedStatement.setString(9,oMarcacao.getPosLon());
        preparedStatement.setString(10,oMarcacao.getStatus());

        preparedStatement.executeUpdate();
	}
	
	
	
	/**
	 * Cadastra os dados de login do usu�rio. Est� vazio porque n�o consegui terminar, 
	 * e porque n�o possui os campos de cadastro na interface.
	 * 
	 * @param idPessoaFisica
	 * @return idLogin
	 * @throws SQLException
	 */
	public int geraLoginUsuario(String username, String senha, boolean pfOuPj) throws SQLException {
		String sql = "insert into login "
				+ "(username, "
				+ "senha, "
				+ "is_pf) "
				
				+ "values (?, ?, ?)";
		
		
		preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
	 * @param ussername do novo usu�rio
	 * @return TRUE se usu�rio n�o cadastrado, e FALSE se usu�rio cadastrado
	 * @throws SQLException
	 */
	public boolean usernameNaoCadastrado(String username) throws SQLException {
		
        String sql = "select username "
        		+ "from login "
                + "where username = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
        	return false;
        }
        
		return true;
	}

	public void cadastrarPessoaJuridica(PessoaJuridica pessoaJuridica) throws SQLException {
		String sql = "insert into pessoa_juridica "
				+ "(nome, "
				+ "cnpj, "
				+ "email, "
				+ "endereco, "
				+ "telefone, "
				+ "id_login) "
				
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
		String sql = "select * from login "
				+ "where username = ? and "
				+ "senha = ?";
		
		preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, login.getUsername());
        preparedStatement.setString(2, login.getSenha());
        
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
        	return true;
        }
        
		return false;
	}

	public Login dadosUsuarioLogado(Login login) throws SQLException {
		String sql = "select * from login "
				+ "where username = ? and "
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
		
		String sql = "select * from pessoa_juridica pj, login l "
				+ "where "
				+ "l.id_login = pj.id_login and "
				+ "l.id_login = ?";
		
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

	public void editarDadosLogin(int idLogin, String username, String senha) throws SQLException {
		String sql = "update login "
				+ "set username = ?, "
				+ "senha = ? "
				+ "where id_login = ?";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, senha);
		preparedStatement.setInt(3, idLogin);
		preparedStatement.executeUpdate();
	}

	public void editarCadastroPessoaFisica(PessoaFisica pessoaFisica) throws SQLException {
		String sql = "update pessoa_fisica "
				+ "set nome = ?, "
				+ "cpf = ?, "
				+ "email = ?, "
				+ "data_nascimento = ?, "
				+ "telefone = ? "
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

	public void editarCadastroPessoaJuridica(PessoaJuridica pessoaJuridica) throws SQLException {
		String sql = "update pessoa_juridica "
				+ "set nome = ?, "
				+ "cnpj = ?, "
				+ "email = ?, "
				+ "endereco = ?, "
				+ "telefone = ? "
				+ "where id_login = ? ";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, pessoaJuridica.getNome());
        preparedStatement.setString(2, pessoaJuridica.getCnpj());
        preparedStatement.setString(3, pessoaJuridica.getEmail());
        preparedStatement.setString(4, pessoaJuridica.getEndereco());
        preparedStatement.setString(5, pessoaJuridica.getTelefone());
        preparedStatement.setInt(6, pessoaJuridica.getIdLogin());

        preparedStatement.executeUpdate();
	}
	
	public ArrayList<MarcacaoDepredacao> listaMarcacoesCadastradas() throws SQLException{
		ArrayList<MarcacaoDepredacao> listaMarcacoesCadastradas = new ArrayList<MarcacaoDepredacao>();
		String sql = "select * from marcacao_depredacao";
		
		preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()){
			MarcacaoDepredacao md = new MarcacaoDepredacao();
			md.setIdMarcacaoDepredacao(resultSet.getInt("id_marcacao_depredacao"));
			md.setTipoDepredacao(resultSet.getString("tipo_depredacao"));
			md.setDescricao(resultSet.getString("descricao"));
			md.setDataMarcacao(resultSet.getString("data_marcacao"));
			md.setIdPessoaFisicaFezMarcacao(resultSet.getInt("id_pessoa_fisica_fez_narcacao"));
			md.setHtml(resultSet.getString("html_depredacao"));
			md.setPosLat(resultSet.getString("lat"));
			md.setPosLon(resultSet.getString("lon"));
			md.setStatus(resultSet.getString("status"));
			
			listaMarcacoesCadastradas.add(md);
		}
		return listaMarcacoesCadastradas;
	}
}
