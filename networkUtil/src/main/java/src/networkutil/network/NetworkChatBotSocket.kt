package src.networkutil.network

import android.os.Handler
import android.os.Looper
import android.util.Log
import io.socket.client.Ack
import io.socket.client.IO
import io.socket.client.Manager
import io.socket.client.Socket
import org.json.JSONObject
import src.networkutil.AndroidLoggingHandler
import src.networkutil.listener.OnBotSocketListener
import java.net.URI
import java.util.logging.Level
import java.util.logging.Logger

object NetworkChatBotSocket {

    var socket: Socket? = null
    var TAG = NetworkChatBotSocket::class.java.simpleName
    var userAuthKey: String? = ""
    var version: Int? = 0
    var appVersion: String? = null
    var lang: String? = null
    var deviceType: String? = null
    var timeZone: String? = null
    var ifSocketReconnect: Boolean = false
    var productType: Int? = 0
    var productTypeId: Int? = 0
    var reconnectAttempts = 0
    private var handler: Handler? = null
    private var runnable: Runnable? = null
    private const val maxReconnectAttempts = 5
    private var listener: OnBotSocketListener? = null

    fun makeSocket(namespace: String?) {
        Log.e(TAG, "makeSocket called" + socket)
        if (socket == null) {
            var url = URI.create(InitNetworkUtils.socket_path + namespace)
            var option = IO.Options()
            option.forceNew = false
            option.reconnection = true
            option.reconnectionDelay = 5000
            option.reconnectionAttempts = maxReconnectAttempts
            option.upgrade = false
            option.transports = arrayOf("websocket")
            socket = IO.socket(url, option)
            Log.e(TAG, "AndroidLoggingHandler enabled called")
            AndroidLoggingHandler.reset(AndroidLoggingHandler())
            Logger.getLogger(Socket::class.java.name).level = Level.FINEST
            Logger.getLogger(io.socket.engineio.client.Socket::class.java.name).level = Level.FINEST
            Logger.getLogger(Manager::class.java.getName()).level = Level.FINEST
        }
    }

    fun initData(
        userAuthKey: String?,
        version: Int? = 0,
        lang: String?,
        appVersion: String?,
        deviceType: String?,
        timeZone: String?,
        productTypeId: Int,
        productType: Int
    ) {
        this.userAuthKey = userAuthKey
        this.version = version
        this.lang = lang
        this.appVersion = appVersion
        this.productType = productType
        this.productTypeId = productTypeId
        this.deviceType = deviceType
        this.timeZone = timeZone
    }

    fun attachListener(listener: OnBotSocketListener?) {
        this.listener = listener
    }

    fun initiateOption(
        nextNode: Int? = 0,
        previousNode: Int? = 0,
        optionValue: String? = "",
        optionId: Int? = 0
    ) {
        Log.e(TAG, "initiateOption isConnected ${socket?.connected()}")
        if (socket != null) {
            if (!socket?.connected()!!) {
                Log.e(TAG, "initiateOption if ")
                listener?.disconnected()
//                connectSocket()
            } else {
                Log.e(TAG, "initiateOption else ")
                var jsonObject = JSONObject()
                jsonObject.put("userauth", userAuthKey)
                jsonObject.put("version", version)
                jsonObject.put("lang", lang)
                jsonObject.put("appVersion", appVersion)
                jsonObject.put("deviceType", deviceType)
                jsonObject.put("productId", productTypeId)
                jsonObject.put("productType", productType)
                jsonObject.put("timezone", timeZone)
                jsonObject.put("nextNode", nextNode)
                jsonObject.put("optionValue", optionValue)
                jsonObject.put("optionId", optionId)
                jsonObject.put("previousNode", previousNode)
                Log.e(TAG, "emit data $jsonObject")
//        }
                socket?.emit(
                    "emit_bot_action", jsonObject,
                    object : Ack {
                        override fun call(vararg args: Any?) {

                            Log.e(TAG, "emit call $args")
                            if (args != null && args.isNotEmpty()) {
                                var response = args.get(0)
                                Log.e(TAG, "response $response")
                                listener?.onResponse(response)
                            }
                        }
                    }
                )
            }
        }
    }

