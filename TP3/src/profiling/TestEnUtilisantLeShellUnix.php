<?php
error_reporting(E_ALL ^ E_WARNING);
ini_set('display_errors', 1);

include("CompteDansFichier.php");
include ("Profile.php");


class TestEnUtilisantLeShellUnix {

    /* @var $profiler Profile */
    private $profiler;
    private $fileName;

    public function __construct($fileName){
        $this->profiler = new Profile();
        $this->fileName = $fileName;
    }

    public function testEnUtilisantLeShellUnix() {
        $this->profiler->profile("CompteDansFichier",
                                 "enUtilisantLeShellUnix",
                                 [18, $this->fileName],
                                 10);
    }

    public function printDetails() {
        $this->profiler->printDetails();
    }
}


$fichier = $argv[1];

$test = new TestEnUtilisantLeShellUnix($fichier);
$test->testEnUtilisantLeShellUnix();
$test->printDetails();

?>
