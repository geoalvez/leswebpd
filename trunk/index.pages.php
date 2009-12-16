<? if( !$from_index ) die("Ops! Esta página naõ devia ser aberta desta maneira!"); ?>

<?

	echo "<font class=index>Todas as páginas por ordem de votação</font><br>";
	echo Listador::ListarMaisVotadas();

?>