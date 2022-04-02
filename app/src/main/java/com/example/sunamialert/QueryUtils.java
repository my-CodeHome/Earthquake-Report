package com.example.sunamialert;

import android.app.ActivityManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {


    private static final String LOG_TAG = "my Activity";

    private QueryUtils() {
    }
/// this method takes the string form of json response and return the list of eathquake objects
    private static ArrayList<Earthquake> extractEarthquakes(String SAMPLE_JSON_RESPONSE) {

        // Create an empty ArrayList that we can start adding earthquakes to
        if(SAMPLE_JSON_RESPONSE.isEmpty())
            return null;

        ArrayList<Earthquake> earthquakes = new ArrayList<>();
        try {
            // convert the json string into real json object
            JSONObject jsonObject = new JSONObject(SAMPLE_JSON_RESPONSE);

            // parse the jason object to add the values into the list
            JSONArray jsonArray = jsonObject.getJSONArray("features");
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject currJsonObject = jsonArray.getJSONObject(i);
                JSONObject properties = currJsonObject.getJSONObject("properties");
                 Double magnitude = properties.getDouble("mag");
                 String place = properties.getString("place");
                 long time = properties.getLong("time");
                 String url = properties.getString("url");
                Earthquake object = new Earthquake(magnitude,place,time,url);
                earthquakes.add(object);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return earthquakes;
    }
    public  static List<Earthquake> fetchEarthquakeData(String EarthquakeUrl)
    {
        // calling getUrl method to get actual url from the string url
        URL url = getUrl(EarthquakeUrl);

        String jsonResponse=null;
        try{
            // making a connection request using the url
            jsonResponse =makeHttpRequest(url);

        }catch (IOException e)
        {
            Log.e(LOG_TAG,"problem in making connection",e);
        }

        // calling extractEarthquakes method to get list of earthquakes from the given string form of json object
      List<Earthquake> earthquakes = extractEarthquakes(jsonResponse);
        return earthquakes;
    }

    /// this method takes the url and makes the http connection and returns string containing the json object
    private static String makeHttpRequest(URL url) throws IOException{
           String jsonResponse =null;
           if(url==null)
               return jsonResponse;
        HttpURLConnection urlConnection=null;
        InputStream inputStream =null;

        try{

            urlConnection= (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            if(urlConnection.getResponseCode()==200)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else
            {
                Log.e(LOG_TAG,"error response code"+urlConnection.getResponseCode());
            }
        }
        catch (IOException e){
            Log.e(LOG_TAG,"connection can't be made",e);
        }finally {
            if(urlConnection!=null)
                urlConnection.disconnect();

            if(inputStream!=null)
                inputStream.close();
        }
        return jsonResponse;
    }
// this method converts the input stream into corresponding string
    private static String readFromStream(InputStream inputStream) throws IOException {

           StringBuilder stringResponse = new StringBuilder();
        if(inputStream!=null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringResponse.append(line);
                line = reader.readLine();
            }
        }
        return stringResponse.toString();
    }

    /// this method converts string url into actual URL object
    private static URL getUrl(String earthquakeUrl) {
           URL url =null;

           try{
               url = new URL(earthquakeUrl);

           }catch (MalformedURLException e){
               Log.e(LOG_TAG, "Problem building the URL ", e);
           }
        return url;
    }

}
