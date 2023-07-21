package com.example.shared_test

import kotlin.random.Random

object SharedFixtureGenerator {
    private const val selectedItem=1
    private val adjectives = listOf(
        "Electric", "Neon", "Cosmic", "Mystic", "Psychedelic",
        "Radiant", "Vibrant", "Whimsical", "Dreamy", "Surreal"
    )

    private val nouns = listOf(
        "Sunset", "Galaxy", "Rainbow", "Oasis", "Horizon",
        "Fountain", "Jungle", "Spectrum", "Harmony", "Mirage"
    )
    private const val artistsCount=2
    private const val albumsCount= artistsCount*2
    private const val tracksCount= albumsCount*2
    private val artistsNames= generateMediaItemNames(artistsCount)
    private val albumsNames=generateMediaItemNames(albumsCount)
    private val tracksNames= generateMediaItemNames(tracksCount)

     fun generateArtistsNames(count:Int): List<String> {
        val mediaItems = mutableListOf<String>()
        for (i in 1..count) {
            val name =adjectives[Random.nextInt(adjectives.size)]+" "+nouns[Random.nextInt(nouns.size)]
            mediaItems.add(name)
        }
        return mediaItems
    }
    /*fun getCategories(count:Int): List<String> {
        val mediaItems = mutableListOf<String>()
        for (i in 1..count) {
            val name =adjectives[Random.nextInt(adjectives.size)]+" "+nouns[Random.nextInt(nouns.size)]
            val mediaItem = name
            mediaItems.add(mediaItem)
        }
        return mediaItems
    }*/
    fun generateMediaItemNames(count:Int): List<String> {
        val mediaItems = mutableListOf<String>()
        for (i in 1..count) {
            val name =adjectives[Random.nextInt(adjectives.size)]+" "+nouns[Random.nextInt(nouns.size)]
            mediaItems.add(name)
        }
        return mediaItems.toList()
    }
    fun getSelectedItem():Int{
        return selectedItem
    }
}