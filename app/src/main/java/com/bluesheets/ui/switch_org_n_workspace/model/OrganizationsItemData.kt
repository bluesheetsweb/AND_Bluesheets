package com.bluesheets.ui.switch_org_n_workspace.model

data class OrganizationsItemData(
    var token: String = "",
    var name: String = "",
    var description: String = "",
    var userId: String = "",
    var workspaceId: String = "",
    var id: String = "",
    var permission: String = "",
    var isLoggedIn: Boolean = false
)
