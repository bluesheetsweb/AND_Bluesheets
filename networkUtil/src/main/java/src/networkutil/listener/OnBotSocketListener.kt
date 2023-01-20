package src.networkutil.listener

interface OnBotSocketListener {
    fun connected()
    fun disconnected()
    fun reConnecting()
    fun onResponse(data: Any?)
}
