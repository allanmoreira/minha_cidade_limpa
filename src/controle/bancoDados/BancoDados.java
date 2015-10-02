package controle.bancoDados;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

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
	
	/***
	 * Cadastro de denúncias feitas por pessoas
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
        preparedStatement.setString(1, oMarcacao.getid());
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
	 * Cadastra os dados de login do usuário. Está vazio porque não consegui terminar, 
	 * e porque não possui os campos de cadastro na interface.
	 * 
	 * @param idPessoaFisica
	 * @return idLogin
	 * @throws SQLException
	 */
	public int geraLoginUsuario(String username, String senha, boolean pfOuPj) throws SQLException {
		String sql = "insert into login "
				+ "(username, "
				+ "senha, "
				+ "pf_ou_pj) "
				
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
	 * Valida se o usuário não existe no banco de dados
	 * 
	 * @param ussername do novo usuário
	 * @return TRUE se usuário não cadastrado, e FALSE se usuário cadastrado
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
}
