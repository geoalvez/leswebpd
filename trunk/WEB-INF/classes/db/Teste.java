package db;

import connect.Buscador;
import connect.BuscadorGoogleResultado;

public class Teste {

	public static void main( String args[] ) throws Exception{

		BuscadorGoogleResultado bg = Buscador.buscarNoGoogle("grade ad hoc deve ter. Dentre essas características, as que");

		System.out.println( bg.count + "," + bg.links.size() );

	}

}
