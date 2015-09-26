package modelos;

public class Login {
	private String username, senha;
	private boolean pfOuPj;
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
	 * @return true se usuario for PF, false se usuário for PJ
	 */
	public boolean isPfOuPj() {
		return pfOuPj;
	}

	public void setPfOuPj(boolean pfOuPj) {
		this.pfOuPj = pfOuPj;
	}
	
	
}
