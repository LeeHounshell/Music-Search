package com.harlie.leehounshell.musicsearch.view;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.harlie.leehounshell.musicsearch.R;
import com.harlie.leehounshell.musicsearch.list.MusicSearchListAdapter;
import com.harlie.leehounshell.musicsearch.util.CustomRecyclerView;
import com.harlie.leehounshell.musicsearch.util.LogHelper;

public class BrowseMusicSearchResultsActivity extends BaseActivity {
    private final static String TAG = "LEE: <" + BrowseMusicSearchResultsActivity.class.getSimpleName() + ">";

    CustomRecyclerView recyclerView;
    MusicSearchListAdapter musicSearchListAdapter;
    String musicSearchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogHelper.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_music_search_results);
        if (getIntent().getExtras() != null) {
            musicSearchResults = getIntent().getExtras().getString(KEY_SEARCH_RESULTS, null);
        }
        else {
            LogHelper.e(TAG, "*** onCreate: getIntent().getExtras.getString(KEY_SEARCH_RESULTS) is null! ***");
        }
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        LogHelper.v(TAG, "onCreateView");
        return super.onCreateView(parent, name, context, attrs);
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
                musicSearchListAdapter = new MusicSearchListAdapter(this, musicSearchResults);
                recyclerView.setAdapter(musicSearchListAdapter);
            } else {
                LogHelper.e(TAG, "*** null CustomRecyclerView ***");
                return;
            }

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

            // NOTE: this is to get rid of an item "gap" that may appear when moving
            recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        LogHelper.v(TAG, "onDestroy");
        recyclerView = null;
        musicSearchResults = null;
        super.onDestroy();
    }
}