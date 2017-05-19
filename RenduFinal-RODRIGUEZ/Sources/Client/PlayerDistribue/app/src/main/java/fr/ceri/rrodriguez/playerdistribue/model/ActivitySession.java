package fr.ceri.rrodriguez.playerdistribue.model;

import java.io.Serializable;

import android.media.MediaPlayer;


public class ActivitySession implements Serializable {
    private static final long serialVersionUID = 7526472295622776147L;

    private String chansonActuelle;
    private MediaPlayer mediaPlayer;

    public void setChansonActuelle(String chansonActuelle) {
        this.chansonActuelle = chansonActuelle;
    }

    public String getChansonActuelle() {
        return this.chansonActuelle;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }
}
