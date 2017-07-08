package com.example.android.pokedex;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

/**
 * Created by Admin on 08-07-2017.
 */

public class PokeLoader extends AsyncTaskLoader<Pokemon> {

    String strUrl;
    public  PokeLoader(Context context,String url){
        super(context);
        strUrl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Pokemon loadInBackground() {
        Log.i("loadInBackground()","loadInBackground is called!");
        Pokemon pokemon =  QueryUtils.extractPokeData(strUrl);
        return pokemon;
    }
}
