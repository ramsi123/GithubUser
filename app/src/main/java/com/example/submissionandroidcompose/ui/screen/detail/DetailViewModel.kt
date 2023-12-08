package com.example.submissionandroidcompose.ui.screen.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.submissionandroidcompose.data.GithubRepository
import com.example.submissionandroidcompose.data.local.entity.FavoriteUserEntity
import com.example.submissionandroidcompose.data.remote.response.DetailUser
import com.example.submissionandroidcompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: GithubRepository) : ViewModel() {

    private val _user: MutableStateFlow<UiState<DetailUser>> = MutableStateFlow(UiState.Loading)
    val user: StateFlow<UiState<DetailUser>>
        get() = _user

    val favoriteData = MutableLiveData<String>()

    val favoriteStatus = favoriteData.switchMap {
        repository.isUserFavorite(it)
    }

    fun updateFavorite(username: String, avatarUrl: String) {
        viewModelScope.launch {
            if (favoriteStatus.value as Boolean) {
                repository.deleteFavoriteUser(username)
            } else {
                repository.insertFavoriteUser(
                    FavoriteUserEntity(
                        username = username, avatarUrl = avatarUrl
                    )
                )
            }
        }
    }

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            repository.getDetailUser(username)
                .catch {
                    _user.value = UiState.Error(it.message.toString())
                }
                .collect { user ->
                    favoriteData.value = user.username
                    _user.value = UiState.Success(user)
                }
        }
    }
}