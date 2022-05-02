package com.estudo.rickandmorty

import com.estudo.rickandmorty.network.NetworkLayer
import com.estudo.rickandmorty.network.response.GetCharactersPageResponse

class CharactersRepository {

    suspend fun getCharactersPage(pageIndex: Int): GetCharactersPageResponse? {
        val request = NetworkLayer.apiClient.getCharactersPage(pageIndex)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return request.body
    }
}