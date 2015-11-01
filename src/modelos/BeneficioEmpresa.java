package modelos;

import java.io.Serializable;

//ESCREVI AQUI 
public class BeneficioEmpresa extends PessoaJuridica implements Serializable{
	private int idMarcacaoDepredacao;
	private String descricaoBeneficio;
	private boolean aprovado;
	public int getIdMarcacaoDepredacao() {
		return idMarcacaoDepredacao;
	}
	public void setIdMarcacaoDepredacao(int idMarcacaoDepredacao) {
		this.idMarcacaoDepredacao = idMarcacaoDepredacao;
	}
	public String getDescricaoBeneficio() {
		return descricaoBeneficio;
	}
	public void setDescricaoBeneficio(String descricaoBeneficio) {
		this.descricaoBeneficio = descricaoBeneficio;
	}
	public boolean isAprovado() {
		return aprovado;
	}
	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}
	
	
	
}
