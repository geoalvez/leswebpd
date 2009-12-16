package util;

import java.util.Enumeration;
import java.util.Hashtable;

public class Functions {
	
    public static String join( String[] colecao, String cola ){

    	if( colecao.length == 0 ) return "";

    	String temp = colecao[0];
    	for( int k=1; k<colecao.length; k++ ){
    		temp += cola + colecao[k];
    	}

    	return temp;
    }
    
    public static String[] hashtableKeys( Hashtable<String, String> table ){
    	
    	String[] result = new String[ table.size() ];
    	Enumeration<String> keys = table.keys();
    	
    	int k = 0;
    	while( keys.hasMoreElements() ){
    		result[k] = keys.nextElement();
    		k++;
    	}

    	return result;
    	
    }

    public static String[] hashtableValues( Hashtable<String, String> table ){
    	
    	String[] result = new String[ table.size() ];
    	Enumeration<String> keys = table.keys();
    	
    	int k = 0;
    	while( keys.hasMoreElements() ){
    		result[k] = table.get( keys.nextElement() );
    		k++;
    	}

    	return result;
    	
    }
}
