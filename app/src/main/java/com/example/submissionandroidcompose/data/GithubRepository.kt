package com.example.submissionandroidcompose.data

import androidx.lifecycle.LiveData
import com.example.submissionandroidcompose.data.local.entity.FavoriteUserEntity
import com.example.submissionandroidcompose.data.local.room.GithubDao
import com.example.submissionandroidcompose.data.remote.response.DetailUser
import com.example.submissionandroidcompose.data.remote.response.Users
import com.example.submissionandroidcompose.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GithubRepository(
    private val apiService: ApiService,
    private val gitHubDao: GithubDao
) {
    suspend fun findSearchUsers(
        username: String
    ): Flow<List<Users>> {
        val response = apiService.getSearchUsers(username)
        val items = response.items
        return flowOf(items)
    }

    suspend fun getDetailUser(
        username: String
    ): Flow<DetailUser> {
        val response = apiService.getDetailUser(username)
        return flowOf(response)
    }

    fun getfavoriteUsers(): Flow<List<FavoriteUserEntity>> {
        return gitHubDao.getFavoriteUsers()
    }

    suspend fun insertFavoriteUser(user: FavoriteUserEntity) {
        gitHubDao.insertFavoriteUser(user)
    }

    suspend fun deleteFavoriteUser(username: String) {
        gitHubDao.deleteFavoriteUser(username)
    }

    fun isUserFavorite(username: String): LiveData<Boolean> {
        return gitHubDao.isUserFavorite(username)
    }

    companion object {
        @Volatile
        private var instance: GithubRepository? = null
        fun getInstance(
            apiService: ApiService,
            gitHubDao: GithubDao
        ): GithubRepository =
            instance ?: synchronized(this) {
                instance ?: GithubRepository(apiService, gitHubDao)
            }.also { instance = it }
    }
}
