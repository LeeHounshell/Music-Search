package com.harlie.leehounshell.musicsearch.util;

@SuppressWarnings("SameReturnValue")
public class LyricsCrazyJsonParser {
    private final static String TAG = "LEE: <" + LyricsCrazyJsonParser.class.getSimpleName() + ">";

    private static String invalidJson; // remember the most recent value

    public LyricsCrazyJsonParser(String invalidJsonFromLyricsWikaCom) {
        LogHelper.v(TAG, "LyricsCrazyJsonParser");
        this.invalidJson = invalidJsonFromLyricsWikaCom;
    }

    public String getArtistName() {
        //return "Tom Waits";
        return getCrazyJsonString("'artist':'");
    }

    public String getSongName() {
        //return "I Hope That I Don\\'t Fall In Love With You";
        return getCrazyJsonString("'song':'");
    }

    public String getLyrics() {
        //return "Well, I hope that I don\\'t fall in love with you\\n\\'Cause falling in love just makes me blue\\nWell, the music plays and you display your heart for me to see\\nI had a beer and now I hear you ca[...]";
        return getCrazyJsonString("'lyrics':'");
    }

    public String getUrl() {
        //return "http://lyrics.wikia.com/api.php?func=getSongName&fmt=json&artist=Tom+Waits&song=i+hope+that+i+don%27t+fall+in+love+with+you";
        return getCrazyJsonString("'url':'");
    }

    private String getCrazyJsonString(String pattern) {
        LogHelper.v(TAG, "getCrazyJsonString: pattern=" + pattern);
        int dataBegin = invalidJson.indexOf(pattern) + pattern.length();
        String crazyThing = invalidJson.substring(dataBegin);
        int dataEnd = crazyThing.indexOf("',");
        if (dataEnd > 0) {
            crazyThing = crazyThing.substring(0, dataEnd);
        }
        else {
            dataEnd = crazyThing.indexOf("'");
            if (dataEnd > 0) {
                crazyThing = crazyThing.substring(0, dataEnd);
            }
        }
        LogHelper.v(TAG, "getCrazyJsonString: ---> crazyThing=" + crazyThing);
        return crazyThing;
    }

    @Override
    public String toString() {
        return "LyricsCrazyJsonParser{" +
                "invalidJson='" + invalidJson + '\'' +
                '}';
    }
}
