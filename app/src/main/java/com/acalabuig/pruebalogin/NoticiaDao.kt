package com.acalabuig.pruebalogin
import androidx.room.*

@Dao
interface NoticiaDao {
    @Query("SELECT * FROM noticias")
    fun getAllNoticias(): List<NoticiaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(noticia: NoticiaEntity)

    @Update
    fun update(noticia: NoticiaEntity)

    @Delete
    fun delete(noticia: NoticiaEntity)
}