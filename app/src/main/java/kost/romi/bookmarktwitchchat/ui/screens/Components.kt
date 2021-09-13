package kost.romi.bookmarktwitchchat.ui.screens

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kost.romi.bookmarktwitchchat.R

/*
ext: String,
onTextChange: (String) -> Unit,
onClick: () -> Unit,
streamers: MutableList<String>
 */

@Composable
fun TopBar(
    streamers: MutableList<String>,
    currentStreamer: String,
    onSwitchStreamer: (String) -> Unit,
    onToggleTheme: () -> Unit
) {
    val TAG = "TopBar"
    Surface(
        color = MaterialTheme.colors.onSurface,
        contentColor = MaterialTheme.colors.onPrimary,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(6.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val textState = remember { mutableStateOf(TextFieldValue()) }
            TopBarSearchBar(
                textState = textState,
                onToggleTheme = onToggleTheme
            )
            StreamerLazyRow(
                streamers = streamers,
                currentStreamer = currentStreamer,
                onSwitchStreamer = onSwitchStreamer
            )
        }
    }
}

@Composable
fun TopBarSearchBar(
    textState: MutableState<TextFieldValue>,
    onToggleTheme: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        var boolTest = false
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },

            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = MaterialTheme.colors.error
            ),
            label = { Text("Search Streamer") },
            modifier = Modifier
                .weight(1f)
                .border(2.dp, MaterialTheme.colors.secondary, RoundedCornerShape(50)),
            textStyle = MaterialTheme.typography.overline
        )
        Button(
            onClick = {
                onToggleTheme()
            },
            modifier = Modifier
                .padding(8.dp, 0.dp, 0.dp, 0.dp)
                .wrapContentWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_dark_mode_24),
                contentDescription = "Toggle Theme"
            )
            if (boolTest) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                boolTest = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                boolTest = false
            }
        }
    }
}

@Composable
fun StreamerLazyRow(
    streamers: MutableList<String>,
    currentStreamer: String,
    onSwitchStreamer: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        itemsIndexed(
            items = streamers
        ) { index, item ->
            if (currentStreamer.equals(item)) {
                ButtonSelected(item = item)
            } else {
                ButtonNotSelected(
                    item = item,
                    onSwitchStreamer = onSwitchStreamer
                )
            }
        }
    }
}

@Composable
fun ButtonSelected(item: String) {
    val TAG = "ButtonSelected"
    Button(
        onClick = {
            Log.i(TAG, "MainScreen: if $item")
        },
        shape = RoundedCornerShape(25),
        modifier = Modifier
            .wrapContentSize()
    )
    {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_check_circle_24),
                contentDescription = "Current Streamer"
            )
            Text(
                text = "$item",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ButtonNotSelected(item: String, onSwitchStreamer: (String) -> Unit) {
    val TAG = "ButtonNotSelected"
    Button(
        onClick = {
            onSwitchStreamer(item)
            Log.i(TAG, "MainScreen: else $item")
        },
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .wrapContentSize()
    )
    {
        Text(text = "$item")
    }
}

var streamersPreview = mutableStateListOf<String>(
    "jakenbakelive",
    "hachubby",
    "nmplol",
    "39daph",
    "winnieechang",
    "nymn",
    "xqcow",
    "esfandtv",
    "greekgodx"
)

@Preview
@Composable
fun TopBarPreview() {
    TopBar(
        streamers = streamersPreview,
        currentStreamer = streamersPreview.get(0),
        onSwitchStreamer = {},
        onToggleTheme = {},
    )
}