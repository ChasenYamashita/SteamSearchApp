package com.example.android.steamsearchapp.data.details;

import com.google.gson.annotations.SerializedName;

public class SteamAppDetailsVideos {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String videoName;

    @SerializedName("thumbnail")
    private String thumbnailUrl;

    @SerializedName("mp4")
    private SteamAppDetailsVideosMp4 steamAppDetailsVideosMp4;

    @SerializedName("highlight")
    private boolean highlight;
}
