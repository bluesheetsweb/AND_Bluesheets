package com.bluesheets.utils

object AppRequestParams {

    fun login(email: String, password: String): HashMap<String, Any?>{
        var params =  HashMap<String, Any?>()
        params.put("email", email)
        params.put("password", password)
        return params
    }

    fun register(name: String, email: String, phoneNumber: String, password: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        var infoParams = HashMap<String, Any?>()
        infoParams.put("registrationType","mobile")
        infoParams.put("phoneNumber", phoneNumber)
        infoParams.put("fullName", name)
        params.put("email", email)
        params.put("password", password)
        params.put("info", infoParams)
        return params
    }
}