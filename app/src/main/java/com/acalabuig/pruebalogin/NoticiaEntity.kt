package com.acalabuig.pruebalogin

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "NoticiaEntity")
data class NoticiaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    var content: String = "",
    var fecha: String = "",
    var esFavorita: Boolean = false,
    var imageurl: String = "",
    var noticiaurl: String = "",
): Serializable