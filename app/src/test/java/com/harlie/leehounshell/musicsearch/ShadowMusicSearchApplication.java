package com.harlie.leehounshell.musicsearch;


import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowApplication;

@Implements(MusicSearchApplication.class)
public class ShadowMusicSearchApplication extends ShadowApplication
{
    private static ShadowMusicSearchApplication sInstance;

    public ShadowMusicSearchApplication() {
        sInstance = this;
    }

    @SuppressWarnings("unused")
    public static synchronized ShadowMusicSearchApplication getInstance() {
        return ShadowMusicSearchApplication.sInstance;
    }

}
