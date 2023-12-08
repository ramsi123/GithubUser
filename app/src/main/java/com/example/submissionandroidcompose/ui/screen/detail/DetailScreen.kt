package com.example.submissionandroidcompose.ui.screen.detail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submissionandroidcompose.di.Injection
import com.example.submissionandroidcompose.ui.ViewModelFactory
import com.example.submissionandroidcompose.ui.common.UiState
import com.example.submissionandroidcompose.ui.components.DetailUser
import com.example.submissionandroidcompose.ui.components.TopAppBarDetailUser

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    username: String,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current

    // checking favorite status
    val favoriteStatus by viewModel.favoriteStatus.observeAsState(false)

    // collect user detail
    viewModel.user.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getDetailUser(username)
            }
            is UiState.Success -> {
                val response = uiState.data
                DetailContent(
                    modifier = modifier,
                    avatarUrl = response.avatarUrl,
                    username = response.username,
                    name = response.name,
                    bio = response.bio,
                    isUserFavorite = favoriteStatus,
                    onFavoriteClicked = {
                        viewModel.updateFavorite(
                            username = response.username,
                            avatarUrl = response.avatarUrl
                        )
                    },
                    navigateBack = navigateBack
                )
            }
            is UiState.Error -> {
                Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    avatarUrl: String?,
    username: String?,
    name: String?,
    bio: String?,
    isUserFavorite: Boolean,
    onFavoriteClicked: () -> Unit,
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBarDetailUser {
                navigateBack()
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onFavoriteClicked()
                }
            ) {
                if (!isUserFavorite) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = null
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            DetailUser(
                modifier = modifier,
                avatarUrl = avatarUrl,
                username = username,
                name = name,
                bio = bio
            )
        }
    }
}