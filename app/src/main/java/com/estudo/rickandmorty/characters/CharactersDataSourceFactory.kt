package com.estudo.rickandmorty.characters

import androidx.paging.DataSource
import com.estudo.rickandmorty.network.response.GetCharacterByIdResponse
import kotlinx.coroutines.CoroutineScope

class CharactersDataSourceFactory(
    private val coroutinesScope: CoroutineScope,
    private val repository: CharactersRepository
) : DataSource.Factory<Int, GetCharacterByIdResponse>() {

    override fun create(): DataSource<Int, GetCharacterByIdResponse> {
        return CharactersDataSource(coroutinesScope, repository)
    }
}