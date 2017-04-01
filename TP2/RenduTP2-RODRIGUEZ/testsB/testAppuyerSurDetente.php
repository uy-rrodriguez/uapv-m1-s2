<?php

require_once(dirname(__FILE__) . '/TP2BUnitTestCase.class.php');

class TP2BTestAppuyerSurDetente extends TP2BUnitTestCase {

    function testAppuyerSurDetente() {
        $revolver = new RevolverA5coups();
        $this->assertEqual($revolver->appuyerSurDetente(), "CLIC");

        $revolver->chargerUneCartouche();
        $this->assertEqual($revolver->appuyerSurDetente(), "BANG");
    }
}

?>
