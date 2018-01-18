<?php

	require 'version.php';
	require 'new_file.php';
	require 'name_new_files.php';

	switch($_POST['Acao']){

		case "VerificarVersao":

			echo VERSAO_ATUAL,"\n",NOVOS_ARQUIVOS,",",NOME_NOVOS_ARQUIVOS;

		break;

		default:

			echo "Consulta Nao Especificada!";

		break;

	}

?>