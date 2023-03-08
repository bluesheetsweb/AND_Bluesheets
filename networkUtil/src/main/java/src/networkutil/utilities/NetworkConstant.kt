package src.networkutil.utilities

import src.networkutil.network.NetworkApiManager

object NetworkConstant {

    const val PREF_NAME = "pref"
    const val DEVICE_TYPE = "android"
    const val LOCAL_BLANK_MESSAGE_ISSUE = "Issue With local message"
    const val TEXT_MESSAGE_INVALID_REQUEST_TYPE = "Issue With local message"
    const val TEXT_MESSAGE_INVALID_REQUESTING_SERVER = "Issue With Requesting Server"
    const val TEXT_MESSAGE_INVALID_RESPONSE = "Unexpected server response"
    const val TEXT_MESSAGE_UN_HANDLED_RESPONSE = "Unhandled response"
    const val TEXT_MESSAGE_NO_INTERNET = "Please check your network connectivity"

    const val HEADER_AUTH_TOKEN = "user_auth_key"
    const val HEADER_VERSION = "version"
    const val HEADER_APP_VERSION = "app_version"
    const val HEADER_LANG = "lang"
    const val HEADER_DEVICE_TYPE = "device_type"
    const val HEADER_TOKEN_ID = "token_id"
    const val HEADER_AUTH2 = "auth2"
    const val HEADER_AUTH_KEY = "userauthkey"
    const val HEADER_VER = "ver"
    const val HEADER_UDID = "udid"
    const val HEADER_APPVERSION = "appversion"
    const val HEADER_DEVICETYPE = "devicetype"
    const val HEADER_TOKENID = "tokenid"
    const val HEADER_TIME_ZONE = "timezone"
    
    const val HEADER_AUTHORIZATION = "Authorization"
    const val HEADER_X_WORKSPACE_TOKEN = "x-workspace-token"
    const val HEADER_X_ORGANISATION_TOKEN = "organization-token"
    const val HEADER_X_USER_AGENT = "user-agent"
    const val HEADER_ACCEPT = "Accept"
    const val HEADER_CONTENT_TYPE = "Content-Type"

    const val HEADER_CONNECTION = "Connection"
    const val HEADER_ACCEPT_ENCODING = "Accept-Encoding"


    const val PREF_KEY_USER_AUTH_KEY = "user_auth_key"
    const val PREF_KEY_ORG_AUTH_KEY = "org_auth_key"
    const val PREF_KEY_WORKSPACE_AUTH_KEY = "work_auth_key"
    const val PREF_KEY_UUID = "udid"
    const val PREF_KEY_SESSION_BACKGROUND_TIME = "analysticsSession"

    const val RELEASE_VERSION = 12

    const val SQS_TOKEN = "nXRrGi2bbMafA8fp7voIL9CwopSxmGH15MSRBIcG"
    const val HEADER_SQS_X_API_KEY = "x-api-key"
    const val PREF_KEY_SESSION_USER_TOKEN = "sessionUserToken"
    const val PREF_OTP_TOKEN = "pref_otp_token"

    var PREF_USER_USER_NAME = "user_name"
    var PREF_USER_EMAIL_ID = "email_id"
    var PREF_USER_MOBLILE_NO = "mobileno"
    var PREF_USER_CUSTOM_ID = "custom_id"
    var PREF_KEY_CHAT_ID = "chat_id"
    var PREF_USER_TRACK_ID = "track_id"
    const val PREF_USER_JABBER_NAME = "username"
    const val PREF_USER_JABBER_PASSWORD = "password"
    const val PREF_TOKEN_ID = "tokenId"
    const val SOCKET_LIVE_GLOBAL_EMIT = "live_global_emit_v2"
    const val SOCKET_LIKE_GLOBAL = "like_global"

    const val appVersion = "appVersion"
    var version = "version"
    val currentLang = "currentLang"

    const val NETWORK_REQUEST_TIME_OUT = 120

    const val REQUEST_TYPE_GET = 1
    const val REQUEST_TYPE_POST = 2
    const val REQUEST_TYPE_POST_MUTIPART = 3

