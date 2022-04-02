package com.example.sunamialert;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private String urls;
    public EarthquakeLoader(@NonNull Context context,String urls) {
        super(context);
         this.urls =urls;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Earthquake> loadInBackground() {
        if ( urls==null) {
            return null;
        }
        List<Earthquake> result = QueryUtils.fetchEarthquakeData(urls);
        return result;
    }
}
