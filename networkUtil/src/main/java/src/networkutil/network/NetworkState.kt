package src.networkutil.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log

object NetworkState {

    private val TAG = NetworkState::class.java.simpleName
    fun isConnectingToInternet(_context: Context): Boolean { // Log.e(TAG, "isConnectingToInternet: " );
        val connectivity =
            _context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.allNetworkInfo
        if (info != null) for (i in info.indices) {
            Log.e(
                TAG,
                "isConnectingToInternet state" + info[i].state
            )
            if (info[i].state == NetworkInfo.State.CONNECTED) {
                return true
            }
        }
        return false
    }
}
