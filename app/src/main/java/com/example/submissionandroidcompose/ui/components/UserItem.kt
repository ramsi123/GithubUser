package com.example.submissionandroidcompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    avatarUrl: String,
    username: String,
    navigateToDetail: (String) -> Unit
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .clickable {
                navigateToDetail(username)
            },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(56.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = modifier.width(12.dp))
        Text(
            text = username,
            modifier = modifier
                .weight(1f)
        )
    }
}
