package com.acalabuig.pruebalogin

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    suspend fun getAllUsers(): MutableList<UserEntity>

    @Insert
    suspend fun addUser(userEntity: UserEntity)

    @Update
    suspend fun updateUser(userEntity: UserEntity)

    @Delete
    suspend fun deleteUser(userEntity: UserEntity)

    @Query("SELECT * FROM UserEntity WHERE name = :username AND password = :password")
     suspend fun getUser(username: String, password: String): UserEntity?
}