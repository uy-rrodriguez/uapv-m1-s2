<?php
require_once('G:\Dev\RepoGit\uapv-m1-s2\TP2\testsA/../simpletest/autorun.php');
require_once('G:\Dev\RepoGit\uapv-m1-s2\TP2\testsA/MonRapporteurHTML.class.php');
require_once('G:\Dev\RepoGit\uapv-m1-s2\TP2\testsA/queFaireAujourdhui.php');

// Choix du rapporteur
SimpleTest::prefer(new MonRapporteurHTML());

class TestGenere extends UnitTestCase {
    function setUp() {
        echo "<div style='margin-top: 0px;'>&nbsp;</div>";
    }

    function tearDown() {
        echo "<div style='margin-top: 20px; border-top: 2px solid #CCC;'>&nbsp;</div>";
    }

    function testCombinaison0() {
        // Exécution
        $res = queFaireAujourdhui(0, 0, 0);

        // Assert du résultat
        $this->assertTrue($res == "Lit:?");
    }
    function testCombinaison1() {
        // Exécution
        $res = queFaireAujourdhui(0, 0, 1);

        // Assert du résultat
        $this->assertTrue($res == "Lit:?");
    }
    function testCombinaison2() {
        // Exécution
        $res = queFaireAujourdhui(0, 1, 0);

        // Assert du résultat
        $this->assertTrue($res == "Lit:?");
    }
    function testCombinaison3() {
        // Exécution
        $res = queFaireAujourdhui(0, 1, 1);

        // Assert du résultat
        $this->assertTrue($res == "Lit:?");
    }
    function testCombinaison4() {
        // Exécution
        $res = queFaireAujourdhui(1, 0, 0);

        // Assert du résultat
        $this->assertTrue($res == "Plage:Bronze");
    }
    function testCombinaison5() {
        // Exécution
        $res = queFaireAujourdhui(1, 0, 1);

        // Assert du résultat
        $this->assertTrue($res == "Plage:Bronze");
    }
    function testCombinaison6() {
        // Exécution
        $res = queFaireAujourdhui(1, 1, 0);

        // Assert du résultat
        $this->assertTrue($res == "Plage:Nage");
    }
    function testCombinaison7() {
        // Exécution
        $res = queFaireAujourdhui(1, 1, 1);

        // Assert du résultat
        $this->assertTrue($res == "Plage:Bronze");
    }

}
?>
