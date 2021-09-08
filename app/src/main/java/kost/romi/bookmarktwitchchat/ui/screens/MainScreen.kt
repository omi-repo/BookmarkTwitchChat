package kost.romi.bookmarktwitchchat.ui.screens

import android.util.Log
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * TODO: give MainScreen its own ViewModel for initializing WebSocket in init(){}
 */

@Composable
fun MainScreen(
    navController: NavHostController,
    message: MutableList<String>,
    scrollState: LazyListState
) {
    val TAG = "MainScreen"
    val coroutineScope = rememberCoroutineScope()

    Scaffold() {
        // reverseLayout = true, to reverse
        LazyColumn(
            reverseLayout = true,
            state = scrollState,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            itemsIndexed(items = message) { index, item ->
                Text(text = item)
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    Scaffold() {
        Column {
            Text(text = "chat")
        }
    }
}