package com.harlie.leehounshell.musicsearch.view;

import android.os.Bundle;

import com.harlie.leehounshell.musicsearch.R;
import com.harlie.leehounshell.musicsearch.model.MusicModel;
import com.harlie.leehounshell.musicsearch.util.LogHelper;

public class ShowMusicLyricsActivity extends BaseActivity {
    private final static String TAG = "LEE: <" + BrowseMusicSearchResultsActivity.class.getSimpleName() + ">";

    private MusicModel musicModel;
    private String musicLyrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogHelper.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_music_lyrics);
        if (getIntent().getExtras() != null) {
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
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        LogHelper.v(TAG, "onBackPressed");
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        LogHelper.v(TAG, "onDestroy");
        super.onDestroy();
    }
}
