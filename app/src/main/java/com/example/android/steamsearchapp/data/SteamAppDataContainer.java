package com.example.android.steamsearchapp.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SteamAppDataContainer implements Serializable {

    @SerializedName("name")
    String name;

    @SerializedName("type")
    String type;

    @SerializedName("steam_appid")
    Integer appid;

    @SerializedName("is_free")
    Boolean is_free;

    @SerializedName("detailed_description")
    String longDescription;

    @SerializedName("short_description")
    String shortDescription;

    @SerializedName("about_the_game")
    String aboutTheGame;

    @SerializedName("supported_languages")
    String languages;

    @SerializedName("header_image")
    String headerImageUrl;

    @SerializedName("website")
    String websiteUrl;

    //developers array
    //publishers array


}
