#include "StructCommande.ice"

module AppMP3Player {

    interface Metaserveur {
        Commande traiterCommande(string ipClient, Commande commande);
        int enregistrerServeur(string ipServeur);
        void supprimerServeur(int idServeur);
    };

};
