package Testes;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

import db.DBClass;
import db.Login;


public class TesteLogin  {

	@DataPoint private static String login1 = "George";
	@DataPoint private static String login2 = "Fernando";
	@DataPoint private static String badLogin1 = "Jurema";
	@DataPoint private static String badLogin2 = "Juliana";

	@DataPoint private static String senha1 = "12345";
	@DataPoint private static String senha2 = "54321";
	@DataPoint private static String badSenha1 = "00000";
	@DataPoint private static String badSenha2 = "11111";


	@BeforeClass
	public static void setUP() throws Exception {

		DBClass.conectar();

		Login.CreateNew(login1, senha1);
		Login.CreateNew(login2, senha2);

	}

	@Test
	public  void testaLogin() throws Exception{

		Login l1 = Login.FromDB(login1);
		Login l2 = Login.FromDB(login2);

		assertThat(l1.getLogin(), is(login1));
		assertThat(l2.getLogin(), is(login2));

		assertThat(l1.getLogin(), is(not(badLogin1)));
		assertThat(l2.getLogin(), is(not(badLogin2)));

		assertThat(l1.getLogin(), is(not("")));
		assertThat(l2.getLogin(), is(not("")));


	}
	@Test
	public  void testaSenha() throws Exception{

		Login l1 = Login.FromDB(login1);
		Login l2 = Login.FromDB(login2);

		assertThat(l1.getSenha(), is(senha1));
		assertThat(l2.getSenha(), is(senha2));

		assertThat(l1.getSenha(), is(not(badSenha1)));
		assertThat(l2.getSenha(), is(not(badSenha2)));

		assertThat(l1.getSenha(), is(not("")));
		assertThat(l2.getSenha(), is(not("")));

	}

	@AfterClass
	public static void endTest() throws SQLException{

		Login l1 = Login.FromDB(login1);
		Login l2 = Login.FromDB(login2);

		l1.DeleteFromDB();
		l2.DeleteFromDB();

	}

}
