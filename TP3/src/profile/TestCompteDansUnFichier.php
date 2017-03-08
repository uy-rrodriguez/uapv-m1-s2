<?php

include("../compte/CompteDansFichier.php");
include ("Profile.php");

class TestCompteDansUnFichier {
    /* @var $profiler Profile */
    private $profiler ;
    private $fileName;
    public function __construct($fileName){
        $this->profiler = new Profile();
        $this->fileName = $fileName;
    }
    public function serieDeTest() {
        ob_start();
        $this->testCreeFichier();
        file_put_contents($this->fileName,ob_get_contents());
        $this->testEnLisantLeFichier();
        ob_clean();

        $this->printDetails();
    }
    public function testCreeFichier(){
        $this->profiler->profile("CompteDansFichier","creeFichier",[1500],3);
    }
    public function testEnLisantLeFichier(){
        $this->profiler->profile("CompteDansFichier","enLisantLeFichier",[10000,]);
    }
    public function printDetails(){
        $this->profiler->printDetails();
    }
}

$testCompteDansFichier = new TestCompteDansUnFichier("/media/allonzo/SIMONVKEY/Test/testFile1.txt");
$testCompteDansFichier->serieDeTest();
?>
