package com.jesusguzman.pokedexapp;

import android.content.Context;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListPokemonAdapter extends RecyclerView.Adapter<ListPokemonAdapter.ViewHolder> {

    private ArrayList<String> pokemonMoveList;
    private Retrofit retrofit;
    private ArrayList<Pokemon> dataset;
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

        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = dataset.get(position);
        holder.nameTextView.setText(pokemon.getName());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void searchPokemonList(ArrayList<Pokemon> pokemonArrayList) {
        dataset.addAll(pokemonArrayList);
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
