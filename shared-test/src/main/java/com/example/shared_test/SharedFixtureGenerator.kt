package com.example.shared_test

import kotlin.random.Random

object SharedFixtureGenerator {


    private val adjectives = listOf(
        "Electric", "Neon", "Cosmic", "Mystic", "Psychedelic",
        "Radiant", "Vibrant", "Whimsical", "Dreamy", "Surreal"
    )

    private val nouns = listOf(
        "Sunset", "Galaxy", "Rainbow", "Oasis", "Horizon",
        "Fountain", "Jungle", "Spectrum", "Harmony", "Mirage"
    )
    private const val count=2

    private val artistsNames= generateMediaItemNames(count)
    private val albumsNames=generateMediaItemNames(count)
    private val tracksNames= generateMediaItemNames(count)
    private fun generateMediaItemNames(count:Int): List<String> {
        val mediaItems = mutableListOf<String>()
        for (i in 1..count) {
            val name =adjectives[Random.nextInt(adjectives.size)]+" "+nouns[Random.nextInt(nouns.size)]
            mediaItems.add(name)
        }
        return mediaItems.toList()
    }

    fun getArtistsNames():List<String>{
        return artistsNames
    }
    fun getAlbumsNames():List<String>{
        return albumsNames
    }
    fun getTracksNames():List<String>{
        return tracksNames
    }



}