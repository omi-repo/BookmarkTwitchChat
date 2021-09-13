package kost.romi.bookmarktwitchchat.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
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
                .padding(8.dp)
        ) {
            items(items = messageList) { item ->
                Divider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(
                            start = 0.dp,
                            top = 4.dp,
                            end = 0.dp,
                            bottom = 4.dp
                        )
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(item.username)
                        }
                        append(" : ")
                        append(item.message)
                    }
                )
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
