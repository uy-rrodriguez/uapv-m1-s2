<?php

require_once('Hasard.class.php');

class RevolverA5Coups {

    static $range = false;
    private $balles = [];
    private $pos = 0;

    function __construct() {
        global $range;
        $range = false;
        $this->balles = [0, 0, 0, 0, 0];
        $this->pos = 0;
    }

    // Retourne BANG ou CLIC
    function appuyerSurDetente() {
        global $range;
        if ($range)
            return "CLIC";

        $ok = ($this->balles[$this->pos]);
        $this->balles[$this->pos] = 0;
        $this->pos = ($this->pos + 1) % 5;
        return ($ok ? "BANG" : "CLIC");
    }

    function chargerUneCartouche() {
        $this->balles = [1, 1, 1, 1, 1];
    }

    static function rangerSousClef() {
        global $range;
        $range = true;
    }

    // Reçoit un objet de type Hasard
    function tournerAleatoirementBarrillet($hasard) {
        $this->pos = ($hasard->tirageEntre2Bornes(1, 5) - 1);
    }

    function viderBarillet() {
        $this->balles = [0, 0, 0, 0, 0];
    }

}

?>
