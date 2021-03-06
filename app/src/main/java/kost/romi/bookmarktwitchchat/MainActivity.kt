package kost.romi.bookmarktwitchchat

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kost.romi.bookmarktwitchchat.ui.screens.MainScreen
import kost.romi.bookmarktwitchchat.ui.theme.BookmarkTwitchChatTheme
import android.R
import android.view.Window

import android.view.WindowManager
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat


/**
 * TODO: add Tinder Scarlet
 * TODO: IMPORTANT TO ALWAYS COMMIT AND PUSH AFTER TODO'S ARE DONE
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val TAG = "MainActivity"

    // should be saved in data store
    val isDark = mutableStateOf(false)

    fun toggleLightTheme() {
        Log.d(TAG, "toggleLightTheme: isDark == $isDark")
        isDark.value = !isDark.value
    }

    val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val scrollState = rememberLazyListState()

            /*// Remember a SystemUiController
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = MaterialTheme.colors.isLight

            SideEffect {
                // Update all of the system bar colors to be transparent, and use
                // dark icons if we're in light theme
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = useDarkIcons
                )

                // setStatusBarsColor() and setNavigationBarsColor() also exist
            }*/

            MainNavHost(
                navController = navController,
                viewModel = mainViewModel,
                scrollState = scrollState
            )
        }

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
//        mainViewModel.stopWebSocket()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
//        mainViewModel.stopWebSocket()
    }

    @Composable
    fun MainNavHost(
        navController: NavHostController,
        viewModel: MainViewModel,
        scrollState: LazyListState
    ) {
        BookmarkTwitchChatTheme(
            darkTheme = isDark.value
        ) {
            NavHost(navController = navController, startDestination = "mainScreen") {

                composable("mainScreen") {
                    MainScreen(
                        messageList = mainViewModel.messageList,
                        scrollState = scrollState,
                        streamers = viewModel.streamers,
                        currentStreamer = viewModel.currentStreamer,
                        onSwitchStreamer = {
                            viewModel::onSwitchStreamer
                        },
                        onToggleTheme = ::toggleLightTheme
                    )
                }
            }
        }
    }

}