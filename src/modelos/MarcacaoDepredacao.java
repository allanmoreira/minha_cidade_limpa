package modelos;

import java.sql.Date;

import controle.conversaoDados.Data;

public class MarcacaoDepredacao {
	private String tipoDepredacao, descricao, status, dataMarcacaoString;
	private String dataMarcacao;
	private boolean cadidatoResolverProblema;
	private int idPessoaFisicaFezMarcacao;
	private String posLon, posLat, html, id;
	
	
	
	public String getid() {
		return id;
	}
	public void setid(String id) {
		this.id = id;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getPosLon() {
		return posLon;
	}
	public void setPosLon(String posLon) {
		this.posLon = posLon;
	}
	public String getPosLat() {
		return posLat;
	}
	public void setPosLat(String posLat) {
		this.posLat = posLat;
	}
	public void setDataMarcacaoString(String dataMarcacaoString) {
		this.dataMarcacaoString = dataMarcacaoString;
	}
	public String getTipoDepredacao() {
		return tipoDepredacao;
	}
	public void setTipoDepredacao(String tipoDepredacao) {
		this.tipoDepredacao = tipoDepredacao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDataMarcacao() {
		return dataMarcacao;
	}
	public void setDataMarcacao(String dataMarcacao) {
		this.dataMarcacao = dataMarcacao;
	}
	public boolean isCadidatoResolverProblema() {
		return cadidatoResolverProblema;
	}
	public void setCadidatoResolverProblema(boolean cadidatoResolverProblema) {
		this.cadidatoResolverProblema = cadidatoResolverProblema;
	}
	public int getIdPessoaFisicaFezMarcacao() {
		return idPessoaFisicaFezMarcacao;
	}
	public void setIdPessoaFisicaFezMarcacao(int idPessoaFisicaFezMarcacao) {
		this.idPessoaFisicaFezMarcacao = idPessoaFisicaFezMarcacao;
	}
	public String getDataMarcacaoString() {
		return dataMarcacaoString;
	}

	
	
}
