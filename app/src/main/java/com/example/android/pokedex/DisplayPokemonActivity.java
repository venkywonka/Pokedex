package com.example.android.pokedex;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DisplayPokemonActivity extends AppCompatActivity implements LoaderCallbacks<Pokemon> {
    String pokeUrl;
    ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pokemon);
        Intent intent = getIntent();
        pokeUrl = intent.getStringExtra("blah");
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected==false){
            Toast.makeText(this,"check internet connection",Toast.LENGTH_SHORT);
            returnIntent();
        }
        else{
            loading = (ProgressBar) findViewById(R.id.loading_spinner);
            Log.i("OnCreate() method","the getLoaderManager().initLoader(0,null,this) is gonna be executed bitch!");
            getLoaderManager().initLoader(0,null,this);}


    }

    @Override
    public Loader<Pokemon> onCreateLoader(int id, Bundle args) {
        Log.i("onCreateLoader() method","the onCreateLoader is executed bitch!");
        return new PokeLoader(DisplayPokemonActivity.this,pokeUrl);
    }

    @Override
    public void onLoadFinished(Loader<Pokemon> loader, Pokemon pokemon) {



        if(pokemon==null){
            Log.i("onLoadFinished() method","no pokemon data");
            Toast.makeText(this,"No such pokemon exists",Toast.LENGTH_SHORT).show();
            returnIntent();
        }
        else{
        Log.i("onLoadFinished() method","the pokemon is populated and is ready to be sent to setUI() method ");
        setUI(pokemon);}
    }

    @Override
    public void onLoaderReset(Loader<Pokemon> loader) {
        Log.i("onLoaderReset() method","the loader is reset ");


    }


    void setUI(Pokemon pokemon){
        Log.i("setUI()","setUI method is called");
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(pokemon.getmName().toString());

        TextView height = (TextView) findViewById(R.id.height);
        height.setText(pokemon.getmHeight().toString());

        TextView weight = (TextView) findViewById(R.id.weight);
        weight.setText(pokemon.getmWeight().toString());


        ImageView image = (ImageView) findViewById(R.id.image);
        Picasso.with(this).load(pokemon.getmSprite()).placeholder(R.drawable.loading).into(image);
        if(image!=null)loading.setVisibility(View.INVISIBLE);

    }


    void returnIntent(){
        Log.i("returnIntent()","return to main activity");
        Intent returnIntent = new Intent(DisplayPokemonActivity.this, MainActivity.class);
        // Start the new activity
        startActivity(returnIntent);
    }
}
