package com.estudo.rickandmorty.network

import com.estudo.rickandmorty.domain.models.Character

object SimpleMortyCache {

    val characterMap = mutableMapOf<Int, Character>()
}