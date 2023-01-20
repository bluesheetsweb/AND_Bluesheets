package src.networkutil.utilities

import java.util.*

object NetworkSupportUtils {

    @Synchronized
    fun generateRandomUUID(): String {
        var uniqueID =
            NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.PREF_KEY_UUID)
        if (uniqueID.isNullOrEmpty() || uniqueID.trim().isNullOrEmpty()) {
            uniqueID = UUID.randomUUID().toString()
            NetworkSharedPrefUtils.INSTANCE.savePreferences(NetworkConstant.PREF_KEY_UUID, uniqueID)
        }
        return uniqueID!!
    }
}