    const val REQUESTING_SERVER_BASE = 1
//    const val REQUESTING_SERVER_BASE_LATEST = 2
//    const val REQUESTING_SERVER_PUBLICATION = 3
//    const val REQUESTING_SERVER_PUBLICATION_OTHER = 4
//    const val REQUESTING_SERVER_NOTIFY_OLD = 5
//    const val REQUESTING_SERVER_ANALYTICS = 6
//    const val REQUESTING_SERVER_4_1 = 7
//    const val REQUESTING_SERVER_BASE_WITHOUTINTERCEPTOR = 8
//    const val REQUESTING_SERVER_SOCKET = 9
//    const val REQUESTING_SERVER_LOCATION = 10
//    const val REQUESTING_SERVER_WITHOUT_BASE_URL = 11
//    const val REQUESTING_SERVER_NODE_API = 12
//    const val REQUESTING_SERVER_SOCKET_2 = 13
//    const val REQUESTING_SERVER_WITHOUT_BASE_URL_SQS = 14
//    const val REQUESTING_SERVER_WITH_CUSTOM_HEADER = 15

    const val RESPONSE_FIELD_STATUS = "status"
    const val RESPONSE_FIELD_CODE = "code"
    const val RESPONSE_FIELD_MESSAGE = "msg"
    const val RESPONSE_FIELD_DATA = "data"
    const val RESPONSE_FIELD_ERROR = "error"
    const val RESPONSE_FIELD_ACTION = "action"
    const val RESPONSE_FIELD_TOOL_TIPS = "tooltips"

    const val RESPONSE_CODE_SUCCESS = 1
    const val RESPONSE_CODE_ERROR = 0
    const val RESPONSE_CODE_EXIT_APP = 9
    const val RESPONSE_CODE_SHOW_DIALOG = 11
    const val RESPONSE_CODE_INVALID = 999
    const val RESPONSE_CODE_ACTION = 50000
    const val RESPONSE_CODE_EXPIRED = 1014
    const val RESPONSE_CODE_TOKEN_EXPIRED = 1026

    const val RESPONSE_CODE_SUCCESS_CODE = 2000
    const val RESPONSE_CODE_SUCCESS_WITH_NOTE = 2012
    const val RESPONSE_CODE_NO_RECORD = 2001
    const val RESPONSE_CODE_ALREADY_PASSED_CME = 2002
    const val RESPONSE_CODE_USER_PASSED_CME = 2003
    const val RESPONSE_CODE_USER_FAILED_CME = 2004
    const val RESPONSE_CODE_DOMAIN_NAME_AVAILABLE = 2005
    const val RESPONSE_CODE_RE_DIRECT_TO_REGISTRATION = 2006
    const val RESPONSE_CODE_REGISTERED_USER = 2007
    const val RESPONSE_CODE_RESTRICTED = 1005
    const val RESPONSE_CODE_WHITE_LIST = 1028
    const val RESPONSE_CODE_IN_PROGRESS = 2014

    const val RESPONSE_ERROR_CODE_UNAUTHORIZED = 1002
    const val RESPONSE_ERROR_CODE_EVENT_ATTENDANCE = 1024
    const val RESPONSE_ERROR_WAIT = 1001
    const val RESPONSE_UPLOAD_DOCUMENT = 1013
    const val RESPONSE_RE_SUBMIT = 1025

    const val RESPONSE_SOCKET_COMMENT_SUCCESS = 2000
    const val RESPONSE_SOCKET_COMMENT_FAILED = 201
    const val RESPONSE_SOCKET_COMMENT_ALREADY_SENT = 251

    const val GENERATE_SESSION_TOKEN =
        "/${NetworkApiManager.GENRIC_API_VERSION_4_3}/session-token-generate"
    const val HEADER_VERSION_VALUE = 3
    const val HEADER_CONNECTION_VALUE = "keep-alive"
    const val HEADER_ACCEPT_ENCODING_VALUE = "gzip, deflate, br"
    const val HEADER_ACCEPT_VALUE = "*/*"
    const val HEADER_CONTENT_TYPE_VALUE = "application/json"
}
