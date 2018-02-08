package com.harlie.leehounshell.musicsearch.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.harlie.leehounshell.musicsearch.util.CustomToast;
import com.harlie.leehounshell.musicsearch.util.LogHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity
{
    private final static String TAG = "LEE: <" + BaseActivity.class.getSimpleName() + ">";

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

    @Override
    protected void onDestroy() {
        LogHelper.v(TAG, "onDestroy");
        super.onDestroy();
    }

}

