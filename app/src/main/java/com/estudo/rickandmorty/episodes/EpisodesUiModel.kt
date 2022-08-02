package com.estudo.rickandmorty.episodes

import com.estudo.rickandmorty.domain.models.Episode

sealed class EpisodesUiModel {
    class Item(val episode: Episode) : EpisodesUiModel()
    class Header(val text: String) : EpisodesUiModel()
}