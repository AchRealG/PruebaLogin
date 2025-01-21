package com.acalabuig.pruebalogin

import androidx.room.*

@Dao
interface LikesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(like: LikesEntity)

    @Delete
    fun delete(like: LikesEntity)

    @Query("SELECT * FROM noticias INNER JOIN ON noticias.id = likes.noticiaId WHERE likes.userId = :userId")
    fun getLikedNoticias(userId: Int): List<NoticiaEntity>
}