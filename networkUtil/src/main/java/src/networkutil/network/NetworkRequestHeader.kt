package src.networkutil.network

import src.networkutil.utilities.NetworkConstant

data class NetworkRequestHeader(
    var authorization: String? = "",
    var organizationToken: String = "",
    var workspaceToken: String = "",
    var contentType: String = "application/json",
    var accept: String = "*/*",
    var user_agent: String = "bluesheets-mobile"
)
