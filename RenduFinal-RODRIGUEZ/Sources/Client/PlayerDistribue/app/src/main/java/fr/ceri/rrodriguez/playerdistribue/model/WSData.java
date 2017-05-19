package fr.ceri.rrodriguez.playerdistribue.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;


public class WSData {

    public static final String COM_PLAY         = "play";
    public static final String COM_STREAM_ADDR  = "stream_addr";
    public static final String COM_PAUSE        = "pause";
    public static final String COM_STOP         = "stop";
    public static final String COM_LIST         = "to_list";
    public static final String COM_SEARCH       = "search";

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


    /**
     * Retourne la valeur de retour parsée en tant que liste d'objets JSON
     */
    public List<Map<String, Object>> parseRetourListeJSON() {
        List listeRetour = new ArrayList<>();

        try {
            JSONArray listeJSON = new JSONArray(retour);

            for (int i=0; i < listeJSON.length(); i++) {
                Map<String, Object> element = new HashMap<>();

                JSONObject o = listeJSON.getJSONObject(i);
                Iterator<String> it = o.keys();

                while (it.hasNext()) {
                    String key = it.next();
                    element.put(key, o.get(key));
                }

                listeRetour.add(element);
            }
        }
        catch (Exception e) {}

        return listeRetour;
    }

    /**
     * Retourne la valeur de retour parsée en tant que String
     */
    public String parseRetourString() {
        return retour.substring(1, retour.length()-1);
    }


    @Override
    public String toString() {
        return "{\"commande\": \"" + commande + "\", "
            + "\"retour\": \"" + retour + "\", "
            + "\"erreur\": " + erreur + ", "
            + "\"msgErreur\": \"" + msgErreur + "\"}";
    }

}
