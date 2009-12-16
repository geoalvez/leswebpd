<%@page import="pdf.AnalisadorDeArquivos"%>
<%@ page
	contentType="text/html; charset=ISO-8859-1"
	import="java.io.*"
	import="java.util.*"
	import="util.*"
	import="db.*"
	import="org.apache.commons.fileupload.*"
	import="com.lowagie.text.pdf.*"
	import="connect.*"
	import="java.sql.ResultSet"
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

		String mensagem = "";

		try {

			Enumeration<String> e = request.getAttributeNames();
			while( e.hasMoreElements() ){
				out.println( e.nextElement() );
			}

			int file_number = Integer.parseInt(request.getParameter("file"));

			Arquivo arquivo = Arquivo.FromDB( file_number );

			String directory_path = "./webapps/les/WEB-INF/upload/" + login.getLogin();
			String file_path = directory_path + "/" + arquivo.getID();

			switch( arquivo.getSituacao() ){
			case 0:
				arquivo.setTextoExtraido( AnalisadorDeArquivos.extrairTexto(file_path) );
				mensagem = "Texto extraído com sucesso";
				break;
			case 1:

				DBClass.getMyConnection().createStatement().execute("delete from buscador_resultados where arquivo_id=" + arquivo.getID() );

				int start_index = Integer.parseInt(request.getParameter("start_index"));
				int tamanho_janela = Integer.parseInt(request.getParameter("tamanho_janela"));
				int passo = Integer.parseInt(request.getParameter("passo"));

				out.println( start_index + "," + tamanho_janela + "," + passo );

				arquivo.definirTexto( start_index, tamanho_janela, passo );
				mensagem = "Quebra do texto para a busca feita com sucesso";
				break;

			case 2:

				if( !arquivo.trechosConcluidos() ) throw new Exception("Este arquivo ainda não foi concluído");

				mensagem = "O arquivo está pronto para ser avaliado";
				break;

			}
			arquivo.setSituacao( arquivo.getSituacao()+1 );
			arquivo.writeToDB();

			out.println( "<script>alert( '" + mensagem + "' ); parent.window.location.href = parent.window.location.href</script>" );

		} catch (Exception e) {
			//out.println( "<script> alert( \"Erro: " + e.getMessage() + "\" ) </script>" );
			throw e;
		}

	}else{
		out.println( "<script> alert('Você precisa estar logado para acessar esta página!') </script>" );
	}

%>
