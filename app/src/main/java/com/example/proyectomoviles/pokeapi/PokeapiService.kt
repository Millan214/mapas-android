package com.example.proyectomoviles.pokeapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeapiService {

    @GET("pokemon")
    fun obtenerListaPokemon(@Query("limit") limit: Int, @Query("offset") offset: Int): Call<PokemonRespuesta>

}