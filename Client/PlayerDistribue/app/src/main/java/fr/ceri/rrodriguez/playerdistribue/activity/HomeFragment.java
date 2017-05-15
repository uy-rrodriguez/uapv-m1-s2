package fr.ceri.rrodriguez.playerdistribue.activity;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.media.MediaPlayer;

import fr.ceri.rrodriguez.playerdistribue.R;
import fr.ceri.rrodriguez.playerdistribue.adapter.SongsAdapter;
import fr.ceri.rrodriguez.playerdistribue.model.SongData;

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

// FAB
import android.support.design.widget.FloatingActionButton;
import android.content.Intent;
import android.speech.RecognizerIntent;


/**
 * Created by Ricci on 27/04/2017.
 */

public class HomeFragment extends Fragment {

    // View principale
    View rootView;

    // View pour afficher la SnackBar
    RelativeLayout snackView;

    // Pour la liste de chansons
    private RecyclerView songsRecyclerView;
    //private RecyclerView.Adapter songsAdapter;
    private SongsAdapter songsAdapter;
    private RecyclerView.LayoutManager songsLayoutManager;

    // Button STOP
    private ImageButton btnStop;

    // FAB
    private FloatingActionButton fab;

    // Progress bar
    private ProgressBar bar;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        snackView = (RelativeLayout) rootView.getParent();


        // Progress bar
        bar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        bar.setIndeterminate(true);


        // Inflate the layout for this fragment

        // Pour afficher la liste de chansons
        songsRecyclerView = (RecyclerView) rootView.findViewById(R.id.songs_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //songsRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        songsLayoutManager = new GridLayoutManager(rootView.getContext(), 3);
        songsRecyclerView.setLayoutManager(songsLayoutManager);

        // specify an adapter
        songsAdapter = new SongsAdapter(new ArrayList<SongData>());
        songsRecyclerView.setAdapter(songsAdapter);


        // Barre avec le nom de la chanson actuelle
        // On recupere la chanson stockee dans le contexte
        PlayerActivity host = (PlayerActivity) this.getActivity();
        String nomChansonActuelle = host.getSession().getChansonActuelle();
        TextView viewChansonActuelle = (TextView) rootView.findViewById(R.id.playing_song_name);
        viewChansonActuelle.setText(nomChansonActuelle);

        // Button STOP
        btnStop = (ImageButton) rootView.findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new BtnStopClickListener());


        // Button FAB
        /*
        fab = (FloatingActionButton) rootView.findViewById(R.id.myFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Enter the ");
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
                startActivityForResult(intent, 100);
            }
        });
        */


        // Je lance la tache qui va recuperer la liste de chansons
        new WSListerChansonsTask().execute();


        return rootView;
    }

    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // Reconnaissance
        ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        String result = results.get(0);

        Log.d("PlayerActivity", "RECONNAISSANCE : " + result);

    }
    */


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    // Classe pour implémenter le click du bouton
    private class BtnStopClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            try {
                // Je lance la tache qui va arreter le streaming
                new WSStopTask(view).execute();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }





    /* ******************************************************************************* */
    /*    Inner class : WSRequestTask, faire appel à des WS.                           */
    /* ******************************************************************************* */

    public class WSListerChansonsTask extends AsyncTask<Void, Void, WSData> {

        @Override
        protected void onPreExecute() {
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected WSData doInBackground(Void... params) {

            WSData wsdata = null;

            try {
                // Appel a stop
                String urlPart = "manuelle/to_list";
                wsdata = WSUtil.callWS(urlPart);

                /*
                final String url = WSUtil.URL_WS + "manuelle/to_list";

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

                HttpEntity<String> entity = new HttpEntity<String>("", headers);

                //wsdata = restTemplate.getForObject(url, WSData.class);
                ResponseEntity<WSData> resp = restTemplate.exchange(url, HttpMethod.GET, entity, WSData.class);
                wsdata = resp.getBody();
                */
            }
            catch (Exception e) {
                Log.e("PlayerActivity", e.getMessage(), e);
            }

            return wsdata;
        }

        @Override
        protected void onPostExecute(WSData wsdata) {
            bar.setVisibility(View.GONE);

            // Traitement de la reponse
            List<SongData> listeChansons = new ArrayList<>();
            int i = 0;

            for (Map<String, Object> o : wsdata.parseRetourListeJSON()) {
                listeChansons.add(new SongData(i, (String) o.get("nom")));
                i++;
            }

            songsRecyclerView.removeAllViews();
            songsAdapter.setSongsDataset(listeChansons);
            songsAdapter.notifyDataSetChanged();
        }

    }



    /* ******************************************************************************* */
    /*    Inner class : WSStopTask, faire appel au WS pour arreter le streaming.       */
    /* ******************************************************************************* */

    public class WSStopTask extends AsyncTask<Void, Void, WSData> {

        private View view;
        private boolean ok;

        public WSStopTask(View view) {
            super();

            this.view = view;
            this.ok = false;
        }

        @Override
        protected void onPreExecute() {
            //bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected WSData doInBackground(Void... params) {

            this.ok = false;
            WSData wsdata = null;

            try {
                // Appel a stop
                String urlPart = "manuelle/stop";
                wsdata = WSUtil.callWS(urlPart);

                if (! wsdata.isErreur()) {
                    this.ok = true;
                }
                else {
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

                // Mise a jour de la vue
                RelativeLayout relative = (RelativeLayout) this.view.getParent();
                TextView viewChansonActuelle = (TextView) relative.findViewById(R.id.playing_song_name);
                viewChansonActuelle.setText("");


                // On change la chanson stockee dans le contexte
                PlayerActivity host = (PlayerActivity) this.view.getContext();
                String nomChanson = host.getSession().getChansonActuelle();;
                host.getSession().setChansonActuelle("");


                // Media Player
                if (host.getSession().getMediaPlayer() != null) {
                    MediaPlayer mediaPlayer = host.getSession().getMediaPlayer();
                    if (mediaPlayer.isPlaying())
                            mediaPlayer.stop();
                }


                // Snackbar
                Snackbar snackbar = Snackbar.make(this.view, "Stop chanson " + nomChanson,
                                                  Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            else {
                Snackbar snackbar = Snackbar.make(this.view, "Erreur pour arreter la chanson",
                                                  Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }

    }

}
