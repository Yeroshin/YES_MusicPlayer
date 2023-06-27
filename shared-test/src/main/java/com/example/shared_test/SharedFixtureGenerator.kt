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

     fun generateArtists(count:Int): List<String> {
        val mediaItems = mutableListOf<String>()
        for (i in 1..count) {
            val name =adjectives[Random.nextInt(adjectives.size)]+" "+nouns[Random.nextInt(nouns.size)]
            val mediaItem = name
            mediaItems.add(mediaItem)
        }
        return mediaItems
    }
    fun getCategories(count:Int): List<String> {
        val mediaItems = mutableListOf<String>()
        for (i in 1..count) {
            val name =adjectives[Random.nextInt(adjectives.size)]+" "+nouns[Random.nextInt(nouns.size)]
            val mediaItem = name
            mediaItems.add(mediaItem)
        }
        return mediaItems
    }
    fun generateMediaItemNames(count:Int): List<String> {
        val mediaItems = mutableListOf<String>()
        for (i in 1..count) {
            val name =adjectives[Random.nextInt(adjectives.size)]+" "+nouns[Random.nextInt(nouns.size)]
            val mediaItem = name
            mediaItems.add(mediaItem)
        }
        return mediaItems
    }
}