package src.networkutil.utilities

import src.networkutil.listener.OnNetworkAlertListener
import src.networkutil.network.NetworkRequest

object NetworkGlobalCallBack {

    var registerNetworkCallBack: NetworkRequest.IOnResponse? = null

    var onNetworkAlertListener: OnNetworkAlertListener? = null
}
