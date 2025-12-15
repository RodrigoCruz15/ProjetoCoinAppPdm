package com.example.coinapppdm.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coinapppdm.data.local.dao.FavoriteDao
import com.example.coinapppdm.data.local.entity.FavoriteCoinEntity

@Database(
    entities = [FavoriteCoinEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "coin_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}