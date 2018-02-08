package com.harlie.leehounshell.musicsearch.util;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.harlie.leehounshell.musicsearch.MusicSearchApplication;
import com.harlie.leehounshell.musicsearch.MusicSearchIntentService;

import org.greenrobot.eventbus.EventBus;

import static com.harlie.leehounshell.musicsearch.MusicSearchIntentService.STATUS_ERROR;
import static com.harlie.leehounshell.musicsearch.MusicSearchIntentService.STATUS_MUSIC_SEARCH_RESULTS;

public class MusicSearchResults implements MyResultReceiver.Receiver {
    private final static String TAG = "LEE: <" + MusicSearchResults.class.getSimpleName() + ">";

    public static class MusicSearchResultsEvent {
        private final static String TAG = "LEE: <" + MusicSearchResultsEvent.class.getSimpleName() + ">";

        private final String searchResults;

        public MusicSearchResultsEvent (String searchResults) {
            this.searchResults = searchResults;
        }

        public String getSearchResults() {
            LogHelper.v(TAG, "getSearchResults");
            return searchResults;
        }

        @Override
        public String toString() {
            return "MusicSearchResultsEvent{" +
                    "searchResults='" + searchResults + '\'' +
                    '}';
        }
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        LogHelper.v(TAG, "onReceiveResult: resultCode=" + resultCode);
        switch (resultCode) {
            case STATUS_MUSIC_SEARCH_RESULTS: {
                LogHelper.v(TAG, "onReceiveResult: STATUS_MUSIC_SEARCH_RESULTS");
                String search = resultData.getString(MusicSearchIntentService.MUSIC_SEARCH);
                String results = resultData.getString(MusicSearchIntentService.MUSIC_SEARCH_RESULTS);
                LogHelper.v(TAG, "===> MUSIC SEARCH: '" + search + "' ---> SUCCESSFUL!");
                post(results);
                break;
            }
            case STATUS_ERROR: {
                LogHelper.v(TAG, "onReceiveResult: STATUS_ERROR");
                String problem = "Error: " + resultData.getString(Intent.EXTRA_TEXT);
                @SuppressLint("ShowToast") Toast toast = Toast.makeText(MusicSearchApplication.getAppContext(), problem, Toast.LENGTH_SHORT);
                new CustomToast(toast).invoke();
                break;
            }
        }
    }

    public static void post(String searchResults) {
        LogHelper.v(TAG, "post");
        MusicSearchResultsEvent searchResultsEvent = new MusicSearchResultsEvent(searchResults);
        EventBus.getDefault().post(searchResultsEvent);
    }
}
