package com.bluesheets.ui.chat.model

import com.bluesheets.BuildConfig

data class ConnectionUserModel( var id: Int,
                                var username: String,
                                var profileimageurl: String){

    fun getStreamId(): String {
        return "${BuildConfig.FLAVOR}-${id}"
    }
}
