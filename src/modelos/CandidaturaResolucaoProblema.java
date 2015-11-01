package modelos;

import java.io.Serializable;

public class CandidaturaResolucaoProblema implements Serializable {
	private int idPessoaFisica, idMarcacaoDepredacao;

	public int getIdPessoaFisica() {
		return idPessoaFisica;
	}

	public void setIdPessoaFisica(int idPessoaFisica) {
		this.idPessoaFisica = idPessoaFisica;
	}

	public int getIdMarcacaoDepredacao() {
		return idMarcacaoDepredacao;
	}

	public void setIdMarcacaoDepredacao(int idMarcacaoDepredacao) {
		this.idMarcacaoDepredacao = idMarcacaoDepredacao;
	}
	
	
}
