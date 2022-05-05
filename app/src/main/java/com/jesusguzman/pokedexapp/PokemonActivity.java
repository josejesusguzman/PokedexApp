package com.jesusguzman.pokedexapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class PokemonActivity extends AppCompatActivity {

    private static final String TAG = "TAGN";
    private TextView textView;
    private String genus;
    private int num;
    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        ImageView imageView = findViewById(R.id.imgView);

        Bundle extras = getIntent().getExtras();
        final String name = extras.getString("name");
        final String id = extras.getString("id");
        final String type = extras.getString("type");
        final String height = extras.getString("height");
        final String weight = extras.getString("weight");

        Glide.with(this).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" +
                id + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        TextView textViewName = (TextView) findViewById(R.id.nameTextView);
        textViewName.setText(name);

    }
}