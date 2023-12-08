package com.example.submissionandroidcompose.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionandroidcompose.data.GithubRepository
import com.example.submissionandroidcompose.data.local.entity.FavoriteUserEntity
import com.example.submissionandroidcompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: GithubRepository) : ViewModel() {

    private val _usersFavorite: MutableStateFlow<UiState<List<FavoriteUserEntity>>> = MutableStateFlow(UiState.Loading)
    val usersFavorite: StateFlow<UiState<List<FavoriteUserEntity>>>
        get() = _usersFavorite

    fun getFavoriteUsers() {
        viewModelScope.launch {
            repository.getfavoriteUsers()
                .catch {
                    _usersFavorite.value = UiState.Error(it.message.toString())
                }
                .collect { users ->
                    _usersFavorite.value = UiState.Success(users)
                }
        }
    }

}