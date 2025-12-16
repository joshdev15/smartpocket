package com.joshdev.smartpocket.repository.database.room

import android.content.Context
import androidx.room.Room

object AppDatabaseSingleton {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room
                .databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "smartpocket-db"
                )
                .fallbackToDestructiveMigration(true)
                .build()

            INSTANCE = instance
            instance
        }
    }
}
