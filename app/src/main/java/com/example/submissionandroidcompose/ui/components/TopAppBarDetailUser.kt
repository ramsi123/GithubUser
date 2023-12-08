package com.example.submissionandroidcompose.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.submissionandroidcompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDetailUser(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(stringResource(R.string.user_detail))
        },
        navigationIcon = {
            IconButton(
                onClick = { navigateBack() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}