package com.ishaan.atlysassignment.features.movie_list.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ishaan.atlysassignment.ui.theme.AtlysAssignmentTheme

@Composable
fun MovieListScreen(
    modifier: Modifier = Modifier
) {

}

@Preview
@Composable
private fun MovieListScreenPreview() {
    AtlysAssignmentTheme {
        MovieListScreen()
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun MovieListScreenDarkPreview() {
    AtlysAssignmentTheme {
        MovieListScreen()
    }
}