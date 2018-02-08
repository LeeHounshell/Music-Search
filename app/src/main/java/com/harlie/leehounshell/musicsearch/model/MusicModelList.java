package com.harlie.leehounshell.musicsearch.model;

import com.harlie.leehounshell.musicsearch.util.LogHelper;

import java.util.List;

// This is a List of MusicModel POJO containing all JSON returned by requests made to https://itunes.apple.com/search?term=<search-term>
public class MusicModelList {
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
}

