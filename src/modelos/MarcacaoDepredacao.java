package modelos;

import java.sql.Date;

import controle.conversaoDados.Data;

public class MarcacaoDepredacao {
	private String tipoDepredacao, descricao, status, dataMarcacaoString;
	private Date dataMarcacao;
	private boolean cadidatoResolverProblema;
	private int idPessoaFisicaFezMarcacao;
	private String posLon, posLat, html;
	
	
	
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
	public Date getDataMarcacao() {
		return dataMarcacao;
	}
	public void setDataMarcacao(Date dataMarcacao) {
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
		setDataMarcacaoString();
		return dataMarcacaoString;
	}
	public void setDataMarcacaoString() {
		Data data = new Data();
		dataMarcacaoString = data.converteDataParaString(dataMarcacao);
	}
	
	
}
