<?php
error_reporting(E_ALL ^ E_WARNING);
ini_set('display_errors', 1);

require_once("compte/CompteDansFichier.php");
require_once("Profile.php");


class TestEnLisantLeFichier {

    /* @var $profiler Profile */
    private $profiler;
    private $fileName;

    public function __construct($fileName){
        $this->profiler = new Profile();
        $this->fileName = $fileName;
    }

    public function testEnLisantLeFichier() {
        $this->profiler->profile("CompteDansFichier",
                                 "enLisantLeFichier",
                                 [18, $this->fileName],
                                 10);
    }

    public function printDetails() {
        $this->profiler->printDetails();
    }
}


$fichier = $argv[1];

$test = new TestEnLisantLeFichier($fichier);
$test->testEnLisantLeFichier();
$test->printDetails();

?>
