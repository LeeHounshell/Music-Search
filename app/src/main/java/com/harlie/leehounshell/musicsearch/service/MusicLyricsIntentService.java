package com.harlie.leehounshell.musicsearch.service;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.harlie.leehounshell.musicsearch.model.MusicModel;
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
public class MusicLyricsIntentService extends IntentService {
    private final static String TAG = "LEE: <" + MusicLyricsIntentService.class.getSimpleName() + ">";

    private static final String LYRICS_ENDPOINT = "http://lyrics.wikia.com/api.php?func=getSong&fmt=json";

    // IntentService can perform, e.g. ACTION_FIND_LYRICS
    private static final String ACTION_FIND_LYRICS = "com.harlie.leehounshell.musicsearch.action.ACTION_FIND_LYRICS";

    public static final String MUSIC_MODEL = "com.harlie.leehounshell.musicsearch.extra.MUSIC_MODEL";
    public static final String LYRICS_SEARCH_RESULTS = "com.harlie.leehounshell.musicsearch.extra.LYRICS_SEARCH_RESULTS";
    private static final String RECEIVER = "receiver";

    public final static int STATUS_LYRICS_SEARCH_RESULTS = 1;
    public final static int STATUS_ERROR = 2;

    private OkHttpClient okHttpClient;
    private MusicModel musicModel;

    public MusicLyricsIntentService() {
        super("MusicLyricsIntentService");
        if (okHttpClient == null) {
            // avoid creating several instances, should be singleton
            okHttpClient = new OkHttpClient();
        }
    }

    /**
     * Starts this service to perform action FindLyrics with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionFindLyrics(Context context, MusicModel musicModel, ResultReceiver lyricsResultReceiver) {
        LogHelper.v(TAG, "startActionFindLyrics");
        Intent intent = new Intent(context, MusicLyricsIntentService.class);
        intent.setAction(ACTION_FIND_LYRICS);
        intent.putExtra(MUSIC_MODEL, musicModel);
        intent.putExtra(RECEIVER, lyricsResultReceiver);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogHelper.v(TAG, "onHandleIntent");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FIND_LYRICS.equals(action)) {
                musicModel = intent.getParcelableExtra(MUSIC_MODEL);
                final ResultReceiver lyricsResultReceiver = intent.getParcelableExtra(RECEIVER);
                handleActionFindLyrics(musicModel, lyricsResultReceiver);
            }
        }
    }

    /**
     * Handle action ACTION_FIND_LYRICS in the provided background thread with the provided parameters.
     */
    private void handleActionFindLyrics(MusicModel musicModel, ResultReceiver receiver) {
        LogHelper.v(TAG, "handleActionFindLyrics: musicModel=" + musicModel);
        Bundle bundle = new Bundle();
        try {
            Request request = new Request.Builder()
                    .url(LYRICS_ENDPOINT
                            + "&artist=" + musicModel.getArtistName().replaceAll(" ", "+")
                            + "&song=" + musicModel.getTrackName().replaceAll(" ", "+"))
                    .build();

            LogHelper.v(TAG, "sending network request..");
            Response response = okHttpClient.newCall(request).execute(); // thread is already in the background, so synchronous call is ok here
            String lyricsResults;
            if (response != null && response.body() != null) {
                //noinspection ConstantConditions
                lyricsResults = response.body().string();
            }
            else {
                LogHelper.e(TAG, "*** network request lyricsResults are null! ***");
                lyricsResults = "";
            }

            bundle.putParcelable(MusicLyricsIntentService.MUSIC_MODEL, musicModel);
            bundle.putString(MusicLyricsIntentService.LYRICS_SEARCH_RESULTS, lyricsResults); // FIXME: edge case if the lyricsResults are too big to process in a passed Bundle
            LogHelper.v(TAG, "---> handleActionFindLyrics SEND THE LYRICS RESULTS: " + lyricsResults);
            receiver.send(STATUS_LYRICS_SEARCH_RESULTS, bundle);
        }
        catch (NullPointerException e) {
            // JUNIT test
            System.err.println("WARNING: NullPointerException in handleActionFindLyrics - JUNIT TEST?");
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
        musicModel = null;
    }
}

