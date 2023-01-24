package com.bluesheets.utils

object AppRequestParams {

    fun login(email: String, password: String): HashMap<String, Any?>{
        var params =  HashMap<String, Any?>()
        params.put("email", email)
        params.put("password", password)
        return params
    }
}