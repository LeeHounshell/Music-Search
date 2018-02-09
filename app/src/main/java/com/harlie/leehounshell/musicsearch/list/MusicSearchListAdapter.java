package com.harlie.leehounshell.musicsearch.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.harlie.leehounshell.musicsearch.MusicSearchApplication;
import com.harlie.leehounshell.musicsearch.R;
import com.harlie.leehounshell.musicsearch.model.MusicModel;
import com.harlie.leehounshell.musicsearch.model.MusicModelList;
import com.harlie.leehounshell.musicsearch.util.CustomToast;
import com.harlie.leehounshell.musicsearch.util.LogHelper;

import java.util.List;

//import com.squareup.picasso.Picasso;

public class MusicSearchListAdapter extends RecyclerView.Adapter<MusicSearchListAdapter.MusicSearchViewHolder> {
    private final static String TAG = "LEE: <" + MusicSearchListAdapter.class.getSimpleName() + ">";

    private final Context context;
    private List<MusicModel> musicModelList;

    public MusicSearchListAdapter(Context context, MusicModelList musicModelList) {
        LogHelper.v(TAG, "MusicSearchListAdapter");
        this.context = context;
        if (musicModelList != null) {
            this.musicModelList = musicModelList.getResults();
            musicModelList.setResultsCount(this.musicModelList.size());
            String foundHowMany = MusicSearchApplication.getAppContext().getString(R.string.found)
                    + " " + musicModelList.getResultsCount()
                    + " " + MusicSearchApplication.getAppContext().getString(R.string.matches);
            CustomToast.post(foundHowMany);
        }
    }

    @Override
    public MusicSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogHelper.v(TAG, "onCreateViewHolder");
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_music_model_row, null);
        return new MusicSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicSearchListAdapter.MusicSearchViewHolder holder, int position) {
        LogHelper.v(TAG, "onBindViewHolder");
        MusicModel musicModel = musicModelList.get(position);
        //Render image using Picasso library
        if (! TextUtils.isEmpty(musicModel.getArtworkUrl100())) {

//            Picasso.with(context)
//                    .load(musicModel.getArtworkUrl100()) // NOTE: will be slower loading the large size artwork
//                    .error(R.drawable.album_image)
//                    .placeholder(R.drawable.album_image)
//                    .fit()
//                    .into(holder.getAlbumCoverArt());

            Glide.with(context)
                    .load(musicModel.getArtworkUrl100()) // NOTE: will be slower loading the large size artwork
                    .apply(new RequestOptions()
                    .placeholder(R.drawable.album_image)
                    .fitCenter())
                    .into(holder.getAlbumCoverArt());

        }
        holder.getTrackName().setText(musicModel.getTrackName());
        holder.getArtistName().setText(musicModel.getArtistName());
        holder.getAlbumName().setText(musicModel.getCollectionName());
    }

    @Override
    public int getItemCount() {
        //LogHelper.v(TAG, "getItemCount");
        return (null != musicModelList ? musicModelList.size() : 0);
    }

    class MusicSearchViewHolder extends RecyclerView.ViewHolder {
        private final String TAG = "LEE: <" + MusicSearchViewHolder.class.getSimpleName() + ">";

        private ImageView albumCoverArt;
        private AppCompatTextView trackName;
        private AppCompatTextView artistName;
        private AppCompatTextView albumName;

        MusicSearchViewHolder(View view) {
            super(view);
            //LogHelper.v(TAG, "MusicSearchViewHolder");
            this.albumCoverArt = view.findViewById(R.id.album_image);
            this.trackName = view.findViewById(R.id.track_name);
            this.artistName = view.findViewById(R.id.artist_name);
            this.albumName = view.findViewById(R.id.album_name);
        }

        ImageView getAlbumCoverArt() {
            //LogHelper.v(TAG, "getAlbumCoverArt");
            return albumCoverArt;
        }

        void setAlbumCoverArt(ImageView albumCoverArt) {
            //LogHelper.v(TAG, "setAlbumCoverArt");
            this.albumCoverArt = albumCoverArt;
        }

        AppCompatTextView getTrackName() {
            //LogHelper.v(TAG, "getTrackName");
            return trackName;
        }

        void setTrackName(AppCompatTextView trackName) {
            //LogHelper.v(TAG, "setTrackName");
            this.trackName = trackName;
        }

        AppCompatTextView getArtistName() {
            //LogHelper.v(TAG, "getArtistName");
            return artistName;
        }

        void setArtistName(AppCompatTextView artistName) {
            //LogHelper.v(TAG, "setArtistName");
            this.artistName = artistName;
        }

        AppCompatTextView getAlbumName() {
            //LogHelper.v(TAG, "getAlbumName");
            return albumName;
        }

        void setAlbumName(AppCompatTextView albumName) {
            //LogHelper.v(TAG, "setAlbumName");
            this.albumName = albumName;
        }
    }
}
