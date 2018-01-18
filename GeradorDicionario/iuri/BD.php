<?php

	phpinfo();

	$to = "moyses15264123@gmail.com";
	$subject = "My subject";
	$txt = "Hello world!";
	$headers = "From: webmaster@example.com" . "\r\n" .
	"CC: moyses15264123@gmail.com";

	if(mail($to,$subject,$txt, $headers)){

		echo "OK";

	}else{

		echo "ERRO";

	}

?>