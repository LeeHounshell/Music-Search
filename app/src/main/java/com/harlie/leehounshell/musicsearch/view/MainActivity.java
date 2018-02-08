package com.harlie.leehounshell.musicsearch.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.google.gson.Gson;
import com.harlie.leehounshell.musicsearch.MusicSearchIntentService;
import com.harlie.leehounshell.musicsearch.R;
import com.harlie.leehounshell.musicsearch.model.MusicModel;
import com.harlie.leehounshell.musicsearch.model.MusicModelList;
import com.harlie.leehounshell.musicsearch.util.CustomToast;
import com.harlie.leehounshell.musicsearch.util.LogHelper;
import com.harlie.leehounshell.musicsearch.util.MusicSearchResults;
import com.harlie.leehounshell.musicsearch.util.MyResultReceiver;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity {
    private final static String TAG = "LEE: <" + MainActivity.class.getSimpleName() + ">";

    Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogHelper.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        LogHelper.v(TAG, "onResume");
        super.onResume();
        if (savedInstanceState == null) {
            String welcome = getString(R.string.welcome);
            CustomToast.post(welcome);
        }
    }

    public void enterSearchTerm(View view) {
        LogHelper.v(TAG, "enterSearchTerm: -click-");
        final AppCompatEditText musicSearchEditText = (AppCompatEditText) view;
        musicSearchEditText.setOnEditorActionListener((v, actionId, event) -> {
            LogHelper.v(TAG, "onEditorAction: actionId=" + actionId + ", event=" + event);
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                    || (actionId == EditorInfo.IME_ACTION_DONE)
                    || (actionId == EditorInfo.IME_ACTION_NEXT) )
            {
                hideSoftKeyboard();
                String searchString = musicSearchEditText.getText().toString();
                LogHelper.v(TAG, "onEditorAction: searchString=" + searchString);
                searchForMusic(searchString);
            }
            return false;
        });
    }

    private void searchForMusic(String searchString) {
        LogHelper.v(TAG, "searchForMusic: " + searchString);
        MyResultReceiver receiver = new MyResultReceiver(new Handler());
        receiver.setReceiver(new MusicSearchResults());
        MusicSearchIntentService.startActionFindMusic(this, searchString, receiver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MusicSearchResults.MusicSearchResultsEvent event) {
        LogHelper.v(TAG, "onMessageEvent");
        String searchResults = event.getSearchResults();
        //LogHelper.v(TAG, "===> SEARCH RESULTS: " + searchResults);
        goToBrowseMusicSearchResultsActivity(searchResults);

        Gson gson = new Gson();
        MusicModelList musicModelList = gson.fromJson(searchResults, MusicModelList.class);
        MusicModel firstMusicModel = musicModelList.getResults().get(0); // FIXME: put these results into a list
        LogHelper.v(TAG, "first firstMusicModel=" + firstMusicModel);
    }

    @Override
    protected void onDestroy() {
        LogHelper.v(TAG, "onDestroy");
        savedInstanceState = null;
        super.onDestroy();
    }
}
