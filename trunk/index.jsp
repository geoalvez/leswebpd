<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="db.*" %>
<%!

	String str = "";
	String pageToLoad = "";
	Login login;

%>
<%

	DBClass.conectar();

	String action = request.getParameter("action");
	if( action == null ){ action = ""; }

	boolean display_msg = false;
	String message = "";
	String login_msg = "";

	if( action.equals("logoff") ){
		session.invalidate();
		session = request.getSession(true);
		display_msg = true;
		message = "Você fez logout do sistema";
	}else if( action.equals("login") ){

		try{

			String login_txt = (String)request.getParameter("login");
			String senha_txt = (String)request.getParameter("senha");

			Login l = Login.FromLogin( login_txt, senha_txt );
			session.setAttribute("current_login", l );

			display_msg = true;
			message = "Você fez login do sistema";

			action = "navigate";
			pageToLoad = "home";

		}catch( Exception e ){
			e.printStackTrace();
			login_msg = e.getMessage();
		}

	}else if( action.equals("view_file") ){
		//... do nothing
	}else{

		action = "navigate";

		pageToLoad = request.getParameter("page");
		if( pageToLoad == null ){ pageToLoad = "index.main"; }

	}

	if( session.getAttribute("current_login") != null ){
		login = (Login)session.getAttribute("current_login");
	}else{
		login = null;
	}

%>

<link href="res/default.css" rel="stylesheet" type="text/css">
<iframe width=1000 height=400 class="hidden_frame" name=hidden_frame></iframe>

<center>

	<% out.println( Formater.caixaUp("","","") ); %>
	<table width="800" cellpadding="0" cellspacing="0"><tbody bgcolor="#FFFFFF">
		<tr><td colspan="2"><img src="gifs/logo.jpg"></td></tr>
		<tr>
			<td colspan=2 background="gifs/logo2_back.jpg" align="right" height="25"></td>
		</tr>
		<tr><td colspan=2>
			<!----------- MEIO DO SITE --------------------->

				<table>
					<td valign="top" bgcolor="#F0F0F0">
					<!------- MENU DO SITE -------------->

						<table width=200 cellspacing="0" cellpadding="0">

							<%

//................................ LOGIN ................

	out.println( "<tr><td>" + Formater.botao("<center>Login</center>", "100%", "30", "5", "#E0E0E0" ) + "</td></tr>" );
	if( login != null ){
		str = "Logado como:<br>" +
				"<font color='#0060FF'><a href='.?action=navigate&page=home'>" + login.getLogin() + "</a></font><br>" +
				"<form method=post action=index.jsp style='margin: 0'>" +
				"<input type=hidden name=action value=logoff>" +
				"<input type=submit class=flat_button_w value='Deslogar'>" +
				"</form>";
	}else{
		str =
				"<form style='margin: 0' method=post action=index.jsp>" +
				"<input type=hidden name=action value=login>" +
				"<table>" +
					"<tr><td>Login:</td><td width='100%'><input class=input name=login></td></tr>" +
					"<tr><td>Senha:</td><td><input class=input type=password name=senha></td></tr>" +
					"<tr><td colspan=2><input class=flat_button_w type=submit value='Fazer login'></td></tr>" +
				"</table>" +
				"</form>";
	}

	out.println( "<tr><td>" + Formater.botao( str, "100%" ) + "</td></tr>" );
	if( login != null ){

		out.println( "<tr><td>" + Formater.botao("<center>Arquivos</center>", "100%", "30", "5", "#E0E0E0" ) + "</td></tr>" );

		//........ Faz a listagem de arquivos
		ResultSet rs = DBClass.getMyConnection().createStatement().executeQuery("select * from arquivos where dono=" + DBClass.dbValue(login.getLogin()));
		while( rs.next() ){

			out.println( "<tr><td>" + Formater.botao(
				"<a href='index.jsp?action=view_file&file=" + rs.getInt("arquivo_id") + "'>" +
					"<img src='gifs/quad.gif' align=absmiddle>&nbsp;[" + rs.getInt("arquivo_id") + "] " + rs.getString("nome") +
				"</a>", "100%", "30" ) + "</td></tr>"
			);
		}

		String form_txt = "" +

		"<tr><td>" + Formater.botao(
			"Enviar novo arquivo:<br>" +
			"<form method='post' action='arquivo.save.jsp' target='hidden_frame' enctype='multipart/form-data'>" +
				"<input type=hidden name=novo value=1>" +
				"<table width='100%'>" +
					"<tr><td width='100%'><input name=arquivo class=input type='file'></td></tr>" +
					"<tr><td><input class=flat_button_w type=submit value='Enviar'></td></tr>" +
				"</table>" +
			"</form>", "100%" ) +
		"</td></tr>";

		out.println( form_txt );


	}
	if( login_msg != "" ){ out.println( "<tr><td>" + Formater.botao("<span class=erro>" + login_msg + "</span>","100%") + "</td></tr>"); }

//................... FIM DE LOGIN

							%>

						</table>

					<!------------- FIM DO MENU DO SITE --------->
					</td><td class=text style="padding: 10; text-align:justify" valign="top" width="100%">
						<%

							if( display_msg ){
								out.println( message );
							};

							if( action.equals("navigate") ){
								if( pageToLoad != "" ){
									%><jsp:include flush="true" page="<%= pageToLoad + \".jsp\" %>" /><%
								}
							}else if( action.equals("view_file") ){
								try{
									Arquivo arquivo = Arquivo.FromDB(Integer.parseInt(request.getParameter("file")));
									%>
										<jsp:include flush="true" page="arquivo.view.jsp" />
									<%
								}catch( Exception e ){
									out.println( "Erro ao visualizar o arquivo: " + e.getMessage() );
								}
							}

						%>

					</td>
				</table>

			<!----------- FIM DO MEIO DO SITE -------------->
		</td></tr>
		<tr><td colspan="2" background="gifs/logo2_back.jpg" height="25" align="center">projeto webpd</td></tr>
	</table>
	<% out.println( Formater.caixaDown() ); %>

</center>