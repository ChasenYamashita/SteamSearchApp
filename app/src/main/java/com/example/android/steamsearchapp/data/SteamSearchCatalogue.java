package com.example.android.steamsearchapp.data;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.steamsearchapp.utils.GitHubUtils;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SteamSearchCatalogue {
    private static final String TAG = SteamSearchCatalogue.class.getSimpleName();
    private static final String BASE_URL = "https://api.steampowered.com";

    private MutableLiveData<List<SteamApp>> searchResults;
    private MutableLiveData<SteamAppDataContainer> appDetails;
    private MutableLiveData<LoadingStatus> loadingStatus;

    private String currentQuery;
    private String currentAppid;

    private SteamService steamService;

    public SteamSearchCatalogue() {
        this.searchResults = new MutableLiveData<>();
        this.searchResults.setValue(null);

        this.loadingStatus = new MutableLiveData<>();
        this.loadingStatus.setValue(LoadingStatus.SUCCESS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.steamService = retrofit.create(SteamService.class);
    }

    public LiveData<List<SteamApp>> getSearchResults() {
        return this.searchResults;
    }

    public LiveData<LoadingStatus> getLoadingStatus() {
        return this.loadingStatus;
    }

    public LiveData<SteamAppDataContainer> getSingleAppDetails(){ return this.appDetails; }

    private boolean shouldExecuteSearch(String query) {
        return !TextUtils.equals(query, this.currentQuery)
                || this.getLoadingStatus().getValue() == LoadingStatus.ERROR;
    }

    private boolean shouldLoadDetails(String appid){
        return !TextUtils.equals(appid, this.currentAppid)
                || this.getLoadingStatus().getValue() == LoadingStatus.ERROR;
    }

    public void loadAppDetails(String appid){
        if (this.shouldLoadDetails(appid)) {
            Log.d(TAG, "running new search for this appid: " + appid);
            this.currentAppid = appid;
            this.executeSearch(appid, "json");
        } else {
            Log.d(TAG, "using cached search results for this appid: " + appid);
        }
    }

    public void executeProductDetails(String appid){
        Call<SteamAppDetails> results;

        results = this.steamService.getSteamProductDetail(appid);

        this.appDetails.setValue(null);
        this.loadingStatus.setValue(LoadingStatus.LOADING);
        results.enqueue(new Callback<SteamAppDetails>() {
            @Override
            public void onResponse(Call<SteamAppDetails> call, Response<SteamAppDetails> response) {
                if (response.code() == 200) {
                    appDetails.setValue(response.body().data);
                    loadingStatus.setValue(LoadingStatus.SUCCESS);
                } else {
                    loadingStatus.setValue(LoadingStatus.ERROR);
                }
            }

            @Override
            public void onFailure(Call<SteamAppDetails> call, Throwable t) {
                t.printStackTrace();
                loadingStatus.setValue(LoadingStatus.ERROR);
            }
        });
    }

    public void loadSearchResults(String query) {
        if (this.shouldExecuteSearch(query)) {
            Log.d(TAG, "running new search for this query: " + query);
            this.currentQuery = query;
            this.executeSearch(query, "json");
        } else {
            Log.d(TAG, "using cached search results for this query: " + query);
        }
    }

    private void executeSearch(String queryTerm, @Nullable String format) {
        Call<SteamSearchResults> results;

        results = this.steamService.getSteamProductCatalogue("STEAMKEY", format);

        this.searchResults.setValue(null);
        this.loadingStatus.setValue(LoadingStatus.LOADING);
        results.enqueue(new Callback<SteamSearchResults>() {
            @Override
            public void onResponse(Call<SteamSearchResults> call, Response<SteamSearchResults> response) {
                if (response.code() == 200) {
                    searchResults.setValue(response.body().products);
                    loadingStatus.setValue(LoadingStatus.SUCCESS);
                } else {
                    loadingStatus.setValue(LoadingStatus.ERROR);
                }
            }

            @Override
            public void onFailure(Call<SteamSearchResults> call, Throwable t) {
                t.printStackTrace();
                loadingStatus.setValue(LoadingStatus.ERROR);
            }
        });
    }
}
