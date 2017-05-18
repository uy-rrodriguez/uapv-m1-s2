package fr.ceri.rrodriguez.playerdistribue.activity;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.media.MediaPlayer;

import fr.ceri.rrodriguez.playerdistribue.R;
import fr.ceri.rrodriguez.playerdistribue.adapter.PhraseAdapter;

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
 * Created by Ricci on 27/04/2017.
 */

public class PhrasesFragment extends DialogFragment {
    
    /* **************************************************************************************** */
    /*      INTERFACE LISTENER DE CE POPUP                                                      */
    /* **************************************************************************************** */
    
    /* L'app ou fragment qui crée ce popup doit implémenter cette interface pour
        réagir aux événements du popup.
        On passe aussi l'instance du popup.
     */
    public interface PhrasesDialogListener {
        public void onPhraseClick(String phrase, DialogFragment dialog);
        public void onAnnulerClick(DialogFragment dialog);
    }
    
    // Instance de l'interface qui va réagir aux événements du popup
    PhrasesDialogListener monListener;
    
    /* **************************************************************************************** */

    
    // View principale
    View rootView;
    
    // Pour la liste de phrases
    private RecyclerView phrasesRecyclerView;
    private PhraseAdapter phraseAdapter;
    private RecyclerView.LayoutManager phrasesLayoutManager;

    // Progress bar
    //private ProgressBar bar;


    public PhrasesFragment(PhrasesDialogListener listener) {
        this.monListener = listener;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        rootView = inflater.inflate(R.layout.phrases_layout, null);
        builder.setView(rootView)
        
                // Add action buttons
               .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       monListener.onAnnulerClick(PhrasesFragment.this);
                   }
               });
        
        // View creation
        Dialog phrasesDialog = builder.create();
        
        
        // Progress bar
        //bar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        //bar.setIndeterminate(true);
        
        
        /* ********** Liste de phrases ********** */

        phrasesRecyclerView = (RecyclerView) rootView.findViewById(R.id.phrases_recycler_view);
        //phrasesRecyclerView.setHasFixedSize(true);
        phrasesLayoutManager = new LinearLayoutManager(rootView.getContext());
        phrasesRecyclerView.setLayoutManager(phrasesLayoutManager);
        
        String[] commandesVocales = {
            "Play I'm like a bird",
            "play im like a bird",
            "jouer a bird",
            "arrete like a bird",
            "Stop nelly furtado",
            "Lire bitter sweet",
            "Stop The Verve Bitter Sweet Symphony",
            "Joue la chanson Hey soul sister",
            "S'il te plait, Lis Hey soul sister",
            "Android, Arrêtes Soul sister",
            "Hey soul sister Jouer",
            "Hey soul sister Arreter"
        };
        phraseAdapter = new PhraseAdapter(commandesVocales, this, this.monListener);
        phrasesRecyclerView.setAdapter(phraseAdapter);
        
        
        return phrasesDialog;
    }
}