    fun connectSocket(isToNotifyListener: Boolean = true) {
        Log.e(TAG, "connectSocket called")

        socket?.connect()?.off()

        socket?.connect()?.on(Socket.EVENT_CONNECT) {
            Log.e(TAG, "EVENT_CONNECT called")
//            initiateOption(nextNode=nextNode,previousNode=previousNode,
//            optionValue=optionValue,optionId =optionId)
            if (isToNotifyListener)
                listener?.connected()
            resetReconnection()
        }

        socket?.on(Socket.EVENT_RECONNECT) {
            Log.e(TAG, "EVENT_RECONNECT called $it")
            ifSocketReconnect = true
        }
        socket?.on(Socket.EVENT_RECONNECT_ATTEMPT) {
            Log.e(TAG, "EVENT_RECONNECT_ATTEMPT called $it")
        }
        socket?.on(Socket.EVENT_RECONNECT_ERROR) {
            Log.e(TAG, "EVENT_RECONNECT_ERROR called $it reconnectAttempts $reconnectAttempts")
            ++reconnectAttempts

            if (reconnectAttempts >= maxReconnectAttempts) {
                val randomTime = getRandomReconnectTime()
                Log.e(TAG, "EVENT_RECONNECT_ERROR randomTime" + randomTime)
                if (handler == null) {
                    handler = Handler(Looper.getMainLooper())
                    runnable = Runnable {
                        Log.e(TAG, "EVENT_RECONNECT_ERROR postDelayed called")
//                        connectSocket(nextNode=nextNode,previousNode=previousNode,
//                                optionValue=optionValue,optionId =optionId)
                        listener?.reConnecting()
                        handler = null
                        reconnectAttempts = 0
                    }
                    if (runnable != null) {
                        handler?.postDelayed(runnable!!, randomTime)
                    }
                }
            }
        }
        socket?.on(Socket.EVENT_RECONNECTING) {
            Log.e(TAG, "EVENT_RECONNECTING called $it")
        }
        socket?.on(Socket.EVENT_RECONNECT_FAILED) {
            Log.e(TAG, "EVENT_RECONNECT_FAILED called $it")
        }

        socket?.on(Socket.EVENT_DISCONNECT) {
            Log.e(TAG, "EVENT_DISCONNECT called" + reconnectAttempts)
        }

        socket?.on(Socket.EVENT_CONNECT_ERROR) {
            Log.e(TAG, "EVENT_CONNECT_ERROR called")
        }

        socket?.on(Socket.EVENT_PING) {
            Log.e(TAG, "EVENT_PING called")
        }

        socket?.on(Socket.EVENT_PONG) {
            Log.e(TAG, "EVENT_PONG called")
        }

        socket?.on(Socket.EVENT_CONNECT_TIMEOUT) {
            Log.e(TAG, "EVENT_CONNECT_TIMEOUT called")
        }

        socket?.on(Socket.EVENT_ERROR) {
            Log.e(TAG, "EVENT_ERROR called")
        }

        socket?.on(Socket.EVENT_MESSAGE) {
            Log.e(TAG, "EVENT_MESSAGE called")
        }
    }

    private fun getRandomReconnectTime(): Long {
        val mininumTime = 2 * 60 * 1000
        val maximumTime = 4 * 60 * 1000
        return (mininumTime..maximumTime).random().toLong()
    }

    private fun resetReconnection() {
        reconnectAttempts = 0
        if (runnable != null)
            handler?.removeCallbacks(runnable!!)

        handler = null
    }

    fun isDisconnected(): Boolean {
        if (socket != null)
            return socket?.connected()?.not()!!
        else
            return true
    }

    fun disconnect() {
        try {
            socket?.connect()?.off()
            socket?.disconnect()
            resetReconnection()
            Log.e(TAG, " call disconnect")
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}
