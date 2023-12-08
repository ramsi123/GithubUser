package com.example.submissionandroidcompose.ui.screen.favorite

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submissionandroidcompose.data.local.entity.FavoriteUserEntity
import com.example.submissionandroidcompose.di.Injection
import com.example.submissionandroidcompose.ui.ViewModelFactory
import com.example.submissionandroidcompose.ui.common.UiState
import com.example.submissionandroidcompose.ui.components.UserItem

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateToDetail: (String) -> Unit
) {
    val context = LocalContext.current
    val listState = rememberLazyListState()

    // collect user favorite data
    viewModel.usersFavorite.collectAsState(initial = UiState.Loading).value.let { uiState ->
       when (uiState) {
           is UiState.Loading -> {
               viewModel.getFavoriteUsers()
           }
           is UiState.Success -> {
               val response = uiState.data
               FavoriteContent(
                   modifier = modifier,
                   users = response,
                   listState = listState,
                   navigateToDetail = navigateToDetail
               )
           }
           is UiState.Error -> {
               Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
           }
       }
    }
}

@Composable
fun FavoriteContent(
    modifier: Modifier = Modifier,
    users: List<FavoriteUserEntity>,
    listState: LazyListState,
    navigateToDetail: (String) -> Unit
) {
    LazyColumn(
        state = listState
    ) {
        items(items = users) { user ->
            UserItem(
                avatarUrl = user.avatarUrl,
                username = user.username,
                navigateToDetail = navigateToDetail,
                modifier = modifier
                    .padding(start = 6.dp, end = 6.dp)
            )
        }
    }
}