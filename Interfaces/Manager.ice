#include "StructCommande.ice"
#include "StructParole.ice"

module AppMP3Player {

    interface Manager {
        Commande commandeVocale(Parole parole);
        Commande commandePhrase(string phrase);
        Commande commandeManuelle(Commande commande);
    };

};
