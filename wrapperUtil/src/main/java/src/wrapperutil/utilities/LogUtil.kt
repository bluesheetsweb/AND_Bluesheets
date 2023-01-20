package src.wrapperutil.utilities

import android.util.Log

object LogUtil {

    fun logException(tag: String?, funName: String, ex: Exception?) {
        if (ex != null) {
            Log.e(tag, funName + ex.message)
        }
    }

    fun logException(tag: String?, funName: String, ex: Throwable?) {
        if (ex != null) {
            Log.e(tag, funName + ex.message)
        }
    }

    fun e(TAG: String, value: String) {
        // Log.e(TAG, value)
//        AFLogManager.error(TAG,value)
    }

    fun w(TAG: String, value: String) {
        // Log.w(TAG, value)
//        AFLogManager.warning(TAG,value)
    }

    fun d(TAG: String, value: String) {
        // Log.d(TAG, value)
//        AFLogManager.error(TAG,value)
    }

    fun i(TAG: String, value: String) {
//        AFLogManager.info(TAG,value)
        // Log.i(TAG, value)
    }

/*
        switch type{
            case .debug:
            //printDebug(message: message, argument: argument)
            DCUIFKmmsharedManager.shared.afLoging?.debug(msg: message, tag: argument)
            break;
            case .fault:
            //printFault(message: message, argument: argument)
            DCUIFKmmsharedManager.shared.afLoging?.error(msg: message, tag: argument)
            break;
            case .error:
            //printError(message: message, argument: argument)
            DCUIFKmmsharedManager.shared.afLoging?.error(msg: message, tag: argument)
            break;
            case .Default:
            // printDefault(message: message, argument: argument)
            DCUIFKmmsharedManager.shared.afLoging?.debug(msg: message, tag: argument)
            break;
            case .info:
            //printInfo(message: message, argument: argument)
            DCUIFKmmsharedManager.shared.afLoging?.info(msg: message, tag: argument)
            break;
        }
*/
}
