package src.wrapperutil.utilities

import android.content.Context

object WrapperGlobalDataHolder {

    private var TAG = WrapperGlobalDataHolder::class.java.simpleName
    var isErrorScreenShown: Boolean? = false
    var applicationContext: Context? = null

    var globalMessageForNoInternet: String? = "Please check network connectivity and retry."
    var globalMessageForError: String? = ""
    var globalMessageForNotFound: String? = ""
    var globalTitleForNotFound: String? = ""
    var globalTitleForError: String? = ""
    var globalTitleForNoInternet: String? = "No Internet"
    var globalRetryButton: String? = "Retry"
    var globalOkButton: String? = ""
}
