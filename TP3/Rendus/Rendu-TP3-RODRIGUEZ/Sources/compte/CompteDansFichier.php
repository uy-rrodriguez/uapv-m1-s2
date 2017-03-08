<?php

error_reporting(E_ALL ^ E_WARNING);
ini_set("display_errors", 1);

class CompteDansFichier {

  public static function creeFichier($number_of_lines) {
    ini_set("display_errors", 0);
    for($i = 0;$i < $number_of_lines;$i++) {
      echo rand(0, $number_of_lines/3 )."\n";
    }
  }

  public function enLisantLeFichier($number,$file_name) {
    $how_many = 0;
    $handle = fopen($file_name, "r");
    if ($handle) {
      while (($n = fgets($handle)) !== false) {;
          if ($n == $number) {
            $how_many++;
          }
      }
    }
    fclose($handle);
    return $how_many;
   }

   public function enChargeantLeFichierEtCherchant($number,$file_name) {
    $how_many = 0;
    $numbers = file($file_name);
    for($i = 0;$i < count($numbers);$i++) {
     if ($number == $numbers[$i]) {
            $how_many++;
          }
    }
    return $how_many;
   }


   public function enUtilisantLeShellUnix($number,$file_name) {
    $how_many=exec('cat '.$file_name.' | egrep ^'.$number.'$ | wc -l');
    return $how_many;
   }

}

?>
