package pdf;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import com.lowagie.text.pdf.PdfReader;

public class AnalisadorDeArquivos {
	
	public static void validarArquivo( String fileName ) throws Exception{
		
		File pdf_file = new File(fileName);
		if( !pdf_file.exists() ) throw new java.io.FileNotFoundException("Arquivo não existente");
		
		try{
			new PdfReader(fileName);
		}catch( Exception e ){
			throw new Exception("Arquivo PDF inválido");
		}


	}
	
	public static String extrairTexto( String fileName ) throws Exception{
		
		File pdf_file = new File(fileName);
		if( !pdf_file.exists() ) throw new java.io.FileNotFoundException("Arquivo não existente");

		PDDocument pdd=PDDocument.load(pdf_file);
		
		PDFTextStripper pts = new PDFTextStripper();

		return pts.getText(pdd);

	}
	
	public static void main( String args[] ) throws Exception{

		System.out.println( AnalisadorDeArquivos.extrairTexto("c:/test.pdf") );
		
	}

}
