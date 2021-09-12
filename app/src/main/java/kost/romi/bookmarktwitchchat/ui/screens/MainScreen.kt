package kost.romi.bookmarktwitchchat.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import kost.romi.bookmarktwitchchat.model.Messages

/**
 *
 */

@Composable
fun MainScreen(
    messageList: MutableList<Messages>,
    scrollState: LazyListState,
    streamers: MutableList<String>,
    currentStreamer: String,
    onSwitchStreamer: (String) -> Unit,
    onToggleTheme: () -> Unit
) {
    val TAG = "MainScreen"
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBar(
                streamers = streamers,
                currentStreamer = currentStreamer,
                onSwitchStreamer = onSwitchStreamer,
                onToggleTheme = onToggleTheme
            )
        }
    ) {
        // reverseLayout = true, to reverse
        LazyColumn(
            reverseLayout = true,
            state = scrollState,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(items = messageList) { item ->
                Row(horizontalArrangement = Arrangement.Start) {
                    Text(text = item.username)
                    Text(text = " : ")
                    Text(text = item.message)
                }
                //MessageRow(item = item)
            }
        }

    }

    @Composable
    fun MessageRow(item: Messages) {
        Row(horizontalArrangement = Arrangement.Start) {
            Text(text = item.username)
            Text(text = " : ")
            Text(text = item.message)
        }
    }
}
