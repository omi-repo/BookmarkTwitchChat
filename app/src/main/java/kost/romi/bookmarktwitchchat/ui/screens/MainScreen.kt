package kost.romi.bookmarktwitchchat.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import kost.romi.bookmarktwitchchat.MainViewModel

@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {
    Scaffold() {
        Column() {
            Text(text = "test")
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    Scaffold() {
        Column() {
            Text(text = "test")
        }
    }
}