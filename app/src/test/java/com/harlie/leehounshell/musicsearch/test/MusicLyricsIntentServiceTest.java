package com.harlie.leehounshell.musicsearch.test;


import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;

import com.harlie.leehounshell.musicsearch.model.LyricsModel;
import com.harlie.leehounshell.musicsearch.model.MusicModel;
import com.harlie.leehounshell.musicsearch.service.MusicLyricsIntentService;
import com.harlie.leehounshell.musicsearch.util.FileUtil;
import com.harlie.leehounshell.musicsearch.view.BrowseMusicSearchResultsActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ServiceController;
import org.robolectric.annotation.Config;

import java.io.InputStream;

import static com.harlie.leehounshell.musicsearch.service.MusicLyricsIntentService.ACTION_FIND_LYRICS;
import static com.harlie.leehounshell.musicsearch.service.MusicLyricsIntentService.MUSIC_MODEL;
import static com.harlie.leehounshell.musicsearch.service.MusicLyricsIntentService.RECEIVER;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@Config(sdk = 25) //clean up Robolectric message: “WARNING: unknown service autofill”
@RunWith(RobolectricTestRunner.class)
public class MusicLyricsIntentServiceTest {
    private final static String TAG = "LEE: <" + MusicLyricsIntentServiceTest.class.getSimpleName() + ">";

    private ServiceController<MusicLyricsIntentService> controller;
    private MusicModel musicModel = null;
    private String lyricsUrl = null;

    @Before
    public void setUp() {
        controller = Robolectric.buildService(MusicLyricsIntentService.class);
        MusicLyricsIntentService service = controller.bind().create().get();
        musicModel = new MusicModel(); // TODO: add all constructor arguments
        musicModel.setArtistName("Tom Waits");
        musicModel.setTrackName("I Hope That I Don\\'t Fall In Love With You");
        musicModel.setCollectionName("Closing Time");
        musicModel.setArtworkUrl100("http://is5.mzstatic.com/image/thumb/Music/v4/f5/08/dd/f508ddf9-bd03-f1d5-6e57-41fc0680005a/source/100x100bb.jpg");
        lyricsUrl = "http://lyrics.wikia.com/Tom_Waits:I_Hope_That_I_Don%27t_Fall_In_Love_With_You";
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testWithIntent() {
        AppCompatActivity activity = Robolectric.setupActivity(BrowseMusicSearchResultsActivity.class);
        Handler handler = new Handler();
        Intent intent = new Intent(RuntimeEnvironment.application, MusicLyricsIntentService.class);
        intent.setAction(ACTION_FIND_LYRICS);
        intent.putExtra(RECEIVER, new ResultReceiver(handler));
        // add extras to intent
        intent.putExtra(MUSIC_MODEL, musicModel);
        controller.withIntent(intent).startCommand(0, 0);
        // assert here
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("lyrics_test_data.txt");
        assertThat(in, notNullValue());
        String jsonInfo = FileUtil.convertStreamToString(in);
        System.out.println(TAG + "jsonInfo=" + jsonInfo);
        assertThat(jsonInfo, notNullValue());

        // NOTE: the loaded jsonInfo is not actually valid JSON.
        // needed to create a custom parser to handle it properly.
        LyricsModel lyricsModel = new LyricsModel(jsonInfo);

        String artistName = lyricsModel.getArtistName();
        assertEquals(musicModel.getArtistName(), artistName);
        String songName = lyricsModel.getSongName();
        assertEquals(musicModel.getTrackName(), songName);
        String lyrics = lyricsModel.getLyrics();
        assertThat(lyrics, notNullValue());
        String url = lyricsModel.getUrl();
        assertEquals(lyricsUrl, url);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testOnHandleIntent() {
        AppCompatActivity activity = Robolectric.setupActivity(BrowseMusicSearchResultsActivity.class);
        Handler handler = new Handler();
        MyTestIntentService intentService = new MyTestIntentService();
        Intent intent = new Intent(RuntimeEnvironment.application, MusicLyricsIntentService.class);
        intent.setAction(ACTION_FIND_LYRICS);
        intent.putExtra(MUSIC_MODEL, musicModel);
        intent.putExtra(RECEIVER, new ResultReceiver(handler));
        controller.withIntent(intent).startCommand(0, 0);
        // now we can call onHandleIntent in the Service
        intentService.onHandleIntent(intent);
    }

    @After
    public void tearDown() {
        controller.destroy();
    }

    public static class MyTestIntentService extends MusicLyricsIntentService {

        @Override
        protected void onHandleIntent(Intent intent) {
            super.onHandleIntent(intent);
        }
    }

}

