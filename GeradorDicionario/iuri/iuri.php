<?php

	define('VERSAO_ATUAL', 1);
	define('NOVOS_ARQUIVOS', 'http://monitoriacastanhal.ufpa.br/iuri/ConELS.zip');
	define('NOME_NOVOS_ARQUIVOS', 'PacoteImagensVersao1.0.zip');

	switch($_POST['Acao']){

		case "VerificarVersao":

			echo VERSAO_ATUAL,"\n",NOVOS_ARQUIVOS,",",NOME_NOVOS_ARQUIVOS;

		break;

		default:

			echo "Consulta Nao Especificada!";

		break;

	}

?>