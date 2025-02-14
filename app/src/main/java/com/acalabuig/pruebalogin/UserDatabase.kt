package com.acalabuig.pruebalogin

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class, NoticiaEntity::class, LikesEntity::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun noticiaDao(): NoticiaDao
    abstract fun likesDao(): LikesDao




}