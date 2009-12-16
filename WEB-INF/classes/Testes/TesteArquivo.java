package Testes;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

import db.Arquivo;
import db.DBClass;
import db.Login;
import db.*;


public class TesteArquivo {

	@DataPoint private static String fileName1 = "arquivo1.pdf";
	@DataPoint private static String fileName2 = "arquivo2.pdf";

	@DataPoint private static Login login1;
	@DataPoint private static Login login2;

	private static int arquivoID1;
	private static int arquivoID2;

	@BeforeClass
	public static void setUp() throws Exception {

		System.out.println( "hello" );
		DBClass.conectar();
		System.out.println("aushdausdh");
		System.out.println( "hello" );

		login1 = Login.CreateNew("George", "12345");
		login2 = Login.CreateNew("Fernando", "54321");

		System.out.println( arquivoID1 );

		arquivoID1 = Arquivo.CreateNew(fileName1, login1).getID();
		arquivoID2 = Arquivo.CreateNew(fileName2, login2).getID();

	}

	@Test
	public void testaArquivoNome() throws Exception {

		Arquivo arquivo1 = Arquivo.FromDB(arquivoID1);
		Arquivo arquivo2 = Arquivo.FromDB(arquivoID2);

		assertThat(arquivo1.getNome(), is(fileName1));
		assertThat(arquivo2.getNome(), is(fileName2));

	}

	@Test
	public void testaArquivoDono() throws Exception {

		Arquivo arquivo1 = Arquivo.FromDB(arquivoID1);
		Arquivo arquivo2 = Arquivo.FromDB(arquivoID2);

		assertThat(arquivo1.getDono().getLogin(), is(login1.getLogin()));
		assertThat(arquivo2.getDono().getLogin(), is(login2.getLogin()));

	}

	@Test
	public void testaArquivoQuebra() throws Exception{

		String testText = "1 2 3 4  5  6  7   8   9  10   \t \n\r 11 12 \t \n 13 14 15 16";

		Arquivo arquivo1 = Arquivo.FromDB(arquivoID1);

		arquivo1.setTextoExtraido(testText);

		arquivo1.definirTexto(3, 3, 3);

		ResultSet rs = BuscadorResultado.LoadFromDB(arquivo1.getID());

		assertThat(rs.getString("trecho"), is("4 5 6"));
		assertThat(rs.next(), is(true));
		assertThat(rs.getString("trecho"), is("7 8 9"));
		assertThat(rs.next(), is(true));
		assertThat(rs.getString("trecho"), is("10 11 12"));
		assertThat(rs.next(), is(true));
		assertThat(rs.getString("trecho"), is("13 14 15"));
		assertThat(rs.next(), is(false));

	}

	@AfterClass
	public static void endTest() throws SQLException{

		Arquivo arquivo1 = Arquivo.FromDB(arquivoID1);
		Arquivo arquivo2 = Arquivo.FromDB(arquivoID2);

		arquivo1.DeleteFromDB();
		arquivo2.DeleteFromDB();

		login1.DeleteFromDB();
		login2.DeleteFromDB();

	}

}
