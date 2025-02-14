package com.acalabuig.pruebalogin
import androidx.room.*


@Dao
interface NoticiaDao {
    @Query("SELECT * FROM NoticiaEntity")
    suspend fun getAllNoticias(): MutableList<NoticiaEntity>

    @Insert
    suspend fun insert(noticia: NoticiaEntity)

    @Update
    suspend fun update(noticia: NoticiaEntity)

    @Delete
    suspend fun delete(noticia: NoticiaEntity)
}