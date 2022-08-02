package com.estudo.rickandmorty

import com.estudo.rickandmorty.domain.mappers.CharacterMapper
import com.estudo.rickandmorty.domain.models.Character
import com.estudo.rickandmorty.network.NetworkLayer
import com.estudo.rickandmorty.network.SimpleMortyCache
import com.estudo.rickandmorty.network.response.GetCharacterByIdResponse
import com.estudo.rickandmorty.network.response.GetEpisodeByIdResponse

class CharactersRepository {

    suspend fun getCharacterById(characterId: Int): Character? {
        val cachedCharacter = SimpleMortyCache.characterMap[characterId]
        if (cachedCharacter != null) {
            return cachedCharacter
        }

        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        val networkEpisodes = getEpisodesFromCharacterResponse(request.body)
        val character = CharacterMapper.buildFrom(
            response = request.body,
            episodes = networkEpisodes
        )

        SimpleMortyCache.characterMap[characterId] = character
        return character
    }

    private suspend fun getEpisodesFromCharacterResponse(characterResponse: GetCharacterByIdResponse): List<GetEpisodeByIdResponse> {
        val episodeRange = characterResponse.episode.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1)
        }.toString()

        val request = NetworkLayer.apiClient.getEpisodeRange(episodeRange)

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }

        return request.body
    }
}