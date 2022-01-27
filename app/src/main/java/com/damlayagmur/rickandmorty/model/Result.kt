package com.damlayagmur.rickandmorty.model

data class Result(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
) {

    fun getCharacterId(): String {
        return url.split("/").last()
    }

}

