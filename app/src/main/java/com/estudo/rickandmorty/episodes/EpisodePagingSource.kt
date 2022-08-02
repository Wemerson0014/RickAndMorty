package com.estudo.rickandmorty.episodes

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.estudo.rickandmorty.domain.mappers.EpisodeMapper
import com.estudo.rickandmorty.domain.models.Episode
import com.estudo.rickandmorty.network.NetworkLayer
import kotlinx.coroutines.CoroutineScope

class EpisodePagingSource(
    private val repository: EpisodeRepository
) : PagingSource<Int, EpisodesUiModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodesUiModel> {
        val pageNumber = params.key ?: 1
        val previousKey = if (pageNumber == 1) null else pageNumber - 1
        val pageRequest = NetworkLayer.apiClient.getEpisodesPage(pageNumber)

        pageRequest.exception?.let {
            return LoadResult.Error(it)
        }

        return LoadResult.Page(
            data = pageRequest.body.results.map { response ->
                EpisodesUiModel.Item(EpisodeMapper.buildFrom(response))
            },
            prevKey = previousKey,
            nextKey = getPageIndexFromNext(pageRequest.body.info.next)
        )
    }

    override fun getRefreshKey(state: PagingState<Int, EpisodesUiModel>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private fun getPageIndexFromNext(next: String?): Int? {
        return next?.split("?page=")?.get(1)?.toInt()
    }
}