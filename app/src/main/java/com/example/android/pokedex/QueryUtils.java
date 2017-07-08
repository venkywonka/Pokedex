package com.example.android.pokedex;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Admin on 08-07-2017.
 */

public class QueryUtils {

    private QueryUtils(){}

    public static Pokemon extractPokeData(String strUrl){
        Log.i("extractPokeData()","the pokemon is gonna be populated with data parsed from json objects that are retreived from the network url");

        Pokemon pokemon = null;
        String json_Response = "";

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.

        try {
            json_Response = makeHTTPRequest(strUrl);
            if(json_Response==null)return null;
        }catch (IOException e){
            Log.e("makeHTTPRequest()","error closing input stream",e);
            return null;
        }
        try {

            // Create a JSONObject from the SAMPLE_JSON_RESPONSE string
            JSONObject baseJsonResponse = new JSONObject(json_Response);
            String name = baseJsonResponse.getString("name");
            String height = baseJsonResponse.getString("height");
            String weight =baseJsonResponse.getString("weight");
            JSONObject jsonSprites = baseJsonResponse.getJSONObject("sprites");
            String sprite = jsonSprites.getString("front_default");
            pokemon = new Pokemon(name,height,weight,sprite);


        } catch (JSONException e){
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            return null;
        }

        // Return the list of earthquakes
        return pokemon;


    }

    private static String makeHTTPRequest(String str_url) throws IOException{
        URL url = createUrl(str_url);
        String jsonResponse = "";

        if (url == null) {
            Log.e("QueryUtils","url is null hence json response is null");
            return jsonResponse;

        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("makeHTTPRequest()", "Problem is with the url connection. Error response code: " + urlConnection.getResponseCode());
                return null;
            }
        } catch (IOException e) {
            Log.e("makeHTTPRequest()", "Problem retrieving the earthquake JSON results. mostly its cuz the url is wrong", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        if(jsonResponse=="")Log.e("makeHTTPRequest()","json response is empty");
        return jsonResponse;

    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        Log.i("createUrl()","the url used: " + stringUrl);
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("createUrl()", "Error with creating URL ", e);
        }
        return url;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}


