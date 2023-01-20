package src.networkutil.network

import src.networkutil.utilities.NetworkConstant

data class NetworkPreDefineResponse(
    var parentKey: String = "",
    var statusKey: String = NetworkConstant.RESPONSE_FIELD_STATUS,
    var codeKey: String = NetworkConstant.RESPONSE_FIELD_CODE,
    var successCode: Int = NetworkConstant.RESPONSE_CODE_SUCCESS,
    var successArrayCode: List<Int> = mutableListOf(),
    var messageKey: String = NetworkConstant.RESPONSE_FIELD_MESSAGE,
    var dataKey: String = NetworkConstant.RESPONSE_FIELD_DATA,
    var dataKeyTypeObject: Boolean = true,
    var dataKeyErrorTypeObject: Boolean = true,
    var isToCheckStatusValueType: Boolean = false,
    var isThirdPartyApi: Boolean = false,
    var actionKey: String = NetworkConstant.RESPONSE_FIELD_ACTION,
    var errorKey: String = NetworkConstant.RESPONSE_FIELD_ERROR,
    var tootTips: String = NetworkConstant.RESPONSE_FIELD_TOOL_TIPS
)
