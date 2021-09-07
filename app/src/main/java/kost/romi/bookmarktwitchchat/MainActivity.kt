package kost.romi.bookmarktwitchchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kost.romi.bookmarktwitchchat.ui.theme.BookmarkTwitchChatTheme

/**
 * TODO: add Tinder Scarlet
 * TODO: IMPORTANT TO ALWAYS COMMIT AND PUSH AFTER TODO'S ARE DONE
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        setContent {
            BookmarkTwitchChatTheme() {
                Text(text = "Test")
            }
        }

    }

    @Preview
    @Composable
    fun Test() {
        Text(text = "Test")
    }
}