<? if( !$from_index ) die("Ops! Esta p�gina na� devia ser aberta desta maneira!"); ?>

<?

	echo "<font class=index>Todas as p�ginas por ordem de vota��o</font><br>";
	echo Listador::ListarMaisVotadas();

?>