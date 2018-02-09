package com.harlie.leehounshell.musicsearch.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.harlie.leehounshell.musicsearch.util.LogHelper;

import java.util.Date;

// This is a POJO for a single JSON array element returned by requests made to https://itunes.apple.com/search?term=<search-term>
@SuppressWarnings("WeakerAccess")
public class MusicModel implements Parcelable {
    private final static String TAG = "LEE: <" + MusicModel.class.getSimpleName() + ">";

    private String title;
    private String kind;
    private long artistId;
    private long collectionId;
    private long trackId;
    private String artistName;
    private String collectionName;
    private String trackName;
    private String trackCensoredName;
    private String trackViewUrl;
    private String previewUrl;
    private String artworkUrl30;
    private String artworkUrl60;
    private String artworkUrl100;
    private String collectionPrice;
    private String trackPrice;
    private Date releaseDate;
    private String collectionExplicitness;
    private String trackExplicitness;
    private int discCount;
    private int discNumber;
    private int trackCount;
    private int trackNumber;
    private int trackTimeMillis;
    private String country;
    private String currency;
    private String primaryGenreName;
    private boolean isStreamable;

    public MusicModel() {
        //LogHelper.v(TAG, "MusicModel");
    }

    @SuppressWarnings("WeakerAccess")
    public MusicModel(MusicModel musicModel) {
        LogHelper.v(TAG, "MusicModel");
        this.title = musicModel.getTitle();
        this.kind = musicModel.getKind();
        this.artistId = musicModel.getArtistId();
        this.collectionId = musicModel.getCollectionId();
        this.trackId = musicModel.getTrackId();
        this.artistName = musicModel.getArtistName();
        this.collectionName = musicModel.getCollectionName();
        this.trackName = musicModel.getTrackName();
        this.trackCensoredName = musicModel.getTrackCensoredName();
        this.trackViewUrl = musicModel.getTrackViewUrl();
        this.previewUrl = musicModel.getPreviewUrl();
        this.artworkUrl30 = musicModel.getArtworkUrl30();
        this.artworkUrl60 = musicModel.getArtworkUrl60();
        this.artworkUrl100 = musicModel.getArtworkUrl100();
        this.collectionPrice = musicModel.getCollectionPrice();
        this.trackPrice = musicModel.getTrackPrice();
        this.releaseDate = musicModel.getReleaseDate();
        this.collectionExplicitness = musicModel.getCollectionExplicitness();
        this.trackExplicitness = musicModel.getTrackExplicitness();
        this.discCount = musicModel.getDiscCount();
        this.discNumber = musicModel.getDiscNumber();
        this.trackCount = musicModel.getTrackCount();
        this.trackNumber = musicModel.getTrackNumber();
        this.trackTimeMillis = musicModel.getTrackTimeMillis();
        this.country = musicModel.getCountry();
        this.currency = musicModel.getCurrency();
        this.primaryGenreName = musicModel.getPrimaryGenreName();
        this.isStreamable = musicModel.isStreamable();
    }

