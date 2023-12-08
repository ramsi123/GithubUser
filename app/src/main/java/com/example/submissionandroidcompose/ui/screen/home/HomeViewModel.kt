package com.example.submissionandroidcompose.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionandroidcompose.data.GithubRepository
import com.example.submissionandroidcompose.data.remote.response.Users
import com.example.submissionandroidcompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: GithubRepository) : ViewModel() {

    private val _users: MutableStateFlow<UiState<List<Users>>> = MutableStateFlow(UiState.Loading)
    val users: StateFlow<UiState<List<Users>>>
        get() = _users

    fun findSearchUsers(username: String) {
        viewModelScope.launch {
            repository.findSearchUsers(username)
                .catch {
                    _users.value = UiState.Error(it.message.toString())
                }
                .collect { users ->
                    _users.value = UiState.Success(users)
                }
        }
    }

}