package com.estudo.rickandmorty.network

import com.estudo.rickandmorty.network.response.GetCharacterByIdResponse
import com.estudo.rickandmorty.network.response.GetCharactersPageResponse
import com.estudo.rickandmorty.network.response.GetEpisodeByIdResponse
import com.estudo.rickandmorty.network.response.GetEpisodesPageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyServices {

    @GET("character/{character-id}")
    suspend fun getCharacterById(
        @Path("character-id") characterId: Int
    )
            : Response<GetCharacterByIdResponse>

    @GET("character/")
    suspend fun getCharactersPage(
        @Query("page") pageIndex: Int
    ): Response<GetCharactersPageResponse>

    @GET("character/")
    suspend fun getCharactersPage(
        @Query("name") characterName: String,
        @Query("page") pageIndex: Int
    ): Response<GetCharactersPageResponse>

    @GET("episode/{episode-id}")
    suspend fun getEpisodeById(
        @Path("episode-id") episodeId: Int
    ): Response<GetEpisodeByIdResponse>

    @GET("episode/{episode-range}")
    suspend fun getEpisodeRange(
        @Path("episode-range") episodeRange: String
    ): Response<List<GetEpisodeByIdResponse>>

    @GET("episode/")
    suspend fun getEpisodesPage(
        @Query("page") pageIndex: Int
    ): Response<GetEpisodesPageResponse>

    @GET("character/{list}")
    suspend fun getMultipleCharacters(
        @Path("list") characterList: List<String>
    ): Response<List<GetCharacterByIdResponse>>
}