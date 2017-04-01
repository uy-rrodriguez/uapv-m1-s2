<?php

require_once(dirname(__FILE__) . '/TP2BUnitTestCase.class.php');

class TP2BTestRangerSousClef extends TP2BUnitTestCase {

    function testRangerSousClef() {
        $revolver = new RevolverA5coups();
        $revolver->chargerUneCartouche();
        $this->assertEqual($revolver->appuyerSurDetente(), "BANG");

        RevolverA5Coups::rangerSousClef();
        $this->assertEqual($revolver->appuyerSurDetente(), "CLIC");
    }
}

?>
