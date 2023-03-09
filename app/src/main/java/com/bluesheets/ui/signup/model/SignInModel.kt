package com.bluesheets.ui.signup.model

import com.google.gson.annotations.SerializedName

data class SignInModel( var id: String?,
                        var email: String?,
                        var userId: String?,
                        var userName: String?,
                        var highestRole: String?,
                        var mobileToken: String?,
                        var donePreboarding: Boolean?,
                        var loggedIn: Boolean?,
                        var profileImageUrl: String?,
                        var phoneNumber: String?,
                        var tfaEnabled: Boolean?,
                        var tfaRequest: String?,
                        var workspace: WorkspaceItem?,
                        var organization: OrganizationItem?
)


data class WorkspaceItem(
    var id: String?,
    var name: String?,
    var logoUrl: String?,
    var workspaceId: String?)

data class OrganizationItem(
    var id: String?,
    var name: String?,
    var organizationId: String?)
