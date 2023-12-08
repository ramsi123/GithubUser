package com.example.submissionandroidcompose.data.remote.retrofit

import com.example.submissionandroidcompose.data.remote.response.DetailUser
import com.example.submissionandroidcompose.data.remote.response.GitbubResponse
import com.example.submissionandroidcompose.data.remote.response.Users
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getSearchUsers(
        @Query("q") query: String
    ): GitbubResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): DetailUser

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String
    ): List<Users>

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") username: String
    ): List<Users>
}