package com.estudo.rickandmorty.episodes

import com.estudo.rickandmorty.domain.mappers.EpisodeMapper
import com.estudo.rickandmorty.domain.models.Episode
import com.estudo.rickandmorty.network.NetworkLayer
import com.estudo.rickandmorty.network.response.GetCharacterByIdResponse
import com.estudo.rickandmorty.network.response.GetEpisodeByIdResponse
import com.estudo.rickandmorty.network.response.GetEpisodesPageResponse

class EpisodeRepository {

    suspend fun fetchEpisodePage(pageIndex: Int): GetEpisodesPageResponse? {
        val pageRequest = NetworkLayer.apiClient.getEpisodesPage(pageIndex)

        if (!pageRequest.isSuccessful) {
            return null
        }

        return pageRequest.body
    }

    suspend fun getEpisodeById(episodeId: Int): Episode? {
        val request = NetworkLayer.apiClient.getEpisodeById(episodeId)

        if (!request.isSuccessful) {
            return null
        }

        val characterList = getCharacterFromEpisodeResponse(request.body)
        return EpisodeMapper.buildFrom(
            networkEpisode = request.body,
            networkCharacters = characterList
        )
    }

    private suspend fun getCharacterFromEpisodeResponse(episodeByIdResponse: GetEpisodeByIdResponse): List<GetCharacterByIdResponse> {
        val characterList = episodeByIdResponse.characters.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1)
        }

        val request = NetworkLayer.apiClient.getMultipleCharacters(characterList)
        return request.bodyNullable ?: emptyList()
    }
}