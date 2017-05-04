package fr.ceri.rrodriguez.playerdistribue.activity;

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

import fr.ceri.rrodriguez.playerdistribue.R;
import fr.ceri.rrodriguez.playerdistribue.adapter.SongsAdapter;

/**
 * Created by Ricci on 27/04/2017.
 */

public class HomeFragment extends Fragment {

    // Pour la liste de chansons
    private RecyclerView songsRecyclerView;
    private RecyclerView.Adapter songsAdapter;
    private RecyclerView.LayoutManager songsLayoutManager;

    // Button STOP
    private ImageButton btnStop;


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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

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
        String [] listeChansonsTest = {"The Eagles - Hotel California",
                "Foo Fighters - The Pretender",
                "John Lenon - Imagine",
                "La Beriso - Una noche más",
                "El Cuarteto de Nos - Hoy estoy raro",
                "La Trampa - Caída libre",
                "Hereford - Bienvenida al show",
                "Lianne La Havas - Full Live Concert"};
        songsAdapter = new SongsAdapter(listeChansonsTest);
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

        return rootView;
    }

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
        public void onClick(View v) {
            try {
                RelativeLayout relative = (RelativeLayout) v.getParent();
                TextView viewChansonActuelle = (TextView) relative.findViewById(R.id.playing_song_name);

                // SnackBar
                Snackbar snackbar = Snackbar.make(v, "Stop " + viewChansonActuelle.getText(), Snackbar.LENGTH_LONG);
                snackbar.show();

                viewChansonActuelle.setText("");


                // On change la chanson stockee dans le contexte
                PlayerActivity host = (PlayerActivity) v.getContext();
                host.getSession().setChansonActuelle("");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
