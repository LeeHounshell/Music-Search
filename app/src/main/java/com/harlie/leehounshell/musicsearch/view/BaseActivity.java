package com.harlie.leehounshell.musicsearch.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.harlie.leehounshell.musicsearch.MusicSearchApplication;
import com.harlie.leehounshell.musicsearch.R;
import com.harlie.leehounshell.musicsearch.util.CustomToast;
import com.harlie.leehounshell.musicsearch.util.LogHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity
{
    private final static String TAG = "LEE: <" + BaseActivity.class.getSimpleName() + ">";

    public final static String KEY_SEARCH_RESULTS = "search_results";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LogHelper.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        hideSoftKeyboard();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onToastEvent(CustomToast.CustomToastEvent event) {
        LogHelper.v(TAG, "onToastEvent");
        if (event != null) {
            LogHelper.v(TAG, "onToastEvent: message=" + event.getToastMessage());
            String message = event.getToastMessage();
            @SuppressLint("ShowToast") Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            new CustomToast(toast).invoke();
        }
    }

    public static int orientation() {
        //LogHelper.v(TAG, "orientation");
        Context context = MusicSearchApplication.getAppContext();
        int orientation = context.getResources().getConfiguration().orientation;
        int sDeviceOrientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            sDeviceOrientation = Configuration.ORIENTATION_PORTRAIT;
        }
        else {
            sDeviceOrientation = Configuration.ORIENTATION_LANDSCAPE;
        }
        return sDeviceOrientation;
    }

    public int getWidth() {
        //LogHelper.v(TAG, "getWidth");
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public int getHeight() {
        //LogHelper.v(TAG, "getHeight");
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public void hideSoftKeyboard() {
        LogHelper.v(TAG, "hideSoftKeyboard");
        if (getWindow() != null) {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    protected void goToBrowseMusicSearchResultsActivity(String searchResults) {
        LogHelper.v(TAG, "goToBrowseMusicSearchResultsActivity");
        Intent browseIntent = new Intent(this, BrowseMusicSearchResultsActivity.class);
        browseIntent.putExtra(KEY_SEARCH_RESULTS, searchResults);
        startTheActivity(browseIntent);
    }

    private void startTheActivity(Intent intent) {
        LogHelper.v(TAG, "startTheActivity");
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
    }

    @Override
    protected void onDestroy() {
        LogHelper.v(TAG, "onDestroy");
        super.onDestroy();
    }

}

