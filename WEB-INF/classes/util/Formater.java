package util;


public class Formater{

	public static String tabelaUp( String titulo, String width ){
		return
		"<table class='form_table' width='$width' cellspacing=0 cellpadding=0>" + 
		"<tr>" + 
		"<td class='form_topo' width='" + width + "'>" + titulo + "</td>" + 
		"</tr>" + 
		"<tr><td>";

	}

	public static String tabelaDown( String texto ){
		return "</td></tr>" + ( !texto.equals("") ? "<tr><td colspan='10' class=form_bottom>" + texto + "</td></tr>" : "" ) +
		"</table>";
	}

	public static String caixaUp( String width, String height, String align ){
		return 
		"<table cellspacing=0 cellpadding=0 height='" + height + "'>" + 
		"<tr>" + 
		"<td valign='bottom' align='right'><img src='gifs/tables/m_et.gif'></td>" + 
		"<td background='gifs/tables/m_t.gif'></td>" + 
		"<td valign='bottom' align='left'><img src='gifs/tables/m_dt.gif'></td>" + 
		"</tr>" + 
		"<tr>" + 
		"<td background='gifs/tables/m_e.gif'></td>" + 
		"<td width='" + width + "' height='" + height + "' background='backs/back_caixa.gif' valign=top align='" + align + "'>";
	}

	public static String caixaMeio(){
		return 
		"</td>" +
		"<td background='gifs/tables/m_d.gif'></td>" +
		"</tr>" +
		"<tr>" +
		"<td background='gifs/tables/m_e.gif'></td>" +
		"<td background='gifs/tables/m_b.gif'><img src='gifs/tables/m_b.gif'></td>" +
		"<td background='gifs/tables/m_d.gif'></td>" +
		"</tr>" +
		"<tr>" +
		"<td background='gifs/tables/m_e.gif'></td>" +
		"<td width='$width' background='backs/back_caixa.gif'>";
	}

	public static String caixaDown(){
		return
		"</td>" + 
		"<td background='gifs/tables/m_d.gif'></td>" + 
		"</tr>" + 
		"<tr>" + 
		"<td valign='bottom' align='right'><img src='gifs/tables/m_eb.gif'></td>" + 
		"<td background='gifs/tables/m_b.gif'></td>" + 
		"<td valign='bottom' align='left'><img src='gifs/tables/m_db.gif'></td>" + 
		"</tr>" + 
		"</table>";
	}

	public static String botao( String content ){
		return botao( content, "" );
	}

	public static String botao( String content, String width ){
		return botao( content, width, "" );
	}

	public static String botao( String content, String width, String height ){
		return botao( content, width, height, "5", "#F0F0F0" );
	}

	public static String botao( String content, String width, String height, String padding, String bgcolor ){
		return 
		"<table cellspacing=0 cellpadding=0>" + 
		"<tr>" + 
		"<td><img src='gifs/button/flat/tl.gif'></td>" + 
		"<td background='gifs/button/flat/t.gif'></td>" + 
		"<td><img src='gifs/button/flat/tr.gif'></td>" + 
		"</tr>" + 
		"<tr>" + 
		"<td background='gifs/button/flat/l.gif'></td>" + 
		"<td style='padding: " + padding + "' width='" + width + "' bgcolor='" + bgcolor + "' height='" + height + "'>" + content + "</td>" + 
		"<td background='gifs/button/flat/r.gif'></td>" + 
		"</tr>" + 
		"<tr>" + 
		"<td><img src='gifs/button/flat/bl.gif'></td>" + 
		"<td background='gifs/button/flat/b.gif'></td>" + 
		"<td><img src='gifs/button/flat/br.gif'></td>" + 
		"</tr>" + 
		"</table>";
	}

	public static String htmlEntityEncode( String s ){
		
		s = s.replace("&",  "&#38;");
		s = s.replace("'",  "&#39;");
		s = s.replace("\"", "&#34;");
		s = s.replace("<",  "&#lt;");
		s = s.replace(">",  "&#gt;");
		
		return s;
	}

	public static String ScriptEncode( String s ){
		return s.replace("'", "\\\'").replace("\"", "\\\"");
	}

/*
	//..... Funções de escape
	function pn( $texto ){ return htmlentities( $texto, ENT_QUOTES ); }
	function np( $texto ){ return html_entity_decode( $texto, ENT_QUOTES ); }
	function pl( $texto ){ return urlencode( htmlentities($texto) ); }
	function lp( $texto ){ return html_entity_decode( urldecode($texto) ); }
	function sn( $texto ){ return str_replace("'", "\\'", str_replace("\"", "\\\"", $texto)); }

	function get_user_foto($login){
		$foto_path = dirname(__FILE__)."/../users/$login/fotos/foto.jpg";
		if( file_exists($foto_path) ){
			return base_url()."/users/$login/fotos/foto.jpg";
		}else{
			return base_url()."/users/default/fotos/foto.jpg";
		}
	}
	function get_user_small_foto($login){
		$foto_path = dirname(__FILE__)."/../users/$login/fotos/small.jpg";
		if( file_exists($foto_path) ){
			return base_url()."/users/$login/fotos/small.jpg";
		}else{
			return base_url()."/users/default/fotos/small.jpg";
		}
	}

	function redirect( $url ){
		echo "<script>window.location.href='$url'</script>
				<a href='$url'>Clique aqui para ir para a próxima página</a>";
	}

	function redirect_to_login( $text_to_show = "", $back_url = "" ){
		$a1 = "text_to_show=".urlencode($text_to_show);
		$a2 = "back_url=".urlencode($back_url);
		header("Location: ".base_url()."/login/index.php?$a1&$a2" );
	}

	function base_url(){
		return "http://www.sccnet.com.br/jackson/ForumAgrotoxicos";
	}

	function show_erro($mensagem){
		echo "
		<table cellspacing=1 cellpadding=5 bgcolor=#505050><td bgcolor=#E0E0E0 class=erro>
			$mensagem
		</td></table>";
	}
	function show_sucesso($mensagem){
		echo "
		<table cellspacing=1 cellpadding=5 bgcolor=#505050><td bgcolor=#E0E0E0 class=sucesso>
			$mensagem
		</td></table>";
	}
 */
}