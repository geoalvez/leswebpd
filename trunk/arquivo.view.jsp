<%@
	page contentType="text/html; charset=ISO-8859-1"
	import="util.*"
	import="java.sql.*"
	import="db.*"
	import="pdf.AnalisadorDeArquivos"
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

		int file_number = Integer.parseInt(request.getParameter("file"));
		boolean view_completo = false;
		if( request.getParameter("view_completo") != null ){
			view_completo = Integer.parseInt(request.getParameter("view_completo")) == 1;
		}

		Arquivo arquivo = Arquivo.FromDB( file_number );
		if( !arquivo.getDono().equals(login) ) throw new Exception("Você não tem permissão de ver este arquivo");

%>

<div class=index>Visualizando o arquivo <%= arquivo.getNome() %></div><br>
	<a href="arquivo.del.jsp?file=<%= arquivo.getID() %>" target=hidden_frame>Excluir este arquivo</a> |
	<a href="arquivo.reset.jsp?file=<%= arquivo.getID() %>" target=hidden_frame>Resetar este arquivo</a>


<br><br>

<div class=little_index>Dados do arquivo:</div><br>

<table class=form_table><tbody class=form_body>
	<tr><td>Data do envio:</td><td><%= arquivo.getDataEnvio() %></td></tr>
	<tr><td>Situação:</td><td><b><%= arquivo.getSituacaoStr() %></b> - <a target=hidden_frame href='arquivo.proceed.jsp?file=<%= file_number %>'><%= arquivo.getNextSituacaoStr() %></a></td></tr>

	<% if( arquivo.getSituacao() == 1 ){ %>

		<tr><td colspan=2>
			<%= view_completo || arquivo.getTextoExtraido().length() <= 1000 ?
					arquivo.getTextoExtraido()
					:
					arquivo.getTextoExtraido().substring(0, 1000) + "..."
			%><br><br>
			<a href='.?action=view_file&file=<%= file_number %>&view_completo=1'>[Ver o texto completo]</a>
			<a href='.?action=view_file&file=<%= file_number %>&view_completo=0'>[Ver o resumo]</a>
		</td></tr>
	<% }else if( arquivo.getSituacao() == 2 ){ %>

		<tr><td colspan=2>

			Trechos do documento selecionados para análise:
			
			<a href='search.jsp?file_id=<%= arquivo.getID() %>' target=hidden_frame>[Começar a busca]</a>
			<br><br>
			<div id=cap_status>...</div><br>
			<div id=cap_percent>...</div>

<style>
	.green_link{ color: green; font-weight: bold }
</style>

			<%
			int[] counts = BuscadorResultado.TrechosBuscados(arquivo.getID());

//			ResultSet rs = BuscadorResultado.LoadFromDB( arquivo.getID() );

//			out.println("<ul>");
//			while( rs.next() ){
//				out.println("<li>" + rs.getString("trecho") + ":</li>");
//			}
//			out.println("</ul>");
			%>

		</td></tr>
		
<script>

trechos_totais = <%= counts[0] %>
trechos_achados = <%= counts[1] %>

atualizar_porcentagem()

function trecho_concluido(){
	trechos_achados++
	setTimeout( "hidden_frame.window.location = 'search.jsp?file_id=<%= arquivo.getID() %>'", 2000 )
	atualizar_porcentagem()
}

function atualizar_porcentagem(){
	document.getElementById('cap_percent').innerHTML = "Trechos buscados " + trechos_achados + " de " + trechos_totais + " (" + (100*trechos_achados / trechos_totais) + "%)"
}

</script>

	<% }else if( arquivo.getSituacao() == 3 ){ %>

		<tr><td colspan=2>
			Resultado da avaliação do arquivo:<br><br>

<%
	int teta = 10;
	boolean exibir_links = false;
	int teta2 = 1;
	if( request.getParameter("teta") != null ) teta = Integer.parseInt(request.getParameter("teta"));
	if( request.getParameter("exibir_links") != null ) exibir_links = Integer.parseInt(request.getParameter("exibir_links")) == 1;
	if( request.getParameter("teta2") != null ) teta2 = Integer.parseInt(request.getParameter("teta2"));
%>

			<form action=".">
				<input type=hidden name=action value=view_file>
				<input type=hidden name=file value=<%= arquivo.getID() %>>
				Limiar de detecção: <input size=2 name=teta value="<%= teta %>"><br>
				Limiar de plágio local: <input size=2 name=teta2 value="<%= teta2 %>"><br>
				Exibir links: <input type=checkbox name=exibir_links value=1 <%= exibir_links ? "checked" : "" %>><br>
				<input type=submit value='Avaliar'>
			</form>

		<%
		
		if( request.getParameter("teta") == null ){
			out.println("Defina o valor do Limiar de tececção para avaliar o arquivo");
		}else{

			int[] hits = BuscadorResultado.TrechosComHits( arquivo.getID(), teta );
			out.println("<b>Evidência global de plagio: " + (100*hits[1]/hits[0]) + "%</b><br>");
			out.println("Dos " + hits[0] + " trechos pesquisados, " + hits[1] + " obtiveram 'hit' (0 &lt; hit &lt;= limiar de detecção)<br>");

			if( exibir_links ){

				ResultSet rs = BuscadorResultado.LoadFromDB( arquivo.getID() );
	
				int acumulado = 0;
				String acumulado_str = "";
				while( rs.next() ){
					
					if( rs.getInt("count") > 0 && rs.getInt("count") <= teta ){

						acumulado++;
						
						acumulado_str += "(" + rs.getInt("count") + ") " + rs.getString("trecho") + ":<br>";
						acumulado_str += "<ul>";
						ResultSet rs_l = BuscadorResultadoLink.LoadFromDB( rs.getInt("id") );
						while( rs_l.next() ){
							acumulado_str += "<li>" + rs_l.getString("link") + "</li>";
						}
						acumulado_str += "</ul>";

					}else{
						
						if( acumulado >= teta2 ){
							out.println( "<hr><b>Evidência local com " + acumulado + " resultados:</b><br><br>");
							out.println( acumulado_str );
						}
						
						acumulado = 0;
						acumulado_str = "";
						
					}

				}
				if( acumulado >= teta2 ){
					out.println( "<hr>Evidência local com " + acumulado + " resultados:<br><br>");
					out.println( acumulado_str );
				}

			}
			
			
		}

		%>

		</td></tr>

	<% } %>

</table>

<br><br>

<%
	}else{
		out.println("Você precisa fazer login para acessar este recurso");
	}
%>