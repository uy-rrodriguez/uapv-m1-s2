package fr.ceri.rrodriguez.playerdistribue.model;

public class WSData {

    private String commande;
    private String retour;
    private boolean erreur;
    private String msgErreur;

    public String getRetour() {
        return this.retour;
    }

    public String getCommande() {
        return this.commande;
    }

    public boolean isErreur() {
        return this.erreur;
    }

    public String getMsgErreur() {
        return this.msgErreur;
    }


    @Override
    public String toString() {
        return "{\"commande\": \"" + commande + "\", "
            + "\"retour\": \"" + retour + "\", "
            + "\"erreur\": " + erreur + ", "
            + "\"msgErreur\": \"" + msgErreur + "\"}";
    }

}
