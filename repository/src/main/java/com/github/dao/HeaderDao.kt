package com.github.dao

import androidx.room.*
import com.github.entity.HeaderEntity

@Dao
interface HeaderDao {
    @Query("SELECT * from header")
    suspend fun getHeader(): HeaderEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(header: HeaderEntity)

    @Update
    suspend fun updateAsync(header: HeaderEntity): Int

    @Query("DELETE FROM header")
    suspend fun deleteAll()
}