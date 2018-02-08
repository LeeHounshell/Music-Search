package com.harlie.leehounshell.musicsearch.test;


import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.harlie.leehounshell.musicsearch.MusicSearchIntentService;
import com.harlie.leehounshell.musicsearch.model.MusicModel;
import com.harlie.leehounshell.musicsearch.model.MusicModelList;
import com.harlie.leehounshell.musicsearch.util.FileUtil;
import com.harlie.leehounshell.musicsearch.view.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ServiceController;
import org.robolectric.annotation.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.harlie.leehounshell.musicsearch.MusicSearchIntentService.ACTION_FIND_MUSIC;
import static com.harlie.leehounshell.musicsearch.MusicSearchIntentService.MUSIC_SEARCH;
import static com.harlie.leehounshell.musicsearch.MusicSearchIntentService.RECEIVER;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@Config(sdk = 25) //clean up Robolectric message: “WARNING: unknown service autofill”
@RunWith(RobolectricTestRunner.class)
public class MusicSearchIntentServiceTest {
    private final static String TAG = "LEE: <" + MusicSearchIntentServiceTest.class.getSimpleName() + ">";

    private MusicSearchIntentService service;
    private ServiceController<MusicSearchIntentService> controller;
    private MusicModel musicModel = null;

    @Before
    public void setUp() {
        controller = Robolectric.buildService(MusicSearchIntentService.class);
        service = controller.bind().create().get();
        musicModel = new MusicModel(); // TODO: add all constructor arguments
        musicModel.setArtistName("Tom Waits");
        musicModel.setTrackName("I Hope That I Don't Fall In Love With You");
    }

    @Test
    public void testWithIntent() {
        AppCompatActivity activity = Robolectric.setupActivity(MainActivity.class);
        Handler handler = new Handler();
        Intent intent = new Intent(RuntimeEnvironment.application, MusicSearchIntentService.class);
        intent.setAction(ACTION_FIND_MUSIC);
        intent.putExtra(RECEIVER, new ResultReceiver(handler));
        // add extras to intent
        intent.putExtra(MUSIC_SEARCH, musicModel.getArtistName());
        controller.withIntent(intent).startCommand(0, 0);
        // assert here
        try {
            Gson gson = new Gson();
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("music_test_data.json");
            assertThat(in, notNullValue());
            String jsonInfo = FileUtil.convertStreamToString(in);
            //System.out.println(TAG + "jsonInfo=" + jsonInfo);
            assertThat(jsonInfo, notNullValue());
            MusicModelList musicModelList = gson.fromJson(jsonInfo, MusicModelList.class);
            assertThat(musicModelList, notNullValue());
            MusicModel firstMusicModel = musicModelList.getResults().get(0);
            System.out.println("first firstMusicModel=" + firstMusicModel);
            assertThat(firstMusicModel, notNullValue());
            String artistName = musicModel.getArtistName();
            assertEquals(artistName, firstMusicModel.getArtistName());
            String trackName = musicModel.getTrackName();
            assertEquals(trackName, firstMusicModel.getTrackName());
        }
        catch (JsonParseException e) {
            System.err.println(TAG + "*** UNABLE TO PARSE JSON *** - e=" + e);
        }
    }

    @Test
    public void testOnHandleIntent() {
        AppCompatActivity activity = Robolectric.setupActivity(MainActivity.class);
        Handler handler = new Handler();
        MyTestIntentService intentService = new MyTestIntentService();
        Intent intent = new Intent(RuntimeEnvironment.application, MusicSearchIntentService.class);
        intent.setAction(ACTION_FIND_MUSIC);
        intent.putExtra(MUSIC_SEARCH, musicModel.getArtistName());
        intent.putExtra(RECEIVER, new ResultReceiver(handler));
        controller.withIntent(intent).startCommand(0, 0);
        // now we can call onHandleIntent in the Service
        intentService.onHandleIntent(intent);
    }

    @After
    public void tearDown() {
        controller.destroy();
    }

    public static class MyTestIntentService extends MusicSearchIntentService {

        @Override
        protected void onHandleIntent(Intent intent) {
            super.onHandleIntent(intent);
        }
    }

}

