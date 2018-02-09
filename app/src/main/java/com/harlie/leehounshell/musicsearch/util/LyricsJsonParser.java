package com.harlie.leehounshell.musicsearch.util;

@SuppressWarnings("SameReturnValue")
public class LyricsJsonParser {
    private final static String TAG = "LEE: <" + LyricsJsonParser.class.getSimpleName() + ">";

    private final String invalidJson;

    public LyricsJsonParser(String invalidJsonFromLyricsWikaCom) {
        LogHelper.v(TAG, "LyricsJsonParser");
        this.invalidJson = invalidJsonFromLyricsWikaCom;
    }

    public String getArtistName() {
        return "Tom Waits"; // FIXME: use regex to parse the invalid JSON
    }

    public String getSongName() {
        return "I Hope That I Don't Fall In Love With You"; // FIXME: use regex to parse the invalid JSON
    }

    public String getLyrics() {
        return "Well, I hope that I don\\'t fall in love with you\\n\\'Cause falling in love just makes me blue\\nWell, the music plays and you display your heart for me to see\\nI had a beer and now I hear you ca[...]"; // FIXME: use regex to parse the invalid JSON
    }

    public String getUrl() {
        return "http://lyrics.wikia.com/api.php?func=getSongName&fmt=json&artist=Tom+Waits&song=i+hope+that+i+don%27t+fall+in+love+with+you"; // FIXME: use regex to parse the invalid JSON
    }

    @Override
    public String toString() {
        return "LyricsJsonParser{" +
                "invalidJson='" + invalidJson + '\'' +
                '}';
    }
}
