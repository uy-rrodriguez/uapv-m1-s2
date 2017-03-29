<?php

class GenerateurUnitTestCase {
    function GenerateurUnitTestCase() {
        $conditions = array();
        $resultats_1 = array();
        $resultats_2 = array();
    }

    private function ecrireEntete($fout, $pathRapporteur, $pathClasseTester) {
        echo "<div>ecrireEntete</div>" . PHP_EOL;

        $contenu = file_get_contents("template/head.txt");
        $contenu = str_replace("[!RAPPORTEUR!]", $pathRapporteur, $contenu);
        $contenu = str_replace("[!CLASSE_TESTER!]", $pathClasseTester, $contenu);

        return fwrite($fout, $contenu);
    }

    private function ecrirePied($fout) {
        echo "<div>ecrirePied</div>" . PHP_EOL;

        $contenu = file_get_contents("template/foot.txt");
        return fwrite($fout, $contenu);
    }

    private function ecrireTest($fout, $nomTest, $valeurs, $resultatAttendu) {
        echo "</div>ecrireTest</div>" . PHP_EOL;

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
            echo "<div>Erreur pour ouvrir le fichier d'entrée</div>" . PHP_EOL;
            return false;
        }

        if (($fout = fopen($out, "w")) === false) {
            echo "<div>Erreur pour créer le fichier de sortie</div>" . PHP_EOL;
            return false;
        }


        // ------------------------ Charge du fichier ------------------------
        // Lecture de conditions
        $count = 0;
        while(($ligne = fgetcsv($fin, 0, ";")) !== false) {

            if ($ligne[0] === NULL)
                break;

            $valeurs = array();
            foreach (array_slice($ligne, 1) as $val) {
                array_push($valeurs, $val);
            }

            $this->conditions[$ligne[0]] = $valeurs;
        }

        // Lecture de resultats attendus (premiere colonne, avant le :)
        $count = 0;
        while(($ligne = fgetcsv($fin, 0, ";")) !== false) {

            if ($ligne[0] === NULL)
                break;

            $valeurs = array();
            foreach (array_slice($ligne, 1) as $val) {
                array_push($valeurs, $val);
            }

            $this->resultats_1[$ligne[0]] = $valeurs;
        }

        // Lecture de resultats attendus (deuxieme colonne, après le :)
        $count = 0;
        while(($ligne = fgetcsv($fin, 0, ";")) !== false) {

            $count++;
            if ($count >= 50)
                break;

            $valeurs = array();
            foreach (array_slice($ligne, 1) as $val) {
                array_push($valeurs, $val);
            }

            $this->resultats_2[$ligne[0]] = $valeurs;
        }



        // ------------------------ Ecriture du fichier PHP ------------------------
        // Entete
        $this->ecrireEntete($fout, "monRapporteurHTML.class.php", "../queFaireAujourdhui.php");

        // Parcours dans les conditions et résultats
        $conditionsKeys = array_keys($this->conditions);
        $resultatsKeys_1 = array_keys($this->resultats_1);
        $resultatsKeys_2 = array_keys($this->resultats_2);
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
            //     En plus, je ne gère que des résultats à deux valeurs (par exemple "Lit:?")
            //

            $resultat = "";

            // Première partie du résultat (avant le :)
            foreach ($resultatsKeys_1 as $key) {
                if ($this->resultats_1[$key][$i]) {
                    $resultat = $key . ":";
                    break;
                }
            }

            // Deuxième partie du résultat (après le :)
            foreach ($resultatsKeys_2 as $key) {
                if ($this->resultats_2[$key][$i]) {
                    $resultat .= $key;
                    break;
                }
            }

            // Si à la fin de la deuxième boucle on n'a pas ajouté de valeur, j'ajoute le "?"
            if (substr($resultat, -1, 1) == ":")
                $resultat .= "?";

            // On écrit le test dans le fichier de sortie
            $this->ecrireTest($fout, "Combinaison$i", $valeursConditions, $resultat);
        }


        // Pied
        $this->ecrirePied($fout);

        fclose($fout);

        return true;
    }
}


// Exécution si appel par GET ou POST
if (php_sapi_name() == "cli") {
    $data = $_SERVER['argv'][1];
    $out = $_SERVER['argv'][2];
}
else {
    $data = $_REQUEST["data"];
    $out = $_REQUEST["out"];
}


$gen = new GenerateurUnitTestCase();
$res = $gen->generer($data, $out);

if ($res)
    echo "<div>Generation OK</div>" . PHP_EOL;
else
    echo "<div>Generation KO</div>" . PHP_EOL;
?>
