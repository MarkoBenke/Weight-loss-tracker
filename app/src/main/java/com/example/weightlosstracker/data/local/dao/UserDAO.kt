package com.example.weightlosstracker.data.local.dao

import androidx.room.*
import com.example.weightlosstracker.data.local.model.UserCache

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserGoal(userData: UserCache)

    @Query("SELECT * FROM user_table WHERE uuid = 0")
    suspend fun getUser(): UserCache?
}