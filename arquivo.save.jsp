<%@page import="pdf.AnalisadorDeArquivos"%>
<%@ page
	contentType="text/html; charset=ISO-8859-1" 
	import="java.io.*"    
	import="java.util.*"    
	import="util.*"
	import="db.*"
	import="org.apache.commons.fileupload.*"
	import="com.lowagie.text.pdf.*"
%>
<%!
	Login login;
%>
<%

	if( session.getAttribute("current_login") != null ){
		login = (Login)session.getAttribute("current_login");
	}else{
		login = null;
	}

	if( login != null ){

		try {
			
			DBClass.getMyConnection().setAutoCommit( false );
			DBClass.getMyConnection().setSavepoint();
			
			boolean isMultipart = FileUpload.isMultipartContent(request);
			if( !isMultipart ) throw new Exception("O 'EncType' do formulário não está correto");

			DiskFileUpload upload = new DiskFileUpload();

			List<FileItem> items = upload.parseRequest(request);

			FileItem a = null;
			for( FileItem fitem : items ){
				if (!fitem.isFormField()) {
					if( fitem.getFieldName().equals("arquivo") ){
						a = fitem;
					}
				}
			}
			
			if( a == null ) throw new Exception("O arquivo não foi informado");
			if( !a.getName().toUpperCase().endsWith(".PDF") ) throw new Exception("A extensão do arquivo não é .PDF");
			
			Arquivo arq = Arquivo.CreateNew( a.getName(), login ); 

			String directory_path = "./webapps./les./WEB-INF/upload/" + login.getLogin();
			String file_path = directory_path + "/" + arq.getID();
			
			File dir = new File(directory_path);
			if( ! dir.exists() ){
				dir.mkdirs();
				if( !dir.exists() ) throw new Exception("O diretório do arquivo não existe e não pôde ser criado");
			}

			File pdf_file = new File( file_path ); 
			a.write( pdf_file );

			try{
				AnalisadorDeArquivos.validarArquivo(file_path);
			}catch( FileNotFoundException e ){
				throw e;
			}catch( Exception e ){
				a.delete();
				throw e;
			}
			
			DBClass.getMyConnection().commit();

			out.println( "<script>alert( 'Arquivo enviado com sucesso' ); parent.window.location.href = parent.window.location.href</script>" );

		} catch (Exception e) {
			DBClass.getMyConnection().rollback();
			out.println( "<script> alert( \"Erro ao salvar o arquivo: " + e.getMessage() + "\" ) </script>" );
		}

	}else{
		out.println( "<script> alert('Você precisa estar logado para acessar esta página!') </script>" );
	}

%>
