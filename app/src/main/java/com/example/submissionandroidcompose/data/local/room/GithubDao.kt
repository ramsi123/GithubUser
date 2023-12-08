package com.example.submissionandroidcompose.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submissionandroidcompose.data.local.entity.FavoriteUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteUser(user: FavoriteUserEntity)

    @Query("DELETE FROM favorite_user WHERE username = :username")
    suspend fun deleteFavoriteUser(username: String)

    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUsers(): Flow<List<FavoriteUserEntity>>

    @Query("SELECT EXISTS(SELECT * FROM favorite_user WHERE username = :username)")
    fun isUserFavorite(username: String): LiveData<Boolean>
}