    @SuppressWarnings("WeakerAccess")
    public MusicModel(String title,
                      String kind,
                      long artistId,
                      long collectionId,
                      long trackId,
                      String artistName,
                      String collectionName,
                      String trackName,
                      String trackCensoredName,
                      String trackViewUrl,
                      String previewUrl,
                      String artworkUrl30,
                      String artworkUrl60,
                      String artworkUrl100,
                      String collectionPrice,
                      String trackPrice,
                      Date releaseDate,
                      String collectionExplicitness,
                      String trackExplicitness,
                      int discCount,
                      int discNumber,
                      int trackCount,
                      int trackNumber,
                      int trackTimeMillis,
                      String country,
                      String currency,
                      String primaryGenreName,
                      boolean isStreamable)
    {
        this.title = title;
        this.kind = kind;
        this.artistId = artistId;
        this.collectionId = collectionId;
        this.trackId = trackId;
        this.artistName = artistName;
        this.collectionName = collectionName;
        this.trackName = trackName;
        this.trackCensoredName = trackCensoredName;
        this.trackViewUrl = trackViewUrl;
        this.previewUrl = previewUrl;
        this.artworkUrl30 = artworkUrl30;
        this.artworkUrl60 = artworkUrl60;
        this.artworkUrl100 = artworkUrl100;
        this.collectionPrice = collectionPrice;
        this.trackPrice = trackPrice;
        this.releaseDate = releaseDate;
        this.collectionExplicitness = collectionExplicitness;
        this.trackExplicitness = trackExplicitness;
        this.discCount = discCount;
        this.discNumber = discNumber;
        this.trackCount = trackCount;
        this.trackNumber = trackNumber;
        this.trackTimeMillis = trackTimeMillis;
        this.country = country;
        this.currency = currency;
        this.primaryGenreName = primaryGenreName;
        this.isStreamable = isStreamable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(long collectionId) {
        this.collectionId = collectionId;
    }

    public long getTrackId() {
        return trackId;
    }

    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getTrackCensoredName() {
        return trackCensoredName;
    }

    public void setTrackCensoredName(String trackCensoredName) {
        this.trackCensoredName = trackCensoredName;
    }

    public String getTrackViewUrl() {
        return trackViewUrl;
    }

    public void setTrackViewUrl(String trackViewUrl) {
        this.trackViewUrl = trackViewUrl;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getArtworkUrl30() {
        return artworkUrl30;
    }

    public void setArtworkUrl30(String artworkUrl30) {
        this.artworkUrl30 = artworkUrl30;
    }

    public String getArtworkUrl60() {
        return artworkUrl60;
    }

    public void setArtworkUrl60(String artworkUrl60) {
        this.artworkUrl60 = artworkUrl60;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public String getCollectionPrice() {
        return collectionPrice;
    }

    public void setCollectionPrice(String collectionPrice) {
        this.collectionPrice = collectionPrice;
    }

    public String getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(String trackPrice) {
        this.trackPrice = trackPrice;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCollectionExplicitness() {
        return collectionExplicitness;
    }

    public void setCollectionExplicitness(String collectionExplicitness) {
        this.collectionExplicitness = collectionExplicitness;
    }

    public String getTrackExplicitness() {
        return trackExplicitness;
    }

    public void setTrackExplicitness(String trackExplicitness) {
        this.trackExplicitness = trackExplicitness;
    }

    public int getDiscCount() {
        return discCount;
    }

    public void setDiscCount(int discCount) {
        this.discCount = discCount;
    }

    public int getDiscNumber() {
        return discNumber;
    }

    public void setDiscNumber(int discNumber) {
        this.discNumber = discNumber;
    }

    public int getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public int getTrackTimeMillis() {
        return trackTimeMillis;
    }

    public void setTrackTimeMillis(int trackTimeMillis) {
        this.trackTimeMillis = trackTimeMillis;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public void setPrimaryGenreName(String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
    }

    public boolean isStreamable() {
        return isStreamable;
    }

    public void setStreamable(boolean streamable) {
        isStreamable = streamable;
    }

    @Override
    public String toString() {
        return "MusicModel{" +
                "title='" + title + '\'' +
                ", kind='" + kind + '\'' +
                ", artistId=" + artistId +
                ", collectionId=" + collectionId +
                ", trackId=" + trackId +
                ", artistName='" + artistName + '\'' +
                ", collectionName='" + collectionName + '\'' +
                ", trackName='" + trackName + '\'' +
                ", trackCensoredName='" + trackCensoredName + '\'' +
                ", trackViewUrl='" + trackViewUrl + '\'' +
                ", previewUrl='" + previewUrl + '\'' +
                ", artworkUrl30='" + artworkUrl30 + '\'' +
                ", artworkUrl60='" + artworkUrl60 + '\'' +
                ", artworkUrl100='" + artworkUrl100 + '\'' +
                ", collectionPrice='" + collectionPrice + '\'' +
                ", trackPrice='" + trackPrice + '\'' +
                ", releaseDate=" + releaseDate +
                ", collectionExplicitness='" + collectionExplicitness + '\'' +
                ", trackExplicitness='" + trackExplicitness + '\'' +
                ", discCount=" + discCount +
                ", discNumber=" + discNumber +
                ", trackCount=" + trackCount +
                ", trackNumber=" + trackNumber +
                ", trackTimeMillis=" + trackTimeMillis +
                ", country='" + country + '\'' +
                ", currency='" + currency + '\'' +
                ", primaryGenreName='" + primaryGenreName + '\'' +
                ", isStreamable=" + isStreamable +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.kind);
        dest.writeLong(this.artistId);
        dest.writeLong(this.collectionId);
        dest.writeLong(this.trackId);
        dest.writeString(this.artistName);
        dest.writeString(this.collectionName);
        dest.writeString(this.trackName);
        dest.writeString(this.trackCensoredName);
        dest.writeString(this.trackViewUrl);
        dest.writeString(this.previewUrl);
        dest.writeString(this.artworkUrl30);
        dest.writeString(this.artworkUrl60);
        dest.writeString(this.artworkUrl100);
        dest.writeString(this.collectionPrice);
        dest.writeString(this.trackPrice);
        dest.writeLong(this.releaseDate != null ? this.releaseDate.getTime() : -1);
        dest.writeString(this.collectionExplicitness);
        dest.writeString(this.trackExplicitness);
        dest.writeInt(this.discCount);
        dest.writeInt(this.discNumber);
        dest.writeInt(this.trackCount);
        dest.writeInt(this.trackNumber);
        dest.writeInt(this.trackTimeMillis);
        dest.writeString(this.country);
        dest.writeString(this.currency);
        dest.writeString(this.primaryGenreName);
        dest.writeByte(this.isStreamable ? (byte) 1 : (byte) 0);
    }

    public MusicModel(Parcel in) {
        this.title = in.readString();
        this.kind = in.readString();
        this.artistId = in.readLong();
        this.collectionId = in.readLong();
        this.trackId = in.readLong();
        this.artistName = in.readString();
        this.collectionName = in.readString();
        this.trackName = in.readString();
        this.trackCensoredName = in.readString();
        this.trackViewUrl = in.readString();
        this.previewUrl = in.readString();
        this.artworkUrl30 = in.readString();
        this.artworkUrl60 = in.readString();
        this.artworkUrl100 = in.readString();
        this.collectionPrice = in.readString();
        this.trackPrice = in.readString();
        long tmpReleaseDate = in.readLong();
        this.releaseDate = tmpReleaseDate == -1 ? null : new Date(tmpReleaseDate);
        this.collectionExplicitness = in.readString();
        this.trackExplicitness = in.readString();
        this.discCount = in.readInt();
        this.discNumber = in.readInt();
        this.trackCount = in.readInt();
        this.trackNumber = in.readInt();
        this.trackTimeMillis = in.readInt();
        this.country = in.readString();
        this.currency = in.readString();
        this.primaryGenreName = in.readString();
        this.isStreamable = in.readByte() != 0;
    }

    public static final Parcelable.Creator<MusicModel> CREATOR = new Parcelable.Creator<MusicModel>() {
        @Override
        public MusicModel createFromParcel(Parcel source) {
            return new MusicModel(source);
        }

        @Override
        public MusicModel[] newArray(int size) {
            return new MusicModel[size];
        }
    };
}
