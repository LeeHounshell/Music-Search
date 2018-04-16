package com.harlie.leehounshell.musicsearch.util;

public class LyricsFormatter {
    private final static String TAG = "LEE: <" + LyricsFormatter.class.getSimpleName() + ">";

    public String formatLyrics(String lyrics) {
        lyrics = lyrics
                .replaceAll("\\\\n", "\n")
                .replaceAll("\\\\'", "'");
        LogHelper.v(TAG, "formatLyrics: lyrics=" + lyrics);
        return lyrics;
    }
}
