package com.joshdev.smartpocket.repository.database.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object AppDatabaseSingleton {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    private val databaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
//                val dao = INSTANCE?.currencyDao()
//                dao?.let {
//                    val defaultCurrency = Currency(
//                        id = 1,
//                        name = "USD",
//                        symbol = "$",
//                        rate = 1.0,
//                    )
//                    dao.insert(defaultCurrency)
//                }
            }
        }
    }

    fun getInstance(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "smartpocket-db"
            ).fallbackToDestructiveMigration(true).build()
            INSTANCE = instance
            instance
        }
    }
}
