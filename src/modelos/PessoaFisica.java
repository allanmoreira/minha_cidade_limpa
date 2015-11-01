package modelos;

import java.io.Serializable;
import java.sql.Date;

import controle.conversaoDados.Data;

public class PessoaFisica extends  Login implements Serializable{
	private String nome, cpf, email, dataNascimentoString, telefone;
	private int idPessoaFisica;
	private Date dataNascimento;
	
	
	public int getIdPessoaFisica() {
		return idPessoaFisica;
	}
	public void setIdPessoaFisica(int idPessoaFisica) {
		this.idPessoaFisica = idPessoaFisica;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDataNascimentoString() {
		setDataNascimentoString();
		return dataNascimentoString;
	}
	public void setDataNascimentoString() {
		Data data = new Data();
		dataNascimentoString = data.converteDataParaString(dataNascimento);
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	
	
}
