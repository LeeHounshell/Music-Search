package com.harlie.leehounshell.musicsearch;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.harlie.leehounshell.musicsearch.util.LogHelper;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class MusicSearchIntentService extends IntentService {
    private final static String TAG = "LEE: <" + MusicSearchIntentService.class.getSimpleName() + ">";

    private static final String SEARCH_ENDPOINT = "https://itunes.apple.com/search?term=";

    // IntentService can perform, e.g. ACTION_FIND_MUSIC
    public static final String ACTION_FIND_MUSIC = "com.harlie.leehounshell.musicsearch.action.ACTION_FIND_MUSIC";

    public static final String MUSIC_SEARCH = "com.harlie.leehounshell.musicsearch.extra.MUSIC_SEARCH";
    public static final String MUSIC_SEARCH_RESULTS = "com.harlie.leehounshell.musicsearch.extra.MUSIC_SEARCH_RESULTS";
    public static final String RECEIVER = "receiver";

    public final static int STATUS_MUSIC_SEARCH_RESULTS = 1;
    public final static int STATUS_ERROR = 2;

    private OkHttpClient okHttpClient;

    public MusicSearchIntentService() {
        super("MusicSearchIntentService");
        if (okHttpClient == null) {
            // avoid creating several instances, should be singleon
            okHttpClient = new OkHttpClient();
        }
    }

    /**
     * Starts this service to perform action FindMusic with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionFindMusic(Context context, String musicSearchText, ResultReceiver searchResultReceiver) {
        LogHelper.v(TAG, "startActionFindMusic");
        Intent intent = new Intent(context, MusicSearchIntentService.class);
        intent.setAction(ACTION_FIND_MUSIC);
        intent.putExtra(MUSIC_SEARCH, musicSearchText);
        intent.putExtra(RECEIVER, searchResultReceiver);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogHelper.v(TAG, "onHandleIntent");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FIND_MUSIC.equals(action)) {
                final String musicSearchText = intent.getStringExtra(MUSIC_SEARCH);
                final ResultReceiver searchResultReceiver = intent.getParcelableExtra(RECEIVER);
                handleActionFindMusic(musicSearchText, searchResultReceiver);
            }
        }
    }

    /**
     * Handle action ACTION_FIND_MUSIC in the provided background thread with the provided parameters.
     */
    private void handleActionFindMusic(String musicSearchText, ResultReceiver receiver) {
        LogHelper.v(TAG, "handleActionFindMusic: musicSearchText=" + musicSearchText);
        Bundle bundle = new Bundle();
        try {
            Request request = new Request.Builder()
                    .url(SEARCH_ENDPOINT + musicSearchText.replaceAll(" ", "+"))
                    .build();

            LogHelper.v(TAG, "sending network request..");
            Response response = okHttpClient.newCall(request).execute(); // thread is already in the background, so synchronous call is ok here
            String results;
            if (response != null && response.body() != null) {
                //noinspection ConstantConditions
                results = response.body().string();
            }
            else {
                LogHelper.e(TAG, "*** network request results are null! ***");
                results = "";
            }

            bundle.putString(MusicSearchIntentService.MUSIC_SEARCH, musicSearchText);
            bundle.putString(MusicSearchIntentService.MUSIC_SEARCH_RESULTS, results); // FIXME: edge case if the results are too big to process in a passed Bundle
            LogHelper.v(TAG, "---> handleActionFindMusic SEND THE RESULTS: " + results);
            receiver.send(STATUS_MUSIC_SEARCH_RESULTS, bundle);
        }
        catch (NullPointerException e) {
            // JUNIT test
            System.err.println("WARNING: NullPointerException in handleActionFindMusic - JUNIT TEST?");
            if (receiver != null) {
                bundle.putString(Intent.EXTRA_TEXT, e.toString());
                receiver.send(STATUS_ERROR, bundle);
            }
        } catch (IOException e) {
            LogHelper.e(TAG, "*** UNABLE TO LOAD JSON *** - e=" + e);
        }
    }

    @Override
    public void onDestroy() {
        LogHelper.v(TAG, "onDestroy");
        super.onDestroy();
        okHttpClient = null;
    }
}

