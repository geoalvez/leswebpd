package Testes;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

import db.Arquivo;
import db.DBClass;
import db.Login;

public class TesteBuscadorResultadoLink {

	@DataPoint private static Login login1;
	@DataPoint private static Login login2;

	@DataPoint private static Arquivo arquivo1;
	@DataPoint private static Arquivo arquivo2;

//	@DataPoint private static BuscadorResultado buscadorResultado1;
//	@DataPoint private static BuscadorResultado buscadorResultado2;
//	
//	@DataPoint private static String buscadorResultadoLinkText1 = "The Text 1 Matches";
//	@DataPoint private static String buscadorResultadoLinkText2 = "The Text 2 Matches";
//
//	@DataPoint private static String badBuscadorResultadoLinkText1 = "The Text 1 doesn't Matches";
//	@DataPoint private static String badBuscadorResultadoLinkText2 = "The Text 2 doesn't Matches";
//	
//	private static int buscadorResultadoLinkID1;
//	private static int buscadorResultadoLinkID2;

	@BeforeClass
	public static void setUP() throws SQLException {

		DBClass.conectar();
		
		login1 = Login.CreateNew("George", "12345");
		login2 = Login.CreateNew("Fernando", "54321");
		
		arquivo1 = Arquivo.CreateNew("arquivo1.pdf", login1);
		arquivo2 = Arquivo.CreateNew("arquivo2.pdf", login2);

//		Os testes desta classe foram movidos para o teste de arquivos
//		buscadorResultado1 = BuscadorResultado.CreateNew(arquivo1, "This is a PDF File");
//		buscadorResultado2 = BuscadorResultado.CreateNew(arquivo1, "This is another PDF File");
//		
//		buscadorResultadoLinkID1 = BuscadorResultadoLink.CreateNew(buscadorResultado1,buscadorResultadoLinkText1).getID();
//		buscadorResultadoLinkID2 = BuscadorResultadoLink.CreateNew(buscadorResultado2,buscadorResultadoLinkText2).getID();
		
	}
	
	@Test
	public void testaLink() throws SQLException{
		
//		Os testes desta classe foram movidos para o teste de arquivos
//		BuscadorResultadoLink b1 = BuscadorResultadoLink.FromDB(buscadorResultadoLinkID1);
//		BuscadorResultadoLink b2 = BuscadorResultadoLink.FromDB(buscadorResultadoLinkID2);
//		
//		assertThat(b1.getLink(), is(buscadorResultadoLinkText1));
//		assertThat(b2.getLink(), is(buscadorResultadoLinkText2));
//
//		assertThat(b1.getLink(), is(not(badBuscadorResultadoLinkText1)));
//		assertThat(b2.getLink(), is(not(badBuscadorResultadoLinkText2)));
//		
//		assertThat(b1.getLink(), is(not("")));
//		assertThat(b2.getLink(), is(not("")));

	}

	@Test
	public void testaBuscadorResultado() throws SQLException{
		
//		BuscadorResultadoLink b1 = BuscadorResultadoLink.FromDB(buscadorResultadoLinkID1);
//		BuscadorResultadoLink b2 = BuscadorResultadoLink.FromDB(buscadorResultadoLinkID2);
//		
//		assertThat(b1.getBuscadorResultado().getID(), is(buscadorResultado1.getID()));
//		assertThat(b2.getBuscadorResultado().getID(), is(buscadorResultado2.getID()));

	}

	@AfterClass
	public static void endTest() throws SQLException{
		
//		BuscadorResultadoLink b1 = BuscadorResultadoLink.FromDB(buscadorResultadoLinkID1);
//		BuscadorResultadoLink b2 = BuscadorResultadoLink.FromDB(buscadorResultadoLinkID2);
//		
//		b1.DeleteFromDB();
//		b2.DeleteFromDB();
//		
//		buscadorResultado1.DeleteFromDB();
//		buscadorResultado2.DeleteFromDB();
//
		arquivo1.DeleteFromDB();
		arquivo2.DeleteFromDB();
		
		login1.DeleteFromDB();
		login2.DeleteFromDB();
		
	}
}
