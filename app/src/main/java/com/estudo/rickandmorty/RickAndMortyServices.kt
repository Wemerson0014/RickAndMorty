package com.estudo.rickandmorty

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyServices {

    @GET("character/{character-id}")
    suspend fun getCharacterById(
        @Path("character-id") characterId: Int
    )
            : Response<GetCharacterByIdResponse>
}