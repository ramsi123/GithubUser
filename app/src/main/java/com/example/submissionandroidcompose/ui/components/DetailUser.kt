package com.example.submissionandroidcompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun DetailUser(
    modifier: Modifier = Modifier,
    avatarUrl: String?,
    username: String?,
    name: String?,
    bio: String?
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(120.dp)
                .clip(CircleShape)
        )
        if (name != null) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold
            )
        }
        if (username != null) {
            Text(text = username)
        }
        if (bio != null) {
            Text(
                modifier = modifier
                    .padding(start = 16.dp, end = 16.dp),
                text = bio
            )
        }
    }
}
