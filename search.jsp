<%@page import="connect.Buscador"%>
<%@page import="pdf.AnalisadorDeArquivos"%>
<%@ page
	contentType="text/html; charset=ISO-8859-1"
	import="java.io.*"
	import="java.util.*"
	import="util.*"
	import="db.*"
	import="connect.*"
	import="org.apache.commons.fileupload.*"
	import="com.lowagie.text.pdf.*"
	import="java.sql.*"
%>
<%!
	Login login;
%>

<script>

function avisar_parent(trecho){
	parent.document.getElementById("cap_status").innerHTML = "Buscando por: " + trecho
}
function concluir(){
	parent.trecho_concluido();
}
</script>

<%

	if( session.getAttribute("current_login") != null ){
		login = (Login)session.getAttribute("current_login");
	}else{
		login = null;
	}

	if( login != null ){

		try {

			int id = Integer.parseInt(request.getParameter("file_id"));
			
			Statement st = DBClass.getMyConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from buscador_resultados where concluido=0 and arquivo_id=" + id + " limit 1");

			if( !rs.next() ) throw new Exception("Este arquivo já está concluído"); 
			out.println("<script>avisar_parent('" + Formater.ScriptEncode(Formater.htmlEntityEncode(rs.getString("trecho"))) + "')</script>");

			BuscadorGoogleResultado b = Buscador.buscarNoGoogle(rs.getString("trecho"));
			
			BuscadorResultadoLink.DeleteLinks( rs.getInt("id") );
			BuscadorResultadoLink.CreateNews( rs.getInt("id"), b.links );
			DBClass.execute("update buscador_resultados set count=" + b.count + ", concluido=1 where id=" + rs.getInt("id"));

			out.println( "<script>concluir()</script>" );

		} catch (Exception e) {

			out.println( "<script> alert( \"Aviso: " + e.getMessage() + "\" ) </script>" );
		}

	}else{
		out.println( "<script> alert('Você precisa estar logado para acessar esta página!') </script>" );
	}

%>
