package com.example.android.pokedex;

import android.util.Log;

/**
 * Created by Admin on 08-07-2017.
 */

public class Pokemon {
    private String mName;
    private String mHeight;
    private String mWeight;
    private String mSprite;
    public Pokemon(String name,String height,String weight,String sprite){
        Log.i("PokemonConstructor","the Pokemon is loaded ");
        mName= name;
        mHeight=height;
        mWeight=weight;
        mSprite=sprite;

}
    public String getmName(){
        return mName;
    }

    public String getmHeight(){
        return mHeight;
    }

    public String getmWeight(){
        return mWeight;
    }

    public String getmSprite(){
        return mSprite;
    }
}
