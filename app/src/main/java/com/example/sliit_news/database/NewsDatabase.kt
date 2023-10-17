package com.example.sliit_news.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.security.AccessControlContext

@Database(entities = [News::class], version = 2) // Change the version to 2
abstract class NewsDatabase : RoomDatabase() {

    abstract fun getNewsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        // Get an instance of the database, creating it if it doesn't exist
        fun getInstance(context: Context): NewsDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context,
                    NewsDatabase::class.java,
                    "news_db"
                ).fallbackToDestructiveMigration() //This line for database migration
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }
}
