package com.example.submissionandroidcompose.di

import android.content.Context
import com.example.submissionandroidcompose.data.GithubRepository
import com.example.submissionandroidcompose.data.local.room.GithubDatabase
import com.example.submissionandroidcompose.data.remote.retrofit.ApiConfig

object Injection {

    fun provideRepository(context: Context): GithubRepository {
        val apiService = ApiConfig.getApiService()
        val database = GithubDatabase.getInstance(context)
        val gitHubDao = database.gitHubDao()
        return GithubRepository.getInstance(apiService, gitHubDao)
    }

}