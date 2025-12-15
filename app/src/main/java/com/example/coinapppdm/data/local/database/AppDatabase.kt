package com.example.coinapppdm.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coinapppdm.data.local.dao.FavoriteCoinDao

@Database(entities = [FavoriteCoinDao::class], version = 1)
abstract class  AppDatabase : RoomDatabase(){
    abstract fun articleDao(): FavoriteCoinDao

    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getInstance(context: Context): AppDatabase?{
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "database-favoritecoin"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}