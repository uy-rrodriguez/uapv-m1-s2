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
import android.widget.ProgressBar;
import android.media.MediaPlayer;
import android.media.AudioManager;

import fr.ceri.rrodriguez.playerdistribue.R;
import fr.ceri.rrodriguez.playerdistribue.activity.PlayerActivity;
import fr.ceri.rrodriguez.playerdistribue.activity.PhrasesFragment;
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
 * Created by Ricci on 18/05/2017.
 */

public class PhraseAdapter extends RecyclerView.Adapter<PhraseAdapter.PhraseViewHolder> {
    private String[] arrPhrases;
    private PhrasesFragment.PhrasesDialogListener listener;
    private PhrasesFragment dialogPhrases;
    
    // Constructeur
    public PhraseAdapter(String[] arrPhrases, PhrasesFragment dialogPhrases, PhrasesFragment.PhrasesDialogListener listener) {
        this.arrPhrases = arrPhrases;
        this.dialogPhrases = dialogPhrases;
        this.listener = listener;
    }


    // Changer la liste de chansons
    public void setArrPhrases(String[] arrPhrases) {
        this.arrPhrases = arrPhrases;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public PhraseAdapter.PhraseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_phrase, parent, false);

        PhraseViewHolder viewHolder = new PhraseViewHolder(v, this.dialogPhrases, this.listener);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PhraseViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        String phrase = arrPhrases[position];
        holder.setPhrase(phrase);

        // Modification des elements visuels
        holder.textViewPhrase.setText(phrase);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arrPhrases.length;
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
    class PhraseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        String phrase;
        TextView textViewPhrase;
        
        // Pour réagir aux actions sur les phrases
        PhrasesFragment dialogPhrases;
        PhrasesFragment.PhrasesDialogListener listener;

        
        public PhraseViewHolder(View itemView, PhrasesFragment dialogPhrases, PhrasesFragment.PhrasesDialogListener listener) {
            super(itemView);
            this.textViewPhrase = (TextView) itemView.findViewById(R.id.phrase);
            itemView.setOnClickListener(this);
            
            // Popup avec les phrases
            this.dialogPhrases = dialogPhrases;
            
            // Interface qui va écouter les clicks sur le popup de phrases
            this.listener = listener;
        }

        public void setPhrase(String phrase) {
            this.phrase = phrase;
        }


        @Override
        public void onClick(View view) {
            try {
                // Je ferme le popup et l'application principale va lancer la tâche qui va traiter la commande de phrase
                this.listener.onPhraseClick(this.phrase, this.dialogPhrases);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
