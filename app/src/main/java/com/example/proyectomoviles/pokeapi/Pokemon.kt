package com.example.proyectomoviles.pokeapi

class Pokemon(var name: String, var url: String){
    private var number: Int = 0

    fun getNumber():Int{
        val urlPartes = url.split("/")
        return urlPartes[urlPartes.size - 2].toInt()
    }

    fun setNumber(number:Int){
        this.number = number
    }
}