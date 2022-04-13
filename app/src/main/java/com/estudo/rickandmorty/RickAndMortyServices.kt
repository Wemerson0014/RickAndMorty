package com.estudo.rickandmorty

import retrofit2.Call
import retrofit2.http.GET

interface RickAndMortyServices {

    @GET("character/2")
    fun getCharacterById(): Call<Any>
}