package fr.ceri.rrodriguez.playerdistribue.model;

public class SongData {
    private int id;
    private String nom;

    public SongData(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }
}
