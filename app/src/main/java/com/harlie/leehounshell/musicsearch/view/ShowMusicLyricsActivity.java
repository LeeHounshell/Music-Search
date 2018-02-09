package com.harlie.leehounshell.musicsearch.view;

import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.harlie.leehounshell.musicsearch.R;
import com.harlie.leehounshell.musicsearch.model.MusicModel;
import com.harlie.leehounshell.musicsearch.util.LogHelper;
import com.makeramen.roundedimageview.RoundedImageView;

public class ShowMusicLyricsActivity extends BaseActivity {
    private final static String TAG = "LEE: <" + ShowMusicLyricsActivity.class.getSimpleName() + ">";

    private String musicSearchResults;
    private MusicModel musicModel;
    private String musicLyrics;
    private TextView trackName;
    private TextView artistName;
    private TextView albumName;
    private TextView lyrics;
    private RoundedImageView albumImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogHelper.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_music_lyrics);
        if (getIntent().getExtras() != null) {
            musicSearchResults = getIntent().getExtras().getString(KEY_SEARCH_RESULTS, null);
            musicModel = getIntent().getExtras().getParcelable(KEY_MUSIC_MODEL);
            musicLyrics = getIntent().getExtras().getString(KEY_LYRICS_RESULTS, null);
        }
        else {
            LogHelper.e(TAG, "*** onCreate: getIntent().getExtras.getString(KEY_SEARCH_RESULTS) is null! ***");
        }
        if (getActionBar() != null) { // FIXME: for this to work properly I need to add a Toolbar to the screen layouts, for now onBackPressed will do
            getActionBar().setDisplayHomeAsUpEnabled(true); // show back arrow
        }
        else {
            LogHelper.w(TAG, "unable to getActionBar()!");
        }
    }

    @Override
    protected void onResume() {
        LogHelper.v(TAG, "onResume");
        LogHelper.v(TAG, "raw musicModel=" + musicModel);
        LogHelper.v(TAG, "raw musicLyrics=" + musicLyrics);
        musicLyrics = musicLyrics
                .replaceAll("\\\\n", "\n")
                .replaceAll("\\\\'", "'");
        super.onResume();
        trackName = findViewById(R.id.track_name);
        trackName.setText(musicModel.getTrackName());
        artistName = findViewById(R.id.artist_name);
        artistName.setText(musicModel.getArtistName());
        albumName = findViewById(R.id.album_name);
        albumName.setText(musicModel.getCollectionName());
        lyrics = findViewById(R.id.lyrics);
        lyrics.setText(musicLyrics);
        albumImage = findViewById(R.id.album_image);

        albumImage.setOnClickListener(v -> LogHelper.v(TAG, "-FOR THE READER-: i did not put a uTube Search API key here."
                + " That would force readers to setup google/uTube services first.."
                + " but, THIS IS A GOOD PLACE TO SEARCH uTUBE - left as an exercise for the reader"));

        //FIXME: here we are re-requesting the Image via the network!  bad bad bad
        //       the good news is that the image is preserved fine on device rotation.
        Glide.with(this)
                .load(musicModel.getArtworkUrl100()) // NOTE: will be slower loading the large size artwork
                .apply(new RequestOptions()
                        .placeholder(R.drawable.album_image)
                        .fitCenter())
                .into(albumImage);
    }

    @Override
    public void onBackPressed() {
        LogHelper.v(TAG, "onBackPressed");
        goToBrowseMusicSearchResultsActivity(musicSearchResults);
    }

    @Override
    protected void onDestroy() {
        LogHelper.v(TAG, "onDestroy");
        musicSearchResults = null;
        musicModel = null;
        musicLyrics = null;
        trackName = null;
        artistName = null;
        albumName = null;
        lyrics = null;
        albumImage = null;
        super.onDestroy();
    }
}
