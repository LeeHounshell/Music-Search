package com.harlie.leehounshell.musicsearch.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.harlie.leehounshell.musicsearch.util.LogHelper;

import java.util.List;

// This is a List of MusicModel POJO containing all JSON returned by requests made to https://itunes.apple.com/search?term=<search-term>
public class MusicModelList implements Parcelable {
    private final static String TAG = "LEE: <" + MusicModelList.class.getSimpleName() + ">";

    private int resultsCount;
    private List<MusicModel> results;

    public int getResultsCount() {
        return resultsCount;
    }

    public void setResultsCount(int resultsCount) {
        this.resultsCount = resultsCount;
    }

    public List<MusicModel> getResults() {
        LogHelper.v(TAG, "getResults");
        return results;
    }

    public void setResults(List<MusicModel> results) {
        LogHelper.v(TAG, "setResults");
        this.results = results;
    }

    @Override
    public String toString() {
        return "MusicModelList [results=" + results + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.resultsCount);
        dest.writeTypedList(this.results);
    }

    public MusicModelList() {
    }

    protected MusicModelList(Parcel in) {
        this.resultsCount = in.readInt();
        this.results = in.createTypedArrayList(MusicModel.CREATOR);
    }

    public static final Parcelable.Creator<MusicModelList> CREATOR = new Parcelable.Creator<MusicModelList>() {
        @Override
        public MusicModelList createFromParcel(Parcel source) {
            return new MusicModelList(source);
        }

        @Override
        public MusicModelList[] newArray(int size) {
            return new MusicModelList[size];
        }
    };
}

