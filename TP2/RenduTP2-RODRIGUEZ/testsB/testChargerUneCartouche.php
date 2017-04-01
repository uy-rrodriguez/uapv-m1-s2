<?php

require_once(dirname(__FILE__) . '/TP2BUnitTestCase.class.php');

class TP2BTestChargerUneCartouche extends TP2BUnitTestCase {
    // si le barillet est plein pas de CLIC !
    function testChargerUneCartouche() {
        $revolver = new RevolverA5coups();
        $revolver->chargerUneCartouche();

        for ($i = 0; $i < 5; $i++) {
            $this->assertEqual($revolver->appuyerSurDetente(), "BANG");
        }
    }
}

?>
