package kost.romi.bookmarktwitchchat.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kost.romi.bookmarktwitchchat.MainViewModel

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
    onSwitchStreamer: (String) -> Unit
) {
    val TAG = "TopBar"
    Surface(
        color = Color.Green,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val textState = remember { mutableStateOf(TextFieldValue()) }
            TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                modifier = Modifier.fillMaxWidth()
            )
            //Text("The textfield has this text: " + textState.value.text)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                itemsIndexed(
                    items = streamers
                ) { index, item ->
                    if (currentStreamer.equals(item)) {
                        /*Text(
                            text = item,
                            fontWeight = FontWeight.Bold
                        )*/
                        Button(
                            onClick = {
                                Log.i(TAG, "MainScreen: if $item")
                            },
                            shape = RoundedCornerShape(25)
                        )
                        {
                            Text(text = "$item", fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Button(
                            onClick = {
                                onSwitchStreamer(item)
                                Log.i(TAG, "MainScreen: else $item")
                            },
                            shape = RoundedCornerShape(50)
                        )
                        {
                            Text(text = "$item")
                        }
                    }
//                    Divider(
//                        modifier = Modifier.weight(1f)
//                    )
                }
            }
        }
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
        onSwitchStreamer = {}
    )
}