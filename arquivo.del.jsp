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

			int file_number = Integer.parseInt(request.getParameter("file"));

			Arquivo arq = Arquivo.FromDB( file_number );

			arq.DeleteFromDB();

			DBClass.getMyConnection().commit();

			out.println( "<script>alert( 'Arquivo excluido com sucesso' ); parent.window.location.href = '.'" );

		} catch (Exception e) {
			DBClass.getMyConnection().rollback();
			out.println( "<script> alert( \"Erro ao excluir o arquivo o arquivo: " + e.getMessage() + "\" ) </script>" );
		}

	}else{
		out.println( "<script> alert('Você precisa estar logado para acessar esta página!') </script>" );
	}

%>
