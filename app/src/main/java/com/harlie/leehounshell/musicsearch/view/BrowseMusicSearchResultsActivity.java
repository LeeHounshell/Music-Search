package com.harlie.leehounshell.musicsearch.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.harlie.leehounshell.musicsearch.R;
import com.harlie.leehounshell.musicsearch.list.MusicSearchListAdapter;
import com.harlie.leehounshell.musicsearch.util.CustomRecyclerView;
import com.harlie.leehounshell.musicsearch.util.CustomToast;
import com.harlie.leehounshell.musicsearch.util.LogHelper;
import com.harlie.leehounshell.musicsearch.view_model.MusicList_ViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BrowseMusicSearchResultsActivity extends BaseActivity {
    private final static String TAG = "LEE: <" + BrowseMusicSearchResultsActivity.class.getSimpleName() + ">";

    private MusicList_ViewModel musicList_viewModel;
    private CustomRecyclerView recyclerView;
    private String musicSearchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogHelper.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_music_search_results);
        if (getIntent().getExtras() != null) {
            musicSearchResults = getIntent().getExtras().getString(KEY_SEARCH_RESULTS, null);
            LogHelper.v(TAG, "onCreate: raw musicSearchResults=" + musicSearchResults);
        }
        else {
            LogHelper.e(TAG, "*** onCreate: getIntent().getExtras.getString(KEY_SEARCH_RESULTS) is null! ***");
        }
        musicList_viewModel = ViewModelProviders.of(this).get(MusicList_ViewModel.class);
        musicList_viewModel.setSearchResults(musicSearchResults);
        musicList_viewModel.processSearchResults();
        if (musicList_viewModel.getMusicList() == null) {
            String tryAgain = getString(R.string.try_again);
            CustomToast.post(tryAgain);
            goToMainActivity(); // foobar, the search failed or the results were too big to fit in the Bundle
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
        super.onResume();
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        LogHelper.v(TAG, "initializeRecyclerView");
        if (recyclerView == null) {
            recyclerView = findViewById(R.id.results_recycler_view);
            if (recyclerView != null) {
                recyclerView.setHasFixedSize(false);
                recyclerView.setNestedScrollingEnabled(false);
                MusicSearchListAdapter musicSearchListAdapter = new MusicSearchListAdapter(this, musicList_viewModel.getMusicList());
                recyclerView.setAdapter(musicSearchListAdapter);
            } else {
                LogHelper.e(TAG, "*** null CustomRecyclerView ***");
                return;
            }

            setNumberOfListColumns();

            // NOTE: this is to get rid of an item "gap" that may appear (usually when moving)
            recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        }
    }

    private void setNumberOfListColumns() {
        LogHelper.v(TAG, "setNumberOfListColumns");
        int three_column_min_width = 1750; // FIXME: move this constant into Firebase config
        int two_column_min_width = 900; // FIXME: move this constant into Firebase config

        if (((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE)
                || ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)) {
            if (getWidth() > three_column_min_width) {
                if (BaseActivity.orientation() == Configuration.ORIENTATION_LANDSCAPE) {
                    LogHelper.v(TAG, "> " + three_column_min_width + " landscape RECYCLER VIEW 3x");
                    recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // 3x side-by-side view
                } else {
                    LogHelper.v(TAG, "> " + three_column_min_width + " portrait RECYCLER VIEW 2x");
                    recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2x side-by-side view
                }
            } else if (getWidth() > two_column_min_width) {
                if (BaseActivity.orientation() == Configuration.ORIENTATION_LANDSCAPE) {
                    LogHelper.v(TAG, "> " + two_column_min_width + " landscape RECYCLER VIEW 2x");
                    recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2x side-by-side view
                } else {
                    LogHelper.v(TAG, "> " + two_column_min_width + " portrait RECYCLER VIEW 1x");
                    recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                }
            } else {
                LogHelper.v(TAG, "default RECYCLER VIEW 1x");
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            }
        } else {
            LogHelper.v(TAG, "(small or normal) RECYCLER VIEW 1x");
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MusicSearchListAdapter.MusicClickItemEvent event) {
        LogHelper.v(TAG, "onMessageEvent");
        String musicLyrics = "FIXME: lyrics go here"; //FIXME: need to create an Intent Service to lookup the song lyrics
        goToShowMusicLyricsActivity(event.getMusicModel(), musicLyrics);
    }

    @Override
    public void onBackPressed() {
        LogHelper.v(TAG, "onBackPressed");
        goToMainActivity();
    }

    @Override
    protected void onDestroy() {
        LogHelper.v(TAG, "onDestroy");
        musicList_viewModel = null;
        recyclerView = null;
        musicSearchResults = null;
        super.onDestroy();
    }
}
