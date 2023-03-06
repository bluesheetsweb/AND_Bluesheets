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

    fun registerOrg(name: String, description: String, workspaceId: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()

        params.put("name", name)
        params.put("description", description)
        params.put("workspaceId", workspaceId)
        return params
    }

    fun registerWorkSpace(name: String, description: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("name", name)
        params.put("description", description)
        return params
    }

    fun userLogOut(access_token: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("access_token", access_token)
        return params
    }

    fun userProfileImage(access_token: String, image: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("access_token", access_token)
        params.put("image", image)
        return params
    }

    fun getWorkSpace(access_token: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("filter", "{\"searchValue\":\"\"}")
        params.put("access_token", access_token)
        return params
    }

    fun getOrganizations(workspaceId: String, access_token: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("filter", "{\"where\":{\"workspaceId\":$workspaceId}}")
        params.put("access_token", access_token)
        return params
    }

     fun loginToWorkSpace(workspaceId: String, access_token: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("workspaceId", workspaceId)
        params.put("access_token", access_token)
        return params
    }

     fun loginToOrganizations(organizationId: String, access_token: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("organizationId", organizationId)
        params.put("access_token", access_token)
        return params
    }



}