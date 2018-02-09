package com.harlie.leehounshell.musicsearch.view_model;

import android.arch.lifecycle.ViewModel;
import android.os.Handler;

import com.harlie.leehounshell.musicsearch.MusicSearchApplication;
import com.harlie.leehounshell.musicsearch.service.MusicSearchIntentService;
import com.harlie.leehounshell.musicsearch.util.LogHelper;
import com.harlie.leehounshell.musicsearch.util.MusicSearchResults;
import com.harlie.leehounshell.musicsearch.util.MyResultReceiver;

public class MusicSearch_ViewModel extends ViewModel {
    private final static String TAG = "LEE: <" + MusicSearch_ViewModel.class.getSimpleName() + ">";

    private String searchString;

    public void searchForMusic(String searchString) {
        LogHelper.v(TAG, "searchForMusic: " + searchString);
        this.searchString = searchString;
        MyResultReceiver receiver = new MyResultReceiver(new Handler());
        receiver.setReceiver(new MusicSearchResults());
        MusicSearchIntentService.startActionFindMusic(MusicSearchApplication.getAppContext(), searchString, receiver);
    }

    public String getSearchString() {
        LogHelper.v(TAG, "getSearchString");
        return searchString;
    }
}
