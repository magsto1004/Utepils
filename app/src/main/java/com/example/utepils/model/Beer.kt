package com.example.utepils.model

data class Beers(val beers: List<Beer>)

data class Beer(val id: Int,
                val name: String,
                val pictureURL: String,
                val description: String,
                val tags: List<String>)

