package kost.romi.bookmarktwitchchat.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

/**
 * TODO: give MainScreen its own ViewModel for initializing WebSocket in init(){}
 * TODO: the implementation of state hoisting is not right. fix it.
 */

@Composable
fun MainScreen(
    navController: NavHostController,
    message: MutableList<String>,
    scrollState: LazyListState,
    streamers: MutableList<String>,
    currentStreamer: String,
    onSwitchStreamer: (String) -> Unit
) {
    val TAG = "MainScreen"
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBar(
                streamers = streamers,
                currentStreamer = currentStreamer,
                onSwitchStreamer = onSwitchStreamer
            )
        }
    ) {
        Column() {

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
}

@Preview
@Composable
fun MainScreenPreview() {
    Scaffold() {
        Column {
//            Text(text = "chat")

        }
    }
}