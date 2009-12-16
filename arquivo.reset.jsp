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

			arq.setTextoExtraido("");
			arq.setSituacao(0);
			arq.writeToDB();

			DBClass.execute("delete from buscador_resultados where arquivo_id=" + arq.getID() );

			DBClass.getMyConnection().commit();

			out.println( "<script>alert( 'Arquivo resetado com sucesso' ); parent.window.location.href = parent.window.location.href</script>" );

		} catch (Exception e) {
			DBClass.getMyConnection().rollback();
			out.println( "<script> alert( \"Erro ao resetar o arquivo o arquivo: " + e.getMessage() + "\" ) </script>" );
		}

	}else{
		out.println( "<script> alert('Você precisa estar logado para acessar esta página!') </script>" );
	}

%>
