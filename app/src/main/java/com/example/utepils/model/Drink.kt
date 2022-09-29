package com.example.utepils.model

data class Drink(val id: Int,
                 val name: String,
                 val description: String,
                 val ingredients: List<String>,
                 val directions: List<String>,
                 val pictureURL: String,
                 val portion: Int,
                 val tags: List<String>,
                 var score: Int = 0)
