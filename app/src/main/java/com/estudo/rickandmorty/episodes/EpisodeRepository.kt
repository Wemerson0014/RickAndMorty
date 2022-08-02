package com.estudo.rickandmorty.episodes

import com.estudo.rickandmorty.network.NetworkLayer
import com.estudo.rickandmorty.network.response.GetEpisodesPageResponse

class EpisodeRepository {

    suspend fun fetchEpisodePage(pageIndex: Int): GetEpisodesPageResponse? {
        val pageRequest = NetworkLayer.apiClient.getEpisodesPage(pageIndex)

        if (!pageRequest.isSuccessful) {
            return null
        }

        return pageRequest.body
    }
}