<?php

	include("../model/dictionaryRegister.php");

	class Controller{

		public static function createNewVersionDictionary(){

			

		}

		public static function createNewRegisterInDictionary($Word, $File){

			$Register = new Register($Word, $File);

			$File = fopen("../resource/databases/BD.sql", "a");

			fwrite( $File, str_replace("#4", "", $Register->createRegister() ) );

			fclose($File);


		}

		public static function insertNewImageToDictionary(){

			$target_dir = "../resource/images/";

			$target_file = $target_dir . basename($_FILES["fileToUpload"]["name"]);

			move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file);

		}

	}

	if (isset($_POST['Action'])) {
		
		switch ($_POST['Action']) {
			
			case 'InsertImage':

				Controller::createNewRegisterInDictionary($_POST['Palavra'], $_FILES['fileToUpload']['name']);
				Controller::insertNewImageToDictionary();

			break;
			
			case 'UpdateDictionary':



			break;
			
			default:



			break;
		
		}

	}


?>