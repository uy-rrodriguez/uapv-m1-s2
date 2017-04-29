package fr.ceri.rrodriguez.playerdistribue.adapter;

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

import fr.ceri.rrodriguez.playerdistribue.R;

/**
 * Created by Ricci on 28/04/2017.
 */

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder> {
    private String[] songsDataset;
    //private SongItemClickListener mClickListener;


    // Provide a suitable constructor (depends on the kind of dataset)
    public SongsAdapter(String[] songsDataset) {
        this.songsDataset = songsDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SongsAdapter.SongViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_song, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        SongViewHolder viewHolder = new SongViewHolder(v);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.songName.setText(songsDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return songsDataset.length;
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
        TextView songName;

        public SongViewHolder(View itemView) {
            super(itemView);
            songName = (TextView) itemView.findViewById(R.id.song_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            try {
                TextView viewChansonClickee = (TextView) view.findViewById(R.id.song_name);

                // SnackBar
                Snackbar snackbar = Snackbar.make(view, "Play " + viewChansonClickee.getText(), Snackbar.LENGTH_LONG);
                snackbar.show();

                RecyclerView recycler = (RecyclerView) view.getParent();
                RelativeLayout fragment = (RelativeLayout) recycler.getParent();
                TextView viewChansonActuelle = (TextView) fragment.findViewById(R.id.playing_song_name);
                viewChansonActuelle.setText(viewChansonClickee.getText());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
