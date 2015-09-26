package controle.bancoDados;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import modelos.PessoaFisica;

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
	
	/**
	 * Cadastra os dados de login do usuário. Está vazio porque não consegui terminar, e porque não possui os campos de cadastro na interface.
	 * 
	 * @param idPessoaFisica
	 * @return idLogin
	 * @throws SQLException
	 */
	public int geraLoginUsuario(boolean pfOuPj) throws SQLException {
		String sql = "insert into login "
				+ "(username, "
				+ "senha, "
				+ "pf_ou_pj) "
				
				+ "values (?, ?, ?)";
		
		preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, "");
		preparedStatement.setString(2, "");
		preparedStatement.setBoolean(3, pfOuPj);
		
		preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        int idLogin = resultSet.getInt(1);
        
        return idLogin;
	}
}
