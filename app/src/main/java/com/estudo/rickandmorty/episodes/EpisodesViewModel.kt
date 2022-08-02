package com.estudo.rickandmorty.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.estudo.rickandmorty.Constants
import com.estudo.rickandmorty.domain.models.Episode
import kotlinx.coroutines.flow.map

class EpisodesViewModel : ViewModel() {

    private val repository = EpisodeRepository()
    val flow = Pager(
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        EpisodePagingSource(repository)
    }.flow.cachedIn(viewModelScope).map {
        it.insertSeparators { model: EpisodesUiModel?, model2: EpisodesUiModel? ->
            if ( model == null) {
                return@insertSeparators EpisodesUiModel.Header("Season 1")
            }

            if (model2 == null) {
                return@insertSeparators null
            }

            if (model is EpisodesUiModel.Header || model2 is EpisodesUiModel.Header) {
                return@insertSeparators null
            }

            val episode1 = (model as EpisodesUiModel.Item).episode
            val episode2 = (model2 as EpisodesUiModel.Item).episode

            return@insertSeparators if (episode2.seasonNumber != episode1.seasonNumber) {
                EpisodesUiModel.Header("Season ${episode2.seasonNumber}")
            } else {
                null
            }
        }
    }
}