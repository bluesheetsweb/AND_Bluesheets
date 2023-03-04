package com.bluesheets.utils

import android.R
import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

object SnackView {
    fun show(activity: Activity?, snackBarMsg: String?) {
        if (activity != null) {
            Snackbar
                .make(
                    activity.window.decorView.findViewById(R.id.content),
                    validateString(snackBarMsg),
                    Snackbar.LENGTH_LONG
                ).show()
        }
    }

    /*
    * if you are passing context from some where then it will be show toast because snackbar can show only for activit
    * */
    fun show(activity: Context?, snackBarMsg: String?) {
        if (activity != null) {
            if (activity is Activity) {
                show(activity as Activity?, snackBarMsg)
            } else {
                Toast.makeText(activity, validateString(snackBarMsg), Toast.LENGTH_LONG).show()
            }
        }
    }

    // for activity and action
    fun show(
        activity: Activity?,
        snackBarMsg: String?,
        actionText: String?,
        clickListener: View.OnClickListener?
    ) {
        if (activity != null) {
            Snackbar
                .make(
                    activity.window.decorView.findViewById(R.id.content),
                    validateString(snackBarMsg),
                    Snackbar.LENGTH_LONG
                )
                .setAction(actionText, clickListener).show()
        }
    }

    // for view and action
    fun show(
        view: View?,
        snackBarMsg: String?,
        actionText: String?,
        clickListener: View.OnClickListener?
    ) {
        if (view != null) {
            Snackbar
                .make(view, validateString(snackBarMsg), Snackbar.LENGTH_LONG)
                .setAction(actionText, clickListener).show()
        }
    }

    // for styling view and action color action
    fun show(
        view: View?,
        viewBgColor: Int,
        colorOfMessage: Int,
        snackBarMsg: String?,
        isCapsMesg: Boolean,
        messageSize: Int,
        actionTextColor: Int,
        actionText: String?,
        clickListener: View.OnClickListener?
    ) {
        if (view != null) {
            val snackbar = Snackbar.make(view, validateString(snackBarMsg), Snackbar.LENGTH_LONG)
            val snackbarView = snackbar.view

            /*  // styling for rest of text
            TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(colorOfMessage);
            textView.setAllCaps(isCapsMesg);
            textView.setTextSize(messageSize<10?20:messageSize);*/

            // styling for background of snackbar
            snackbarView.setBackgroundColor(viewBgColor)

            // styling for action of text
            snackbar.setActionTextColor(actionTextColor)
            snackbar.setAction(actionText, clickListener).show()
        }
    }

    private fun validateString(msg: String?): String {
        return msg ?: "null"
    }
}
