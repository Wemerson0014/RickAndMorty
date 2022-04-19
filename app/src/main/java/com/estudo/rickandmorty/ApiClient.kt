package com.estudo.rickandmorty

import retrofit2.Response

class ApiClient(private val rickAndMortyService: RickAndMortyServices) {

    suspend fun getCharacterById(characterId: Int): Response<GetCharacterByIdResponse> {
        return rickAndMortyService.getCharacterById(characterId)
    }
}