package com.example.proyectomoviles.pokeapi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.proyectomoviles.R
import kotlinx.android.synthetic.main.item_pokemon.view.*

class PokemonAdapter(context: Context) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>(){

    var dataset: ArrayList<Pokemon>
    private val context: Context

    init {
        this.context = context
        dataset = ArrayList()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonAdapter.ViewHolder, position: Int) {
        val p: Pokemon? = dataset?.get(position)
        holder.tvnombre?.text = p?.name

        Glide.with(context)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${p?.getNumber()}.png")
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.ivfoto!!)

    }

    override fun getItemCount(): Int {
        return dataset?.count()!!
    }

    fun addListaPokemon(listaPokemon:ArrayList<Pokemon>){
        dataset?.addAll(listaPokemon)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        var vista = view
        var ivfoto: ImageView? = null
        var tvnombre: TextView? = null

        init {
            ivfoto = vista.IVPoke
            tvnombre = vista.TVPoke
        }

    }

}