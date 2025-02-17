package com.acalabuig.pruebalogin

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class, NoticiaEntity::class, LikesEntity::class], version = 3)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun noticiaDao(): NoticiaDao
    abstract fun likesDao(): LikesDao




}