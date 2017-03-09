#pragma once

module AppMP3Player {

    /**
     * Liste d paramètres qui seront passés à une Commande.
     */
    sequence<string> CommandeParams;

    /**
     * Cette structure représente une commande, avec ses paramètres d'entrée et de sortie.
     */
    struct Commande {
        /**
         * Chaîne représentant la commande à exécuter.
         */
        string commande;

        /**
         * Liste de paramètres pour la commande.
         * L'ordre des paramètres doit être respecté afin que la commande
         * puisse s'exécuter de manière correcte.
         */
        CommandeParams params;

        /**
         * True s'il y a eu une erreur dans le traitement de la commande.
         */
        bool erreur;

        /**
         * Message de l'erreur ou vide s'il n'y a pas eu d'erreur.
         */
        string msgErreur;

        /**
         * Représentation en forme de string pour le retour.
         * JSON pour des objets complexes, comme une liste de chansons.
         */
        string retour;
    };

};
