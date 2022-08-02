package com.estudo.rickandmorty.network.response

data class GetEpisodesPageResponse(
    val info: PageInfo = PageInfo(),
    val results: List<GetEpisodeByIdResponse> = emptyList()
)