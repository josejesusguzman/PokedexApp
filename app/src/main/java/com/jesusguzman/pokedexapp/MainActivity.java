package com.jesusguzman.pokedexapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "POKEDEX";

    public Retrofit retrofit;

    private RecyclerView recyclerView;
    private ListPokemonAdapter listPokemonAdapter;

    private int offset;
    private boolean isScrolledCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listPokemonAdapter = new ListPokemonAdapter(this);
        recyclerView.setAdapter(listPokemonAdapter);
        recyclerView.setHasFixedSize(true);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0) {
                        int visibletemCount = gridLayoutManager.getChildCount();
                        int totalItemCount = gridLayoutManager.getItemCount();
                        int pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isScrolledCheck) {
                            if ((visibletemCount + pastVisibleItems) >= totalItemCount) {
                                isScrolledCheck = false;
                            }
                        }
                    }
            }
        });

        retrofit = new Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        offset = 0;
        isScrolledCheck = true;
        getData();

    }

    private void getData() {
        PokeApiService service = retrofit.create(PokeApiService.class);
        Call<PokemonRequest> pokemonRequestCall = service.getPokemonList(151);

        pokemonRequestCall.enqueue(new Callback<PokemonRequest>() {
            @Override
            public void onResponse(Call<PokemonRequest> call, Response<PokemonRequest> response) {
                isScrolledCheck = true;
                if (response.isSuccessful()) {
                    PokemonRequest pokemonRequest = response.body();
                    ArrayList<Pokemon> pokemonArrayList = pokemonRequest.getResults();
                    listPokemonAdapter.searchPokemonList(pokemonArrayList);
                    getStats();
                } else {
                    Log.e(TAG, " onResponseData: " + response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<PokemonRequest> call, Throwable t) {
                System.out.println("ASLKDJSAKLDJKLAS");
            }
        });
    }

    public void getStats() {
        PokeApiService service = retrofit.create(PokeApiService.class);
        ArrayList<Pokemon> pokemonArrayList = listPokemonAdapter.dataset;

        for (Pokemon pokemon: pokemonArrayList) {
            Call<PokemonRequest> pokemonRequestCall = service.getPokemonStats(pokemon.getId());

            pokemonRequestCall.enqueue(new Callback<PokemonRequest>() {
                @Override
                public void onResponse(Call<PokemonRequest> call, Response<PokemonRequest> response) {
                    if (response.isSuccessful()) {
                        PokemonRequest pokemonRequest = response.body();
                        Pokemon pokemonUpdate = listPokemonAdapter.dataset.get(pokemonRequest.getId()-1);

                        pokemonUpdate.setHeight(pokemonRequest.getHeight());
                        pokemonUpdate.setWeight(pokemonRequest.getWeight());
                        pokemonUpdate.setType(pokemonRequest.getTypes().get(0).getType().getName());

                        listPokemonAdapter.dataset.set(pokemonRequest.getId()-1, pokemonUpdate);
                    } else {
                        Log.e(TAG, " onResponseStats: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<PokemonRequest> call, Throwable t) {

                }
            });
        }
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}