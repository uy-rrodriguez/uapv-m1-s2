<?php
require_once('../simpletest/autorun.php');
//require_once('../simpletest/unit_tester.php');
//require_once('../simpletest/reporter.php');

require_once('monRapporteurHTML.class.php');
require_once('../queFaireAujourdhui.php');

// Choix du rapporteur
SimpleTest::prefer(new MonRapporteurHTML());


class TestOfQueFaireAujourdhui extends UnitTestCase {
    function log($msg) {
        echo "<div style='font-size: 12px; font-weight: bold; border-bottom: 1px solid #CCC;'>$msg</div>";
    }

    function setUp() {
        echo "<div style='margin-top: 0px;'>&nbsp;</div>";
    }

    function tearDown() {
        echo "<div style='margin-top: 20px; border-top: 2px solid #CCC;'>&nbsp;</div>";
    }

    function testMeteoBelleMerChaudeAvecRequins() {

        // Valeurs
        $this->log('Chargement des données');
        $meteo_belle = true;
        $mer_chaude = true;
        $presence_requins = true;

        // Appel à la fonction
        $this->log('Exécution');
        $res = queFaireAujourdhui($meteo_belle, $mer_chaude, $presence_requins);

        // Assert du résultat
        $this->log('Asserts');
        $this->assertTrue($res == 'Plage:Bronze');
    }

    function testMeteoMauvaiseMerFroideSansRequins() {

        // Valeurs
        $this->log('Chargement des données');
        $meteo_belle = false;
        $mer_chaude = false;
        $presence_requins = false;

        // Appel à la fonction
        $this->log('Exécution');
        $res = queFaireAujourdhui($meteo_belle, $mer_chaude, $presence_requins);

        // Assert du résultat
        $this->log('Asserts');
        $this->assertTrue($res == 'Lit:?');
    }
}
?>
