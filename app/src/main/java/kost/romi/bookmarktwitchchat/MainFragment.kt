package kost.romi.bookmarktwitchchat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import io.javalin.Javalin
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.get
import io.ktor.client.features.websocket.*
import io.ktor.client.features.websocket.WebSockets.Feature.install
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kost.romi.bookmarktwitchchat.databinding.FragmentMainBinding
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString.Companion.encodeUtf8
import org.json.JSONException

import org.json.JSONObject
import java.lang.Exception
import java.net.InetSocketAddress
import java.net.URI
import java.util.Collections.singletonMap
import okio.ByteString


/**
 *
 */
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val client_ID = "xw13yy6lxjr776cr3273hm83urcjvn"
        val client_secret = "qo52p3dscdesjb0nwjrwkyqjnpen8m"
        val oauth = "oauth:mluc9wkfrsrzlucppan2ne3k49ni0u"
        val nickname = "wildo210"
        val address = "ws://irc-ws.chat.twitch.tv"
        val port = 80

        /*viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
//            socketIOFun(address, port, oauth, nickname)
//            ktorSocket(address, port, oauth, nickname)
//            withJavalin(address, port, oauth, nickname)
//            socketIOAccordingJoyce(address, port, oauth, nickname)
//            ktorWebsocketClient(address, port, oauth, nickname)
//            webSocketWithOkHttp(address, port, oauth, "wildo210")
            ktorWithBasicAuth(address, port, oauth, nickname)
        }*/

    }

    private suspend fun ktorWithBasicAuth(
        address: String,
        port: Int,
        oauth: String,
        nickname: String
    ) {
        val client = HttpClient {
            install(WebSockets)
        }
        client.ws(
            method = HttpMethod.Get,
            host = "$address:$port"
        ) { // this: DefaultClientWebSocketSession

            // Send text frame.
            send("pass $oauth")
            send("nick wildo210")
            send("join #vapejuicejordan")

            // Receive frame.
            val frame = incoming.receive()
            when (frame) {
                is Frame.Text -> println(frame.readText())
                is Frame.Binary -> println(frame.readBytes())
            }
        }
    }

    private fun webSocketWithOkHttp(
        address: String,
        port: Int,
        oauth: String,
        nickname: String
    ) {
        val client: OkHttpClient = OkHttpClient()
        val request: Request = Request.Builder().url(address).build()
        val echo = EchoWebSocketListener(address, port, oauth)
//        val ws = client.dispatcher().executorService().shutdown()
    }

    private suspend fun ktorWebsocketClient(
        address: String,
        port: Int,
        oauth: String,
        nickname: String
    ) {
        val client = HttpClient(CIO)
        client.ws(
            method = HttpMethod.Get,
            host = address,
            port = port, path = ""
        ) { // this: DefaultClientWebSocketSession

            // Send text frame.
            send("pass $oauth")

            // Receive frame.
            val frame = incoming.receive()
            when (frame) {
//                is Frame.Text -> println(frame.readText())
//                is Frame.Binary -> println(frame.readBytes())
            }
        }
    }

    private fun socketIOAccordingJoyce(
        address: String,
        port: Int,
        oauth: String,
        nickname: String
    ) {
        val options: IO.Options =
            IO.Options.builder()
                .setPort(443)
                .build()
        val webSocket = IO.socket("wss://irc-ws.chat.twitch.tv", options)
        webSocket.connect()
        webSocket
            .on(Socket.EVENT_CONNECT) {
                Log.i("SOCKET", "Socket.EVENT_CONNECT: $it")
                webSocket.send("PASS $oauth".encodeUtf8())
                webSocket.send("NICK wildo210".encodeUtf8())
            }
            .on(Socket.EVENT_CONNECT_ERROR) {
                Log.i("SOCKET", "Socket.EVENT_CONNECT_ERROR: $it")
                it.forEach {
                    when (it) {
                        is String -> Log.e("error is String", "${it}")
                        is Exception -> Log.e("error is Exception", "${it.printStackTrace()}")
                    }
                }
            }
            .on(Socket.EVENT_DISCONNECT) {
                Log.i("SOCKET", "Socket.EVENT_DISCONNECT: $it")
                it.forEach {
                    when (it) {
                        is String -> Log.i("disconnect is String", "$it")
                        is Exception -> Log.e("disconnect is Exception", "${it.printStackTrace()}")
                    }
                }
            }
            .on("") {
                it.forEach {
                    if (it is String) {
                        Log.i("on(\"\")", "$it")
                    }
                }
                requireActivity().runOnUiThread(Runnable {
                    val data = it.get(0) as JSONObject
                    val username: String
                    val message: String
                    try {
                        username = data.getString("username")
                        message = data.getString("message")
                        Log.i("SOCKET", "Username: $username\nMessage: $message")
                    } catch (e: JSONException) {
                        return@Runnable
                    }
                })
            }
    }

    suspend fun withJavalin(address: String, port: Int, oauth: String, nickname: String) {
        val app = Javalin.create().start()
//        app.get("/") { ctx -> ctx.result("Hello World") }

        app.ws("$address:$port") { ws ->
            ws.onConnect { ctx -> println("Connected") }
            ws.onMessage { ctx ->
//                val user = ctx.message<User>(); // convert from json string to object
//                ctx.send(user); // convert to json string and send back
            }
            ws.onClose { ctx -> println("Closed") }
            ws.onError { ctx -> println("Errored") }
        }
    }

    suspend fun ktorSocket(address: String, port: Int, oauth: String, nickname: String) {
        try {
            val socket =
                aSocket(ActorSelectorManager(Dispatchers.IO))
                    .tcp()
                    .connect(InetSocketAddress(address, port))
            val input = socket.openReadChannel()
            val output = socket.openWriteChannel(autoFlush = true)

//            output.writeStringUtf8("pass $oauth")
//            output.writeStringUtf8("nick $nickname")
//            output.writeStringUtf8("join #waterlynn")

            Log.i("ktor log write", "ktorSocket: ")

            val response = input.readUTF8Line(10000)
            Log.i("ktor log response", "$response")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun socketIOFun(address: String, port: Int, oauth: String, nickname: String) {

        val uri = URI.create(address)

        val options: IO.Options =
            IO.Options.builder()
                .setPort(port)
                .build()

        val optionsz = IO.Options.builder()
            .setAuth(singletonMap("paa", oauth))
            .build()

//        val webSocket = IO.socket(uri, options)
        val webSocket = IO.socket("irc.chat.twitch.tv:6667")

        webSocket.connect()

//        webSocket.send("pass $oauth".encodeUtf8())
//        webSocket.send("nick $nickname".encodeUtf8())
//        webSocket.send("join #trucker_dylan".encodeUtf8())

        webSocket.emit("pass", oauth)
        webSocket.emit("nick", nickname)
        webSocket.emit("join", "#trucker_dylan")

        webSocket
            .on(Socket.EVENT_CONNECT) {
                Log.i("SOCKET", "Socket.EVENT_CONNECT: $it")
                it.forEach {
                    Toast.makeText(requireContext(), "Socket connected!", Toast.LENGTH_SHORT).show()
//                    when (it) {
//                        is String -> Log.i("connect is String", "$it")
//                        is Exception -> Log.e("connect is Exception", "${it.printStackTrace()}")
//                    }
                }
            }
            .on(Socket.EVENT_CONNECT_ERROR) {
                Log.i("SOCKET", "Socket.EVENT_CONNECT_ERROR: $it")
                it.forEach {
                    when (it) {
                        is String -> Log.e("error is String", "${it}")
                        is Exception -> Log.e("error is Exception", "${it.printStackTrace()}")
                    }
                }
            }
            .on(Socket.EVENT_DISCONNECT) {
                Log.i("SOCKET", "Socket.EVENT_DISCONNECT: $it")
                it.forEach {
                    when (it) {
                        is String -> Log.i("disconnect is String", "$it")
                        is Exception -> Log.e("disconnect is Exception", "${it.printStackTrace()}")
                    }
                }
            }
            .on("") {
                it.forEach {
                    if (it is String) {
                        Log.i("on(\"\")", "$it")
                    }
                }
                requireActivity().runOnUiThread(Runnable {
                    val data = it.get(0) as JSONObject
                    val username: String
                    val message: String
                    try {
                        username = data.getString("username")
                        message = data.getString("message")
                        Log.i("SOCKET", "Username: $username\nMessage: $message")
                    } catch (e: JSONException) {
                        return@Runnable
                    }
                })
            }

    }

}

class EchoWebSocketListener(address: String, port: Int, val oauth: String) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        webSocket.send("pass $oauth")
//        webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        Log.i("onMessage", "Receiving : $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        Log.i("onMessage", "Receiving bytes : " + bytes.hex())
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        Log.i("onClosed", "Closing : $code / $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        Log.i("onFailure", "Error : " + t.message)
    }

    companion object {
        private const val NORMAL_CLOSURE_STATUS = 1000
    }
}