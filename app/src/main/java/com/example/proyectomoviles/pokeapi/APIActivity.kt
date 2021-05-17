package com.example.proyectomoviles.pokeapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomoviles.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIActivity : AppCompatActivity() {

    private var retrofit:Retrofit ?= null
    private var TAG = "POKEDEX"

    private var recyclerView: RecyclerView ?= null
    private var pokemonAdapter: PokemonAdapter ?= null

    private var offset: Int = 0
    private var aptoParaCargar: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_p_i)

        recyclerView = findViewById(R.id.recyclerview)
        pokemonAdapter = PokemonAdapter(this)
        recyclerView?.adapter = pokemonAdapter
        val layoutManager = GridLayoutManager(this,3)
        recyclerView?.layoutManager = layoutManager

        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy>0){
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    if(aptoParaCargar){
                        if((visibleItemCount + pastVisibleItems) >= totalItemCount){
                            Log.i(TAG,"Llegamos al final")
                            aptoParaCargar = false
                            offset += 20
                            obtenerDatos(offset)
                        }
                    }

                }
            }
        })

        retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        aptoParaCargar = true
        offset = 0
        obtenerDatos(offset)

    }

    private fun obtenerDatos(offset: Int) {
        val service = retrofit?.create(PokeapiService::class.java)
        service?.obtenerListaPokemon(20,offset)?.enqueue(object : Callback<PokemonRespuesta>{
            override fun onResponse(
                call: Call<PokemonRespuesta>,
                response: Response<PokemonRespuesta>
            ) {
                aptoParaCargar = true
                if(response.isSuccessful){
                    val pokemonRespuesta = response.body()
                    val listaPokemon = pokemonRespuesta?.getResults()

                    pokemonAdapter?.addListaPokemon(listaPokemon!!)

                }else{
                    Log.e(TAG," onResponse: "+response.errorBody())
                }
            }

            override fun onFailure(call: Call<PokemonRespuesta>, t: Throwable) {
                aptoParaCargar = true
                Log.e(TAG," onFailure: "+t.message)
            }

        })
    }


}