package com.harlie.leehounshell.musicsearch.util;

import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.harlie.leehounshell.musicsearch.R;

import org.greenrobot.eventbus.EventBus;

public class CustomToast {
    private final static String TAG = "LEE: <" + CustomToast.class.getSimpleName() + ">";

    private final Toast mToast;

    public static class CustomToastEvent {
        private final String toastMessage;

        CustomToastEvent(String toastMessage) {
            this.toastMessage = toastMessage;
        }

        public String getToastMessage() {
            return toastMessage;
        }

        @Override
        public String toString() {
            return "CustomToastEvent{" +
                    "toastMessage='" + toastMessage + '\'' +
                    '}';
        }
    }

    private CustomToast() {
        this.mToast = null;
    }

    public CustomToast(Toast toast) {
        this.mToast = toast;
    }

    public static void post(String toastMessage) {
        LogHelper.v(TAG, "post: toastMessage=" + toastMessage);
        CustomToastEvent customToastEvent = new CustomToastEvent(toastMessage);
        EventBus.getDefault().post(customToastEvent);
    }

    public void invoke() {
        LogHelper.v(TAG, "invoke");
        if (mToast != null) {
            View toastView = mToast.getView();
            if (toastView != null) {
                AppCompatTextView message = toastView.findViewById(android.R.id.message);
                message.setTypeface(null, Typeface.BOLD);
                float large_text = 24f;
                message.setTextSize(large_text);
                message.setPadding(0, 24, 0, 0);
                int transparent_pixel = R.drawable.toast_background;
                toastView.setBackgroundResource(transparent_pixel);
                mToast.setView(toastView);
                mToast.show();
            }
            else {
                LogHelper.w(TAG, "*** mToast.getView() is null! ***");
            }
        }
    }

}
