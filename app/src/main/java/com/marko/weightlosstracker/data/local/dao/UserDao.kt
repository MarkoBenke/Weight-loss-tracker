package com.marko.weightlosstracker.data.local.dao

import androidx.room.*
import com.marko.weightlosstracker.data.local.entities.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userData: UserEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(userData: UserEntity)

    @Query("SELECT * FROM user_table WHERE uuid = 0")
    suspend fun getUser(): UserEntity?
}