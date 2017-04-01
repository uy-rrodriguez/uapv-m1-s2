<?php

require_once(dirname(__FILE__) . '/TP2BUnitTestCase.class.php');

class TP2BTestTournerAleatoirementBarrillet extends TP2BUnitTestCase {
    // Apres de tourner le barrillet, si le revolver est charge on devrait pouvoir tirer

    function testTournerAleatoirementBarrillet() {
        $revolver = new RevolverA5coups();
        $revolver->chargerUneCartouche();

        // On lui passe le Mock cree dans la classe mere
        $revolver->tournerAleatoirementBarrillet($this->hasard);

        for($i = 0;$i < 5;$i++) {
            $this->assertEqual($revolver->appuyerSurDetente(), "BANG");
        }
    }
}

?>
