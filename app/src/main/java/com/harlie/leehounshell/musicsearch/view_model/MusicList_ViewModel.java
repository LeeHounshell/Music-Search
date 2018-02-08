package com.harlie.leehounshell.musicsearch.view_model;

import android.arch.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.harlie.leehounshell.musicsearch.model.MusicModelList;
import com.harlie.leehounshell.musicsearch.util.LogHelper;
import com.harlie.leehounshell.musicsearch.util.MusicSearchResults;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MusicList_ViewModel extends ViewModel {
    private final static String TAG = "LEE: <" + MusicList_ViewModel.class.getSimpleName() + ">";

    private String searchResults;
    private MusicModelList musicModelList;

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(MusicSearchResults.MusicSearchResultsEvent event) {
        LogHelper.v(TAG, "onMessageEvent");
        searchResults = event.getSearchResults();
        MusicListSearchResultsEvent searchResultsEvent = new MusicListSearchResultsEvent();
        EventBus.getDefault().post(searchResultsEvent);
    }

    public void processSearchResults() {
        LogHelper.v(TAG, "processSearchResults");
        Gson gson = new Gson();
        musicModelList = gson.fromJson(searchResults, MusicModelList.class);
    }

    public MusicModelList getMusicList() {
        LogHelper.v(TAG, "getMusicList");
        return musicModelList;
    }

    public void setMusicList(MusicModelList musicModelList) {
        LogHelper.v(TAG, "setMusicList");
        this.musicModelList = musicModelList;
    }

    @SuppressWarnings("WeakerAccess")
    public String getSearchResults() {
        LogHelper.v(TAG, "getSearchResults");
        return searchResults;
    }

    public void setSearchResults(String searchResults) {
        LogHelper.v(TAG, "setSearchResults");
        this.searchResults = searchResults;
    }

    class MusicListSearchResultsEvent {
        private final String TAG = "LEE: <" + MusicListSearchResultsEvent.class.getSimpleName() + ">";

        public String getSearchResults() {
            LogHelper.v(TAG, "getSearchResults");
            return MusicList_ViewModel.this.getSearchResults();
        }
    }
}
