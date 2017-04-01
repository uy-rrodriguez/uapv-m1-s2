<?php

require_once(dirname(__FILE__) . '/../simpletest/autorun.php');
require_once(dirname(__FILE__) . '/MonRapporteurHTML.class.php');

require_once(dirname(__FILE__) . '/Hasard.class.php');
require_once(dirname(__FILE__) . '/RevolverA5Coups.class.php');

// Creation des Mocks
Mock::generate('Hasard');

// Choix du rapporteur
SimpleTest::prefer(new MonRapporteurHTML());


abstract class TP2BUnitTestCase extends UnitTestCase {

    function setUp() {
        echo "<div style='margin-top: 0px;'>" . $this->getLabel() . "</div>";

        // Defintion des mocks
        $this->hasard = new MockHasard();
        $this->hasard->returns('tirageEntre2Bornes', 1); // défini mock acteur
        $this->hasard->expect('tirageEntre2Bornes', array(1,5));  // défini mock critique

    }

    function tearDown() {
        echo "<div style='margin-top: 20px; border-top: 2px solid #CCC;'>&nbsp;</div>";

        RevolverA5Coups::rangerSousClef();
    }

}

?>
