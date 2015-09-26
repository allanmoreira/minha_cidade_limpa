package controle.conversaoDados;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.joda.time.DateTime;

public class Data {

	SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
	
	/**
	 * Pega a data atual do sistema 
	 * @return a data em java.util.Date 
	 */
	public java.util.Date getDataAtual(){
		DateTime hoje = new DateTime();
		return hoje.toDate();
	}
	
	/**
	 * Converte a data informada pelo usuário em data no formato para o banco de dados
	 * @param data no formado [dd/mm/yyyy]
	 * @return data convertida para o banco de dados
	 */
	public Date converteStringParaData(String dataString) {
		Date data = null;
		try {
			data = new Date(formatoData.parse(dataString).getTime());
		} catch (ParseException e) { }
		return data;
	}

	public String converteDataParaString(Date data) {
		String dataString = formatoData.format(data);
		return dataString;
	}
	
	public String converteDataParaString(java.util.Date data){
		String dataString = formatoData.format(data);
		return dataString;
	}
		
	public Date pegaDataAtualEConverteParaDatasql() {
		java.util.Date dataUtil = new java.util.Date();  
		java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());
		
		return dataSql;
	}
	
	public Date converteDataUtilParaDataSql(java.util.Date dataUtil) {
		Date dataSql = new Date(dataUtil.getTime());
		return dataSql;
	}

	public java.util.Date converteDataSqlParaDataUtil(Date dataSql) {
		java.util.Date dataUtil = new java.util.Date(dataSql.getTime());
		return dataUtil;
	}
}
