package com.estudo.rickandmorty.domain.models

data class Episode(
    val id: Int = 0,
    val name: String = "",
    val airDate: String = "",
    val seasonNumber: Int = 0,
    val episodeNumber: Int = 0
) {
    fun getFormattedSeason(): String {
        return "Season $seasonNumber Episode $episodeNumber"
    }

    fun getFormattedSeasonTruncate(): String {
        return "S.$seasonNumber E.$episodeNumber"
    }
}