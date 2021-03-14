package com.example.android.steamsearchapp.data.details;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SteamAppDetailsScreenshot implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("path_thumbnail")
    private String thumbnailUrl;

    @SerializedName("path_full")
    private String screenshotUrl;
}
