package com.acalabuig.pruebalogin

import androidx.room.*
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LikesDao {
    @Insert
    suspend fun insert(like: LikesEntity)

    @Delete
    suspend fun delete(like: LikesEntity)

    @Update
    suspend fun actualizarFavorito(like: LikesEntity)

    @Query("SELECT * FROM LikesEntity WHERE userId = :userId")
    suspend fun getLikedNoticias(userId: Long): MutableList<LikesEntity>
}