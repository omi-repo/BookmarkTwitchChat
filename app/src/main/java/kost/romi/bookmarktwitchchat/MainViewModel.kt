package kost.romi.bookmarktwitchchat

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocketException
import com.neovisionaries.ws.client.WebSocketFactory
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@ViewModelScoped
class MainViewModel @Inject constructor() : ViewModel() {

    var wbLaunched = false

    //    var massage = mutableStateOf(mutableListOf<String>("test", "test1"))
    var message = mutableStateListOf<String>("test", "test1")

    val webSocketClient = WebSocketFactory().createSocket("wss://irc-ws.chat.twitch.tv")

    init {
        launchStockTickerWebSocketNV()
    }

    fun launchStockTickerWebSocketNV() {
        viewModelScope.launch {
            startStockTickerWebSocket()
        }
    }

    suspend fun startStockTickerWebSocket() {
        val TAG = "startStockTickerWebSocket"
        webSocketClient.addListener(object : WebSocketAdapter() {
            override fun onConnected(
                websocket: WebSocket?,
                headers: MutableMap<String, MutableList<String>>?
            ) {
                super.onConnected(websocket, headers)
                Log.d(TAG, "onConnected: ")
                Log.d(TAG, "onConnected: ${headers.toString()}")
                websocket?.sendText("PASS oauth:jxq8kwp09wtl1pzhkh6pblde7z5y5b")
                websocket?.sendText("NICK wildo210")
                websocket?.sendText("JOIN #39daph")
                for (head in headers?.entries!!) {
                    Log.d(TAG, "onConnected: ${head.key} & ${head.value}")
                }

                wbLaunched = true
            }

            override fun onTextMessage(websocket: WebSocket?, text: String?) {
                super.onTextMessage(websocket, text)
                Log.d(TAG, "onTextMessage: $text")
//                _massage.value = text
                if (text != null) {
                    message.add(0, text)
                }
                Log.d(TAG, "pingSenderName: ${websocket?.pingSenderName}")
//                websocket?.sendText("{\"action\":\"subscribe\",\"params\":\"T.LPL,Q.MSFT\"}")
                Log.d(
                    TAG,
                    "connectedSocket?.inetAddress: ${websocket?.connectedSocket?.inetAddress}"
                )
                Log.d(
                    TAG,
                    "connectedSocket?.isConnected: ${websocket?.connectedSocket?.isConnected}"
                )
                Log.d(
                    TAG,
                    "connectedSocket?.port: ${websocket?.connectedSocket?.port}"
                )
                Log.d(
                    TAG,
                    "websocket?.agreedProtocol: ${websocket?.agreedProtocol}"
                )
                Log.d(
                    TAG,
                    "websocket?.isOpen: ${websocket?.isOpen}"
                )
                Log.d(
                    TAG,
                    "websocket?.state: ${websocket?.state}"
                )
                if (text?.contains("ping") == true) {
                    websocket?.sendText("{\"type\":\"pong\"}")
                    Log.d(TAG, "onMessage: {\"type\":\"pong\"}")
                }
            }

            override fun onTextMessageError(
                websocket: WebSocket?,
                cause: WebSocketException?,
                data: ByteArray?
            ) {
                super.onTextMessageError(websocket, cause, data)
                Log.e(TAG, "onError: cause: ", cause)
                Log.d(TAG, "onError: data: ${data.contentToString()}")
            }

            override fun onError(websocket: WebSocket?, cause: WebSocketException?) {
                super.onError(websocket, cause)
                Log.e(TAG, "onError: ", cause)
            }

            override fun onConnectError(websocket: WebSocket?, exception: WebSocketException?) {
                super.onConnectError(websocket, exception)
                Log.e(TAG, "onError: ", exception)
            }
        })
        webSocketClient.connectAsynchronously()
    }

    fun stopWebSocket() {
        val TAG = "stopWebSocket"
        if (webSocketClient.connectedSocket.isConnected) {
            webSocketClient.disconnect()
            Log.d(TAG, "stopWebSocket: webSocketClient.disconnect()")
        }
        Log.d(TAG, "stopWebSocket: list size: ${message.size}")
    }

}