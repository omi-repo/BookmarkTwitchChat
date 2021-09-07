package kost.romi.bookmarktwitchchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kost.romi.bookmarktwitchchat.ui.screens.MainScreen
import kost.romi.bookmarktwitchchat.ui.theme.BookmarkTwitchChatTheme

/**
 * TODO: add Tinder Scarlet
 * TODO: IMPORTANT TO ALWAYS COMMIT AND PUSH AFTER TODO'S ARE DONE
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        setContent {
            val navController = rememberNavController()
            MainNavHost(navController = navController, viewModel = mainViewModel)
        }

    }

    @Composable
    fun MainNavHost(navController: NavHostController, viewModel: MainViewModel) {
        BookmarkTwitchChatTheme {
            NavHost(navController = navController, startDestination = "mainScreen") {
                composable("mainScreen") {
                    MainScreen(navController, viewModel)
                }
            }
        }
    }

}