package testes;
import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import controle.bancoDados.BancoDados;


public class TestCaseServlet {

	@Test
	public void testUsuarioNaoCadastradoNoBanco() {
		BancoDados bco = new BancoDados();
		
		String un = "usuario_teste";
		
		try {
			bco.conectarAoBco();
			boolean expected = true;
			boolean actual = bco.usernameNaoCadastrado(un);
			bco.encerrarConexao();
			
			assertEquals(expected, actual);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
