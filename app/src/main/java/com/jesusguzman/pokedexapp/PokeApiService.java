package com.jesusguzman.pokedexapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApiService {

    @GET("pokemon")
    Call<PokemonRequest> getPokemonList(@Query("limit") int limit);

    @GET("pokemon/{id}")
    Call<PokemonRequest> getPokemonStats(@Path("id") int id);

}
