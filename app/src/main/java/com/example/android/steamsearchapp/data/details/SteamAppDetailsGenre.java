package com.example.android.steamsearchapp.data.details;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SteamAppDetailsGenre implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("description")
    private String description;
}
