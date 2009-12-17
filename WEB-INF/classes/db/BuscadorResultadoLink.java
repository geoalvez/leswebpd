package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractList;

public class BuscadorResultadoLink{

	public static void CreateNews( int buscadorResultadoID, AbstractList<String> links ) throws SQLException{

		if( links.size() == 0 ) return;
		
		String query = "";
		for( String s : links ){
			query += ",(" + buscadorResultadoID + "," + DBClass.dbValue(s) + ")"; 
		}
		query = "insert into buscador_resultados_links (buscador_resultado_id,link) values " + query.substring(1);
		System.out.println(query);
		DBClass.execute(query);

	}
	
	public static ResultSet LoadFromDB( int buscadorResultadoID ) throws SQLException{

	  	Statement st = DBClass.getMyConnection().createStatement();

    	return st.executeQuery( "select * from buscador_resultados_links where buscador_resultado_id=" + buscadorResultadoID );

	}
	
	public static void DeleteLinks( int buscadorResultadoID ) throws SQLException{
		DBClass.execute("delete from buscador_resultados_links where buscador_resultado_id=" + buscadorResultadoID );
	}

}