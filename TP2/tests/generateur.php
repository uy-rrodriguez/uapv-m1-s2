<?php

class GenerateurUnitTestCase {
    function GenerateurUnitTestCase() {
        $conditions = array();
        $resultats = array();
    }

    private function ecrireEntete($fout, $pathRapporteur, $pathClasseTester) {
        $contenu = file_get_contents("template/head.txt");
        $contenu = str_replace("[!RAPPORTEUR!]", $pathRapporteur, $contenu);
        $contenu = str_replace("[!CLASSE_TESTER!]", $pathClasseTester, $contenu);

        return fwrite($fout, $contenu);
    }

    private function ecrirePied($fout) {
        $contenu = file_get_contents("template/foot.txt");
        return fwrite($fout, $contenu);
    }

    private function ecrireTest($fout, $nomTest, $valeurs, $resultatAttendu) {
        $contenu = file_get_contents("template/test.txt");

        $contenu = str_replace("[!NOM_TEST!]", $nomTest, $contenu);

        for ($i = 0; $i < count($valeurs); $i++) {
            $contenu = str_replace("[!VALEUR_$i!]", $valeurs[$i], $contenu);
        }

        $contenu = str_replace("[!RESULTAT!]", $resultatAttendu, $contenu);

        return fwrite($fout, $contenu);
    }

    function generer($data, $out) {
        // Ouverture des fichiers I/O
        if (($fin = fopen($data, "r")) === false) {
            echo "<div>Erreur pour ouvrir le fichier d'entrée</div>";
            return false;
        }

        if (($fout = fopen($out, "r+")) === false) {
            echo "<div>Erreur pour créer le fichier de sortie</div>";
            return false;
        }


        // ------------------------ Charge du fichier ------------------------
        // Lecture de conditions
        $count = 0;
        while(($ligne = fgetcsv($fin, 0, ";")) !== false) {

            $count++;
            if ($count >= 50)
                break;

            if ($ligne[0] === NULL)
                break;

            $valeurs = array();
            foreach (array_slice($ligne, 1) as $val) {
                array_push($valeurs, ($val == "1"));
            }

            $this->conditions[$ligne[0]] = $valeurs;
        }

        // Lecture de resultats attendus
        $count = 0;
        while(($ligne = fgetcsv($fin, 0, ";")) !== false) {

            $count++;
            if ($count >= 50)
                break;

            $valeurs = array();
            foreach (array_slice($ligne, 1) as $val) {
                array_push($valeurs, ($val == "1"));
            }

            $this->resultats[$ligne[0]] = $valeurs;
        }



        // ------------------------ Ecriture du fichier PHP ------------------------
        // Entete
        $this->ecrireEntete($fout, "../monRapporteurHTML.class.php", "../queFaireAujourdhui.php");

        // Parcours dans les conditions et résultats
        $conditionsKeys = array_keys($this->conditions);
        $resultatsKeys = array_keys($this->resultats);
        $nombreTests = count($this->conditions[$conditionsKeys[0]]);

        // Génération d'un test par combinaison de valeurs
        for ($i = 0; $i < $nombreTests; $i++) {

            // Tableau avec les valeurs des conditions
            $valeursConditions = array();
            foreach ($conditionsKeys as $key) {
                $valeursConditions[] = $this->conditions[$key][$i];
            }

            // String résultant attendu
            // Le string est construit à partir des labels des différents résultats possibles.
            // Si pour la combinaison actuelle la valeur dans le tableau $resultats est True,
            // le label de cette condition doit appraître dans le retour de la fonction à tester.
            //
            // ATTENTION :
            //     Une grande limitation de cette méthode est qu'elle est fortement dépendante de l'orde
            //     de déclaration des résultats dans le fichier CSV d'entrée. J'ai fait exprès de mettre
            //     Bronze et Nage à la fin, afin que Lit et Plage soient en première place.
            //
            $resultat = "";
            $resultatPieces = array();
            foreach ($resultatsKeys as $key) {
                $isLabelDansResultat = $this->resultats[$key][$i];
                $resultatPieces[] = ($isLabelDansResultat ? $key : "?");
            }

            // On fait la concatenation du tableau auxiliaire et cela donne le résultat attendue de la méthode en test
            $resultat = implode(":", $resultatPieces);

            // On écrit le test dans le fichier de sortie
            $this->ecrireTest($fout, "Combinaison$i", $valeursConditions, $resultat);
        }


        // Pied
        $this->ecrirePied($fout);

        return true;
    }
}


// Exécution si appel par GET ou POST
$data = $_REQUEST["data"];
$out = $_REQUEST["out"];

$gen = new GenerateurUnitTestCase();
$res = $gen->generer($data, $out);

if ($res)
    echo "<div>Génération OK</div>";
else
    echo "<div>Génération KO</div>";
?>
