package db;

import java.sql.SQLException;
import java.util.Hashtable;

public class Login extends DBClass{

	private String login = "";
	private String senha = "";

	//..... Properties da classe
	public String getLogin(){
		return login;
	}
	public String getSenha(){
		return senha;
	}
	public void setSenha(String value){
		this.senha = value;
		addUpdateToDump("senha", value);
	}
	//.... Fim de Properties da classe

	private void createNew( String login, String senha ) throws SQLException{
		
		Hashtable<String,String> campos = new Hashtable<String,String>();
		
		campos.put( "login", dbValue(login) );
		campos.put( "senha", dbValue(senha) );
		
		super.insertDB(campos);
		loadFromDB( login );
		
	}
	
	private void loadFromDB( String login ) throws SQLException{
		
		super.readDB( "login=" + dbValue(login) );
		
		this.login = loadString("login");
		this.senha = loadString("senha");
		
	}
	
	@Override
	public String dbIdentifier() {
		return "login=" + dbValue(getLogin());
	}

	@Override
	public String getMyTable() {
		return "login";
	}

	private Login(String login) throws SQLException {
		super();
		loadFromDB(login);
	}
	private Login( boolean createNew, String login, String senha ) throws SQLException{
		super();
		this.createNew(login, senha);
	}

	public static Login FromDB( String login ) throws SQLException{
		
		Login l = new Login(login);
		return l;
		
	}
	
	public static Login FromLogin( String login, String senha ) throws SQLException{
		
		Login l = new Login(login);
		if( !l.getSenha().equals(senha) ) throw new SQLException("Login e senha incompatíveis");
		return l;
		
	}

	public static Login CreateNew( String login, String senha ) throws SQLException{
		
		Login l = new Login( true, login, senha );
		return l;
		
	}
	
	public static void main( String args[] ) throws SQLException{
		
		DBClass.conectar();
		
		Login.CreateNew("hacjsib","jurema");
		System.out.println("ausdhasudh");
		
	}
	
}
