package modelos;

public class Login {
	private String username, senha;
	private boolean PF;
	private int idLogin;

	public int getIdLogin() {
		return idLogin;
	}

	public void setIdLogin(int idLogin) {
		this.idLogin = idLogin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	/**
	 * 
	 * @return true se usuario for PF, false se usu�rio for PJ
	 */
	public boolean isPF() {
		return PF;
	}

	public void setPF(boolean pF) {
		PF = pF;
	}
	
}
