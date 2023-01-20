package src.networkutil.utilities

import androidx.annotation.IntDef
import src.networkutil.utilities.NetworkConstant.REQUEST_TYPE_GET
import src.networkutil.utilities.NetworkConstant.REQUEST_TYPE_POST
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

class NetworkEnumAnnotation(var state: Int) {
    @IntDef(REQUEST_TYPE_GET, REQUEST_TYPE_POST)
    @Retention(RetentionPolicy.SOURCE)
    annotation class RequestType

//    @IntDef(REQUESTING_SERVER_BASE, REQUESTING_SERVER_BASE_LATEST, REQUESTING_SERVER_PUBLICATION,
//            REQUESTING_SERVER_PUBLICATION_OTHER, REQUESTING_SERVER_NOTIFY_OLD, REQUESTING_SERVER_ANALYTICS,
//            REQUESTING_SERVER_4_1,REQUESTING_SERVER_SOCKET, REQUESTING_SERVER_LOCATION)
//    @Retention(RetentionPolicy.SOURCE)
//    annotation class RequestingServer

    @IntDef(
        NetworkConstant.REQUESTING_SERVER_BASE
    )
    @Retention(RetentionPolicy.SOURCE)
    annotation class RequestingServer
}
