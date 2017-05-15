package fr.ceri.rrodriguez.playerdistribue.adapter;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.media.AudioManager;

import fr.ceri.rrodriguez.playerdistribue.R;
import fr.ceri.rrodriguez.playerdistribue.activity.PlayerActivity;
import fr.ceri.rrodriguez.playerdistribue.model.SongData;
import fr.ceri.rrodriguez.playerdistribue.model.ActivitySession;

// Communication avec le webservice
import java.util.Arrays;
import android.os.AsyncTask;
import android.util.Log;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import fr.ceri.rrodriguez.playerdistribue.util.WSUtil;
import fr.ceri.rrodriguez.playerdistribue.model.WSData;


/**
 * Created by Ricci on 28/04/2017.
 */

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder> {
    private List<SongData> songsDataset;
    //private SongItemClickListener mClickListener;


    // Provide a suitable constructor (depends on the kind of dataset)
    public SongsAdapter(List<SongData> songsDataset) {
        this.songsDataset = songsDataset;
    }


    // Changer la liste de chansons
    public void setSongsDataset(List<SongData> songsDataset) {
        this.songsDataset = songsDataset;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public SongsAdapter.SongViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_song, parent, false);

        SongViewHolder viewHolder = new SongViewHolder(v);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        SongData songData = songsDataset.get(position);
        holder.setSongData(songData);

        // Modification des elements visuels
        holder.songName.setText(songData.getNom());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return songsDataset.size();
    }


    /*
    // allows clicks events to be caught
    public void setClickListener(SongItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // Parent activity will implement this method to respond to click events
    public interface SongItemClickListener {
        void onItemClick(View view, int position);
    }
    */


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        SongData songData;
        TextView songName;

        public SongViewHolder(View itemView) {
            super(itemView);
            this.songName = (TextView) itemView.findViewById(R.id.song_name);
            itemView.setOnClickListener(this);
        }

        public void setSongData(SongData songData) {
            this.songData = songData;
        }


        @Override
        public void onClick(View view) {
            try {
                //TextView viewChansonClickee = (TextView) view.findViewById(R.id.song_name);

                // Je lance la tache qui va jouer la chanson
                new WSPlayTask(songData, view).execute();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    /* ******************************************************************************* */
    /*    Inner class : WSRequestTask, faire appel à des WS.                           */
    /* ******************************************************************************* */

    public class WSPlayTask extends AsyncTask<Void, Void, WSData> {

        private SongData songData;
        private View view;

        private boolean ok;
        private String streamAddr;
        private ActivitySession session;

        public WSPlayTask(SongData songData, View view) {
            super();

            this.songData = songData;
            this.view = view;

            this.ok = false;
            this.streamAddr = "";
            this.session = null;
        }

        @Override
        protected void onPreExecute() {
            //bar.setVisibility(View.VISIBLE);

            // On charge la session
            PlayerActivity host = (PlayerActivity) this.view.getContext();
            this.session = host.getSession();


            // SnackBar
            Snackbar snackbar = Snackbar.make(this.view, "Play " + this.songData.getNom(),
                                              Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        @Override
        protected WSData doInBackground(Void... params) {

            this.ok = false;
            WSData wsdata = null;

            try {
                // Appel a play
                String urlPart = "manuelle/play/" + this.songData.getId();
                wsdata = WSUtil.callWS(urlPart);

                if (! wsdata.isErreur()) {

                    // Appel pour recuperer l'adresse du streaming
                    urlPart = "manuelle/stream_addr";

                    do {
                        wsdata = WSUtil.callWS(urlPart);
                    }
                    while (!wsdata.isErreur() && wsdata.getRetour().equals("false"));


                    // Une fois que l'adresse a ete recuperee
                    if (! wsdata.isErreur()) {
                        this.streamAddr = wsdata.parseRetourString();
                        this.ok = true;

                        // Media Player
                        //if (this.session.getMediaPlayer() == null)
                            this.session.setMediaPlayer(new MediaPlayer());

                        MediaPlayer mediaPlayer = this.session.getMediaPlayer();

                        //if (mediaPlayer.isPlaying())
                        //    mediaPlayer.stop();

                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.setDataSource(this.streamAddr);
                        mediaPlayer.setVolume(1, 1);
                        mediaPlayer.prepare(); // might take long! (for buffering, etc)
                        mediaPlayer.start();
                    }
                }

                if (wsdata.isErreur()) {
                    throw new Exception(wsdata.getMsgErreur());
                }

            }
            catch (Exception e) {
                Log.e("PlayerActivity", e.getMessage(), e);
            }

            return wsdata;
        }

        @Override
        protected void onPostExecute(WSData wsdata) {
            //bar.setVisibility(View.GONE);

            if (this.ok) {
                Snackbar snackbar = Snackbar.make(this.view, "Adresse " + this.streamAddr,
                                                  Snackbar.LENGTH_LONG);
                snackbar.show();


                // Mise a jour de la vue
                RecyclerView recycler = (RecyclerView) this.view.getParent();
                RelativeLayout fragment = (RelativeLayout) recycler.getParent();
                TextView viewChansonActuelle = (TextView) fragment.findViewById(R.id.playing_song_name);
                viewChansonActuelle.setText(this.songData.getNom());


                // On change la chanson stockee dans le contexte
                this.session.setChansonActuelle(this.songData.getNom());

            }
            else {
                Snackbar snackbar = Snackbar.make(this.view, "Erreur pour jouer la chanson",
                                                  Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }

    }

}
