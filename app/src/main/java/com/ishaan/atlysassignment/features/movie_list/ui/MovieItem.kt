package com.ishaan.atlysassignment.features.movie_list.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ishaan.atlysassignment.R
import com.ishaan.atlysassignment.data.network.BaseURL
import com.ishaan.atlysassignment.ui.theme.AtlysAssignmentTheme

@Composable
fun MovieItem(
    posterPath: String,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .clickable {
                onClick()
            }
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            modifier = Modifier
                .width(172.dp)
                .aspectRatio(1f)
        ) {
            val movieUrl = "${BaseURL.IMAGE_BASE_URL}$posterPath"
            AsyncImage(
                model = movieUrl,
                contentDescription = "Movie Poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                placeholder = painterResource(R.drawable.ic_placeholder_image),
                error = painterResource(R.drawable.ic_error_image)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Preview
@Composable
fun MovieItemPreview() {
    AtlysAssignmentTheme {
        MovieItem(
            posterPath = "/ebyxeBh56QNXxSJgTnmz7fXAlwk.jpg",
            title = "title",
            onClick = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieItemDarkPreview() {
    AtlysAssignmentTheme {
        MovieItem(
            posterPath = "/ebyxeBh56QNXxSJgTnmz7fXAlwk.jpg",
            title = "title",
            onClick = {}
        )
    }
}