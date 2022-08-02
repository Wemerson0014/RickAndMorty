package com.estudo.rickandmorty.network.response

data class GetCharactersPageResponse(
    val info: PageInfo = PageInfo(),
    val results: List<GetCharacterByIdResponse> = emptyList()
)