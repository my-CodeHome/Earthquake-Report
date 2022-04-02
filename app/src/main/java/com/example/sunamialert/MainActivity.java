package com.example.sunamialert;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=20";
    private CustomAdapter mCustomAdapter;
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private TextView mEmptyStateTextView;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.earthquake_listView);
         mEmptyStateTextView = findViewById(R.id.empty_view);
         mProgressBar = findViewById(R.id.loading_spinner);
          listView.setEmptyView(mEmptyStateTextView);

        ArrayList<Earthquake> earthquakes = new ArrayList<>();
        mCustomAdapter = new CustomAdapter(this,earthquakes);
        listView.setAdapter(mCustomAdapter);
        ConnectivityManager connMagr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMagr.getActiveNetworkInfo();

        if(networkInfo!=null&& networkInfo.isConnected())
      getSupportLoaderManager().initLoader(EARTHQUAKE_LOADER_ID,null,this);
        else
        {
            mProgressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setText("Check Your Internet Connectivity");
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Earthquake currEarthquake = (Earthquake) adapterView.getAdapter().getItem(i);

                Uri websiteUri = Uri.parse(currEarthquake.getUrl());

                Intent websiteIntent= new Intent(Intent.ACTION_VIEW,websiteUri);
                startActivity(websiteIntent);
            }
        });
    }

    @NonNull
    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, @Nullable Bundle args) {
        return new EarthquakeLoader(this,USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Earthquake>> loader, List<Earthquake> data) {
        mCustomAdapter.clear();
        mProgressBar.setVisibility(View.GONE);
        if(data!=null || !data.isEmpty())
            mCustomAdapter.addAll(data);

        mEmptyStateTextView.setText(R.string.No_Earthquake);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Earthquake>> loader) {
         mCustomAdapter.clear();
    }
}