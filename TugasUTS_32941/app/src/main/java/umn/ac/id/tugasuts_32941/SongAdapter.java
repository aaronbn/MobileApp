package umn.ac.id.tugasuts_32941;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.LinkedList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {

    private LayoutInflater inflater;
    private Context context;
    private LinkedList<String> mlistSong;
    ArrayList<String> mlistSong2;

    public SongAdapter(Context context, LinkedList<String> listSong) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.mlistSong=listSong;
        this.mlistSong2 = new ArrayList<String>(listSong);
    }

    @Override
    public SongAdapter.SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_song, parent, false);
        SongHolder holder = new SongHolder(view, this);
        return holder;
    }

    @Override
    public void onBindViewHolder(SongAdapter.SongHolder holder, int position) {
        String mCurrent = mlistSong.get(position);
        holder.serial_number.setText(mCurrent);
        holder.serial_number.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return mlistSong.size();
    }

    class SongHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public final TextView serial_number;
        final SongAdapter mAdapter;



        public SongHolder(View itemView, SongAdapter adapter) {
            super(itemView);
            serial_number = (TextView)itemView.findViewById(R.id.txtSong);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            int mPosition = getLayoutPosition();
            String element = mlistSong.get(mPosition).substring(mlistSong.get(mPosition).lastIndexOf("/")+1).replace(".mp3", "").replace(".wav", "");
            context = v.getContext();
            Intent intentPlayer = new Intent(context, PlayerActivity.class);
            intentPlayer.putExtra("songs", mlistSong2);
            intentPlayer.putExtra("songname", element);
            intentPlayer.putExtra("pos", mPosition);
            context.startActivity(intentPlayer);
        }

    }
}
