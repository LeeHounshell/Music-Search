package com.harlie.leehounshell.musicsearch.util;

import android.databinding.BaseObservable;

import com.harlie.leehounshell.musicsearch.BR;

public class VisibleHelper extends BaseObservable {
    private final static String TAG = "LEE: <" + VisibleHelper.class.getSimpleName() + ">";

    private boolean visible = false;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        notifyPropertyChanged(BR.progressVisible);
    }
}
