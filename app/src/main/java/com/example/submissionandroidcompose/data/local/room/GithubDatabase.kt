package com.example.submissionandroidcompose.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submissionandroidcompose.data.local.entity.FavoriteUserEntity

@Database(entities = [FavoriteUserEntity::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun gitHubDao(): GithubDao

    companion object {
        @Volatile
        private var instance: GithubDatabase? = null
        fun getInstance(context: Context): GithubDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    GithubDatabase::class.java, "News.db"
                ).build()
            }
    }

}