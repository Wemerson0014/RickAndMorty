package com.estudo.rickandmorty.episodes.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estudo.rickandmorty.domain.models.Episode
import com.estudo.rickandmorty.episodes.EpisodeRepository
import kotlinx.coroutines.launch

class EpisodeDetailViewModel : ViewModel() {

    private val repository = EpisodeRepository()

    private var _episodeLiveData = MutableLiveData<Episode?>()
    val episodeLiveData: LiveData<Episode?> = _episodeLiveData

    fun fetchEpisode(episodeId: Int) {
        viewModelScope.launch {
            val episode = repository.getEpisodeById(episodeId)
            _episodeLiveData.postValue(episode)
        }
    }
}