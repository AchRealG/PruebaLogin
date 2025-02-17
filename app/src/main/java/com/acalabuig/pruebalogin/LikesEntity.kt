package com.acalabuig.pruebalogin

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "LikesEntity", primaryKeys = ["userId", "noticiaId"], foreignKeys = [ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["userId"], onDelete = ForeignKey.CASCADE),
                                                                                   ForeignKey(entity = NoticiaEntity::class, parentColumns = ["id"], childColumns = ["noticiaId"], onDelete = ForeignKey.CASCADE)

])
data class LikesEntity(

    val noticiaId: Long,
    val userId: Int
)
