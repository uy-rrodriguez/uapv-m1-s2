<?php
require_once(dirname(__FILE__) . '/TP2BUnitTestCase.class.php');

class TP2BTestViderBarillet extends TP2BUnitTestCase {
    // Si le barillet est bien vide pas de BANG !
    function testViderBarillet() {

        $revolver = new RevolverA5coups();
        $revolver->viderBarillet();

        for ($i = 0; $i < 5; $i++) {
            $this->assertTrue($revolver->appuyerSurDetente() === "CLIC");
        }
    }
}
?>
