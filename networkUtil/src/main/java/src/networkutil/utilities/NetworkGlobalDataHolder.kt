package src.networkutil.utilities

import android.util.Log
import src.networkutil.listener.OnGenericUpdateListener
import src.networkutil.listener.OnSocketCommentStatusListener
import src.networkutil.listener.OnSocketListener

object NetworkGlobalDataHolder {

    private var TAG = NetworkGlobalDataHolder::class.java.simpleName

    private var myAuthKey: String = ""
    private var myOrgKey: String = ""
    private var myWorkKey: String = ""

    var isSessionCreationInProgress = false

    var campaignId: String = ""

    var autoRefreshNetworkListener: HashMap<Long?, OnGenericUpdateListener> = hashMapOf()

    var socketCallBackListener: OnSocketListener? = null

    var toolTipsJsonArray: Any? = null

    var liveCommentStatusCallbackListener: OnSocketCommentStatusListener? = null

    var globalOkButton: String? = ""

    fun autoCallAllPendingRequests(token: String?) {
        Log.e(TAG, "autoCallAllPendingRequests called" + autoRefreshNetworkListener.size)
        if (autoRefreshNetworkListener != null) {
            autoRefreshNetworkListener.forEach { (key, value) ->
                println("$key = $value")
                Log.e(TAG, "autoCallAllPendingRequests key" + key)
                Log.e(TAG, "autoCallAllPendingRequests value" + value)
                value.onUpdate(token)
            }
            autoRefreshNetworkListener.clear()
        }
    }


    fun getMyAuthKey(): String {
        if (myAuthKey.isNullOrBlank()) {
            myAuthKey =
                NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.PREF_KEY_USER_AUTH_KEY)
        }
        if (myAuthKey.isNullOrBlank())
            myAuthKey = ""
        return myAuthKey
    }
    fun getOrgAuthKey(): String {
        if (myOrgKey.isNullOrBlank()) {
            myOrgKey =
                NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.PREF_KEY_ORG_AUTH_KEY)
        }
        if (myOrgKey.isNullOrBlank())
            myOrgKey = ""
        return "$myOrgKey"
    }
    fun getWorkAuthKey(): String {
        if (myWorkKey.isNullOrBlank()) {
            myWorkKey =
                NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.PREF_KEY_WORKSPACE_AUTH_KEY)
        }
        if (myWorkKey.isNullOrBlank())
            myWorkKey = ""
        return "$myWorkKey"
    }

    fun clearAllKeys() {
        myAuthKey = ""
        myOrgKey = ""
        myWorkKey = ""
        NetworkSharedPrefUtils.INSTANCE.savePreferences(NetworkConstant.PREF_KEY_USER_AUTH_KEY, "")
        NetworkSharedPrefUtils.INSTANCE.savePreferences(NetworkConstant.PREF_KEY_WORKSPACE_AUTH_KEY, "")
        NetworkSharedPrefUtils.INSTANCE.savePreferences(NetworkConstant.PREF_KEY_ORG_AUTH_KEY, "")
    }
}
