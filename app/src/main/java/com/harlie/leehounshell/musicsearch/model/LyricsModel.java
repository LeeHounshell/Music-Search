package com.harlie.leehounshell.musicsearch.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.harlie.leehounshell.musicsearch.util.LogHelper;
import com.harlie.leehounshell.musicsearch.util.LyricsCrazyJsonParser;

@SuppressWarnings("WeakerAccess")
public class LyricsModel implements Parcelable {
    private final static String TAG = "LEE: <" + LyricsModel.class.getSimpleName() + ">";

    private String artistName;
    private String songName;
    private String lyrics;
    private String url;

    public LyricsModel() {
        //LogHelper.v(TAG, "LyricsModel");
    }

    @SuppressWarnings("WeakerAccess")
    public LyricsModel(LyricsModel lyricsModel) {
        //LogHelper.v(TAG, "LyricsModel");
        this.artistName = lyricsModel.getArtistName();
        this.songName = lyricsModel.getSongName();
        this.lyrics = lyricsModel.getLyrics();
        this.url = lyricsModel.getUrl();
    }

    @SuppressWarnings("WeakerAccess")
    public LyricsModel(String artistName, String songName, String lyrics, String url) {
        //LogHelper.v(TAG, "LyricsModel");
        this.artistName = artistName;
        this.songName = songName;
        this.lyrics = lyrics;
        this.url = url;
    }

    public LyricsModel(String jsonInfo) {
        LogHelper.v(TAG, "---> crazy LyricsModel: jsonInfo=" + jsonInfo);
        LyricsCrazyJsonParser lyricsCrazyJsonParser = new LyricsCrazyJsonParser(jsonInfo);
        setArtistName(lyricsCrazyJsonParser.getArtistName());
        setSongName(lyricsCrazyJsonParser.getSongName());
        setLyrics(lyricsCrazyJsonParser.getLyrics());
        setUrl(lyricsCrazyJsonParser.getUrl());
        LogHelper.v(TAG, "---> crazy parse results=" + this);
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "LyricsModel{" +
                "artistName='" + artistName + '\'' +
                ", songName='" + songName + '\'' +
                ", lyrics='" + lyrics + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.artistName);
        dest.writeString(this.songName);
        dest.writeString(this.lyrics);
        dest.writeString(this.url);
    }

    private LyricsModel(Parcel in) {
        this.artistName = in.readString();
        this.songName = in.readString();
        this.lyrics = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<LyricsModel> CREATOR = new Parcelable.Creator<LyricsModel>() {
        @Override
        public LyricsModel createFromParcel(Parcel source) {
            return new LyricsModel(source);
        }

        @Override
        public LyricsModel[] newArray(int size) {
            return new LyricsModel[size];
        }
    };
}
