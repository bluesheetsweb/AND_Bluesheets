package src.networkutil.network

import src.networkutil.utilities.NetworkConstant

data class NetworkRequestHeader(
    var authorization: String? = "",
    var organizationToken: String = "",
    var workspaceToken: String = "",
    var contentType: String = "",
    var accept: String = "*/*"
)
