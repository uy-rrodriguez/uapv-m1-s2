package fr.ceri.rrodriguez.playerdistribue.model;

import java.io.Serializable;

public class ActivitySession implements Serializable {
    private static final long serialVersionUID = 7526472295622776147L;

    private String chansonActuelle;

    public void setChansonActuelle(String chansonActuelle) {
        this.chansonActuelle = chansonActuelle;
    }

    public String getChansonActuelle() {
        return this.chansonActuelle;
    }
}
