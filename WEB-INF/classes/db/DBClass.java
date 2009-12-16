package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import util.Functions;

public abstract class DBClass{

	private static Connection con = null;
	private ResultSet rs = null;

	private Hashtable<String,String> update_dump = new Hashtable<String,String>();

	public static Connection getMyConnection(){
		return con;
	}
	public abstract String getMyTable();
	public abstract String dbIdentifier();

	public static void conectar() throws SQLException{
		
		con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/webpd","root", "jurema");
		
	}

	public boolean equals( DBClass quem ){
		return quem.dbIdentifier().equals( this.dbIdentifier() );
	}

    protected void readDB( String identifier ) throws SQLException{
    	
    	Statement st = getMyConnection().createStatement();

    	this.rs = st.executeQuery( "select * from " + this.getMyTable() + " where " + identifier );

    	if( !rs.next() ) throw new SQLException("Registro não encontrado no banco de dados em: " + this.getMyTable() + " - " + identifier );
	
	}

	protected void insertDB( Hashtable<String,String> campos ) throws SQLException{
	
		String[] keys = Functions.hashtableKeys(campos);
		String[] values = Functions.hashtableValues(campos);
		
		String sql = "insert into " + this.getMyTable() + "(" + Functions.join(keys, ",") + ") values( " + Functions.join(values,",") + " )";

		getMyConnection().createStatement().executeUpdate(sql);
		
	}

	protected boolean loadIsNull( String field ) throws SQLException{ return this.rs.getString(field) == null; }
	protected String loadString( String field ) throws SQLException{ return this.rs.getString( field ); }
	protected int loadInt( String field ) throws SQLException{ return this.rs.getInt( field ); }
	protected boolean loadBoolean( String field ) throws SQLException{ return this.rs.getBoolean( field ); }
	protected double loadDouble( String field ) throws SQLException{ return this.rs.getDouble( field ); }
	protected Date loadDate( String field ) throws SQLException{ return this.rs.getDate( field ); }

	protected void addUpdateToDump( String propName, Date value ){
		addUpdateToDump(propName, value, false);
	}
	protected void addUpdateToDump( String propName, Date value, boolean apenasData ){
		Format f = apenasData ? new SimpleDateFormat("yyyy-MM-dd") : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		addUpdateToDump(propName, f.format( value ) );
	}
	protected void addUpdateToDump( String propName, String value ){
		addUpdateToDump(propName, value, false);
	}
	protected void addUpdateToDump( String propName, int value ){
		addUpdateToDump(propName, value + "", false);
	}
	protected void addUpdateToDump( String propName, DBNull value ){
		addUpdateToDump(propName, "", true);
	}
    protected void addUpdateToDump( String propName, String valor, boolean isNull ){
		if( ! isNull ){
			add_to_dump( propName, propName + "=" + dbValue(valor) );
		}else{
			add_to_dump( propName, propName + "= null" );
		}
	}

    private void add_to_dump( String propName, String action ){
		update_dump.put( propName, action );
	}

    public void writeToDB() throws SQLException{

		if( update_dump.size() == 0 ) return;
		
		String[] values = Functions.hashtableValues(update_dump);
		
        String sql = "update " + this.getMyTable() + " set " + Functions.join(values,",") + " where " + dbIdentifier();

		getMyConnection().createStatement().execute( sql );

        clearUpdateDump();
		
    }

    protected void clearUpdateDump(){
        this.update_dump = new Hashtable<String,String>();
    }
	
	public void DeleteFromDB() throws SQLException{
		getMyConnection().createStatement().execute( "delete from " + getMyTable() + " where " + dbIdentifier() ) ;
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static String dbValue( String value ){
		return "'" + value.replace("'", "''") + "'";
	}
	public static String dbValue( double value ){
		return dbValue( value + "" );
	}
	
  	public static int getLastInsertedID() throws SQLException {

		int retval = 0;    

    	Statement stmt = DBClass.getMyConnection().createStatement();

    	String query = "SELECT @@IDENTITY";

    	ResultSet rs = stmt.executeQuery(query);

    	while (rs.next()) {
      		retval = rs.getInt(1);
    	}

    	stmt.close();

    	return retval;
  	}

  	public static void execute( String query ) throws SQLException{

  		DBClass.getMyConnection().createStatement().execute(query);
  		
  	}

}