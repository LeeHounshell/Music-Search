package com.harlie.leehounshell.musicsearch.util;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.harlie.leehounshell.musicsearch.MusicSearchApplication;
import com.harlie.leehounshell.musicsearch.model.MusicModel;
import com.harlie.leehounshell.musicsearch.service.MusicLyricsIntentService;

import org.greenrobot.eventbus.EventBus;

import static com.harlie.leehounshell.musicsearch.service.MusicLyricsIntentService.STATUS_ERROR;
import static com.harlie.leehounshell.musicsearch.service.MusicLyricsIntentService.STATUS_LYRICS_SEARCH_RESULTS;

public class MusicLyricsResults implements MyResultReceiver.Receiver {
    private final static String TAG = "LEE: <" + MusicLyricsResults.class.getSimpleName() + ">";

    public static class MusicLyricsResultsEvent {
        private final static String TAG = "LEE: <" + MusicLyricsResultsEvent.class.getSimpleName() + ">";

        @SuppressWarnings("CanBeFinal")
        private MusicModel musicModel;
        @SuppressWarnings("CanBeFinal")
        private String lyricsResults;

        MusicLyricsResultsEvent(MusicModel musicModel, String lyricsResults) {
            LogHelper.v(TAG, "MusicLyricsResultsEvent");
            this.musicModel = musicModel;
            this.lyricsResults = lyricsResults;
        }

        public MusicModel getMusicModel() {
            LogHelper.v(TAG, "getMusicModel");
            return musicModel;
        }

        public String getLyrics() {
            LogHelper.v(TAG, "getLyrics");
            return lyricsResults;
        }

        @Override
        public String toString() {
            return "MusicLyricsResultsEvent{" +
                    "musicModel='" + musicModel + '\'' +
                    "lyricsResults='" + lyricsResults + '\'' +
                    '}';
        }
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        LogHelper.v(TAG, "onReceiveResult: resultCode=" + resultCode);
        switch (resultCode) {
            case STATUS_LYRICS_SEARCH_RESULTS: {
                LogHelper.v(TAG, "onReceiveResult: STATUS_LYRICS_SEARCH_RESULTS");
                MusicModel musicModel = resultData.getParcelable(MusicLyricsIntentService.MUSIC_MODEL);
                String lyrics = resultData.getString(MusicLyricsIntentService.LYRICS_SEARCH_RESULTS);
                LogHelper.v(TAG, "===> MUSIC LYRICS: ---> SUCCESSFUL!");
                post(musicModel, lyrics);
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

    private static void post(MusicModel musicModel, String lyricsResults) {
        LogHelper.v(TAG, "post");
        MusicLyricsResultsEvent lyricsResultsEvent = new MusicLyricsResultsEvent(musicModel, lyricsResults);
        EventBus.getDefault().post(lyricsResultsEvent);
    }
}
