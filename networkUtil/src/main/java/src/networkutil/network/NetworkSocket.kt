package src.networkutil.network

import android.os.Handler
import android.os.Looper
import android.util.Log
import io.socket.client.Ack
import io.socket.client.IO
import io.socket.client.Manager
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import src.networkutil.utilities.NetworkConstant
import src.networkutil.utilities.NetworkGlobalDataHolder
import java.net.URI
import java.util.logging.Level
import java.util.logging.Logger

object NetworkSocket {

    var socket: Socket? = null
    var TAG = NetworkSocket::class.java.simpleName
    var userAuthKey: String? = ""
    var socketNamespace: String? = null
    var connectListner: Emitter.Listener? = null
    var errorListner: Emitter.Listener? = null

    // var connectErrorListner: Emitter.Listener? = null
    // var reciveEvventJoin: Emitter.Listener? = null
    var reciveCommentResponse: Emitter.Listener? = null

    // var reciveViewComment: Emitter.Listener? = null
    var socketDisconnect: Emitter.Listener? = null

    // var likeCountEmitter: Emitter.Listener? = null
    //  var pollEmitter: Emitter.Listener? = null
    //  var webinarReloadEmitter: Emitter.Listener? = null
    private var isReloadEmitterCalled = false

    // var liveUsers: Emitter.Listener? = null
    var roomName: String? = null
    var roomNameLike: String? = null
    var roomNamPoll: String? = null
    var roomNameWebinarReload: String? = null
    var roomNameCommentShowHide: String? = null
    var ifSocketReconnect: Boolean = false
    var isReciveEventAdded: Boolean = false
    var productType: Int? = 0
    var productTypeId: Int? = 0
    var isPingForLiveUser: Boolean = false
    var listMessageIds: MutableList<String> = mutableListOf()
    var reconnectAttempts = 0
    private var handler: Handler? = null
    private var runnable: Runnable? = null
    private const val maxReconnectAttempts = 5

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
            handelCallbacks()

