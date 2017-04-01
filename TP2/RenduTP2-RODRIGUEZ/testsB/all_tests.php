<?php

require_once(dirname(__FILE__) . '/../simpletest/autorun.php');


class TP2BAllTests extends TestSuite {

    function TP2BAllTests() {
        $this->TestSuite("Tous les tests sur RevolverA5Coups");
        $this->addFile(dirname(__FILE__) . "/testAppuyerSurDetente.php");
        $this->addFile(dirname(__FILE__) . "/testChargerUneCartouche.php");
        $this->addFile(dirname(__FILE__) . "/testRangerSousClef.php");
        $this->addFile(dirname(__FILE__) . "/testTournerAleatoirementBarrillet.php");
        $this->addFile(dirname(__FILE__) . "/testViderBarrillet.php");
    }

}

?>
