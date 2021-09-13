package kost.romi.bookmarktwitchchat

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.neovisionaries.ws.client.*
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

import android.util.Log
import androidx.lifecycle.viewModelScope
import kost.romi.bookmarktwitchchat.model.Messages
import kotlinx.coroutines.launch
import okhttp3.internal.closeQuietly
import okhttp3.internal.trimSubstring


/**
 * TODO: Tryout this WebSocket API implementation, might work better: https://medium.com/swlh/android-tutorial-part-1-using-java-websocket-with-kotlin-646a5f1f09de
 */

@ViewModelScoped
class MainViewModel @Inject constructor() : ViewModel() {

    val TAG = "MainViewModel"

    var wbLaunched = false

    var webSocketClient: WebSocket? = null

    //    var massage = mutableStateOf(mutableListOf<String>("test", "test1"))
    var messageListString = mutableStateListOf<String>("test", "test1")

    var messageList = mutableStateListOf<Messages>()

    var streamers = mutableStateListOf<String>(
        "dafran",
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

    var currentStreamer = streamers.get(0)


    init {
        launchStockTickerWebSocketNV(currentStreamer)
    }


    fun onSwitchStreamer(newStreamer: String) {
        Log.i(TAG, "onSwitchStreamer: $newStreamer")
        currentStreamer = newStreamer
        webSocketClient?.socket?.closeQuietly()
    }

    fun launchStockTickerWebSocketNV(currentStreamer: String) {
        Log.i(TAG, "launchStockTickerWebSocketNV: ")
        webSocketClient = WebSocketFactory().createSocket("wss://irc-ws.chat.twitch.tv")
        viewModelScope.launch {
            startStockTickerWebSocket(currentStreamer)
        }
    }

    suspend fun startStockTickerWebSocket(currentStreamer: String) {
        Log.i(TAG, "startStockTickerWebSocket: ")
        val TAG = "startStockTickerWebSocket"
        webSocketClient?.addListener(object : WebSocketAdapter() {
            override fun onConnected(
                websocket: WebSocket?,
                headers: MutableMap<String, MutableList<String>>?
            ) {
                super.onConnected(websocket, headers)
                Log.d(TAG, "onConnected: ")
                Log.d(TAG, "onConnected: ${headers.toString()}")
                websocket?.sendText("PASS oauth:jxq8kwp09wtl1pzhkh6pblde7z5y5b")
                websocket?.sendText("NICK wildo210")
                websocket?.sendText("JOIN #$currentStreamer")
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
                    if (text.substring(0, ":tmi.twitch.tv".length).contains(":tmi.twitch.tv")) {
                        //skip
                    } else {
                        messageListString.add(0, text)
                        val username = text.trimSubstring(1, text.indexOf("!"))
                        val message =
                            text.trimSubstring(
                                text.indexOf("#$currentStreamer :") + "#$currentStreamer :".length,
                                text.length
                            )
                        Log.i(TAG, "onTextMessage(filtered): $username|$message")
                        messageList.add(0, Messages(username = username, message = message))
                    }
                }
                /*Log.d(TAG, "pingSenderName: ${websocket?.pingSenderName}")
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
                )*/
                if (text?.contains("ping") == true) {
                    websocket?.sendText("{\"type\":\"pong\"}")
                    Log.d(TAG, "onMessage: {\"type\":\"pong\"}")
                }

            }

            override fun onDisconnected(
                websocket: WebSocket?,
                serverCloseFrame: WebSocketFrame?,
                clientCloseFrame: WebSocketFrame?,
                closedByServer: Boolean
            ) {
                super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer)
                Log.d(TAG, "onDisconnected: ")
                stopWebSocket(websocket)
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
                Log.e(TAG, "cause?.cause: ", cause?.cause)
                Log.d(TAG, "cause?.message: ${cause?.message}")
                Log.d(TAG, "cause?.error?.name: ${cause?.error?.name}")
                Log.d(TAG, "cause?.error?.ordinal: ${cause?.error?.ordinal}")
                //Log.d(TAG, "cause?.metaInfo: ${cause?.metaInfo}")
                val errorMessage = cause?.message
                if (errorMessage?.contains("connection abort") == true) {
                    Log.i("ERROR", "onError message: Connection abort")
                }
                // TODO: show refresh button to restart the chat.
            }

            override fun onConnectError(websocket: WebSocket?, exception: WebSocketException?) {
                super.onConnectError(websocket, exception)
                Log.e(TAG, "onError: ", exception)
            }
        })
        webSocketClient?.connectAsynchronously()
    }

    fun stopWebSocket(websocket: WebSocket?) {
        val TAG = "stopWebSocket"
//        webSocketClient?.disconnect()
        websocket?.disconnect()
        if (webSocketClient?.connectedSocket?.isConnected == true) {
            Log.d(TAG, "stopWebSocket: webSocketClient.disconnect()")
        }
        Log.d(TAG, "stopWebSocket: list size: ${messageListString.size}")
    }

}