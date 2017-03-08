<?php
    include("../CompteDansFichier.php");
    $compteFichier = new CompteDansFichier();
    $compteFichier->creeFichier($argv[1]);
?>
