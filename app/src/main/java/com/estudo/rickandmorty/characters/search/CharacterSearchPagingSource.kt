package com.estudo.rickandmorty.characters.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.estudo.rickandmorty.domain.mappers.CharacterMapper
import com.estudo.rickandmorty.domain.models.Character
import com.estudo.rickandmorty.network.NetworkLayer
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities

class CharacterSearchPagingSource(
    private val userSearch: String,
    private val localExceptionCallback: (LocalException) -> Unit
) : PagingSource<Int, Character>() {

    sealed class LocalException : Exception() {
        object EmptySearch : LocalException()
        object NoResults : LocalException()
    }

    @ObsoleteCoroutinesApi
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, Character> {

        if (userSearch.isEmpty()) {
            val exception = LocalException.EmptySearch
            localExceptionCallback(exception)
            return LoadResult.Error(exception)
        }
        val pageNumber = params.key ?: 1
        val previousKey = if (pageNumber == 1) null else pageNumber - 1

        val request = NetworkLayer.apiClient.getCharactersPage(
            characterName = userSearch,
            pageIndex = pageNumber
        )

        request.exception?.let {
            return LoadResult.Error(it)
        }

        return LoadResult.Page(
            data = request.bodyNullable?.results?.map { characterResponse ->
                CharacterMapper.buildFrom(characterResponse)
            } ?: emptyList(),
            prevKey = previousKey,
            nextKey = getPageIndexFromNext(request.bodyNullable?.info?.next)
        )
    }

    private fun getPageIndexFromNext(next: String?): Int? {
        if (next == null) {
            return null
        }

        val remainder = next.substringAfter("page=")
        val finalIndex = if (remainder.contains('&')) {
            remainder.indexOfFirst { it == '&' }
        } else {
            remainder.length
        }

        return remainder.substring(0, finalIndex).toIntOrNull()
    }
}