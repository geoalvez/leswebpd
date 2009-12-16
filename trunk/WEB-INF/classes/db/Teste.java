package db;

import connect.Buscador;
import connect.BuscadorGoogleResultado;

public class Teste {

	public static void main( String args[] ) throws Exception{

		BuscadorGoogleResultado bg = Buscador.buscarNoGoogle("A bibliographic revision about the related subject, a");

		System.out.println( bg.count + "," + bg.links.size() );
	}

}
