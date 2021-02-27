package com.marko.weightlosstracker.data.local.dao

import androidx.room.*
import com.marko.weightlosstracker.data.local.model.UserCache

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userData: UserCache)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(userData: UserCache)

    @Query("SELECT * FROM user_table WHERE uuid = 0")
    suspend fun getUser(): UserCache?
}