#include "StructCommande.ice"

module AppMP3Player {
    /**
     * Cette interface représente les méthodes fournies par le méta-serveur, qui sont accesibles par
     * le module de traitement de commandes et les mini-serveurs.
     */
    interface Metaserveur {
        Commande traiterCommande(string ipClient, Commande commande);
        int enregistrerServeur(string ipServeur);
        void supprimerServeur(int idServeur);
    };

};
