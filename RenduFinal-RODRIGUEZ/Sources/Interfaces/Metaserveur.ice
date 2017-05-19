#include "StructCommande.ice"

module AppMP3Player {
    /**
     * Cette interface représente les méthodes fournies par le Métaserveur, qui sont accesibles par
     * le module de traitement de commandes.
     */
    interface Metaserveur {
        Commande traiterCommande(string ipClient, Commande commande);
    };

};
