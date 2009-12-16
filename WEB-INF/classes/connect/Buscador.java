package connect;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Buscador {

	public static BuscadorGoogleResultado buscarNoGoogle( String query ) throws Exception{

		String code_base = getGoogleCode(query);
		if( code_base.indexOf("http://sorry.google.com/sorry/?continue") >=0 ) throw new Exception("O Google está impedindo que as buscas automáticas continuem");

		System.out.println( code_base );
		
		ArrayList<String> result = new ArrayList<String>();

		String st = "<li class=g><h3 class=r>";
		String et = "</h3>";
		String sc = "<p id=resultStats>";
		String ec = "seconds";

		int pos1, pos2, pos3, pos4, count = 0;

		String code = code_base;
		while( (pos1 = code.indexOf(st)) >= 0 ){
			pos2 = code.indexOf(et,pos1);
			pos3 = pos1 + st.length();
			pos4 = pos2 + et.length();

			result.add( code.substring(pos3, pos2).trim() );
			code = code.substring(pos4, code.length()-1 );
		}

		pos1 = pos2 = pos3 = pos4 = 0;
		code = code_base;

		if( code.indexOf("did not match any documents") >= 0 || code.indexOf("No results found for") >=0 ){
			count = 0;
			result.clear();
		}else if( (pos1 = code.indexOf(sc)) >=0 ){

			pos2 = code.indexOf(ec,pos1);
			pos3 = pos1 + sc.length();
			pos4 = pos2 + ec.length();

			System.out.println( pos1 + "," + pos2 + "," + pos3 + "," + pos4 );
			
			if( pos1 <= 0 || pos2 <=0 || pos3 <= 0 || pos4 <= 0 || pos1 >= pos2 || pos3 >= pos4 ){
				count = 0;
			}else{

				String find = code.substring( pos3, pos2 ).trim();

				String delimiter = "of about <b>";
				if( find.indexOf("of about") < 0 ) delimiter = "of <b>";
				
				int pos_l1 = find.indexOf(delimiter);
				int pos_l2 = find.indexOf("</b>", pos_l1);
				int pos_l3 = pos_l1 + delimiter.length();
				
				System.out.println("-------------------------------");
				System.out.println( find );
				System.out.println(delimiter + ":" + find.length() + ":" + pos_l1 + "," + pos_l2 + "," + pos_l3);
				System.out.println( find.substring(pos_l1));
				System.out.println("-------------------------------");

				count = Integer.parseInt( find.substring(pos_l3,pos_l2).replace(".", "").replace(",","") );

			}
		}

		return new BuscadorGoogleResultado(result,count);

	}

	private static String getGoogleCode( String query ) throws IOException{

		Socket sk = new Socket();
		DataOutputStream os = null;
		BufferedReader is = null;

		sk.bind(null);
		sk.connect( new InetSocketAddress("www.google.com", 80), 5000 );

		os = new DataOutputStream(sk.getOutputStream());
		is = new BufferedReader( new InputStreamReader(sk.getInputStream()) );

//		Accept: */*
//		Referer: http://www.google.com/search?sourceid=navclient&ie=UTF-8&oe=UTF-8&q=iehttpheaders
//		Accept-Language: en-us
//		Accept-Encoding: gzip, deflate
//		User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; iOpus-I-M; .NET CLR 1.0.3705; .NET CLR 1.1.4322)
//		Host: www.blunck.info
//		Connection: Keep-Alive
//		Cache-Control: no-cache

		query = URLEncoder.encode("\"" + query + "\"","UTF-8");
		String header = 
			"GET /search?q=" + query + " HTTP/1.1\r\n" +
			"Connection: Close\r\n\r\n";

		os.writeBytes(header);    

		String line;
		String result = "";
		while ((line = is.readLine()) != null) {
			result += line; 
		}

		os.close();
		is.close();
		sk.close();

		return result;

	}

}
