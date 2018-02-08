package com.harlie.leehounshell.musicsearch.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.harlie.leehounshell.musicsearch.R;
import com.harlie.leehounshell.musicsearch.util.CustomToast;
import com.harlie.leehounshell.musicsearch.util.LogHelper;
import com.harlie.leehounshell.musicsearch.util.MusicSearchResults;
import com.harlie.leehounshell.musicsearch.view_model.MusicSearch_ViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity {
    private final static String TAG = "LEE: <" + MainActivity.class.getSimpleName() + ">";

    private Bundle savedInstanceState;
    private MusicSearch_ViewModel musicSearch_viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogHelper.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_main);
        musicSearch_viewModel = ViewModelProviders.of(this).get(MusicSearch_ViewModel.class);
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
                musicSearch_viewModel.searchForMusic(searchString);
            }
            return false;
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MusicSearchResults.MusicSearchResultsEvent event) {
        LogHelper.v(TAG, "onMessageEvent");
        goToBrowseMusicSearchResultsActivity(event.getSearchResults());
    }

    @Override
    protected void onDestroy() {
        LogHelper.v(TAG, "onDestroy");
        savedInstanceState = null;
        super.onDestroy();
    }
}
