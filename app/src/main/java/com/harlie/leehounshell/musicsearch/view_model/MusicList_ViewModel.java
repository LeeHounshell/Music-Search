package com.harlie.leehounshell.musicsearch.view_model;

import android.arch.lifecycle.ViewModel;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.harlie.leehounshell.musicsearch.MusicSearchApplication;
import com.harlie.leehounshell.musicsearch.R;
import com.harlie.leehounshell.musicsearch.model.MusicModel;
import com.harlie.leehounshell.musicsearch.model.MusicModelList;
import com.harlie.leehounshell.musicsearch.service.MusicLyricsIntentService;
import com.harlie.leehounshell.musicsearch.util.CustomToast;
import com.harlie.leehounshell.musicsearch.util.LogHelper;
import com.harlie.leehounshell.musicsearch.util.MusicLyricsResults;
import com.harlie.leehounshell.musicsearch.util.MusicSearchResults;
import com.harlie.leehounshell.musicsearch.util.MyResultReceiver;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MusicList_ViewModel extends ViewModel {
    private final static String TAG = "LEE: <" + MusicList_ViewModel.class.getSimpleName() + ">";

    private String searchResults;
    private MusicModelList musicModelList;

    public void searchForLyrics(MusicModel musicModel) {
        LogHelper.v(TAG, "searchForMusic: artist=" + musicModel.getArtistName() + ", songName=" + musicModel.getTrackName());
        MyResultReceiver receiver = new MyResultReceiver(new Handler());
        receiver.setReceiver(new MusicLyricsResults());
        MusicLyricsIntentService.startActionFindLyrics(MusicSearchApplication.getAppContext(), musicModel, receiver);
    }

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
        try {
            musicModelList = gson.fromJson(searchResults, MusicModelList.class);
        }
        catch (JsonSyntaxException e) {
            LogHelper.e(TAG, "*** unable to parse searchResults! *** - e=" + e);
            musicModelList = null;
            String invalidSearchResults = MusicSearchApplication.getAppContext().getString(R.string.invalid_search_results);
            CustomToast.post(invalidSearchResults);
        }
        catch (JsonParseException e) {
            LogHelper.e(TAG, "*** unable to parse searchResults! *** - e=" + e);
            musicModelList = null;
            String invalidSearchResults = MusicSearchApplication.getAppContext().getString(R.string.invalid_search_results);
            CustomToast.post(invalidSearchResults);
        }
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

    public int getResultsCount() {
        int resultsCount = 0;
        if (musicModelList != null) {
            resultsCount = musicModelList.getResultsCount();
        }
        return resultsCount;
    }

    class MusicListSearchResultsEvent {
        private final String TAG = "LEE: <" + MusicListSearchResultsEvent.class.getSimpleName() + ">";

        public String getSearchResults() {
            LogHelper.v(TAG, "getSearchResults");
            return MusicList_ViewModel.this.getSearchResults();
        }
    }
}
