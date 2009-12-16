<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="db.*" %>

<%!
	Login login;
%>
<%

	if( session.getAttribute("current_login") != null ){
		login = (Login)session.getAttribute("current_login");
	}else{
		login = null;
	}
%>

<div class=index>Bem-vindo, <%= login.getLogin() %></div><br><br>

Seus arquivos enviados:<br>

<table class=form_table>
	<tr class=form_header><td class=form_header>Nome do arquivo</td><td>Data de envio</td><td>Situação</td></tr>
	<%

		ResultSet rs = DBClass.getMyConnection().createStatement().executeQuery(
					"select * from arquivos where dono=" + DBClass.dbValue(login.getLogin())
		);

		while( rs.next() ){

			out.println(
					"<tr class=form_body>" +
						"<td class=form_body><a href='.?action=view_file&file=" + rs.getInt("arquivo_id") + "'>" + rs.getString("nome") + "</a></td>" +
						"<td>" + rs.getDate("data_envio") + "</td>" +
						"<td>" + Arquivo.TranslateSituacao( rs.getInt("situacao") ) + "</td>" +
					"</tr>" );

		}

	%>
</table>