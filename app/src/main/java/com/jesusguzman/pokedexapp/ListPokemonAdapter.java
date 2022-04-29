package com.jesusguzman.pokedexapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListPokemonAdapter extends RecyclerView.Adapter<ListPokemonAdapter.ViewHolder> {

    private ArrayList<String> pokemonMoveList;
    private Retrofit retrofit;
    public ArrayList<Pokemon> dataset;
    private Context context;
    private static final String TAG = "TAG";
    private ArrayMap<Integer, ImageView> imageMap = new ArrayMap<>();

    public ListPokemonAdapter(Context context) {
        retrofit = new Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        this.context = context;
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);

        final TextView nameView = (TextView) view.findViewById(R.id.nameTextView);
        final ImageView imageView = (ImageView) view.findViewById(R.id.photoImageView);

        view.setOnClickListener(view1 -> {
            int imageId = 0;
            pokemonMoveList = new ArrayList<String>();
            for (Pokemon pokemon: dataset) {
                if (pokemon.getName() != null && pokemon.getName().contains(nameView.getText())) {
                    imageId = pokemon.getId();
                }
                ImageView pokeImage = imageMap.get(imageId);
                Intent pokeIntent = new Intent(context.getApplicationContext(), PokemonActivity.class);

                view.buildDrawingCache();
                Bitmap image = pokeImage.getDrawingCache();

                Bundle extras = new Bundle();
                Pokemon p = dataset.get(imageId - 1);
                String name = p.getName();
                String type = p.getType();
                Integer height = p.getHeight();
                Integer weight = p.getWeight();
                extras.putString("id", String.valueOf(imageId));
                extras.putParcelable("imagebitmap", image);
                extras.putString("name", name);
                extras.putString("type", type);
                extras.putString("height", "Alto: " + height * 10 + " cm");
                extras.putString("weight", "Peso: " + weight * 100 + " grs");
                extras.putStringArrayList("moves", pokemonMoveList);
                pokeIntent.putExtras(extras);

                context.startActivity(pokeIntent);


            }

        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = dataset.get(position);
        holder.nameTextView.setText(pokemon.getName());

        Log.d("Poke", pokemon.getId() + "");

        Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" +
                pokemon.getId() + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.photoImageView);

        imageMap.put(pokemon.getId(), holder.photoImageView);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void clearPokemonList() {
        dataset.clear();
    }

    public void searchPokemonList(ArrayList<Pokemon> pokemonArrayList) {
        dataset.addAll(pokemonArrayList);
        notifyDataSetChanged();
    }

    public void setFilter(ArrayList<Pokemon> newList) {
        dataset = new ArrayList<>();
        dataset.addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView photoImageView;
        private TextView nameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            photoImageView = (ImageView) itemView.findViewById(R.id.photoImageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);

        }
    }
}
