package com.example.android.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText name;
    String pokeName="";
    String pokeUrl = "https://pokeapi.co/api/v2/pokemon/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    void openPokemon(View view){
        label:
        name = (EditText) findViewById(R.id.editText);
        pokeName = name.getText().toString();
        name.setText("");
        pokeName = pokeName.substring(0, 1).toLowerCase() + pokeName.substring(1);
        Log.i("GO button","the modified pokeName: "+pokeName);
        if(pokeName.isEmpty()){
            Toast.makeText(this,"ENTER SOMETHING!",Toast.LENGTH_SHORT).show();

        }
        else{pokeUrl = pokeUrl + pokeName;
        Log.i("openPokemon","the url has been formed: " + pokeUrl);
        Intent pokeIntent = new Intent(MainActivity.this, DisplayPokemonActivity.class);
        pokeIntent.putExtra("blah",pokeUrl);
        // Start the new activity
        startActivity(pokeIntent);}
    }
}
