package src.networkutil.listener

interface OnSocketListener {

    fun onConnect(isConnected: Boolean?) {
    }

    fun onRoomJoined(isJoined: Boolean?) {
    }

    fun onCommentReceived(comment: Any?) {
    }

    fun onCommentViewed(comment: Any?) {
    }

    fun onLikeCountUpdate(likeCount: Int?, productId: Int) {
    }
}
