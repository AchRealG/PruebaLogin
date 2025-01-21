package com.acalabuig.pruebalogin

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "noticiaId"])
data class LikesEntity(
    val userId: Int,
    val noticiaId: Int
)
