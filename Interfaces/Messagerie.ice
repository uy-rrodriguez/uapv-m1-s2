module AppMP3Player {
    /**
     * La structure Chanson représente l'objet qui sera échangé dans les messages IceStorm
     * entre le méta-serveur et les mini-serveurs.
     */
    struct Chanson {
        string nom;
        string artiste;
        string categorie;

        string ipServeur;
        string path;
    };


    /**
     * Cette interface représente un gestionnaire de chansons du côté mini-serveur.
     * Le gestionnaire sera utilisé via IceStorm en tant que Publisher pour publier des messages.
     * Les méthodes de l'interface sont les différents messages possibles. Pour l'instant :
     *     - ajouter une chanson
     *     - supprimer une chanson
     */
    interface TopicChansonsManager {
        void ajouterChanson(Chanson c);
        void supprimerChanson(Chanson c);
    };


    /**
     * Cette interface représente un gestionnaire de chansons du côté méta-serveur.
     * Le gestionnaire sera utilisé via IceStorm en tant que Publisher pour publier des messages.
     * Les méthodes de l'interface sont les différents messages possibles. Pour l'instant :
     *     - jouer une chanson
     *     - mettre en pause une chanson
     *     - arrêter une chanson
     */
    interface TopicCommandesManager {
        void jouerChanson(Chanson c);
        void pauseChanson(Chanson c);
        void arreterChanson(Chanson c);
    };
};
