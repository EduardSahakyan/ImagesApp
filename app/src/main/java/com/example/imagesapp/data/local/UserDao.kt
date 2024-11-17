package com.example.imagesapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.imagesapp.data.local.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT COUNT(*) > 0 FROM users WHERE email = :email")
    suspend fun isEmailUsed(email: String): Boolean

    @Query("SELECT COUNT(*) > 0 FROM users WHERE email = :email AND password = :password")
    suspend fun isUserValid(email: String, password: String): Boolean

    @Insert
    suspend fun insertUser(userEntity: UserEntity)

}