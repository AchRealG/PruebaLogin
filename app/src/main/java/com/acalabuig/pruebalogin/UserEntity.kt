package com.acalabuig.pruebalogin

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "UserEntity")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String? = "",
    var password: String? = ""
) : Serializable