            Log.e(TAG, "AndroidLoggingHandler enabled called")
//            AndroidLoggingHandler.reset(AndroidLoggingHandler())
            Logger.getLogger(Socket::class.java.name).level = Level.FINEST
            Logger.getLogger(io.socket.engineio.client.Socket::class.java.name).level = Level.FINEST
            Logger.getLogger(Manager::class.java.getName()).level = Level.FINEST
        }
    }

    fun startPingPong() {

        return
        var jsonObject = JSONObject()
        jsonObject.put("user_auth", userAuthKey)
        socket?.emit("joinRoom", jsonObject)

        socket?.emit("ping_alert", jsonObject)
        socket?.on(
            "pong_" + this
                .userAuthKey
        ) {
            socket?.emit("ping_alert", jsonObject)
        }
    }

    private fun connectSocket(firstTime: String? = "no") {
        Log.e(TAG, "connectSocket called")

        socket?.connect()?.off()

        socket?.connect()?.on(Socket.EVENT_CONNECT) {
            Log.e(TAG, "EVENT_CONNECT called")

            startPingPong()
            joinRoom(firstTime)
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
                        connectSocket(firstTime)
                        handler = null
                        reconnectAttempts = 0
                    }

                    if (runnable != null) {
                        handler?.postDelayed(runnable!!, randomTime)
                    }
/*
                    handler?.postDelayed({
                        Log.e(TAG,"EVENT_RECONNECT_ERROR postDelayed called")
                        connectSocket(firstTime)
                        handler=null
                        reconnectAttempts=0
                    }, randomTime)
*/
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
            // leaveRoomOnDisconnect()
            // ifSocketReconnect = true;
            // socket?.connect()
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

        // val mininumTime=20*1000
        // val maximumTime=40*1000

        return (mininumTime..maximumTime).random().toLong()
    }

    fun joinRoom(
        roomName: String?,
        authKey: String?,
        productType: Int?,
        productTypeId: Int?,
        roomNameLike: String?,
        roomNamPoll: String?,
        isPingForLiveUser: Boolean = false,
        roomNameWebinarReload: String?,
        roomNameCommentShowHide: String?,
        firstTime: String? = "no"
    ) {
        Log.e(TAG, "$authKey joinRoom roomName $roomName")
        Log.e(TAG, "joinRoom roomNameForLike $roomNameLike")
        Log.e(TAG, "joinRoom roomNamPoll $roomNamPoll")
        Log.e(TAG, "joinRoom firstTime $firstTime")
        Log.e(TAG, "joinRoom dapputils")
        isDisconnectExplicit = false
        this.userAuthKey = authKey
        this.roomName = roomName
        this.isPingForLiveUser = isPingForLiveUser
        // this.roomNameLike=roomNameLike
        this.roomNameLike = NetworkConstant.SOCKET_LIKE_GLOBAL
        this.roomNamPoll = roomNamPoll
        this.roomNameWebinarReload = roomNameWebinarReload
        this.roomNameCommentShowHide = roomNameCommentShowHide
        this.productType = productType
        this.productTypeId = productTypeId
        if (socket?.connected() == true) {
            joinRoom(firstTime)
        } else {
            connectSocket(firstTime)
        }
    }

    private fun resetReconnection() {
        reconnectAttempts = 0
        if (runnable != null)
            handler?.removeCallbacks(runnable!!)

        handler = null
    }

    /*fun configure(namespace: String?, authKey: String?, roomName: String?) {

        this.roomName = roomName
        socketNamespace = namespace
        userAuthKey = authKey
        handelCallbacks()
        socket.let {
            it!!.connect()
                    .on(Socket.EVENT_CONNECT) {
                        Log.d("SignallingClient", "Socket connected!!!!!")


                    }
        }


    }

    fun reConnect() {

        if (socketNamespace != null && userAuthKey != null)
            configure(socketNamespace, userAuthKey, roomName)
    }*/

    fun handelCallbacks() {

        connectListner = Emitter.Listener {
            Log.e(TAG, "handelCallbacks called")
            joinRoom()
            NetworkGlobalDataHolder.socketCallBackListener?.onConnect(true)
        }

        socketDisconnect = Emitter.Listener { args ->
        }

        socket?.on(
            Socket.EVENT_ERROR,
            Emitter.Listener { args ->

                NetworkGlobalDataHolder.socketCallBackListener?.onConnect(false)
                if (listMessageIds != null && listMessageIds.isNotEmpty()) {
                    for (ids in listMessageIds) {
                        NetworkGlobalDataHolder.liveCommentStatusCallbackListener?.onCommentFailed(
                            ids,
                            productTypeId!!
                        )
                    }
                    listMessageIds.clear()
                }
            }
        )
        // socket?.on(Socket.EVENT_CONNECT, connectListner)
        socket?.on(Socket.EVENT_DISCONNECT, socketDisconnect)
    }

    var isDisconnectExplicit: Boolean = true
    fun leaveRoom() {
//        var jsonObject = JSONObject()
//        jsonObject.put("roomname", roomName)
//        jsonObject.put("user_auth", userAuthKey)
//        Socket.reciveViewComment
        try {

            socket?.off("comment_v2")
            socket?.off(NetworkConstant.SOCKET_LIVE_GLOBAL_EMIT)
            socket?.off(roomNameLike)
            socket?.off(roomNamPoll)
            socket?.off(roomNameWebinarReload)
            socket?.off(roomNameCommentShowHide)

//            socket?.off(roomName, reciveViewComment)
//            socket?.off(_root_ide_package_.src.dcapputils.utilities.DCConstant.SOCKET_LIVE_GLOBAL_EMIT, liveUsers)
//            socket?.off(roomNameLike, likeCountEmitter)
//            socket?.off(roomNamPoll, pollEmitter)
//            socket?.off(roomNameWebinarReload, webinarReloadEmitter)
            isDisconnectExplicit = true
            socket?.connect()?.off()
            socket?.disconnect()
            resetReconnection()
            isReciveEventAdded = false
            isReloadEmitterCalled = false
            Log.e(TAG, " call leaveRoom")
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    fun leaveRoomOnDisconnect() {
//        var jsonObject = JSONObject()
//        jsonObject.put("roomname", roomName)
//        jsonObject.put("user_auth", userAuthKey)
//        Socket.reciveViewComment
        try {

            socket?.off("comment_v2")
            socket?.off(NetworkConstant.SOCKET_LIVE_GLOBAL_EMIT)
            socket?.off(roomNameLike)
            socket?.off(roomNamPoll)
            socket?.off(roomNameWebinarReload)
            socket?.off(roomNameCommentShowHide)

//            socket?.off(roomName, reciveViewComment)
//            socket?.off(_root_ide_package_.src.dcapputils.utilities.DCConstant.SOCKET_LIVE_GLOBAL_EMIT, liveUsers)
//            socket?.off(roomNameLike, likeCountEmitter)
//            socket?.off(roomNamPoll, pollEmitter)
//            socket?.off(roomNameWebinarReload, webinarReloadEmitter)
            //  isDisconnectExplicit = true
            // socket?.connect()?.off()
            // socket?.disconnect()
            // resetReconnection()
            isReciveEventAdded = false
            // isReloadEmitterCalled = false
            Log.e(TAG, " call leaveRoom")
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun joinRoom(firstTime: String? = "no") {
        Log.e(TAG, "joinRoom isReciveEventAdded" + isReciveEventAdded + "firstTime" + firstTime)

        if (socket?.hasListeners("comment_v2") == false) {
            isReciveEventAdded = false
        }
        if (isReciveEventAdded == true) {
        } else {
            socket?.on(
                "comment_v2",
                Emitter.Listener { args ->

                    if (args != null && args?.isNotEmpty() == true) {
                        Log.e(TAG, "call reciveViewComment ${args[args?.size!! - 1]?.toString()}")

                    /*  S1	comment id
                      S2	product type
                      S3	product id
                      S4	comment
                      S5	unique code
                      S6	User register name
                      S7	custom id
                      S8	profile pic
                      S9	commented by
                      S10	speciality name
                      S11	is reported flag*/

                        val jsonArray = JSONObject(args[args.size - 1].toString()).optJSONArray("data")
                        Log.e(TAG, "call reciveViewComment jsonObject $jsonArray")

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val keys: Iterator<String> = jsonObject.keys()
                            val finalObject = JSONObject()
                            val userObject = JSONObject()

                            while (keys.hasNext()) {
                                val key = keys.next()
                                val value = jsonObject.get(key)
                                Log.e(TAG, "call reciveViewComment key $key value $value")
                                if (key.equals("S1")) {
                                    finalObject.put("comment_id", value)
                                } else if (key.equals("S2")) {
                                    finalObject.put("product_type", value)
                                } else if (key.equals("S3")) {
                                    finalObject.put("product_id", value)
                                } else if (key.equals("S4")) {
                                    finalObject.put("comment", value)
                                } else if (key.equals("S5")) {
                                    finalObject.put("unique_code", value)
                                } else if (key.equals("S6")) {
                                    userObject.put("full_name", value)
                                } else if (key.equals("S7")) {
                                    userObject.put("custom_id", value)
                                } else if (key.equals("S8")) {
                                    userObject.put("profile_pic", value)
                                } else if (key.equals("S9")) {
                                    finalObject.put("commented_by", value)
                                } else if (key.equals("S10")) {
                                    userObject.put("speciality_name", value)
                                } else if (key.equals("S11")) {
                                    finalObject.put("is_reported", value)
                                } else if (key.equals("S12")) {
                                    userObject.put("permission", value)
                                }
                            }
                            finalObject.put("user_profile", userObject)

                            Log.e(TAG, "call reciveViewComment finalObject $finalObject")

                            val dataObject = JSONObject()

                            val commentObject = JSONObject()
                            commentObject.put("comment", finalObject)
                            dataObject.put("data", commentObject)

                            NetworkGlobalDataHolder.socketCallBackListener?.onCommentViewed(dataObject)
                        }

/*
                    try {
                        var jsonResponse = JSONObject(args[args?.size!! - 1].toString())
                        var jsonObject = JSONObject(jsonResponse.optString("data"))
                        var commentJson = jsonObject.optJSONObject("comment")
                        if (commentJson == null) {
                            var arrayJson = jsonObject.optJSONArray("comment")
                            if (arrayJson != null && arrayJson?.length() > 0) {
                                commentJson = arrayJson.optJSONObject(0)
                            }
                        }
                        if (commentJson != null) {
                            var comentId = commentJson.optInt("comment_id")
                            acknowledgeCommmentReceived(comentId)
                        }
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
*/
                    } else
                        Log.e(TAG, " call reciveViewComment ")
                }
            )
            getLikeCount()
            if (isPingForLiveUser)
                getLiveUser()
            getPoll()
            getWebinarReload()
            getShowHideComment()
            isReciveEventAdded = true
        }

        var jsonObject = JSONObject()
        /* jsonObject.put("roomname", roomName)
         jsonObject.put("user_auth", userAuthKey)
         jsonObject.put("product_type", productType)
         jsonObject.put("product_type_id", productTypeId) */

        /*  A1	user auth key
          A2	access token //not using
          S1	room name
          S2	product type
          S3	product type id
          S4	is new join request flag*/

        jsonObject.put("A1", userAuthKey)
        jsonObject.put("S1", roomName)
        jsonObject.put("S2", productType)
        jsonObject.put("S3", productTypeId)
        jsonObject.put("S4", firstTime)

        //   2000 ---> live case for self join
        // 2002---> live case and like case

        Log.e(TAG, "joinRoom jsonObject" + jsonObject)
        socket?.emit(
            "joinRoom", jsonObject,
            object : Ack {
                override fun call(vararg args: Any?) {

                    try {
                        Log.e(TAG, "joinRoom emit call ${args[args?.size!! - 1]}")

                        val jsonResponse = JSONObject(args[args?.size!! - 1].toString())

                        val code = jsonResponse.optInt("code")

                        when (code) {
                            2002 -> {
                                val liveDataObject = jsonResponse.optJSONObject("data")
                                val likeDataObject = jsonResponse.optJSONObject("like_data")
                                parsingLiveObject(liveDataObject)
                                parsingLikeObject(likeDataObject)
                            }
                            2000 -> {
                                val liveDataObject = jsonResponse.optJSONObject("data")
                                parsingLiveObject(liveDataObject)
                            }
                        }
                    } catch (ex: java.lang.Exception) {
                        ex.printStackTrace()
                    }
                }
            }
        )
    }

    private fun parsingLiveObject(dataObject: JSONObject) {
        val productId = dataObject.optInt("S3")
        val value = dataObject.optString("V1")
        val value2 = dataObject.optInt("V2")

        Log.e(TAG, "parsingLiveObject productId" + productId)
        Log.e(TAG, "parsingLiveObject value" + value)
    }

    private fun parsingLikeObject(likeDataObject: JSONObject) {

        val productId = likeDataObject.optInt("S3")
        val value = likeDataObject.optInt("V1")

        Log.e(TAG, "parsingLikeObject productId" + productId)
        Log.e(TAG, "parsingLikeObject value" + value)

        NetworkGlobalDataHolder.socketCallBackListener?.onLikeCountUpdate(value, productId)
    }

    private fun acknowledgeCommmentReceived(commentId: Int?) {
        return
/*
        if (isReciveEventAdded == true) {


        } else {
            socket?.on(roomName, Emitter.Listener { args ->

                if (args != null && args?.isNotEmpty()) {
                    Log.e(TAG, " call reciveViewComment ${args[args?.size!! - 1]?.toString()}")
                    DCGlobalDataHolder.socketCallBackListener?.onCommentViewed(args[args?.size!! - 1])
                    try {
                        var jsonResponse = JSONObject(args[args?.size!! - 1].toString())
                        var jsonObject = JSONObject(jsonResponse.optString("data"))
                        var commentJson = jsonObject.optJSONObject("comment")
                        if (commentJson == null) {
                            var arrayJson = jsonObject.optJSONArray("comment")
                            if (arrayJson != null && arrayJson?.length() > 0) {
                                commentJson = arrayJson.optJSONObject(0)
                            }
                        }
                        if (commentJson != null) {
                            var comentId = commentJson.optInt("comment_id")
                            acknowledgeCommmentReceived(comentId)
                        }
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                } else
                    Log.e(TAG, " call reciveViewComment ")
            })
            isReciveEventAdded = true

        }
*/
/*
        var jsonObject = JSONObject()
        jsonObject.put("user_auth", userAuthKey)
        jsonObject.put("comment_id", commentId)
        jsonObject.put("product_type", productType)
        jsonObject.put("product_type_id", productTypeId)
        socket?.emit("comment_Receive_Ack", jsonObject, object : Ack {
            override fun call(vararg args: Any?) {
                Log.e(TAG, "emit call $args")
            }
        })
        Log.e(TAG, "acknowledgeCommmentReceived $commentId")*/
    }

    fun getLiveUser() {
        Log.e(TAG, "getLiveUser roomName" + roomName)
        Log.e(TAG, "getLiveUser socket" + roomName)
        Log.e(TAG, "getLiveUser liveUsers")
        socket?.on(
            NetworkConstant.SOCKET_LIVE_GLOBAL_EMIT,
            Emitter.Listener { args ->

                // {"product_type":27,"product_id":401,"value":"16+"}

                if (args != null && args?.isNotEmpty() == true) {
                    try {
                        val jsonResponse = JSONObject(args[args?.size!! - 1].toString())
                        Log.e(TAG, "getLiveUser jsonResponse" + jsonResponse)
                        val productId = jsonResponse.optInt("S3")
                        val value = jsonResponse.optString("V1")

                        Log.e(TAG, "getLiveUser productId" + productId)
                        Log.e(TAG, "getLiveUser value" + value)

                        val value2 = jsonResponse.optInt("V2")
                    } catch (t: Throwable) {
                        t.printStackTrace()
                    }

                    // Log.e(TAG, " call liveUsers ${args[args?.size!! - 1]?.toString()}")
                } else
                    Log.e(TAG, " call liveUsers ")
            }
        )
    }

    fun getShowHideComment() {
        Log.e(TAG, "getShowHideComment roomNameCommentShowHide" + roomNameCommentShowHide)
        socket?.on(
            roomNameCommentShowHide,
            Emitter.Listener { args ->
                if (args != null && args?.isNotEmpty() == true) {
                    try {
                        val jsonResponse = JSONObject(args[args?.size!! - 1].toString())
                        val productId = jsonResponse.optInt("product_id")
                        val productType = jsonResponse.optInt("product_type")
                        val commentUniqueCode = jsonResponse.optString("unique_code")
                        val action = jsonResponse.optString("action")

                        Log.e(TAG, "getShowHideComment productId" + productId)
                        Log.e(TAG, "getShowHideComment productType" + productType)
                        Log.e(TAG, "getShowHideComment commentUniqueCode" + commentUniqueCode)
                        Log.e(TAG, "getShowHideComment action" + action)
                    } catch (t: Throwable) {
                        t.printStackTrace()
                    }

                    Log.e(TAG, " call getShowHideComment ${args[args?.size!! - 1]?.toString()}")
                } else
                    Log.e(TAG, " call getShowHideComment ")
            }
        )
        // mockShowHide()
    }

    private fun mockShowHide() {
        Log.e(TAG, "mockShowHide called")
        var productId = 717
        var commentUniqueCode = ""

        Handler().postDelayed(
            {
                // "comment": "comment detail"
                commentUniqueCode = "80f1c5b2086ae05c2721e9d3b251f9371598946748175-717"
            },
            5000
        )

        Handler().postDelayed(
            {
                // "comment": "comment detail"
                commentUniqueCode = "80f1c5b2086ae05c2721e9d3b251f9371598946748175-717"
            },
            10000
        )

        Handler().postDelayed(
            {
                // "comment": "comment  full"
                commentUniqueCode = "80f1c5b2086ae05c2721e9d3b251f9371598946735349-717"
            },
            20000
        )

        Handler().postDelayed(
            {
                // "comment": "comment  full"
                commentUniqueCode = "80f1c5b2086ae05c2721e9d3b251f9371598946735349-717"
            },
            35000
        )

        /*  Handler().postDelayed({
              // "comment": "comment detail"
              commentUniqueCode="80f1c5b2086ae05c2721e9d3b251f9371598946748175-717"
              DCGlobalDataHolder.onCommentShowHideListener?.updateCommentState(productId, 27, commentUniqueCode, "show")
          },25000)


        */
    }

    fun getLikeCount() {
        Log.e(TAG, "getLikeCount called" + roomNameLike)
        socket?.on(
            roomNameLike,
            Emitter.Listener { args ->
                try {
                    //  val count= args[args?.size!! - 1] as Int
                    Log.e(TAG, "likeCountEmitter ${args[args?.size!! - 1]?.toString()}")
                    val jsonResponse = JSONObject(args[args?.size!! - 1].toString())
                    Log.e(TAG, "likeCountEmitter jsonResponse $jsonResponse")
                /*  val productId=jsonResponse.optInt("product_id")
                  val value=jsonResponse.optInt("value")
  */
                    val productId = jsonResponse.optInt("S3")
                    val value = jsonResponse.optInt("V1")

                    Log.e(TAG, "likeCountEmitter productId" + productId)
                    Log.e(TAG, "likeCountEmitter value" + value)

                    NetworkGlobalDataHolder.socketCallBackListener?.onLikeCountUpdate(value, productId)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        )
    }

    fun getPoll() {
        Log.e(TAG, "getPoll called" + roomNamPoll)
        Log.e(TAG, "getPoll pollEmitter")
        socket?.on(
            roomNamPoll,
            Emitter.Listener { args ->
                try {
                    //  val count= args[args?.size!! - 1] as Int
                    Log.e(TAG, "pollEmitter args" + args)
                    Log.e(TAG, " call pollEmitter ${args[args?.size!! - 1]?.toString()}")
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        )
    }

    fun getWebinarReload() {
        Log.e(TAG, "getWebinarReload called" + roomNameWebinarReload)
        Log.e(TAG, "getWebinarReload webinarReloadEmitter")
        socket?.on(
            roomNameWebinarReload,
            Emitter.Listener { args ->
                try {
                    //  val count= args[args?.size!! - 1] as Int
                    Log.e(TAG, "webinarReloadEmitter args" + args)
                    Log.e(TAG, " call webinarReloadEmitter ${args[args?.size!! - 1]?.toString()}")
                    if (!isReloadEmitterCalled) {
                        isReloadEmitterCalled = true
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        )
    }

    fun disconnectSocket() {

        // socket?.disconnect()
    }

    fun addComment(
        roomName: String?,
        comment: String?,
        commentId: String?,
        productType: Int?,
        productTypeId: Int?
    ) {
        Log.e(TAG, "addComment isConnected ${socket?.connected()}")
        if (socket != null) {
            if (!socket?.connected()!!) {
                Log.e(TAG, "addComment if ")
                connectSocket()
                NetworkGlobalDataHolder.liveCommentStatusCallbackListener?.onCommentFailed(
                    commentId,
                    productTypeId!!
                )
            } else {
                Log.e(TAG, "addComment else ")
                var jsonObject = JSONObject()
                /*  jsonObject.put("roomname", roomName)
                  jsonObject.put("user_auth", userAuthKey)
                  jsonObject.put("comment", comment)
                  jsonObject.put("product_type", productType)
                  jsonObject.put("product_type_id", productTypeId)
                  jsonObject.put("unique_code", commentId) */

                /*  S1	Room Name
                  S2	product type
                  S3	product id
                  S4	comment
                  S5	unique code
                  A1	user auth key*/

                jsonObject.put("A1", userAuthKey)
                jsonObject.put("S1", roomName)
                jsonObject.put("S2", productType)
                jsonObject.put("S3", productTypeId)
                jsonObject.put("S4", comment)
                jsonObject.put("S5", commentId)
                Log.e(TAG, "emit data $jsonObject")
                listMessageIds.add(commentId!!)

                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        if (listMessageIds != null && listMessageIds.isNotEmpty()) {
                            for (ids in listMessageIds) {
                                NetworkGlobalDataHolder.liveCommentStatusCallbackListener?.onCommentFailed(
                                    ids,
                                    productTypeId!!
                                )
                            }
                            listMessageIds.clear()
                        }
                    },
                    5000
                )
//        }
                socket?.emit(
                    "comment_post", jsonObject,
                    object : Ack {
                        override fun call(vararg args: Any?) {
                            for ((index, data) in listMessageIds.withIndex()) {
                                if (commentId.equals(data)) {
                                    listMessageIds.removeAt(index)
                                    break
                                }
                            }
                            Log.e(TAG, "emit call $args")
                            if (args != null && args.isNotEmpty()) {
                                var response = args.get(0)
                                Log.e(TAG, "response $response")
                                if (response != null) {
                                    try {
                                        var jsonObject = JSONObject(response.toString())
                                        var code = jsonObject.optInt("code")
                                        var success = jsonObject.optBoolean("success")
                                        var error = jsonObject.optBoolean("error")
                                        var uniqueCode = jsonObject.optString("unique_code")

                                        Log.e(TAG, "comment_post success" + success)
                                        Log.e(TAG, "comment_post error" + error)
                                        Log.e(TAG, "comment_post uniqueCode" + uniqueCode)
                                        Log.e(TAG, "comment_post code" + code)

                                        if (success && code == NetworkConstant.RESPONSE_SOCKET_COMMENT_ALREADY_SENT) {
                                            Log.e(TAG, "comment_post already sent")
                                            var commentNewId = commentId
                                            if (uniqueCode != null && !uniqueCode.isNullOrBlank())
                                                commentNewId = uniqueCode
                                            NetworkGlobalDataHolder.liveCommentStatusCallbackListener?.onCommentAlreadySent(
                                                commentNewId,
                                                productTypeId!!
                                            )
                                        } else if (success && code == NetworkConstant.RESPONSE_SOCKET_COMMENT_SUCCESS) {
                                            Log.e(TAG, "comment_post success")
                                            var commentNewId = commentId
                                            if (uniqueCode != null && !uniqueCode.isNullOrBlank())
                                                commentNewId = uniqueCode
                                            NetworkGlobalDataHolder.liveCommentStatusCallbackListener?.onCommentSuccess(
                                                commentNewId,
                                                productTypeId!!
                                            )
                                        } else if (error) {
                                            Log.e(TAG, "comment_post failed")
                                            var commentNewId = commentId
                                            if (uniqueCode != null && !uniqueCode.isNullOrBlank())
                                                commentNewId = uniqueCode
                                            NetworkGlobalDataHolder.liveCommentStatusCallbackListener?.onCommentFailed(
                                                commentNewId,
                                                productTypeId!!
                                            )
                                        }

/*
                                    when (code) {
                                        DCConstant.RESPONSE_SOCKET_COMMENT_SUCCESS -> {
                                            var commentNewId = commentId
                                            if (uniqueCode != null && !uniqueCode.isNullOrBlank())
                                                commentNewId = uniqueCode
                                            DCGlobalDataHolder.liveCommentStatusCallbackListener?.onCommentSuccess(commentNewId,productTypeId!!)

                                        }
                                        DCConstant.RESPONSE_SOCKET_COMMENT_FAILED -> {
                                            var commentNewId = commentId
                                            if (uniqueCode != null && !uniqueCode.isNullOrBlank())
                                                commentNewId = uniqueCode
                                            DCGlobalDataHolder.liveCommentStatusCallbackListener?.onCommentFailed(commentNewId,productTypeId!!)
                                        }
                                        DCConstant.RESPONSE_SOCKET_COMMENT_ALREADY_SENT -> {
                                            var commentNewId = commentId
                                            if (uniqueCode != null && !uniqueCode.isNullOrBlank())
                                                commentNewId = uniqueCode
                                            DCGlobalDataHolder.liveCommentStatusCallbackListener?.onCommentAlreadySent(commentNewId,productTypeId!!)
                                        }
                                    }
*/
                                    } catch (e: Throwable) {
                                        e.printStackTrace()
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }

    fun isDisconnected(): Boolean {
        if (socket != null)
            return socket?.connected()?.not()!!
        else
            return true
    }
}
