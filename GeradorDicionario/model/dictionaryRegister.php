<?php

	class Register{

		private $Word;
		private $File;

		public function __construct($Word, $File)
	    {	    	
	    	$this->Word = $Word;
	    	$this->File = $File;
	    }

	    public function createRegister(){

	    	$TableName = "Palavras";
	    	$TableColumns = "Palavra,Arquivo";
	    	$RegisterValues = "'".$this->getWord()."','".$this->getFile()."'";

	    	return "INSERT INTO " . $TableName . "(" . $TableColumns . ")" . " VALUES (" . $RegisterValues . ")\n" ;

	    }
		
		public function setWord($Word)
		{
		    $this->Word = $Word;
		    return $this;
		}
		
		public function setFile($File)
		{
		    $this->File = $File;
		    return $this;
		}

		public function getWord()
		{
		    return $this->Word;
		}

		public function getFile()
		{
		    return $this->File;
		}

	}



?>