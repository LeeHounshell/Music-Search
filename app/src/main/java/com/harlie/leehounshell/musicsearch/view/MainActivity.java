package com.harlie.leehounshell.musicsearch.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.harlie.leehounshell.musicsearch.R;
import com.harlie.leehounshell.musicsearch.databinding.ActivityMainBinding;
import com.harlie.leehounshell.musicsearch.interfaces.ISearchMusic;
import com.harlie.leehounshell.musicsearch.util.CustomToast;
import com.harlie.leehounshell.musicsearch.util.LogHelper;
import com.harlie.leehounshell.musicsearch.util.MusicSearchResults;
import com.harlie.leehounshell.musicsearch.view_model.MusicSearch_ViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity implements ISearchMusic {
    private final static String TAG = "LEE: <" + MainActivity.class.getSimpleName() + ">";

    private ActivityMainBinding mBinding;
    private Bundle savedInstanceState;
    private MusicSearch_ViewModel musicSearch_viewModel;

    private static boolean didWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogHelper.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        musicSearch_viewModel = ViewModelProviders.of(this).get(MusicSearch_ViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setSearchMusic((ISearchMusic) this);
    }

    @Override
    protected void onResume() {
        LogHelper.v(TAG, "onResume");
        super.onResume();
        if (savedInstanceState == null && ! didWelcome) {
            String welcome = getString(R.string.welcome);
            CustomToast.post(welcome);
            didWelcome = true;
        }
    }

    @Override
    public void searchForMusic(View view) {
        LogHelper.v(TAG, "-FOR THE READER-: i did not put API keys for Google Voice to Text translation here."
                + " That would force readers to setup google services first.."
                + " but, THIS IS A GOOD PLACE TO SEARCH USING MICROPHONE INPUT - left as an exercise for the reader");

        final AppCompatEditText musicSearchEditText = (AppCompatEditText) view;
        musicSearchEditText.setOnEditorActionListener((v, actionId, event) -> {
            LogHelper.v(TAG, "onEditorAction: actionId=" + actionId + ", event=" + event);
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                    || (actionId == EditorInfo.IME_ACTION_DONE)
                    || (actionId == EditorInfo.IME_ACTION_NEXT) )
            {
                hideSoftKeyboard();
                String searchString = musicSearchEditText.getText().toString();
                if (searchString.trim().length() == 0) { // don't allow a whitespace search
//                    String invalidSearchTerm = getString(R.string.invalid_search_term);
//                    CustomToast.post(invalidSearchTerm);
                }
                else {
                    LogHelper.v(TAG, "onEditorAction: searchString=" + searchString);
                    if (getProgressCircle() != null) {
                        getProgressCircle().setVisibility(View.VISIBLE);
                    }
                    musicSearch_viewModel.searchForMusic(searchString.trim());
                }
            }
            return false;
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MusicSearchResults.MusicSearchResultsEvent event) {
        LogHelper.v(TAG, "onMessageEvent");
        if (getProgressCircle() != null) {
            getProgressCircle().setVisibility(View.GONE);
        }
        goToBrowseMusicSearchResultsActivity(event.getSearchResults());
    }

    @Override
    protected void onDestroy() {
        LogHelper.v(TAG, "onDestroy");
        savedInstanceState = null;
        musicSearch_viewModel = null;
        super.onDestroy();
    }
}
