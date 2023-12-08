package com.example.submissionandroidcompose.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submissionandroidcompose.data.remote.response.Users
import com.example.submissionandroidcompose.di.Injection
import com.example.submissionandroidcompose.ui.ViewModelFactory
import com.example.submissionandroidcompose.ui.common.UiState
import com.example.submissionandroidcompose.ui.components.SearchBar
import com.example.submissionandroidcompose.ui.components.UserItem

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateToDetail: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val context = LocalContext.current
    val controller = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()

    // collect users data
    viewModel.users.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.findSearchUsers("a")
            }
            is UiState.Success -> {
                HomeContent(
                    users = uiState.data,
                    listState = listState,
                    modifier = modifier,
                    query = query,
                    onQueryChange = {
                        query = it
                    },
                    onSearch = {
                        controller?.hide()
                        if (query.isEmpty()) {
                            viewModel.findSearchUsers("a")
                        } else {
                            viewModel.findSearchUsers(query)
                        }
                    },
                    onClearedSearchBar = {
                        query = ""
                    },
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
fun HomeContent(
    users: List<Users>,
    listState: LazyListState,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onClearedSearchBar: () -> Unit,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = listState
    ) {
        item {
            SearchBar(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                onClearedSearchBar = onClearedSearchBar
            )
        }
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