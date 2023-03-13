package com.bluesheets.utils

object AppRequestParams {

    // Sign In
    fun login(email: String, password: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("email", email)
        params.put("password", password)
        return params
    }

    // Sign Up
    fun register(
        name: String,
        email: String,
        phoneNumber: String,
        password: String
    ): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        var infoParams = HashMap<String, Any?>()
        infoParams.put("registrationType", "mobile")
        infoParams.put("phoneNumber", phoneNumber)
        infoParams.put("fullName", name)
        params.put("email", email)
        params.put("password", password)
        params.put("info", infoParams)
        return params
    }

    // Create/Edit Organization
    fun registerOrg(name: String, description: String, workspaceId: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("name", name)
        params.put("description", description)
        params.put("workspaceId", workspaceId)
        return params
    }

    // Get Organizations for WorkSpaces
    fun getOrganizationsForWorkSpace(
        access_token: String,
        workspaceId: String
    ): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("access_token", access_token)
        params.put("filter", "{\"where\":{\"workspaceId\":$workspaceId}}")
        return params
    }

    // Login To Organizations
    fun loginToOrganizations(access_token: String, organizationId: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("access_token", access_token)
        params.put("organizationId", organizationId)
        return params
    }

    // Create/Edit Workspace
    fun registerWorkSpace(name: String, description: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("name", name)
        params.put("description", description)
        return params
    }

    // Login To WorkSpace
    fun loginToWorkSpace(access_token: String, workspaceId: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("access_token", access_token)
        params.put("workspaceId", workspaceId)
        return params
    }

    // Get WorkSpace For Users
    fun getWorkSpaceForUser(filter: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("filter", filter)
        return params
    }

    // Upload WorkSpace Logo
    //    fun uploadWorkSpaceLogo(access_token: String): HashMap<String, Any?> {
    //        var params = HashMap<String, Any?>()
    //        params.put("filter", "{\"searchValue\":\"\"}")
    //        params.put("access_token", access_token)
    //        return params
    //    }


    // User Related API

    // Get User

    // LogOut User
    fun userLogOut(access_token: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("access_token", access_token)
        return params
    }

    // Upload Profile Image of USER.
    fun userProfileImage(access_token: String, image: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("access_token", access_token)
        params.put("image", image)
        return params
    }

    // Forgot Password
    fun userForgotPassword(email: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("email", email)
        return params
    }

    // Accept Invite With Code
    fun userInviteCode(code: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("code", code)
        return params
    }


    // Documents Related APIs

    // Delete Document
    fun deleteDocument(organizationToken: String, access_token: String): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("access_token", access_token)
        params.put("organization", organizationToken)
        return params
    }

    // Get Documents
    fun getAllDocuments(
        access_token: String,
        organizationToken: String,
        filter: String
    ): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("access_token", access_token)
        params.put("organization", organizationToken)
        params.put("filter", filter)
        return params
    }

    fun getUses(): HashMap<String, Any?> {
        var params = HashMap<String, Any?>()
        params.put("contactsLevel", "workspace")
        return params
    }

    fun uploadFiles(
        source: String,
        size: String,
        title: String,
        channelID: String,
        userDocID: String
    ): HashMap<String, Any?> {
        val files = arrayListOf<HashMap<String, Any?>>()
        val file = hashMapOf<String, Any?>(
            "src" to source,
            "size" to size,
            "title" to title
        )
        files.add(file)

        val data = hashMapOf<String, Any?>(
            "channelId" to channelID,
            "sentFromChat" to true,
            "userDocumentTypeId" to userDocID
        )

        val params = hashMapOf<String, Any?>(
            "files" to files,
            "data" to data
        )

        return params
    }

}