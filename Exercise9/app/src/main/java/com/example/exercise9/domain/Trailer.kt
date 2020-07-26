package com.example.exercise9.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Trailer (
    @PrimaryKey
    val movieId: String,
    val url: String
)