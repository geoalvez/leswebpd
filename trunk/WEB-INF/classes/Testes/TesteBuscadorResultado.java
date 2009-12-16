package Testes;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

import db.Arquivo;
import db.DBClass;
import db.Login;

public class TesteBuscadorResultado {

	@DataPoint private static Login login1;
	@DataPoint private static Login login2;

	@DataPoint private static Arquivo arquivo1;
	@DataPoint private static Arquivo arquivo2;
	
//	@DataPoint private static String buscadorResultadoTrecho1 = "This is a PDF file";
//	@DataPoint private static String buscadorResultadoTrecho2 = "This is another PDF file";
//
//	@DataPoint private static String badBuscadorResultadoTrecho1 = "This is not a PDF file";
//	@DataPoint private static String badBuscadorResultadoTrecho2 = "This is not another PDF file";
//	
//	private static int buscadorResultadoID1;
//	private static int buscadorResultadoID2;

	@BeforeClass
	public static void setUP() throws SQLException {

		DBClass.conectar();
		
		login1 = Login.CreateNew("George", "12345");
		login2 = Login.CreateNew("Fernando", "54321");
		
		arquivo1 = Arquivo.CreateNew("arquivo1.pdf", login1);
		arquivo2 = Arquivo.CreateNew("arquivo2.pdf", login2);

//		Os testes desta classe foram movidos para o teste de arquivos
//		buscadorResultadoID1 = BuscadorResultado.CreateNew(arquivo1, buscadorResultadoTrecho1).getID();
//		buscadorResultadoID2 = BuscadorResultado.CreateNew(arquivo2, buscadorResultadoTrecho2).getID();
		
	}
	
	@Test
	public void testaTrecho() throws SQLException{
		
//		Os testes desta classe foram movidos para o teste de arquivos
//		BuscadorResultado b1 = BuscadorResultado.FromDB(buscadorResultadoID1);
//		BuscadorResultado b2 = BuscadorResultado.FromDB(buscadorResultadoID2);
//		
//		assertThat(b1.getTrecho(), is(buscadorResultadoTrecho1));
//		assertThat(b2.getTrecho(), is(buscadorResultadoTrecho2));
//
//		assertThat(b1.getTrecho(), is(not(badBuscadorResultadoTrecho1)));
//		assertThat(b2.getTrecho(), is(not(badBuscadorResultadoTrecho2)));
//		
//		assertThat(b1.getTrecho(), is(not("")));
//		assertThat(b2.getTrecho(), is(not("")));

	}

	@Test
	public void testaArquivo() throws SQLException{
		
//		Os testes desta classe foram movidos para o teste de arquivos
//		BuscadorResultado b1 = BuscadorResultado.FromDB(buscadorResultadoID1);
//		BuscadorResultado b2 = BuscadorResultado.FromDB(buscadorResultadoID2);
//		
//		assertThat(b1.getArquivo().getID(), is(arquivo1.getID()));
//		assertThat(b2.getArquivo().getID(), is(arquivo2.getID()));

	}
	
	@AfterClass
	public static void endTest() throws SQLException{
		
//		BuscadorResultado b1 = BuscadorResultado.FromDB(buscadorResultadoID1);
//		BuscadorResultado b2 = BuscadorResultado.FromDB(buscadorResultadoID2);
//		
//		b1.DeleteFromDB();
//		b2.DeleteFromDB();
		
		arquivo1.DeleteFromDB();
		arquivo2.DeleteFromDB();
		
		login1.DeleteFromDB();
		login2.DeleteFromDB();
		
	}
}
