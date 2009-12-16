package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class BuscadorResultado{

	public static void CreateNews( int arquivoID, LinkedList<String> trechos ) throws SQLException{
		
		if( trechos.size() == 0 ) return;
		
		String query = "";
		for( String s : trechos ){
			query += ",(" + arquivoID + "," + DBClass.dbValue(s) + ")"; 
		}
		query = "insert into buscador_resultados (arquivo_id,trecho) values " + query.substring(1);
		
		DBClass.execute(query);

	}
	
	public static ResultSet LoadFromDB( int arquivoID ) throws SQLException{

	  	Statement st = DBClass.getMyConnection().createStatement();

    	return st.executeQuery( "select * from buscador_resultados where arquivo_id=" + arquivoID );

	}
	
	public static int[] TrechosBuscados( int arquivo_id ) throws SQLException{
		
		int[] result = {0,0};
		
	  	Statement st = DBClass.getMyConnection().createStatement();

	  	ResultSet rs = st.executeQuery( "select count(*) quant from buscador_resultados where arquivo_id=" + arquivo_id );
	  	rs.next();
		
	  	result[0] = rs.getInt("quant");

	  	rs = st.executeQuery( "select count(*) quant from buscador_resultados where concluido=1 and arquivo_id=" + arquivo_id );
	  	rs.next();

	  	result[1] = rs.getInt("quant");
	  	
	  	return result;
		
	}
	
	public static void UpdateCount( int id, int count ) throws SQLException{

	  	Statement st = DBClass.getMyConnection().createStatement();

    	st.executeQuery( "update buscador_resultados set count = " + count + " where buscador_resultado_id=" + id );

	}

	public static int[] TrechosComHits( int arquivo_id, int teta ) throws SQLException{
		
		int[] result = {0,0};
		
	  	Statement st = DBClass.getMyConnection().createStatement();

	  	ResultSet rs = st.executeQuery( "select count(*) quant from buscador_resultados where arquivo_id=" + arquivo_id );
	  	rs.next();
		
	  	result[0] = rs.getInt("quant");

	  	rs = st.executeQuery( "select count(*) quant from buscador_resultados where count>0 and count<=" + teta + " and arquivo_id=" + arquivo_id );
	  	rs.next();

	  	result[1] = rs.getInt("quant");
	  	
	  	return result;
		
	}	
}