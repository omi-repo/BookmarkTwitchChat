package kost.romi.bookmarktwitchchat

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    val TAG = "MyApplication"

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }
}