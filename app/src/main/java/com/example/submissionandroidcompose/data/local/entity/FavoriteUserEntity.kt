package com.example.submissionandroidcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_user")
data class FavoriteUserEntity(
    @PrimaryKey(autoGenerate = false)
    val username: String = "",
    val avatarUrl: String = ""
)