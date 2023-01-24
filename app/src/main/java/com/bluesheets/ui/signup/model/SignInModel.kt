package com.bluesheets.ui.signup.model

data class SignInModel( var id: String,
                        var email: String,
                        var userName: String,
                        var userToken: String,
                        var highestRole: String,
                        var mobileToken: String,
                        var donePreboarding: Boolean,
                        var loggedIn: Boolean,
                        var userAvatar: String,
                        var phoneNumber: String,
                        var tfaEnabled: Boolean,
                        var tfaRequest: String,
                        var workspace: WorkspaceItem,
                        var organization: OrganizationItem
)


data class WorkspaceItem(
    var token: String,
    var name: String,
    var description: String,
    var userId: String,
    var id: String,
    var permission: String,
    var isLoggedIn: Boolean,
    var logoUrl: String)

data class OrganizationItem(
    var token: String,
    var name: String,
    var description: String,
    var userId: String,
    var workspaceId: String,
    var id: String,
    var permission: String,
    var isLoggedIn: Boolean)
