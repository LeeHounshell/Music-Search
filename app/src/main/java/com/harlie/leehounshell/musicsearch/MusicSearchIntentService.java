package com.harlie.leehounshell.musicsearch;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.harlie.leehounshell.musicsearch.util.FileUtil;
import com.harlie.leehounshell.musicsearch.util.LogHelper;

import java.io.IOException;
import java.io.InputStream;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class MusicSearchIntentService extends IntentService {
    private final static String TAG = "LEE: <" + MusicSearchIntentService.class.getSimpleName() + ">";

    // IntentService can perform, e.g. ACTION_FIND_MUSIC
    public static final String ACTION_FIND_MUSIC = "com.harlie.leehounshell.musicsearch.action.ACTION_FIND_MUSIC";

    public static final String MUSIC_SEARCH = "com.harlie.leehounshell.musicsearch.extra.MUSIC_SEARCH";
    public static final String MUSIC_SEARCH_RESULTS = "com.harlie.leehounshell.musicsearch.extra.MUSIC_SEARCH_RESULTS";
    public static final String RECEIVER = "receiver";

    public final static int STATUS_MUSIC_SEARCH_RESULTS = 1;
    public final static int STATUS_ERROR = 2;

    public MusicSearchIntentService() {
        super("MusicSearchIntentService");
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
        LogHelper.v(TAG, "handleActionFindMusic");
        Bundle bundle = new Bundle();
        try {
            bundle.putString(MusicSearchIntentService.MUSIC_SEARCH, musicSearchText);

            // FIXME: replace hard test data with network results and remove "music_test_data.json" from assets
            InputStream in = getAssets().open("music_test_data.json");

            String dummyResults = FileUtil.convertStreamToString(in);
            bundle.putString(MusicSearchIntentService.MUSIC_SEARCH_RESULTS, dummyResults); // FIXME: edge case if the results are too big to process in Bundle
            //LogHelper.v(TAG, "---> handleActionFindMusic SEND THE RESULTS: " + dummyResults);
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

}

