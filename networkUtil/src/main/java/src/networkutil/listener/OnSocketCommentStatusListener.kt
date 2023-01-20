package src.networkutil.listener

interface OnSocketCommentStatusListener {

    fun onCommentSuccess(commentId: String?, productId: Int)
    fun onCommentAlreadySent(commentId: String?, productId: Int)
    fun onCommentFailed(commentId: String?, productId: Int)
    fun onResendComment(comment: Any?, productId: Int)
}
