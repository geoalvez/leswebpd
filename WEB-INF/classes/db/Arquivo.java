package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;

import connect.Buscador;
import connect.BuscadorGoogleResultado;

public class Arquivo extends DBClass{

	private Arquivo() throws SQLException {
		super();
	}


	private static String[] situacoes = {"Não avaliado", "Texto extraído", "Texto Definido", "Avaliado" };
	private static String[] next_situacoes = {"Extrair texto", "Definir texto", "Avaliar o arquivo", "" };
	private int id;
	private String nome;
	private Login dono;
	private Date dataEnvio;
	private int situacao;
	private String textoExtraido = "";

	//........... Properties da classe
	public int getID(){
		return id;
	}

	public String getNome(){
		return nome;
	}
	public void setNome( String value ){
		this.nome = value;
		addUpdateToDump( "nome", value );
	}

	public Login getDono(){
		return dono;
	}
	public void setDono( Login value ){
		this.dono = value;
		if( value == null ){
			addUpdateToDump( "dono", new DBNull() );
		}else{
			addUpdateToDump( "dono", value.getLogin() );
		}
	}

	public Date getDataEnvio(){
		return this.dataEnvio;
	}
	public void setDataEnvio( Date value ){
		this.dataEnvio = value;
		addUpdateToDump("data_envio", value);
	}

	public int getSituacao(){
		return situacao;
	}
	public void setSituacao( int value ){
		this.situacao = value;
		addUpdateToDump("situacao", value);
	}
	public String getSituacaoStr(){
		return situacoes[getSituacao()];
	}
	public String getNextSituacaoStr(){
		if( getSituacao() == 1 ){
			return
					"<form method=post action=arquivo.proceed.jsp target=hidden_frame>" +
					"<input type=hidden name=file value=" + this.getID() + ">" +
					"<table>" +
						"<tr><td>Offset do início do arquivo:</td><td><input name=start_index></td></tr>" +
						"<tr><td>Tamanho da janela de busca:</td><td><input name=tamanho_janela></td></tr>" +
						"<tr><td>Passo de busca:</td><td><input name=passo></td></tr>" +
						"<tr><td colspan=2><input type=submit value='Definir o texto'></td></tr>" +
					"</table>" +
					"</form>";
		}
		return next_situacoes[ getSituacao() ];
	}

	public String getTextoExtraido(){
		return this.textoExtraido;
	}
	public void setTextoExtraido( String value ){
		this.textoExtraido = value;
		addUpdateToDump( "texto_extraido", value );
	}

	public void definirTexto( int start_index, int tamanho_janela, int passo ) throws Exception{

		String texto = this.getTextoExtraido();

		texto = texto.replace("\n", " ");
		texto = texto.replace("\r", " ");
		texto = texto.replace("\t", " ");
		texto = texto.replace("  ", " ");

		String[] palavras = texto.split(" ");

		if( start_index >= palavras.length ) throw new Exception("O index de start é maior que a quantidade de palavras no texto");

		DBClass.getMyConnection().createStatement().execute("delete from buscador_resultados where arquivo_id=" + this.getID() );

		LinkedList<String> trechos = new LinkedList<String>();

		while( start_index < palavras.length ){

			String linha = "";

			int k = 0, count = 0;
			while( count < tamanho_janela && start_index + k < palavras.length ){

				palavras[start_index+k] = palavras[start_index+k].trim();

				if( palavras[start_index+k].length() != 0 ){
					linha += palavras[start_index + k] + " ";
					count++;
				}
				k++;

			}

			linha = linha.trim().replace("  "," ");

			start_index += passo;

			if( linha.length()>0 ) trechos.add(linha);

		}

		BuscadorResultado.CreateNews(this.getID(), trechos);

	}
	
	public boolean trechosConcluidos() throws SQLException{
		
		ResultSet rs = getMyConnection().createStatement().executeQuery("select count(*) quant from buscador_resultados where concluido=0 and arquivo_id=" + this.getID());
		rs.next();
		
		return rs.getInt("quant") == 0;
		
	}

	//................. Fim de Properties da classe

	private void createNew( String nome, Login dono ) throws SQLException{

		Hashtable<String,String> campos = new Hashtable<String,String>();
		campos.put( "nome", dbValue(nome) );
		campos.put( "dono", dbValue(dono.getLogin()) );
		campos.put( "data_envio", "now()" );

		super.insertDB(campos);
		loadFromDB( getLastInsertedID() );

	}

	private void loadFromDB( int id ) throws SQLException{

		super.readDB( "arquivo_id=" + id );

		this.id = loadInt("arquivo_id");
		this.nome = loadString("nome");
		this.dono = Login.FromDB( loadString("dono") );
		this.dataEnvio = loadDate("data_envio");
		this.situacao = loadInt("situacao");
		this.textoExtraido = loadString("texto_extraido");

	}

	@Override
	public String dbIdentifier() {
		return "arquivo_id=" + getID();
	}

	@Override
	public String getMyTable() {
		return "arquivos";
	}

	public static Arquivo FromDB( int id ) throws SQLException{

		Arquivo a = new Arquivo();
		a.loadFromDB(id);
		return a;

	}

	public static Arquivo CreateNew( String nome, Login dono ) throws SQLException{

		Arquivo a = new Arquivo();
		a.createNew(nome, dono);
		return a;
	}

	public static String TranslateSituacao( int situacao ){

		return situacoes[situacao];

	}
	
	public static void main( String args[] ) throws Exception{
		
		DBClass.conectar();
		
		ResultSet rs = DBClass.getMyConnection().createStatement().executeQuery("select * from buscador_resultados where id=5618");
		rs.next();
		
		BuscadorGoogleResultado bg = Buscador.buscarNoGoogle("jackson");
		
		System.out.println( bg.count + "," + bg.links.size() );

	}

}
