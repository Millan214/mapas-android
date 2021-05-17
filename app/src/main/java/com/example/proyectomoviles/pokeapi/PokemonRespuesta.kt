package com.example.proyectomoviles.pokeapi

class PokemonRespuesta {
    private var results: ArrayList<Pokemon> ?= null
    fun getResults(): ArrayList<Pokemon>{
        return results!!
    }
}