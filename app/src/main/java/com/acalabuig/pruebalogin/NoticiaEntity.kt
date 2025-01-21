package com.acalabuig.pruebalogin

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noticias")
data class NoticiaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val url: String
)