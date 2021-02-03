package com.github.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.dao.HeaderDao
import com.github.entity.HeaderEntity

@Database(entities = [HeaderEntity::class], version = 3, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun mainDao(): HeaderDao

    companion object {
        private var INSTANCE: UserDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): UserDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "user.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }

                return INSTANCE!!
            }
        }
    }

}